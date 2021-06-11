package server.commands;

import server.data.*;
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
 * @version 3.1
 * A class that implements user commands
 */
public class AllCommands {

    private String fileName = "test.json";
    private String fileNameClosing = "test.json";

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public void setFileNameClosing(String fileNameClosing) {
        this.fileNameClosing = fileNameClosing;
    }

    private final HashSet<server.data.SpaceMarine> spaceMarineHashSet = new HashSet<>();
    private String date;

    /** Method of creating information */
    private final HashMap<String, String> infoOnCommands;
    {
        infoOnCommands = new HashMap<>();
        infoOnCommands.put("help: ", "display help for available commands");
        infoOnCommands.put("info: ", "output information about the collection");
        infoOnCommands.put("show: ", "output all elements of the collection in a string representation to the standard output stream");
        infoOnCommands.put("add: ", " add a new item to the collection");
        infoOnCommands.put("update_by_id id: ", "update the value of a collection element whose id is equal to the specified one");
        infoOnCommands.put("remove_by_id id: ", "delete an item from the collection by its id");
        infoOnCommands.put("clear: ", "clear the collection");
        infoOnCommands.put("save: ", "save the collection to a file");
        infoOnCommands.put("execute_script filename: ", "read and execute the script from the specified file");
        infoOnCommands.put("exit: ", "end the program (without saving it to a file)");
        infoOnCommands.put("add_if_max: ", " add a new item to a collection if its value exceeds the value of the largest item in that collection");
        infoOnCommands.put("add_if_min: ", "add a new item to a collection if its value is less than the smallest item in that collection");
        infoOnCommands.put("remove_greater: ", "remove all health items from the collection that exceed the specified value");
        infoOnCommands.put("filter_by_weapon_type: ", "output elements with the value of the weaponType field equal to the specified value");
        infoOnCommands.put("print_field_ascending_health: ", "output the values of the health field of all elements in ascending order");
        infoOnCommands.put("print_field_descending_category: ", "output the values of the category field of all elements in descending order");
    }

    /**
     * Method creating a SpaceMarine collection from a file
     * @return true - success, false - otherwise
     */
    public AllCommands(){
        Gson gson = new Gson();
        try {
            Type collectionType = new TypeToken<HashSet<server.data.SpaceMarine>>(){}.getType();
            HashSet<server.data.SpaceMarine> spaceMarineHashSetNew = gson.fromJson(read(fileName), collectionType);
            int suc = 0;
            int wrong = 0;
            for (SpaceMarine spaceMarine : spaceMarineHashSetNew) {
                if (spaceMarine.getId() == 0 || spaceMarine.getName().equals("null") || spaceMarine.getChapter().getName().equals("null") ||
                        spaceMarine.getCoordinates().getX().equals("null") || spaceMarine.getCoordinates().getY().equals("null")
                        || spaceMarine.getCreationDate().equals("null") || spaceMarine.getHealth() == 0) {
                    System.out.println("Please, do not change the file manually. Collection was not read");
                    wrong++;
                }
                else {
                    spaceMarineHashSet.add(spaceMarine);
                    spaceMarine.setCreationDate(updateDate());
                    suc++;
                }
            }
            System.out.println(suc + " elements were loaded and " + wrong + " elements weren't loaded");
            date = updateDate();

        } catch (JsonSyntaxException jsonSyntaxException) {
            System.out.println("Incorrect representation of the json object");

        }catch (NullPointerException nullPointerException) {
            System.out.println("Please, do not change the file manually. There's an error(");

        }
    }

    /**
     * Class which check file is existed, and can be readable and writeable.
     * @return text-json
     */
    public String read(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        String text = "[]";
        try (Reader reader =  new InputStreamReader(new FileInputStream(fileName), "UTF8")) {
            int data = reader.read();
            while (data != -1) {
                stringBuilder.append((char) data);
                data = reader.read();
            }
        } catch (UnsupportedEncodingException unsupportedEncodingException){
            System.out.println("Incorrect encoding specified");
        } catch (IOException ioException){
            System.out.println("Incorrect file path/read permissions");
            System.exit(-1);
        }
        if (stringBuilder.toString().equals("")) {
            return text;
        } else {
            return stringBuilder.toString();
        }
    }

    /** Method that outputs a list of commands */
    public void help() {
        for (Map.Entry<String, String> a : infoOnCommands.entrySet()) {
            System.out.println(a.getKey() + a.getValue());
        }
    }

    /** Method that outputs information about the collection */
    public String info(){
//        System.out.println("Type of collection: " + spaceMarineHashSet.getClass());
//        System.out.println("Amount of elements in the collection: " + spaceMarineHashSet.size());
//        System.out.println("Date of creation: " + date);
        return ("Type of collection: " + spaceMarineHashSet.getClass() + "\n" +
                "Amount of elements in the collection: " + spaceMarineHashSet.size() + "\n" +
                "Date of creation: " + date );
    }

