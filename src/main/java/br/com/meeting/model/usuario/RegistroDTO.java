package br.com.meeting.model.usuario;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class RegistroDTO {

    private String password;

    private String login;
}
