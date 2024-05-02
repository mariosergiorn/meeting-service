package br.com.meeting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/publish")
public class RabbitMQController {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQController(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping(value = "/message")
    public ResponseEntity<HttpStatus> postMessage(@RequestParam("exchange") String exchange,
                                                  @RequestParam("routingKey") String routingKey, @RequestBody Object message) {

        log.info("Sending message to exchange {} with routingKey {}", exchange, routingKey);
        rabbitTemplate.convertAndSend(exchange, routingKey, message);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
