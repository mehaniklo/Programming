package Data;


import java.io.IOException;
import java.util.Scanner;

/**
 * Перечесление оружия солдата
 */
public enum Weapon {
    BOLT_RIFLE(1),
    HEAVY_FLAMER(2),
    MULTI_MELTA(3),
    MISSILE_LAUNCHER(4);

    private int ind;

    Weapon(int ind) {
        this.ind=ind;
    }


    static Weapon fillWeapon(Scanner scanner){
        while(true){
            System.out.println("Введите одно из значений: 1 - BOLT_RIFLE, 2 - HEAVY_FLAMER, 3 - MULTI_MELTA, 4 - MISSILE_LAUNCHER");
            String weapon = scanner.nextLine().trim();
            if (weapon.equalsIgnoreCase("1")){
                return BOLT_RIFLE;
            } else if (weapon.equalsIgnoreCase("2")){
                return HEAVY_FLAMER;
            }else if (weapon.equalsIgnoreCase("3")){
                return MULTI_MELTA;
            }else if (weapon.equalsIgnoreCase("4")){
                return MISSILE_LAUNCHER;
            }else{
                System.err.println("Вы ввели несуществующее значение.");
            }

        }

    }
    static Weapon fillWeaponFromFile(Scanner scanner) throws IOException {
        while(true){
            String weapon = scanner.nextLine().trim();
            if (weapon.equalsIgnoreCase("BOLT_RIFLE")){
                return BOLT_RIFLE;
            } else if (weapon.equalsIgnoreCase("HEAVY_FLAMER")){
                return HEAVY_FLAMER;
            }else if (weapon.equalsIgnoreCase("MULTI_MELTA")){
                return MULTI_MELTA;
            }else if (weapon.equalsIgnoreCase("MISSILE_LAUNCHER")){
                return MISSILE_LAUNCHER;
            }else{
                throw new IOException("Вы ввели неверные данные.");
            }

        }

    }
    /**
     * Генерирует красивый вывод enum
     * @return Возвращает все элементы enum через запятую
     */
    public static String nameList() {
        String nameList = "";
        for (Weapon weaponType : values()) {
            nameList += weaponType.name() + ", ";
        }
        return nameList.substring(0, nameList.length()-2);
    }

    /**
     *
     * @return индекс оружия солдата
     */
    public int getInd() {
        return ind;
    }
}
