package Folder.commands;

import Folder.collection.Ticket;
import Folder.exceptions.IdNotFoundException;

import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Command class that remove tickets from the collection greater than given one
 */
public class RemoveGreaterCommand extends CommandWithAdditionalArgument{
    /**ticket name*/
    private int id;

    /**
     * Remove tickets from the collection greater than given one
     */
    @Override
    public String execute(String userName) {
        Ticket ticket;
        try {
            ticket = getCollection().stream().filter(t -> t.getId() == id && t.getOwner().equals(userName)).collect(Collectors.toList()).get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new IdNotFoundException();
        }
        Ticket finalTicket = ticket;
        if (getCollection().removeIf(i -> i.getPrice() > finalTicket.getPrice() && i.getOwner().equals(userName))) {
            setCollectionChanged(true);
            return "How many items were deleted";
        }
        return "Unfortunately, nothing could be deleted";
    }

    /**
     * Getting ticket name
     * @param obj - ticket name
     */
    @Override
    public void addArgument(String obj) {
        id = Integer.parseInt(obj);
    }

    /**
     * @return info about command
     */
    @Override
    public String toString() {
        return "remove_greater <ticket id> : remove all items from the collection that exceed the specified limit";
    }
}
