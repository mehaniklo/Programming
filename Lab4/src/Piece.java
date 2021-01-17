public class Piece {
    /**
     class which represents piece
     */
    private String name;
    private double count;

    //getter for count
    public double getCount() {
        return count;
    }

    //setter for count
    public void setCount(double count) {
        this.count = count;
        System.out.println("Count piece = " + count);
    }

    //getter for name
    public String getName() {
        return name;
    }

    //setter for name
    public void setName(String name) {
        this.name = name;
    }

    //method for combining pieces
    public String combine(){
        count = 1;
        return ("Получилось, клочки собрались в один большой клочок!");
    }
}
