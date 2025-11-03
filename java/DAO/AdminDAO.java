package DAO;

import java.sql.*;

public class AdminDAO {

    private final Connection conn; // koneksi dari luar (Service)

    // Konstruktor baru
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
