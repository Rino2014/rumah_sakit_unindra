package Transaksi;

import Koneksi.Koneksi;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

public class AntreanPembayaran extends JFrame {

    Connection con;
    Statement st;
    ResultSet rs;

    JTable tblplgn;
    JTextField tcari;
    JButton bcari, brefresh, bkeluar;

    DefaultTableModel tabmode;

    public AntreanPembayaran() {

        // ===============================
        // FRAME
        // ===============================

        setTitle("ANTREAN PEMBAYARAN - RUMAH SAKIT UNINDRA");

        setSize(1000, 600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        // ===============================
        // KONEKSI
        // ===============================

        con = Koneksi.getKoneksi();

        // ===============================
        // PANEL HEADER
        // ===============================

        JPanel header = new JPanel();

        header.setBackground(new Color(0, 102, 153));

        JLabel judul = new JLabel(
                "ANTREAN PEMBAYARAN RUMAH SAKIT UNINDRA");

        judul.setForeground(Color.WHITE);

        judul.setFont(
                new Font("Segoe UI", Font.BOLD, 24));

        header.add(judul);

        add(header, BorderLayout.NORTH);

        // ===============================
        // PANEL TENGAH
        // ===============================

        JPanel tengah = new JPanel();

        tengah.setLayout(null);

        tengah.setBackground(Color.WHITE);

        // ===============================
        // TEXTFIELD CARI
        // ===============================

        JLabel lcari = new JLabel("Cari Pasien");

        lcari.setBounds(20, 20, 100, 30);

        lcari.setFont(
                new Font("Segoe UI", Font.BOLD, 14));

        tengah.add(lcari);

        tcari = new JTextField();

        tcari.setBounds(120, 20, 250, 30);

        tcari.setFont(
                new Font("Segoe UI", Font.PLAIN, 14));

        tengah.add(tcari);

        // ===============================
        // BUTTON CARI
        // ===============================

        bcari = new JButton("Cari");

        bcari.setBounds(390, 20, 100, 30);

        bcari.setBackground(new Color(0, 153, 204));

        bcari.setForeground(Color.WHITE);

        tengah.add(bcari);

        // ===============================
        // BUTTON REFRESH
        // ===============================

        brefresh = new JButton("Refresh");

        brefresh.setBounds(500, 20, 100, 30);

        brefresh.setBackground(new Color(0, 153, 102));

        brefresh.setForeground(Color.WHITE);

        tengah.add(brefresh);

        // ===============================
        // BUTTON KELUAR
        // ===============================

        bkeluar = new JButton("Keluar");

        bkeluar.setBounds(610, 20, 100, 30);

        bkeluar.setBackground(Color.RED);

        bkeluar.setForeground(Color.WHITE);

        tengah.add(bkeluar);

        // ===============================
        // TABEL
        // ===============================

        tblplgn = new JTable();

        tblplgn.setFont(
                new Font("Segoe UI", Font.PLAIN, 13));

        tblplgn.setRowHeight(25);

        JScrollPane scroll =
                new JScrollPane(tblplgn);

        scroll.setBounds(20, 70, 940, 430);

        scroll.setBorder(
                BorderFactory.createLineBorder(
                        new Color(0, 102, 153), 2));

        tengah.add(scroll);

        add(tengah, BorderLayout.CENTER);

        // ===============================
        // EVENT BUTTON
        // ===============================

        bcari.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(
                    java.awt.event.ActionEvent evt) {

                cariData();
            }
        });

        brefresh.addActionListener(
                new java.awt.event.ActionListener() {

                    public void actionPerformed(
                            java.awt.event.ActionEvent evt) {

                        tampilData();
                    }
                });

        bkeluar.addActionListener(
                new java.awt.event.ActionListener() {

                    public void actionPerformed(
                            java.awt.event.ActionEvent evt) {

                        dispose();
                    }
                });

        // ===============================
        // EVENT KLIK TABEL
        // ===============================

        tblplgn.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    public void mouseClicked(
                            java.awt.event.MouseEvent evt) {

                        // FIX 1: nama method disesuaikan
                        pilihPasien();
                    }
                });

        tampilData();
    }

    // =====================================================
    // TAMPIL DATA
    // =====================================================

    private void tampilData() {

        // FIX 2: kolom header sesuai permintaan (id_resep, bukan id_kasir)
        Object[] baris = {
            "ID Registrasi",
            "ID Rekam Medis",
            "ID Resep",
            "Nama Pasien",
            
        };

        tabmode =
                new DefaultTableModel(null, baris);

        tblplgn.setModel(tabmode);

        try {

            // FIX 3: query dari tabel kasir, ambil kolom yang sesuai
            // Sesuaikan nama tabel dan kolom dengan database Anda
            String sql =
                    "SELECT id_registrasi, id_rekam_medis, id_resep, nama_pasien FROM kasir";

            st = con.createStatement();

            rs = st.executeQuery(sql);

            while (rs.next()) {

                String[] data = {
                    rs.getString("id_registrasi"),
                    rs.getString("id_rekam_medis"),
                    rs.getString("id_resep"),    // FIX: id_resep bukan id_kasir
                    rs.getString("nama_pasien"),
                    
                };

                tabmode.addRow(data);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    e);
        }
    }

    // =====================================================
    // CARI DATA
    // =====================================================

    private void cariData() {

        // FIX 4: kolom header sesuai permintaan
        Object[] baris = {
            "ID Registrasi",
            "ID Rekam Medis",
            "ID Resep",
            "Nama Pasien",
            
        };

        tabmode =
                new DefaultTableModel(null, baris);

        tblplgn.setModel(tabmode);

        try {

            // FIX 5: pencarian berdasarkan nama_pasien agar lebih berguna
            // (bisa juga tetap id_resep jika memang ingin cari by ID)
            String sql =
                    "SELECT id_registrasi, id_rekam_medis, id_resep, nama_pasien "
                    + "FROM kasir "
                    + "WHERE nama_pasien LIKE '%"
                    + tcari.getText() + "%' "
                    + "OR id_resep LIKE '%"
                    + tcari.getText() + "%'";

            st = con.createStatement();

            rs = st.executeQuery(sql);

            while (rs.next()) {

                String[] data = {
                    rs.getString("id_registrasi"),
                    rs.getString("id_rekam_medis"),
                    rs.getString("id_resep"),    // FIX: id_resep
                    rs.getString("nama_pasien"),
                    
                };

                tabmode.addRow(data);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    e);
        }
    }

    // =====================================================
    // PILIH PASIEN
    // FIX 6: nama method disamakan dengan yang dipanggil di event listener
    // =====================================================

    private void pilihPasien() {

        int baris =
                tblplgn.getSelectedRow();

        if (baris < 0) return; // tidak ada baris dipilih

        String idRegistrasi =
                tblplgn.getValueAt(baris, 0).toString();

        String namaPasien =
                tblplgn.getValueAt(baris, 3).toString();

        JOptionPane.showMessageDialog(
                null,
                "Pasien Dipilih : "
                + namaPasien
                + "\nID Registrasi : "
                + idRegistrasi);

        // nanti bisa diarahkan ke Form Pembayaran
    }

    // =====================================================
    // MAIN
    // FIX 7: class name disamakan dengan AntreanPembayaran
    // =====================================================

    public static void main(String args[]) {

        new AntreanPembayaran().setVisible(true); // FIX: bukan AntreanPemeriksaan
    }
}