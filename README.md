# ⚡ PETIR (Pelayanan Transportasi Terintegrasi) 🚉

<img width="1536" height="1024" alt="Anu" src="https://github.com/user-attachments/assets/fecf95f3-53f3-411b-807b-31348170ff13" />

## ⚙ Deskripsi Program

Program PETIR merupakan sistem manajemen transportasi darat berbasis Java yang terhubung dengan database MySQL. Sistem ini dirancang untuk mengelola proses administrasi tiket, jadwal, rute, dan data transportasi secara terintegrasi antara admin dan penumpang.
Program ini memiliki dua peran utama yaitu Admin dan Penumpang. Admin dapat menambah, mengubah, dan menghapus data tiket, sedangkan penumpang dapat melihat, mencari, dan membeli tiket sesuai rute dan jadwal yang tersedia.
Seluruh komponen dalam sistem PETIR dibangun menggunakan pendekatan Object-Oriented Programming (OOP) secara menyeluruh untuk memastikan keamanan, efisiensi, serta kemudahan pengembangan di masa mendatang.

## ✨ Flowchart & Usecase


``` bash
https://drive.google.com/file/d/1vuFVS61Yx1Rh_dnzgd2hc7dsrvdid3cK/view?usp=sharing
```

## ✨ Slide Decks
``` bash
https://bit.ly/3JpGg8R
```

## 🔍 Fitur Program

### 1. Login dan Registrasi Akun
Pengguna dapat melakukan login sebagai admin atau penumpang, serta mendaftarkan akun baru ke dalam sistem.

### 2. Fitur Admin

- Melihat seluruh daftar tiket.

- Menambahkan tiket baru dengan data asal, tujuan, jenis transportasi, harga, dan jadwal keberangkatan.

- Mengedit tiket yang telah ada.

- Menghapus tiket yang tidak digunakan.

- Melihat data pendukung (rute, jadwal, dan transportasi).

### 3. Fitur Penumpang

- Melihat daftar tiket yang tersedia.

- Mencari tiket berdasarkan asal, tujuan, atau jenis transportasi.

- Membeli tiket secara langsung dari sistem.

- Melihat dan mengatur saldo akun.

### 4. Koneksi Database Otomatis
Sistem terhubung dengan MySQL Database melalui kelas DatabaseConnection yang akan memastikan setiap transaksi berjalan aman dan konsisten.

### 5. Tampilan Data Terstruktur
Seluruh data tiket ditampilkan dalam format tabel dengan kolom utama yaitu No, Asal - Tujuan, Transportasi, Harga, dan Jadwal.

## 🌟 Penerapan 5 pilar OOP

Program ini menerapkan 5 pilar utama Pemrograman Berorientasi Objek (OOP) sebagai berikut:

### 1. Encapsulation

Encapsulation diterapkan dengan menjadikan atribut-atribut pada Package Model di dalam Class Pengguna, penumpang, dan Admin bersifat private, kemudian diakses melalui getter dan setter. Hal ini memastikan data tidak bisa diubah secara langsung dari luar class dan menjaga keamanan data objek tiket.

``` bash
package Model;

public abstract class Pengguna {
    protected int id;
    protected String nama;
    protected String password;
    protected String noTelp;

    public Pengguna(int id, String nama, String password, String noTelp) {
        this.id = id;
        this.nama = nama;
        this.password = password;
        this.noTelp = noTelp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public abstract String getRole();
    public abstract void printInfo();
}

```

### 2. Inheritance
Kelas Admin dan Penumpang merupakan subclass dari kelas abstract Pengguna.
Melalui pewarisan ini, kedua kelas tersebut mewarisi atribut dan metode umum seperti id, nama, password, dan noTelp.

``` bash
package Model;

public class Admin extends Pengguna {
    private String akses;

    public Admin(int id, String nama, String password, String noTelp, String akses) {
        super(id, nama, password, noTelp);
        this.akses = akses;
    }
```

``` bash
package Model;

public class Penumpang extends Pengguna {
    private int saldo;

    public Penumpang(int id, String nama, String password, String noTelp, int saldo) {
        super(id, nama, password, noTelp);
        this.saldo = saldo;
    }
```

