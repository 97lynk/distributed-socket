package io.a97lynk.socketsync.client;

import io.a97lynk.socketsync.server.ChatClientHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatClient {

   static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", Integer.parseInt(args[0]));
        executorService.execute(new ChatClientHandler(socket));
    }
}
