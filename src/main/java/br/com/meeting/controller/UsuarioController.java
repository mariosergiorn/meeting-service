package br.com.meeting.controller;

import br.com.meeting.dto.UsuarioDto;
import br.com.meeting.model.Usuario;
import br.com.meeting.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(final UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public UsuarioDto save(@RequestBody UsuarioDto userRequest) {
        return service.saveUser(userRequest);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<Usuario> getByName(@PathVariable String name) {
        return ResponseEntity.ok(service.getUserByName(name));
    }

    @GetMapping(value = "/id/{userId}")
    public ResponseEntity<Optional<Usuario>> getById(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getUserById(userId));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsers() {
        List<Usuario> users = service.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Usuario> updateUser(@PathVariable Long userId, @RequestBody Usuario user) {
        Usuario updatedUser = service.updateUser(userId, user);
        if (Objects.nonNull(updatedUser)) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        boolean deleted = service.deleteUser(userId);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
