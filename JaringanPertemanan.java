import java.util.*;

public class JaringanPertemanan {
    private Map<String, Set<String>> graf;

    public JaringanPertemanan() {
        graf = new HashMap<>();
    }

    //fungsi untuk menambah teman mahasiswa
    public void tambahMahasiswa(String nama, List<String> daftarTeman) {
        graf.putIfAbsent(nama, new HashSet<>());

        for (String teman : daftarTeman) {
            graf.putIfAbsent(teman, new HashSet<>());
            graf.get(nama).add(teman);
            graf.get(teman).add(nama);
        }
    }

    //fungsi untuk menambah hubungan antar 2 mahasiswa
    public void tambahPertemanan(String mhs1, String mhs2) {
        if (!graf.containsKey(mhs1) || !graf.containsKey(mhs2)) {
            System.out.println("Salah satu mahasiswa tidak terdaftar.");
            return;
        }
        graf.get(mhs1).add(mhs2);
        graf.get(mhs2).add(mhs1);
        System.out.println("Mahasiswa berhasil ditambahkan");
    }

    //fungsi untuk menghapus mahasiswa serta semua hubungannya
    public void hapusMahasiswa(String nama) {
        if (!graf.containsKey(nama)) {
            System.out.println("Mahasiswa tidak ditemukan.");
            return;
        }
        for (String teman : graf.get(nama)) {
            graf.get(teman).remove(nama);
        }
        graf.remove(nama);
        System.out.println("Mahasiswa berhasil dihapus");
    }

    //fungsi untuk mencari hubungan teman antar 2 mahasiswa
    public Set<String> cariTemanBersama(String mhs1, String mhs2) {
        if (!graf.containsKey(mhs1) || !graf.containsKey(mhs2)) {
            System.out.println("Salah satu mahasiswa tidak ditemukan.");
            return Collections.emptySet();
        }

        Set<String> teman1 = graf.get(mhs1);
        Set<String> teman2 = graf.get(mhs2);

        Set<String> hasil = new HashSet<>(teman1);
        hasil.retainAll(teman2); // ambil irisan

        return hasil;
    }

    //fungsi untuk menampilkan teman antar 2 mahasiswa
    public void tampilkanTeman(String nama) {
        if (!graf.containsKey(nama)) {
            System.out.println("Mahasiswa tidak ditemukan.");
            return;
        }

        System.out.println("Teman-teman " + nama + ": " + graf.get(nama));
    }

    //main
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        JaringanPertemanan jp = new JaringanPertemanan();
        int pilihan;

        do {
            System.out.println("\n--- MENU JARINGAN PERTEMANAN ---");
            System.out.println("1. Tambah Mahasiswa & Teman");
            System.out.println("2. Tambah Pertemanan antara 2 Mahasiswa");
            System.out.println("3. Hapus Mahasiswa");
            System.out.println("4. Cari Teman Bersama");
            System.out.println("5. Tampilkan Daftar Teman");
            System.out.println("6. Keluar");
            System.out.print("Pilih menu (1-6): ");
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    System.out.print("Nama mahasiswa: ");
                    String nama = scanner.nextLine();

                    System.out.print("Masukkan daftar teman (pisahkan dengan koma): ");
                    String inputTeman = scanner.nextLine();
                    List<String> daftarTeman = new ArrayList<>();
                    if (!inputTeman.isEmpty()) {
                        daftarTeman = Arrays.asList(inputTeman.split(",\\s*"));
                    }

                    jp.tambahMahasiswa(nama, daftarTeman);
                    System.out.println("Mahasiswa dan pertemanannya ditambahkan.");
                    break;

                case 2:
                    System.out.print("Nama mahasiswa 1: ");
                    String mhs1 = scanner.nextLine();
                    System.out.print("Nama mahasiswa 2: ");
                    String mhs2 = scanner.nextLine();

                    jp.tambahPertemanan(mhs1, mhs2);
                    break;

                case 3:
                    System.out.print("Nama mahasiswa yang ingin dihapus: ");
                    String hapusNama = scanner.nextLine();
                    jp.hapusMahasiswa(hapusNama);
                    break;

                case 4:
                    System.out.print("Nama mahasiswa pertama: ");
                    String tm1 = scanner.nextLine();
                    System.out.print("Nama mahasiswa kedua: ");
                    String tm2 = scanner.nextLine();

                    Set<String> hasil = jp.cariTemanBersama(tm1, tm2);
                    System.out.println("Teman bersama: " + hasil);
                    break;

                case 5:
                    System.out.print("Nama mahasiswa: ");
                    String tampilNama = scanner.nextLine();
                    jp.tampilkanTeman(tampilNama);
                    break;

                case 6:
                    System.out.println("Program selesai.");
                    break;

                default:
                    System.out.println("Pilihan tidak valid.");
            }

        } while (pilihan != 6);

        scanner.close();
        }
    }