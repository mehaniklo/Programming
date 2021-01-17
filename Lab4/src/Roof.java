import java.util.Objects;

public class Roof implements Resin {
    /**
     Class that represents roof actions and values.
     */
    private String name;
    private int sizeRoof;
    private String color;
    private boolean tarred;
    private boolean clean;

    Roof(String name, int sizeRoof, String color, boolean tarred, boolean clean) {
        this.name = name;
        this.sizeRoof = sizeRoof;
        this.color = color;
        this.tarred = tarred;
        this.clean = clean;
    }

    //getter for name
    public String getName() {
        return name;
    }

    //setter for name
    public void setName(String name) {
        this.name = name;
    }

    //getter for sizeRoof
    public int getSizeRoof() {
        return sizeRoof;
    }

    //setter for sizeRoof
    public void setSizeRoof(int sizeRoof) {
        this.sizeRoof = sizeRoof;
    }

    //getter for color
    public String getColor() {
        return color;
    }

    //setter for color
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean getTarred() {
        return tarred;
    }

    @Override
    public void setTarred(boolean tarred) {
        this.tarred = tarred;
    }

    //getter for clean
    public boolean getClean() {
        if (clean){
            System.out.println("На крыше сейчас стало пусто...");
        }
        else{
            System.out.println("На крыше что-то есть!");
        }
        return clean;
    }

    //setter for clean
    public void setClean(boolean clean) {
        this.clean = clean;
    }

    //check the status of the object
    public void cleanedStatus(Poster p) {
        if (getClean()) {
            System.out.println(Location.ROOF.getLocation() + " ничего не прилипло... ");
        }
        else {
            System.out.println("* " + Location.ROOF.getLocation() + " прилипла " + p.getName() + " *");
        }
    }

    //check the status of the object
    public void tarredStatus() {
        if (getTarred()) {
            System.out.println("В данный момент " + getName() + " просмолена.");
        }
        else {
            System.out.println("В данный момент " + getName() + " не просмолена.");
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Roof roof = (Roof) o;
        return sizeRoof == roof.sizeRoof &&
                tarred == roof.tarred &&
                clean == roof.clean &&
                Objects.equals(name, roof.name) &&
                Objects.equals(color, roof.color);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString() {
        return "Roof{" +
                "name='" + name + '\'' +
                ", sizeRoof=" + sizeRoof +
                ", color='" + color + '\'' +
                ", tarred=" + tarred +
                ", clean=" + clean +
                '}';
    }
}
