package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import Ui.UiKit;
import Service.Service;

// tambahan import untuk filter teks
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/** Form Registrasi dengan background gradient dan card putih. */
public class RegisterForm extends JFrame {
    private final JTextField txtNama = new JTextField();
    private final JTextField txtTelp = new JTextField();
    private final JPasswordField txtPass = new JPasswordField();

    private final JFrame parent;     // boleh null
    private final Service service;   // bawa Service yang sama

    // Konstruktor utama
    public RegisterForm(Service service) { this(service, null); }

    public RegisterForm(Service service, JFrame parent) {
        this.service = service;
        this.parent  = parent;

        setTitle("Daftar Akun Baru");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 520);
        setLocationRelativeTo(null);

        // === BACKGROUND: panel gradient hijau pastel -> beige tan ===
        GradientPane bg = new GradientPane();
        bg.setLayout(new GridBagLayout());
        setContentPane(bg);

        // === CARD PUTIH DI TENGAH ===
        CardPanel card = new CardPanel();
        card.setLayout(new GridBagLayout());
        card.setBorder(new EmptyBorder(24, 28, 28, 28));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 14, 8, 14);
        g.anchor = GridBagConstraints.WEST;
        g.fill   = GridBagConstraints.HORIZONTAL;
        g.gridx  = 0;
        g.gridwidth = 2;

        // Title & sub
        JLabel title = new JLabel("Buat Akun Pengguna Baru");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        JLabel sub = new JLabel("Silahkan isi data di bawah dengan benar");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 13));
        sub.setForeground(new Color(100, 110, 120));

        g.gridy = 0; card.add(title, g);
        g.gridy = 1; card.add(sub, g);

        // Terapkan filter input:
        // - txtNama: blokir spasi sama sekali
        // - txtTelp: hanya angka, tanpa spasi, maksimal 15 digit
        ((AbstractDocument) txtNama.getDocument()).setDocumentFilter(new NoSpaceFilter());
        ((AbstractDocument) txtTelp.getDocument()).setDocumentFilter(new DigitsOnlyFilter(15));

        // Nama
        g.gridwidth = 1; g.gridy = 2; g.gridx = 0;
        card.add(label("Nama"), g);
        g.gridx = 1; txtNama.setPreferredSize(new Dimension(260, 36));
        txtNama.setToolTipText("Tidak boleh ada spasi. Contoh: Petir01");
        card.add(txtNama, g);

        // Password
        g.gridy = 3; g.gridx = 0;
        card.add(label("Password"), g);
        g.gridx = 1; txtPass.setPreferredSize(new Dimension(260, 36));
        txtPass.setEchoChar((char) 0);
        txtPass.setToolTipText("Minimal 6 karakter disarankan.");
        card.add(txtPass, g);

        // No Telp
        g.gridy = 4; g.gridx = 0;
        card.add(label("No. Telp"), g);
        g.gridx = 1; txtTelp.setPreferredSize(new Dimension(260, 36));
        txtTelp.setToolTipText("Hanya angka, 10–15 digit, tanpa spasi atau tanda lain.");
        card.add(txtTelp, g);

        // Tombol
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        btnRow.setOpaque(false);
        JButton btnBatal  = softButton("Batal");
        JButton btnDaftar = softButton("Buat Akun Baru");

        btnBatal.addActionListener(e -> {
            dispose();
            if (parent != null) parent.setVisible(true);
            else new modernlogin(service).setVisible(true);
        });
        btnDaftar.addActionListener(e -> registerAction());

        btnRow.add(btnBatal);
        btnRow.add(btnDaftar);

        g.gridy = 5; g.gridx = 0; g.gridwidth = 2;
        g.fill = GridBagConstraints.NONE; g.anchor = GridBagConstraints.CENTER;
        card.add(btnRow, g);

        // Tambahkan card ke tengah background
        GridBagConstraints root = new GridBagConstraints();
        root.gridx = 0; root.gridy = 0;
        root.fill = GridBagConstraints.NONE;
        root.anchor = GridBagConstraints.CENTER;
        root.insets = new Insets(24, 24, 24, 24);
        bg.add(card, root);

        // Kembali ke login saat jendela ditutup dari X
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosed(java.awt.event.WindowEvent e) {
                if (parent != null) parent.setVisible(true);
                else new modernlogin(service).setVisible(true);
            }
        });
    }

    // ---------- UI Helpers ----------
    private JLabel label(String text) {
        JLabel l = new JLabel(text + ":");
        l.setFont(new Font("SansSerif", Font.BOLD, 14));
        return l;
    }

    private JButton softButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setForeground(Color.BLACK);
        b.setBackground(new Color(236, 236, 213)); // ECECD5
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(170, 38));
        return b;
    }

    /** Panel gradien hijau pastel (C4E1A4) -> beige tan (C4A77B). */
    /** Panel gradien hijau pastel (C4E1A4) -> beige tan (C4A77B). */
