package lw03.unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Set<String> mahasiswaterdaftar = new HashSet<>();

        Set<String> mahasiswacheckin = new HashSet<>();

        List<String> hasilcheckin = new ArrayList<>();

        int totalditolak = 0;


        // Membaca file registrations.txt
        Scanner scannerRegistrasi = new Scanner(new File("src/lw03/unguided/registrations.txt"));

        while (scannerRegistrasi.hasNext()) {

            String mahasiswaId = scannerRegistrasi.next();
            mahasiswaterdaftar.add(mahasiswaId);
        }

        scannerRegistrasi.close();


        // Membaca file checkins.txt
        Scanner scannerCheckin = new Scanner(new File("src/lw03/unguided/checkins.txt"));

        while (scannerCheckin.hasNext()) {

            String mahasiswaId = scannerCheckin.next();

            // Mengecek apakah mahasiswa terdaftar

            if (!mahasiswaterdaftar.contains(mahasiswaId)) {
                // Mahasiswa tidak terdaftar
                hasilcheckin.add(mahasiswaId + ": Rejected (Not registered");

                totalditolak++;
            }

            // Cek apakah sudah check-in
            else if (mahasiswacheckin.contains(mahasiswaId)) {
                // Mahasiswa sudah check-in sebelumnya
                hasilcheckin.add(mahasiswaId + ": Rejected (Already checked in)");

                totalditolak++;
            }

            // Check-in berhasil
            else {
                mahasiswacheckin.add(mahasiswaId);
                hasilcheckin.add(mahasiswaId + ": Checked in");
            }
        }

        scannerCheckin.close();

        //menampilkan hasil check-in
        System.out.println("===== Event Check-In Results =====");

        for (String hasil : hasilcheckin) {
            System.out.println(hasil);
        }

        // Menghitung jumlah mahasiswa absen

        int jumlahterdaftar = mahasiswaterdaftar.size();
        int checkinberhasil = mahasiswacheckin.size();
        int mahasiswaabsen = jumlahterdaftar - checkinberhasil;

        // Menampilkan Summary
        System.out.println("===== Final Event Summary =====");
        System.out.println("Registered students: " + jumlahterdaftar);
        System.out.println("Successful check-ins: " + checkinberhasil);
        System.out.println("Absent students: " + mahasiswaabsen);
        System.out.println("Rejected attempts: " + totalditolak);
    }
    
}
