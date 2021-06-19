package Folder.commands;

import Folder.collection.Ticket;
import Folder.exceptions.IdNotFoundException;

import java.util.LinkedList;

/**
 * Command class that updates the ticket with the given id
 */
public class UpdateCommand extends CommandWithAdditionalArgument{
    /**ticket id*/
    private int ID;

    public Ticket ticket;


    /**
     * Update the ticket with the given id
     */
    @Override
    public String execute() {
        getCollection().stream().filter(t -> t.getId() == ID).forEach(t -> ticket = t);
        if (ticket == null) throw new IdNotFoundException();
        return "The element with the specified id was successfully updated";
    }

    /**
     * Getiing ticket id {@link UpdateCommand#ID}
     * @param obj - ticket id
     */
    @Override
    public void addArgument(String obj) {
        ID = Integer.parseInt(obj);
    }

    /**
     * @return info about command
     */
    @Override
    public String toString() {
        return "update <id> : update the value of a collection element whose id is equal to the specified one";
    }
}
