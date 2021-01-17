public class Hemuli extends Personage {
    /**
     Class that represents the actions and values of the Hemuli.
     It extends Personage abstract class.
     */
    private String size;
    private String name;

    //getter for size
    public String getSize() {
        return size;
    }

    //setter for size
    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    //food method
    public void eat(Moomins m) {
        System.out.println(getSize() + " " + getName() + " успешно угостила " + m.getName() + " " + Location.HOME.getLocation() + ". ");
    }

    
}
