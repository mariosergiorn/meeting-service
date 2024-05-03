package br.com.meeting.controller;

import br.com.meeting.dto.ReuniaoDto;
import br.com.meeting.model.Reuniao;
import br.com.meeting.model.Usuario;
import br.com.meeting.service.ReuniaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/meetings")
public class ReuniaoController {

    private final ReuniaoService service;

    public ReuniaoController(final ReuniaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ReuniaoDto> createMeeting(@RequestBody ReuniaoDto reuniaoDto) {
        ReuniaoDto createdMeeting = service.createMeeting(reuniaoDto);
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

    @GetMapping
    public ResponseEntity<List<Reuniao>> getAllUsers() {
        List<Reuniao> meetings = service.getAllMeetings();
        if (meetings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(meetings, HttpStatus.OK);
    }

    @PutMapping("/{meetingId}")
    public ResponseEntity<ReuniaoDto> updateMeeting(@PathVariable Long meetingId, @RequestBody ReuniaoDto meeting) {
        ReuniaoDto updatedMeeting = service.updateMeeting(meetingId, meeting);
        if (Objects.nonNull(updatedMeeting)) {
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

}
