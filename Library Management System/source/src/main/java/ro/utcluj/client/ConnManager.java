package ro.utcluj.client;

import java.io.IOException;
import java.net.Socket;

public class ConnManager {

    private static final String HOST = "localhost";
    private static final int PORT = 3000;
    private static ServerConnection serverConnection;

    public static void setConn() {
        try {
            serverConnection = new ServerConnection(new Socket(HOST, PORT));
            serverConnection.start();
        } catch (IOException e) {
                e.printStackTrace();
        }
    }

    public static ServerConnection getConn(){
        return serverConnection;
    }
}
