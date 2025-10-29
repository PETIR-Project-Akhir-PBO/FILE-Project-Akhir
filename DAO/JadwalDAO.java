package DAO;

import ConnectDB.DatabaseConnection;

import java.sql.*;

public class JadwalDAO {

    public int insert(Timestamp jamBerangkat, Timestamp jamTiba, Date tanggal, int idRute) {
        String sql = "INSERT INTO jadwal (jam_berangkat, jam_tiba, tanggal, id_rute) VALUES (?, ?, ?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setTimestamp(1, jamBerangkat);
            ps.setTimestamp(2, jamTiba);
            ps.setDate(3, tanggal);
            ps.setInt(4, idRute);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }
}