    /** Method for displaying collection items in a row */
    public void show() {
        for (SpaceMarine spaceMarine : spaceMarineHashSet) {
            System.out.println(spaceMarine.toString());
        }
    }

    /**
     * Method for updating ID
     * @return long ID
     */
    public long updateId() {
        long id = 0;
        for (SpaceMarine spaceMarine : spaceMarineHashSet) {
            if (spaceMarine.getId() > id) {
                id = spaceMarine.getId();
            }
        }
        return id + 3;
    }

    /**
     * Method for updating name
     * @return String name
     */
    public String updateName() {
        for ( ; ;) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter a name: ");
                String name = scanner.nextLine().trim();
                if (name.equals("")) {
                    System.out.println("The field can't be empty, try again ");
                    continue;
                }
                return name;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("The value must be a string, please try again ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("The program is stopped");
                System.exit(-1);
            }
        }
    }

    /**
     * Method for updating y-coordinate
     * @return Float y
     */
    public Float updateY() {
        for ( ; ; ) {
            try {
                System.out.print("Enter Y coordinate, min value is -879: ");
                Scanner scanner = new Scanner(System.in);
                float y = scanner.nextFloat();
                String stringY = Float.toString(y);
                if (y < -879) {
                    System.out.println("Min value is -879, try again ");
                    continue;
                }
                if (stringY.equals("") ) {
                    System.out.println("The field can't be empty, try again ");
                    continue;
                }
                return y;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("The value must be a float-type number, please try again");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("The program is stopped");
                System.exit(-1);
            }
        }
    }

    /**
     * Method for updating x-coordinate
     * @return Float x
     */
    public Float updateX() {
        for ( ; ; ) {
            try {
                System.out.print("Enter X coordinate: ");
                Scanner scanner = new Scanner(System.in);
                float x = scanner.nextFloat();
                String stringX = Float.toString(x);
                if (stringX.equals("") ) {
                    System.out.println("The field can't be empty, try again ");
                    continue;
                }
                return x;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("The value must be a float-type number, try again");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("The program is stopped");
                System.exit(-1);
            }
        }
    }

    /** Method for update coordinates */
    public Coordinates updateCoordinates() {
        return new Coordinates(updateX(), updateY());
    }

    /**
     * Method for updating health
     * @return int health
     */
    public int updateHealth() {
        for ( ; ; ) {
            try {
                System.out.print("Enter health, value must be greater than 0: ");
                Scanner scanner = new Scanner(System.in);
                int health = scanner.nextInt();
                if (health <= 0) {
                    System.out.println("The value must be greater than 0, try again");
                    continue;
                }
                return health;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("The value must be a int-type number, try again");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("The program is stopped");
                System.exit(-1);
            }
        }
    }

    /**
     * Method for updating name of the chapter
     * @return String nameChapter
     */
    public String updateNameChapter() {
        for ( ; ; ) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter a name of chapter: ");
                String nameChapter = scanner.nextLine().trim();
                if (nameChapter.equals("")) {
                    System.out.println("The field can't be empty, try again");
                }
                return nameChapter;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("The value must be a string, try again");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("The program is stopped");
                System.exit(-1);
            }
        }
    }

    /**
     * Method for updating name of parent legion
     * @return String parentLegion
     */
    public String updateParentLegion() {
        for ( ; ; ) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter a name of parent legion: ");
                String parentLegion = scanner.nextLine().trim();
                if (parentLegion.equals("")) {
                    System.out.println("The field can't be empty, try again");
                }
                return parentLegion;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("The value must be a string, try again");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("The program is stopped");
                System.exit(-1);
            }
        }
    }

    /**
     * Method for updating Chapter
     * @return Chapter chapter
     */
    public Chapter updateChapter() {
        return new Chapter(updateNameChapter(), updateParentLegion());
    }

    /**
     * Method for updating astartes
     * @return AstartesCategory astartes Category
     */
    public AstartesCategory updateAstartesCategory() {
        for ( ; ; ) {
            try {
                System.out.println("Enter the desired option number");
                System.out.print("1 - SCOUT, 2 - AGGRESSOR, 3 - ASSAULT, 4 - TACTICAL, 5 - LIBRARIAN ");
                Scanner scanner = new Scanner(System.in);
                int number = scanner.nextInt();
                switch (number) {
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
                System.out.println("Wrong choice, try again...");
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("The value must be a number, try again");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("The program is stopped");
                System.exit(-1);
            }
        }
    }

    /**
     * Method for updating weapon
     * @return Weapon weapon
     */
    public Weapon updateWeapon() {
        for ( ; ; ) {
            try {
                System.out.println("Enter the desired option number");
                System.out.print("1 - BOLT_RIFLE, 2 - HEAVY_FLAMER, 3 - MULTI_MELTA, 4 - MISSILE_LAUNCHER ");
                Scanner scanner = new Scanner(System.in);
                int number = scanner.nextInt();
                switch (number) {
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
                System.out.println("Wrong choice, try again...");
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("The value must be a number, try again");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("The program is stopped");
                System.exit(-1);
            }
        }
    }

    /**
     * Method for updating weapon
     * @return MeleeWeapon melee weapon
     */
    public MeleeWeapon updateMeleeWeapon() {
        for ( ; ; ) {
            try {
                System.out.println("Enter the desired option number");
                System.out.print("1 - CHAIN_SWORD, 2 - POWER_SWORD, 3 - CHAIN_AXE, 4 - MANREAPER, 5 - POWER_BLADE ");
                Scanner scanner = new Scanner(System.in);
                int number = scanner.nextInt();
                switch (number) {
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
                System.out.println("Wrong choice, try again...");
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("The value must be a number, try again");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("The program is stopped");
                System.exit(-1);
            }
        }
    }

    /** Method for printing the current date in a string representation */
    public String updateDate() {
        return ZonedDateTime.now().toString();
    }

    /** Adding method */
    public void add() {
        SpaceMarine newSpaceMarine = new SpaceMarine(updateId(), updateName(), updateDate(), updateCoordinates(), updateHealth(), updateAstartesCategory(), updateWeapon(), updateMeleeWeapon(), updateChapter());
        spaceMarineHashSet.add(newSpaceMarine);
        System.out.println("Element added");
    }

    /** Method for updating an element by it`s ID */
    public void updateById(String id) {
        int k = 0;
        try {
            for (SpaceMarine spaceMarine : spaceMarineHashSet) {
                long longId = spaceMarine.getId();
                String stringId = String.valueOf(longId);
                if (stringId.equals(id)) {
                    spaceMarineHashSet.remove(spaceMarine);
                    SpaceMarine updatedSpaceMarine = new SpaceMarine(longId, updateName(), updateDate(), updateCoordinates(), updateHealth(), updateAstartesCategory(), updateWeapon(), updateMeleeWeapon(), updateChapter());
                    spaceMarineHashSet.add(updatedSpaceMarine);
                    System.out.println("Element updated");
                    k++;
                }
            }
            if (k == 0) {
                System.out.println("The element with this ID was not found");
            }
        }
        catch (ConcurrentModificationException concurrentModificationException) {
        }
    }

    /** Method for removing an element by it`s ID */
    public void removeById(String id) {
        int k=0;
        try {
            for (SpaceMarine spaceMarine : spaceMarineHashSet) {
                long longId = spaceMarine.getId();
                String strId = String.valueOf(longId);
                if (strId.equals(id)) {
                    spaceMarineHashSet.remove(spaceMarine);
                    System.out.println("Element deleted");
                    k++;
                }
            }
            if (k==0) {
                System.out.println("The element with this ID was not found");
            }
        }
        catch (ConcurrentModificationException concurrentModificationException) {
        }
    }

    /** Method for removing all elements from collection */
    public void clear() {
        spaceMarineHashSet.clear();
    }

    /** Method for saving java collection to Json */
    public void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(fileNameClosing), "UTF8")) {
            gson.toJson(spaceMarineHashSet, writer);
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Write access is not available, change this");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Method of disabling the program */
    public void exit() {
        try {
            System.out.println("The program shuts down");
            TimeUnit.SECONDS.sleep(2);
            System.exit(0);
        } catch (InterruptedException interruptedException) {
            System.out.println("The program shuts down");
            System.exit(0);
        }
    }

    /** Method for adding an item to the collection if its health is greater than the one you entered */
    public void addIfMax(SpaceMarine enterSpaceMarine) {
        long maxHealth = Long.MIN_VALUE;
        for (SpaceMarine spaceMarine : spaceMarineHashSet) {
            if (spaceMarine.getHealth() > maxHealth) {
                maxHealth = spaceMarine.getHealth();
            }
        }
        if (enterSpaceMarine.getHealth() > maxHealth) {
            spaceMarineHashSet.add(enterSpaceMarine);
            System.out.println("Element added");
        } else {
            System.out.println("The element is not added because the value of the element does not exceed the value entered");
        }
    }

    /** Method for adding an item to the collection if its health is less than the entered health */
    public void addIfMin(SpaceMarine enterSpaceMarine) {
        long minimalHealth = Long.MAX_VALUE;
        for (SpaceMarine spaceMarine : spaceMarineHashSet) {
            if (spaceMarine.getHealth() < minimalHealth) {
                minimalHealth = spaceMarine.getHealth();
            }
        }
        if (enterSpaceMarine.getHealth() < minimalHealth) {
            spaceMarineHashSet.add(enterSpaceMarine);
            System.out.println("Element added");
        } else {
            System.out.println("The element is not added because the value of the element exceeds the entered value");
        }
    }

    /** Method for remove elements from collection if it`s health more than entered health */
    public void removeGreater(int health) {
        int counter = 0;
        try {
            for (SpaceMarine spaceMarine : spaceMarineHashSet) {
                if (spaceMarine.getHealth() > health) {
                    spaceMarineHashSet.remove(spaceMarine);
                    counter += 1;
                }
            }
            System.out.println("Operation completed. " + counter + " elements were removed");
        }
        catch (ConcurrentModificationException concurrentModificationException) {
        }
    }

    /** Method for counting the number of elements equal to this */
    public void filterByWeaponType(String weapons) {
        int counter = 0;
        for (SpaceMarine spaceMarine : spaceMarineHashSet) {
            try {
                if (spaceMarine.getWeaponType().name().toLowerCase(Locale.ROOT).equals(weapons)){
                    counter += 1;
                }
            }
            catch (NullPointerException nullPointerException){
                System.out.println("Invalid weapon entered");
            }
        }
        System.out.println(counter + " elements are equal");
    }

    /** Method for displaying all elements (health) in ascending order */
    public void printFieldAscendingHealth() {
        ArrayList<Integer> spaceMarineArrayList = new ArrayList<>();
        for (SpaceMarine spaceMarine : spaceMarineHashSet) {
            spaceMarineArrayList.add(spaceMarine.getHealth());
        }
        Collections.sort(spaceMarineArrayList);
        System.out.println("Elements in this collection were sorted in ascending order");
        for (Integer integer : spaceMarineArrayList) {
            System.out.println(integer);
        }
    }

    /** Method for displaying all elements (category) in descending order */
    public void printFieldDescendingCategory() {
        ArrayList<AstartesCategory> astartesCategoriesArrayList = new ArrayList<>();
        for (SpaceMarine spaceMarine : spaceMarineHashSet) {
            astartesCategoriesArrayList.add(spaceMarine.getCategory());
        }
        Collections.sort(astartesCategoriesArrayList);
        System.out.println("Elements in this collection were sorted in descending order.");
        for (AstartesCategory ac : astartesCategoriesArrayList) {
            System.out.println(ac);
        }
    }

    /** Method for passing the file name via a command-line argument */
    public void reading(String[] naming){
        if (naming.length != 1)
            System.out.println("Usage: Pass the file name with a command-line argument.");
        else{
            setFileName(naming[0]);
            setFileNameClosing(naming[0]);
        }
    }

    public HashSet<SpaceMarine> getSpaceMarineHashSet() {
        return spaceMarineHashSet;
    }

    public HashMap getInfoCommands() {
        return infoOnCommands;
    }

        /** Method for executing a script from an external file */
    public void execute_script(String fikeName) {
        try {
            System.out.println("Please do not allow recursion");
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
                        updateById(userCommand[1]);
                        break;
                    case "remove_by_id":
                        removeById(userCommand[1]);
                        break;
                    case "clear":
                        clear();
                        break;
                    case "save":
                        save();
                        break;
                    case "execute_script":
                        System.out.println("This script cannot to contain this cmd");
                        break;
                    case "exit":
                        exit();
                    case "add_if_min":
                        addIfMin(new SpaceMarine(updateId(), updateName(), updateDate(), updateCoordinates(), updateHealth(), updateAstartesCategory(), updateWeapon(), updateMeleeWeapon(), updateChapter()));
                        break;
                    case "add_if_max":
                        addIfMax(new SpaceMarine(updateId(), updateName(), updateDate(), updateCoordinates(), updateHealth(),  updateAstartesCategory(), updateWeapon(), updateMeleeWeapon(), updateChapter()));
                        break;
                    case "remove_greater":
                        removeGreater(updateHealth());
                        break;
                    case "print_field_ascending_health":
                        printFieldAscendingHealth();
                        break;
                    case "print_field_descending_category":
                        printFieldDescendingCategory();
                        break;
                    case "filter_by_weapon_type":
                        filterByWeaponType(userCommand[1]);
                        break;
                    default:
                        reader.readLine();
                        break;
                }
                System.out.println("Command is ended");
            }
            System.out.println("Commands are ended");
            reader.close();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found, try again");
        } catch (IOException ioException) {
            System.out.println("File reading exception, try again");
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            System.out.println("No argument entered, try again");
        }
    }
}