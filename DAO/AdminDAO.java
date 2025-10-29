package DAO;

import ConnectDB.DatabaseConnection;
import java.sql.*;

public class AdminDAO {

    public boolean isAdmin(int idPengguna) {
        String sql = "SELECT akses FROM admin WHERE id_pengguna=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
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
