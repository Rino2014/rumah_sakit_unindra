package Transaksi;

import Koneksi.Koneksi;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormPendaftaran extends javax.swing.JFrame {

    Connection con;
    Statement st;
    ResultSet rs;

    DefaultTableModel tabmode;

    boolean sedangIsiCombo = false;

    public FormPendaftaran() {

        initComponents();

        con = Koneksi.getKoneksi();

        tampilPoli();
        tampilIDPasien();
        tampilData();
        autoID();

        tnamadokter.setEditable(false);

        setLocationRelativeTo(null);
    }

    // =====================================================
    // AUTO ID
    // =====================================================

    private void autoID() {

        try {

            String sql =
            "SELECT id_registrasi FROM pendaftaran_pasien "
            + "ORDER BY id_registrasi DESC LIMIT 1";

            st = con.createStatement();

            rs = st.executeQuery(sql);

            if (rs.next()) {

                String id =
                rs.getString("id_registrasi").substring(3);

                int urut =
                Integer.parseInt(id) + 1;

                tid.setText(
                "PDT" + String.format("%03d", urut));

            } else {

                tid.setText("PDT001");
            }

        } catch (Exception e) {

            tid.setText("PDT001");
        }
    }

    // =====================================================
    // TAMPIL DATA
    // =====================================================

    private void tampilData() {

        Object[] baris = {

            "ID Registrasi",
            "Tanggal",
            "ID Pasien",
            "Nama Pasien",
            "Poli",
            "ID Dokter",
            "Nama Dokter",
            "Pembayaran",
            "Keluhan"
        };

        tabmode =
        new DefaultTableModel(null, baris);

        tblplgn.setModel(tabmode);

        try {

            String sql =
            "SELECT * FROM pendaftaran_pasien";

            st = con.createStatement();

            rs = st.executeQuery(sql);

            while (rs.next()) {

                String[] data = {

                    rs.getString("id_registrasi"),
                    rs.getString("tanggal_kunjungan"),
                    rs.getString("id_pasien"),
                    rs.getString("nama_pasien"),
                    rs.getString("poli"),
                    rs.getString("id_dokter"),
                    rs.getString("nama_dokter"),
                    rs.getString("jenis_pembayaran"),
                    rs.getString("keluhan_awal")
                };

                tabmode.addRow(data);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null,
            "Gagal tampil data : "
            + e.getMessage());
        }
    }

    // =====================================================
    // TAMPIL POLI
    // =====================================================

private void tampilPoli() {
        try {
            sedangIsiCombo = true; // Mengunci event agar tidak berjalan saat proses clear
            cpoli.removeAllItems();
            
            String sql = "SELECT DISTINCT poli FROM dokter";
            st = con.createStatement();
            rs = st.executeQuery(sql);

            cpoli.addItem("-- Pilih Poli --");
            while (rs.next()) {
                String poli = rs.getString("poli");
                if (poli != null && !poli.equals("")) {
                    cpoli.addItem(poli);
                }
            }
            sedangIsiCombo = false; // Membuka kembali kunci event
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal tampil poli : " + e.getMessage());
        }
    }

    // =====================================================
    // TAMPIL DOKTER BERDASARKAN POLI
    // =====================================================
    private void tampilDokterByPoli() {
        if (sedangIsiCombo) return; // Lewati jika combo sedang dalam proses reset item

        try {
            sedangIsiCombo = true;
            tiddokter.removeAllItems();
            tnamadokter.setText(""); // Reset nama dokter terdahulu
            
            if (cpoli.getSelectedItem() == null) {
                sedangIsiCombo = false;
                return;
            }

            String poli = cpoli.getSelectedItem().toString();
            if (poli.equals("-- Pilih Poli --")) {
                tiddokter.addItem("-- Pilih ID Dokter --");
                sedangIsiCombo = false;
                return;
            }

            // Menambahkan default item untuk ID Dokter
            tiddokter.addItem("-- Pilih ID Dokter --");

            String sql = "SELECT id_dokter FROM dokter WHERE poli='" + poli + "'";
            st = con.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                tiddokter.addItem(rs.getString("id_dokter"));
            }
            sedangIsiCombo = false;
        } catch (Exception e) {
            sedangIsiCombo = false;
            JOptionPane.showMessageDialog(null, "Gagal tampil dokter : " + e.getMessage());
        }
    }

    // =====================================================
    // TAMPIL NAMA DOKTER
    // =====================================================
    private void tampilNamaDokter() {
        if (sedangIsiCombo) return; // Lewati jika combo sedang dalam proses reset item

        try {
            if (tiddokter.getSelectedItem() == null) {
                tnamadokter.setText("");
                return;
            }

            String iddokter = tiddokter.getSelectedItem().toString();
            if (iddokter.equals("-- Pilih ID Dokter --")) {
                tnamadokter.setText("");
                return;
            }

            String sql = "SELECT nama_dokter FROM dokter WHERE id_dokter='" + iddokter + "'";
            st = con.createStatement();
            rs = st.executeQuery(sql);

            if (rs.next()) {
                tnamadokter.setText(rs.getString("nama_dokter"));
            } else {
                tnamadokter.setText("");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal tampil nama dokter : " + e.getMessage());
        }
    }
    // =====================================================
    // TAMPIL ID PASIEN
    // =====================================================

    private void tampilIDPasien() {

        try {

            sedangIsiCombo = true;

            tidpasien.removeAllItems();

            String sql =
            "SELECT id_pasien FROM pasien";

            st = con.createStatement();

            rs = st.executeQuery(sql);

            tidpasien.addItem("-- Pilih ID Pasien --");

            while (rs.next()) {

                tidpasien.addItem(
                rs.getString("id_pasien"));
            }

            sedangIsiCombo = false;

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null,
            "Gagal tampil pasien : "
            + e.getMessage());
        }
    }

    // =====================================================
    // CARI PASIEN
    // =====================================================

    private void cariPasien() {

        try {

            if (tidpasien.getSelectedItem() == null) {
                return;
            }

            String idpasien =
            tidpasien.getSelectedItem().toString();

            if (idpasien.equals("-- Pilih ID Pasien --")) {

                kosongkanFieldPasien();
                return;
            }

            String sql =
            "SELECT * FROM pasien "
            + "WHERE id_pasien='"
            + idpasien + "'";

            st = con.createStatement();

            rs = st.executeQuery(sql);

            if (rs.next()) {

                tnama.setText(
                rs.getString("nama_pasien"));

                ttempatlahir.setText(
                rs.getString("tempat_lahir"));

                ttanggallahir.setText(
                rs.getString("tanggal_lahir"));

                tkelamin.setText(
                rs.getString("jenis_kelamin"));

                ttelepon.setText(
                rs.getString("no_telepon"));

                talamat.setText(
                rs.getString("alamat"));
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null,
            "Gagal cari pasien : "
            + e.getMessage());
        }
    }

    // =====================================================
    // KOSONGKAN FIELD PASIEN
    // =====================================================

    private void kosongkanFieldPasien() {

        tnama.setText("");
        ttempatlahir.setText("");
        ttanggallahir.setText("");
        tkelamin.setText("");
        ttelepon.setText("");
        talamat.setText("");
    }

    // =====================================================
    // SIMPAN DATA
    // =====================================================

    private void simpanData() {

        try {

            SimpleDateFormat sdf =
            new SimpleDateFormat("yyyy-MM-dd");

            String tanggal =
            sdf.format(tkunjungan.getDate());

            String sql =
            "INSERT INTO pendaftaran_pasien VALUES "
            + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.setString(1, tid.getText());
            ps.setString(2, tanggal);

            ps.setString(3,
            tidpasien.getSelectedItem().toString());

            ps.setString(4, tnama.getText());
            ps.setString(5, ttempatlahir.getText());
            ps.setString(6, ttanggallahir.getText());
            ps.setString(7, tkelamin.getText());
            ps.setString(8, ttelepon.getText());
            ps.setString(9, talamat.getText());

            ps.setString(10,
            cpoli.getSelectedItem().toString());

            ps.setString(11,
            tiddokter.getSelectedItem().toString());

            ps.setString(12,
            tnamadokter.getText());

            ps.setString(13,
            cbayar.getSelectedItem().toString());

            ps.setString(14,
            tkeluhan.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null,
            "Data berhasil disimpan");

            tampilData();

            reset();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null,
            "Gagal simpan : "
            + e.getMessage());
        }
    }

    // =====================================================
    // RESET
    // =====================================================

