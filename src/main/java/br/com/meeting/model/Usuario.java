package br.com.meeting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity(name = "TB_USUARIOS")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", length = 60, nullable = false)
    private String name;

    @Column(name = "EMAIL", length = 60, unique = true, nullable = false)
    private String email;

    @Column(name = "PHONE", length = 15, nullable = false)
    private String phone;
}
