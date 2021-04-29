package Collections;

/**
 * @author Mikhail Mysov
 * @version 2.1
 * Class for describing field Coordinates of element
 */
public class Coordinates {
    /** field x */
    private float x; //Поле не может быть null
    /** field y */
    private float y; //Значение поля должно быть больше -879

    /**
     * Constructor for making Coordinates field
     * @param x - x-coordinate
     * @param y - y-coordinate
     */
    public Coordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /** Method for printing this field into a string representation */
    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
