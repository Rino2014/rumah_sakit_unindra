package Master;

import Koneksi.Koneksi;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormObat extends javax.swing.JFrame {

    Connection con;
    Statement st;
    ResultSet rs;

    DefaultTableModel tabmode;

    public FormObat() {

        initComponents();

        con = Koneksi.getKoneksi();

        tampilData();

        autoID();

        setLocationRelativeTo(null);
    }

    // =====================================================
    // TAMPIL DATA
    // =====================================================

    private void tampilData(){

        Object[] baris = {

            "ID Obat",
            "Nama Obat",
            "Jenis Obat",
            "Satuan",
            "Harga Beli",
            "Harga Jual",
            "Stok",
            "Tanggal Expired",
            "Lokasi Rak"
        };

        tabmode = new DefaultTableModel(null, baris);

        tblplgn.setModel(tabmode);

        try {

            String sql = "SELECT * FROM obat";

            st = con.createStatement();

            rs = st.executeQuery(sql);

            while(rs.next()){

                String[] data = {

                    rs.getString("id_obat"),
                    rs.getString("nama_obat"),
                    rs.getString("jenis_obat"),
                    rs.getString("satuan"),
                    rs.getString("harga_beli"),
                    rs.getString("harga_jual"),
                    rs.getString("stok"),
                    rs.getString("tanggal_expired"),
                    rs.getString("lokasi_rak")
                };

                tabmode.addRow(data);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }
    }

    // =====================================================
    // AUTO ID
    // =====================================================

private void autoID() {
    try {
        // Mengambil hanya 1 data ID obat terbesar/terakhir dari database
        String sql = "SELECT id_obat FROM obat ORDER BY id_obat DESC LIMIT 1";
        st = con.createStatement();
        rs = st.executeQuery(sql);

        if (rs.next()) {
            // 1. Ambil String ID terakhir dari database, misal "OB001"
            String idTerakhir = rs.getString("id_obat"); 
            
            // 2. Potong teks "OB" (indeks ke-2 hingga akhir) untuk mendapatkan angka "001"
            String nomorSaja = idTerakhir.substring(2); 
            
            // 3. Ubah string angka menjadi integer lalu tambah 1 (1 + 1 = 2)
            int angkaBaru = Integer.parseInt(nomorSaja) + 1; 
            
            // 4. Format kembali menjadi "OB" diikuti 3 digit angka (misal: "OB002")
            // %03d memastikan format angka tetap 3 digit (002, 015, 100)
            String idOtomatis = "OB" + String.format("%03d", angkaBaru);
            
            tid.setText(idOtomatis);
        } else {
            // Jika tabel obat masih kosong atau habis di-reset melalui menu Maintenance, mulai dari OB001
            tid.setText("OB001");
        }
    } catch (Exception e) {
        // Membantu melacak error di console NetBeans jika ada ketidakcocokan tipe data
        e.printStackTrace(); 
        tid.setText("OB001");
    }
}

    // =====================================================
    // RESET
    // =====================================================

    private void reset(){

        tnamaobat.setText("");

        cjenis.setSelectedIndex(0);

        csatuan.setSelectedIndex(0);

        thargabeli.setText("");

        thargajual.setText("");

        tstok.setText("");

        texpired.setDate(null);

        crack.setSelectedIndex(0);

        tcari.setText("");
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
        tnamaobat = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cjenis = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        tstok = new javax.swing.JTextField();
        csatuan = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        thargabeli = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        thargajual = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        texpired = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        bsimpan = new javax.swing.JButton();
        bubah = new javax.swing.JButton();
        bhapus = new javax.swing.JButton();
        bbatal = new javax.swing.JButton();
        bkeluar = new javax.swing.JButton();
        crack = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));

        jLabel18.setFont(new java.awt.Font("News706 BT", 0, 30)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("DATA OBAT");

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
                .addContainerGap(36, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bcari)
                        .addGap(62, 62, 62))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 810, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bcari))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setText("ID Obat");

        tid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidActionPerformed(evt);
            }
        });

        jLabel3.setText("Nama Obat");

        jLabel13.setText("Jenis Obat");

        cjenis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Obat Analgesik (Pereda Nyeri)", "Obat Antibiotik", "Obat Antihistamin (Alergi)", "Obat Herbal", "Obat Vitamin dan Suplemenbox" }));

        jLabel16.setText("Stock");

        tstok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tstokActionPerformed(evt);
            }
        });

        csatuan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "tablet", "kapsul", "botol", "strip", "box" }));

        jLabel4.setText("Satuan");

        jLabel5.setText("Harga Beli");

        jLabel8.setText("Harga Jual");

        thargajual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thargajualActionPerformed(evt);
            }
        });

        jLabel10.setText("Lokasi Rak");

        jLabel11.setText("Tanggal Expired");

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

        crack.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rak 1", "Rak 2", "Rak 3" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel16))
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tid, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tnamaobat)
                                    .addComponent(tstok, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cjenis, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(csatuan, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(thargajual, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                                        .addComponent(thargabeli, javax.swing.GroupLayout.Alignment.LEADING)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bsimpan)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bubah)
                        .addGap(18, 18, 18)
                        .addComponent(bhapus)
                        .addGap(18, 18, 18)
                        .addComponent(bbatal)
                        .addGap(18, 18, 18)
                        .addComponent(bkeluar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(crack, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(texpired, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(37, 37, 37))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(tid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(texpired, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tnamaobat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(crack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(cjenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(tstok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(csatuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(thargabeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(thargajual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bubah)
                    .addComponent(bhapus)
                    .addComponent(bbatal)
                    .addComponent(bkeluar)
                    .addComponent(bsimpan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblplgnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblplgnMouseClicked
        try {

            int baris =
            tblplgn.getSelectedRow();

            tid.setText(
            tblplgn.getValueAt(baris,0).toString());

            tnamaobat.setText(
            tblplgn.getValueAt(baris,1).toString());

            cjenis.setSelectedItem(
            tblplgn.getValueAt(baris,2).toString());

            csatuan.setSelectedItem(
            tblplgn.getValueAt(baris,3).toString());

            thargabeli.setText(
            tblplgn.getValueAt(baris,4).toString());

            thargajual.setText(
            tblplgn.getValueAt(baris,5).toString());

            tstok.setText(
            tblplgn.getValueAt(baris,6).toString());

            java.util.Date tanggal =
            new SimpleDateFormat("yyyy-MM-dd")
            .parse(tblplgn.getValueAt(baris,7).toString());

            texpired.setDate(tanggal);

            crack.setSelectedItem(
            tblplgn.getValueAt(baris,8).toString());

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_tblplgnMouseClicked

    private void tcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tcariActionPerformed

    private void bcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcariActionPerformed
     Object[] baris = {

            "ID Obat",
            "Nama Obat",
            "Jenis Obat",
            "Satuan",
            "Harga Beli",
            "Harga Jual",
            "Stok",
            "Tanggal Expired",
            "Lokasi Rak"
        };

        tabmode =
        new DefaultTableModel(null, baris);

        tblplgn.setModel(tabmode);

        try {

            String sql =
            "SELECT * FROM obat "
            + "WHERE nama_obat LIKE '%"
            + tcari.getText()+"%'";

            st = con.createStatement();

            rs = st.executeQuery(sql);

            while(rs.next()){

                String[] data = {

                    rs.getString("id_obat"),
                    rs.getString("nama_obat"),
                    rs.getString("jenis_obat"),
                    rs.getString("satuan"),
                    rs.getString("harga_beli"),
                    rs.getString("harga_jual"),
                    rs.getString("stok"),
                    rs.getString("tanggal_expired"),
                    rs.getString("lokasi_rak")
                };

                tabmode.addRow(data);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_bcariActionPerformed

    private void tidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tidActionPerformed

    private void tstokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tstokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tstokActionPerformed

    private void thargajualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thargajualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_thargajualActionPerformed

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpanActionPerformed
    try {

            SimpleDateFormat sdf =
            new SimpleDateFormat("yyyy-MM-dd");

            String tanggal =
            sdf.format(texpired.getDate());

            String sql =
            "INSERT INTO obat VALUES("
            + "'"+tid.getText()+"',"
            + "'"+tnamaobat.getText()+"',"
            + "'"+cjenis.getSelectedItem()+"',"
            + "'"+csatuan.getSelectedItem()+"',"
            + "'"+thargabeli.getText()+"',"
            + "'"+thargajual.getText()+"',"
            + "'"+tstok.getText()+"',"
            + "'"+tanggal+"',"
            + "'"+crack.getSelectedItem()+"')";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
            null,
            "Data Obat Berhasil Disimpan");

            tampilData();

            reset();

            autoID();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_bsimpanActionPerformed

    private void bubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bubahActionPerformed
  
        try {

            SimpleDateFormat sdf =
            new SimpleDateFormat("yyyy-MM-dd");

            String tanggal =
            sdf.format(texpired.getDate());

            String sql =
            "UPDATE obat SET "

            + "nama_obat='"
            + tnamaobat.getText()+"',"

            + "jenis_obat='"
            + cjenis.getSelectedItem()+"',"

            + "satuan='"
            + csatuan.getSelectedItem()+"',"

            + "harga_beli='"
            + thargabeli.getText()+"',"

            + "harga_jual='"
            + thargajual.getText()+"',"

            + "stok='"
            + tstok.getText()+"',"

            + "tanggal_expired='"
            + tanggal+"',"

            + "lokasi_rak='"
            + crack.getSelectedItem()+"' "

            + "WHERE id_obat='"
            + tid.getText()+"'";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
            null,
            "Data Obat Berhasil Diubah");

            tampilData();

            reset();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_bubahActionPerformed

    private void bhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhapusActionPerformed
        try {

            String sql =
            "DELETE FROM obat "
            + "WHERE id_obat='"
            + tid.getText()+"'";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
            null,
            "Data Obat Berhasil Dihapus");

            tampilData();

            reset();

            autoID();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_bhapusActionPerformed

    private void bbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatalActionPerformed
        reset();
    }//GEN-LAST:event_bbatalActionPerformed

    private void bkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bkeluarActionPerformed
        dispose();
    }//GEN-LAST:event_bkeluarActionPerformed

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
            java.util.logging.Logger.getLogger(FormObat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormObat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormObat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormObat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormObat().setVisible(true);
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
    private javax.swing.JComboBox<String> cjenis;
    private javax.swing.JComboBox<String> crack;
    private javax.swing.JComboBox<String> csatuan;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblplgn;
    private javax.swing.JTextField tcari;
    private com.toedter.calendar.JDateChooser texpired;
    private javax.swing.JTextField thargabeli;
    private javax.swing.JTextField thargajual;
    private javax.swing.JTextField tid;
    private javax.swing.JTextField tnamaobat;
    private javax.swing.JTextField tstok;
    // End of variables declaration//GEN-END:variables
}
