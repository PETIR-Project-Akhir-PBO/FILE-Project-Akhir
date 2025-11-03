package ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import Service.Service;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ppppetir"; // ganti kalau beda
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection conn;

    private DatabaseConnection() {}

    // Satu-satunya tempat bikin koneksi fisik
    private static synchronized Connection ensureConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");   // pastikan driver ter-load
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Koneksi berhasil.");
            }
            return conn;
        } catch (Exception e) {
            throw new RuntimeException("Gagal membuat koneksi: " + e.getMessage(), e);
        }
    }

    // === API yang bisa kamu pakai di mana saja ===
    public static Connection getConn() {                   // dipakai di banyak contoh
        return ensureConnection();
    }

    public static Connection getConnection() {             // kompatibel dgn kode lama kamu
        return ensureConnection();
    }

    public static Service createService() {
        return new Service(getConn());
    }
}
