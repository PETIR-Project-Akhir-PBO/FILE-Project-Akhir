package GUI;

import Service.Service;
import Model.Tiket;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import Ui.UiKit;

public class MainDashboard extends JFrame {

    // ====== DEPENDENCY + IDENTITAS ======
    private final Service service;
    private final String namaPengguna;
    private final int penumpangId;

    // state
    private int currentSaldo = 0;

    // ====== UI ======
    private JLabel lblSaldo;
    private JComboBox<String> cbModa;
    private JTextField txtCari;

    private JPanel listPanel;
    private JScrollPane spCards;
    private final List<TicketCard> allCards = new ArrayList<>();
    
    private static final int CARD_W = 360; // lebar 1 kartu
    private static final int GAP    = 16;  // jarak antar kartu

    // ====== CTOR (cocok panggilan modernlogin) ======
    public MainDashboard(Service service, String namaPengguna, int penumpangId) {
        this.service = service;
        this.namaPengguna = namaPengguna;
        this.penumpangId = penumpangId;
        initUI();
        loadData();
        refreshSaldo();
    }

    // ====== Helpers ======
    private Font safeFont() {
        Font f = UIManager.getFont("Label.font");
        if (f == null) f = new JLabel().getFont();
        if (f == null) f = new Font("SansSerif", Font.PLAIN, 13);
        return f;
    }
    private String toRupiah(int v) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("id","ID"));
        return nf.format(v).replace("Rp", "Rp ").trim();
    }

    // ====== UI BUILD ======
    private void initUI() {
        // Palet
        Color BG   = new Color(0xC4E1A4);
        Color CARD = new Color(0xECECD5);
        Color ACCENT = new Color(0xC4E1A4);
        Color ACCENT_HOVER = new Color(0xA9C58C);
        Color TEXT = new Color(33, 37, 41);
        Color SUBTEXT = new Color(108, 117, 125);

        Font BASE = safeFont();
        Font H1 = BASE.deriveFont(Font.BOLD, 20f);
        Font H2 = BASE.deriveFont(Font.BOLD, 14f);
        Font BODY = BASE.deriveFont(Font.PLAIN, 13f);

        setTitle("Dashboard — Pengguna");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(16,16));
        getContentPane().setBackground(BG);
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(16,16,16,16));

        // ===== Header (sapaan + saldo + tombol) =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CARD);
        header.setBorder(new CompoundBorder(
                new MatteBorder(0,0,1,0,new Color(235,238,241)),
                new EmptyBorder(22,16,22,16)
        ));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        left.setOpaque(false);
        JLabel lblHalo = new JLabel("Halo, " + namaPengguna);
        lblHalo.setFont(H1);
        lblHalo.setForeground(TEXT);

        lblSaldo = new JLabel("Saldo: —");
        lblSaldo.setFont(safeFont().deriveFont(Font.BOLD, 14f));
        lblSaldo.setForeground(new Color(180, 60, 60));

        left.add(lblHalo);
        left.add(Box.createHorizontalStrut(18));
        left.add(lblSaldo);
        header.add(left, BorderLayout.WEST);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        actions.setOpaque(false);

        JButton btnTopUp = makePrimaryButton("Top Up", ACCENT, ACCENT_HOVER);
        btnTopUp.addActionListener(e -> {
            new TopUpDialog(
                    this, service, penumpangId,
                    this::refreshSaldo
            ).setVisible(true);
        });

        JButton btnKembali = makePrimaryButton("←  Kembali", ACCENT, ACCENT_HOVER);
        btnKembali.addActionListener(e -> {
            dispose();
            try {
                Class.forName("GUI.modernlogin")
                        .asSubclass(javax.swing.JFrame.class)
                        .getDeclaredConstructor().newInstance()
                        .setVisible(true);
            } catch (Throwable ignore) {
                JOptionPane.showMessageDialog(null,
                        "Tidak menemukan LoginForm. Pastikan class LoginForm tersedia.",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        actions.add(btnTopUp);
        actions.add(btnKembali);
        header.add(actions, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // ===== Filter bar =====
        JPanel filterBar = new JPanel(new GridBagLayout());
        filterBar.setBackground(CARD);
        filterBar.setBorder(new EmptyBorder(14,16,6,16));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(0,0,0,12);
        gc.gridy = 0;

        JLabel lblModa = new JLabel("Pilih Transportasi");
        lblModa.setFont(H2);
        lblModa.setForeground(SUBTEXT);

        cbModa = new JComboBox<>(new String[]{"Bus", "Kereta Api", "Travel"});
        cbModa.setFont(BODY);
        cbModa.setPreferredSize(new Dimension(240, 34));
        cbModa.setBackground(Color.WHITE);

        JLabel lblCari = new JLabel("Cari");
        lblCari.setFont(H2);
        lblCari.setForeground(SUBTEXT);

        txtCari = new PlaceholderTextField("Ketik rute, tanggal, atau jam…");
        txtCari.setFont(BODY);
        txtCari.setPreferredSize(new Dimension(280, 34));
        txtCari.setBackground(Color.WHITE);

        gc.gridx = 0; filterBar.add(lblModa, gc);
        gc.gridx = 1; filterBar.add(cbModa, gc);
        gc.weightx = 1; gc.gridx = 2; filterBar.add(Box.createHorizontalGlue(), gc);
        gc.weightx = 0; gc.gridx = 3; filterBar.add(lblCari, gc);
        gc.gridx = 4; filterBar.add(txtCari, gc);

        // ===== Card container =====
        // ===== Card container (responsif) =====
        listPanel = new JPanel();                           // kolom dihitung dinamis
        listPanel = new JPanel(new GridLayout(0, 3, 16, 16));
        listPanel.setOpaque(false);

        spCards = new JScrollPane(
                listPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        spCards.setBorder(new EmptyBorder(0,0,0,0));
        spCards.getViewport().setBackground(CARD);
        spCards.setBackground(CARD);
        spCards.getVerticalScrollBar().setUI(new ModernScrollBarUI());

        // relayout setiap viewport diresize (mis. jendela dipersempit/diperlebar)
        spCards.getViewport().addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override public void componentResized(java.awt.event.ComponentEvent e) {
                relayoutCards();
            }
        });


        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(CARD);
        tableCard.setBorder(new EmptyBorder(0,16,16,16));

        JLabel title = new JLabel("Jadwal & Tiket Transportasi");
        title.setFont(H2);
        title.setForeground(TEXT);
        title.setBorder(new EmptyBorder(10,0,8,0));

        tableCard.add(title, BorderLayout.NORTH);
        tableCard.add(spCards, BorderLayout.CENTER);

        JPanel centerWrap = new JPanel(new BorderLayout());
        centerWrap.setOpaque(false);
        centerWrap.add(filterBar, BorderLayout.NORTH);
        centerWrap.add(tableCard, BorderLayout.CENTER);
        add(centerWrap, BorderLayout.CENTER);

        // events
        cbModa.addActionListener(e -> loadData());
        txtCari.getDocument().addDocumentListener(new DocumentListener() {
            void apply() { filterCards(txtCari.getText().trim()); }
            public void insertUpdate(DocumentEvent e) { apply(); }
            public void removeUpdate(DocumentEvent e) { apply(); }
            public void changedUpdate(DocumentEvent e) { apply(); }
        });
    }

    // ====== Data ======
    private void refreshSaldo() {
        try {
            currentSaldo = service.getSaldo(penumpangId);
            lblSaldo.setText("Saldo: " + toRupiah(currentSaldo));
        } catch (Exception ex) {
            lblSaldo.setText("Saldo: —");
        }
    }

    private void loadData() {
        listPanel.removeAll();
        allCards.clear();

        String jenisDipilih = (String) cbModa.getSelectedItem();
        // Ambil semua tiket dari service, lalu filter di UI berdasar jenis
        List<Tiket> list;
        try {
            list = service.getSemuaTiket(); // asumsi tersedia; kalau ada getByJenis, boleh ganti
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + ex.getMessage());
            return;
        }

        for (Tiket t : list) {
            if (t.getJenisTransportasi() != null &&
                !t.getJenisTransportasi().equalsIgnoreCase(jenisDipilih)) continue;

            // ... di MainDashboard.loadData(), saat looping tiap Tiket t:
            String jadwal = (t.getJadwalStr() == null) ? "" : t.getJadwalStr();
            String jamBerangkat = "-", tanggal = "-";
            int sp = jadwal.indexOf(' ');
            if (sp > 0) {
                jamBerangkat = jadwal.substring(0, sp);
                tanggal      = jadwal.substring(sp + 1);
            }

            // ambil jam tiba dari model (hasil query di Service)
            String jamTiba = (t.getJamTibaStr() == null || t.getJamTibaStr().isBlank())
                    ? "-" : t.getJamTibaStr();

            // lalu kirim ke kartu:
            TicketCard card = new TicketCard(
                t.getId(),
                t.getTitikAwal() + " - " + t.getTitikAkhir(),
                t.getHarga(),
                tanggal, jamBerangkat, jamTiba,
                t.getTitikAwal(), t.getTitikAkhir(),
                (t.getJenisTransportasi() == null ? "" : t.getJenisTransportasi()) +
                    " | " + (t.getNamaTransportasi() == null ? "" : t.getNamaTransportasi())
            );

            allCards.add(card);
            listPanel.add(card);
            relayoutCards();
        }

        listPanel.revalidate();
        listPanel.repaint();

        filterCards(txtCari.getText().trim());
    }

    private void filterCards(String q) {
        listPanel.removeAll();
        String query = q.trim().toLowerCase();
        for (TicketCard tc : allCards) {
            if (tc.matches(query)) listPanel.add(tc);
        }
        listPanel.revalidate();
        listPanel.repaint();
        relayoutCards();
    }
    
    // Hitung jumlah kolom berdasarkan lebar viewport
private void relayoutCards() {
    if (spCards == null) return;
    int vw = spCards.getViewport().getWidth();
    if (vw <= 0) return;

    int usable = Math.max(0, vw - GAP);                // sisakan gap kecil
    int cols = Math.max(1, usable / (CARD_W + GAP));   // minimal 1 kolom

    java.awt.LayoutManager lm = listPanel.getLayout();
    if (lm instanceof GridLayout gl) {
        if (gl.getColumns() != cols) {
            listPanel.setLayout(new GridLayout(0, cols, GAP, GAP));
            listPanel.revalidate();
            listPanel.repaint();
        }
    } else {
        listPanel.setLayout(new GridLayout(0, cols, GAP, GAP));
        listPanel.revalidate();
        listPanel.repaint();
    }
}


    // ====== Button util ======
    private JButton makePrimaryButton(String text, Color base, Color hover) {
        JButton b = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Float scale = (Float) getClientProperty("scale");
                if (scale == null) scale = 1f;
                int w = getWidth(), h = getHeight();
                int dx = Math.round((w - w * scale) / 2f);
                int dy = Math.round((h - h * scale) / 2f);
                g2.translate(dx, dy);
                g2.scale(scale, scale);
                ButtonModel m = getModel();
                Color bg = (m.isRollover() || m.isArmed()) ? hover : base;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(bg.darker());
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 12, 12);
                g2.setColor(Color.BLACK);
                FontMetrics fm = g2.getFontMetrics(getFont());
                String s = getText();
                int tx = (getWidth() - fm.stringWidth(s)) / 2;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.setFont(getFont());
                g2.drawString(s, tx, ty);
                g2.dispose();
            }
        };
        b.putClientProperty("scale", 1f);
        b.setRolloverEnabled(true);
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setFont(safeFont().deriveFont(Font.BOLD, 13f));
        b.setForeground(Color.BLACK);
        b.setMargin(new Insets(10,16,10,16));
        final Timer animator = new Timer(15, null);
        animator.addActionListener(e -> {
            float current = (Float) b.getClientProperty("scale");
            boolean pressed = Boolean.TRUE.equals(b.getClientProperty("pressed"));
            float target = pressed ? 0.92f : 1.0f;
            float next = current + (target - current) * 0.25f;
            if (Math.abs(next - target) < 0.003f) {
                next = target;
                animator.stop();
            }
            b.putClientProperty("scale", next);
            b.repaint();
        });
        b.getModel().addChangeListener(ev -> {
            boolean pressed = b.getModel().isArmed() && b.getModel().isPressed();
            b.putClientProperty("pressed", pressed);
            if (!animator.isRunning()) animator.start();
        });
        return b;
    }

    // ====== Placeholder field ======
    static class PlaceholderTextField extends JTextField {
        private final String placeholder;
        PlaceholderTextField(String placeholder) { this.placeholder = placeholder; }
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getText().isEmpty() && !isFocusOwner()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                Font f = getFont();
                if (f == null) f = UIManager.getFont("Label.font");
                if (f == null) f = new Font("SansSerif", Font.PLAIN, 13);
                g2.setColor(new Color(150,160,170));
                g2.setFont(f.deriveFont(Font.ITALIC));
                Insets ins = getInsets();
                int y = getHeight() / 2 + g2.getFontMetrics().getAscent() / 2 - 3;
                g2.drawString(placeholder, ins.left + 4, y);
                g2.dispose();
            }
        }
    }

    // ====== Scrollbar ======
    static class ModernScrollBarUI extends BasicScrollBarUI {
        private final Color THUMB = new Color(0xB7CF99);
        private final Color THUMB_HOVER = new Color(0xA9C58C);
        private final Color TRACK = new Color(0xECECD5);
        @Override protected void configureScrollBarColors() { thumbColor = THUMB; trackColor = TRACK; }
        @Override protected Dimension getMaximumThumbSize() { return new Dimension(9999, 9999); }
        @Override protected Dimension getMinimumThumbSize() {
            return (scrollbar.getOrientation() == JScrollBar.VERTICAL) ?
                    new Dimension(10, 40) : new Dimension(40, 10);
        }
        @Override protected JButton createDecreaseButton(int o) { JButton b = new JButton(); b.setPreferredSize(new Dimension(0,0)); b.setVisible(false); return b; }
        @Override protected JButton createIncreaseButton(int o) { JButton b = new JButton(); b.setPreferredSize(new Dimension(0,0)); b.setVisible(false); return b; }
        @Override protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(isThumbRollover() ? THUMB_HOVER : THUMB);
            g2.fillRoundRect(r.x, r.y, r.width, r.height, 10, 10);
            g2.dispose();
        }
        @Override protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(TRACK);
            g2.fillRect(r.x, r.y, r.width, r.height);
            g2.dispose();
        }
    }

    // ====== Kartu ======
    private class TicketCard extends JPanel {
        private final String fullTextForFilter;
        final int idTiketInt, hargaInt;

        TicketCard(int idTiket,
                   String namaRute, int hargaInt,
                   String tanggal, String jamBerangkat, String jamTiba,
                   String titikAwal, String titikAkhir, String namaTransport) {

            this.idTiketInt = idTiket;
            this.hargaInt = hargaInt;

            setLayout(new BorderLayout());
            setBackground(Color.WHITE);
            setBorder(new CompoundBorder(
                    new LineBorder(new Color(0xDADFE3), 1, true),
                    new EmptyBorder(8, 10, 8, 10)
            ));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JPanel head = new JPanel(new BorderLayout());
            head.setOpaque(false);

            JLabel lblRute = new JLabel(namaRute);
            lblRute.setFont(safeFont().deriveFont(Font.BOLD, 14f));
            lblRute.setForeground(Color.BLACK);

            JLabel lblHarga = new JLabel(toRupiah(hargaInt));
            lblHarga.setFont(safeFont().deriveFont(Font.BOLD, 14f));
            lblHarga.setForeground(new Color(200, 0, 0));
            lblHarga.setHorizontalAlignment(SwingConstants.RIGHT);

            head.add(lblRute, BorderLayout.WEST);
            head.add(lblHarga, BorderLayout.EAST);

            JPanel body = new JPanel(new GridLayout(0,2,12,6));
            body.setOpaque(false);
            body.setBorder(new EmptyBorder(6,0,0,0));
            body.add(makePair("Jam berangkat", jamBerangkat));
            body.add(makePair("Jam tiba", jamTiba));
            body.add(makePair("Titik awal", titikAwal));
            body.add(makePair("Titik Akhir", titikAkhir));
            body.add(makePair("Tanggal", tanggal));
            body.add(makePair("Nama transportasi", namaTransport));

            add(head, BorderLayout.NORTH);
            add(body, BorderLayout.CENTER);

            fullTextForFilter = (idTiketInt + " " + namaRute + " " + toRupiah(hargaInt) + " " +
                    tanggal + " " + jamBerangkat + " " + jamTiba + " " +
                    titikAwal + " " + titikAkhir + " " + namaTransport).toLowerCase();

            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                    new ConfirmBeliDialog(
                            MainDashboard.this, service, penumpangId, idTiketInt,
                            MainDashboard.this::refreshSaldo
                    ).setVisible(true);
                }
            });

            setPreferredSize(new Dimension(280, 160));
            setMinimumSize(new Dimension(260, 150));
            setMaximumSize(new Dimension(320, 170));

        }

        private JPanel makePair(String label, String value) {
            JPanel p = new JPanel(new GridLayout(2,1));
            p.setOpaque(false);
            JLabel l = new JLabel(label);
            l.setFont(safeFont().deriveFont(Font.PLAIN, 12f));
            l.setForeground(new Color(108,117,125));
            JLabel v = new JLabel(value);
            v.setFont(safeFont().deriveFont(Font.BOLD, 13f));
            v.setForeground(new Color(33,37,41));
            p.add(l); p.add(v);
            return p;
        }

        boolean matches(String query) {
            if (query == null || query.isBlank()) return true;
            Pattern p = Pattern.compile(Pattern.quote(query), Pattern.CASE_INSENSITIVE);
            return p.matcher(fullTextForFilter).find();
        }
    }

    // ====== Main test (opsional) ======
    public static void main(String[] args) {
        // Dummy untuk test manual jika perlu:
        // new MainDashboard(new Service(), "Cristiano Ronaldo", 1).setVisible(true);
    }
}
