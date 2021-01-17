public class Moomins extends Personage {
    private String name;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void eat(Food f, Moomins m) {

        System.out.println(m.getName() + " выпил " + f.getFood() + ".");
    }
}
