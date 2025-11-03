package Ui;

import GUI.RegisterForm;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class UiKit {

    /* ======================== PUBLIC API ======================== */

    // Overload tanpa parent (tetap mendukung pemakaian lama)
    public static void info  (String title, String message) { info  (null, title, message); }
    public static void warn  (String title, String message) { warn  (null, title, message); }
    public static void error (String title, String message) { error (null, title, message); }
    public static boolean confirm(String title, String message) { return confirm(null, title, message); }

    // Versi dengan parent (disarankan)
    public static void info(Component parent, String title, String message) {
        FancyDialog d = baseDialog(parent, title, message, Type.SUCCESS);
        d.addButtons(primaryButton("Tutup", d::dispose));
        d.open();
    }
    public static void warn(Component parent, String title, String message) {
        FancyDialog d = baseDialog(parent, title, message, Type.WARNING);
        d.addButtons(primaryButton("Tutup", d::dispose));
        d.open();
    }
    public static void error(Component parent, String title, String message) {
        FancyDialog d = baseDialog(parent, title, message, Type.ERROR);
        d.addButtons(primaryButton("Tutup", d::dispose));
        d.open();
    }
    public static boolean confirm(Component parent, String title, String message) {
        final boolean[] ok = {false};
        FancyDialog d = baseDialog(parent, title, message, Type.QUESTION);
        JButton bOk     = primaryButton("OK",   () -> { ok[0] = true;  d.dispose(); });
        JButton bCancel = neutralButton("Batal",        d::dispose);
        d.addButtons(bCancel, bOk);
        d.open();
        return ok[0];
    }

    /* ======================== BUTTON FACTORIES ======================== */

    /** Tombol utama: hijau, kotak sudut 10px */
    public static JButton primaryButton(String text, Runnable onClick) {
        return roundedButton(text,
                new Color(0x39A853),     // base
                new Color(0x2F8E46),     // hover
                Color.WHITE,
                10,
                onClick);
    }

    /** Tombol netral: abu, kotak sudut 10px */
    public static JButton neutralButton(String text, Runnable onClick) {
        return roundedButton(text,
                new Color(0xE5E7EB),
                new Color(0xD1D5DB),
                new Color(0x111827),
                10,
                onClick);
    }

    /** Implementasi tombol rounded rectangle */
    private static JButton roundedButton(String text,
                                         Color base, Color hover, Color fg,
                                         int arc,
                                         Runnable onClick) {
        JButton b = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color fill = getModel().isRollover() || getModel().isPressed() ? hover : base;
                g2.setColor(fill);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

                g2.setColor(fg);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int tx = (getWidth() - fm.stringWidth(getText())) / 2;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), tx, ty);

                g2.dispose();
            }
        };
        b.setOpaque(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setFont(b.getFont().deriveFont(Font.BOLD, 13f));
        b.setPreferredSize(new Dimension(104, 36));
        if (onClick != null) b.addActionListener(e -> onClick.run());
        return b;
    }

    public static void success(RegisterForm aThis, String pendaftaran, String pendaftaran_berhasil_Silakan_login) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /* ======================== SIMPLE FANCY DIALOG ======================== */

    private enum Type { SUCCESS, WARNING, ERROR, QUESTION }

    private static FancyDialog baseDialog(Component parent, String title, String message, Type type) {
        FancyDialog d = new FancyDialog(parent, title);
        d.setBody(makeBody(type, title, message));
        return d;
    }

    private static JComponent makeBody(Type type, String title, String message) {
        JPanel wrap = new JPanel(new BorderLayout(0, 12));
        wrap.setOpaque(false);
        wrap.setBorder(new EmptyBorder(8, 8, 0, 8));

        JPanel head = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        head.setOpaque(false);
        JLabel icon = new JLabel(circleIcon(type));
        JLabel ttl  = new JLabel(title);
        ttl.setFont(ttl.getFont().deriveFont(Font.BOLD, 16f));
        ttl.setForeground(new Color(0x111827));
        head.add(icon); head.add(ttl);

        JTextArea ta = new JTextArea(message);
        ta.setEditable(false);
        ta.setFocusable(false);
        ta.setOpaque(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setFont(ta.getFont().deriveFont(13f));
        ta.setForeground(new Color(0x374151));
        ta.setBorder(new EmptyBorder(4, 4, 4, 4));

        wrap.add(head, BorderLayout.NORTH);
        wrap.add(ta,   BorderLayout.CENTER);
        return wrap;
    }

    private static Icon circleIcon(Type t) {
        Color c;
        String sym;
        switch (t) {
            case SUCCESS -> { c = new Color(0x34A853); sym = "\u2713"; } // ✓
            case WARNING -> { c = new Color(0xF59E0B); sym = "!"; }
            case ERROR   -> { c = new Color(0xEF4444); sym = "\u2716"; } // ✖
            default      -> { c = new Color(0x3B82F6); sym = "?"; }
        }
        int size = 36, arc = size;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(c); g.fillRoundRect(0, 0, size, size, arc, arc);
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 20f));
        FontMetrics fm = g.getFontMetrics();
        int x = (size - fm.stringWidth(sym)) / 2;
        int y = (size + fm.getAscent() - fm.getDescent()) / 2;
        g.drawString(sym, x, y);
        g.dispose();
        return new ImageIcon(img);
    }

    /* -------- Lightweight dialog container dengan footer tombol -------- */

    private static class FancyDialog extends JDialog {
        private final JPanel body = new JPanel(new BorderLayout());
        private final JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        FancyDialog(Component parent, String title) {
            super(SwingUtilities.getWindowAncestor(parent), title, ModalityType.APPLICATION_MODAL);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());
            JPanel bg = new JPanel(new BorderLayout());
            bg.setBorder(new EmptyBorder(16,16,16,16));
            bg.setBackground(new Color(0xEDF3E3)); // hijau muda lembut

            body.setOpaque(false);
            footer.setOpaque(false);
            footer.setBorder(new EmptyBorder(16, 0, 0, 0));

            bg.add(body, BorderLayout.CENTER);
            bg.add(footer, BorderLayout.SOUTH);
            setContentPane(bg);
            setSize(520, 240);
            setLocationRelativeTo(parent);
        }

        void setBody(JComponent content) { body.removeAll(); body.add(content, BorderLayout.CENTER); }
        void addButtons(JButton... btns) { footer.removeAll(); for (JButton b: btns) footer.add(b); }
        void open() { setVisible(true); }
    }
}
