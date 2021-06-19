package Server;

import Folder.commands.Command;
import Folder.exceptions.ConnectionException;

import javax.jws.soap.SOAPBinding;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ServerMaker {
    protected SocketAddress socketAddress;
    protected ServerSocketChannel serverChannel;
    protected Selector selector;
    private final ConcurrentHashMap<Serializable, String> responses = new ConcurrentHashMap<>();
    public volatile ConcurrentHashMap<String, Serializable> requests = new ConcurrentHashMap<>();
    public volatile ConcurrentHashMap<String, SocketChannel> usersChannels = new ConcurrentHashMap<>();
    boolean itsWritten = false;

    public ServerMaker(int PORT) {
        this.socketAddress = new InetSocketAddress(PORT);
        try {
            selector = Selector.open();
            this.serverChannel = ServerSocketChannel.open();
            serverChannel.bind(socketAddress);
            System.out.println("Waiting for connection.");
        } catch (IOException e) {
            System.out.println("The client is not connected to the server");
            System.exit(1);
        }
    }
    public  ConcurrentHashMap<Serializable, String> getResponses() {return responses;}
    public  void removeResponse(Serializable key) {getResponses().remove(key);}
    public  void addRequest(String user, Serializable request) {
        getRequests().put(user, request);
    }
    public ConcurrentHashMap<String, Serializable> getRequests() {return requests;}



    public SocketChannel acceptConnection() throws IOException {
        SocketChannel socketChannel = null;
        while (socketChannel == null) {
            socketChannel = serverChannel.accept();
        }
        socketChannel.configureBlocking(false);
        return socketChannel;
    }

    public byte[] readData(SocketChannel channel)  {
    byte[] a = new byte[4096];
    ByteBuffer buffer = ByteBuffer.wrap(a);
    try {
        buffer.clear();
        int b = channel.read(buffer);
        while (b == -1 || b == 0) {
            b = channel.read(buffer);
            buffer.clear();
        };
        buffer.flip();
        buffer.clear();
        return a;
    } catch (IOException e) {
        throw new ConnectionException("The client is not connected to the server to receive data");
    }
    }

    public static byte[] serialize(Serializable obj) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Serializable deserialize(byte[] rawData) {

        try {
            if (rawData != null) {
                ByteArrayInputStream bis = new ByteArrayInputStream(rawData);
                ObjectInputStream objectInputStream = new ObjectInputStream(bis);
                return (Serializable) objectInputStream.readObject();
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;

    }

    public void writeData(byte[] bytes, SocketChannel channel) throws ConnectionException {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        try {
            while(channel.write(buffer) == 0) {};
            itsWritten = true;
            getRequests().remove(channel);
        } catch (IOException e) {
            throw new ConnectionException("The client is not connected to the server to send data");
        }
        buffer.flip();
        buffer.clear();
    }

    private void closeChannel(SocketChannel channel) {
        try {
            channel.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

