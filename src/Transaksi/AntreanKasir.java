package Transaksi;

import Koneksi.Koneksi;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class AntreanKasir extends JFrame {

    Connection con;
    Statement st;
    ResultSet rs;

    JTable tblplgn;
    JTextField tcari;
    JButton bcari, brefresh, bkeluar;
    DefaultTableModel tabmode;

    Color bgUtama = new Color(236, 242, 248);
    Color biruHeader = new Color(8, 93, 158);
    Color biruGelap = new Color(33, 53, 85);
    Color biruBtn = new Color(33, 150, 243);
    Color hijauBtn = new Color(46, 204, 113);
    Color merahBtn = new Color(231, 76, 60);
    Color teksGelap = new Color(44, 62, 80);

    public AntreanKasir() {

        con = Koneksi.getKoneksi();

        setTitle("DATA ANTREAN KASIR - RUMAH SAKIT UNINDRA");
        setSize(1020, 630);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(bgUtama);

        // ================= HEADER =================
        JPanel header = new JPanel();
        header.setBackground(biruHeader);
        header.setPreferredSize(new Dimension(getWidth(), 90));
        header.setLayout(new BorderLayout());

        JLabel judul = new JLabel("DATA ANTREAN KASIR / PEMBAYARAN", SwingConstants.CENTER);
        judul.setForeground(Color.WHITE);
        judul.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JLabel subjudul = new JLabel("Rumah Sakit Unindra", SwingConstants.CENTER);
        subjudul.setForeground(new Color(220, 240, 255));
        subjudul.setFont(new Font("Segoe UI", Font.PLAIN, 25));

        JPanel tengahHeader = new JPanel();
        tengahHeader.setOpaque(false);
        tengahHeader.setLayout(new BorderLayout());
        tengahHeader.add(judul, BorderLayout.CENTER);
        tengahHeader.add(subjudul, BorderLayout.SOUTH);

        header.add(tengahHeader, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // ================= PANEL UTAMA =================
        JPanel tengah = new JPanel();
        tengah.setLayout(null);
        tengah.setBackground(bgUtama);

        JPanel panelCari = new JPanel();
        panelCari.setLayout(null);
        panelCari.setBackground(Color.WHITE);
        panelCari.setBounds(20, 18, 965, 78);
        panelCari.setBorder(BorderFactory.createLineBorder(new Color(190, 200, 210), 1));
        tengah.add(panelCari);

        JLabel lcari = new JLabel("Cari Data");
        lcari.setBounds(15, 23, 100, 30);
        lcari.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lcari.setForeground(teksGelap);
        panelCari.add(lcari);

        tcari = new JTextField();
        tcari.setBounds(120, 22, 280, 34);
        tcari.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tcari.setForeground(teksGelap);
        tcari.setBackground(Color.WHITE);
        tcari.setCaretColor(teksGelap);
        tcari.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(170, 180, 190), 1),
                new EmptyBorder(4, 10, 4, 10)
        ));
        panelCari.add(tcari);

        bcari = new JButton("Cari");
        bcari.setBounds(425, 22, 110, 34);
        styleButton(bcari, biruBtn);

        brefresh = new JButton("Refresh");
        brefresh.setBounds(548, 22, 110, 34);
        styleButton(brefresh, hijauBtn);

        bkeluar = new JButton("Keluar");
        bkeluar.setBounds(671, 22, 110, 34);
        styleButton(bkeluar, merahBtn);

        panelCari.add(bcari);
        panelCari.add(brefresh);
        panelCari.add(bkeluar);

        // ================= TABEL =================
        tblplgn = new JTable();
        tblplgn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblplgn.setRowHeight(30);
        tblplgn.setBackground(Color.WHITE);
        tblplgn.setForeground(teksGelap);
        tblplgn.setGridColor(new Color(220, 226, 232));
        tblplgn.setSelectionBackground(new Color(187, 222, 251));
        tblplgn.setSelectionForeground(Color.BLACK);
        tblplgn.setShowGrid(true);

        JScrollPane scroll = new JScrollPane(tblplgn);
        scroll.setBounds(20, 108, 965, 445);
        scroll.setBorder(BorderFactory.createLineBorder(biruHeader, 2));
        scroll.getViewport().setBackground(Color.WHITE);

        tengah.add(scroll);
        add(tengah, BorderLayout.CENTER);

        // ================= EVENT =================
        bcari.addActionListener(e -> cariData());

        brefresh.addActionListener(e -> {
            tcari.setText("");
            tampilData();
        });

        bkeluar.addActionListener(e -> dispose());

        tcari.addActionListener(e -> cariData());

        tblplgn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pilihData();
            }
        });

        tampilData();
    }

    // ================= STYLE BUTTON =================
    private void styleButton(final JButton btn, final Color warna) {
        btn.setUI(new BasicButtonUI());
        btn.setBackground(warna);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(true);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(warna.darker(), 1));
    }

    // ================= STYLE TABEL =================
    private void styleTabel() {
        JTableHeader header = tblplgn.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 36));
        header.setReorderingAllowed(false);
        header.setBackground(biruGelap);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setOpaque(true);
        headerRenderer.setBackground(biruGelap);
        headerRenderer.setForeground(Color.WHITE);
        headerRenderer.setFont(new Font("Segoe UI", Font.BOLD, 13));
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        headerRenderer.setBorder(BorderFactory.createLineBorder(new Color(60, 80, 110)));

        for (int i = 0; i < tblplgn.getColumnCount(); i++) {
            tblplgn.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                java.awt.Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                setBorder(new EmptyBorder(0, 8, 0, 8));
                setHorizontalAlignment(SwingConstants.LEFT);

                if (isSelected) {
                    c.setBackground(new Color(187, 222, 251));
                    c.setForeground(Color.BLACK);
                } else {
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(new Color(245, 249, 252));
                    }
                    c.setForeground(teksGelap);
                }

                return c;
            }
        };

        for (int i = 0; i < tblplgn.getColumnCount(); i++) {
            tblplgn.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
    }

    // ================= TAMPIL DATA =================
    private void tampilData() {
        Object[] baris = {
            "ID Resep",
            "ID Registrasi",
            "Nama Pasien",
            "Poli"
        };

        tabmode = new DefaultTableModel(null, baris);
        tblplgn.setModel(tabmode);

        try {
            String sql =
                "SELECT f.id_resep, f.id_registrasi, f.nama_pasien, p.poli " +
                "FROM farmasi f " +
                "LEFT JOIN pendaftaran_pasien p ON f.id_registrasi = p.id_registrasi " +
                "ORDER BY f.id_resep ASC";

            st = con.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                String[] data = {
                    rs.getString("id_resep"),
                    rs.getString("id_registrasi"),
                    rs.getString("nama_pasien"),
                    rs.getString("poli")
                };
                tabmode.addRow(data);
            }

            styleTabel();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // ================= CARI DATA =================
    private void cariData() {
        Object[] baris = {
            "ID Resep",
            "ID Registrasi",
            "Nama Pasien",
            "Poli"
        };

        tabmode = new DefaultTableModel(null, baris);
        tblplgn.setModel(tabmode);

        try {
            String sql =
                "SELECT f.id_resep, f.id_registrasi, f.nama_pasien, p.poli " +
                "FROM farmasi f " +
                "LEFT JOIN pendaftaran_pasien p ON f.id_registrasi = p.id_registrasi " +
                "WHERE f.nama_pasien LIKE '%" + tcari.getText() + "%' " +
                "OR f.id_resep LIKE '%" + tcari.getText() + "%' " +
                "OR f.id_registrasi LIKE '%" + tcari.getText() + "%' " +
                "OR p.poli LIKE '%" + tcari.getText() + "%' " +
                "ORDER BY f.id_resep ASC";

            st = con.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                String[] data = {
                    rs.getString("id_resep"),
                    rs.getString("id_registrasi"),
                    rs.getString("nama_pasien"),
                    rs.getString("poli")
                };
                tabmode.addRow(data);
            }

            styleTabel();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // ================= PILIH DATA =================
    private void pilihData() {
        int baris = tblplgn.getSelectedRow();

        if (baris == -1) {
            return;
        }

        String id = tblplgn.getValueAt(baris, 0).toString();
        String nama = tblplgn.getValueAt(baris, 2).toString();

        JOptionPane.showMessageDialog(
            null,
            "Data Kasir Dipilih : " + nama +
            "\nID Resep : " + id
        );
    }

    // ================= MAIN =================
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
        }

        new AntreanKasir().setVisible(true);
    }
}