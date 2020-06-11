package ro.utcluj.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.Instant;

public class ServerConnection extends Thread{

    private final Socket socket;
    private final ObjectOutputStream output;
    private final ObjectInputStream input;

    public ServerConnection(Socket socket) throws IOException
    {
        this.socket = socket;
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());

    }
    @Override
    public void run()
    {
//        System.out.println("A intrat in run");
        try
        {
//            System.out.println("A intrat in try-ul de la run");
            while (socket.isConnected())
            {
                // Seems that input.available() is not reliable?
                boolean serverHasData = socket.getInputStream().available() > 0;
                if (serverHasData) {
//                    System.out.println("AM PRIMIT.");
//                    String msg = getResponse();
//                    ResponseHandler.handleResponseMessage(msg);
                }

                try
                {
                    Thread.sleep(500);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            System.out.println(Instant.now() + " Server disconnected");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void sendRequest(String messageToSend){
        try{
            output.writeObject(messageToSend);
        } catch (IOException e) {
            System.out.println(Instant.now() + " Cannot send message to server");
            e.printStackTrace();
        }
    }

    public String getResponse() {
        String responseMessage = "E GOL";
        try {
            responseMessage = (String) input.readObject();
            System.out.println(Instant.now() + " Server responded with:  " + responseMessage);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(Instant.now() + " Cannot read message from server");
        }

        return responseMessage;
    }
}
