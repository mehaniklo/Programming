public abstract class Clothes {
    /**
     abstract class to describe the clothing
     */
    private String name;
    private double size;

    public Clothes(String name, double size) {
        this.name = name;
        this.size = size;
    }
    //getter for name
    public String getName() {
        return name;
    }

    //setter for name
    public void setName(String name) {
        this.name = name;
    }

    //getter for size
    public double getSize() {
        return size;
    }

    //setter for size
    public void setSize(double size) {
        this.size = size;
    }
}
