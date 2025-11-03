package GUI;

import GUI.MainDashboard.PlaceholderTextField;
import Service.Service;
import Model.Tiket;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import javax.swing.border.CompoundBorder;

/**
 * AdminDashboard (GUI) — tampilkan tabel tiket + CRUD (Tambah/Edit/Hapus).
 * Menggunakan Service untuk akses database.
 */
public class AdminDashboard extends JFrame {

    private final Service service;
    private final String namaAdmin;

    // UI
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    private JComboBox<String> cbModa;
    private JTextField txtCari;

    public AdminDashboard(Service service, String namaAdmin) {
        this.service = service;
        this.namaAdmin = namaAdmin;
        initUI();
        loadData();
    }

    // =============== UI ===============
    private void initUI() {
        // Palet
        Color BG   = new Color(0xC4E1A4);
        Color CARD = new Color(0xECECD5);
        Color ACC  = new Color(0xC4E1A4);
        Color ACC2 = new Color(0xA9C58C);
        Color TEXT = new Color(33,37,41);
        Color SUBT = new Color(108,117,125);

        setTitle("Dashboard — Admin");
        setSize(1100, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(16,16));
        getContentPane().setBackground(BG);
        ((JComponent)getContentPane()).setBorder(new EmptyBorder(16,16,16,16));

        Font BASE = safeFont();
        Font H1 = BASE.deriveFont(Font.BOLD, 20f);
        Font H2 = BASE.deriveFont(Font.BOLD, 14f);
        Font BODY = BASE.deriveFont(Font.PLAIN, 13f);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CARD);
        header.setBorder(new CompoundBorder(
                new MatteBorder(0,0,1,0,new Color(235,238,241)),
                new EmptyBorder(16,16,16,16)
        ));

