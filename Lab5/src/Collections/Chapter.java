package Collections;

/**
 * @author Mikhail Mysov
 * @version 2.1
 * Class for describing field Chapter of element
 */
public class Chapter {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String parentLegion;

    /**
     * Constructor for making Chapter field
     * @param name - Chapter name
     * @param parentLegion - Parent legion
     */
    public Chapter(String name, String parentLegion) {
        this.name = name;
        this.parentLegion = parentLegion;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "name='" + name + '\'' +
                ", parentLegion='" + parentLegion + '\'' +
                '}';
    }
}
