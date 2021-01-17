public class Main {

    public static void main(String[] args) throws NonStart {
        /**
         Main class
         */
            //creates new objects from different classes
            Play play = new Play("Пьеса");
            Food food = new Food();
            Hemuli hemuli = new Hemuli();
            Moomins moomins = new Moomins();
            Poster poster = new Poster();
            Roof roof = new Roof("крыша", 5, "yellow", true, true);
            //checking countKidsException, if the number of kids is less than 0
        try {
            Kids kids = new Kids("Малыши", 24, "Лесные");
            Snusmumriken snusmumriken = new Snusmumriken("Снусмурик");
            Poster.Remnants remnants = new Poster.Remnants();
            Stockings stockings = new Stockings("чулки", 39, false);
            Jacket jacket = new Jacket("пиджак", 50, false);
            Grass grass = new Grass(false, false);

            Piece piece = new Piece();
            //realization of anonymous class
            Piece piece1 = new Piece() {
                @Override
                public String combine() {
                    return super.getName();
                }
            };

            //set characteristics
            hemuli.setName("Хемулиха");
            hemuli.setSize("Маленькая");
            moomins.setName("Мумми");
            poster.setName("Театральная афиша");
            poster.setMaterial("тонкая бумага");
            piece.setName("клочка");

            kids.checkCount();
            play.startPremiere();

            try {
                if (!play.getPremiere() ) {
                    throw new NonStart("Ошибка! Пьеса не началась!");
                }

            hemuli.eat(moomins);
            moomins.eat(food);

            poster.spinningStatus();
            poster.plannedStatus();
            poster.stickedStatus(roof);
            roof.cleanedStatus(poster);
            roof.tarredStatus();

            kids.climb(kids, roof);
            kids.pick(kids, poster);
            kids.handOver(kids, poster, snusmumriken);
            poster.getMaterial();
            poster.transformation(kids, piece);
            remnants.dropped();
            remnants.burned();

            kids.replic1();
            kids.goDown(roof);

            snusmumriken.grumble1();
            snusmumriken.wash(stockings, kids);

            kids.pull(jacket, snusmumriken);
            snusmumriken.grumble2();
            snusmumriken.spread(grass, poster, piece);
            snusmumriken.combine(piece);
            }

            catch (NonStart nonStart){
                System.out.println(nonStart.getMessage());
                System.out.println("Представления не будет(");
            }

        }
        catch (CountKidsException countKidsException){
            System.out.println(countKidsException.getMessage());
            System.out.println("Число малышей изменено на 0! А без малышей и пьесы нет(");
        }
    }
}
