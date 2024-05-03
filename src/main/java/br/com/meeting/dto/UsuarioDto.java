package br.com.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioDto {

    private Long id;

    private String name;

    private String email;

    private String phone;

    private String password;
}
