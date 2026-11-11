# Inventori Laboratorium — Praktikum 7

Nama: ISI_NAMA
NIM: ISI_NIM
Kelas: ISI_KELAS

## Materi
CREATE dan READ dengan JDBC, PreparedStatement, ResultSet, dan JTable.

## Lingkungan
NetBeans IDE 8.2, JDK 8, XAMPP, dan driver JDBC kelas dari pertemuan 6.

## Prasyarat
Database db_inventori_lab dan data kategori dari pertemuan 6 sudah tersedia.
Akun lab_p06 memiliki SELECT dan INSERT pada database tersebut.

## Cara menjalankan di NetBeans
1. Nyalakan MySQL pada XAMPP.
2. Buka proyek PrKoneksiInventori melalui File > Open Project.
3. Pastikan JAR driver di folder lib terpasang pada Libraries.
4. Salin config/database.example.properties menjadi config/database.properties.
5. Isi host, port, database, user, dan password lokal yang sesuai.
6. Jalankan koneksi/UjiKoneksi.java dengan Run File.
7. Setelah koneksi berhasil, jalankan tampilan/FormBarang.java dengan Run File.

## Fitur
- Menampilkan kategori dan daftar barang.
- Menambah barang baru dengan parameter SQL.
- Validasi input dan pesan kode duplikat.
- Memuat ulang daftar tanpa menggandakan baris.

## Bukti dan laporan
Screenshot: bukti/pertemuan-07/
Laporan: laporan/P07_NIM_Nama.pdf

## Catatan
database.properties berisi konfigurasi lokal dan tidak disertakan.
Database tidak otomatis dibuat oleh aplikasi; gunakan bahan pertemuan 6.
