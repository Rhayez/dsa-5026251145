package lw04.unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    // Bubble Sort - Urutan Menurun (Descending)
    public static void sortCards(int[] arr, int[] afterTwoPasses) {

        int n = arr.length;

        // Melakukan sorting sebanyak N-1 pass
        for (int i = 0; i < n - 1; i++) {

            // Membandingkan elemen yang bersebelahan
            for (int j = 0; j < n - 1 - i; j++) {

                if (arr[j] < arr[j + 1]) {

                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }

            // Menyimpan array setelah pass kedua
            if (i == 1) {

                for (int k = 0; k < n; k++) {
                    afterTwoPasses[k] = arr[k];
                }
            }
        }
    }

    // Binary Search - Urutan Menurun (Descending)
    public static int[] findParcel(int[] arr, int target) {

        int low = 0;
        int high = arr.length - 1;
        int steps = 0;

        while (low <= high) {

            int mid = (low + high) / 2;

            // Menghitung jumlah langkah pencarian
            steps++;

            if (arr[mid] == target) {

                return new int[] {mid, steps};

            } else if (target < arr[mid]) {

                // Mencari di bagian kanan
                low = mid + 1;

            } else {

                // Mencari di bagian kiri
                high = mid - 1;
            }
        }

        return new int[] {-1, steps};
    }

    public static void main(String[] args) {

        try {

            // Membaca input dari parcels.txt
            Scanner input = new Scanner(new File("D:\\dsa-5026251145\\src\\lw04\\unguided\\parcels.txt"));

            // Membaca jumlah parcel
            int N = input.nextInt();

            int[] parcels = new int[N];

            // Menyimpan berat setiap parcel
            for (int i = 0; i < N; i++) {
                parcels[i] = input.nextInt();
            }

            // Membaca jumlah target
            int M = input.nextInt();

            int[] targets = new int[M];

            // Menyimpan berat parcel yang dicari
            for (int i = 0; i < M; i++) {
                targets[i] = input.nextInt();
            }

            input.close();

            // Array untuk menyimpan hasil setelah 2 pass
            int[] afterTwoPasses = new int[N];

            // Melakukan sorting
            sortCards(parcels, afterTwoPasses);

            // Menampilkan array setelah 2 pass
            System.out.println("==== After 2 Passes ====");

            for (int i = 0; i < N; i++) {

                if (i > 0) {
                    System.out.print(" ");
                }

                System.out.print(afterTwoPasses[i]);
            }

            System.out.println();

            // Menampilkan array yang sudah terurut sepenuhnya
            System.out.println();
            System.out.println("==== Fully Sorted ====");

            for (int i = 0; i < N; i++) {

                if (i > 0) {
                    System.out.print(" ");
                }

                System.out.print(parcels[i]);
            }

            System.out.println();

            // Mencari setiap target
            System.out.println();
            System.out.println("==== Search Results ====");

            for (int i = 0; i < M; i++) {

                int[] result = findParcel(parcels, targets[i]);

                int index = result[0];
                int steps = result[1];

                if (index != -1) {

                    System.out.println(
                        targets[i] + ": FOUND at index " + index
                        + " (steps: " + steps + ")"
                    );

                } else {

                    System.out.println(
                        targets[i] + ": NOT FOUND (steps: " + steps + ")"
                    );
                }
            }

        } catch (FileNotFoundException e) {

            System.out.println("parcels.txt not found.");

        }
    }
}
