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
    }

    //fungsi untuk mencari teman antar 2 mahasiswa
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
        JaringanPertemanan jp = new JaringanPertemanan();

        jp.tambahMahasiswa("A", Arrays.asList("B", "C"));
        jp.tambahMahasiswa("D", Arrays.asList("B", "C"));
        jp.tambahPertemanan("A", "D");

        jp.tampilkanTeman("A");
        jp.tampilkanTeman("D");

        Set<String> temanBersama = jp.cariTemanBersama("A", "D");
        System.out.println("Teman bersama A dan D: " + temanBersama);

        jp.hapusMahasiswa("C");
        jp.tampilkanTeman("A");
        jp.tampilkanTeman("D");
    }
}
