package DAO;

import Model.Tiket;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TiketDAO {

    private final Connection conn; // ✅ koneksi dikirim dari Service

    public TiketDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Tiket> getAllWithDetail() {
    List<Tiket> list = new ArrayList<>();
    String sql =
        "SELECT t.id_tiket, t.harga, " +
        "       j.id_jadwal, j.tanggal, j.jam_berangkat, j.jam_tiba, " +
        "       r.titik_awal, r.titik_akhir, " +
        "       tp.jenis AS jenis_transportasi, tp.nama_transportasi " +
        "FROM tiket t " +
        "JOIN jadwal j ON t.id_jadwal = j.id_jadwal " +
        "JOIN rute   r ON t.id_rute   = r.id_rute " +
        // pakai id_transportasi dari tabel tiket (paling aman, kolomnya pasti ada)
        "JOIN transportasi tp ON t.id_transportasi = tp.id_transportasi " +
        "ORDER BY j.tanggal, j.jam_berangkat, t.id_tiket";

    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        java.text.SimpleDateFormat fDate =
                new java.text.SimpleDateFormat("dd/MM/yyyy", new java.util.Locale("id","ID"));
        java.text.SimpleDateFormat fTime =
                new java.text.SimpleDateFormat("HH:mm", new java.util.Locale("id","ID"));

        while (rs.next()) {
            Tiket t = new Tiket();
            t.setId(rs.getInt("id_tiket"));
            t.setHarga(rs.getInt("harga"));
            t.setIdJadwal(rs.getInt("id_jadwal"));

            java.sql.Date tanggal = rs.getDate("tanggal");
            java.sql.Timestamp jb = rs.getTimestamp("jam_berangkat");
            java.sql.Timestamp jt = rs.getTimestamp("jam_tiba");

            String tanggalStr   = (tanggal != null) ? fDate.format(tanggal) : "-";
            String berangkatStr = (jb != null) ? fTime.format(jb) : "-";
            String tibaStr      = (jt != null) ? fTime.format(jt) : "-";

            t.setJadwalStr(berangkatStr + " " + tanggalStr); // contoh: "07:30 23/10/2025"
            t.setJamTibaStr(tibaStr);                         // contoh: "09:15"
            t.setTitikAwal(rs.getString("titik_awal"));
            t.setTitikAkhir(rs.getString("titik_akhir"));
            t.setJenisTransportasi(rs.getString("jenis_transportasi"));
            t.setNamaTransportasi(rs.getString("nama_transportasi"));

            list.add(t);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}



    public boolean insert(int harga, int idJadwal, int idTransportasi, int idRute) {
        String sql = "INSERT INTO tiket (harga, id_jadwal, id_transportasi, id_rute) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, harga);
            ps.setInt(2, idJadwal);
            ps.setInt(3, idTransportasi);
            ps.setInt(4, idRute);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(int idTiket, int harga, int idJadwal, int idTransportasi, int idRute) {
        String sql = "UPDATE tiket SET harga=?, id_jadwal=?, id_transportasi=?, id_rute=? WHERE id_tiket=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, harga);
            ps.setInt(2, idJadwal);
            ps.setInt(3, idTransportasi);
            ps.setInt(4, idRute);
            ps.setInt(5, idTiket);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int idTiket) {
        String sql = "DELETE FROM tiket WHERE id_tiket=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTiket);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Tiket findById(int idTiket) {
        String sql = """
            SELECT t.id_tiket, t.harga,
                   j.id_jadwal, j.jam_berangkat,
                   r.id_rute, r.titik_awal, r.titik_akhir,
                   tr.id_transportasi, tr.nama_transportasi, tr.jenis
            FROM tiket t
            JOIN jadwal j ON t.id_jadwal = j.id_jadwal
            JOIN rute r ON t.id_rute = r.id_rute
            JOIN transportasi tr ON t.id_transportasi = tr.id_transportasi
            WHERE t.id_tiket=?
            """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTiket);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Tiket tk = new Tiket(
                            rs.getInt("id_tiket"),
                            rs.getInt("harga"),
                            rs.getInt("id_jadwal"),
                            rs.getInt("id_transportasi"),
                            rs.getInt("id_rute")
                    );
                    Timestamp ts = rs.getTimestamp("jam_berangkat");
                    String jam = (ts == null) ? "-" :
                            new SimpleDateFormat("HH:mm dd/MM/yyyy").format(new java.util.Date(ts.getTime()));
                    tk.setDisplayInfo(
                            rs.getString("titik_awal"),
                            rs.getString("titik_akhir"),
                            rs.getString("jenis"),
                            rs.getString("nama_transportasi"),
                            jam
                    );
                    return tk;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
