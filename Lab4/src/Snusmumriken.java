public class Snusmumriken extends Personage{
    /**
     Class that represents snusmumriken actions and values.
     It extends Personage class.
     */
    private String name;

    public Snusmumriken(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    //the first remark of the snusmumriken
    public void grumble1(){
        System.out.println(getName() + " ворчал: \"Морровы дети!\".");
    }

    //the second remark of the snusmumriken
    public void grumble2(){
        System.out.println(getName() + " ворчал: \"Ладно, ладно! Что там еще за таинственное письмо?\"");
    }

    //wash method
    public void wash(Stockings s, Kids k){
        if (s.getClean()) {
            System.out.println(name + " говорит: \"" + s.getName() + " же уже чистые! Зачем их стирать? Ладно, я их испачкаю.");
            s.setClean(false);
        }
        else {
            System.out.println(name + " стирает " + s.getName() + " " + k.getName() + "(-ей) " + Location.HOME.getLocation() + ".");
            s.setClean(true);
        }
    }

    //spread method that spreads the poster into pieces
    public void spread(Grass g, Poster poster, Piece p){
        System.out.println(name + " разложил на " + g.getName() + "(-е) " + p.getCount() + " " + p.getName() + "." );
        g.setCrumpled(true);
    }

    //method that randomly combine pieces
    public void combine(Piece p){
        System.out.println(name + " попытался сложить " + p.getCount() + " " + p.getName() + ".");
        if (Math.random() > 0.3){
            System.out.println(p.combine());
        }
        else {
            System.out.println("Ничего не получилось, афишу теперь никогда не прочитать(");
        }
    }
}
