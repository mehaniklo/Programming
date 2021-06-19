package Folder.commands;
import Folder.collection.Ticket;

import java.util.LinkedList;

/**
 * Command class that outputs sum of the ticket price
 */
public class SumOfPriceCommand extends CommandWithoutAdditionalArgument{


    /**
     * Output sum of the ticket price
     */
    @Override
    public String execute() {
        double k = getCollection().stream().mapToDouble(Ticket::getPrice).sum();
        return "Total ticket price: " + k;
    }

    /**
     * @return info about command
     */
    @Override
    public String toString() {
        return "sum_of_price : print the sum of the values of the price field for all";
    }
}
