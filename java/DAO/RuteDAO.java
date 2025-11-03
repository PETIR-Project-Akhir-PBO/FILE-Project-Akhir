package DAO;

import Model.Rute;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RuteDAO {

    private final Connection conn; // ✅ koneksi bersama dari Service

    public RuteDAO(Connection conn) {
        this.conn = conn;
    }

    public int insert(String titikAwal, String titikAkhir, int idTransportasi) {
        String nama = titikAwal + " - " + titikAkhir;
        String sql = "INSERT INTO rute (nama_rute, titik_awal, titik_akhir, id_transportasi) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nama);
            ps.setString(2, titikAwal);
            ps.setString(3, titikAkhir);
            ps.setInt(4, idTransportasi);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Rute findById(int id) {
        String sql = "SELECT * FROM rute WHERE id_rute = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Rute(
                        rs.getInt("id_rute"),
                        rs.getString("nama_rute"),
                        rs.getString("titik_awal"),
                        rs.getString("titik_akhir"),
                        rs.getInt("id_transportasi")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Rute> getAll() {
        List<Rute> out = new ArrayList<>();
        String sql = "SELECT * FROM rute ORDER BY id_rute";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new Rute(
                    rs.getInt("id_rute"),
                    rs.getString("nama_rute"),
                    rs.getString("titik_awal"),
                    rs.getString("titik_akhir"),
                    rs.getInt("id_transportasi")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }
}
