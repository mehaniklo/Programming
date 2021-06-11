package server.commands;

public class InfoCommand extends AbstractCommand{

    public InfoCommand(AllCommands allCommands) {
        super(allCommands);
        setDescription("Prints information about the collection.");
    }

    @Override
    public synchronized String execute() {
        return getAllCommands().info();
    }
}
