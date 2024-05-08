package br.com.meeting.controller;

import br.com.meeting.config.TokenService;
import br.com.meeting.model.usuario.AutenticacaoDTO;
import br.com.meeting.model.usuario.LoginResponseDTO;
import br.com.meeting.model.usuario.RegistroDTO;
import br.com.meeting.model.usuario.Usuario;
import br.com.meeting.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("autorizacao")
@AllArgsConstructor
public class AutorizacaoController {

    private AuthenticationManager authenticationManager;

    private UsuarioRepository usuarioRepository;

    private TokenService tokenService;

    @PostMapping("/logar")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AutenticacaoDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getLogin(), data.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/registrar")
    public ResponseEntity<HttpStatus> register(@RequestBody RegistroDTO data){
        if(this.usuarioRepository.findByLogin(data.getLogin()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        Usuario newUser = new Usuario(data.getLogin(), encryptedPassword);

        this.usuarioRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
