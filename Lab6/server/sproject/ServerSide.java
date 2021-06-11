package server.sproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import server.commands.*;
import server.data.SpaceMarine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class ServerSide {

    protected final AllCommands serverCollection;
    /** Map for printing available commands for user */
    private final HashMap<String, AbstractCommand> availableCommands;

    /**
     * Constructor for this class
     * @param serverCollection - object of class CollectionManager
     */
    ServerSide(AllCommands serverCollection) {

        this.serverCollection = serverCollection;
        availableCommands = new HashMap<>();
        availableCommands.put("add", new AddCommand(serverCollection));
        availableCommands.put("add_if_min", new AddIfMinCommand(serverCollection));
        availableCommands.put("add_if_max", new AddIfMaxCommand(serverCollection));
        availableCommands.put("clear", new ClearCommand(serverCollection));
        availableCommands.put("filter_by_weapon_type", new FilterByWeaponTypeCommand(serverCollection));
        availableCommands.put("execute_script", new ExecuteScriptCommand(serverCollection));
        availableCommands.put("exit", new ExitCommand(serverCollection));
        availableCommands.put("print_field_ascending_health", new PrintFieldAscendingHealthCommand(serverCollection));
        availableCommands.put("help", new HelpCommand(serverCollection));
        availableCommands.put("info", new InfoCommand(serverCollection));
        availableCommands.put("remove_by_id", new RemoveByIdCommand(serverCollection));
        availableCommands.put("remove_greater", new RemoveGreaterCommand(serverCollection));
        availableCommands.put("save", new SaveCommand(serverCollection));
        availableCommands.put("show", new ShowCommand(serverCollection));
        availableCommands.put("print_field_descending_category", new PrintFieldDescendingCategoryCommand(serverCollection));
        availableCommands.put("update_by_id", new UpdateByIdCommand(serverCollection));
    }

    public void run() {
        try{
        InetAddress host = InetAddress.getByName("localhost");
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(host, 2437));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        SelectionKey key = null;

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {

                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


                AbstractCommand errorCommand = new AbstractCommand(null) {
                    @Override
                    public String execute() {
                        return "Unknown command, write the help command";
                    }
                };

                if (selector.select() <= 0)
                    continue;
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();

                while (iterator.hasNext()) {
                    key = (SelectionKey) iterator.next();
                    iterator.remove();
                    String answer = "";

                    if (key.isAcceptable()) {
                        SocketChannel sc = serverSocketChannel.accept();
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        System.out.println("$$ Connection Accepted: " + sc.getLocalAddress() + " $$ \n");
                    }

                    if (key.isReadable()) {
                        ByteBuffer bbread = ByteBuffer.allocate(65535);
                        ByteBuffer bbwrite = ByteBuffer.allocate(65535);

                        SocketChannel sc = (SocketChannel) key.channel();

                        sc.read(bbread);
                        String result = new String(bbread.array()).trim();

                        String[] parsedCommand = result.trim().split(" ", 2);


                        if (result.length() <= 0) {
                            sc.close();
                            System.out.println("Connection closed...");
                            System.out.println("Server will keep running. " + "Try running another client to "
                                    + "re-establish connection");
                        } else if (parsedCommand.length == 1) {
                            answer = availableCommands.getOrDefault(parsedCommand[0], errorCommand).execute();
                        } else if (parsedCommand.length == 2) {
                            answer = availableCommands.getOrDefault(parsedCommand[0], errorCommand).execute(parsedCommand[1]);
                        } else answer = "Unknown command, write the help command";

                        bbread.clear();

                        sc.write(bbwrite.wrap(answer.getBytes()));
                        System.out.println(answer);
                    }
                }
            } catch (IOException ioException) {

            }
        }
    }
        catch (IOException exception) {
            System.err.println( " is disconnected to server");
        } catch (NoSuchElementException | ArrayIndexOutOfBoundsException exception) {
            System.out.println("Program will be finished now");
            System.exit(0);
        } catch (NullPointerException nullPointerException) {
            System.out.println("Program will be finished now");
            System.exit(0);
        }
    }
}