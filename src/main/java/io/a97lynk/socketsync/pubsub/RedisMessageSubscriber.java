package io.a97lynk.socketsync.pubsub;

import io.a97lynk.socketsync.server.ChatServer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class RedisMessageSubscriber implements MessageListener {

    private final ChatServer chatServer;

    public void onMessage(Message message, byte[] pattern) {
        log.info("Message received: {}", message.toString());
        chatServer.sendMessage(new String(message.getBody()));
    }
}