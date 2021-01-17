public class Stockings extends Clothes{
    /**
     Class that represents stockings actions and values.
     It extends Clothes class.
     */
    private boolean clean;

    public Stockings(String name, double size, boolean clean) {
        super(name, size);
        this.clean = clean;
    }

    //getter for clean
    public boolean getClean() {
        return clean;
    }

    //setter for clean
    public void setClean(boolean clean) {
        this.clean = clean;
        if (getClean()) {
            System.out.println("Чулки стали чистыми!");
        }
        else{
            System.out.println("Чулки стали грязными!");
        }
    }


}
