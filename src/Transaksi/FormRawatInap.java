package Transaksi;

import Koneksi.Koneksi;
import com.toedter.calendar.JDateChooser;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormRawatInap extends javax.swing.JFrame {

    Connection con;
    Statement st;
    ResultSet rs;

    DefaultTableModel tabmode;

    public FormRawatInap() {

        initComponents();

        con = Koneksi.getKoneksi();

        tampilData();

        tampilDokter();

        autoID();

        setLocationRelativeTo(null);
    }

    // =====================================================
    // AUTO ID
    // =====================================================

private void autoID() {
    try {
        // Mengambil hanya 1 data ID rawat inap terbesar/terakhir dari database
        String sql = "SELECT id_rawat_inap FROM rawat_inap ORDER BY id_rawat_inap DESC LIMIT 1";
        st = con.createStatement();
        rs = st.executeQuery(sql);

        if (rs.next()) {
            // 1. Ambil String ID terakhir dari database, misal "RWI001"
            String idTerakhir = rs.getString("id_rawat_inap"); 
            
            // 2. Potong teks "RWI" (3 karakter), mengambil angka di belakangnya saja ("001")
            String nomorSaja = idTerakhir.substring(3); 
            
            // 3. Ubah string angka menjadi integer lalu tambah 1 (1 + 1 = 2)
            int angkaBaru = Integer.parseInt(nomorSaja) + 1; 
            
            // 4. Format kembali menjadi "RWI" diikuti 3 digit angka (misal: "RWI002")
            // %03d memastikan format angka tetap rapi 3 digit (002, 015, 100)
            String idOtomatis = "RWI" + String.format("%03d", angkaBaru);
            
            tidrawat.setText(idOtomatis);
        } else {
            // Jika tabel rawat_inap masih kosong atau habis di-reset, mulai dari RWI001
            tidrawat.setText("RWI001");
        }
    } catch (Exception e) {
        // Membantu melihat log error di console NetBeans jika terjadi kendala data
        e.printStackTrace(); 
        tidrawat.setText("RWI001");
    }
}

    // =====================================================
    // TAMPIL DOKTER
    // =====================================================

    private void tampilDokter(){

        try {

            String sql =
            "SELECT DISTINCT id_dokter "
            + "FROM pemeriksaan_dokter "
            + "WHERE tindakan_medis='rawat_inap'";

            st = con.createStatement();

            rs = st.executeQuery(sql);

            cdokter.removeAllItems();

            while(rs.next()){

                cdokter.addItem(
                rs.getString("id_dokter"));
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
            null,e);
        }
    }

    // =====================================================
    // TARIK DATA PEMERIKSAAN
    // =====================================================

    private void tampilPasien(){

        try {

            String sql =
            "SELECT * FROM pemeriksaan_dokter "
            + "WHERE id_dokter='"
            + cdokter.getSelectedItem()
            + "'";

            st = con.createStatement();

            rs = st.executeQuery(sql);

            if(rs.next()){

                tidpasien.setText(
                rs.getString("id_registrasi"));

                tidkamar.setText(
                rs.getString("id_pasien"));

                tdiagnosa.setText(
                rs.getString("diagnosa"));
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
            null,e);
        }
    }

    // =====================================================
    // HITUNG TOTAL BIAYA
    // =====================================================

    private void hitungTotal(){

        try {

            Date masuk = dmasuk.getDate();
            Date keluar = dkeluar.getDate();

            int biayaPerHari =
            Integer.parseInt(
            ttotal.getText());

            long selisih =
            keluar.getTime() -
            masuk.getTime();

            long hari =
            selisih / (1000 * 60 * 60 * 24);

            if(hari == 0){

                hari = 1;
            }

            long total =
            hari * biayaPerHari;

            ttotal.setText(
            String.valueOf(total));

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
            null,
            "Gagal menghitung total biaya");
        }
    }

    // =====================================================
    // TAMPIL DATA
    // =====================================================

    private void tampilData(){

        Object[] baris = {

            "ID Rawat",
            "ID Pasien",
            "ID Dokter",
            "ID Kamar",
            "Tanggal Masuk",
            "Tanggal Keluar",
            "Diagnosa",
            "Biaya Per Hari",
            "Total Biaya"
        };

        tabmode =
        new DefaultTableModel(null,baris);

        tblplgn.setModel(tabmode);

        try {

            String sql =
            "SELECT * FROM rawat_inap";

            st = con.createStatement();

            rs = st.executeQuery(sql);

            while(rs.next()){

                String[] data = {

                    rs.getString("id_rawat_inap"),
                    rs.getString("id_pasien"),
                    rs.getString("id_dokter"),
                    rs.getString("id_kamar"),
                    rs.getString("tanggal_masuk"),
                    rs.getString("tanggal_keluar"),
                    rs.getString("diagnosa"),
                    rs.getString("biaya_kamar"),
                    rs.getString("total_biaya")
                };

                tabmode.addRow(data);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
            null,e);
        }
    }

    // =====================================================
    // RESET
    // =====================================================

    private void reset(){

        autoID();

        tidpasien.setText("");
        tidkamar.setText("");
        tdiagnosa.setText("");
        tbiaya.setText("");
        ttotal.setText("");

        cdokter.setSelectedIndex(0);

        dmasuk.setDate(null);

        dkeluar.setDate(null);
    }
    
    
            public void actionPerformed(
            java.awt.event.ActionEvent evt) {

                try {

                    hitungTotal();

                    SimpleDateFormat fm =
                    new SimpleDateFormat(
                    "yyyy-MM-dd");

                    String masuk =
                    fm.format(
                    dmasuk.getDate());

                    String keluar =
                    fm.format(
                    dkeluar.getDate());

                    String sql =
                    "INSERT INTO rawat_inap VALUES("

                    + "'"+tidrawat.getText()+"',"

                    + "'"+tidpasien.getText()+"',"

                    + "'"+tidkamar.getText()+"',"

                    + "'"+cdokter.getSelectedItem()+"',"

                    + "'"+masuk+"',"

                    + "'"+keluar+"',"

                    + "'"+tdiagnosa.getText()+"',"

                    + "'"+tbiaya.getText()+"',"

                    + "'"+ttotal.getText()+"')";

                    PreparedStatement ps =
                    con.prepareStatement(sql);

                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(
                    null,
                    "Data Berhasil Disimpan");

                    tampilData();

                    reset();

                } catch (Exception e) {

                    JOptionPane.showMessageDialog(
                    null,e);
                }
            }
       @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblplgn = new javax.swing.JTable();
        tcari = new javax.swing.JTextField();
        bcari = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tidrawat = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cdokter = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        tidpasien = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tidkamar = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        tdiagnosa = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        dmasuk = new com.toedter.calendar.JDateChooser();
        dkeluar = new com.toedter.calendar.JDateChooser();
        jLabel17 = new javax.swing.JLabel();
        tbiaya = new javax.swing.JTextField();
        bsimpan = new javax.swing.JButton();
        bubah = new javax.swing.JButton();
        bhapus = new javax.swing.JButton();
        bbatal = new javax.swing.JButton();
        bkeluar = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        ttotal = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        tidpasien1 = new javax.swing.JTextField();
        tidpasien2 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        tidkamar1 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        tidkamar2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bcari))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 810, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bcari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));

        jLabel18.setFont(new java.awt.Font("News706 BT", 0, 30)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("RAWAT INAP");

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

        jLabel2.setText("ID Rawat Inap");

        tidrawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidrawatActionPerformed(evt);
            }
        });

        jLabel6.setText("ID Rekam Medis");

        cdokter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cdokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cdokterActionPerformed(evt);
            }
        });

        jLabel21.setText("ID Registrasi");

        tidpasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidpasienActionPerformed(evt);
            }
        });

        jLabel3.setText("ID Rawat Inap");

        tidkamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidkamarActionPerformed(evt);
            }
        });

        jLabel13.setText("Diagnosa");

        tdiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tdiagnosaActionPerformed(evt);
            }
        });

        jLabel14.setText("Jadwal Inap");

        jLabel15.setText("Tanggal Masuk");

        jLabel16.setText("Tanggal Keluar");

        jLabel17.setText("Biaya Kamar Perhari ");

        tbiaya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbiayaActionPerformed(evt);
            }
        });

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

        jLabel19.setText("Biaya Total Kamar Inap");

        ttotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ttotalActionPerformed(evt);
            }
        });

        jLabel25.setText("ID Dokter");

        tidpasien1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidpasien1ActionPerformed(evt);
            }
        });

        tidpasien2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidpasien2ActionPerformed(evt);
            }
        });

        jLabel26.setText("Nama Dokter");

        jLabel20.setText("Nama Pasien");

        tidkamar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidkamar1ActionPerformed(evt);
            }
        });

        jLabel27.setText("ID Pasien");

        tidkamar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidkamar2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tdiagnosa, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel26))
                                .addGap(63, 63, 63)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tidpasien2)
                                    .addComponent(tidpasien1)
                                    .addComponent(tidrawat, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tidpasien)
                                    .addComponent(cdokter, 0, 178, Short.MAX_VALUE))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel14)
                                        .addGap(115, 115, 115))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel17)
                                            .addComponent(jLabel15)
                                            .addComponent(jLabel16)
                                            .addComponent(jLabel19))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(dmasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(dkeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tbiaya, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(ttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(89, 89, 89))))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tidkamar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(bsimpan)
                                        .addGap(18, 18, 18)
                                        .addComponent(bubah)
                                        .addGap(18, 18, 18)
                                        .addComponent(bhapus)
                                        .addGap(18, 18, 18)
                                        .addComponent(bbatal)
                                        .addGap(18, 18, 18)
                                        .addComponent(bkeluar))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(13, 13, 13)
                                        .addComponent(jLabel27)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tidkamar2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tidkamar1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(39, 39, 39))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tidrawat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(tidkamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cdokter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tidpasien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addGap(54, 54, 54))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel15)
                                .addGap(21, 21, 21)
                                .addComponent(jLabel16))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(163, 163, 163)
                                .addComponent(jLabel14)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(86, 86, 86)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel17)
                                            .addComponent(tbiaya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tidpasien1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel25)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(dmasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(40, 40, 40))
                                        .addComponent(dkeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tidpasien2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel19)
                                .addComponent(ttotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel26))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(tidkamar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tidkamar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bubah)
                            .addComponent(bhapus)
                            .addComponent(bbatal)
                            .addComponent(bkeluar)
                            .addComponent(bsimpan))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(tdiagnosa, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblplgnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblplgnMouseClicked
        try {

            int baris =
            tblplgn.getSelectedRow();

            tidrawat.setText(
            tblplgn.getValueAt(baris,0).toString());

            tidpasien.setText(
            tblplgn.getValueAt(baris,1).toString());

            tidpasien.setText(
            tblplgn.getValueAt(baris,2).toString());

            cdokter.setSelectedItem(
            tblplgn.getValueAt(baris,3).toString());

            tidkamar.setText(
            tblplgn.getValueAt(baris,4).toString());

            SimpleDateFormat fm =
            new SimpleDateFormat(
            "yyyy-MM-dd");

            dmasuk.setDate(
            fm.parse(
            tblplgn.getValueAt(baris,5).toString()));

            dkeluar.setDate(
            fm.parse(
            tblplgn.getValueAt(baris,6).toString()));

            tdiagnosa.setText(
            tblplgn.getValueAt(baris,7).toString());

            tbiaya.setText(
            tblplgn.getValueAt(baris,8).toString());

            ttotal.setText(
            tblplgn.getValueAt(baris,9).toString());

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
            null,
            e);
        } 
    }//GEN-LAST:event_tblplgnMouseClicked

    private void tcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tcariActionPerformed

    private void bcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcariActionPerformed
        try {

            String sql =
            "SELECT * FROM rawat_inap "
            + "WHERE id_rawat_inap LIKE '%"
            + tcari.getText()+"%' "

            + "OR id_pasien LIKE '%"
            + tcari.getText()+"%' "

            + "OR id_dokter LIKE '%"
            + tcari.getText()+"%'";

            st = con.createStatement();

            rs = st.executeQuery(sql);

            while(rs.next()){

                String[] data = {

                    rs.getString("id_rawat"),
                    rs.getString("id_pasien"),
                    rs.getString("id_dokter"),
                    rs.getString("id_kamar"),
                    rs.getString("tanggal_masuk"),
                    rs.getString("tanggal_keluar"),
                    rs.getString("diagnosa"),
                    rs.getString("biaya_kamar"),
                    rs.getString("total_biaya")
                };

                tabmode.addRow(data);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
            null,
            e);
        }
    }//GEN-LAST:event_bcariActionPerformed

    private void tidrawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidrawatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tidrawatActionPerformed

    private void tidpasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidpasienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tidpasienActionPerformed

    private void tidkamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidkamarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tidkamarActionPerformed

    private void tdiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tdiagnosaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tdiagnosaActionPerformed

    private void cdokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cdokterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cdokterActionPerformed

    private void tbiayaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbiayaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbiayaActionPerformed

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpanActionPerformed
        try {

            hitungTotal();

            SimpleDateFormat fm =
            new SimpleDateFormat(
            "yyyy-MM-dd");

            String masuk =
            fm.format(
            dmasuk.getDate());

            String keluar =
            fm.format(
            dkeluar.getDate());

            String sql =
            "INSERT INTO rawat_inap VALUES("

            + "'"+tidrawat.getText()+"',"

            + "'"+tidpasien.getText()+"',"

            + "'"+tidpasien.getText()+"',"

            + "'"+cdokter.getSelectedItem()+"',"

            + "'"+tidkamar.getText()+"',"

            + "'"+masuk+"',"

            + "'"+keluar+"',"

            + "'"+tdiagnosa.getText()+"',"

            + "'"+tbiaya.getText()+"',"

            + "'"+ttotal.getText()+"')";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
            null,
            "Data Berhasil Disimpan");

            tampilData();

            reset();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
            null,
            e);
        }
    }//GEN-LAST:event_bsimpanActionPerformed

    private void bubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bubahActionPerformed

        try {

            hitungTotal();

            SimpleDateFormat fm =
            new SimpleDateFormat(
            "yyyy-MM-dd");

            String masuk =
            fm.format(
            dmasuk.getDate());

            String keluar =
            fm.format(
            dkeluar.getDate());

            String sql =
            "UPDATE rawat_inap SET "

            + "id_pasien='"
            + tidpasien.getText()+"',"

            + "id_kamar='"
            + tidkamar.getText()+"',"

            + "id_dokter='"
            + cdokter.getSelectedItem()+"',"

            + "tanggal_masuk='"
            + masuk+"',"

            + "tanggal_keluar='"
            + keluar+"',"

            + "diagnosa='"
            + tdiagnosa.getText()+"',"

            + "biaya_kamar='"
            + tbiaya.getText()+"',"

            + "total_biaya='"
            + ttotal.getText()+"' "

            + "WHERE id_rawat='"
            + tidrawat.getText()+"'";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
            null,
            "Data Berhasil Diubah");

            tampilData();

            reset();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
            null,
            e);
        }
    }//GEN-LAST:event_bubahActionPerformed

    private void bhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhapusActionPerformed

        try {

            int jawab =
            JOptionPane.showConfirmDialog(
            null,
            "Yakin ingin menghapus data?",
            "Konfirmasi",
            JOptionPane.YES_NO_OPTION);

            if(jawab == JOptionPane.YES_OPTION){

                String sql =
                "DELETE FROM rawat_inap "
                + "WHERE id_rawat_inap'"
                + tidrawat.getText()+"'";

                PreparedStatement ps =
                con.prepareStatement(sql);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(
                null,
                "Data Berhasil Dihapus");

                tampilData();

                reset();
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
            null,
            e);
        }  
    }//GEN-LAST:event_bhapusActionPerformed

    private void bbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatalActionPerformed
        reset();
    }//GEN-LAST:event_bbatalActionPerformed

    private void bkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bkeluarActionPerformed
        dispose();
    }//GEN-LAST:event_bkeluarActionPerformed

    private void ttotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ttotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ttotalActionPerformed

    private void tidpasien1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidpasien1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tidpasien1ActionPerformed

    private void tidpasien2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidpasien2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tidpasien2ActionPerformed

    private void tidkamar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidkamar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tidkamar1ActionPerformed

    private void tidkamar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidkamar2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tidkamar2ActionPerformed

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
            java.util.logging.Logger.getLogger(FormRawatInap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormRawatInap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormRawatInap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormRawatInap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormRawatInap().setVisible(true);
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
    private javax.swing.JComboBox<String> cdokter;
    private com.toedter.calendar.JDateChooser dkeluar;
    private com.toedter.calendar.JDateChooser dmasuk;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField tbiaya;
    private javax.swing.JTable tblplgn;
    private javax.swing.JTextField tcari;
    private javax.swing.JTextField tdiagnosa;
    private javax.swing.JTextField tidkamar;
    private javax.swing.JTextField tidkamar1;
    private javax.swing.JTextField tidkamar2;
    private javax.swing.JTextField tidpasien;
    private javax.swing.JTextField tidpasien1;
    private javax.swing.JTextField tidpasien2;
    private javax.swing.JTextField tidrawat;
    private javax.swing.JTextField ttotal;
    // End of variables declaration//GEN-END:variables
}