private void reset() {
        tkunjungan.setDate(null);
        
        sedangIsiCombo = true; // Kunci diaktifkan saat reset
        if (tidpasien.getItemCount() > 0) { tidpasien.setSelectedIndex(0); }
        kosongkanFieldPasien();
        
        if (cpoli.getItemCount() > 0) { cpoli.setSelectedIndex(0); }
        if (tiddokter.getItemCount() > 0) { tiddokter.removeAllItems(); tiddokter.addItem("-- Pilih ID Dokter --"); }
        tnamadokter.setText("");
        cbayar.setSelectedIndex(0);
        sedangIsiCombo = false; // Buka kembali kunci
        
        tkeluhan.setText("");
        tcari.setText("");
        autoID();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblplgn = new javax.swing.JTable();
        tcari = new javax.swing.JTextField();
        bcari = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        tid = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tnama = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        ttempatlahir = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tkunjungan = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ttelepon = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        talamat = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        tkeluhan = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        ttanggallahir = new javax.swing.JTextField();
        tkelamin = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cpoli = new javax.swing.JComboBox<>();
        cbayar = new javax.swing.JComboBox<>();
        bsimpan = new javax.swing.JButton();
        bubah = new javax.swing.JButton();
        bhapus = new javax.swing.JButton();
        bbatal = new javax.swing.JButton();
        bkeluar = new javax.swing.JButton();
        tidpasien = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        tnamadokter = new javax.swing.JTextField();
        tiddokter = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));

        jLabel18.setFont(new java.awt.Font("News706 BT", 0, 30)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("PENDAFTARAN PASIEN");

        jLabel22.setFont(new java.awt.Font("Impact", 1, 36)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("+");

        jLabel24.setFont(new java.awt.Font("Impact", 1, 36)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("+");

        jLabel23.setFont(new java.awt.Font("Impact", 1, 36)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("+");

        jLabel9.setFont(new java.awt.Font("Bodoni Bd BT", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 204, 0));
        jLabel9.setText("Rumah Sakit Unindra");
        jLabel9.setToolTipText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(jLabel22))
                .addGap(27, 27, 27)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(51, 51, 51))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addContainerGap())))
        );

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        tblplgn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblplgn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblplgnMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblplgn);

        tcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tcariActionPerformed(evt);
            }
        });

        bcari.setText("Cari");
        bcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 810, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bcari)
                        .addGap(21, 21, 21))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bcari))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel2.setText("ID Registrasi");

        tid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidActionPerformed(evt);
            }
        });

        jLabel3.setText("Nama Pasien");

        jLabel13.setText("Tempat Lahir");

        jLabel11.setText("Tanggal Kunjungan ");

        jLabel4.setText("Jenis Kelamin");

        jLabel5.setText("No. Telepon");

        jLabel8.setText("Alamat");

        jLabel6.setText("ID Pasien");

        jLabel7.setText("Poli");

        jLabel10.setText("ID Dokter ");

        tkeluhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tkeluhanActionPerformed(evt);
            }
        });

        jLabel14.setText("Tanggal Lahir");

        jLabel12.setText("Keluhan Awal");

        jLabel15.setText("Jenis Pembayaran");

        cpoli.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cpoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cpoliActionPerformed(evt);
            }
        });

        cbayar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Transfer", "Cash", "Asuransi" }));

        bsimpan.setBackground(new java.awt.Color(255, 255, 0));
        bsimpan.setText("Simpan");
        bsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsimpanActionPerformed(evt);
            }
        });

        bubah.setBackground(new java.awt.Color(255, 255, 0));
        bubah.setText("Ubah");
        bubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bubahActionPerformed(evt);
            }
        });

        bhapus.setBackground(new java.awt.Color(255, 255, 51));
        bhapus.setText("Hapus");
        bhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bhapusActionPerformed(evt);
            }
        });

        bbatal.setBackground(new java.awt.Color(255, 255, 51));
        bbatal.setText("Batal");
        bbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbatalActionPerformed(evt);
            }
        });

        bkeluar.setBackground(new java.awt.Color(255, 255, 51));
        bkeluar.setText("Keluar");
        bkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bkeluarActionPerformed(evt);
            }
        });

        tidpasien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        tidpasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidpasienActionPerformed(evt);
            }
        });

        jLabel16.setText("Nama Dokter");

        tnamadokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnamadokterActionPerformed(evt);
            }
        });

        tiddokter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        tiddokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tiddokterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(66, 66, 66)
                        .addComponent(tkelamin, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addComponent(jLabel8))
                            .addGap(74, 74, 74)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(ttelepon)
                                .addComponent(talamat, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel6))
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tkunjungan, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tid, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tidpasien, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel13))
                                .addGap(67, 67, 67)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tnama, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                                    .addComponent(ttempatlahir))))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel14)
                            .addGap(67, 67, 67)
                            .addComponent(ttanggallahir))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bsimpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bubah)
                        .addGap(18, 18, 18)
                        .addComponent(bhapus)
                        .addGap(18, 18, 18)
                        .addComponent(bbatal)
                        .addGap(18, 18, 18)
                        .addComponent(bkeluar)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel16))
                                .addGap(53, 53, 53))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel10))
                                .addGap(99, 99, 99)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(tkeluhan)
                                .addComponent(cpoli, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbayar, 0, 196, Short.MAX_VALUE)
                                .addComponent(tnamadokter))
                            .addComponent(tiddokter, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(cpoli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(tkunjungan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(tiddokter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(tidpasien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(tnamadokter, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tnama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(ttempatlahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(ttanggallahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(tkelamin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(tkeluhan, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(ttelepon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel8)
                        .addComponent(talamat, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bubah)
                        .addComponent(bhapus)
                        .addComponent(bbatal)
                        .addComponent(bkeluar)
                        .addComponent(bsimpan)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblplgnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblplgnMouseClicked
    try {

        int baris =
        tblplgn.getSelectedRow();

        tid.setText(
        tblplgn.getValueAt(baris,0).toString());

        java.util.Date tanggal =
        new java.text.SimpleDateFormat("yyyy-MM-dd")
        .parse(tblplgn.getValueAt(baris,1).toString());

        tkunjungan.setDate(tanggal);

        tidpasien.setSelectedItem(
        tblplgn.getValueAt(baris,2).toString());

        tnama.setText(
        tblplgn.getValueAt(baris,3).toString());

        ttempatlahir.setText(
        tblplgn.getValueAt(baris,4).toString());

        ttanggallahir.setText(
        tblplgn.getValueAt(baris,5).toString());

        tkelamin.setText(
        tblplgn.getValueAt(baris,6).toString());

        ttelepon.setText(
        tblplgn.getValueAt(baris,7).toString());

        talamat.setText(
        tblplgn.getValueAt(baris,8).toString());

        cpoli.setSelectedItem(
        tblplgn.getValueAt(baris,9).toString());

        tiddokter.setSelectedItem(
        tblplgn.getValueAt(baris,10).toString());

        tnamadokter.setText(
        tblplgn.getValueAt(baris,11).toString());

        cbayar.setSelectedItem(
        tblplgn.getValueAt(baris,12).toString());

        tkeluhan.setText(
        tblplgn.getValueAt(baris,13).toString());

    } catch (Exception e) {

        JOptionPane.showMessageDialog(null,
        "Error Klik Tabel : " + e.getMessage());
    } 
    }//GEN-LAST:event_tblplgnMouseClicked

    private void tcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tcariActionPerformed

    private void bcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcariActionPerformed

    Object[] baris = {

        "ID Registrasi",
        "Tanggal Kunjungan",
        "ID Pasien",
        "Nama Pasien",
        "Tempat Lahir",
        "Tanggal Lahir",
        "Jenis Kelamin",
        "No Telepon",
        "Alamat",
        "Poli",
        "ID Dokter",
        "Nama Dokter",
        "Jenis Pembayaran",
        "Keluhan Awal"
    };

    tabmode =
    new DefaultTableModel(null, baris);

    tblplgn.setModel(tabmode);

    try {

        String sql =
        "SELECT * FROM pendaftaran_pasien "

        + "WHERE nama_pasien LIKE '%"
        + tcari.getText() + "%' "

        + "OR id_pasien LIKE '%"
        + tcari.getText() + "%'";

        st = con.createStatement();

        rs = st.executeQuery(sql);

        while (rs.next()) {

            String[] data = {

                rs.getString("id_registrasi"),
                rs.getString("tanggal_kunjungan"),
                rs.getString("id_pasien"),
                rs.getString("nama_pasien"),
                rs.getString("tempat_lahir"),
                rs.getString("tanggal_lahir"),
                rs.getString("jenis_kelamin"),
                rs.getString("no_telepon"),
                rs.getString("alamat"),
                rs.getString("poli"),
                rs.getString("id_dokter"),
                rs.getString("nama_dokter"),
                rs.getString("jenis_pembayaran"),
                rs.getString("keluhan_awal")
            };

            tabmode.addRow(data);
        }

    } catch (Exception e) {

        JOptionPane.showMessageDialog(null,
        "Gagal Cari Data : " + e.getMessage());
    }
    }//GEN-LAST:event_bcariActionPerformed

    private void tidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tidActionPerformed

    private void tkeluhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tkeluhanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tkeluhanActionPerformed

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpanActionPerformed
        // VALIDASI INPUT
        if (tkunjungan.getDate() == null) {

            JOptionPane.showMessageDialog(null,
            "Tanggal Kunjungan Wajib Diisi");

            return;
        }

        if (tidpasien.getSelectedItem() == null
        || tidpasien.getSelectedItem().toString()
        .equals("-- Pilih ID Pasien --")) {

            JOptionPane.showMessageDialog(null,
            "ID Pasien Wajib Dipilih");

            return;
        }

        if (tnama.getText().equals("")) {

            JOptionPane.showMessageDialog(null,
            "Nama Pasien Masih Kosong");

            return;
        }

        if (cpoli.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(null,
            "Poli Wajib Dipilih");

            return;
        }

        if (tiddokter.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(null,
            "Dokter Wajib Dipilih");

            return;
        }

        try {

            // FORMAT TANGGAL
            SimpleDateFormat sdf =
            new SimpleDateFormat("yyyy-MM-dd");

            String tanggal =
            sdf.format(tkunjungan.getDate());

            // QUERY SIMPAN
            String sql =
            "INSERT INTO pendaftaran_pasien ("

            + "id_registrasi,"
            + "tanggal_kunjungan,"
            + "id_pasien,"
            + "nama_pasien,"
            + "tempat_lahir,"
            + "tanggal_lahir,"
            + "jenis_kelamin,"
            + "no_telepon,"
            + "alamat,"
            + "poli,"
            + "id_dokter,"
            + "nama_dokter,"
            + "jenis_pembayaran,"
            + "keluhan_awal"

            + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps =
            con.prepareStatement(sql);

            // SET DATA
            ps.setString(1, tid.getText());
            ps.setString(2, tanggal);
            ps.setString(3,
            tidpasien.getSelectedItem().toString());

            ps.setString(4, tnama.getText());
            ps.setString(5, ttempatlahir.getText());
            ps.setString(6, ttanggallahir.getText());
            ps.setString(7, tkelamin.getText());
            ps.setString(8, ttelepon.getText());
            ps.setString(9, talamat.getText());

            ps.setString(10,
            cpoli.getSelectedItem().toString());

            ps.setString(11,
            tiddokter.getSelectedItem().toString());

            ps.setString(12,
            tnamadokter.getText());

            ps.setString(13,
            cbayar.getSelectedItem().toString());

            ps.setString(14,
            tkeluhan.getText());

            // EKSEKUSI
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null,
            "Data Pendaftaran Berhasil Disimpan");

            // REFRESH DATA
            tampilData();

            // RESET FORM
            reset();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null,
            "Gagal Simpan Data : "
            + e.getMessage());
        }
    }//GEN-LAST:event_bsimpanActionPerformed

    private void bubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bubahActionPerformed

        try {

            SimpleDateFormat sdf =
            new SimpleDateFormat("yyyy-MM-dd");

            String tanggal =
            sdf.format(tkunjungan.getDate());

            String sql =
            "UPDATE pendaftaran_pasien SET "

            + "tanggal_kunjungan='" + tanggal + "',"

            + "id_pasien='"
            + tidpasien.getSelectedItem() + "',"

            + "nama_pasien='"
            + tnama.getText() + "',"

            + "tempat_lahir='"
            + ttempatlahir.getText() + "',"

            + "tanggal_lahir='"
            + ttanggallahir.getText() + "',"

            + "jenis_kelamin='"
            + tkelamin.getText() + "',"

            + "no_telepon='"
            + ttelepon.getText() + "',"

            + "alamat='"
            + talamat.getText() + "',"

            + "poli='"
            + cpoli.getSelectedItem() + "',"

            + "id_dokter='"
            + tiddokter.getSelectedItem() + "',"

            + "nama_dokter='"
            + tnamadokter.getText() + "',"

            + "jenis_pembayaran='"
            + cbayar.getSelectedItem() + "',"

            + "keluhan_awal='"
            + tkeluhan.getText() + "' "

            + "WHERE id_registrasi='"
            + tid.getText() + "'";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null,
            "Data Berhasil Diubah");

            tampilData();

            reset();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null,
            "Gagal Ubah : " + e.getMessage());
        }
    }//GEN-LAST:event_bubahActionPerformed

    private void bhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhapusActionPerformed

        try {

            String sql =
            "DELETE FROM pendaftaran_pasien "
            + "WHERE id_registrasi='"
            + tid.getText() + "'";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null,
            "Data Berhasil Dihapus");

            tampilData();

            reset();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null,
            "Gagal Hapus : " + e.getMessage());
        }
    }//GEN-LAST:event_bhapusActionPerformed

    private void bbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatalActionPerformed
        reset();
    }//GEN-LAST:event_bbatalActionPerformed

    private void bkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bkeluarActionPerformed
        dispose();
    }//GEN-LAST:event_bkeluarActionPerformed

    private void tidpasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidpasienActionPerformed
    cariPasien();        // TODO add your handling code here:
    }//GEN-LAST:event_tidpasienActionPerformed

    private void tnamadokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnamadokterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tnamadokterActionPerformed

    private void tiddokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tiddokterActionPerformed
     tampilNamaDokter();    // TODO add your handling code here:
    }//GEN-LAST:event_tiddokterActionPerformed

    private void cpoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cpoliActionPerformed
     tampilDokterByPoli();
    }//GEN-LAST:event_cpoliActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormPendaftaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormPendaftaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormPendaftaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPendaftaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormPendaftaran().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bbatal;
    private javax.swing.JButton bcari;
    private javax.swing.JButton bhapus;
    private javax.swing.JButton bkeluar;
    private javax.swing.JButton bsimpan;
    private javax.swing.JButton bubah;
    private javax.swing.JComboBox<String> cbayar;
    private javax.swing.JComboBox<String> cpoli;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField talamat;
    private javax.swing.JTable tblplgn;
    private javax.swing.JTextField tcari;
    private javax.swing.JTextField tid;
    private javax.swing.JComboBox<String> tiddokter;
    private javax.swing.JComboBox<String> tidpasien;
    private javax.swing.JTextField tkelamin;
    private javax.swing.JTextField tkeluhan;
    private com.toedter.calendar.JDateChooser tkunjungan;
    private javax.swing.JTextField tnama;
    private javax.swing.JTextField tnamadokter;
    private javax.swing.JTextField ttanggallahir;
    private javax.swing.JTextField ttelepon;
    private javax.swing.JTextField ttempatlahir;
    // End of variables declaration//GEN-END:variables
}
