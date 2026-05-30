package Master;

import Koneksi.Koneksi;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormPoli extends javax.swing.JFrame {

    Connection con;
    Statement st;
    ResultSet rs;

    DefaultTableModel tabmode;

    public FormPoli() {

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
            "ID Poli",
            "Nama Poli",
            "Kepala Poli",
            "Lokasi Poli",
            "Jadwal Operasional",
            "Dokter Penanggung Jawab"
        };

        tabmode = new DefaultTableModel(null, baris);

        tblplgn.setModel(tabmode);

        try {

            String sql = "SELECT * FROM poli_klinik";

            st = con.createStatement();

            rs = st.executeQuery(sql);

            while(rs.next()){

                String[] data = {

                    rs.getString("id_poli"),
                    rs.getString("nama_poli"),
                    rs.getString("kepala_poli"),
                    rs.getString("lokasi_poli"),
                    rs.getString("jadwal_operasional"),
                    rs.getString("dokter_penanggung_jawab")
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
        // Mengambil hanya 1 data ID poli terbesar/terakhir dari database
        String sql = "SELECT id_poli FROM poli_klinik ORDER BY id_poli DESC LIMIT 1";
        st = con.createStatement();
        rs = st.executeQuery(sql);

        if (rs.next()) {
            // 1. Ambil String ID terakhir dari database, misal "PL001"
            String idTerakhir = rs.getString("id_poli"); 
            
            // 2. Potong teks "PL" (indeks ke-2 hingga akhir) untuk mengambil angka "001"
            String nomorSaja = idTerakhir.substring(2); 
            
            // 3. Ubah string angka menjadi integer lalu tambah 1 (1 + 1 = 2)
            int angkaBaru = Integer.parseInt(nomorSaja) + 1; 
            
            // 4. Format kembali menjadi "PL" diikuti 3 digit angka (misal: "PL002")
            // %03d memastikan format angka tetap 3 digit (002, 015, 100)
            String idOtomatis = "PL" + String.format("%03d", angkaBaru);
            
            tid.setText(idOtomatis);
        } else {
            // Jika tabel poli_klinik masih kosong atau habis di-reset, mulai dari PL001
            tid.setText("PL001");
        }
    } catch (Exception e) {
        // Membantu melacak error di console NetBeans jika terjadi kendala data
        e.printStackTrace(); 
        tid.setText("PL001");
    }
}

    // =====================================================
    // AMBIL JADWAL CHECKBOX
    // =====================================================

    private String getJadwal(){

        String jadwal = "";

        if(chkSenin.isSelected()){
            jadwal += "Senin, ";
        }

        if(chkSelasa.isSelected()){
            jadwal += "Selasa, ";
        }

        if(chkRabu.isSelected()){
            jadwal += "Rabu, ";
        }

        if(chkKamis.isSelected()){
            jadwal += "Kamis, ";
        }

        if(chkJumat.isSelected()){
            jadwal += "Jumat, ";
        }

        if(chkSabtu.isSelected()){
            jadwal += "Sabtu, ";
        }

        if(chkMinggu.isSelected()){
            jadwal += "Minggu, ";
        }

        return jadwal;
    }

    // =====================================================
    // TAMPIL CHECKBOX
    // =====================================================

    private void tampilCheckbox(String jadwal){

        chkSenin.setSelected(
        jadwal.contains("Senin"));

        chkSelasa.setSelected(
        jadwal.contains("Selasa"));

        chkRabu.setSelected(
        jadwal.contains("Rabu"));

        chkKamis.setSelected(
        jadwal.contains("Kamis"));

        chkJumat.setSelected(
        jadwal.contains("Jumat"));

        chkSabtu.setSelected(
        jadwal.contains("Sabtu"));

        chkMinggu.setSelected(
        jadwal.contains("Minggu"));
    }

    // =====================================================
    // RESET FORM
    // =====================================================

    private void reset(){

        tnamapoli.setSelectedIndex(0);

        tkepalapoli.setText("");

        tlokasipoli.setText("");

        tdokterpj.setText("");

        chkSenin.setSelected(false);
        chkSelasa.setSelected(false);
        chkRabu.setSelected(false);
        chkKamis.setSelected(false);
        chkJumat.setSelected(false);
        chkSabtu.setSelected(false);
        chkMinggu.setSelected(false);

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
        jLabel13 = new javax.swing.JLabel();
        tnamapoli = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        tkepalapoli = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tlokasipoli = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        chkSenin = new javax.swing.JCheckBox();
        chkJumat = new javax.swing.JCheckBox();
        chkSelasa = new javax.swing.JCheckBox();
        chkSabtu = new javax.swing.JCheckBox();
        chkRabu = new javax.swing.JCheckBox();
        chkMinggu = new javax.swing.JCheckBox();
        chkKamis = new javax.swing.JCheckBox();
        badd = new javax.swing.JButton();
        bubah = new javax.swing.JButton();
        bhapus = new javax.swing.JButton();
        bbatal = new javax.swing.JButton();
        bkeluar = new javax.swing.JButton();
        tdokterpj = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));

        jLabel18.setFont(new java.awt.Font("News706 BT", 0, 30)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("POLI KLINIK");

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
                .addGap(583, 583, 583)
                .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bcari)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 810, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bcari))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setText("ID Poli");

        tid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidActionPerformed(evt);
            }
        });

        jLabel13.setText("Nama Poli");

        tnamapoli.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Poli Jantung", "Poli Anak", "Poli Kandungan dan Kebidanan", "Poli Gigi", "Poli Mata" }));
        tnamapoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnamapoliActionPerformed(evt);
            }
        });

        jLabel4.setText("Kepala Poli");

        jLabel5.setText("Lokasi Poli");

        jLabel16.setText("Dokter Penanggung");

        jLabel25.setText("Jadwal Operasional");

        chkSenin.setText("Senin");
        chkSenin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSeninActionPerformed(evt);
            }
        });

        chkJumat.setText("Jumat");
        chkJumat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkJumatActionPerformed(evt);
            }
        });

        chkSelasa.setText("Selasa");
        chkSelasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSelasaActionPerformed(evt);
            }
        });

        chkSabtu.setText("Sabtu");
        chkSabtu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSabtuActionPerformed(evt);
            }
        });

        chkRabu.setText("Rabu");
        chkRabu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRabuActionPerformed(evt);
            }
        });

        chkMinggu.setText("Minggu");
        chkMinggu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMingguActionPerformed(evt);
            }
        });

        chkKamis.setText("Kamis");
        chkKamis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkKamisActionPerformed(evt);
            }
        });

        badd.setBackground(new java.awt.Color(255, 255, 0));
        badd.setText("Simpan");
        badd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                baddActionPerformed(evt);
            }
        });

        bubah.setBackground(new java.awt.Color(255, 255, 0));
        bubah.setText("Ubah");
        bubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bubahActionPerformed(evt);
            }
        });

        bhapus.setBackground(new java.awt.Color(255, 255, 0));
        bhapus.setText("Hapus");
        bhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bhapusActionPerformed(evt);
            }
        });

        bbatal.setBackground(new java.awt.Color(255, 255, 0));
        bbatal.setText("Batal");
        bbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbatalActionPerformed(evt);
            }
        });

        bkeluar.setBackground(new java.awt.Color(255, 255, 0));
        bkeluar.setText("Keluar");
        bkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bkeluarActionPerformed(evt);
            }
        });

        tdokterpj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tdokterpjActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel13))
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tkepalapoli)
                            .addComponent(tid, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tlokasipoli)
                            .addComponent(tnamapoli, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkSenin)
                            .addComponent(chkJumat))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkSabtu)
                            .addComponent(chkSelasa))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(chkRabu)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chkKamis))
                            .addComponent(chkMinggu)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(31, 31, 31)
                        .addComponent(tdokterpj, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(55, 55, 55))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(badd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bubah)
                .addGap(18, 18, 18)
                .addComponent(bhapus)
                .addGap(18, 18, 18)
                .addComponent(bbatal)
                .addGap(18, 18, 18)
                .addComponent(bkeluar)
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(tid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(tnamapoli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(tkepalapoli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(tdokterpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addComponent(jLabel25)
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chkSenin)
                            .addComponent(chkSelasa)
                            .addComponent(chkRabu)
                            .addComponent(chkKamis))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chkJumat)
                            .addComponent(chkMinggu)
                            .addComponent(chkSabtu))))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tlokasipoli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bubah)
                    .addComponent(bhapus)
                    .addComponent(bbatal)
                    .addComponent(bkeluar)
                    .addComponent(badd))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblplgnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblplgnMouseClicked

        int baris =
        tblplgn.getSelectedRow();

        tid.setText(
        tblplgn.getValueAt(baris,0).toString());

        tnamapoli.setSelectedItem(
        tblplgn.getValueAt(baris,1).toString());

        tkepalapoli.setText(
        tblplgn.getValueAt(baris,2).toString());

        tlokasipoli.setText(
        tblplgn.getValueAt(baris,3).toString());

        String jadwal =
        tblplgn.getValueAt(baris,4).toString();

        tampilCheckbox(jadwal);

        tdokterpj.setText(
        tblplgn.getValueAt(baris,5).toString());   
    }//GEN-LAST:event_tblplgnMouseClicked

    private void tcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tcariActionPerformed

    private void bcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcariActionPerformed
  Object[] baris = {
            "ID Poli",
            "Nama Poli",
            "Kepala Poli",
            "Lokasi Poli",
            "Jadwal Operasional",
            "Dokter Penanggung Jawab"
        };

        tabmode =
        new DefaultTableModel(null, baris);

        tblplgn.setModel(tabmode);

        try {

            String sql =
            "SELECT * FROM poli_klinik "
            + "WHERE nama_poli LIKE '%"
            + tcari.getText()+"%'";

            st = con.createStatement();

            rs = st.executeQuery(sql);

            while(rs.next()){

                String[] data = {

                    rs.getString("id_poli"),
                    rs.getString("nama_poli"),
                    rs.getString("kepala_poli"),
                    rs.getString("lokasi_poli"),
                    rs.getString("jadwal_operasional"),
                    rs.getString("dokter_penanggung_jawab")
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

    private void chkSeninActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSeninActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkSeninActionPerformed

    private void chkJumatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkJumatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkJumatActionPerformed

    private void chkSelasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSelasaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkSelasaActionPerformed

    private void chkSabtuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSabtuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkSabtuActionPerformed

    private void chkRabuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRabuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkRabuActionPerformed

    private void chkMingguActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMingguActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkMingguActionPerformed

    private void chkKamisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkKamisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkKamisActionPerformed

    private void baddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_baddActionPerformed
        try {

            String sql =
            "INSERT INTO poli_klinik VALUES("
            + "'"+tid.getText()+"',"
            + "'"+tnamapoli.getSelectedItem()+"',"
            + "'"+tkepalapoli.getText()+"',"
            + "'"+tlokasipoli.getText()+"',"
            + "'"+getJadwal()+"',"
            + "'"+tdokterpj.getText()+"')";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
            null,
            "Data Poli Berhasil Disimpan");

            tampilData();

            reset();

            autoID();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_baddActionPerformed

    private void bubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bubahActionPerformed

        try {

            String sql =
            "UPDATE poli SET "

            + "nama_poli='"
            + tnamapoli.getSelectedItem()+"',"

            + "kepala_poli='"
            + tkepalapoli.getText()+"',"

            + "lokasi_poli='"
            + tlokasipoli.getText()+"',"

            + "jadwal_operasional='"
            + getJadwal()+"',"

            + "dokter_penanggung_jawab='"
            + tdokterpj.getText()+"' "

            + "WHERE id_poli='"
            + tid.getText()+"'";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
            null,
            "Data Poli Berhasil Diubah");

            tampilData();

            reset();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_bubahActionPerformed

    private void bhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhapusActionPerformed
  try {

            String sql =
            "DELETE FROM poli_klinik "
            + "WHERE id_poli='"
            + tid.getText()+"'";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
            null,
            "Data Poli Berhasil Dihapus");

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

    private void tdokterpjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tdokterpjActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tdokterpjActionPerformed

    private void tnamapoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnamapoliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tnamapoliActionPerformed

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
            java.util.logging.Logger.getLogger(FormPoli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormPoli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormPoli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPoli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormPoli().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton badd;
    private javax.swing.JButton bbatal;
    private javax.swing.JButton bcari;
    private javax.swing.JButton bhapus;
    private javax.swing.JButton bkeluar;
    private javax.swing.JButton bubah;
    private javax.swing.JCheckBox chkJumat;
    private javax.swing.JCheckBox chkKamis;
    private javax.swing.JCheckBox chkMinggu;
    private javax.swing.JCheckBox chkRabu;
    private javax.swing.JCheckBox chkSabtu;
    private javax.swing.JCheckBox chkSelasa;
    private javax.swing.JCheckBox chkSenin;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblplgn;
    private javax.swing.JTextField tcari;
    private javax.swing.JTextField tdokterpj;
    private javax.swing.JTextField tid;
    private javax.swing.JTextField tkepalapoli;
    private javax.swing.JTextField tlokasipoli;
    private javax.swing.JComboBox<String> tnamapoli;
    // End of variables declaration//GEN-END:variables
}
