package server.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import server.data.SpaceMarine;

import java.util.HashSet;

public class AddIfMaxCommand extends AbstractCommand {
    public AddIfMaxCommand(AllCommands allCommands) {
        super(allCommands);
        setDescription("Adds an element to collection if it`s height less then min height in this collection");
    }


    public synchronized String execute(String arg) {
        HashSet<SpaceMarine> spaceMarineHashSet = getAllCommands().getSpaceMarineHashSet();
        long minimalHealth = Long.MIN_VALUE;
        for (SpaceMarine spaceMarine : spaceMarineHashSet) {
            if (spaceMarine.getHealth() > minimalHealth) {
                minimalHealth = spaceMarine.getHealth();
            }
        }
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        SpaceMarine spaceMarine1 = gson.fromJson(arg, SpaceMarine.class);
        if (spaceMarine1.getHealth() > minimalHealth) {
            getAllCommands().getSpaceMarineHashSet().add(spaceMarine1);
            getAllCommands().save();
            return "Element was added successfully.";
        } else {
            getAllCommands().save();
            return "Element wan not added to collection because its height  " +
                    "lower than lower element`s height in the collection.";
        }
    }
}
