import java.util.Objects;

public class Poster implements Flying, Stick {

    private String name;
    private boolean spinning = true;
    private boolean planned = true;
    private boolean sticked = true;

    public String getName() {
        return name;
    }

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

    public void spinningStatus(Poster p) {
        if (p.getSpinning()) {
            System.out.println(p.getName() + " кружится " + Location.FOREST.getLocation() + ".");
        }
        else {
            System.out.println(p.getName() + " не кружится " + Location.FOREST.getLocation() + ".");
        }
    }

    public void plannedStatus(Poster p) {
        if (p.getPlanned()) {
            System.out.print(p.getName() + " планирует " + Location.GLADE.getLocation());
            spinning = false;
        }
        else {
            System.out.print(p.getName() + " остаётся кружиться " + Location.FOREST.getLocation());
        }
    }

    public void stickedStatus(Poster p, Roof r) {
        if (p.getSticked()) {
            System.out.println(" и прилипает " + Location.ROOF.getLocation() + ".");
            planned = false;
            r.setClean(false);
        }
        else {
            System.out.println(p.getName() + " остаётся кружиться " + Location.FOREST.getLocation() + ".");
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
}