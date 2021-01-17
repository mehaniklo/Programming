public class Jacket extends Clothes {
    /**
     Class that represents jacket actions and values.
     It extends Clothes class.
     */
    private boolean torn;

    public Jacket(String name, double size, boolean torn) {
        super(name, size);
        this.torn = torn;
    }

    public boolean getTorn() {
        return torn;
    }

    public void setTorn(boolean torn) {
        this.torn = torn;

        //local inner class button
        class Button{
            public void checkButton(){
                if (torn) {
                    System.out.println("Пуговица пиджака оторвалась!");
                }
                else {
                    System.out.println("Пуговица пиджака на месте! ");
                }
            }
        }

        Button button = new Button();

        if (torn) {
            System.out.println("Пиджак рвётся!(");
            button.checkButton();
        }
        else {
            System.out.println("Пиджак всё ещё цел!");
            button.checkButton();
        }

    }


}
