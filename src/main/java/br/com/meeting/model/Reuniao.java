package br.com.meeting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "TB_REUNIOES")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reuniao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_meeting")
    private Long id;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "date", length = 10, nullable = false)
    private String date;

    @Column(name = "location", length = 50, nullable = false)
    private String location;

    @Column(name = "description", length = 50, nullable = false)
    private String description;

    @OneToMany(mappedBy = "meeting")
    private List<UsuarioReuniao> participants;

}
