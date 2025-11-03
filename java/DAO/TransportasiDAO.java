package DAO;

import Model.Transportasi;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class TransportasiDAO {

    private final Connection conn; // ✅ koneksi dikirim dari Service

    public TransportasiDAO(Connection conn) {
        this.conn = conn;
    }

    public int insert(String nama, String jenis, int kapasitas, Timestamp jadwal) {
        String sql = "INSERT INTO transportasi (nama_transportasi, jenis, kapasitas, jadwal_keberangkatan) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nama);
            ps.setString(2, jenis);
            ps.setInt(3, kapasitas);
            ps.setTimestamp(4, jadwal);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Transportasi findById(int id) {
        String sql = "SELECT * FROM transportasi WHERE id_transportasi=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Timestamp ts = rs.getTimestamp("jadwal_keberangkatan");
                    LocalDateTime ldt = ts == null ? null
                            : ts.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    return new Transportasi(
                            rs.getInt("id_transportasi"),
                            rs.getString("nama_transportasi"),
                            rs.getString("jenis"),
                            rs.getInt("kapasitas"),
                            ldt
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Transportasi> getAll() {
        List<Transportasi> out = new ArrayList<>();
        String sql = "SELECT * FROM transportasi ORDER BY id_transportasi";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("jadwal_keberangkatan");
                LocalDateTime ldt = ts == null ? null
                        : ts.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                out.add(new Transportasi(
                        rs.getInt("id_transportasi"),
                        rs.getString("nama_transportasi"),
                        rs.getString("jenis"),
                        rs.getInt("kapasitas"),
                        ldt
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }
}
