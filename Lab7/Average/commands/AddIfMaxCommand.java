package Folder.commands;

import Server.CommandDecoder;
import Folder.collection.Ticket;

import java.util.LinkedList;

/**
 * Command class that adds element to the collection if it price bigger than other
 */

public class AddIfMaxCommand extends CommandWithAdditionalArgument{
    /**price of a new ticket*/
    private Double price;
    /**collection of tickets*/

    public Ticket ticket;


    /**
     * add element to the collection, if it more than other
     */
    @Override
    public String execute() {
        if (price > getCollection().getLast().getPrice()) getCollection().add(ticket);
        else return "A new item cannot be added due to the low price.";
        CommandDecoder.sort(getCollection());
        return "The new item was successfully added to the collection";
    }

    public boolean canNewTicketBeAdded() {
        return price > getCollection().getLast().getPrice();
    }

    /**
     * Getting price of ticket to compare with others {@link AddIfMaxCommand#price}
     * @param obj - additional argument
     */
    @Override
    public void addArgument(String obj) {
        price = Double.parseDouble(obj);
    }

    /**
     * @return info about command
     */
    @Override
    public String toString() {
        return "add_if_max <price> : add a new item to a collection if its price value exceeds the value of the largest item in that collection";
    }
}
