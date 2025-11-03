package GUI;

import Service.Service;

import javax.swing.*;
import java.awt.*;

public class TopUpDialog extends JDialog {

    private final Service service;
    private final int userId;
    private JTextField txtNominal;
    private final Runnable onDone; // agar parent bisa refresh saldo

    public TopUpDialog(Frame owner, Service service, int userId) {
        this(owner, service, userId, null);
    }

    public TopUpDialog(Frame owner, Service service, int userId, Runnable onDone) {
        super(owner, "Isi Saldo", true);
        this.service = service;
        this.userId = userId;
        this.onDone = onDone;

        setSize(360, 160);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10,10));

        JPanel center = new JPanel(new GridLayout(2,1,8,8));
        center.setBorder(BorderFactory.createEmptyBorder(16,16,0,16));
        center.add(new JLabel("Nominal Top Up (angka):"));
        txtNominal = new JTextField();
        center.add(txtNominal);
        add(center, BorderLayout.CENTER);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnBatal = new JButton("Batal");
        JButton btnOK    = new JButton("Tambah Saldo");
        btnBatal.addActionListener(e -> dispose());
        btnOK.addActionListener(e -> onSubmitTopup());
        btns.add(btnBatal); btns.add(btnOK);
        add(btns, BorderLayout.SOUTH);
    }

    private void onSubmitTopup() {
        String s = txtNominal.getText().trim();
        if (s.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nominal tidak boleh kosong.");
            return;
        }
        try {
            int nominal = Integer.parseInt(s);
            if (nominal <= 0) {
                JOptionPane.showMessageDialog(this, "Nominal harus > 0.");
                return;
            }
            boolean ok = service.tambahSaldo(userId, nominal);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Saldo berhasil ditambahkan.");
                if (onDone != null) onDone.run();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambah saldo. (Mungkin melebihi batas)");
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Nominal harus angka.");
        }
    }
}
