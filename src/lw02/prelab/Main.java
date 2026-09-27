package lw02.prelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;


public class Main {

    public static void main(String[] args) {

        LinkedList<String[]> transactions = new LinkedList<>();
        LinkedList<String[]> customers = new LinkedList<>();

        // Membaca file transaksi menggunakan scanner

        try {
            Scanner scanner = new Scanner(new File("src/lw02/prelab/transactions.txt"));

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();
                
                String[] data = line.split(" ");

                boolean found = false;

                for (String[] customer : customers) {

                    if (customer[0].equals(data[0])) {
                        found = true;
                        break;
                    }
                }

                if (!found) {

                    customers.add(new String[] {
                        data[0],
                        "0"
                    });
                }

                transactions.add(data);
            }

            scanner.close();

            // Membuat Queue (Antrian)

            Queue<String[]> queue = new LinkedList<>();

            // Memindahkan transaksi dari LinkedList ke Queue

            while (!transactions.isEmpty()) {

                queue.offer(transactions.removeFirst());

            }

            // Menyimpan withdrawal yang gagal

            Stack<String[]> failedTransactions = new Stack<>();

            // Memproses Queue

            while (!queue.isEmpty()) {

                String[] transaction = queue.poll();

                for (String[] customer : customers) {

                    if (customer[0].equals(transaction[0])) {

                        int balance = Integer.parseInt(customer[1]);
                        int amount = Integer.parseInt(transaction[2]);

                        // Deposit

                        if (transaction[1].equals("DEPOSIT")) {

                            balance += amount;

                        // Withdraw

                        } else if (transaction[1].equals("WITHDRAW")) {

                            if (amount > balance) {

                                failedTransactions.push(transaction);

                            }
                            else {
                                balance -= amount;
                            }

                        }

                        // Update saldo

                        customer[1] = String.valueOf(balance);

                        break;

                    }

                }

            }

            // Menampilkan saldo akhir

            System.out.println("=== Final Balances ===");

            for (String[] customer : customers) {

                System.out.println(customer[0] + " : " + customer[1]);
            }

            // Memberikan spasi antara saldo akhir dan transaksi yang gagal

            System.out.println(" ");

            // Menampilkan transaksi yang gagal

            System.out.println("=== Failed Transaction ===");

            while (!failedTransactions.isEmpty()) {

                String[] transaction = failedTransactions.pop();

                System.out.println(transaction[0] + " " + transaction[1] + " " + transaction[2]);
            }


        } catch (FileNotFoundException e) {
            System.out.println("transactions.txt not found.");
        }
            

    }

}