static class GradientPane extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        // ✅ gunakan RenderingHints (bukan Graphics2D)
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint gp = new GradientPaint(
            0, 0, new Color(196, 225, 164),
            getWidth(), getHeight(), new Color(196, 167, 123)
        );
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
    }
}


    /** Panel card putih dengan sudut membulat & bayangan halus. */
    static class CardPanel extends JPanel {
        private final int arc = 24;
        CardPanel() { setOpaque(false); }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight();
            g2.setColor(new Color(0, 0, 0, 28));
            g2.fillRoundRect(8, 10, w - 16, h - 16, arc, arc);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 2, w - 16, h - 16, arc, arc);
            g2.dispose();
        }
    }

    // ---------- Action ----------
    private void registerAction() {
        String nama = txtNama.getText().trim();
        String pass = new String(txtPass.getPassword()).trim();
        String telp = txtTelp.getText().trim();

        // Validasi kosong
        if (nama.isEmpty() || pass.isEmpty() || telp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lengkapi semua data.");
            return;
        }

        // Validasi larangan spasi pada "Nama"
        if (nama.contains(" ")) {
            JOptionPane.showMessageDialog(this,
                    "Nama tidak boleh mengandung spasi.\nContoh yang benar: fakhri01",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validasi nomor telepon: hanya angka, 10–15 digit, tanpa spasi/tanda
        if (!telp.matches("\\d{10,15}")) {
            JOptionPane.showMessageDialog(this,
                    "No. Telp harus hanya angka (10–15 digit) dan tanpa spasi.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // (Opsional) Validasi password minimal 6 karakter
        if (pass.length() < 6) {
            JOptionPane.showMessageDialog(this,
                    "Password minimal 6 karakter.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int id = service.daftarPengguna(nama, pass, telp); // lewat Service
            if (id > 0) {
                UiKit.info(this, "Pendaftaran", "Pendaftaran berhasil! Silakan login.");
                dispose();
                if (parent != null) parent.setVisible(true);
                else new modernlogin(service).setVisible(true);
            } else if (id == -2) {
                JOptionPane.showMessageDialog(this, "No. HP sudah digunakan.");
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan pengguna.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + ex.getMessage());
        }
    }

    /* ====================== DocumentFilters ====================== */

    /** Filter untuk MENOLAK semua spasi pada input. */
    static class NoSpaceFilter extends DocumentFilter {
        @Override public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string == null) return;
            String s = string.replace(" ", "");
            if (!s.isEmpty()) super.insertString(fb, offset, s, attr);
        }
        @Override public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text == null) return;
            String s = text.replace(" ", "");
            super.replace(fb, offset, length, s, attrs);
        }
    }

    /** Filter untuk HANYA mengizinkan angka, dengan panjang maksimum. */
    static class DigitsOnlyFilter extends DocumentFilter {
        private final int maxLen;
        DigitsOnlyFilter(int maxLen) { this.maxLen = maxLen; }

        @Override public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string == null) return;
            String s = string.replaceAll("\\D", ""); // buang non-digit
            if (s.isEmpty()) return;
            if (fb.getDocument().getLength() + s.length() <= maxLen) {
                super.insertString(fb, offset, s, attr);
            } // jika lebih dari max, diamkan (tidak input)
        }

        @Override public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text == null) { super.replace(fb, offset, length, null, attrs); return; }
            String s = text.replaceAll("\\D", "");
            int newLen = fb.getDocument().getLength() - length + s.length();
            if (newLen <= maxLen) {
                super.replace(fb, offset, length, s, attrs);
            }
        }
    }
}
