package Data;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public enum AstartesCategory implements Serializable {
    SCOUT,
    AGGRESSOR,
    ASSAULT,
    TACTICAL,
    LIBRARIAN;

    static AstartesCategory fillAstartesCategory(Scanner scanner) {
        while (true) {
            System.out.println("Введите одно из значений: 1 - SCOUT, 2 - AGGRESSOR, 3 - ASSAULT, 4 - TACTICAL, 5 - LIBRARIAN");
            String astartesCategory = scanner.nextLine().trim();
            if (astartesCategory.equalsIgnoreCase("1")) {
                return SCOUT;
            } else if (astartesCategory.equalsIgnoreCase("2")) {
                return AGGRESSOR;
            } else if (astartesCategory.equalsIgnoreCase("3")) {
                return ASSAULT;
            } else if (astartesCategory.equalsIgnoreCase("4")) {
                return TACTICAL;
            } else if (astartesCategory.equalsIgnoreCase("5")) {
                return LIBRARIAN;
            } else {
                System.err.println("Вы ввели несуществующее значение.");
            }

        }
    }
    static AstartesCategory fillAstartesCategoryFromFile(Scanner scanner) throws IOException {
        String astartesCategory = scanner.nextLine().trim();
        if (astartesCategory.equalsIgnoreCase("SCOUT")) {
            return SCOUT;
        } else if (astartesCategory.equalsIgnoreCase("AGGRESSOR")) {
            return AGGRESSOR;
        } else if (astartesCategory.equalsIgnoreCase("ASSAULT")) {
            return ASSAULT;
        } else if (astartesCategory.equalsIgnoreCase("TACTICAL")) {
            return TACTICAL;
        } else if (astartesCategory.equalsIgnoreCase("LIBRARIAN")) {
            return LIBRARIAN;
        } else {
            throw new IOException("Вы ввели неверные данные.");
        }
    }


}
