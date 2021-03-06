import Data.SpaceMarines;
import Exceptions.CommandAlreadyExistsException;
import Exceptions.RightException;
import Exceptions.SameIdException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.*;

public class Server {
    public static int SERVER_PORT = 8954;
    public static final Logger logger = Logger.getLogger(Server.class.getName());
    String source;
    private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    private static ThreadPoolExecutor executor;

    public static void main(String[] args) throws SameIdException, InvocationTargetException, IllegalAccessException, InstantiationException, RightException, NoSuchMethodException, CommandAlreadyExistsException, IOException, ClassNotFoundException {
        String envVariable = "Logging.txt";

        Server server = new Server(envVariable);
        server.launch();
    }

    public Server(String source) throws IOException {
        this.source=source;
        fileTxt=new FileHandler(source);
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);
    }

    public void launch() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, CommandAlreadyExistsException, SameIdException, RightException, IOException, ClassNotFoundException {
        DatagramSocket socket = setSocket();
        if (socket != null) {
            logger.info("Сервер запущен");

            Scanner scanner = new Scanner(System.in);
            Sender sender = new Sender(socket,10);
            Interpreter interpreter = new Interpreter(sender,socket);
            interpreter.work();
            Receiver receiver = new Receiver(socket, interpreter, 10);

            receiver.receive();

            shutDownHook();
            while (true) {
                interpreter.askCommand(scanner);
            }
        }
    }

    public DatagramSocket setSocket() {
        try {
            return new DatagramSocket(SERVER_PORT);
        } catch (SocketException e) {
            e.printStackTrace();

        }
        return null;
    }

    private void shutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Сервер остановлен");

        }));
    }
}