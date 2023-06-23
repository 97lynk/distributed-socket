package io.a97lynk.socketsync.pubsub;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class RedisMessagePublisher implements MessagePublisher {

    private RedisTemplate<String, String> redisTemplate;

    private ChannelTopic topic;

    public void publish(String message) {
        log.info("Message sent: {} to {}", message, topic.getTopic());
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
