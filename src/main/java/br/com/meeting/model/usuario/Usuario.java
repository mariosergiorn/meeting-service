package br.com.meeting.model.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity(name = "TB_USUARIO")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Usuario implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_user")
    private Long id;

    @Column(name = "name", length = 60, nullable = false)
    private String name;

    @Column(name = "email", length = 60, unique = true, nullable = false)
    private String email;

    @Column(name = "phone", length = 15, nullable = false)
    private String phone;

    @Column(name = "password", length = 10, nullable = false)
    private String password;

    @Column(name = "LOGIN", length = 16, nullable = false)
    private String login;

    @Column(name = "ROLE", nullable = false)
    private UsuarioRole role;

    public Usuario(String login, String password){
        this.login = login;
        this.password = password;
        this.role = UsuarioRole.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UsuarioRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
