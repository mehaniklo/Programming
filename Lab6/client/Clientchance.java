package client;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import server.data.SpaceMarine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.*;


public class Clientchance {



    public void run() throws Exception {
        try {
            InetSocketAddress addr = new InetSocketAddress(InetAddress.getByName("localhost"), 2437);

            SocketChannel sc = SocketChannel.open();
            sc.connect(addr);

            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            StringBuilder sum = new StringBuilder();

            while (true) {
                System.out.print("Type a command: ");
                String msg = input.readLine();

                ByteBuffer bbread = ByteBuffer.allocate(65535);
                ByteBuffer bbwrite = ByteBuffer.allocate(65535);

                bbwrite = ByteBuffer.wrap(msg.getBytes());

                if (msg.equalsIgnoreCase("exit")) {
                    sc.write(bbwrite);
                    System.out.println("Finishing a program");
                    System.exit(0);}

                else if (msg.equalsIgnoreCase("add")) {
                    Updater updater = new Updater();
                    SpaceMarine spaceMarine = new SpaceMarine(updater.updateId(), updater.updateName(), updater.updateDate(),
                            updater.updateCoordinates(), updater.updateHealth(), updater.updateAstartesCategory(),
                            updater.updateWeapon(), updater.updateMeleeWeapon(), updater.updateChapter());
                    Gson gson = new Gson();
                    sum.append(msg).append(" ").append(gson.toJson(spaceMarine));
                    sc.write(bbwrite.wrap(sum.toString().getBytes()));
                } else if (msg.equalsIgnoreCase("update_by_id")) {
                    Updater updater = new Updater();
                    int id;
                    for (; ; ) {
                        try {
                            Scanner scanner = new Scanner(System.in);
                            System.out.println("Enter ID of element");
                            id = scanner.nextInt();
                            if (id > 0) {
                                break;
                            } else {
                                System.out.println("ID must be a positive number. ");
                            }
                        } catch (InputMismatchException inputMismatchException) {
                            System.out.println("Value must be a number less than 2 billion and positive");
                        } catch (NoSuchElementException noSuchElementException) {
                            System.out.println("Program will be finished now");
                            System.exit(0);
                        }
                    }
                    SpaceMarine spaceMarine = new SpaceMarine(id, updater.updateName(), updater.updateDate(),
                            updater.updateCoordinates(), updater.updateHealth(), updater.updateAstartesCategory(),
                            updater.updateWeapon(), updater.updateMeleeWeapon(), updater.updateChapter());
                    Gson gson = new Gson();
                    sum.append(msg).append(" ").append(gson.toJson(spaceMarine));
                    sc.write(bbwrite.wrap(sum.toString().getBytes()));
                } else if (msg.equalsIgnoreCase("add_if_min")) {
                    Updater updater = new Updater();
                    SpaceMarine spaceMarine = new SpaceMarine(updater.updateId(), updater.updateName(), updater.updateDate(),
                            updater.updateCoordinates(), updater.updateHealth(), updater.updateAstartesCategory(),
                            updater.updateWeapon(), updater.updateMeleeWeapon(), updater.updateChapter());
                    Gson gson = new Gson();
                    sum.append(msg).append(" ").append(gson.toJson(spaceMarine));
                    sc.write(bbwrite.wrap(sum.toString().getBytes()));
                } else if (msg.equalsIgnoreCase("add_if_max")) {
                    Updater updater = new Updater();
                    SpaceMarine spaceMarine = new SpaceMarine(updater.updateId(), updater.updateName(), updater.updateDate(),
                            updater.updateCoordinates(), updater.updateHealth(), updater.updateAstartesCategory(),
                            updater.updateWeapon(), updater.updateMeleeWeapon(), updater.updateChapter());
                    Gson gson = new Gson();
                    sum.append(msg).append(" ").append(gson.toJson(spaceMarine));
                    sc.write(bbwrite.wrap(sum.toString().getBytes()));
                } else {
                    sc.write(bbwrite);
                }
                bbwrite.clear();
                sc.read(bbread);
                String result = new String(bbread.array()).trim();
                //System.out.println(result + "\n");

                System.out.println("\n" + result + "\n");
            }
        }  catch (NoSuchElementException noSuchElementException) {
            System.out.println("The program is stopped");
            System.exit(-1);
        } catch (NullPointerException nullPointerException) {
            System.out.println("The program is stopped");
            System.exit(-1);
        } catch (JsonSyntaxException jsonSyntaxException) {
            System.out.println("The program is stopped");
            System.exit(-1);
        } catch (IOException ioException){
            System.out.println("The program is stopped");
        }


    }
    public static Boolean processConnect(SelectionKey key) {
        SocketChannel sc = (SocketChannel) key.channel();
        try {
            while (sc.isConnectionPending()) {
                sc.finishConnect();
            }
        } catch (IOException e) {
            key.cancel();
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
