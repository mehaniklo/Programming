package mainPart;

import commands.*;
import exceptions.*;
import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientPart {
    private String lastString = "";
    private final InputStream inputStream;
    private final Scanner in;
    private final ServerConnect serverDeliver;
    private final CommandDecoder cd = new CommandDecoder();
    private int attempt = 0;
    private UserData userData;

    public ClientPart (ServerConnect serverDeliver, InputStream inputStream) {
        this.serverDeliver = serverDeliver;
        this.inputStream = inputStream;
        this.in = new Scanner(this.inputStream);
    }

    private String safeRead(String field) {
        do {
            if (this.inputStream == System.in) {
                System.out.println(field);
            }
            try {
                lastString = in.nextLine();
            } catch (NoSuchElementException e) {
                in.close();
                System.exit(0);
            }
        } while (lastString.length() > 200);
        return lastString;
    }

    public void authorization() {
        String answer = safeRead("Do you have an account? (Enter No to create an account) ");
        if (answer.trim().toLowerCase().equals("no")) createNewAccount();
        String result = "";
        while (!result.equals("Login completed successfully")) {
            System.out.println("Authorization: ");
            String[] loginAndPassword = loginAndPasswordInput();
            userData = new UserData(loginAndPassword[0], loginAndPassword[1]);
            serverDeliver.writeData(userData);
            //System.exit(1);
            result = (String) serverDeliver.readData();
            System.out.println(result);
        }
    }

    public void writeUserData(Serializable data) {
        userData.setData(data);
        serverDeliver.writeData(userData);
    }

    public void createNewAccount() {
        String[] loginAndPassword = loginAndPasswordInput();
        serverDeliver.writeData(new UserData(loginAndPassword[0], loginAndPassword[1], false));
        System.out.println(serverDeliver.readData());
    }

    private String[] loginAndPasswordInput() {
        String login = "";
        String password = "";
        do {
            login = safeRead("Enter your login: ");
        } while (login.equals(""));
        do {
            password = safeRead("Enter your password: (whitespace characters are not allowed)");
        } while (password.equals("") || password.contains(" "));
        String[] result = new String[2];
        result[0] = login;
        result[1] = password;
        return result;
    }

    public void understanding() {
        String command = "";
        while (!command.equals("exit")) {
            command = safeRead("Enter the command: (help-find out the list of commands, exit-exit the program (without saving))");
            try {
                understand(command);
            } catch (ConnectionException e) {
                System.out.println(e.getMessage());
                break;
            }
            catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void understand(String command) throws ConnectionException {
        String s = "";
        try {
            if (!command.equals("exit")) {
                if (!command.equals("Unreadable Command")) {
                    Command inputCommand = cd.decode(command.trim());
                    if (inputCommand.getClass() == ExecuteScriptCommand.class) {
                        try {
                            inputCommand.execute();
                            if (((ExecuteScriptCommand) inputCommand).getScanner() != null) {
                                String com = ((ExecuteScriptCommand) inputCommand).giveNewCommandFromFile();
                                while (com != null) {
                                    try {
                                        understand(com);
                                    } catch (ConnectionException e) {
                                        System.out.println(e.getMessage());
                                        System.exit(1);
                                    }
                                    com = ((ExecuteScriptCommand) inputCommand).giveNewCommandFromFile();
                                }
                                ExecuteScriptCommand.executeScriptCommands.clear();
                            }
                        } catch (InfiniteRecursionException e) {
                            System.out.println( e.getMessage());
                        }
                    } else {
                        writeUserData(inputCommand);
                        s = ((String) serverDeliver.readData());
                    }
                    if (s != null && s.startsWith("Enter the ticket name:")) {
                        serverDeliver.toggleReconnectionIsNeeded();
                        while (!s.contains("Ticket created")) {
                            String safeRead = safeRead(s);
                            try {
                                //userData.setData(safeRead);
                                writeUserData(safeRead);
                            } catch (ConnectionException e) {
                                System.out.println(e.getMessage());
                                serverDeliver.toggleReconnectionIsNeeded();
                                return;
                            }
                            s = ((String) serverDeliver.readData());
                        }
                    }
                    String output = null;
                    if (s != null) {
                        output = s.replace("Ticket created", "");
                    }
                    System.out.println(output);
                }
            }
        } catch (NumberFormatException e) {System.out.println("The argument has a neutral type (id - int, price - double)");}
        catch(NullPointerException | IllegalArgumentException e){
                if (command.equals("exit")) { System.exit(0); }
                System.out.println("There is no such command.");
        }
        catch(IllegalCountOfArgumentsException e){
                System.out.println(e.getMessage());
        }
    }
}
