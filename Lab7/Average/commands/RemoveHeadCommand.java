package Folder.commands;

import Folder.collection.Ticket;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Command class that removes first element in the collection
 */
public class RemoveHeadCommand extends CommandWithoutAdditionalArgument{


    /**
     * Remove first element in the collection
     */
    @Override
    public String execute(String userName) {
        try {
            Ticket ticket = getCollection().getFirst();
            if (!ticket.getOwner().equals(userName)) throw new NoSuchElementException();
            String result = getCollection().getFirst().toString();
            getCollection().removeFirst();
            setCollectionChanged(true);
            return result;
        } catch (NoSuchElementException e) {return "The collection is empty or the first item doesn't belong to you";}
    }

    /**
     * @return info about command
     */
    @Override
    public String toString() {
        return "remove_head : print the first item in the collection and delete it";
    }
}
