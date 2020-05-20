package group10.client.multiplayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SocketClient {
    private InetAddress host;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public SocketClient() throws IOException, ClassNotFoundException, InterruptedException {

        initConnections();
    }

    public void initConnections() throws IOException {
        //get the localhost IP address, if server is running on some other IP, you need to use that
        this.host = InetAddress.getLocalHost();
        //establish socket connection to server
        this.socket = new Socket(host.getHostName(), 9876);
        //write to socket using ObjectOutputStream
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        //read the server response message
        this.ois = new ObjectInputStream(socket.getInputStream());
    }

    public void closeConnections() throws IOException {
        //close resources
        ois.close();
        oos.close();
        System.out.println("client is closed");
    }

    public void sendMessage(Object message) throws IOException {
        oos.writeObject(message);
        if(message.equals("exit")){
            closeConnections();
        }
    }

    public Object readMessage() throws IOException, ClassNotFoundException, InterruptedException {
        Object message = ois.readObject();
        Thread.sleep(10);
        return message;
    }
}