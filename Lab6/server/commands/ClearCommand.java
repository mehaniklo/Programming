package server.commands;

public class ClearCommand extends AbstractCommand{
    public ClearCommand(AllCommands allCommands) {
        super(allCommands);
        setDescription("Removes all elements of the collection.");
    }

    /**
     * Method for executing this command
     *
     * @return executing status into a string representation
     */
    @Override
    public synchronized String execute() {
        getAllCommands().clear();
        getAllCommands().save();
        return "Collection is cleaned.";
    }

}
