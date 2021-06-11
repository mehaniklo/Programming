package server.commands;

public class ExitCommand extends AbstractCommand{

    public ExitCommand(AllCommands allCommands) {
        super(allCommands);
        setDescription("Switches off a program.");
    }

    @Override
    public synchronized String execute() {
        getAllCommands().save();
        return "Finishing a program";
    }
}
