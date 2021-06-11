package server.commands;

import server.data.SpaceMarine;

import java.util.HashSet;

public class ShowCommand extends AbstractCommand{

    public ShowCommand(AllCommands allCommands) {
        super(allCommands);
        setDescription("output all elements of the collection in a string representation to the standard output stream");
    }

    @Override
    public synchronized String execute() {
        HashSet<SpaceMarine> spaceMarineHashSet = getAllCommands().getSpaceMarineHashSet();
        StringBuilder result = new StringBuilder();
        for (SpaceMarine spaceMarine : spaceMarineHashSet) {
            result.append(spaceMarine.toString() + "\n");
        }
        if (spaceMarineHashSet.size() != 0) {
            return result.toString();
        } else return "Collection is empty.";
    }

}
