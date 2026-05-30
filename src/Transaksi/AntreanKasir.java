package Transaksi;

import Koneksi.Koneksi;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AntreanKasir extends JFrame {

    Connection con;
    Statement st;
    ResultSet rs;

    JTable tblplgn;
    JTextField tcari;
    JButton bcari, brefresh, bkeluar;

    DefaultTableModel tabmode;

    public AntreanKasir() {

        setTitle("ANTREAN KASIR - RUMAH SAKIT UNINDRA");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        con = Koneksi.getKoneksi();

        // ================= HEADER =================
        JPanel header = new JPanel();
        header.setBackground(new Color(0, 102, 153));

        JLabel judul = new JLabel("ANTREAN KASIR RUMAH SAKIT UNINDRA");
        judul.setForeground(Color.WHITE);
        judul.setFont(new Font("Segoe UI", Font.BOLD, 24));

        header.add(judul);
        add(header, BorderLayout.NORTH);

        // ================= CENTER =================
        JPanel tengah = new JPanel();
        tengah.setLayout(null);
        tengah.setBackground(Color.WHITE);

        JLabel lcari = new JLabel("Cari Pasien");
        lcari.setBounds(20, 20, 120, 30);
        lcari.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tengah.add(lcari);

        tcari = new JTextField();
        tcari.setBounds(120, 20, 250, 30);
        tcari.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tengah.add(tcari);

        bcari = new JButton("Cari");
        bcari.setBounds(390, 20, 100, 30);
        bcari.setBackground(new Color(0, 153, 204));
        bcari.setForeground(Color.WHITE);
        tengah.add(bcari);

        brefresh = new JButton("Refresh");
        brefresh.setBounds(500, 20, 100, 30);
        brefresh.setBackground(new Color(0, 153, 102));
        brefresh.setForeground(Color.WHITE);
        tengah.add(brefresh);

        bkeluar = new JButton("Keluar");
        bkeluar.setBounds(610, 20, 100, 30);
        bkeluar.setBackground(Color.RED);
        bkeluar.setForeground(Color.WHITE);
        tengah.add(bkeluar);

        // ================= TABLE =================
        tblplgn = new JTable();
        tblplgn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblplgn.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tblplgn);
        scroll.setBounds(20, 70, 940, 430);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 153), 2));

        tengah.add(scroll);

        add(tengah, BorderLayout.CENTER);

        // ================= EVENT =================
        bcari.addActionListener(e -> cariData());
        brefresh.addActionListener(e -> tampilData());
        bkeluar.addActionListener(e -> dispose());

        tblplgn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pilihData();
            }
        });

        tampilData();
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
            "SELECT id_resep, id_registrasi, nama_pasien, poli " +
            "FROM farmasi";

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
            "SELECT id_resep, id_registrasi, nama_pasien, poli " +
            "FROM farmasi " +
            "WHERE nama_pasien LIKE '%" + tcari.getText() + "%' " +
            "OR id_resep LIKE '%" + tcari.getText() + "%'";

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

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // ================= PILIH DATA =================
    private void pilihData() {

        int baris = tblplgn.getSelectedRow();

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
        new AntreanKasir().setVisible(true);
    }
}