        JLabel lblHalo = new JLabel("Halo, " + namaAdmin);
        lblHalo.setFont(H1);
        lblHalo.setForeground(TEXT);
        header.add(lblHalo, BorderLayout.WEST);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT,12,0));
        actions.setOpaque(false);

        JButton btnKembali = makeBtn("Kembali", ACC, ACC2);
        JButton btnTambah  = makeBtn("Menambahkan Tiket", ACC, ACC2);
        JButton btnEdit    = makeBtn("Edit Tiket", ACC, ACC2);
        JButton btnHapus   = makeBtn("Hapus Tiket", ACC, ACC2);

        actions.add(btnKembali);
        actions.add(btnTambah);
        actions.add(btnEdit);
        actions.add(btnHapus);

        header.add(actions, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // Filter bar
        JPanel filter = new JPanel(new GridBagLayout());
        filter.setBackground(CARD);
        filter.setBorder(new EmptyBorder(14,16,6,16));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(0,0,0,12);
        gc.gridy = 0;

        JLabel lModa = new JLabel("Pilih Transportasi");
        lModa.setFont(H2); lModa.setForeground(SUBT);

        cbModa = new JComboBox<>(new String[]{"Bus", "Kereta Api", "Travel"});
        cbModa.setFont(BODY);
        cbModa.setPreferredSize(new Dimension(240,34));

        JLabel lCari = new JLabel("Cari");
        lCari.setFont(H2); lCari.setForeground(SUBT);

        txtCari = new PlaceholderTextField("Ketik rute, tanggal, atau jam…");
        txtCari.setFont(BODY);
        txtCari.setPreferredSize(new Dimension(280,34));

        gc.gridx=0; filter.add(lModa, gc);
        gc.gridx=1; filter.add(cbModa, gc);
        gc.weightx=1; gc.gridx=2; filter.add(Box.createHorizontalGlue(), gc);
        gc.weightx=0; gc.gridx=3; filter.add(lCari, gc);
        gc.gridx=4; filter.add(txtCari, gc);

        // Table
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(CARD);
        tableCard.setBorder(new EmptyBorder(0,16,16,16));

        JLabel title = new JLabel("Data Mengenai Tiket Transportasi");
        title.setFont(H2); title.setForeground(TEXT);
        title.setBorder(new EmptyBorder(10,0,8,0));

        model = new DefaultTableModel(
            new String[]{
                "ID Tiket", "Tanggal", "Berangkat", "Tiba",
                "Nama Rute", "Nama Transportasi", "Harga (Rp)"
            }, 0
        ){ @Override public boolean isCellEditable(int r,int c){ return false; }};

        table = new JTable(model);
        table.setFont(BODY);
        table.setRowHeight(28);
        table.setFillsViewportHeight(true);
        table.setShowGrid(true);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        table.setIntercellSpacing(new Dimension(1,1));
        table.setGridColor(new Color(210,214,216));
        
        // Matikan auto-resize agar ukuran kolom tidak berubah otomatis
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JTableHeader th = table.getTableHeader();
        th.setReorderingAllowed(false);
        th.setFont(BASE.deriveFont(Font.BOLD,13f));
        th.setForeground(TEXT);
        ((DefaultTableCellRenderer)th.getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        // Kunci lebar kolom agar stabil
        lockColumnWidth("ID Tiket", 80);
        lockColumnWidth("Tanggal", 120);
        lockColumnWidth("Berangkat", 95);
        lockColumnWidth("Tiba", 95);
        lockColumnWidth("Harga (Rp)", 120);
        
        setPreferredOnly("Nama Rute",           280);   // minimal nyaman
        setPreferredOnly("Nama Transportasi",   280);

        // center alignment
        TableCellRenderer center = new StripeCellRenderer(SwingConstants.CENTER);
        setColRenderer("ID Tiket", center);
        setColRenderer("Tanggal", center);
        setColRenderer("Berangkat", center);
        setColRenderer("Tiba", center);
        setColRenderer("Nama Rute", center);
        setColRenderer("Nama Transportasi", center);
        setColRenderer("Harga (Rp)", center);
        table.setDefaultRenderer(Object.class, new StripeCellRenderer(SwingConstants.LEFT));

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new MatteBorder(1,1,1,1,new Color(231,234,238)));
        sp.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        sp.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        sp.getViewport().setBackground(Color.WHITE);
        sp.setBackground(CARD);

        tableCard.add(title, BorderLayout.NORTH);
        tableCard.add(sp, BorderLayout.CENTER);

        JPanel centerWrap = new JPanel(new BorderLayout());
        centerWrap.setOpaque(false);
        centerWrap.add(filter, BorderLayout.NORTH);
        centerWrap.add(tableCard, BorderLayout.CENTER);
        add(centerWrap, BorderLayout.CENTER);

        // events
        cbModa.addActionListener(e -> loadData());
        txtCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            void apply() {
                String s = txtCari.getText().trim();
                if (s.isEmpty()) sorter.setRowFilter(null);
                else sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(s)));
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e){ apply(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e){ apply(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e){ apply(); }
        });

        btnKembali.addActionListener(e -> {
            new GUI.modernlogin().setVisible(true); // ganti sesuai kelasmu
            dispose();
        });

        btnTambah.addActionListener(e -> onTambah());
        btnEdit.addActionListener(e -> onEdit());
        btnHapus.addActionListener(e -> onHapus());
        
        // redistribusi saat panel di-resize
        table.addComponentListener(new ComponentAdapter() {
            @Override public void componentResized(ComponentEvent e) {
                SwingUtilities.invokeLater(() -> {
                    try {
                        int total = table.getParent().getWidth(); // viewport
                        int fixed = 0;
                        for (String n : new String[]{"ID Tiket","Tanggal","Berangkat","Tiba","Harga (Rp)"}) {
                            fixed += table.getColumn(n).getWidth();
                        }
                        int borders = 24; // kira-kira padding/scrollbar
                        int sisa = Math.max(0, total - fixed - borders);
                        int each = Math.max(220, sisa / 2);
                        setPreferredOnly("Nama Rute", each);
                        setPreferredOnly("Nama Transportasi", each);
                    } catch (Exception ignore) {}
                });
            }
        });
    }

    // =============== DATA ===============
    private void loadData() {
        model.setRowCount(0);
        String jenisDipilih = (String) cbModa.getSelectedItem();

        List<Tiket> list;
        try {
            list = service.getSemuaTiket();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + ex.getMessage());
            return;
        }

        for (Tiket t : list) {
            if (t.getJenisTransportasi()!=null &&
                !t.getJenisTransportasi().equalsIgnoreCase(jenisDipilih)) continue;

            // pecah "07:00 22/10/2025" → jam/tanggal
            String tanggal = "-", berangkat="-";
            String js = t.getJadwalStr();
            if (js != null) {
                int sp = js.indexOf(' ');
                if (sp>0) { berangkat = js.substring(0, sp); tanggal = js.substring(sp+1); }
                else { berangkat = js; }
            }

            String rute = (safe(t.getTitikAwal()) + " - " + safe(t.getTitikAkhir())).trim();
            model.addRow(new Object[]{
                    t.getId(),
                    toTanggalIndo(tanggal),
                    berangkat,
                    safe(t.getJamTibaStr()),
                    rute,
                    safe(t.getNamaTransportasi()),
                    toRupiah(t.getHarga())
            });
        }
    }

    // =============== ACTIONS ===============
    private void onTambah() {
        JTextField tfAsal = new JTextField();
        JTextField tfTujuan = new JTextField();
        JComboBox<String> cbJenis = new JComboBox<>(new String[]{"Bus","Kereta Api","Travel"});
        JTextField tfNamaTrans = new JTextField();
        JTextField tfHarga = new JTextField();
        JTextField tfBerangkat = new JTextField(); // HH:mm
        JTextField tfTiba = new JTextField();      // HH:mm
        JTextField tfTanggal = new JTextField();   // dd/MM/yyyy
        tfTanggal.setToolTipText("Contoh: 14/12/2025 (boleh juga 14-12-2025 atau 14 12 2025)");


        Object[] form = {
                "Asal:", tfAsal,
                "Tujuan:", tfTujuan,
                "Jenis:", cbJenis,
                "Nama Transportasi:", tfNamaTrans,
                "Harga (angka):", tfHarga,
                "Jam Berangkat (HH:mm):", tfBerangkat,
                "Jam Tiba (HH:mm):", tfTiba,
                "Tanggal (dd/MM/yyyy):", tfTanggal
        };

        if (JOptionPane.showConfirmDialog(this, form, "Tambah Tiket",
                JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;

        try {
            int harga = Integer.parseInt(tfHarga.getText().trim());
            if (harga <= 0) throw new NumberFormatException();

            Timestamp berangkat = combineToTimestamp(normDate(tfTanggal.getText()), tfBerangkat.getText().trim());
            Timestamp tiba      = combineToTimestamp(normDate(tfTanggal.getText()), tfTiba.getText().trim());
            java.sql.Date tgl   = parseSqlDate(normDate(tfTanggal.getText()));


            boolean ok = service.tambahTiket(
                    tfAsal.getText().trim(),
                    tfTujuan.getText().trim(),
                    Objects.toString(cbJenis.getSelectedItem(),""),
                    tfNamaTrans.getText().trim(),
                    harga,
                    berangkat, tiba, tgl
            );
            if (ok) {
                JOptionPane.showMessageDialog(this, "Tiket berhasil ditambahkan.");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan tiket.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Input tidak valid: " + ex.getMessage());
        }
    }

    private void onEdit() {
        int viewRow = table.getSelectedRow();
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(this, "Pilih baris tiket dulu.");
            return;
        }
        int row = table.convertRowIndexToModel(viewRow);
        int idTiket = Integer.parseInt(model.getValueAt(row,0).toString());

        JTextField tfAsal = new JTextField();
        JTextField tfTujuan = new JTextField();
        JComboBox<String> cbJenis = new JComboBox<>(new String[]{"Bus","Kereta Api","Travel"});
        JTextField tfNamaTrans = new JTextField();
        JTextField tfHarga = new JTextField(model.getValueAt(row,5).toString().replaceAll("[^0-9]",""));
        JTextField tfBerangkat = new JTextField(model.getValueAt(row,2).toString());
        JTextField tfTiba = new JTextField(model.getValueAt(row,3).toString());
        JTextField tfTanggal = new JTextField(fromTanggalIndo(model.getValueAt(row,1).toString()));
        tfTanggal.setToolTipText("Contoh: 14/12/2025 (boleh juga 14-12-2025 atau 14 12 2025)");

        Object[] form = {
                "Harga (angka):", tfHarga,
                "Asal:", tfAsal,
                "Tujuan:", tfTujuan,
                "Jenis:", cbJenis,
                "Nama Transportasi:", tfNamaTrans,
                "Jam Berangkat (HH:mm):", tfBerangkat,
                "Jam Tiba (HH:mm):", tfTiba,
                "Tanggal (dd/MM/yyyy):", tfTanggal
        };
        if (JOptionPane.showConfirmDialog(this, form, "Edit Tiket",
                JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;

        try {
            int harga = Integer.parseInt(tfHarga.getText().trim());
            if (harga <= 0) throw new NumberFormatException();

            Timestamp berangkat = combineToTimestamp(normDate(tfTanggal.getText()), tfBerangkat.getText().trim());
            Timestamp tiba      = combineToTimestamp(normDate(tfTanggal.getText()), tfTiba.getText().trim());
            java.sql.Date tgl   = parseSqlDate(normDate(tfTanggal.getText()));


            boolean ok = service.updateTiket(
                    idTiket,
                    harga,
                    tfAsal.getText().trim(),
                    tfTujuan.getText().trim(),
                    Objects.toString(cbJenis.getSelectedItem(),""),
                    tfNamaTrans.getText().trim(),
                    berangkat, tiba, tgl
            );
            if (ok) {
                JOptionPane.showMessageDialog(this, "Tiket berhasil diupdate.");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal update tiket.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Input tidak valid: " + ex.getMessage());
        }
    }

    private void onHapus() {
        int viewRow = table.getSelectedRow();
        if (viewRow < 0) { JOptionPane.showMessageDialog(this, "Pilih baris tiket dulu."); return; }
        int row = table.convertRowIndexToModel(viewRow);
        int id = Integer.parseInt(model.getValueAt(row,0).toString());

        if (JOptionPane.showConfirmDialog(this,
                "Yakin hapus tiket ID " + id + " ?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;

        boolean ok = service.hapusTiket(id);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Tiket dihapus.");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menghapus tiket.");
        }
    }

    // =============== Utils ===============
    private Font safeFont() {
        Font f = UIManager.getFont("Label.font");
        if (f == null) f = new JLabel().getFont();
        if (f == null) f = new Font("SansSerif", Font.PLAIN, 13);
        return f;
    }

    private JButton makeBtn(String text, Color base, Color hover) {
        JButton b = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Float sc = (Float) getClientProperty("scale");
                if (sc == null) sc = 1f;
                int w=getWidth(), h=getHeight();
                int dx = Math.round((w - w*sc)/2f), dy = Math.round((h - h*sc)/2f);
                g2.translate(dx,dy); g2.scale(sc,sc);
                ButtonModel m = getModel();
                Color bg = (m.isRollover()||m.isArmed())? hover : base;
                g2.setColor(bg); g2.fillRoundRect(0,0,getWidth(),getHeight(),12,12);
                g2.setColor(bg.darker()); g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,12,12);
                g2.setColor(Color.BLACK);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int tx=(getWidth()-fm.stringWidth(getText()))/2;
                int ty=(getHeight()+fm.getAscent()-fm.getDescent())/2;
                g2.drawString(getText(), tx, ty);
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

        final javax.swing.Timer animator = new javax.swing.Timer(15, null);
        animator.addActionListener(e -> {
            float cur = (Float) b.getClientProperty("scale");
            boolean pressed = Boolean.TRUE.equals(b.getClientProperty("pressed"));
            float target = pressed ? 0.92f : 1f;
            float next = cur + (target - cur) * 0.25f;
            if (Math.abs(next-target) < 0.003f) { next = target; animator.stop(); }
            b.putClientProperty("scale", next); b.repaint();
        });
        b.getModel().addChangeListener(ev -> {
            boolean pressed = b.getModel().isArmed() && b.getModel().isPressed();
            b.putClientProperty("pressed", pressed);
            if (!animator.isRunning()) animator.start();
        });
        return b;
    }

    private void lockColumnWidth(String name, int w) {
    SwingUtilities.invokeLater(() -> {
        try {
            TableColumn col = table.getColumnModel().getColumn(
                    table.getColumnModel().getColumnIndex(name));
            col.setMinWidth(w);
            col.setPreferredWidth(w);
            col.setMaxWidth(w);
            col.setResizable(false);      // benar-benar fixed
        } catch (IllegalArgumentException e) {
            System.err.println("Kolom tidak ditemukan: " + name);
        }
    });
}

// fleksibel: ada minimum & preferred, tapi TIDAK dibatasi max
private void setPreferredOnly(String name, int preferred) {
    SwingUtilities.invokeLater(() -> {
        try {
            TableColumn col = table.getColumnModel().getColumn(
                    table.getColumnModel().getColumnIndex(name));
            col.setMinWidth(Math.min(180, preferred));   // cegah terlalu kecil
            col.setPreferredWidth(preferred);
            col.setMaxWidth(Integer.MAX_VALUE);          // bebas melebar
            col.setResizable(true);
        } catch (IllegalArgumentException e) {
            System.err.println("Kolom tidak ditemukan: " + name);
        }
    });
}



    private static class StripeCellRenderer extends DefaultTableCellRenderer {
        private final int align;
        StripeCellRenderer(int align) { this.align = align; }
        @Override public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setHorizontalAlignment(align);
            if (!isSelected) {
                Color even = new Color(252,253,255);
                Color odd  = Color.WHITE;
                c.setBackground((row % 2 == 0) ? even : odd);
            }
            setBorder(new MatteBorder(0,0,1,0,new Color(240,243,246)));
            return c;
        }
    }

    private static class ModernScrollBarUI extends BasicScrollBarUI {
        private final Color THUMB = new Color(0xB7CF99);
        private final Color THUMB_HOVER = new Color(0xA9C58C);
        private final Color TRACK = new Color(0xECECD5);
        @Override protected void configureScrollBarColors(){ thumbColor=THUMB; trackColor=TRACK; }
        @Override protected Dimension getMaximumThumbSize(){ return new Dimension(9999,9999); }
        @Override protected Dimension getMinimumThumbSize(){
            return (scrollbar.getOrientation()==JScrollBar.VERTICAL) ? new Dimension(10,40) : new Dimension(40,10);
        }
        @Override protected JButton createDecreaseButton(int o){ JButton b=new JButton(); b.setPreferredSize(new Dimension(0,0)); b.setVisible(false); return b; }
        @Override protected JButton createIncreaseButton(int o){ JButton b=new JButton(); b.setPreferredSize(new Dimension(0,0)); b.setVisible(false); return b; }
        @Override protected void paintThumb(Graphics g, JComponent c, Rectangle r){
            Graphics2D g2=(Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(isThumbRollover()? THUMB_HOVER: THUMB);
            g2.fillRoundRect(r.x,r.y,r.width,r.height,10,10); g2.dispose();
        }
        @Override protected void paintTrack(Graphics g, JComponent c, Rectangle r){
            Graphics2D g2=(Graphics2D)g.create(); g2.setColor(TRACK); g2.fillRect(r.x,r.y,r.width,r.height); g2.dispose();
        }
    }
    
    private void goBackToLogin() {
    String[] candidates = {
        "GUI.modernlogin",
        "GUI.LoginForm",
        "com.GUI_Login.login.modernlogin",
        "com.GUI_Login.login.LoginForm"
    };
    for (String fqn : candidates) {
        try {
            Class<?> cls = Class.forName(fqn);
            if (javax.swing.JFrame.class.isAssignableFrom(cls)) {
                javax.swing.JFrame f = (javax.swing.JFrame) cls.getDeclaredConstructor().newInstance();
                f.setLocationRelativeTo(null);
                f.setVisible(true);
                return;
            }
        } catch (Throwable ignore) {}
    }
    JOptionPane.showMessageDialog(this,
        "Tidak menemukan form login (modernlogin/LoginForm). Pastikan nama paket & kelasnya benar.",
        "Info", JOptionPane.INFORMATION_MESSAGE);
}


    private void setColWidth(String name, int width){
    SwingUtilities.invokeLater(() -> {
        TableColumnModel cm = table.getColumnModel();
        for (int i = 0; i < cm.getColumnCount(); i++) {
            Object hv = cm.getColumn(i).getHeaderValue();
            if (name.equals(String.valueOf(hv))) {
                cm.getColumn(i).setPreferredWidth(width);
                break;
            }
        }
    });
}

private void setColRenderer(String name, TableCellRenderer r){
    SwingUtilities.invokeLater(() -> {
        TableColumnModel cm = table.getColumnModel();
        for (int i = 0; i < cm.getColumnCount(); i++) {
            Object hv = cm.getColumn(i).getHeaderValue();
            if (name.equals(String.valueOf(hv))) {
                cm.getColumn(i).setCellRenderer(r);
                break;
            }
        }
    });
}


    // -------- helpers format/parsing --------
    private String safe(String s){ return (s==null || s.isBlank()) ? "-" : s; }

    private String toRupiah(int v) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("id","ID"));
        return nf.format(v).replace("Rp", "Rp ").trim();
    }
    
    private String normDate(String s) {
    if (s == null) return "";
    return s.trim()
            .replace('-', '/')
            .replace(' ', '/');   // hasil akhir dd/MM/yyyy
}

    private Timestamp combineToTimestamp(String ddMMyyyy, String HHmm) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        f.setLenient(false);
        Date d = f.parse(ddMMyyyy + " " + HHmm);
        return new Timestamp(d.getTime());
    }

    private java.sql.Date parseSqlDate(String ddMMyyyy) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        f.setLenient(false);
        Date d = f.parse(ddMMyyyy);
        return new java.sql.Date(d.getTime());
    }

    // "22 Okt 2025" -> "22/10/2025" dan sebaliknya untuk tampilan
    private String fromTanggalIndo(String t){
        try{
            Date d = new SimpleDateFormat("dd MMM yyyy", new Locale("id","ID")).parse(t);
            return new SimpleDateFormat("dd/MM/yyyy").format(d);
        }catch(Exception e){ return t; }
    }
    private String toTanggalIndo(String ddMMyyyy){
        try{
            Date d = new SimpleDateFormat("dd/MM/yyyy").parse(ddMMyyyy);
            return new SimpleDateFormat("dd MMM yyyy", new Locale("id","ID")).format(d);
        }catch(Exception e){ return ddMMyyyy; }
    }

    // =============== main test ===============
    public static void main(String[] args) {
        // Contoh penggunaan:
        // Connection conn = DB.getConn(); Service svc = new Service(conn);
        // new AdminDashboard(svc, "Admin 1").setVisible(true);
    }
}
