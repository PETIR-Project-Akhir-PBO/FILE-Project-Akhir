package Service;

import DAO.*;
import Model.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Service {

    // Pegang 1 koneksi bersama
    private final Connection conn;

    // Semua DAO pakai koneksi yang sama
    private final PenggunaDAO penggunaDAO;
    private final AdminDAO adminDAO;
    private final PenumpangDAO penumpangDAO;
    private final TiketDAO tiketDAO;
    private final TransportasiDAO transportasiDAO;
    private final RuteDAO ruteDAO;
    private final JadwalDAO jadwalDAO;

    // ===== Konstruktor untuk APLIKASI GUI =====
    public Service(Connection conn) {
        this.conn = conn;
        this.penggunaDAO     = new PenggunaDAO(conn);
        this.adminDAO        = new AdminDAO(conn);
        this.penumpangDAO    = new PenumpangDAO(conn);
        this.tiketDAO        = new TiketDAO(conn);
        this.transportasiDAO = new TransportasiDAO(conn);
        this.ruteDAO         = new RuteDAO(conn);
        this.jadwalDAO       = new JadwalDAO(conn);
        ensurePenjualanTable();
    }
    
    // di Service.java, tambahkan method ini
    public int daftarPengguna(String nama, String password, String noTelp) {
        return penggunaDAO.daftar(nama, password, noTelp);
    }


    // ================== AUTH ==================
    /** Hasil login untuk dipakai GUI */
    public static class AuthResult {
        public final int id;
        public final String nama;
        public final boolean isAdmin;
        public AuthResult(int id, String nama, boolean isAdmin) {
            this.id = id; this.nama = nama; this.isAdmin = isAdmin;
        }
    }

    /** Dipanggil modernlogin */
    public AuthResult loginAuth(String username, String password) throws Exception {
        Pengguna p = penggunaDAO.login(username, password);
        if (p == null) return null;
        boolean isAdmin = adminDAO.isAdmin(p.getId());
        return new AuthResult(p.getId(), p.getNama(), isAdmin);
    }

    /** Opsi sederhana kalau kamu ingin boolean saja */
    public boolean login(String username, String password) throws Exception {
        return loginAuth(username, password) != null;
    }

    // ================ PENUMPANG ===============
    public int getSaldo(int idPengguna) { return penumpangDAO.getSaldo(idPengguna); }
    public boolean tambahSaldo(int idPengguna, int nominal) { return penumpangDAO.tambahSaldo(idPengguna, nominal); }
    public boolean kurangiSaldo(int idPengguna, int nominal) { return penumpangDAO.kurangiSaldo(idPengguna, nominal); }

    // ================= TIKET ==================
    public List<Tiket> getSemuaTiket() { return tiketDAO.getAllWithDetail(); }
    public Tiket findTiketById(int id) { return tiketDAO.findById(id); }
    public boolean hapusTiket(int id) { return tiketDAO.delete(id); }

    public boolean tambahTiket(String asal, String tujuan, String jenis, String namaTrans,
                               int harga, Timestamp berangkat, Timestamp tiba, Date tanggal) throws Exception {
        int idTrans = transportasiDAO.insert(namaTrans, jenis, 50, berangkat);
        if (idTrans == -1) return false;
        int idRute = ruteDAO.insert(asal, tujuan, idTrans);
        if (idRute == -1) return false;
        int idJadwal = jadwalDAO.insert(berangkat, tiba, tanggal, idRute);
        if (idJadwal == -1) return false;
        return tiketDAO.insert(harga, idJadwal, idTrans, idRute);
    }

    public boolean updateTiket(int idTiket, int harga, String asal, String tujuan,
                               String jenis, String namaTrans, Timestamp berangkat,
                               Timestamp tiba, Date tanggal) throws Exception {
        int idTrans = transportasiDAO.insert(namaTrans, jenis, 50, berangkat);
        int idRute  = ruteDAO.insert(asal, tujuan, idTrans);
        int idJadwal= jadwalDAO.insert(berangkat, tiba, tanggal, idRute);
        return tiketDAO.update(idTiket, harga, idJadwal, idTrans, idRute);
    }

    // =============== PENJUALAN ===============
    public boolean catatPenjualan(int idPengguna, int idTiket, int harga) {
        String sql = "INSERT INTO penjualan (id_pengguna, id_tiket, harga) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPengguna);
            ps.setInt(2, idTiket);
            ps.setInt(3, harga);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private void ensurePenjualanTable() {
        String ensure = """
            CREATE TABLE IF NOT EXISTS penjualan (
              id_penjualan INT AUTO_INCREMENT PRIMARY KEY,
              id_pengguna INT NOT NULL,
              id_tiket INT NOT NULL,
              harga INT NOT NULL,
              waktu TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """;
        try (Statement st = conn.createStatement()) {
            st.execute(ensure);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // ============= Util untuk GUI =============
    /** Cari tiket mengandung kata kunci pada rute/transportasi/jadwal/harga */
    public List<Tiket> cariTiket(String keyword) {
        String key = keyword == null ? "" : keyword.toLowerCase();
        List<Tiket> all = tiketDAO.getAllWithDetail();
        List<Tiket> found = new ArrayList<>();
        for (Tiket t : all) {
            String rute  = (t.getTitikAwal() + " " + t.getTitikAkhir()).toLowerCase();
            String trans = (t.getJenisTransportasi() + " " + t.getNamaTransportasi()).toLowerCase();
            String jad   = t.getJadwalStr().toLowerCase();
            boolean match = rute.contains(key) || trans.contains(key) || jad.contains(key)
                            || String.valueOf(t.getHarga()).contains(key);
            if (match) found.add(t);
        }
        return found;
    }
}
