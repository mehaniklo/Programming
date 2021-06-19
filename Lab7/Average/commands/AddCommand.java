package Folder.commands;
import Folder.collection.Ticket;
import Server.CommandDecoder;

import java.util.LinkedList;

/**
 * Command class that adds the element to the collection
 */
public class AddCommand extends CommandWithoutAdditionalArgument {

    public Ticket ticket = new Ticket();

    /**
     * add element to the collection
     */
    @Override
    public String execute() {
        getCollection().add(ticket);
        CommandDecoder.sort(getCollection());
        return "The new item was successfully added to the collection";
    }

    /**
     * @return info about command
     */
    @Override
    public String toString() {
        return "add : add a new item to the collection";
    }
}
