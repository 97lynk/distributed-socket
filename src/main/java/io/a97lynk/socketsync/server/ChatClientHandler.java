package io.a97lynk.socketsync.server;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Slf4j
public class ChatClientHandler extends Thread {

    private final Socket clientSocket;

    private PrintWriter out;

    private BufferedReader in;

    public ChatClientHandler(Socket socket) throws IOException {
        this.clientSocket = socket;

        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void run() {
        try {
            receiveMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveMessage() throws IOException {
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            if (".".equals(inputLine)) {
                log.info("bye");
                out.println("bye");
                break;
            }
            log.info(inputLine);
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void close() throws IOException {
        if (out != null) out.close();
        if (in != null) in.close();
    }
}