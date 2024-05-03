package br.com.meeting.controller;

import br.com.meeting.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/publish")
public class RabbitMQController {

    @Value("${meeting.queue.notification}")
    private String queue;

    @Value("${meeting.exchange.direct}")
    private String exchange;

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQController(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping(value = "/message")
    public ResponseEntity<HttpStatus> postMessage(@RequestBody Message message) {
        log.info("Sending message to exchange {} with routingKey {}", exchange, queue);
        rabbitTemplate.convertAndSend(exchange, queue, message);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
