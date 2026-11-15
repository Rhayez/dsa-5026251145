package lw05.prelab;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    static int jumlahGedung;
    static String[] namaGedung;
    static int[][] graph;

    // Menambahkan jalur dua arah antara dua gedung
    static void addEdge(int gedung1, int gedung2, int waktu) {
        graph[gedung1][gedung2] = waktu;
        graph[gedung2][gedung1] = waktu;
    }

    // Menampilkan urutan gedung yang dikunjungi
    static void printPath(ArrayList<Integer> jalur) {
        for (int i = 0; i < jalur.size(); i++) {
            System.out.print(namaGedung[jalur.get(i)]);

            if (i < jalur.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    // Breadth-First Search: mengunjungi gedung berdasarkan tingkat kedekatan
    static ArrayList<Integer> bfs(int mulai) {
        ArrayList<Integer> urutan = new ArrayList<>();
        boolean[] dikunjungi = new boolean[jumlahGedung];

        Queue<Integer> antrean = new LinkedList<>();

        dikunjungi[mulai] = true;
        antrean.add(mulai);

        while (!antrean.isEmpty()) {
            int sekarang = antrean.poll();
            urutan.add(sekarang);

            // Memeriksa gedung tetangga dari nomor terkecil
            for (int i = 0; i < jumlahGedung; i++) {
                if (graph[sekarang][i] != 0 && !dikunjungi[i]) {
                    dikunjungi[i] = true;
                    antrean.add(i);
                }
            }
        }

        return urutan;
    }

    // Depth-First Search: mengunjungi gedung sedalam mungkin
    static ArrayList<Integer> dfsOrder = new ArrayList<>();

    static void dfs(int sekarang, boolean[] dikunjungi) {
        dikunjungi[sekarang] = true;
        dfsOrder.add(sekarang);

        // Memeriksa seluruh gedung tetangga
        for (int i = 0; i < jumlahGedung; i++) {
            if (graph[sekarang][i] != 0 && !dikunjungi[i]) {
                dfs(i, dikunjungi);
            }
        }
    }

    // Mencari gedung terdekat yang belum dikunjungi
    static int findTheNextNode(int[] jarak, boolean[] dikunjungi) {
        int jarakMinimum = Integer.MAX_VALUE;
        int gedungBerikutnya = -1;

        for (int i = 0; i < jumlahGedung; i++) {
            if (!dikunjungi[i] && jarak[i] < jarakMinimum) {
                jarakMinimum = jarak[i];
                gedungBerikutnya = i;
            }
        }

        return gedungBerikutnya;
    }

    // Menyusun rute terpendek menggunakan array prev
    static void route(int sekarang, int[] prev,
                      ArrayList<Integer> jalur) {

        if (sekarang == -1) {
            return;
        }

        // Menelusuri gedung sebelumnya secara rekursif
        route(prev[sekarang], prev, jalur);

        jalur.add(sekarang);
    }

    // Algoritma Dijkstra untuk mencari waktu perjalanan terpendek
    static void dijkstraAlg(int mulai, int tujuan) {

        int[] jarak = new int[jumlahGedung];
        int[] prev = new int[jumlahGedung];
        boolean[] dikunjungi = new boolean[jumlahGedung];

        // Menginisialisasi semua jarak dengan nilai tak terhingga
        Arrays.fill(jarak, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);

        // Jarak dari gedung awal ke dirinya sendiri adalah 0
        jarak[mulai] = 0;

        for (int i = 0; i < jumlahGedung; i++) {

            // Memilih gedung dengan jarak terkecil
            int sekarang = findTheNextNode(jarak, dikunjungi);

            if (sekarang == -1) {
                break;
            }

            dikunjungi[sekarang] = true;

            // Memperbarui jarak ke gedung tetangga
            for (int j = 0; j < jumlahGedung; j++) {

                if (graph[sekarang][j] != 0 &&
                    !dikunjungi[j] &&
                    jarak[sekarang] != Integer.MAX_VALUE) {

                    int jarakBaru = jarak[sekarang] + graph[sekarang][j];

                    // Mengganti jarak jika ditemukan rute yang lebih pendek
                    if (jarakBaru < jarak[j]) {
                        jarak[j] = jarakBaru;
                        prev[j] = sekarang;
                    }
                }
            }
        }

        // Menampilkan waktu terpendek menuju setiap gedung
        System.out.println("=== Shortest Time from "
                + namaGedung[mulai] + " ===");

        for (int i = 0; i < jumlahGedung; i++) {
            System.out.println(namaGedung[i] + " : "
                    + jarak[i] + " min");
        }

        // Menampilkan rute terpendek menuju gedung tujuan
        System.out.println("=== Shortest Route from "
                + namaGedung[mulai] + " to "
                + namaGedung[tujuan] + " ===");

        ArrayList<Integer> jalur = new ArrayList<>();

        route(tujuan, prev, jalur);

        printPath(jalur);

        System.out.println("Total time: "
                + jarak[tujuan] + " min");
    }

    // Method utama program
    public static void main(String[] args) throws Exception {

        // Membaca data dari file campus.txt
        Scanner sc = new Scanner(new File("D:\\dsa-5026251145\\src\\lw05\\prelab\\campus.txt"));

        // Membaca jumlah gedung
        jumlahGedung = sc.nextInt();

        // Membaca nama setiap gedung
        namaGedung = new String[jumlahGedung];

        for (int i = 0; i < jumlahGedung; i++) {
            namaGedung[i] = sc.next();
        }

        // Membuat adjacency matrix
        graph = new int[jumlahGedung][jumlahGedung];

        // Membaca jumlah jalur antar gedung
        int jumlahJalur = sc.nextInt();

        // Membaca setiap jalur dan waktu perjalanan
        for (int i = 0; i < jumlahJalur; i++) {
            int gedung1 = sc.nextInt();
            int gedung2 = sc.nextInt();
            int waktu = sc.nextInt();

            addEdge(gedung1, gedung2, waktu);
        }

        // Membaca gedung awal dan gedung tujuan
        int mulai = sc.nextInt();
        int tujuan = sc.nextInt();

        sc.close();

        // Menjalankan Breadth-First Search
        System.out.println("=== BFS from "
                + namaGedung[mulai] + " ===");

        printPath(bfs(mulai));

        // Menjalankan Depth-First Search
        System.out.println("=== DFS from "
                + namaGedung[mulai] + " ===");

        dfsOrder.clear();

        boolean[] dikunjungi = new boolean[jumlahGedung];

        dfs(mulai, dikunjungi);

        printPath(dfsOrder);

        // Menjalankan algoritma Dijkstra
        dijkstraAlg(mulai, tujuan);
    }
}
