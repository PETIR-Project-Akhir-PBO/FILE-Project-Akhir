package DAO;

import ConnectDB.DatabaseConnection;
import Model.Pengguna;
import Model.Penumpang;

import java.sql.*;

public class PenggunaDAO {

    public Pengguna login(String nama, String password) {
        String sql = "SELECT * FROM pengguna WHERE nama=? AND password=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nama);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id_pengguna");
                String noTelp = rs.getString("no_telp");
                int saldo = 0;
                try (PreparedStatement p2 = c.prepareStatement("SELECT saldo FROM penumpang WHERE id_pengguna=?")) {
                    p2.setInt(1, id);
                    ResultSet r2 = p2.executeQuery();
                    if (r2.next()) saldo = r2.getInt("saldo");
                    else {
                        try (PreparedStatement ins = c.prepareStatement("INSERT INTO penumpang (id_pengguna, saldo) VALUES (?, 0)")) {
                            ins.setInt(1, id);
                            ins.executeUpdate();
                            saldo = 0;
                        }
                    }
                }
                return new Penumpang(id, rs.getString("nama"), rs.getString("password"), noTelp, saldo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int daftar(String nama, String password, String noTelp) {
        String cek = "SELECT id_pengguna FROM pengguna WHERE no_telp=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(cek)) {
            ps.setString(1, noTelp);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return -2;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

        String sql = "INSERT INTO pengguna (nama, password, no_telp) VALUES (?, ?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nama);
            ps.setString(2, password);
            ps.setString(3, noTelp);
            ps.executeUpdate();
            ResultSet g = ps.getGeneratedKeys();
            if (g.next()) {
                int id = g.getInt(1);
                try (PreparedStatement ins = c.prepareStatement("INSERT INTO penumpang (id_pengguna, saldo) VALUES (?, 0)")) {
                    ins.setInt(1, id);
                    ins.executeUpdate();
                }
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
