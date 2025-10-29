package DAO;

import ConnectDB.DatabaseConnection;
import Model.Tiket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TiketDAO {

    public List<Tiket> getAllWithDetail() {
        List<Tiket> out = new ArrayList<>();
        String sql = """
            SELECT t.id_tiket, t.harga,
                   j.id_jadwal, j.jam_berangkat,
                   r.id_rute, r.titik_awal, r.titik_akhir,
                   tr.id_transportasi, tr.nama_transportasi, tr.jenis
            FROM tiket t
            JOIN jadwal j ON t.id_jadwal = j.id_jadwal
            JOIN rute r ON t.id_rute = r.id_rute
            JOIN transportasi tr ON t.id_transportasi = tr.id_transportasi
            ORDER BY t.id_tiket
            """;
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Tiket tk = new Tiket(rs.getInt("id_tiket"), rs.getInt("harga"), rs.getInt("id_jadwal"), rs.getInt("id_transportasi"), rs.getInt("id_rute"));
                Timestamp ts = rs.getTimestamp("jam_berangkat");
                String jam = ts == null ? "-" : new java.text.SimpleDateFormat("HH:mm dd/MM/yyyy").format(new java.util.Date(ts.getTime()));
                tk.setDisplayInfo(rs.getString("titik_awal"), rs.getString("titik_akhir"), rs.getString("jenis"), rs.getString("nama_transportasi"), jam);
                out.add(tk);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return out;
    }

    public boolean insert(int harga, int idJadwal, int idTransportasi, int idRute) {
        String sql = "INSERT INTO tiket (harga, id_jadwal, id_transportasi, id_rute) VALUES (?, ?, ?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, harga);
            ps.setInt(2, idJadwal);
            ps.setInt(3, idTransportasi);
            ps.setInt(4, idRute);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean update(int idTiket, int harga, int idJadwal, int idTransportasi, int idRute) {
        String sql = "UPDATE tiket SET harga=?, id_jadwal=?, id_transportasi=?, id_rute=? WHERE id_tiket=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, harga);
            ps.setInt(2, idJadwal);
            ps.setInt(3, idTransportasi);
            ps.setInt(4, idRute);
            ps.setInt(5, idTiket);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean delete(int idTiket) {
        String sql = "DELETE FROM tiket WHERE id_tiket=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idTiket);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
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
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idTiket);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Tiket tk = new Tiket(rs.getInt("id_tiket"), rs.getInt("harga"), rs.getInt("id_jadwal"), rs.getInt("id_transportasi"), rs.getInt("id_rute"));
                Timestamp ts = rs.getTimestamp("jam_berangkat");
                String jam = ts == null ? "-" : new java.text.SimpleDateFormat("HH:mm dd/MM/yyyy").format(new java.util.Date(ts.getTime()));
                tk.setDisplayInfo(rs.getString("titik_awal"), rs.getString("titik_akhir"), rs.getString("jenis"), rs.getString("nama_transportasi"), jam);
                return tk;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}
