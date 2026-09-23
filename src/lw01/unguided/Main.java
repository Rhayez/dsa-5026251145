package lw01.unguided;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        
        List<WashService> washes = new ArrayList<>();

        try (Scanner scanner = new Scanner(Main.class.getResourceAsStream("washes.txt"))) {
    
            while (scanner.hasNext()) {
                String type = scanner.next();
                String id = scanner.next();
                int days = scanner.nextInt();
                int units = scanner.nextInt();

                if (type.equals("Car")) {
                    washes.add(new CarWash(id, days));
                } else if (type.equals("Motorcycle")) {
                    washes.add(new MotorcycleWash(id, days));
                }
            }

            for (WashService wash : washes) {
                System.out.println(wash.summary());
            }

        } 
        
    }
}