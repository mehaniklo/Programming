package server.commands;

import server.data.AstartesCategory;
import server.data.SpaceMarine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class PrintFieldDescendingCategoryCommand extends AbstractCommand{

    public PrintFieldDescendingCategoryCommand(AllCommands allCommands) {
        super(allCommands);
        setDescription("output the values of the category field of all elements in descending order");
    }

    public synchronized String execute() {
        HashSet<SpaceMarine> spaceMarineHashSet = getAllCommands().getSpaceMarineHashSet();
        StringBuilder stringBuilder = new StringBuilder();

        ArrayList<AstartesCategory> astartesCategoriesArrayList = new ArrayList<>();
        for (SpaceMarine spaceMarine : spaceMarineHashSet) {
            astartesCategoriesArrayList.add(spaceMarine.getCategory());
        }
        Collections.sort(astartesCategoriesArrayList);

        for (AstartesCategory ac : astartesCategoriesArrayList) {
            stringBuilder.append(ac + "\n");
        }
        return stringBuilder.toString();
    }

}
