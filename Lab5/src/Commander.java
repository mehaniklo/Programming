import java.util.*;
import Collections.*;

/**
 * @author Mikhail Mysov
 * @version 2.1
 * How the user's command is processed
 */
public class Commander {
    /** Field for getting the user's command */
    private String userCommand = "";
    /** Field for separating user input into a command and an argument to it */
    private String[] command;
    /** Collection manager for implementing user commands */
    CollectionManager collectionManager = new CollectionManager();

    /**
     * Constructor for making a commander
     * @param collectionManager - CollectionManager class object which will realise user`s commands
     */
    public Commander(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Method for starting interactive mood
     */
    public void interactiveMode() {
        try {
            try (Scanner commandReader = new Scanner(System.in)) {
                    collectionManager.collectionCreater();
                while (!userCommand.equals("exit")) {
                    System.out.print("Enter a command: ");
                    userCommand = commandReader.nextLine();
                    command = userCommand.trim().toLowerCase().split(" ", 2);
                    try {
                        switch (command[0]) {
                            case "":
                                break;
                            case "help":
                                collectionManager.help();
                                break;
                            case "info":
                                collectionManager.info();
                                break;
                            case "show":
                                collectionManager.show();
                                break;
                            case "add":
                                collectionManager.add();
                                break;
                            case "update_by_id":
                                collectionManager.update_by_id(command[1]);
                                break;
                            case "remove_by_id":
                                collectionManager.remove_by_id(command[1]);
                                break;
                            case "clear":
                                collectionManager.clear();
                                break;
                            case "save":
                                collectionManager.save();
                                break;
                            case "execute_script":
                                collectionManager.execute_script(command[1]);
                                break;
                            case "exit":
                                collectionManager.exit();
                                break;
                            case "add_if_min":
                                System.out.println("Enter characteristics of element, which will be compared with elements in collection.");
                                collectionManager.add_if_min(new SpaceMarine(collectionManager.receiveId(), collectionManager.receiveName(), collectionManager.receiveCoordinates(), collectionManager.returnDate(),
                                        collectionManager.receiveHealth(),  collectionManager.receiveAstartesCategory(), collectionManager.receiveWeapon(), collectionManager.receiveMeleeWeapon(), collectionManager.receiveChapter()));
                                break;
                            case "add_if_max":
                                System.out.println("Enter characteristics of element, which will be compared with elements in collection.");
                                collectionManager.add_if_max(new SpaceMarine(collectionManager.receiveId(), collectionManager.receiveName(), collectionManager.receiveCoordinates(), collectionManager.returnDate(),
                                        collectionManager.receiveHealth(),  collectionManager.receiveAstartesCategory(), collectionManager.receiveWeapon(), collectionManager.receiveMeleeWeapon(), collectionManager.receiveChapter()));
                                break;
                            case "remove_greater":
                                collectionManager.remove_greater(collectionManager.receiveHealth());
                                break;
                            case "print_field_ascending_health":
                                collectionManager.print_field_ascending_health();
                                break;
                            case "print_field_descending_category":
                                collectionManager.print_field_descending_category();
                                break;
                            case "filter_by_weapon_type":
                                collectionManager.filter_by_weapon_type(command[1]);
                                break;
                            default:
                                System.out.println("Unknown command.");
                                break;
                        }
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        System.out.println("Argument of command is absent.");
                    }
                }
            }
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Program will be finished now.");
            System.exit(1);
        }
    }
}