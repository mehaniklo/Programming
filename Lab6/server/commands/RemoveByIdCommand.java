package server.commands;

import server.data.SpaceMarine;

import java.util.ConcurrentModificationException;
import java.util.HashSet;

public class RemoveByIdCommand extends AbstractCommand {

    public RemoveByIdCommand(AllCommands allCommands) {
        super(allCommands);
        setDescription("Removes an element finding it by it`s ID.");
    }


    public String execute(String id) {
        int k = 0;
        try {
            HashSet<SpaceMarine> spaceMarineHashSet = getAllCommands().getSpaceMarineHashSet();
            for (SpaceMarine spaceMarine : spaceMarineHashSet) {
                long longId = spaceMarine.getId();
                String strId = String.valueOf(longId);
                if (strId.equals(id)) {
                    spaceMarineHashSet.remove(spaceMarine);
                    getAllCommands().save();
                    k++;
                    return "Element was removed successfully.";
                }
            }
            if (k == 0) {
                getAllCommands().save();
                return "Element with this ID is not exist.";
            }
        } catch (ConcurrentModificationException concurrentModificationException) {
        }
        return "Element with this ID is not exist.";
    }
}