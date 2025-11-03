package GUI;

import Service.Service;
import Model.Tiket;

import javax.swing.*;
import java.awt.*;

public class ConfirmBeliDialog extends JDialog {

    private final Service service;
    private final int userId;
    private final int tiketId;
    private final Runnable onSuccess; // callback opsional agar parent bisa refresh

    public ConfirmBeliDialog(Frame owner, Service service, int userId, int tiketId) {
        this(owner, service, userId, tiketId, null);
    }

    public ConfirmBeliDialog(Frame owner, Service service, int userId, int tiketId, Runnable onSuccess) {
        super(owner, "Konfirmasi Pembelian", true);
        this.service = service;
        this.userId = userId;
        this.tiketId = tiketId;
        this.onSuccess = onSuccess;

        setSize(420, 220);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10,10));

        var t = service.findTiketById(tiketId);
        String detail = (t == null)
                ? "Tiket tidak ditemukan."
                : "<html>Yakin beli tiket ini?<br/>"
                  + t.getTitikAwal() + " → " + t.getTitikAkhir()
                  + "<br/>" + t.getJenisTransportasi() + " " + t.getNamaTransportasi()
                  + "<br/>Jadwal: " + t.getJadwalStr()
                  + "<br/>Harga: <b>Rp" + t.getHarga() + "</b></html>";

        JLabel lbl = new JLabel(detail);
        lbl.setBorder(BorderFactory.createEmptyBorder(16,16,0,16));
        add(lbl, BorderLayout.CENTER);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnBatal = new JButton("Batal");
        JButton btnBeli  = new JButton("Beli");
        btnBatal.addActionListener(e -> dispose());
        btnBeli.addActionListener(e -> onConfirm());
        btns.add(btnBatal); btns.add(btnBeli);
        add(btns, BorderLayout.SOUTH);
    }

    private void onConfirm() {
        Tiket t = service.findTiketById(tiketId);
        if (t == null) {
            JOptionPane.showMessageDialog(this, "Tiket tidak ditemukan.");
            return;
        }
        int saldo = service.getSaldo(userId);
        if (saldo < t.getHarga()) {
            JOptionPane.showMessageDialog(this, "Saldo tidak cukup. Silakan top up.");
            return;
        }

        // potong saldo
        if (!service.kurangiSaldo(userId, t.getHarga())) {
            JOptionPane.showMessageDialog(this, "Gagal memotong saldo.");
            return;
        }
        // catat penjualan
        if (!service.catatPenjualan(userId, t.getId(), t.getHarga())) {
            service.tambahSaldo(userId, t.getHarga()); // rollback sederhana
            JOptionPane.showMessageDialog(this, "Gagal mencatat penjualan.");
            return;
        }
        // hapus tiket
        if (!service.hapusTiket(t.getId())) {
            service.tambahSaldo(userId, t.getHarga()); // rollback sederhana
            JOptionPane.showMessageDialog(this, "Gagal menghapus tiket.");
            return;
        }

        JOptionPane.showMessageDialog(this, "Pembelian berhasil!");
        if (onSuccess != null) onSuccess.run();
        dispose();
    }
}
