package server.commands;

public abstract class AbstractCommand {

    /** Field for using opportunities of class Collection manager */
    private AllCommands allCommands;
    /** Field for getting short description for a command */
    private String description;

    public AbstractCommand(AllCommands allCommands) {
        this.allCommands = allCommands;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public synchronized String execute() {
        return "Argument is absent";
    }

    public synchronized String execute(String arg) {
        return "Wrong command";
    }

    public AllCommands getAllCommands() {
        return allCommands;
    }
}
