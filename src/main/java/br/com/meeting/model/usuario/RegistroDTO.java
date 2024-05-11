package br.com.meeting.model.usuario;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class RegistroDTO {

    private String name;

    private String email;

    private String phone;

    private String password;

    private String login;

    private UsuarioRole role = UsuarioRole.USER;

}
