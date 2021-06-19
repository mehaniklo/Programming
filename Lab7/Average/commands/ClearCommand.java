package Folder.commands;

import Folder.collection.Ticket;

import java.util.LinkedList;

/**
 * Command class that clears the collection
 */
public class ClearCommand extends CommandWithoutAdditionalArgument{


    /**
     * Clear the collection
     */
    @Override
    public String execute(String userName) {
        if (getCollection().removeIf((t) -> t.getOwner().equals(userName))) {
            setCollectionChanged(true);
            return "the part of your collection that belongs to you has been deleted.";
        }
        return "There were no tickets belonging to you.";
}

    /**
     * @return info about command
     */
    @Override
    public String toString() {
        return "clear : clear the collection";
    }
}
