package Service;

import DAO.*;
import Model.*;
import ConnectDB.DatabaseConnection;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Service {
    private final Scanner sc = new Scanner(System.in);
    private final PenggunaDAO penggunaDAO = new PenggunaDAO();
    private final AdminDAO adminDAO = new AdminDAO();
    private final PenumpangDAO penumpangDAO = new PenumpangDAO();
    private final TiketDAO tiketDAO = new TiketDAO();
    private final TransportasiDAO transportasiDAO = new TransportasiDAO();
    private final RuteDAO ruteDAO = new RuteDAO();
    private final JadwalDAO jadwalDAO = new JadwalDAO();

    public Service() {
        ensurePenjualanTable();
    }

    public void start() {
        while (true) {
            System.out.println("\n=== SISTEM TRANSPORTASI DARAT ===");
            System.out.println("1. Login");
            System.out.println("2. Daftar");
            System.out.println("3. Keluar");
            System.out.print("Pilih: ");
            String pilih = sc.nextLine().trim();
            switch (pilih) {
                case "1" -> loginFlow();
                case "2" -> daftarFlow();
                case "3" -> { System.out.println("Keluar."); return; }
                default -> System.out.println("Input salah.");
            }
        }
    }

    private void loginFlow() {
        System.out.print("Nama: ");
        String nama = sc.nextLine().trim();
        System.out.print("Password: ");
        String pass = sc.nextLine().trim();
        Pengguna p = penggunaDAO.login(nama, pass);
        if (p == null) {
            System.out.println("Login gagal. Nama atau password salah.");
            return;
        }
        boolean isAdmin = adminDAO.isAdmin(p.getId());
        if (isAdmin) menuAdmin(new Admin(p.getId(), p.getNama(), p.getPassword(), p.getNoTelp(), "crud"));
        else {
            Penumpang pen = new Penumpang(p.getId(), p.getNama(), p.getPassword(), p.getNoTelp(), penumpangDAO.getSaldo(p.getId()));
            menuPenumpang(pen);
        }
    }

    private void daftarFlow() {
        System.out.print("Masukkan Nama: ");
        String nama = sc.nextLine().trim();
        if (nama.contains(" ")) { System.out.println("Username tidak boleh mengandung spasi."); return; }
        System.out.print("Masukkan Password: ");
        String pass = sc.nextLine().trim();
        System.out.print("Masukkan ulang Password: ");
        String pass2 = sc.nextLine().trim();
        if (!pass.equals(pass2)) { System.out.println("Password tidak cocok."); return; }
        System.out.print("Masukkan No Telp (angka): ");
        String no = sc.nextLine().trim();
        if (!no.matches("\\d+")) { System.out.println("No telp harus angka."); return; }
        int res = penggunaDAO.daftar(nama, pass, no);
        if (res == -2) System.out.println("Nomor HP ini sudah digunakan untuk login.");
        else if (res > 0) System.out.println("Pendaftaran berhasil. ID Pengguna: " + res);
        else System.out.println("Gagal mendaftar.");
    }

    private void menuAdmin(Admin admin) {
        while (true) {
            System.out.println("\n=== MENU ADMIN ===");
            System.out.println("1. Lihat Semua Tiket");
            System.out.println("2. Tambah Tiket");
            System.out.println("3. Edit Tiket");
            System.out.println("4. Hapus Tiket");
            System.out.println("5. Lihat Data Pendukung (Rute/Jadwal/Transportasi)");
            System.out.println("6. Logout");
            System.out.print("Pilih: ");
            String p = sc.nextLine().trim();
            switch (p) {
                case "1" -> viewTiket();
                case "2" -> tambahTiketAdmin();
                case "3" -> editTiketAdmin();
                case "4" -> hapusTiketAdmin();
                case "5" -> viewDataPendukung();
                case "6" -> { System.out.println("Logout."); return; }
                default -> System.out.println("Input salah.");
            }
        }
    }

    private void menuPenumpang(Penumpang penumpang) {
        while (true) {
            System.out.println("\n=== MENU PENUMPANG ===");
            System.out.println("1. Lihat Tiket");
            System.out.println("2. Beli Tiket");
            System.out.println("3. Cari Tiket");
            System.out.println("4. Lihat Saldo");
            System.out.println("5. Isi Saldo");
            System.out.println("6. Logout");
            System.out.print("Pilih: ");
            String p = sc.nextLine().trim();
            switch (p) {
                case "1" -> viewTiket();
                case "2" -> beliTiket(penumpang);
                case "3" -> cariTiket();
                case "4" -> System.out.println("Saldo anda: Rp" + penumpangDAO.getSaldo(penumpang.getId()));
                case "5" -> isiSaldoFlow(penumpang);
                case "6" -> { System.out.println("Logout."); return; }
                default -> System.out.println("Input salah.");
            }
        }
    }


    private void viewTiket() {
        List<Tiket> list = tiketDAO.getAllWithDetail();
        if (list.isEmpty()) {
            System.out.println("Belum ada data tiket.");
            return;
        }
        System.out.println("No  | Asal - Tujuan             | Transportasi                           | Harga     | Jadwal");
        System.out.println("----------------------------------------------------------------------------------------------");
        int no = 1;
        for (Tiket t : list) {
            System.out.printf("%-3d | ", no++);
            System.out.printf("%-25s | %-35s | Rp%-8d | %-20s\n",
                    t.getTitikAwal() + " - " + t.getTitikAkhir(),
                    t.getJenisTransportasi() + " " + t.getNamaTransportasi(),
                    t.getHarga(),
                    t.getJadwalStr());
        }
        System.out.println("----------------------------------------------------------------------------------------------");
    }

    private void tambahTiketAdmin() {
        try {
            System.out.print("Masukkan Asal: ");
            String asal = sc.nextLine().trim();
            System.out.print("Masukkan Tujuan: ");
            String tujuan = sc.nextLine().trim();
            System.out.print("Masukkan Transportasi dan Nama Transportasi (contoh: Bus Sinar Jaya): ");
            String trans = sc.nextLine().trim();
            String[] sp = trans.split(" ", 2);
            String jenis = sp[0];
            String namaTrans = sp.length > 1 ? sp[1] : "-";
            System.out.print("Masukkan Harga: ");
            int harga = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Masukkan Jadwal Keberangkatan (Contoh: 07:00 22/05/2025): ");
            String j = sc.nextLine().trim();
            java.util.Date d = new SimpleDateFormat("HH:mm dd/MM/yyyy").parse(j);
            Timestamp ts = new Timestamp(d.getTime());
            Timestamp ts2 = ts;
            int idTrans = transportasiDAO.insert(namaTrans, jenis, 50, ts);
            if (idTrans == -1) { System.out.println("Gagal menambah transportasi."); return; }
            int idRute = ruteDAO.insert(asal, tujuan, idTrans);
            if (idRute == -1) { System.out.println("Gagal menambah rute."); return; }
            Date tanggal = new Date(ts.getTime());
            int idJadwal = jadwalDAO.insert(ts, ts2, tanggal, idRute);
            if (idJadwal == -1) { System.out.println("Gagal menambah jadwal."); return; }
            boolean ok = tiketDAO.insert(harga, idJadwal, idTrans, idRute);
            System.out.println(ok ? "Tiket berhasil ditambahkan." : "Gagal menambah tiket.");
        } catch (Exception e) {
            System.out.println("Input salah: " + e.getMessage());
        }
    }

    private void editTiketAdmin() {
        try {
            viewTiket();
            System.out.print("Masukkan ID tiket yang ingin diubah: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            Tiket t = tiketDAO.findById(id);
            if (t == null) { System.out.println("Tiket tidak ditemukan."); return; }
            System.out.print("Harga baru (" + t.getHarga() + "): ");
            int harga = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Masukkan Asal baru: ");
            String asal = sc.nextLine().trim();
            System.out.print("Masukkan Tujuan baru: ");
            String tujuan = sc.nextLine().trim();
            System.out.print("Masukkan Transportasi dan Nama Transportasi: ");
            String trans = sc.nextLine().trim();
            String[] sp = trans.split(" ", 2);
            String jenis = sp[0];
            String namaTrans = sp.length > 1 ? sp[1] : "-";
            System.out.print("Masukkan Jadwal Keberangkatan (Contoh: 07:00 22/05/2025): ");
            String j = sc.nextLine().trim();
            java.util.Date d = new SimpleDateFormat("HH:mm dd/MM/yyyy").parse(j);
            Timestamp ts = new Timestamp(d.getTime());
            int idTrans = transportasiDAO.insert(namaTrans, jenis, 50, ts);
            int idRute = ruteDAO.insert(asal, tujuan, idTrans);
            Date tanggal = new Date(ts.getTime());
            int idJadwal = jadwalDAO.insert(ts, ts, tanggal, idRute);
            boolean ok = tiketDAO.update(id, harga, idJadwal, idTrans, idRute);
            System.out.println(ok ? "Tiket berhasil diupdate." : "Gagal update tiket.");
        } catch (Exception e) {
            System.out.println("Input salah: " + e.getMessage());
        }
    }

    private void hapusTiketAdmin() {
        try {
            viewTiket();
            System.out.print("Masukkan ID tiket yang ingin dihapus: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            boolean ok = tiketDAO.delete(id);
            System.out.println(ok ? "Tiket dihapus." : "Gagal hapus tiket.");
        } catch (Exception e) {
            System.out.println("Input salah.");
        }
    }

    private void viewDataPendukung() {
        System.out.println("\n=== TRANSPORTASI ===");
        for (Transportasi t : transportasiDAO.getAll()) t.printInfo();
        System.out.println("\n=== RUTE ===");
        for (Rute r : ruteDAO.getAll()) r.printInfo();
    }

    private void isiSaldoFlow(Penumpang p) {
        try {
            System.out.print("Masukkan nominal isi saldo (angka): Rp");
            int nominal = Integer.parseInt(sc.nextLine().trim());
            if (nominal <= 0) { System.out.println("Nominal harus > 0."); return; }
            int current = penumpangDAO.getSaldo(p.getId());
            if ((long)current + nominal > 10_000_000L) { System.out.println("Melebihi batas saldo maksimal Rp10.000.000."); return; }
            String va = generateVA();
            System.out.println("Virtual Account anda: " + va);
            System.out.println("Silakan lakukan pembayaran ke VA di atas...");
            System.out.println("Pembayaran berhasil diterima!");
            boolean ok = penumpangDAO.tambahSaldo(p.getId(), nominal);
            if (ok) System.out.println("Saldo berhasil ditambahkan. Saldo baru: Rp" + penumpangDAO.getSaldo(p.getId()));
            else System.out.println("Gagal menambahkan saldo.");
        } catch (Exception e) {
            System.out.println("Input salah.");
        }
    }

    private String generateVA() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<12;i++) sb.append(r.nextInt(10));
        return sb.toString();
    }

    private void beliTiket(Penumpang p) {
        List<Tiket> list = tiketDAO.getAllWithDetail();
        if (list.isEmpty()) {
            System.out.println("Belum ada tiket tersedia.");
            return;
        }

        System.out.println("Daftar Tiket Tersedia:");
        System.out.println("No  | Asal - Tujuan             | Transportasi                           | Harga     | Jadwal");
        System.out.println("----------------------------------------------------------------------------------------------");
        int no = 1;
        for (Tiket t : list) {
            System.out.printf("%-3d | %-25s | %-35s | Rp%-8d | %-20s\n",
                    no++, t.getTitikAwal() + " - " + t.getTitikAkhir(),
                    t.getJenisTransportasi() + " " + t.getNamaTransportasi(),
                    t.getHarga(),
                    t.getJadwalStr());
        }
        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.print("Masukkan nomor tiket yang ingin dibeli (0 untuk batal): ");

        try {
            int pilihan = Integer.parseInt(sc.nextLine().trim());
            if (pilihan == 0) {
                System.out.println("Batal membeli tiket.");
                return;
            }
            if (pilihan < 1 || pilihan > list.size()) {
                System.out.println("Nomor tidak valid.");
                return;
            }

            Tiket chosen = list.get(pilihan - 1);
            int harga = chosen.getHarga();
            int saldo = penumpangDAO.getSaldo(p.getId());

            if (saldo < harga) {
                System.out.println("Saldo tidak cukup. Silakan top-up.");
                return;
            }

            boolean bayar = penumpangDAO.kurangiSaldo(p.getId(), harga);
            if (!bayar) {
                System.out.println("Gagal proses pembayaran.");
                return;
            }

            boolean catat = recordPenjualan(p.getId(), chosen.getId(), harga);
            if (!catat) {
                System.out.println("Gagal mencatat penjualan (rollback).");
                penumpangDAO.tambahSaldo(p.getId(), harga);
                return;
            }

            boolean hapus = tiketDAO.delete(chosen.getId());
            System.out.println("Pembelian berhasil. Saldo sisa: Rp" + penumpangDAO.getSaldo(p.getId()));

        } catch (Exception e) {
            System.out.println("Input salah.");
        }
    }

    private void cariTiket() {
        System.out.print("Masukkan kata kunci (asal/tujuan/transportasi): ");
        String key = sc.nextLine().trim().toLowerCase();
        List<Tiket> all = tiketDAO.getAllWithDetail();
        List<Tiket> found = new java.util.ArrayList<>();

        for (Tiket t : all) {
            String rute = (t.getTitikAwal() + " " + t.getTitikAkhir()).toLowerCase();
            String trans = (t.getJenisTransportasi() + " " + t.getNamaTransportasi()).toLowerCase();
            String jad = t.getJadwalStr().toLowerCase();
            if (rute.contains(key) || trans.contains(key) || jad.contains(key) || String.valueOf(t.getHarga()).contains(key)) {
                found.add(t);
            }
        }

        if (found.isEmpty()) {
            System.out.println("Tiket tidak ditemukan.");
            return;
        }

        System.out.println("Hasil pencarian:");
        System.out.println("No  | Asal - Tujuan             | Transportasi                           | Harga     | Jadwal");
        System.out.println("----------------------------------------------------------------------------------------------");
        int no = 1;
        for (Tiket t : found) {
            System.out.printf("%-3d | %-25s | %-35s | Rp%-8d | %-20s\n",
                    no++, t.getTitikAwal() + " - " + t.getTitikAkhir(),
                    t.getJenisTransportasi() + " " + t.getNamaTransportasi(),
                    t.getHarga(),
                    t.getJadwalStr());
        }
        System.out.println("----------------------------------------------------------------------------------------------");
    }

    private boolean recordPenjualan(int idPengguna, int idTiket, int harga) {
        String ensure = "CREATE TABLE IF NOT EXISTS penjualan (id_penjualan INT AUTO_INCREMENT PRIMARY KEY, id_pengguna INT NOT NULL, id_tiket INT NOT NULL, harga INT NOT NULL, waktu TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        String sql = "INSERT INTO penjualan (id_pengguna, id_tiket, harga) VALUES (?, ?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
             Statement st = c.createStatement()) {
            st.execute(ensure);
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, idPengguna);
                ps.setInt(2, idTiket);
                ps.setInt(3, harga);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private void ensurePenjualanTable() {
        try (Connection c = DatabaseConnection.getConnection();
             Statement st = c.createStatement()) {
            String ensure = "CREATE TABLE IF NOT EXISTS penjualan (id_penjualan INT AUTO_INCREMENT PRIMARY KEY, id_pengguna INT NOT NULL, id_tiket INT NOT NULL, harga INT NOT NULL, waktu TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            st.execute(ensure);
        } catch (SQLException e) { e.printStackTrace(); }
    }
}