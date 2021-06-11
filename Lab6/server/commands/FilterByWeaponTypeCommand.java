package server.commands;

import server.data.SpaceMarine;

import java.util.HashSet;
import java.util.Locale;

public class FilterByWeaponTypeCommand extends AbstractCommand{

    public FilterByWeaponTypeCommand(AllCommands allCommands) {
        super(allCommands);
        setDescription("output elements with the value of the weaponType field equal to the specified value");
    }

    @Override
    public synchronized String execute(String arg) {
        int counter = 0;
        HashSet<SpaceMarine> spaceMarineHashSet = getAllCommands().getSpaceMarineHashSet();
        arg = arg.toLowerCase();
        for (SpaceMarine spaceMarine : spaceMarineHashSet) {
            try {
                if (spaceMarine.getWeaponType().name().toLowerCase(Locale.ROOT).equals(arg)){
                    counter += 1;
                }
            }
            catch (NullPointerException nullPointerException){
                return "Invalid weapon entered";
            }
        }

        return counter + " elements are equal";
    }

}
