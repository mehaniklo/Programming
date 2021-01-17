import java.util.Objects;

public class Poster implements Flying, Stick {
    /**
     Class that represents poster actions and values.
     */
    private String name;
    private boolean spinning = true;
    private boolean planned = true;
    private boolean sticked = true;
    private final String secondName = "листок";
    private String material;

    //getter for secondName
    public String getSecondName() {
        return secondName;
    }

    //getter for name
    public String getName() {
        return name;
    }

    //setter for name
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean getSpinning() {
        return spinning;
    }

    @Override
    public void setSpinning(boolean spinning) {
        this.spinning = spinning;
    }

    @Override
    public boolean getPlanned() {
        return planned;
    }

    @Override
    public void setPlanned(boolean planned) {
        this.planned = planned;
    }

    @Override
    public boolean getSticked() {
        return sticked;
    }

    @Override
    public void setSticked(boolean sticked) {
        this.sticked = sticked;
    }

    //check the status of the object
    public void spinningStatus() {
        if (getSpinning()) {
            System.out.println(getName() + " кружится " + Location.FOREST.getLocation() + ".");
        }
        else {
            System.out.println(getName() + " не кружится " + Location.FOREST.getLocation() + ".");
        }
    }

    //check the status of the object
    public void plannedStatus() {
        if (getPlanned()) {
            System.out.print(getName() + " планирует " + Location.GLADE.getLocation());
            spinning = false;
        }
        else {
            System.out.print(getName() + " остаётся кружиться " + Location.FOREST.getLocation());
        }
    }

    //check the status of the object
    public void stickedStatus(Roof r) {
        if (getSticked()) {
            System.out.println(" и прилипает " + Location.ROOF.getLocation() + ".");
            planned = false;
            r.setClean(false);
        }
        else {
            System.out.println(getName() + " остаётся кружиться " + Location.FOREST.getLocation() + ".");
        }
    }

    @Override
    public String toString() {
        return "Poster{" +
                "name='" + name + '\'' +
                ", spinning=" + spinning +
                ", planned=" + planned +
                ", sticked=" + sticked +
                '}';
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Poster poster = (Poster) o;
        return spinning == poster.spinning &&
                planned == poster.planned &&
                sticked == poster.sticked &&
                Objects.equals(name, poster.name);
    }

    //getter for material
    public String getMaterial() {
        System.out.println("Материал театральной афиши - " + material + ".");
        return material;
    }

    //setter for material
    public void setMaterial(String material) {
        this.material = material;
    }

    //performs the transformation of the poster into a piece
    public void transformation(Kids k, Piece piece){
        System.out.println(getName() + " превратилась в " + k.getCount() + " клочка.");
        piece.setCount(k.getCount());
    }

    //using static class
    public static class Remnants {

        private final String name = "Остатки";

        public String getName() {
            return name;
        }

        public void dropped(){
            System.out.println(name + " упали " + Location.PIPE.getLocation() + ".");
        }

        public void burned(){
            System.out.println(name + " сгорели.");
        }

    }
}