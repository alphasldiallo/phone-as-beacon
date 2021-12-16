package loniya.org.bleadvertising;

import java.util.Random;

public class Util {


    //Namespace are generated based on the ASCII table
    public static String generateNamespace(){

        int leftLimit = 48; //Lower bound = a
        int rightLimit = 102; // Upper bound = f . Namespaces of Eddystone use HEX format
        int targetStringLength = 20; // Limit of the generated string is set to 20 characted
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 97)) // Here, we exclude uppercase and extra characters
                .limit(targetStringLength) // And we set the limit at 20
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;

    }
}
