package group10.client.multiplayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketServer {

    //static ServerSocket variable
    private static ServerSocket server;

    private  ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Socket socket;

    //socket server port on which it will listen
    private static int port = 9876;

    public SocketServer() throws IOException, ClassNotFoundException {
        initConnections();
    }

    public void initConnections() throws IOException {
        //create the socket server object
        server = new ServerSocket(port);
        //keep listens indefinitely until receives 'exit' call or program terminates
        System.out.println("Waiting for the client request");
        //creating socket and waiting for client connection
        socket = server.accept();
        //read from socket to ObjectInputStream object
        ois = new ObjectInputStream(socket.getInputStream());
        //create ObjectOutputStream object
        oos = new ObjectOutputStream(socket.getOutputStream());
    }
    public void closeConnections() throws IOException {
        //close resources
        ois.close();
        oos.close();
        socket.close();
        System.out.println("Shutting down Socket server!!");
        //close the ServerSocket object
        server.close();
    }

    public void sendMessage(String message) throws IOException {
        //write object to Socket
        oos.writeObject(message);
    }

    public String readMessage() throws IOException, ClassNotFoundException {
        //convert ObjectInputStream object to String
        String message = (String) ois.readObject();
        System.out.println("Message Received: " + message);

        //terminate the server if client sends exit request
        if(message.equals("exit")){
            closeConnections();
        }
        return message;
    }
}