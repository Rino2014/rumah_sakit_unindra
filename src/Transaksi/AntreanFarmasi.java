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

public class AntreanFarmasi extends JFrame {

    Connection con;
    Statement st;
    ResultSet rs;

    JTable tblplgn;
    JTextField tcari;
    JButton bcari, brefresh, bkeluar;

    DefaultTableModel tabmode;

    public AntreanFarmasi() {

        // ===============================
        // FRAME
        // ===============================
        setTitle("DATA ANTREAN FARMASI - RUMAH SAKIT UNINDRA");
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

        JLabel judul = new JLabel("DATA ANTREAN FARMASI PASIEN");
        judul.setForeground(Color.WHITE);
        judul.setFont(new Font("Segoe UI", Font.BOLD, 24));
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
        JLabel lcari = new JLabel("Cari Data");
        lcari.setBounds(20, 20, 100, 30);
        lcari.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tengah.add(lcari);

        tcari = new JTextField();
        tcari.setBounds(120, 20, 250, 30);
        tcari.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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
        tblplgn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblplgn.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tblplgn);
        scroll.setBounds(20, 70, 940, 430);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 153), 2));
        tengah.add(scroll);

        add(tengah, BorderLayout.CENTER);

        // ===============================
        // EVENT BUTTON
        // ===============================
        bcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariData();
            }
        });

        brefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tampilData();
            }
        });

        bkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispose();
            }
        });

        // ===============================
        // EVENT KLIK TABEL
        // ===============================
        tblplgn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pilihPasien();
            }
        });

        // Panggil method tampil data pertama kali saat form dibuka
        tampilData();
    }

    // =====================================================
    // TAMPIL DATA (Mengambil dari tabel: form_pemeriksaan)
    // =====================================================
    private void tampilData() {
        //ganti sesuai kolom di database Anda
        Object[] baris = {
            "ID Periksa",
            "ID Registrasi",
            "Nama Pasien",
            "Poli",
            
        };

        tabmode = new DefaultTableModel(null, baris);
        tblplgn.setModel(tabmode);

        try {
            // Query diarahkan ke form_pemeriksaan
            String sql = "SELECT * FROM pemeriksaan_dokter ORDER BY id_rekam_medis ASC";
            st = con.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                String[] data = {
                    rs.getString("id_rekam_medis"),
                    rs.getString("id_registrasi"),
                    rs.getString("nama_pasien"),
                    rs.getString("poli"),
                   
                };
                tabmode.addRow(data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data pemeriksaan: " + e.getMessage(), 
                    "Error Database", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =====================================================
    // CARI DATA
    // =====================================================
    private void cariData() {
        //ganti sesuai kolom di database Anda
        Object[] baris = {
            "ID Periksa",
            "ID Registrasi",
            "Nama Pasien",
            "Poli",
            
        };

        tabmode = new DefaultTableModel(null, baris);
        tblplgn.setModel(tabmode);

        try {
            // Pencarian berdasarkan Nama Pasien atau Diagnosa
            String sql = "SELECT * FROM pemeriksaan_dokter "
                       + "WHERE nama_pasien LIKE '%" + tcari.getText() + "%' "
                       + "OR diagnosa LIKE '%" + tcari.getText() + "%' "
                       + "ORDER BY id_rekam_medis ASC";

            st = con.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                String[] data = {
                    rs.getString("id_rekam_medis"),
                    rs.getString("id_registrasi"),
                    rs.getString("nama_pasien"),
                    rs.getString("poli"),
                    
                };
                tabmode.addRow(data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal mencari data: " + e.getMessage(), 
                    "Error Pencarian", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =====================================================
    // PILIH PASIEN (Menampilkan detail ketika baris diklik)
    // =====================================================
     private void pilihPasien() {
        int baris = tblplgn.getSelectedRow();

        if (baris == -1) {
            return;
        }

        String id = tblplgn.getValueAt(baris, 0).toString();
        String nama = tblplgn.getValueAt(baris, 1).toString();

        JOptionPane.showMessageDialog(null, "Pasien Dipilih : " + nama + "\nID Registrasi : " + id);
    }

    // =====================================================
    // MAIN
    // =====================================================
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(AntreanFarmasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AntreanFarmasi().setVisible(true);
            }
        });
    }
}