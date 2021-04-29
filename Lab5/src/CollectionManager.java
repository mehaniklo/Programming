import Collections.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Mikhail Mysov
 * @version 2.1
 * A class that implements user commands
 */
public class CollectionManager {

    private String fileName;
    private String fileNameClosing;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public void setFileNameClosing(String fileNameClosing) {
        this.fileNameClosing = fileNameClosing;
    }

    //private final String fileName = "test.json";
    //private final String fileNameClosing = "test.json";
    private final HashSet<SpaceMarine> spaceMarineHashSet = new HashSet<>();
    private ZonedDateTime initializationDate;

    /** Method creating a manual */
    private final HashMap<String, String> commandsInfo;
    {
        commandsInfo = new HashMap<>();
        commandsInfo.put("help", " - display help for available commands");
        commandsInfo.put("info", " - print all elements in string representation to standard output");
        commandsInfo.put("show", " - display all elements of the collection in a string representation");
        commandsInfo.put("add", " - add new element to the collection");
        commandsInfo.put("update_by_id id", " - update the element`s value, whose ID is equal to the given." +
                " You should to enter ID after entering a command.");
        commandsInfo.put("remove_by_id id", " - remove an element from the collection by its ID." +
                " You should to enter ID after entering a command.");
        commandsInfo.put("clear", " - clear the collection");
        commandsInfo.put("save", " - save the collection to file");
        commandsInfo.put("execute_script filename", " - read and execute a script from specified file." +
                " You should to enter path to file after entering a command.");
        commandsInfo.put("exit", " - end the program (without saving to file)");
        commandsInfo.put("add_if_max", " - add new element to the collection, if it`s value is greater, " +
                "than biggest element of this collection. You should to enter characteristics of" +
                " comparing element after entering a command.");
        commandsInfo.put("add_if_min", " - add new element to the collection, if it`s value less, " +
                "than smallest element of this collection. You should to enter characteristics of" +
                " comparing element after entering a command.");
        commandsInfo.put("remove_greater", " - remove from the collection all elements greater than the specified" +
                " one. You should to enter a height which will be comparing with element`s heights.");
        commandsInfo.put("filter_by_weapon_type", " output count elements whose weaponType field value" +
                        " is equal to the specified value");
        commandsInfo.put("print_field_ascending_health", " output the values of the health" +
                        " field of all elements in ascending order");
        commandsInfo.put("print_field_descending_category", "output the values of the category"
                        + "field of all elements in descending order");     }

    /**
     * Method that creates a SpaceMarine collection based on data from the fileName file
     * @return true - success, false - otherwise
     */
    public boolean collectionCreater() {
        Gson gson = new Gson();
        try {

            Type collectionType = new TypeToken<HashSet<SpaceMarine>>(){}.getType();
            HashSet<SpaceMarine> spaceMarineHashSetnew = gson.fromJson(read(fileName), collectionType);
            for (SpaceMarine spaceMarine : spaceMarineHashSetnew) {
                spaceMarineHashSet.add(spaceMarine);
                spaceMarine.setCreationDate(ZonedDateTime.now().toString());
            }
            initializationDate = ZonedDateTime.now();
            return true;
        } catch (Exception e) {
            System.out.println("Incorrect representation of the object.");
            return false;
        }
    }

