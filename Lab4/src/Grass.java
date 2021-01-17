public class Grass {
    /**
     a class that represents the action with the grass
     */
    final private String NAME = "трава";
    private boolean crumpled;
    private boolean wet;


    public Grass(boolean crumpled, boolean wet) {
        this.crumpled = crumpled;
        this.wet = wet;
    }

    //getter for name
    public String getName() {
        return NAME;
    }

    //getter crumpled
    public boolean getCrumpled() {
        return crumpled;
    }

    //setter for crumpled and creating an object private inner class
    public void setCrumpled(boolean crumpled) {
        this.crumpled = crumpled;
        BladeOfGrass bladeOfGrass = new BladeOfGrass();
        bladeOfGrass.checkCrumpled();
    }

    //getter for wet
    public boolean getWet() {
        return wet;
    }

    //setter for wet
    public void setWet(boolean wet) {
        this.wet = wet;
    }

    //private inner class
    private class BladeOfGrass{

        private double countBladeOfGrass = Math.round(Math.random()*10000);

        public double getCountBladeOfGrass() {
            return countBladeOfGrass;
        }

        public void setCountBladeOfGrass(double countBladeOfGrass) {
            this.countBladeOfGrass = countBladeOfGrass;
        }

        public void checkCrumpled(){
            if (crumpled) {
                System.out.println("Трава замялась! Все " + countBladeOfGrass + " травинки легли на землю...");
            }

            else {
                System.out.println("Трава ровная как струна! Все " + countBladeOfGrass + " травинки стоят и колышутся!");
            }
        }


    }
}
