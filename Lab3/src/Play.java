public class Play {
    private String name;

    Play(String name){
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void premiere() {
        System.out.println(name + " началась.");
    }
}
