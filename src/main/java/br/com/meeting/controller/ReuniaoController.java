package br.com.meeting.controller;

import br.com.meeting.config.KeyManager;
import br.com.meeting.dto.ReuniaoDto;
import br.com.meeting.model.Message;
import br.com.meeting.model.Reuniao;
import br.com.meeting.model.usuario.Usuario;
import br.com.meeting.service.ReuniaoService;
import br.com.meeting.utils.Constantes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
@Slf4j
public class ReuniaoController {

    private final ReuniaoService service;

    private final RabbitMQController rabbit;

    private final KeyManager keyManager;

    @PostMapping("/teste")
    public ResponseEntity<Void> teste() {
        byte[] crypto = keyManager.encrypt("Mensagem");
        rabbit.postStringMessage(crypto.toString());
        log.info("Mensagem Cryptografada {}", crypto.toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<ReuniaoDto> createMeeting(@RequestBody ReuniaoDto reuniaoDto) {
        ReuniaoDto createdMeeting = service.createMeeting(reuniaoDto);
        rabbit.postMessage(buildMessage(createdMeeting, Constantes.CREATED));
        return new ResponseEntity<>(createdMeeting, HttpStatus.CREATED);
    }

    @GetMapping("/id/{meetingId}")
    public ResponseEntity<ReuniaoDto> getMeetingById(@PathVariable Long meetingId) {
        ReuniaoDto meeting = service.getMeetingById(meetingId);
        if (Objects.nonNull(meeting)) {
            return new ResponseEntity<>(meeting, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/participants/{meetingId}")
    public ResponseEntity<List<Usuario>> getUsersByMeetingId(@PathVariable Long meetingId) {
        List<Usuario> users = service.getUsersByMeetingId(meetingId);
        if (Objects.nonNull(users)) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/meetings/{userId}")
    public ResponseEntity<List<Reuniao>> getMeetingsByUserId(@PathVariable Long userId) {
        List<Reuniao> meetings = service.getMeetingsByUserId(userId);
        if (Objects.nonNull(meetings)) {
            return new ResponseEntity<>(meetings, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Reuniao>> getAllMeetings() {
        List<Reuniao> meetings = service.getAllMeetings();
        if (meetings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(meetings, HttpStatus.OK);
    }

    @PutMapping("/put/{meetingId}")
    public ResponseEntity<ReuniaoDto> updateMeeting(@PathVariable Long meetingId, @RequestBody ReuniaoDto meeting) {
        ReuniaoDto updatedMeeting = service.updateMeeting(meetingId, meeting);
        if (Objects.nonNull(updatedMeeting)) {
            rabbit.postMessage(buildMessage(updatedMeeting, Constantes.UPDATED));
            return new ResponseEntity<>(updatedMeeting, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{meetingId}")
    public ResponseEntity<Void> deleteMeeting(@PathVariable Long meetingId) {
        boolean deleted = service.deleteMeeting(meetingId);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private Message buildMessage(ReuniaoDto reuniaoDto, String status) {
        return Message.builder().idMeeting(reuniaoDto.getId()).status(status).build();
    }

}
