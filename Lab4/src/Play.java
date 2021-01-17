public class Play {
    /**
     class which represents play
     */
    private String name;
    private boolean premiere = true;

    Play(String name){
        this.name = name;
    }

    //getter for count
    public String getName() {
        return this.name;
    }

    //method that declares the beginning of the play
    public void startPremiere() {
        if (premiere) {
            System.out.println(name + " началась.");
        }
    }

    //getter for premiere
    public boolean getPremiere() {
        return premiere;
    }

    //setter for premiere
    public void setPremiere(boolean premiere) {
        this.premiere = premiere;
    }
}
