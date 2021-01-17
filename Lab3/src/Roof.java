import java.util.Objects;

public class Roof implements Resin {

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSizeRoof() {
        return sizeRoof;
    }

    public void setSizeRoof(int sizeRoof) {
        this.sizeRoof = sizeRoof;
    }

    public String getColor() {
        return color;
    }

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

    public boolean getClean() {
        return clean;
    }

    public void setClean(boolean clean) {
        this.clean = clean;
    }

    public void cleanedStatus(Roof r, Poster p) {
        if (r.getClean()) {
            System.out.println(Location.ROOF.getLocation() + " ничего не прилипло... ");
        }
        else {
            System.out.println("* " + Location.ROOF.getLocation() + " прилипла " + p.getName() + " *");
        }
    }
    public void tarredStatus(Roof r) {
        if (r.getTarred()) {
            System.out.print("В данный момент " + r.getName() + " просмолена.");
        }
        else {
            System.out.print("В данный момент " + r.getName() + " не просмолена.");
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
