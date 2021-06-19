package Folder.commands;

import Folder.exceptions.IdNotFoundException;
import Folder.exceptions.IllegalCountOfArgumentsException;
import Folder.exceptions.InfiniteRecursionException;
import Server.CommandDecoder;
import Folder.collection.Ticket;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Command class what executes script
 */
public class ExecuteScriptCommand extends CommandWithAdditionalArgument{
    /**file path to execute the script*/
    private String filePath;
    public static final HashSet<String> executeScriptCommands = new HashSet<>();
    private transient Scanner scanner;
    private CommandDecoder cd;


    /**
     * Execute script
     */
    @Override
    public String execute() {
        File file = new File(filePath);
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("the cauldron file was not found");
        }
        catch (SecurityException ex) {
            System.out.println("Not enough access rights to work with the file.");
        }
        cd = new CommandDecoder(getCollection());
        //executeScriptCommands.clear();
        return "Start of script execution";
    }

    public String giveNewCommandFromFile() throws InfiniteRecursionException {

        String command;
        if (scanner.hasNextLine()) {
            command = scanner.nextLine();
            try {
                if (executeScriptCommands.contains(command)) {
                    throw new InfiniteRecursionException();
                }
                if (command.contains("execute_script")) executeScriptCommands.add(command);
                System.out.println(command);
                if (command.equals("exit")) System.exit(0);
                cd.decode(command);
                return command;
            } catch (NullPointerException | IllegalArgumentException | IllegalCountOfArgumentsException | IdNotFoundException e) {
                System.out.println("Failed to execute the command");
            }
        } else return null;
        return "Unreadable Command";
    }

    public Scanner getScanner() {return scanner;}

    /**
     * Getting file path to execute script {@link ExecuteScriptCommand#filePath}
     * @param obj - file path
     */
    @Override
    public void addArgument(String obj) {
        filePath = obj;

    }

    /**
     * @return info about command
     */
    @Override
    public String toString() {
        return "execute_script <file_path> : read and execute the script from the specified file.";
    }
}