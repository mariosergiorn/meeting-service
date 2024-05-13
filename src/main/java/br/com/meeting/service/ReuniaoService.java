package br.com.meeting.service;

import br.com.meeting.dto.ReuniaoDto;
import br.com.meeting.dto.UsuarioDto;
import br.com.meeting.model.Reuniao;
import br.com.meeting.model.usuario.Usuario;
import br.com.meeting.model.UsuarioReuniao;
import br.com.meeting.repository.ReuniaoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReuniaoService {

    private final ReuniaoRepository repository;

    private final ModelMapper modelMapper;

    private final UsuarioReuniaoService usuarioReuniaoService;

    @Transactional
    public ReuniaoDto createMeeting(ReuniaoDto reuniaoDto) {
        Reuniao reuniao = convertToEntity(reuniaoDto);
        Reuniao reuniaoRetorno = repository.save(reuniao);

        for (UsuarioDto usuario : reuniaoDto.getParticipants()) {
            UsuarioReuniao usuarioReuniao = new UsuarioReuniao();
            usuarioReuniao.setMeeting(reuniaoRetorno);
            usuarioReuniao.setUser(convertToEntity(usuario));
            usuarioReuniaoService.create(usuarioReuniao);
        }

        return convertToDTO(reuniao);
    }

    @Transactional(readOnly = true)
    public List<Usuario> getUsersByMeetingId(Long idMeeting) {
        List<UsuarioReuniao> usuarioReuniao = usuarioReuniaoService.getUsersByMeetingId(idMeeting);
        List<Usuario> usuarios = new ArrayList<>();
        for (UsuarioReuniao usuario : usuarioReuniao) {
            usuarios.add(usuario.getUser());
        }
        return usuarios;
    }

    @Transactional(readOnly = true)
    public List<Reuniao> getMeetingsByUserId(Long idUser) {
        List<UsuarioReuniao> usuarioReuniao = usuarioReuniaoService.getMeetingsByUserId(idUser);
        List<Reuniao> meetings = new ArrayList<>();
        for (UsuarioReuniao usuario : usuarioReuniao) {
            meetings.add(usuario.getMeeting());
        }
        return meetings;
    }

    @Transactional(readOnly = true)
    public ReuniaoDto getMeetingById(Long id) {
        Reuniao reuniao = repository.findById(id).orElse(null);
        if (Objects.nonNull(reuniao)) {
            return convertToDTO(reuniao);
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<Reuniao> getAllMeetings() {
        return repository.findAll();
    }

    @Transactional
    public ReuniaoDto updateMeeting(Long meetingId, ReuniaoDto updatedMeetingDTO) {
        if (Objects.nonNull(repository.findById(meetingId).orElse(null))) {
            Reuniao updatedMeeting = repository.save(convertToEntity(updatedMeetingDTO));
            return convertToDTO(updatedMeeting);
        } else {
            return null;
        }
    }

    @Transactional
    public boolean deleteMeeting(Long meetingId) {
        if (repository.existsById(meetingId)) {
            repository.deleteById(meetingId);
            return true;
        } else {
            return false;
        }
    }

    private Usuario convertToEntity(UsuarioDto usuarioDto) {
        return modelMapper.map(usuarioDto, Usuario.class);
    }

    private ReuniaoDto convertToDTO(Reuniao reuniao) {
        return modelMapper.map(reuniao, ReuniaoDto.class);
    }

    private Reuniao convertToEntity(ReuniaoDto reuniao) {
        return modelMapper.map(reuniao, Reuniao.class);
    }
}
