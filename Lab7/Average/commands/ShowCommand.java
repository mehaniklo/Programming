package Folder.commands;

import Folder.collection.Ticket;

import java.util.LinkedList;

/**
 * Command class which outputs elements in the collection
 */
public class ShowCommand extends CommandWithoutAdditionalArgument{


    @Override
    public String execute() {
        StringBuilder result = new StringBuilder();
        getCollection().forEach(t -> result.append(t).append("\n"));
        if (getCollection().size() != 0) return result.toString();
        return "Nothing to show";
    }

    @Override
    public String toString() {
        return "show : output all elements of the collection in a string representation to the standard output stream";
    }
}
