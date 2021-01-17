public class Main {

    public static void main(String[] args) {

        Play play = new Play("Пьеса");
        Food food = new Food();
        Hemuli hemuli = new Hemuli();
        Moomins moomins = new Moomins();
        Poster poster = new Poster();
        Roof roof = new Roof("крыша", 5, "yellow", true, true);

        hemuli.setName("Хемулиха");
        hemuli.setSize("Маленькая");

        moomins.setName("Мумми");

        poster.setName("Театральная афиша");

        play.premiere();

        hemuli.eat(hemuli, moomins);
        moomins.eat(food, moomins);

        poster.spinningStatus(poster);
        poster.plannedStatus(poster);
        poster.stickedStatus(poster, roof);
        roof.cleanedStatus(roof,poster);
        roof.tarredStatus(roof);
    }
}
