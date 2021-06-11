package client;

import server.data.*;

import java.time.ZonedDateTime;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class Updater {



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
     * Method for updating ID
     * @return long ID
     */
    public long updateId() {
        for ( ; ; ) {
            try {
                return new Random().nextInt(Integer.MAX_VALUE);
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be integer-type of number. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
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
}
