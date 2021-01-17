public class Hemuli extends Personage {
    private String size;
    private String name;

    public String getSize() {
        return size;
    }

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

    public void eat(Hemuli h, Moomins m) {
        System.out.print(h.getSize() + " " + h.getName() + " успешно угостила " + m.getName() + " " + Location.HOME.getLocation() + ". ");
    }

    
}