### 3. Abstraction
Kelas Pengguna bersifat abstract dan berfungsi sebagai kerangka umum untuk berbagai jenis pengguna. Implementasi detail metode yang sesuai diberikan pada kelas Admin dan Penumpang agar setiap tipe pengguna memiliki perilaku yang berbeda.

``` bash
package Model;

public abstract class Pengguna {
    protected int id;
    protected String nama;
    protected String password;
    protected String noTelp;
```

### 4. Polymorphism
Program ini menggunakan konsep polimorfisme dalam pemanggilan metode yang sama melalui referensi bertipe Pengguna. Objek yang digunakan dapat berupa Admin atau Penumpang, dan metode yang dijalankan akan menyesuaikan dengan tipe objek tersebut.

``` bash
    @Override
    public String getRole() { return "Admin"; }

    @Override
    public void printInfo() {
        System.out.println("[Admin] " + nama + " | akses: " + akses);
    }
```

``` bash
    @Override
    public String getRole() { return "Penumpang"; }

    @Override
    public void printInfo() {
        System.out.println("[Penumpang] " + nama + " | Saldo: Rp" + saldo);
    }
```

### 5. Interface
Interface Cetak digunakan untuk menyeragamkan metode printInfo() pada beberapa kelas seperti Tiket, Transportasi, dan Rute. Dengan penerapan ini, setiap kelas wajib menampilkan informasi data secara terstandarisasi sesuai ketentuan interface.

``` bash
package Model;

public interface Cetak {
    void printInfo();
}
```

## 📂 Struktur Folder / Package
Dibawah ini adalah Struktur Folder dan Package

``` bash
src/
 ├── Main/
 │    └── Main.java
 ├── ConnectDB/
 │    └── DatabaseConnection.java
 ├── Model/
 │    ├── Cetak.java
 │    ├── Pengguna.java
 │    ├── Admin.java
 │    ├── Penumpang.java
 │    ├── Transportasi.java
 │    ├── Rute.java
 │    ├── Jadwal.java
 │    └── Tiket.java
 ├── DAO/
 │    ├── PenggunaDAO.java
 │    ├── AdminDAO.java
 │    ├── PenumpangDAO.java
 │    ├── TransportasiDAO.java
 │    ├── RuteDAO.java
 │    ├── JadwalDAO.java
 │    └── TiketDAO.java
 └── Service/
      └── Service.java
```

Struktur Package dari Neatbeans

<img width="575" height="559" alt="image" src="https://github.com/user-attachments/assets/9dc30fea-889b-4cf3-a129-e1f436b0cbec" />

## ✨Penerapan Nilai Tambah

### 1. Penerapan Pola Desain DAO (Data Access Object)

Program PETIR menerapkan pola desain DAO sebagai bentuk pemisahan logika bisnis dengan logika akses data.
DAO digunakan untuk mengatur semua interaksi antara program dengan database MariaDB/MySQL.
Dengan pendekatan ini, struktur kode menjadi lebih modular, mudah dipelihara, dan fleksibel apabila terjadi perubahan di sistem basis data.

Contoh penerapan DAO pada program PETIR adalah

``` bash
package DAO;

import java.sql.*;

public class AdminDAO {

    private final Connection conn;

    public AdminDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean isAdmin(int idPengguna) {
        String sql = "SELECT akses FROM admin WHERE id_pengguna=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPengguna);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String akses = rs.getString("akses");
                return akses != null && akses.equalsIgnoreCase("crud");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

```

### 2. Penerapan Arsitektur MVC (Model - View - Controller)

Struktur folder pada proyek PETIR disusun berdasarkan arsitektur MVC, di mana:

- Model → berisi representasi data dan logika entitas (misalnya Tiket, Rute, Transportasi, Pengguna, Penumpang, dll).

- View → direpresentasikan melalui tampilan teks berbasis terminal (CLI), yang menampilkan hasil pemrosesan kepada pengguna.

- Controller (Service) → menghubungkan antara Model dan View, memproses input dari pengguna, dan memanggil fungsi DAO untuk akses data.

Contoh diagram pada program/package MVC PETIR

``` bash
src/
├── ConnectDB/
│   └── DatabaseConnection.java     ← Koneksi ke database
│
├── DAO/                            ← Data Access Object
│   ├── TiketDAO.java
│   ├── RuteDAO.java
│   ├── TransportasiDAO.java
│   ├── PenggunaDAO.java
│   └── PenumpangDAO.java
│
├── Model/                          ← Model (Representasi Data)
│   ├── Tiket.java
│   ├── Rute.java
│   ├── Transportasi.java
│   ├── Pengguna.java
│   ├── Penumpang.java
│   └── Cetak.java
│
├── Service/                        ← Controller
│   └── Service.java
│
└── Main/
    └── Main.java                   ← Entry point program
```

