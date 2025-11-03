package Main;

import javax.swing.SwingUtilities;
import GUI.modernlogin;
import Service.Service;
import ConnectDB.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Service svc = DatabaseConnection.createService();
            new modernlogin(svc).setVisible(true);
        });
    }
}