    /**
     * Class which check file is existed, and can be readable and writeable.
     * @return text-json
     */
    public String read(String fileName) {
        String textJson = "";
        try (FileInputStream fis = new FileInputStream(fileName);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr)) {
            String line;
            while ((line = br.readLine()) != null) {
                textJson += line + "\n";
            }
        } catch (Exception e) {
            System.out.println("wrong file path for FileName.");
        }
        return textJson;
    }

    /** Method for printing manual for user */
    public void help() {
        for (Map.Entry<String, String> entry : commandsInfo.entrySet()) {
            System.out.println(entry.getKey() + entry.getValue());
        }
    }

    /** Method for printing information about the collection */
    public void info(){
        System.out.println("Type of collection: " + spaceMarineHashSet.getClass());
        System.out.println("Amount of elements in the collection: " + spaceMarineHashSet.size());
        System.out.println("Date of creation: " + initializationDate);
        System.out.println("Date of change: " + initializationDate);
    }

    /** Method for printing collection elements into the string representation */
    public void show() {
        for (SpaceMarine spaceMarine : spaceMarineHashSet) {
            System.out.println(spaceMarine.toString());
        }
    }

    /**
     * Method for receiving ID of element
     * @return long ID
     */
    public long receiveId() {
        long maxId = 0;
        for (SpaceMarine spaceMarine : spaceMarineHashSet) {
            if (spaceMarine.getId() > maxId) {
                maxId = spaceMarine.getId();
            }
        }
        return maxId + 1;
    }

    /**
     * Method for receiving name of element
     * @return String name
     */
    public String receiveName() {
        for ( ; ; ) {
            try {
                System.out.println("Attention! If name will be so long, you may lose some part of this information");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter a name: ");
                String name = scanner.nextLine().trim();
                if (name.equals("")) {
                    System.out.println("This value cannot be empty. Try again");
                    continue;
                }
                return name;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be string. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    /**
     * Method for receiving y-coordinate of element
     * @return Float y
     */
    public Float receiveY() {
        for ( ; ; ) {
            try {
                System.out.print("Enter Y coordinate. Min value is -879. ");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Value cannot be empty. ");
                float y = scanner.nextFloat();
                System.out.print("Value cannot be empty. ");
                String yy = Float.toString(y);
                if (y < -879) {
                    System.out.println("Min value is -879. Try again. ");
                    continue;
                }
                if (yy.equals("") ) {
                    System.out.println("This value cannot be empty. Try again. ");
                    continue;
                }
                return y;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a float-type number. Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    /**
     * Method for receiving x-coordinate of element
     * @return Float x
     */
    public Float receiveX() {
        for ( ; ; ) {
            try {
                System.out.print("Enter X coordinate. This value cannot be empty. ");
                Scanner scanner = new Scanner(System.in);
                return scanner.nextFloat();
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a float-type number. Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    /** Method for making coordinates by using methods receiveX() and receiveY() */
    public Coordinates receiveCoordinates() {
        return new Coordinates(receiveX(), receiveY());
    }

    /**
     * Method for receiving height of element
     * @return int height
     */
    public int receiveHealth() {
        for ( ; ; ) {
            try {
                System.out.print("Enter health. Value must be greater than 0. ");
                Scanner scanner = new Scanner(System.in);
                int health = scanner.nextInt();
                if (health <= 0) {
                    System.out.println("This value must be greater than 0. Try again. ");
                    continue;
                }
                return health;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a long-type number. Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    /**
     * Method for receiving name of the chapter
     * @return String nameChapter
     */
    public String receiveNameChapter() {
        for ( ; ; ) {
            try {
                System.out.println("Attention! If name will be so long, you may lose some part of this information");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter a name of chapter: ");
                String nameChapter = scanner.nextLine().trim();
                if (nameChapter.equals("")) {
                    System.out.println("This value cannot be empty. Try again. ");
                }
                return nameChapter;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a string. Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    /**
     * Method for receiving name of parent legion
     * @return String parentLegion
     */
    public String receiveParentLegion() {
        for ( ; ; ) {
            try {
                System.out.println("Attention! If name will be so long, you may lose some part of this information");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter a name of parent legion: ");
                String parentLegion = scanner.nextLine().trim();
                if (parentLegion.equals("")) {
                    System.out.println("This value cannot be empty. Try again. ");
                }
                return parentLegion;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a string. Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    /**
     * Method for receiving Chapter field by using methods receiveNameChapter(), receiveParentLegion()
     * @return Chapter chapter
     */
    public Chapter receiveChapter() {
        return new Chapter(receiveNameChapter(), receiveParentLegion());
    }

    /**
     * Method for receiving astartes of element
     * @return AstartesCategory astartes Category
     */
    public AstartesCategory receiveAstartesCategory() {
        for ( ; ; ) {
            try {
                System.out.println("Choose variant of Astartes Category. Enter the number corresponding to the desired option. ");
                System.out.print("Variants: 1 - SCOUT, 2 - AGGRESSOR, 3 - ASSAULT, 4 - TACTICAL, 5 - LIBRARIAN. ");
                Scanner scanner = new Scanner(System.in);
                int acChoose = scanner.nextInt();
                switch (acChoose) {
                    case 1:
                        return AstartesCategory.SCOUT;
                    case 2:
                        return AstartesCategory.AGGRESSOR;
                    case 3:
                        return AstartesCategory.ASSAULT;
                    case 4:
                        return AstartesCategory.TACTICAL;
                    case 5:
                        return AstartesCategory.LIBRARIAN;
                    default:
                        break;
                }
                System.out.println("You should to enter 1, 2, 3, 4 or 5. Try again. ");
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a number (1, 2, 3, 4 or 5). Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    /**
     * Method for receiving weapon of element
     * @return Weapon weapon
     */
    public Weapon receiveWeapon() {
        for ( ; ; ) {
            try {
                System.out.println("Choose variant of weapon. Enter the number corresponding to the desired option.");
                System.out.print("Variants: 1 - BOLT_RIFLE, 2 - HEAVY_FLAMER, 3 - MULTI_MELTA, 4 - MISSILE_LAUNCHER. ");
                Scanner scanner = new Scanner(System.in);
                int weapon = scanner.nextInt();
                switch (weapon) {
                    case 1:
                        return Weapon.BOLT_RIFLE;
                    case 2:
                        return Weapon.HEAVY_FLAMER;
                    case 3:
                        return Weapon.MULTI_MELTA;
                    case 4:
                        return Weapon.MISSILE_LAUNCHER;
                    default:
                        break;
                }
                System.out.println("You should to enter 1, 2, 3 or 4. Try again. ");
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a number (1, 2, 3 or 4). Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    /**
     * Method for receiving weapon of element
     * @return MeleeWeapon melee weapon
     */
    public MeleeWeapon receiveMeleeWeapon() {
        for ( ; ; ) {
            try {
                System.out.println("Choose variant of melee weapon. Enter the number corresponding to the desired option.");
                System.out.print("Variants: 1 - CHAIN_SWORD, 2 - POWER_SWORD, 3 - CHAIN_AXE, 4 - MANREAPER, 5 - POWER_BLADE. ");
                Scanner scanner = new Scanner(System.in);
                int meleeWeapon = scanner.nextInt();
                switch (meleeWeapon) {
                    case 1:
                        return MeleeWeapon.CHAIN_SWORD;
                    case 2:
                        return MeleeWeapon.POWER_SWORD;
                    case 3:
                        return MeleeWeapon.CHAIN_AXE;
                    case 4:
                        return MeleeWeapon.MANREAPER;
                    case 5:
                        return MeleeWeapon.POWER_BLADE;
                    default:
                        break;
                }
                System.out.println("You should to enter 1, 2, 3, 4 or 5. Try again. ");
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a number (1, 2, 3, 4 or 5). Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    /** Method for printing the current date in a string representation */
    public String returnDate() {
        return ZonedDateTime.now().toString();
    }

    /** Method for adding element by using all receive-fields methods */
    public void add() {
        SpaceMarine newSpaceMarine = new SpaceMarine(receiveId(), receiveName(), receiveCoordinates(), returnDate(),
                receiveHealth(),  receiveAstartesCategory(), receiveWeapon(), receiveMeleeWeapon(), receiveChapter());
        spaceMarineHashSet.add(newSpaceMarine);
    }

    /** Method for updating the element by it`s ID */
    public void update_by_id(String id) {
        for (SpaceMarine spaceMarine : spaceMarineHashSet) {
            long longId = spaceMarine.getId();
            String strId = String.valueOf(longId);
            if (strId.equals(id)) {
                spaceMarineHashSet.remove(spaceMarine);
                SpaceMarine updatedSpaceMarine = new SpaceMarine(longId, receiveName(), receiveCoordinates(), spaceMarine.returnCreationDate(),
                        receiveHealth(),  receiveAstartesCategory(), receiveWeapon(), receiveMeleeWeapon(), receiveChapter());
                spaceMarineHashSet.add(updatedSpaceMarine);
                System.out.println("Element was updated successfully.");
            }
        }
        System.out.println("Element with this ID is not defined. Try again.");
    }

    /** Method for removing the element by it`s ID */
    public void remove_by_id(String id) {
        try {
            for (SpaceMarine spaceMarine : spaceMarineHashSet) {
                long longId = spaceMarine.getId();
                String strId = String.valueOf(longId);
                if (strId.equals(id)) {
                    spaceMarineHashSet.remove(spaceMarine);
                    System.out.println("Element was deleted successfully.");
                }
            }
            System.out.println("Element with this ID is not defined.");
        }
        catch (ConcurrentModificationException concurrentModificationException) {
        }
    }

    /** Method for removing all elements from collection */
    public void clear() {
        spaceMarineHashSet.clear();
        System.out.println("Collection was cleaned successfully.");
    }

    /** Method for switching off the program */
    public void exit() {
        try {
            System.out.println("Program will be finished now. ");
            TimeUnit.SECONDS.sleep(3);
            System.exit(0);
        } catch (InterruptedException interruptedException) {
            System.out.println("Program will be finished now.");
            System.exit(0);
        }
    }

    /** Method for adding element to collection if it`s health less than entered health */
    public void add_if_min(SpaceMarine example) {
        long minimalHealth = Long.MAX_VALUE;
        for (SpaceMarine spaceMarine : spaceMarineHashSet) {
            if (spaceMarine.getHealth() < minimalHealth) {
                minimalHealth = spaceMarine.getHealth();
            }
        }
        if (example.getHealth() < minimalHealth) {
            spaceMarineHashSet.add(example);
            System.out.println("Element was added successfully.");
        } else {
            System.out.println("Element was not added to collection because it's health  " +
                    "greater than lower element`s health in the collection.");
        }
    }

    /** Method for adding element to collection if it's health is greater than entered health */
    public void add_if_max(SpaceMarine example) {
        long maxHealth = Long.MIN_VALUE;
        for (SpaceMarine spaceMarine : spaceMarineHashSet) {
            if (spaceMarine.getHealth() > maxHealth) {
                maxHealth = spaceMarine.getHealth();
            }
        }
        if (example.getHealth() > maxHealth) {
            spaceMarineHashSet.add(example);
            System.out.println("Element was added successfully.");
        } else {
            System.out.println("Element was not added to collection because it's health  " +
                    "less than highest element`s health in the collection.");
        }
    }

    /** Method for remove elements from collection if it`s health more than entered health */
    public void remove_greater(int health) {
        int counter = 0;
        try {
            for (SpaceMarine spaceMarine : spaceMarineHashSet) {
                if (spaceMarine.getHealth() > health) {
                    spaceMarineHashSet.remove(spaceMarine);
                    counter += 1;
                }
            }
            System.out.println("Operation was finished successfully. " + counter + " elements were deleted.");
        }
        catch (ConcurrentModificationException concurrentModificationException) {
        }
    }


    /** Method for displaying all elements (health) in ascending order */
    public void print_field_ascending_health() {
        ArrayList<Integer> spaceMarineArrayList = new ArrayList<>();
        for (SpaceMarine spaceMarine : spaceMarineHashSet) {
            spaceMarineArrayList.add(spaceMarine.getHealth());
        }
        Collections.sort(spaceMarineArrayList);
        System.out.println("Elements of this collection were sorted in ascending order.");
        for (Integer integer : spaceMarineArrayList) {
            System.out.println(integer);
        }
    }

    /** Method for displaying all elements (category) in descending order */
    public void print_field_descending_category() {
        ArrayList<AstartesCategory> astartesCategoriesArrayList = new ArrayList<>();
        for (SpaceMarine spaceMarine : spaceMarineHashSet) {
            astartesCategoriesArrayList.add(spaceMarine.getCategory());
        }
        Collections.sort(astartesCategoriesArrayList);
        System.out.println("Elements of this collection were sorted in descending order.");
        for (AstartesCategory ac : astartesCategoriesArrayList) {
            System.out.println(ac);
        }
    }

    /** Method for counting amount elements equal to this one */
    public void filter_by_weapon_type(String weapons) {
        int count = 0;
        for (SpaceMarine spaceMarine : spaceMarineHashSet) {
            try {
                if (spaceMarine.getWeaponType().name().toLowerCase(Locale.ROOT).equals(weapons)){
                    count += 1;
                }
            }
            catch (NullPointerException nullPointerException){
                System.out.println("Invalid weapon entered");
            }
        }
        System.out.println(count + " elements are equal");
    }

    /** Method for saving java collection to Json */
    public void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(fileNameClosing)) {
            gson.toJson(spaceMarineHashSet, writer);
            System.out.println("Object saved successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reading(String[] naming){
        if (naming.length != 1)
            System.out.println("Usage: Pass the file name with a command-line argument.");
        else{
            setFileName(naming[0]);
            setFileNameClosing(naming[0]);
        }
    }

        /** Method for executing script from external file */
    public void execute_script(String fikeName) {
        try {
            System.out.println("To avoid recursion, your file cannot contain execute script commands.");
            BufferedReader reader = new BufferedReader(new FileReader(fikeName));
            String[] userCommand;
            String cmd;
            while((cmd = reader.readLine()) != null) {
                userCommand = cmd.trim().toLowerCase().split(" ", 2);
                switch (userCommand[0]) {
                    case "help":
                        help();
                        break;
                    case "info":
                        info();
                        break;
                    case "show":
                        show();
                        break;
                    case "add":
                        add();
                        break;
                    case "update_by_id":
                        update_by_id(userCommand[1]);
                        break;
                    case "remove_by_id":
                        remove_by_id(userCommand[1]);
                        break;
                    case "clear":
                        clear();
                        break;
                    case "save":
                        save();
                        break;
                    case "execute_script":
                        System.out.println("This script cannot to contain this cmd.");
                        break;
                    case "exit":
                        exit();
                    case "add_if_min":
                        add_if_min(new SpaceMarine(receiveId(), receiveName(), receiveCoordinates(), returnDate(),
                                receiveHealth(),  receiveAstartesCategory(), receiveWeapon(), receiveMeleeWeapon(), receiveChapter()));
                        break;
                    case "add_if_max":
                        add_if_max(new SpaceMarine(receiveId(), receiveName(), receiveCoordinates(), returnDate(),
                                receiveHealth(),  receiveAstartesCategory(), receiveWeapon(), receiveMeleeWeapon(), receiveChapter()));
                        break;
                    case "remove_greater":
                        remove_greater(receiveHealth());
                        break;
                    case "print_field_ascending_health":
                        print_field_ascending_health();
                        break;
                    case "print_field_descending_category":
                        print_field_descending_category();
                        break;
                    case "filter_by_weapon_type":
                        filter_by_weapon_type(userCommand[1]);
                        break;
                    default:
                        reader.readLine();
                        break;
                }
                System.out.println("Command is ended.");
            }
            System.out.println("Commands are ended.");
            reader.close();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found. Try again.");
        } catch (IOException ioException) {
            System.out.println("File reading exception. Try again.");
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            System.out.println("No argument entered. Try again.");
        }
    }
}