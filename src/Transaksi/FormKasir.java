package Transaksi;

import Koneksi.Koneksi;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormKasir extends javax.swing.JFrame {

    Connection con;
    Statement st;
    ResultSet rs;
    DefaultTableModel tabmode;

public FormKasir() {

    initComponents();

    con = Koneksi.getKoneksi();

    tampilResep();

    tampilData();

    autoIDKasir();

    }

    // =====================================================
    // FORMAT RUPIAH
    // =====================================================

    private String rupiah(int angka){

        Locale localeID = new Locale("id","ID");

        NumberFormat format =
        NumberFormat.getInstance(localeID);

        return "Rp. " + format.format(angka);
    }

    // =====================================================
    // HAPUS FORMAT RUPIAH
    // =====================================================

    private int ambilAngka(String nilai){

        nilai = nilai.replace("Rp.","")
                     .replace(".","")
                     .replace(",","")
                     .trim();

        return Integer.parseInt(nilai);
    }

    // =====================================================
    // AUTO ID KASIR
    // =====================================================

    private void autoIDKasir(){

        try{

            String sql =
            "SELECT id_kasir FROM kasir "
            + "ORDER BY id_kasir DESC LIMIT 1";

            st = con.createStatement();

            rs = st.executeQuery(sql);

            if(rs.next()){

                String id =
                rs.getString("id_kasir")
                .substring(3);

                int angka =
                Integer.parseInt(id)+1;

                tidkasir.setText(
                "KSR"+String.format("%03d", angka));

            }else{

                tidkasir.setText("KSR001");
            }

        }catch(Exception e){

            tidkasir.setText("KSR001");
        }
    }

    // =====================================================
    // TAMPIL RESEP
    // =====================================================

 private void tampilResep(){

        try {

            cidresep.removeAllItems();

            String sql =
            "SELECT DISTINCT id_resep FROM farmasi ORDER BY id_resep";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {

                cidresep.addItem(
                rs.getString("id_resep"));

                System.out.println(
                "ID Resep : " +
                rs.getString("id_resep"));
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
            null,
            "Error tampil resep : " + e);
        }
      
    }
    // =====================================================
    // TARIK DATA
    // =====================================================
    private void tarikData(){
    try {

        if(cidresep.getSelectedItem() == null){
            return;
        }

        String resep =
        cidresep.getSelectedItem().toString();

        // =====================================
        // FARMASI
        // =====================================

        String sql =
        "SELECT * FROM farmasi "
        + "WHERE id_resep='"+resep+"'";

        st = con.createStatement();

        rs = st.executeQuery(sql);

        if(rs.next()){

            tidrekam.setText(
            rs.getString("id_rekam_medis"));

            tidregistrasi.setText(
            rs.getString("id_registrasi"));

            tnamapasien.setText(
            rs.getString("nama_pasien"));
            
            tmetode.setText(
            rs.getString("metode_pembayaran"));


            int obat =
            rs.getInt("total_bayar");

            tbiayaobat.setText(
            rupiah(obat));
        }

        // =====================================
        // PEMERIKSAAN DOKTER
        // =====================================

        String sql2 =
        "SELECT * FROM pemeriksaan_dokter " +
        "WHERE id_rekam_medis='" +
        tidrekam.getText() + "'";

        rs = st.executeQuery(sql2);

        if(rs.next()){

            tidpasien.setText(
            rs.getString("id_pasien"));

            int dokter =
            rs.getInt("biaya_dokter");

            tbiayadokter.setText(
            rupiah(dokter));
        }

        // =====================================
        // RAWAT INAP
        // =====================================
        String sql3 =
        "SELECT * FROM rawat_inap "
        + "WHERE id_rekam_medis='"
        + tidrekam.getText()+"'";

        rs = st.executeQuery(sql3);

        if(rs.next()){

            tidrawatinap.setText(
            rs.getString("id_rawat_inap"));

            int kamar =
            rs.getInt("biaya_kamar");

            tbiayakamar.setText(
            rupiah(kamar));

        }else{

            tidrawatinap.setText("-");

            tbiayakamar.setText("Rp. 0");
        }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
            null,
            "Error Tarik Data : "+e);
        }
  
}   
    // =====================================================
    // HITUNG TOTAL
    // =====================================================

    private void hitungTotal(){

        int obat =
        ambilAngka(
        tbiayaobat.getText());

        int dokter =
        ambilAngka(
        tbiayadokter.getText());

        int kamar =
        ambilAngka(
        tbiayakamar.getText());

        int total =
        obat + dokter + kamar;

        ttotal.setText(
        rupiah(total));
    }

    // =====================================================
    // TAMPIL DATA
    // =====================================================

    private void tampilData(){

        Object[] baris = {

            "ID Kasir",
            "ID Resep",
            "ID Rekam",
            "ID Registrasi",
            "ID Pasien",
            "Nama Pasien",
            "ID Rawat Inap",
            "Biaya Obat",
            "Biaya Dokter",
            "Biaya Kamar",
            "Total"
        };

        tabmode =
        new DefaultTableModel(null,baris);

        tblplgn.setModel(tabmode);

        try{

            String sql =
            "SELECT * FROM kasir";

            st = con.createStatement();

            rs = st.executeQuery(sql);

            while(rs.next()){

                String[] data={

                    rs.getString("id_kasir"),
                    rs.getString("id_resep"),
                    rs.getString("id_rekam_medis"),
                    rs.getString("id_registrasi"),
                    rs.getString("id_pasien"),
                    rs.getString("nama_pasien"),
                    rs.getString("id_rawat_inap"),
                    rupiah(rs.getInt("biaya_obat")),
                    rupiah(rs.getInt("biaya_dokter")),
                    rupiah(rs.getInt("biaya_kamar")),
                    rupiah(rs.getInt("total_bayar"))
                };

                tabmode.addRow(data);
            }

        }catch(Exception e){

            JOptionPane.showMessageDialog(null,e);
        }
    }

    // =====================================================
    // SIMPAN
    // =====================================================

    private void simpanData(){

        try{

            int obat =
            ambilAngka(
            tbiayaobat.getText());

            int dokter =
            ambilAngka(
            tbiayadokter.getText());

            int kamar =
            ambilAngka(
            tbiayakamar.getText());

            int total =
            ambilAngka(
            ttotal.getText());

            String sql =
            "INSERT INTO kasir VALUES(?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.setString(1,
            tidkasir.getText());

            ps.setString(2,
            cidresep.getSelectedItem().toString());

            ps.setString(3,
            tidrekam.getText());

            ps.setString(4,
            tidregistrasi.getText());

            ps.setString(5,
            tidpasien.getText());

            ps.setString(6,
            tnamapasien.getText());

            ps.setString(7,
            tidrawatinap.getText());

            ps.setInt(8,obat);

            ps.setInt(9,dokter);

            ps.setInt(10,kamar);

            ps.setInt(11,total);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
            null,
            "Data berhasil disimpan");

            tampilData();

            reset();

        }catch(Exception e){

            JOptionPane.showMessageDialog(null,e);
        }
    }

    // =====================================================
    // RESET
    // =====================================================

    private void reset(){

        tidrekam.setText("");
        tidregistrasi.setText("");
        tidpasien.setText("");
        tnamapasien.setText("");
        tidrawatinap.setText("");
        tbiayaobat.setText("");
        tbiayadokter.setText("");
        tbiayakamar.setText("");
        ttotal.setText("");

        autoIDKasir();
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
        tidkasir = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cidresep = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tidrekam = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tidregistrasi = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        tidrawatinap = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tbiayadokter = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        tbiayaobat = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        tbiayakamar = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        tmetode = new javax.swing.JTextField();
        ttotal = new javax.swing.JTextField();
        bsimpan = new javax.swing.JButton();
        bubah = new javax.swing.JButton();
        bhapus = new javax.swing.JButton();
        bbatal = new javax.swing.JButton();
        bkeluar = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        tnamapasien = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        tidpasien = new javax.swing.JTextField();
        bhitung = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));

        jLabel18.setFont(new java.awt.Font("News706 BT", 0, 30)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("KASIR");

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
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

        jLabel2.setText("ID Kasir");

        tidkasir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidkasirActionPerformed(evt);
            }
        });

        jLabel6.setText("ID Resep");

        cidresep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cidresep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cidresepActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("TOTAL BAYAR");

        jLabel7.setText("ID Rekam Medis");

        tidrekam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidrekamActionPerformed(evt);
            }
        });

        jLabel8.setText("ID Registrasi");

        tidregistrasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidregistrasiActionPerformed(evt);
            }
        });

        jLabel10.setText("ID Rawat Inap");

        tidrawatinap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidrawatinapActionPerformed(evt);
            }
        });

        jLabel11.setText("Biaya Dokter");

        tbiayadokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbiayadokterActionPerformed(evt);
            }
        });

        jLabel12.setText("Biaya Obat");

        tbiayaobat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbiayaobatActionPerformed(evt);
            }
        });

        jLabel13.setText("Biaya Kamar");

        tbiayakamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbiayakamarActionPerformed(evt);
            }
        });

        jLabel14.setText("Metode Pembayaran");

        tmetode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmetodeActionPerformed(evt);
            }
        });

        ttotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ttotalActionPerformed(evt);
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

        jLabel15.setText("Nama Pasien");

        tnamapasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnamapasienActionPerformed(evt);
            }
        });

        jLabel16.setText("ID Pasien");

        tidpasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidpasienActionPerformed(evt);
            }
        });

        bhitung.setBackground(new java.awt.Color(0, 255, 51));
        bhitung.setText("Hitung");
        bhitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bhitungActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15))
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tnamapasien)
                        .addGap(495, 495, 495))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(tidrawatinap, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tidregistrasi, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tidrekam, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cidresep, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tidkasir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                            .addComponent(tidpasien))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(149, 149, 149)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel11)
                                                    .addComponent(jLabel12))
                                                .addGap(63, 63, 63)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(tbiayadokter)
                                                    .addComponent(tbiayaobat)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel13)
                                                .addGap(63, 63, 63)
                                                .addComponent(tbiayakamar)))
                                        .addGap(109, 109, 109))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(ttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(148, 148, 148))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(147, 147, 147)
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18)
                                .addComponent(tmetode, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bsimpan)
                .addGap(18, 18, 18)
                .addComponent(bubah)
                .addGap(18, 18, 18)
                .addComponent(bhapus)
                .addGap(18, 18, 18)
                .addComponent(bbatal)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bhitung)
                    .addComponent(bkeluar))
                .addGap(50, 50, 50))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tidkasir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cidresep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGap(20, 20, 20))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(tidrekam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(tidregistrasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(tidrawatinap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tidpasien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(tnamapasien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tmetode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(tbiayaobat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(tbiayadokter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(tbiayakamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(59, 59, 59)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(ttotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bhitung))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bubah)
                    .addComponent(bhapus)
                    .addComponent(bbatal)
                    .addComponent(bkeluar)
                    .addComponent(bsimpan))
                .addGap(31, 31, 31)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblplgnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblplgnMouseClicked
    int baris =
    tblplgn.getSelectedRow();

    tidkasir.setText(
    tblplgn.getValueAt(baris,0).toString());

    cidresep.setSelectedItem(
    tblplgn.getValueAt(baris,1).toString());

    tidrekam.setText(
    tblplgn.getValueAt(baris,2).toString());

    tidregistrasi.setText(
    tblplgn.getValueAt(baris,3).toString());

    tidpasien.setText(
    tblplgn.getValueAt(baris,4).toString());

    tnamapasien.setText(
    tblplgn.getValueAt(baris,5).toString());

    tidrawatinap.setText(
    tblplgn.getValueAt(baris,6).toString());

    tbiayadokter.setText(
    tblplgn.getValueAt(baris,7).toString());

    tbiayaobat.setText(
    tblplgn.getValueAt(baris,8).toString());

    tbiayakamar.setText(
    tblplgn.getValueAt(baris,9).toString());

    ttotal.setText(
    tblplgn.getValueAt(baris,10).toString());

    tmetode.setText(
    tblplgn.getValueAt(baris,11).toString());
    }//GEN-LAST:event_tblplgnMouseClicked

    private void tcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tcariActionPerformed

    private void bcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcariActionPerformed
    Object[] baris = {

        "ID Kasir",
        "ID Resep",
        "ID Rekam",
        "ID Registrasi",
        "ID Pasien",
        "Nama Pasien",
        "ID Kamar",
        "Biaya Dokter",
        "Biaya Obat",
        "Biaya Kamar",
        "Total",
        "Asuransi"
    };

    tabmode =
    new DefaultTableModel(null,baris);

    tblplgn.setModel(tabmode);

    try {

        String sql =
        "SELECT * FROM kasir "
        + "WHERE nama_pasien LIKE '%"
        + tcari.getText()+"%'";

        st = con.createStatement();

        rs = st.executeQuery(sql);

        while(rs.next()){

            String[] data = {

                rs.getString("id_kasir"),
                rs.getString("id_resep"),
                rs.getString("id_rekam_medis"),
                rs.getString("id_registrasi"),
                rs.getString("id_pasien"),
                rs.getString("nama_pasien"),
                rs.getString("id_kamar"),
                rs.getString("biaya_dokter"),
                rs.getString("biaya_obat"),
                rs.getString("biaya_kamar"),
                rs.getString("total_tagihan"),
                rs.getString("jenis_asuransi")
            };

            tabmode.addRow(data);
        }

    } catch (Exception e) {

        JOptionPane.showMessageDialog(null,e);
    }
    }//GEN-LAST:event_bcariActionPerformed

    private void tidkasirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidkasirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tidkasirActionPerformed

    private void cidresepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cidresepActionPerformed
    tarikData();
    }//GEN-LAST:event_cidresepActionPerformed

    private void tidrekamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidrekamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tidrekamActionPerformed

    private void tidregistrasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidregistrasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tidregistrasiActionPerformed

    private void tidrawatinapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidrawatinapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tidrawatinapActionPerformed

    private void tbiayadokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbiayadokterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbiayadokterActionPerformed

    private void tbiayaobatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbiayaobatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbiayaobatActionPerformed

    private void tbiayakamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbiayakamarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbiayakamarActionPerformed

    private void tmetodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmetodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tmetodeActionPerformed

    private void ttotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ttotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ttotalActionPerformed

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpanActionPerformed
    try {

        int dokter =
        ambilAngka(
        tbiayadokter.getText());

        int obat =
        ambilAngka(
        tbiayaobat.getText());

        int kamar =
        ambilAngka(
        tbiayakamar.getText());

        int total =
        ambilAngka(
        ttotal.getText());
        System.out.println(total);
        
        String sql =
        "INSERT INTO kasir VALUES("
        + "'"+tidkasir.getText()+"',"
        + "'"+tidregistrasi.getText()+"',"
        + "'"+tidpasien.getText()+"',"
        + "'"+tidrawatinap.getText()+"',"
        + "'"+dokter+"',"
        + "'"+obat+"',"

        + "'"+cidresep.getSelectedItem()+"',"
        + "'"+kamar+"',"
        +"'"+total+"',"
        + "'"+ tmetode.getText()+"',"
        + "'"+tidrekam.getText()+"',"
        + "'"+tnamapasien.getText()+"'"
        + ")";

        PreparedStatement ps =
        con.prepareStatement(sql);

        ps.executeUpdate();

        JOptionPane.showMessageDialog(
        null,
        "Data Berhasil Disimpan");

        tampilData();

        reset();

    } catch (SQLException e) {
        System.out.println(e.toString());
        JOptionPane.showMessageDialog(
        null,e);
    }
    }//GEN-LAST:event_bsimpanActionPerformed

    private void bubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bubahActionPerformed
    try {

        String sql =
        "UPDATE kasir SET "

        + "id_resep='"+cidresep.getSelectedItem()+"',"

        + "id_rekam_medis='"+tidrekam.getText()+"',"

        + "id_registrasi='"+tidregistrasi.getText()+"',"

        + "id_pasien='"+tidpasien.getText()+"',"

        + "nama_pasien='"+tnamapasien.getText()+"',"

        + "id_kamar='"+tidrawatinap.getText()+"',"

        + "biaya_dokter='"+tbiayadokter.getText()+"',"

        + "biaya_obat='"+tbiayaobat.getText()+"',"

        + "biaya_kamar='"+tbiayakamar.getText()+"',"

        + "total_tagihan='"+ttotal.getText()+"',"

        + "jenis_asuransi='"+tmetode.getText()+"' "

        + "WHERE id_kasir='"
        + tidkasir.getText()+"'";

        PreparedStatement ps =
        con.prepareStatement(sql);

        ps.executeUpdate();

        JOptionPane.showMessageDialog(
        null,
        "Data Berhasil Diubah");

        tampilData();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(null,e);
    }
    }//GEN-LAST:event_bubahActionPerformed

    private void bhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhapusActionPerformed
    try {

        String sql =
        "DELETE FROM kasir "
        + "WHERE id_kasir='"
        + tidkasir.getText()+"'";

        PreparedStatement ps =
        con.prepareStatement(sql);

        ps.executeUpdate();

        JOptionPane.showMessageDialog(
        null,
        "Data Berhasil Dihapus");

        tampilData();

        reset();

        autoID();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(null,e);
    }
    }//GEN-LAST:event_bhapusActionPerformed

    private void bbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatalActionPerformed
        reset();
    }//GEN-LAST:event_bbatalActionPerformed

    private void bkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bkeluarActionPerformed
        dispose();
    }//GEN-LAST:event_bkeluarActionPerformed

    private void tnamapasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnamapasienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tnamapasienActionPerformed

    private void tidpasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidpasienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tidpasienActionPerformed

    private void bhitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhitungActionPerformed
    hitungTotal();
    }//GEN-LAST:event_bhitungActionPerformed

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
            java.util.logging.Logger.getLogger(FormKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormKasir().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bbatal;
    private javax.swing.JButton bcari;
    private javax.swing.JButton bhapus;
    private javax.swing.JButton bhitung;
    private javax.swing.JButton bkeluar;
    private javax.swing.JButton bsimpan;
    private javax.swing.JButton bubah;
    private javax.swing.JComboBox<String> cidresep;
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField tbiayadokter;
    private javax.swing.JTextField tbiayakamar;
    private javax.swing.JTextField tbiayaobat;
    private javax.swing.JTable tblplgn;
    private javax.swing.JTextField tcari;
    private javax.swing.JTextField tidkasir;
    private javax.swing.JTextField tidpasien;
    private javax.swing.JTextField tidrawatinap;
    private javax.swing.JTextField tidregistrasi;
    private javax.swing.JTextField tidrekam;
    private javax.swing.JTextField tmetode;
    private javax.swing.JTextField tnamapasien;
    private javax.swing.JTextField ttotal;
    // End of variables declaration//GEN-END:variables

    private void autoID() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
