package server.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import server.data.SpaceMarine;

import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.HashSet;

public class RemoveGreaterCommand extends AbstractCommand {
    public RemoveGreaterCommand(AllCommands allCommands) {
        super(allCommands);
        setDescription("Removes all elements which height more than current.");
    }


    public synchronized String execute(String arg) {
        HashSet<SpaceMarine> spaceMarineHashSet = getAllCommands().getSpaceMarineHashSet();
        int counter = 0;
        int argInt = Integer.parseInt (arg.trim ());
        try {
            for (SpaceMarine spaceMarine : spaceMarineHashSet) {
                if (spaceMarine.getHealth() > argInt) {
                    spaceMarineHashSet.remove(spaceMarine);
                    counter += 1;
                }
            }
            System.out.println("Operation completed. " + counter + " elements were removed");
        }
        catch (ConcurrentModificationException concurrentModificationException) {
        }
        return "Operation completed. " + counter + " elements were removed";

        /*int counter = 0;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        HashSet<SpaceMarine> spaceMarineHashSet = getAllCommands().getSpaceMarineHashSet();
        SpaceMarine

        if (spaceMarineHashSet.size() != 0) {
            int beginSize = spaceMarineHashSet.size();
            spaceMarineHashSet.removeIf(p -> (p != null && p.compareTo(spaceMarine) > 0));
            getAllCommands().save();
            return "Amount of elements which were removed: " + (beginSize - spaceMarineHashSet.size());
        } else return "Collection is empty. Comparing is impossible.";*/
    }
}
