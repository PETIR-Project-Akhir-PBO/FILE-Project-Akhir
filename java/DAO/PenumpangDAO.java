package DAO;

import Model.Penumpang;
import java.sql.*;

public class PenumpangDAO {

    private final Connection conn; // ✅ koneksi dikirim dari Service

    public PenumpangDAO(Connection conn) {
        this.conn = conn;
    }

    public Penumpang getById(int id) {
        String sql = """
            SELECT p.id_pengguna, p.nama, p.password, p.no_telp, pn.saldo
            FROM pengguna p
            LEFT JOIN penumpang pn ON p.id_pengguna = pn.id_pengguna
            WHERE p.id_pengguna = ?
            """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int saldo = rs.getInt("saldo");
                return new Penumpang(
                        rs.getInt("id_pengguna"),
                        rs.getString("nama"),
                        rs.getString("password"),
                        rs.getString("no_telp"),
                        saldo
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getSaldo(int id) {
        String sql = "SELECT saldo FROM penumpang WHERE id_pengguna=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("saldo");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean updateSaldo(int id, int saldoBaru) {
        String sql = "UPDATE penumpang SET saldo=? WHERE id_pengguna=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, saldoBaru);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean tambahSaldo(int id, int nominal) {
        int current = getSaldo(id);
        long hasil = (long) current + nominal;
        if (hasil > 10_000_000L) return false;
        return updateSaldo(id, current + nominal);
    }

    public boolean kurangiSaldo(int id, int nominal) {
        int current = getSaldo(id);
        if (current < nominal) return false;
        return updateSaldo(id, current - nominal);
    }
}
