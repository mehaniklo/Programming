package server.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import server.data.SpaceMarine;

import java.io.IOException;

public class AddCommand extends AbstractCommand{
    /**
     * Constructor for this class
     *
     * @param allCommands - field for using opportunities of Collection Manager
     */
    public AddCommand(AllCommands allCommands) {
        super(allCommands);
        setDescription("Adds new element to the collection.");
    }

    public synchronized String execute(String arg) {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        SpaceMarine spaceMarine = gson.fromJson(arg, SpaceMarine.class);

        getAllCommands().getSpaceMarineHashSet().add(spaceMarine);
        getAllCommands().save();

        return "Element was added successfully.";
    }
}
