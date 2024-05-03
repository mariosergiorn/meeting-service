package br.com.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReuniaoDto {

    private Long id;

    private String title;

    private String date;

    private String location;

    private String description;

    private List<UsuarioDto> participants;
}
