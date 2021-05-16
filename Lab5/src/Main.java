import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Commands commands = new Commands();
        commands.reading(args);
        OutputHandler outputHandler = new OutputHandler(commands);
        outputHandler.manualInput();
    }
}