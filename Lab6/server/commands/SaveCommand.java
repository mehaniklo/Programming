package server.commands;

public class SaveCommand extends AbstractCommand {

    public SaveCommand(AllCommands allCommands) {
        super(allCommands);
        setDescription("Does nothing. Saving is an automatic process");
    }


    @Override
    public synchronized String execute() {
        getAllCommands().save();
        return "the file is already saved";
    }
}
