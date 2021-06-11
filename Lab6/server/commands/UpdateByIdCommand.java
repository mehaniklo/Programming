package server.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import server.data.SpaceMarine;

import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.HashSet;

public class UpdateByIdCommand extends AbstractCommand {


    public UpdateByIdCommand(AllCommands allCommands) {
        super(allCommands);
        setDescription("Updates an element finding it by it`s ID.");
    }


    public String execute(String arg) {
        int k = 0;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        SpaceMarine newSpaceMarine = gson.fromJson(arg, SpaceMarine.class);
        String id = String.valueOf(newSpaceMarine.getId());
        HashSet<SpaceMarine> spaceMarineHashSet = getAllCommands().getSpaceMarineHashSet();

        for (SpaceMarine spaceMarine : spaceMarineHashSet) {
            long longId = spaceMarine.getId();
            String stringId = String.valueOf(longId);
            if (stringId.equals(id)) {
                spaceMarineHashSet.remove(spaceMarine);
                getAllCommands().getSpaceMarineHashSet().add(newSpaceMarine);
                getAllCommands().save();
                return "Element was added successfully.";
            }
        }
        return "Element was not added, because it`s id is not defined in the collection";
    }
}