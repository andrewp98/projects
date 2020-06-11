package ro.utcluj.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;

public class Server
{

//    private static SessionFactory sessionFactory;

    static class Connection extends Thread {
        private final Socket clientSocket;
        private final ObjectOutputStream output;
        private final ObjectInputStream input;
        private long lastSentMessageMillis;

        public Connection(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            this.input = new ObjectInputStream(clientSocket.getInputStream());
            this.output = new ObjectOutputStream(clientSocket.getOutputStream());
        }

        public void handleRequest() {
            RequestHandler rq = new RequestHandler();
            try {
                String msg = (String) input.readObject();
                System.out.println(Instant.now() + " Got from client: " + msg);

                String response = rq.handleRequestMessage(msg, this);
                System.out.println(Instant.now() + " Responding with: " + response);
                output.writeObject(response);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (clientSocket.isConnected()) {
                    boolean clientHasData = clientSocket.getInputStream().available() > 0;
                    if (clientHasData) {
                        this.handleRequest();
                    }

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Instant.now() + " client.Client disconnected?");

                // cleanup
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        AppSes appSes = new AppSes();
        try (ServerSocket socket = new ServerSocket(3000))
        {

            while (true)
            {
                System.out.println(Instant.now() + " Waiting for client...");
                Socket clientSocket = socket.accept();
                new Connection(clientSocket).start();
            }
        }
    }
}

