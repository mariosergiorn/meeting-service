package br.com.meeting.service;

import br.com.meeting.model.UsuarioReuniao;
import br.com.meeting.repository.UsuarioReuniaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioReuniaoService {

    private final UsuarioReuniaoRepository repository;

    public UsuarioReuniao create(UsuarioReuniao usuarioReuniao) {
        return repository.save(usuarioReuniao);
    }

    public List<UsuarioReuniao> getUsersByMeetingId(Long idMeeting) {
        return repository.findAllByUsuarioId(idMeeting);
    }
}
