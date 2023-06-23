package io.a97lynk.socketsync.controller;

import io.a97lynk.socketsync.pubsub.MessagePublisher;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class PushMessageController {

    private final MessagePublisher messagePublisher;

    @GetMapping
    public String pushMessage(@RequestParam("m") String message) {
        messagePublisher.publish(message);
        return message;
    }
}
