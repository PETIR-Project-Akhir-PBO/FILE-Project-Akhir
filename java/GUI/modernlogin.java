package GUI;

import Service.Service;
import Model.Pengguna;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import javax.swing.text.JTextComponent;

/** Login modern PETIR — UI seperti versi lama, hanya ditambah integrasi Service. */
public class modernlogin extends JFrame {
    // ===== integrasi =====
    private final Service service;

    // ===== komponen UI lama =====
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JCheckBox cbShow;
    private JButton btnLogin, btnRegister;

    // ===== konstruktor BARU: terima Service =====
    public modernlogin(Service service) {
        this.service = service;
        setTitle("Selamat Datang di PETIR");
        setSize(780, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        buildUI(); // ⬅️ semua komponen UI lama dibangun di sini
    }

    public modernlogin() {
        this(ConnectDB.DatabaseConnection.createService());
    }

    // ================= UI LAMA (biarkan tampilannya sama) =================
    private void buildUI() {
        // Panel kiri gradasi
        JPanel leftPanel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(196, 225, 164),   // C4E1A4 pastel green
                        getWidth(), getHeight(), new Color(196, 167, 123) // C4A77B beige tan
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 20, 0);

        JPanel leftContent = new JPanel();
        leftContent.setLayout(new BoxLayout(leftContent, BoxLayout.Y_AXIS));
        leftContent.setOpaque(false);
        leftContent.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel iconBus = new JLabel();
        setIconFrom("assets/bus.png", iconBus, 140, 140);
        if (iconBus.getIcon() == null) {
            iconBus.setText("\uD83D\uDE8C");
            iconBus.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 120));
            iconBus.setForeground(Color.WHITE);
        }
        iconBus.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel leftTitle = new JLabel("SELAMAT DATANG DI APLIKASI PETIR");
        leftTitle.setForeground(new Color(236, 236, 213));
        leftTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        leftTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftContent.add(Box.createVerticalGlue());
        leftContent.add(iconBus);
        leftContent.add(Box.createRigidArea(new Dimension(0, 20)));
        leftContent.add(leftTitle);
        leftContent.add(Box.createVerticalGlue());
        leftPanel.add(leftContent, gbc);

        // Panel kanan — form login (tengah)
        JPanel right = new JPanel(new GridBagLayout());
        right.setBackground(Color.WHITE);

        JLabel iconUser = new JLabel("\uD83D\uDC64", SwingConstants.CENTER);
        iconUser.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        iconUser.setMaximumSize(new Dimension(90, 90));
        iconUser.setPreferredSize(new Dimension(90, 90));

        JLabel signIn = new JLabel("Log In", SwingConstants.CENTER);
        signIn.setFont(new Font("SansSerif", Font.BOLD, 22));

        txtUsername = new JTextField("Masukkan username...");
        txtUsername.setForeground(Color.GRAY);
        addPlaceholder(txtUsername, "Masukkan username...");
        styleField(txtUsername);

        txtPassword = new JPasswordField("Masukkan password...");
        txtPassword.setEchoChar((char)0);
        txtPassword.setForeground(Color.GRAY);
        addPlaceholder(txtPassword, "Masukkan password...");
        styleField(txtPassword);

        cbShow = new JCheckBox("Tampilkan password");
        cbShow.setOpaque(false);
        cbShow.addActionListener(e -> {
            if (cbShow.isSelected()) txtPassword.setEchoChar((char)0);
            else if (!String.valueOf(txtPassword.getPassword()).equals("Masukkan password..."))
                txtPassword.setEchoChar('•');
        });

        btnLogin = new JButton("Login");
        styleButton(btnLogin);
        btnLogin.setBackground(new Color(196, 225, 164));
        btnLogin.setForeground(Color.DARK_GRAY);
        btnLogin.addActionListener(e -> doLogin());

        btnRegister = new JButton("Daftar Akun Baru");
        styleButton(btnRegister);
        btnRegister.setBackground(new Color(196,225,164));
        btnRegister.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnRegister.setForeground(Color.BLACK);
        btnRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // ⬇️ penting: kirim service ke RegisterForm
        btnRegister.addActionListener(e -> { new RegisterForm(service).setVisible(true); dispose(); });

        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        java.util.function.Consumer<JComponent> center = c -> {
            c.setAlignmentX(Component.CENTER_ALIGNMENT);
            form.add(c);
        };

        Dimension FIELD_SIZE = new Dimension(280, 36);
        txtUsername.setMaximumSize(FIELD_SIZE);
        txtPassword.setMaximumSize(FIELD_SIZE);
        btnLogin.setMaximumSize(new Dimension(200, 40));
        btnRegister.setMaximumSize(new Dimension(200, 36));

        center.accept(iconUser);
        form.add(Box.createVerticalStrut(6));
        center.accept(signIn);
        form.add(Box.createVerticalStrut(14));
        center.accept(txtUsername);
        form.add(Box.createVerticalStrut(8));
        center.accept(txtPassword);
        form.add(Box.createVerticalStrut(4));
        center.accept(cbShow);
        form.add(Box.createVerticalStrut(12));
        center.accept(btnLogin);
        form.add(Box.createVerticalStrut(8));
        center.accept(btnRegister);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.gridy = 0;
        gc.weightx = 1; gc.weighty = 1;
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.NONE;
        right.add(form, gc);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, right);
        split.setDividerLocation(420);
        split.setDividerSize(0);
        add(split, BorderLayout.CENTER);
    }

    private void styleField(JTextField field) {
        field.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
    }

    private void styleButton(JButton btn) {
        btn.setBackground(new Color(0, 128, 96));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void addPlaceholder(JTextComponent field, String placeholder) {
        field.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                    if (field instanceof JPasswordField)
                        ((JPasswordField)field).setEchoChar('•');
                }
            }
            @Override public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                    if (field instanceof JPasswordField)
                        ((JPasswordField)field).setEchoChar((char)0);
                }
            }
        });
    }

    private void setIconFrom(String path, JLabel lbl, int w, int h) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(path)) {
            if (is != null) {
                BufferedImage img = ImageIO.read(is);
                lbl.setIcon(new ImageIcon(img.getScaledInstance(w, h, Image.SCALE_SMOOTH)));
                return;
            }
        } catch (Exception ignored) {}
        try {
            File f = new File(path);
            if (f.exists()) {
                BufferedImage img = ImageIO.read(f);
                lbl.setIcon(new ImageIcon(img.getScaledInstance(w, h, Image.SCALE_SMOOTH)));
            }
        } catch (Exception ignored) {}
    }
    // ================= END UI =================

    // ============ LOGIN dengan Service ============
    private void doLogin() {
        String u = txtUsername.getText().trim();
        String p = new String(txtPassword.getPassword()).trim();

        if (u.isEmpty() || p.isEmpty() || u.equals("Masukkan username...") || p.equals("Masukkan password...")) {
            JOptionPane.showMessageDialog(this, "Isi username dan password dengan benar.");
            return;
        }
        try {
            Service.AuthResult ar = service.loginAuth(u, p);
            if (ar == null) {
                JOptionPane.showMessageDialog(this, "Username atau password salah.");
                return;
            }
            JOptionPane.showMessageDialog(this, "Login berhasil!");
            if (ar.isAdmin) {
                new AdminDashboard(service, ar.nama).setVisible(true);
            } else {
                new MainDashboard(service, ar.nama, ar.id).setVisible(true);
            }
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + ex.getMessage());
        }
    }
}