## 💻 Antarmuka Program (GUI)

Program PETIR (Program Transportasi Terintegrasi) memiliki antarmuka grafis berbasis Java Swing yang dirancang dengan pendekatan clean design, menggunakan kombinasi warna hijau muda dan putih untuk menciptakan tampilan yang tenang, ringan, dan konsisten. Setiap halaman memiliki tata letak yang seragam, font yang mudah dibaca, serta tombol-tombol dengan fungsi yang jelas untuk memudahkan navigasi pengguna.

### 1. Halaman Login


> ![LoginPage](https://github.com/user-attachments/assets/965ad8c1-dd05-4373-a5b9-626123541dba)


Halaman login adalah tampilan pertama saat aplikasi dijalankan. Bagian kiri jendela menampilkan ikon bus berwarna putih dengan latar gradien hijau muda dan teks “Selamat Datang di Aplikasi PETIR”.
Bagian kanan berisi panel login dengan komponen sebagai berikut:

- Label Log In sebagai judul.

- Kolom Masukkan username...

- Kolom Masukkan password...

- Checkbox Tampilkan password untuk menampilkan atau menyembunyikan karakter password.

- Tombol Login untuk masuk ke dalam aplikasi.

- Tombol Daftar Akun Baru untuk membuka halaman pendaftaran pengguna baru.

- Layout halaman ini sederhana dan intuitif, memastikan pengguna dapat memahami fungsi setiap elemen tanpa kebingungan.

### 2. Halaman Daftar Akun Baru


> ![RegisterPage](https://github.com/user-attachments/assets/a60477a2-2ac7-4d31-a8b6-611e4ad1a1ef)


Halaman pendaftaran digunakan bagi pengguna baru yang ingin membuat akun di sistem PETIR. Antarmuka ini mempertahankan gaya visual yang sama dengan halaman login, memberikan konsistensi pengalaman pengguna.

Formulir pendaftaran terdiri dari tiga kolom input:

- Nama – digunakan sebagai username untuk login.

- Password – digunakan untuk keamanan akun.

- Nomor HP – berfungsi sebagai identitas unik untuk membedakan pengguna meskipun nama sama.

Di bagian bawah terdapat dua tombol:

- Batal untuk kembali ke halaman login tanpa menyimpan data.

- Buat Akun Baru untuk menyimpan data dan membuat akun jika semua input valid.

Validasi dilakukan secara langsung di sisi program.
Jika nomor HP sudah digunakan atau input tidak sesuai (misalnya terdapat spasi pada username atau password kosong), pengguna akan menerima pesan peringatan dengan penjelasan yang mudah dipahami.

### 3. Dashboard Admin


> ![AdminPage](https://github.com/user-attachments/assets/7c4ae524-7a14-4487-b0f3-d441efe174ed)

Setelah login sebagai Admin, pengguna diarahkan ke Dashboard Admin.
Bagian atas halaman menampilkan sapaan “Halo, [nama admin]”, serta tombol:

- Kembali untuk logout.

- Menambahkan Tiket untuk menambah data tiket baru.

- Edit Tiket untuk mengubah data tiket yang sudah ada.

- Hapus Tiket untuk menghapus data tiket tertentu.

Di bawah area kontrol terdapat dua fitur tambahan:

- Dropdown Pilih Transportasi untuk memfilter data tiket berdasarkan jenis transportasi (Bus, Kereta, Travel, dan sebagainya).

- Kolom Pencarian untuk mencari tiket berdasarkan nama rute, tanggal, atau waktu keberangkatan.

Tabel utama menampilkan data tiket secara terstruktur, dengan kolom:

- No (Nomor urut tiket)

- Tanggal keberangkatan

- Jam berangkat

- Jam tiba

- Nama rute (asal - tujuan)

- Nama transportasi dan jenis

- Harga tiket

Admin dapat melihat seluruh daftar tiket sekaligus melakukan operasi CRUD (Create, Read, Update, Delete) secara langsung dari tampilan ini.

### 4. Dashboard Pengguna

> ![UserPage](https://github.com/user-attachments/assets/31d82d44-7af8-46c6-bcb8-9765294b59ea)


Setelah login sebagai Penumpang, pengguna diarahkan ke Dashboard Pengguna.
Bagian atas halaman menampilkan:

- Sapaan “Halo, [nama pengguna]”

- Informasi Saldo pengguna dalam format rupiah

- Tombol Top Up untuk menambah saldo

- Tombol Kembali untuk logout atau kembali ke menu sebelumnya

Bagian utama layar berisi daftar tiket yang tersedia, ditampilkan dalam bentuk kartu tiket (card view).
Setiap kartu menampilkan informasi:

- Rute perjalanan (asal dan tujuan)

- Jam berangkat dan tiba

- Titik awal dan akhir

- Tanggal keberangkatan

- Jenis dan nama transportasi

- Harga tiket dalam format rupiah

Harga tiket ditampilkan dengan warna merah untuk menonjolkan aspek pembelian.
Pengguna juga dapat melakukan pencarian tiket melalui kolom Cari dan memfilter berdasarkan jenis transportasi melalui dropdown di bagian atas.

### 5. Desain Warna dan Navigasi

Antarmuka PETIR mengadopsi palet warna hijau pastel dan putih, yang memberikan kesan lembut dan profesional.
Warna utama hijau digunakan untuk tombol, panel utama, dan aksen teks, sedangkan latar belakang putih menjaga keterbacaan dan fokus pada data. Font yang digunakan berwarna hitam atau abu-abu tua dengan ukuran sedang, untuk kenyamanan membaca di layar laptop maupun monitor.

Navigasi antar halaman dilakukan melalui tombol-tombol yang jelas:

- Halaman login menuju halaman dashboard sesuai peran pengguna (admin atau penumpang).

- Tombol Kembali terdapat di setiap halaman utama untuk memastikan pengguna dapat kembali ke halaman sebelumnya dengan mudah.

- Transisi antar halaman dilakukan secara langsung tanpa jeda, mempercepat pengalaman pengguna.


## Nilai Tambah

### 1. Penerapan Pola Desain DAO (Data Access Object)

Program PETIR menerapkan pola desain DAO sebagai bentuk pemisahan logika bisnis dengan logika akses data.
DAO digunakan untuk mengatur semua interaksi antara program dengan database MariaDB/MySQL.
Dengan pendekatan ini, struktur kode menjadi lebih modular, mudah dipelihara, dan fleksibel apabila terjadi perubahan di sistem basis data.

Contoh penerapan DAO pada program PETIR adalah

``` bash
package DAO;

import java.sql.*;

public class AdminDAO {

    private final Connection conn;

    public AdminDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean isAdmin(int idPengguna) {
        String sql = "SELECT akses FROM admin WHERE id_pengguna=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPengguna);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String akses = rs.getString("akses");
                return akses != null && akses.equalsIgnoreCase("crud");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

```

### 2. Penerapan Arsitektur MVC (Model - View - Controller)

Struktur folder pada proyek PETIR disusun berdasarkan arsitektur MVC, di mana:

Model → berisi representasi data dan logika entitas (misalnya Tiket, Rute, Transportasi, Pengguna, Penumpang, dll).

View → direpresentasikan melalui tampilan teks berbasis terminal (CLI), yang menampilkan hasil pemrosesan kepada pengguna.

Controller (Service) → menghubungkan antara Model dan View, memproses input dari pengguna, dan memanggil fungsi DAO untuk akses data.

Contoh diagram pada program/package MVC PETIR

``` bash
src/
├── ConnectDB/
│   └── DatabaseConnection.java     ← Koneksi ke database
│
├── DAO/                            ← Data Access Object
│   ├── TiketDAO.java
│   ├── RuteDAO.java
│   ├── TransportasiDAO.java
│   ├── PenggunaDAO.java
│   └── PenumpangDAO.java
│
├── Model/                          ← Model (Representasi Data)
│   ├── Tiket.java
│   ├── Rute.java
│   ├── Transportasi.java
│   ├── Pengguna.java
│   ├── Penumpang.java
│   └── Cetak.java
│
├── Service/                        ← Controller
│   └── Service.java
│
└── Main/
    └── Main.java                   ← Entry point program

```
