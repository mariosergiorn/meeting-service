package br.com.meeting.service;

import br.com.meeting.dto.UsuarioDto;
import br.com.meeting.model.usuario.Usuario;
import br.com.meeting.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository userRepository;

    private final ModelMapper modelMapper;

    public UsuarioService(UsuarioRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(rollbackFor = Throwable.class)
    public UsuarioDto saveUser(UsuarioDto usuarioDto) {
        Usuario usuario = modelMapper.map(usuarioDto, Usuario.class);
        userRepository.save(usuario);
        return modelMapper.map(usuario, UsuarioDto.class);
    }

    @Transactional(readOnly = true)
    public Usuario getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> getUserById(Long id) {
        return userRepository.findById(String.valueOf(id));
    }

    @Transactional(readOnly = true)
    public List<Usuario> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public Usuario updateUser(Long userId, Usuario newUser) {
        Optional<Usuario> existingUserOptional = userRepository.findById(String.valueOf(userId));
        if (existingUserOptional.isPresent()) {
            Usuario existingUser = existingUserOptional.get();
            BeanUtils.copyProperties(newUser, existingUser);
            return userRepository.save(existingUser);
        } else {
            throw new EntityNotFoundException("Usuário Não Encontrado");
        }
    }

    @Transactional
    public boolean deleteUser(Long userId) {
        Optional<Usuario> userOptional = userRepository.findById(String.valueOf(userId));
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return true;
        } else {
            return false;
        }
    }

}
