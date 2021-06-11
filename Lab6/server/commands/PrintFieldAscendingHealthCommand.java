package server.commands;

import server.data.SpaceMarine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class PrintFieldAscendingHealthCommand extends AbstractCommand{

    public PrintFieldAscendingHealthCommand(AllCommands allCommands) {
        super(allCommands);
        setDescription("output the values of the health field of all elements in ascending order");
    }

    public synchronized String execute() {
        HashSet<SpaceMarine> spaceMarineHashSet = getAllCommands().getSpaceMarineHashSet();
        ArrayList<Integer> spaceMarineArrayList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (SpaceMarine spaceMarine : spaceMarineHashSet) {
            spaceMarineArrayList.add(spaceMarine.getHealth());
        }
        Collections.sort(spaceMarineArrayList);
        System.out.println("Elements in this collection were sorted in ascending order");
        for (Integer integer : spaceMarineArrayList) {
            stringBuilder.append(integer + "\n");
        }
        return stringBuilder.toString();
    }
}
