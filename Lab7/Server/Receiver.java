
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.FileHandler;

public class Receiver {
    private ExecutorService pool;
    private DatagramSocket socket;
    private Interpreter interpreter;

    private FileHandler fileHandler;

    public Receiver(DatagramSocket socket, Interpreter interpreter, int nThreads) {
        pool = Executors.newFixedThreadPool(nThreads);
        this.socket = socket;
        this.interpreter = interpreter;
        //this.fileHandler=filetext;
    }

    public void receive(){
        ReceiverTask task = new ReceiverTask(socket, interpreter);
        pool.execute(task);
    }


}