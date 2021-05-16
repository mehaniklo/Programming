package Collections;

import java.time.ZonedDateTime;
import java.util.*;

/**
 * @author Mikhail Mysov
 * @version 2.1
 * Class for describing field Space Marine of element
 */
public class SpaceMarine {
    /** Field ID */
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    /** Field name */
    private String name; //Поле не может быть null, Строка не может быть пустой
    /** Field coordinates */
    private Coordinates coordinates; //Поле не может быть null
    /** Field creation date */
    private String creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    /** Field health */
    private int health; //Значение поля должно быть больше 0
    /** Field astartes category */
    private AstartesCategory category; //Поле может быть null
    /** Field weapon */
    private Weapon weapon; //Поле не может быть null
    /** Field melee weapon */
    private MeleeWeapon meleeWeapon; //Поле может быть null
    /** Field chapter */
    private Chapter chapter; //Поле не может быть null

    /** Constructor for making a space marine */
    public SpaceMarine(long id, String name, String creationDate, Coordinates coordinates, int health, AstartesCategory category, Weapon weaponType, MeleeWeapon meleeWeapon, Chapter chapter) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.coordinates = coordinates;
        this.health = health;
        this.category = category;
        this.weapon = weaponType;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
    }

    /** Default constructor */
    public SpaceMarine(){}

    /**
     * Method for get ID
     * @return long id
     */
    public long getId() {
        return id;
    }

    /**
     * Method for get health
     * @return int health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Method for get astartes category
     * @return category
     */
    public AstartesCategory getCategory() {
        return category;
    }

    /**
     * Method for get weapon
     * @return weapon
     */
    public Weapon getWeaponType() {
        return weapon;
    }

    /** Method for passing a value to the creation date field */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    private List<SpaceMarine> spaceMarineList = null;

    /** Method for passing a value to the persons list */
    public void setSpaceMarine(ArrayList<SpaceMarine> spaceMarines) {
        this.spaceMarineList = spaceMarines;
    }

    /** Method for printing SpaceMarine-class object into string representation */
    @Override
    public String toString() {
        return "SpaceMarine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", health=" + health +
                ", category=" + category +
                ", weaponType=" + weapon +
                ", meleeWeapon=" + meleeWeapon +
                ", chapter=" + chapter +
                '}';
    }
}
