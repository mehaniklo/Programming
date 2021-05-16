import Collections.*;
import java.util.*;

/**
 * @author Mikhail Mysov
 * @version 2.1
 * Processing user commands
 */
public class OutputHandler {
    /** Field for getting the user's command */
    private String userCommand = "";
    /** Field for dividing user input into a command and an argument to it */
    private String[] command;
    /** Implementations of user commands */
    Commands commands = new Commands();

    /**
     * Constructor for input
     * @param commands - a class that will implement user commands
     */
    public OutputHandler(Commands commands) {
        this.commands = commands;
    }

    /**
     * Method for starting manual input
     */
    public void manualInput() {
        try {
            try (Scanner commandReader = new Scanner(System.in)) {
                if (commands.collectionCreater()) {
                    while (!userCommand.equals("exit")) {
                        System.out.print("Enter a command: ");
                        userCommand = commandReader.nextLine();
                        command = userCommand.trim().toLowerCase().split(" ", 2);
                        try {
                            switch (command[0]) {
                                case "":
                                    break;
                                case "help":
                                    commands.help();
                                    break;
                                case "info":
                                    commands.info();
                                    break;
                                case "show":
                                    commands.show();
                                    break;
                                case "add":
                                    commands.add();
                                    break;
                                case "update_by_id":
                                    commands.update_by_id(command[1]);
                                    break;
                                case "remove_by_id":
                                    commands.remove_by_id(command[1]);
                                    break;
                                case "clear":
                                    commands.clear();
                                    break;
                                case "save":
                                    commands.save();
                                    break;
                                case "execute_script":
                                    commands.execute_script(command[1]);
                                    break;
                                case "exit":
                                    commands.exit();
                                    break;
                                case "add_if_min":
                                    System.out.println("Enter the item characteristics that will be compared with the items in the collection");
                                    commands.add_if_min(new SpaceMarine(commands.updateId(), commands.updateName(), commands.updateDate(), commands.updateCoordinates(), commands.updateHealth(), commands.updateAstartesCategory(), commands.updateWeapon(), commands.updateMeleeWeapon(), commands.updateChapter()));
                                    break;
                                case "add_if_max":
                                    System.out.println("Enter the item characteristics that will be compared with the items in the collection");
                                    commands.add_if_max(new SpaceMarine(commands.updateId(), commands.updateName(), commands.updateDate(), commands.updateCoordinates(), commands.updateHealth(), commands.updateAstartesCategory(), commands.updateWeapon(), commands.updateMeleeWeapon(), commands.updateChapter()));
                                    break;
                                case "remove_greater":
                                    commands.remove_greater(commands.updateHealth());
                                    break;
                                case "print_field_ascending_health":
                                    commands.print_field_ascending_health();
                                    break;
                                case "print_field_descending_category":
                                    commands.print_field_descending_category();
                                    break;
                                case "filter_by_weapon_type":
                                    commands.filter_by_weapon_type(command[1]);
                                    break;
                                default:
                                    System.out.println("Unknown command");
                                    break;
                            }
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            System.out.println("Command argument is missing");
                        }
                    }
                }
            }
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("The program is stopped");
            System.exit(-1);
        }
    }
}