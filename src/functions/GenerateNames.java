package functions;

import java.util.Random;

public class GenerateNames {

    public static String generateRandomName() {
        String[] firstNames = {"John", "Jane", "Emily", "Michael", "Sarah", "William", "Nancy", "Robert", "Linda", "James", "Tomas", "Julius", "Deivydas", "Antanas", "Girmantas",
        		"Džiuliana", "Eivydas", "Ignas", "Timas", "Romas", "Tomm", "Johnny", "Lukas", "Nojus"};

        String[] lastNames = {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor","Taylozzzz", "Tssssr", "Anthonyyyy",
        		"Taylorrrr", "Taylorad", "Tayloraizor", "Hola", "Yay","Sup", "Num", "Gud","Mingar", "Lomant"};

        Random rand = new Random();

        int randomFirstNameIndex = rand.nextInt(firstNames.length);
        int randomLastNameIndex = rand.nextInt(lastNames.length);

        String randomName = firstNames[randomFirstNameIndex] + " " + lastNames[randomLastNameIndex];

        return randomName;
    }
}
