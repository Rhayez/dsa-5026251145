package lw04.prelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            // Membaca input dari file branches.txt
            File file = new File("D:\\dsa-5026251145\\src\\lw04\\prelab\\branches.txt");
            Scanner scanner = new Scanner(file);

            // Membaca jumlah cabang dan nilai stabilitasnya
            int n = scanner.nextInt();
            int[] branches = new int[n];
            for (int i = 0; i < n; i++) {
                branches[i] = scanner.nextInt();
            }

            // Membaca jumlah target dan nilai targetnya
            int m = scanner.nextInt();
            int[] targets = new int[m];
            for (int i = 0; i < m; i++) {
                targets[i] = scanner.nextInt();
            }
            scanner.close();

            // Mengurutkan menggunakan Insertion Sort (Descending) tepat 3 pass
            for (int i = 1; i <= 3; i++) {
                int key = branches[i];
                int j = i - 1;
                
                // Menggeser elemen yang lebih kecil dari key ke kanan
                while (j >= 0 && branches[j] < key) {
                    branches[j + 1] = branches[j];
                    j--;
                }
                branches[j + 1] = key;
            }

            // Menampilkan array setelah 3 pass
            for (int i = 0; i < n; i++) {
                System.out.print(branches[i] + (i == n - 1 ? "" : " "));
            }
            System.out.println();

            // Mencari target menggunakan Linear Search
            for (int t = 0; t < m; t++) {
                int target = targets[t];
                int foundIndex = -1;
                
                for (int k = 0; k < n; k++) {
                    if (branches[k] == target) {
                        foundIndex = k;
                        break;
                    }
                }
                
                if (foundIndex != -1) {
                    System.out.println(target + ": FOUND at index " + foundIndex);
                } else {
                    System.out.println(target + ": NOT FOUND");
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File branches.txt tidak ditemukan.");
        }
    }
}