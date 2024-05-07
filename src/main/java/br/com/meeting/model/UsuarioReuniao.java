package br.com.meeting.model;

import br.com.meeting.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "TB_USUARIO_REUNIAO")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioReuniao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_meeting")
    private Reuniao meeting;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Usuario user;
}
