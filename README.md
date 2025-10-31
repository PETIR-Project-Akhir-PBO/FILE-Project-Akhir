# PETIR (Pelayanan Transportasi terintegrasi)

## 1. Deskripsi Program

Program PETIR merupakan sistem manajemen transportasi darat berbasis Java yang terhubung dengan database MySQL. Sistem ini dirancang untuk mengelola proses administrasi tiket, jadwal, rute, dan data transportasi secara terintegrasi antara admin dan penumpang.
Program ini memiliki dua peran utama yaitu Admin dan Penumpang. Admin dapat menambah, mengubah, dan menghapus data tiket, sedangkan penumpang dapat melihat, mencari, dan membeli tiket sesuai rute dan jadwal yang tersedia.
Seluruh komponen dalam sistem PETIR dibangun menggunakan pendekatan Object-Oriented Programming (OOP) secara menyeluruh untuk memastikan keamanan, efisiensi, serta kemudahan pengembangan di masa mendatang.

## 2. Fitur Program

### 1. Login dan Registrasi Akun
Pengguna dapat melakukan login sebagai admin atau penumpang, serta mendaftarkan akun baru ke dalam sistem.

### 2. Fitur Admin

Melihat seluruh daftar tiket

Menambahkan tiket baru dengan data asal, tujuan, jenis transportasi, harga, dan jadwal keberangkatan

Mengedit tiket yang telah ada

Menghapus tiket yang tidak digunakan

Melihat data pendukung (rute, jadwal, dan transportasi)

### 3. Fitur Penumpang

Melihat daftar tiket yang tersedia

Mencari tiket berdasarkan asal, tujuan, atau jenis transportasi

Membeli tiket secara langsung dari sistem

Melihat dan mengatur saldo akun

### 4. Koneksi Database Otomatis
Sistem terhubung dengan MySQL Database melalui kelas DatabaseConnection yang akan memastikan setiap transaksi berjalan aman dan konsisten.

### 5. Tampilan Data Terstruktur
Seluruh data tiket ditampilkan dalam format tabel dengan kolom utama: No, Asal - Tujuan, Transportasi, Harga, dan Jadwal.

## 3. Penerapan 5 pilar OOP

Program ini menerapkan 5 pilar utama Pemrograman Berorientasi Objek (OOP) sebagai berikut:

### 1. Encapsulation

Encapsulation diterapkan dengan menjadikan atribut-atribut pada Package Model di dalam Class Pengguna, penumpang, dan Admin bersifat private, kemudian diakses melalui getter dan setter.
Hal ini memastikan data tidak bisa diubah secara langsung dari luar class dan menjaga keamanan data objek tiket.

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
Kelas Pengguna bersifat abstract dan berfungsi sebagai kerangka umum untuk berbagai jenis pengguna.
Implementasi detail metode yang sesuai diberikan pada kelas Admin dan Penumpang agar setiap tipe pengguna memiliki perilaku yang berbeda.

``` bash
package Model;

public abstract class Pengguna {
    protected int id;
    protected String nama;
    protected String password;
    protected String noTelp;
```

### 4. Polymorphism
Program ini menggunakan konsep polimorfisme dalam pemanggilan metode yang sama melalui referensi bertipe Pengguna.
Objek yang digunakan dapat berupa Admin atau Penumpang, dan metode yang dijalankan akan menyesuaikan dengan tipe objek tersebut.

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
Interface Cetak digunakan untuk menyeragamkan metode printInfo() pada beberapa kelas seperti Tiket, Transportasi, dan Rute.
Dengan penerapan ini, setiap kelas wajib menampilkan informasi data secara terstandarisasi sesuai ketentuan interface.

``` bash
package Model;

public interface Cetak {
    void printInfo();
}
```

## 4. Struktur Folder / Package
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

Foto Struktur Package dari Neatbeans

``` bash
<img width="575" height="559" alt="image" src="https://github.com/user-attachments/assets/9dc30fea-889b-4cf3-a129-e1f436b0cbec" />
```
