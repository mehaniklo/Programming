package server.commands;

import java.util.HashMap;
import java.util.Map;

public class HelpCommand extends AbstractCommand {

    public HelpCommand(AllCommands allCommands) {
        super(allCommands);
        setDescription("Displays help for available commands.");
    }

    @Override
    public synchronized String execute() {
        StringBuilder result = new StringBuilder();
        HashMap<String, String> commandsInfo = getAllCommands().getInfoCommands();
        for (Map.Entry<String, String> entry : commandsInfo.entrySet()) {
            result.append(entry.getKey() + entry.getValue() + "\n");
        }
        getAllCommands().save();
        return result.toString();
    }
}
