package io.a97lynk.socketsync.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class ChatServer {

    private ServerSocket serverSocket;

    @Value("${server.socket.port}")
    private int socketPort;

    private Map<Long, ChatClientHandler> clients = new ConcurrentHashMap<>();

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @PostConstruct
    public void start() throws IOException {
        executorService.submit(() -> {
            try {
                serverSocket = new ServerSocket(socketPort);
                log.info("Start socket service with port: {}", socketPort);
                while (true) {
                    log.info("Waiting for the client request");
                    ChatClientHandler clientHandler = new ChatClientHandler(serverSocket.accept());
                    clientHandler.start();
                    clients.put(System.nanoTime(), clientHandler);
                }
            } catch (IOException ex) {
                log.warn(ex.getMessage());
            }
        });
    }

    public void sendMessage(String message) {
        log.info("Send message {}", message);
        clients.values().forEach(c -> c.sendMessage(message));
    }

    @PreDestroy
    public void stop() throws IOException {
        log.info("End socket service with port: {}", socketPort);
        serverSocket.close();
    }

}
