package lw03.prelab;   

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        // Problem 1
        problem1();

        // Problem 2
        problem2();

        // Problem 3
        problem3();
    }

    // PROBLEM 1 - PLAYLIST
    public static void problem1() throws FileNotFoundException {

        System.out.println("===== Problem 1 =====");

        List<String> playlist = new ArrayList<>();

        Scanner scanner = new Scanner(new File("src/lw03/prelab/playlist.txt"));

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();

            if (line.startsWith("ADD ")) {

                String song = line.substring(4);
                playlist.add(song);

            } else if (line.startsWith("INSERT ")) {

                String[] parts = line.split(" ", 3);

                int index = Integer.parseInt(parts[1]);
                String song = parts[2];

                playlist.add(index, song);

            } else if (line.startsWith("REMOVE ")) {

                String song = line.substring(7);
                playlist.remove(song);
            }
        }

        scanner.close();

        System.out.println("Total songs: " + playlist.size());

        for (int i = 0; i < playlist.size(); i++) {

            System.out.println((i + 1) + ": " + playlist.get(i));
        }

        System.out.println();
    }


    // PROBLEM 2 - PARTICIPANTS
    public static void problem2() throws FileNotFoundException {

        System.out.println("===== Problem 2 =====");

        Set<String> participants = new LinkedHashSet<>();

        int duplicateRegistrations = 0;

        Scanner scanner = new Scanner(new File("src/lw03/prelab/participants.txt"));

        while (scanner.hasNextLine()) {

            String name = scanner.nextLine();

            if (!participants.add(name)) {

                duplicateRegistrations++;
            }
        }

        scanner.close();

        System.out.println("Unique participants: " + participants.size());

        int number = 1;

        for (String name : participants) {

            System.out.println(number + ". " + name);

            number++;
        }

        System.out.println(
                "Duplicate registrations: " + duplicateRegistrations
        );

        System.out.println();
    }


    // PROBLEM 3 - INVENTORY
    public static void problem3() throws FileNotFoundException {

        System.out.println("===== Problem 3 =====");

        Map<String, Integer> inventory = new LinkedHashMap<>();

        int failedSales = 0;

        Scanner scanner = new Scanner(new File("src/lw03/prelab/inventory.txt"));

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();

            String[] parts = line.split(" ");

            String type = parts[0];
            String product = parts[1];
            int quantity = Integer.parseInt(parts[2]);

            if (type.equals("ADD")) {

                if (!inventory.containsKey(product)) {

                    inventory.put(product, quantity);

                } else {

                    int currentStock = inventory.get(product);

                    inventory.put(
                            product,
                            currentStock + quantity
                    );
                }

            } else if (type.equals("SELL")) {

                if (inventory.containsKey(product)) {

                    int currentStock = inventory.get(product);

                    if (currentStock >= quantity) {

                        inventory.put(
                                product,
                                currentStock - quantity
                        );

                    } else {

                        failedSales++;
                    }

                } else {

                    failedSales++;
                }
            }
        }

        scanner.close();

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {

            System.out.println(
                    entry.getKey() + ": " + entry.getValue()
            );
        }

        System.out.println("Failed sales: " + failedSales);
    }
}
