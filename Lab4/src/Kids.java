public class Kids {
    /**
     Class that represents kids actions and values.
     */
    private String name;
    private double count;
    private String adj;

    public Kids(String name, double count, String adj) {
        this.name = name;
        this.count = count;
        this.adj = adj;
    }
    //setter for adj
    public String getAdj() {
        return adj;
    }

    //getter for adj
    public void setAdj(String adj) {
        this.adj = adj;
    }

    //getter for name
    public String getName() {
        return name;
    }

    //setter for name
    public void setName(String name) {
        this.name = name;
    }

    //getter for count
    public double getCount() {
        return count;
    }

    //setter for count
    public void setCount(double count) {
        this.count = count;
    }

    //method for creating a full name
    private String fullName(Kids k){
        return (k.getName() + " в количестве " + k.getCount() + " штуки ");
    }

    //method of climbing to the roof
    public void climb(Kids k, Roof r){
        System.out.println(k.fullName(k) + "вскарабкались " + Location.ROOF.getLocation() + ".");
        r.setClean(false);
        r.getClean();
    }

    //method of picking the poster
    public void pick(Kids k, Poster p){
        System.out.println(k.fullName(k) + "подобрали " + p.getSecondName() + ".");
    }

    //method of hand over a poster
    public void handOver(Kids k, Poster p, Snusmumriken s){
        System.out.println(k.fullName(k) + "желают вручить объект " + p.getName() + " " + s.getName() + "у.");
    }

    //method go down from the roof
    public void goDown(Roof r){
        System.out.println(getAdj() + " " + getName() + " " + "скатывались, спрыгивали, съезжали " + Location.LAND.getLocation() + ".");
        r.setClean(true);
        r.getClean();
    }

    //the first remark of the kids
    public void replic1(){
        System.out.println(getAdj() + " " + getName() + " " + "визжали: \"Тебе письмо!\".");
    }

    //method of pulling children
    public void pull(Jacket j, Snusmumriken s){
        System.out.println(name + " дергают " + s.getName() + "а за " + j.getName() + ".");
        j.setTorn(true);
    }

    //method that checking if the count is correct
    public boolean checkCount() throws CountKidsException {
        if (getCount() < 0) {
            throw new CountKidsException("Ошибка! Число малышей не может быть отрицательным!");
        }
        else {
            return true;
        }
    }

}
