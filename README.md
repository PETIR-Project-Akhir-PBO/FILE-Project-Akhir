# ⚡PETIR (PELAYANAN TRANSPORTASI TERINTEGRASI)🚆
PETIR adalah sebuah aplikasi untuk melayani penumpang atau pengguna transportasi umum di Indonesia dengan pembelian tiket yang sudah terintegrasi oleh sistem.

## ⚙️ Deskripsi Program
PETIR (Pelayanan Transportasi Terintegrasi) adalah aplikasi tiket terpadu untuk transportasi umum di Indonesia. Pengguna bisa merencanakan rute lintas moda dan membeli tiket dalam satu platform, sehingga perjalanan lebih cepat, mudah, dan terjangkau. PETIR mendukung SDG 11 (Sustainable Cities & Communities) dengan mengurangi ketergantungan kendaraan pribadi, kemacetan, dan emisi.

## Fitur Utama
Aplikasi PETIR (Pelayanan Transportasi Terintegrasi) memiliki fitur utama seperti login dan registrasi pengguna, serta dua peran yaitu admin dan penumpang. Admin dapat mengelola data tiket, rute, dan jadwal, sedangkan penumpang dapat melihat dan membeli tiket dengan mudah menggunakan saldo digital. Semua data tersimpan dalam database terintegrasi, sehingga layanan transportasi menjadi lebih efisien dan praktis.

## Penerapan 5 pilar OOP
### 🎀 Encapsulation
Pada class Pengguna, encapsulation diterapkan dengan menyembunyikan data di dalam class (menggunakan protected) dan menyediakan akses terkontrol lewat getter dan setter. Dengan begitu, data tidak bisa diubah sembarangan dari luar, setiap perubahan harus lewat method yang disediakan, sehingga keamanan dan konsistensi data tetap terjaga.

> <img width="876" height="786" alt="image" src="https://github.com/user-attachments/assets/02b3d57c-8533-4365-9229-f8f0a78f5d50" />

### 🎀 Abstraction
Class Pengguna disebut abstrak karena berfungsi sebagai kerangka dasar bagi class lain. Class ini tidak bisa dibuat objeknya langsung, tapi memberikan dasar seperti data dan struktur umum yang nantinya akan diwarisi dan dilengkapi oleh class turunannya.

> <img width="445" height="146" alt="image" src="https://github.com/user-attachments/assets/bb8f0ebf-5f3a-40f3-8900-1909ceb30bea" />


### 🎀 Inheritance
Pada dua gambar tersebut, konsep inheritance diterapkan karena class Admin mewarisi (extends) class Pengguna atau Person. Artinya, Admin otomatis mendapatkan semua data dan fungsi yang ada di class induk tanpa harus menulis ulang.

> <img width="605" height="155" alt="image" src="https://github.com/user-attachments/assets/07275657-36ee-44da-b9ec-f1442a42318b" />

> <img width="521" height="71" alt="image" src="https://github.com/user-attachments/assets/afeb84b5-be84-486f-95bc-def7d20618f3" />

### 🎀 Polymorphism
### 🎀 Interface
Interface Cetak digunakan sebagai template atau aturan dasar yang harus diikuti oleh kelas lain. Di dalamnya hanya ada deklarasi method tanpa isi, yaitu printInfo(). Kelas lain yang menggunakan interface ini, seperti Admin, Penumpang, dan Tiket, wajib membuat isi dari method tersebut

> <img width="371" height="129" alt="image" src="https://github.com/user-attachments/assets/39034578-551e-4c46-b22e-1f5c33ead246" />

## 📦 Packages

<img width="306" height="146" alt="image" src="https://github.com/user-attachments/assets/9baf0b3b-7c68-4f1b-95db-afe89ee8a2e5" />

###  ConnectDB
Package ini berfungsi untuk menghubungkan program dengan database. File DatabaseConnection.java digunakan supaya koneksi ke MySQL bisa dilakukan dengan mudah dan terpusat.

> <img width="347" height="58" alt="image" src="https://github.com/user-attachments/assets/000d03e7-c744-4bd9-82f6-265c867ad54a" />

###  DAO

Package ini digunakan untuk mengelola proses pengambilan dan penyimpanan data ke database.

> <img width="329" height="218" alt="image" src="https://github.com/user-attachments/assets/9fb99d29-061d-40b9-95bb-ab7dc3f08331" />
- AdminDAO.java
Mengelola data admin: tambah, ubah, hapus, cari admin serta validasi login admin.
- JadwalDAO.java
Mengatur data jadwal keberangkatan/kedatangan: simpan, update, hapus, ambil jadwal per rute/transportasi.
- PenggunaDAO.java
Akses umum untuk data pengguna (induk): cek akun, ambil data dasar pengguna.
- PenumpangDAO.java
Operasi khusus penumpang: buat akun, ubah profil, hapus akun, cari penumpang, dan atur saldo.
- RuteDAO.java
Kelola data rute: asal–tujuan, tambah/ubah/hapus rute, serta ambil daftar rute untuk kebutuhan tiket/jadwal.
- TiketDAO.java
Proses tiket: tampilkan tiket tersedia (join rute/jadwal/transportasi), simpan pembelian, update status, hapus/cari tiket.

- TransportasiDAO.java
Data moda transportasi: tambah/ubah/hapus jenis/nama transportasi dan ambil daftar transportasi yang aktif.


### 1.2. DATABASE

## 2. Implementasi Pemerograman Berorientasi Objek (PBO)

### 2.1. FLOWCHART dan USE CASE

#### 2.1.1. FLOWCHART

#### 2.1.2. USE CASE

### 2.2. PROGRAM UTAMA

### 2.3. GUI
