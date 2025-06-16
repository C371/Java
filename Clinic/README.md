## Clinic Management System

Sistem managemen clinic berbasi Java ini memungkinkan pengguna untuk:

- Mengelola data pasien dan dokter
- Mengatur jadwal konsultasi/kunjungan dokter
- Menampilkan informasi dengan antarmuka menu berbasis teks
------

## Struktur Direktori Program
 - Main.java
     - MainPage.java
         - DoctorLogin.java
         - PatientRecordManagement.java
         - AppointmentManagement.java
         - AppointmentMap.java
 - PatientRecords.csv
 - DoctorRecords.csv
 - AppointmentRecords.csv
 - Schedule.csv
------

## Cara Penggunaan Program

1. **Persiapan**
    - Pastikan Anda telah menginstal Java Development Kit (JDK) 8.
    - Buka terminal atau command prompt, dan pindah ke direktori `Clinic/`.

2. **Kompilasi Semua File**
   ```bash
   javac *.java

3. **Jalankan Program**
   ```bash
   java Main
------

Fitur Utama:
1. **Login Dokter**
     - Dokter dapat login menggunakan username dan password yang telah ditentukan.
     - Mendukung proses login untuk dokter berdasarkan username dan password.
     - Menggunakan struktur data linked list untuk menyimpan data login dokter.

2. **Manajemen Data Pasien**
     - Menambahkan dan menampilkan daftar pasien.
     - Menggunakan linked list buatan sendiri.
     - Menyimpan data pasien ke file PatientRecords.csv.

3. **Penjadwalan Janji Temu**
     - Menjadwalkan janji temu antara pasien dan dokter.
     - Menggunakan struktur data Queue untuk mengelola antrean.
     - Menyimpan data dalam AppointmentRecords.csv.

4. **Pemetaan Janji Temu**
     - Menyediakan tampilan visual janji temu berdasarkan ID pasien.
     - Mengelola dan mencari janji temu dari data yang tersedia.

5. **Navigasi Menu**
     - Menu berbasis teks sebagai antarmuka utama pengguna.
     - Menghubungkan semua fitur di atas melalui Main.java.
