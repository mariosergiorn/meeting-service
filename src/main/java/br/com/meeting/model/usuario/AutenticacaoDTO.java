package br.com.meeting.model.usuario;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class AutenticacaoDTO {

    private String login;

    private String password;
}
