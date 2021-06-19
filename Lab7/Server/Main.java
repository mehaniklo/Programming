package Server;

import Folder.collection.Ticket;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is main
 */
public class Main {

    private static volatile Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            ServerMaker serverMaker = new ServerMaker(2425);
            ServerPart serverPart = new ServerPart(serverMaker);
            Computer computer = new Computer(serverPart, serverMaker);
            serverPart.getDataBaseWorker().connectToDB();
            serverPart.getDataBaseWorker().getCollectionFromDB();
            Thread thread = new Thread(computer::computingRead);
            Thread thread1 = new Thread(computer::computingWrite);
            Thread thread2 = new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNext()) {
                    String next = scanner.next();
                    if (next.trim().toLowerCase().equals("exit")) System.exit(0);
                }
            });
            thread.start();
            thread1.start();
            thread2.start();
            serverPart.readCommands();
        } catch (Exception e) {
            System.out.println("Something went wrong or the client got tired of sitting here and entering random commands");
            e.printStackTrace();
        }
        System.exit(0);
    }
}
