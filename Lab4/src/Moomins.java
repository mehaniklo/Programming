public class Moomins extends Personage {
    /**
     Class that represents the actions and values of the Moomins.
     It extends Personage abstract class.
     */
    private String name;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    //food method
    public void eat(Food f) {
        System.out.println(getName() + " выпил " + f.getFood() + ".");
    }
}
