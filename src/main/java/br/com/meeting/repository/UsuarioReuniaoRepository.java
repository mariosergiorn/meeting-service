package br.com.meeting.repository;

import br.com.meeting.model.UsuarioReuniao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioReuniaoRepository extends JpaRepository<UsuarioReuniao, Long> {

    List<UsuarioReuniao> findAllByMeetingId(Long id);

}
