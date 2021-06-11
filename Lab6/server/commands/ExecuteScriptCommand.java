package server.commands;

import server.data.SpaceMarine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ExecuteScriptCommand extends AbstractCommand{

    public ExecuteScriptCommand(AllCommands allCommands) {
        super(allCommands);
        setDescription("Executes script from a file.");
    }

    public String execute(String arg) {
        try {
            System.out.println("Please do not allow recursion");
            BufferedReader reader = new BufferedReader(new FileReader(arg));
            StringBuilder stringBuilder = new StringBuilder();
            String[] userCommand;
            String cmd;
            while((cmd = reader.readLine()) != null) {
                userCommand = cmd.trim().toLowerCase().split(" ", 2);
                switch (userCommand[0]) {
                    case "help":
                        HelpCommand help = new HelpCommand(getAllCommands());
                        stringBuilder.append(help.execute()).append("\n");
                        break;
                    case "info":
                        InfoCommand info = new InfoCommand(getAllCommands());
                        stringBuilder.append(info.execute()).append("\n");
                        break;
                    case "show":
                        ShowCommand show = new ShowCommand(getAllCommands());
                        stringBuilder.append(show.execute()).append("\n");
                        break;
                    case "add":
                        AddCommand add = new AddCommand(getAllCommands());
                        stringBuilder.append(add.execute(userCommand[1])).append("\n");
                        break;
                    case "update_by_id":
                        UpdateByIdCommand update_by_id = new UpdateByIdCommand(getAllCommands());
                        stringBuilder.append(update_by_id.execute(userCommand[1])).append("\n");
                        break;
                    case "remove_by_id":
                        RemoveByIdCommand remove_by_id = new RemoveByIdCommand(getAllCommands());
                        stringBuilder.append(remove_by_id.execute(userCommand[1])).append("\n");
                        break;
                    case "clear":
                        ClearCommand clear = new ClearCommand(getAllCommands());
                        stringBuilder.append(clear.execute()).append("\n");
                        break;
                    case "save":
                        SaveCommand save = new SaveCommand(getAllCommands());
                        stringBuilder.append(save.execute()).append("\n");
                        break;
                    case "execute_script":
                        stringBuilder.append("This script cannot to contain this command.").append("\n");
                        break;
                    case "exit":
                        ExitCommand exit = new ExitCommand(getAllCommands());
                        stringBuilder.append(exit.execute()).append("\n");
                        break;
                    case "add_if_min":
                        AddIfMinCommand addIfMinCommand = new AddIfMinCommand(getAllCommands());
                        stringBuilder.append(addIfMinCommand.execute(userCommand[1])).append("\n");
                        break;
                    case "add_if_max":
                        AddIfMaxCommand addIfMaxCommand = new AddIfMaxCommand(getAllCommands());
                        stringBuilder.append(addIfMaxCommand.execute(userCommand[1])).append("\n");
                        break;
                    case "remove_greater":
                        RemoveGreaterCommand removeGreaterCommand = new RemoveGreaterCommand(getAllCommands());
                        stringBuilder.append(removeGreaterCommand.execute(userCommand[1])).append("\n");
                        break;
//                    case "print_field_ascending_health":
//                        print_field_ascending_health();
//                        break;
//                    case "print_field_descending_category":
//                        print_field_descending_category();
//                        break;
                    case "filter_by_weapon_type":
                        FilterByWeaponTypeCommand filterByWeaponTypeCommand = new FilterByWeaponTypeCommand(getAllCommands());
                        stringBuilder.append(filterByWeaponTypeCommand.execute(userCommand[1])).append("\n");
                        break;
                    default:
                        reader.readLine();
                        break;
                }
            }
            reader.close();
            getAllCommands().save();
            return stringBuilder.toString();
        } catch (FileNotFoundException fileNotFoundException) {
            return "File not found, try again";
        } catch (IOException ioException) {
            return "File reading exception, try again";
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return "No argument entered, try again";
        }
    }
}
