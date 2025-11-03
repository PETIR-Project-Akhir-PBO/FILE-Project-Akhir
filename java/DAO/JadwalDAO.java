package DAO;

import java.sql.*;

public class JadwalDAO {

    private final Connection conn; // ✅ koneksi dikirim dari Service

    public JadwalDAO(Connection conn) {
        this.conn = conn;
    }

    public int insert(Timestamp jamBerangkat, Timestamp jamTiba, Date tanggal, int idRute) {
        String sql = "INSERT INTO jadwal (jam_berangkat, jam_tiba, tanggal, id_rute) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setTimestamp(1, jamBerangkat);
            ps.setTimestamp(2, jamTiba);
            ps.setDate(3, tanggal);
            ps.setInt(4, idRute);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
