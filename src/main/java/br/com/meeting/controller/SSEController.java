package br.com.meeting.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/sse")
public class SSEController {

    public List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    //Subscription
    @CrossOrigin
    @RequestMapping(value = "/subscribe", consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe() {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            sseEmitter.send(SseEmitter.event().name("INIT"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        emitters.add(sseEmitter);
        sseEmitter.onCompletion(() -> emitters.remove(sseEmitter));
        return sseEmitter;

    }

    @PostMapping(value = "/dispatchEvent")
    public void dispatchEventToClients (@RequestParam String message) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name("notification").data(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
