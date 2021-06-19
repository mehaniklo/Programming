package Folder.commands;

import Folder.collection.Ticket;

import java.util.Date;
import java.util.LinkedList;

/**
 * Command class which outputs info about collection
 */
public class InfoCommand extends CommandWithoutAdditionalArgument {


    /**
     * Output info about collection (type, count of elements, creation time)
     */
    public String execute() {
        String result;
        result = "Collection Type: " + getCollection().getClass() + "\n";
        result += "Number of elements: " + getCollection().size() + "\n";
        if (getCollection().size() != 0 ) {
            Date date = new Date();
            for (Ticket t : getCollection()) {
                if (t.getDateOfCreation().getTime() < date.getTime()) date = t.getDateOfCreation();
            }
            result += "Creation time: " + date;
        }
        return result;
    }

    /**
     * @return info about command
     */
    @Override
    public String toString() {
        return "info : send information about the collection to the standard output stream.";
    }
}
