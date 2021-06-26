import Answers.Answer;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;

public class Sender  {
    private ExecutorService pool;
    private DatagramSocket socket;
    private FileHandler fileHandler;

    public Sender(DatagramSocket socket, int nThreads) {
        pool = Executors.newFixedThreadPool(nThreads);
        this.socket = socket;
    }

    public void send(Answer answer, InetAddress address, int port) {
        SenderTask task = new SenderTask(socket, answer, address, port);
        pool.execute(task);
    }
}
