package monopoly;

import java.util.Scanner;

/**
 * Created by fjricci on 4/7/2015.
 */
public class Input {
    public static boolean inputBool(Scanner scanner){
        while(true){
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("Yes"))
                return true;
            else if (input.equalsIgnoreCase("No"))
                return false;
        }
    }
}
