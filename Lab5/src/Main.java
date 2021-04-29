import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        CollectionManager collectionManager = new CollectionManager();
        collectionManager.reading(args);
        Commander commander = new Commander(collectionManager);
        commander.interactiveMode();
    }
}