package Transaksi;

import Koneksi.Koneksi;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormFarmasi extends javax.swing.JFrame {

    Connection con;
    Statement st;
    ResultSet rs;

    DefaultTableModel tabmode;

        public FormFarmasi() {

           initComponents();

           con = Koneksi.getKoneksi();

           autoID();

           tampilRekamMedis();

           tampilObat();

           if(crekam.getItemCount() > 0){
               crekam.setSelectedIndex(0);
           }

           tampilData();

           setLocationRelativeTo(null);
       }

    // =====================================================
    // FORMAT RUPIAH
    // =====================================================
    private String formatAngka(int angka){

        return String.valueOf(angka);
    }
    // =====================================================
    // AMBIL ANGKA DARI RUPIAH
    // =====================================================

    private int ambilAngka(String text){
        try{

            if(text == null || text.equals("")){
                return 0;
            }

            // Ambil hanya angka
            String angka =
            text.replaceAll("[^0-9]", "");

            if(angka.equals("")){
                return 0;
            }

            return Integer.parseInt(angka);

        }catch(Exception e){

            return 0;
        }
    }

    // =====================================================
    // AUTO ID RESEP
    // =====================================================

    private void autoID() {

        try {

            String sql =
            "SELECT id_resep FROM farmasi "
            + "ORDER BY id_resep DESC LIMIT 1";

            st = con.createStatement();

            rs = st.executeQuery(sql);

            if(rs.next()){

                String id =
                rs.getString("id_resep")
                .substring(3);

                int urut =
                Integer.parseInt(id)+1;

                tidresep.setText(
                "FRM"+
                String.format("%03d", urut));

            } else {

                tidresep.setText("FRM001");
            }

        } catch (Exception e) {

            tidresep.setText("FRM001");
        }
    }

    // =====================================================
    // TAMPIL ID REKAM MEDIS
    // =====================================================

    private void tampilRekamMedis(){

        try {

            String sql =
            "SELECT DISTINCT id_rekam_medis "
            + "FROM pemeriksaan_dokter";

            st = con.createStatement();

            rs = st.executeQuery(sql);

            crekam.removeAllItems();

            while(rs.next()){

                crekam.addItem(
                rs.getString("id_rekam_medis"));
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
            null,e);
        }
    }

    // =====================================================
    // TAMPIL DATA PASIEN
    // =====================================================

    private void tampilPasien(){

        try {

            if(crekam.getSelectedItem() == null){
                return;
            }

            String sql =
            "SELECT * FROM pemeriksaan_dokter "
            + "WHERE id_rekam_medis=?";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.setString(1,
            crekam.getSelectedItem().toString());

            ResultSet rs2 = ps.executeQuery();

            if(rs2.next()){

                tidregistrasi.setText(
                rs2.getString("id_registrasi"));

                tnamapasien.setText(
                rs2.getString("nama_pasien"));
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
            null,e);
        }
    }

    // =====================================================
    // TAMPIL OBAT
    // =====================================================

    private void tampilObat(){

        try {

            String sql =
            "SELECT nama_obat FROM obat";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ResultSet rs2 =
            ps.executeQuery();

            cobat1.removeAllItems();
            cobat2.removeAllItems();
            cobat3.removeAllItems();
            cobat4.removeAllItems();

            while(rs2.next()){

                String obat =
                rs2.getString("nama_obat");

                cobat1.addItem(obat);
                cobat2.addItem(obat);
                cobat3.addItem(obat);
                cobat4.addItem(obat);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
            null,e);
        }
    }

    // =====================================================
    // TAMPIL HARGA
    // =====================================================

    private void tampilHarga(
    javax.swing.JComboBox<String> cobat,
    javax.swing.JTextField tharga){

        try {

            if(cobat.getSelectedItem() == null){
                return;
            }

            String sql =
            "SELECT harga_jual FROM obat "
            + "WHERE nama_obat=?";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.setString(1,
            cobat.getSelectedItem().toString());

            ResultSet rs2 =
            ps.executeQuery();

            if(rs2.next()){

                int harga =
                rs2.getInt("harga_jual");

                tharga.setText(
                String.valueOf(harga));

                hitungTotal();
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
            null,e);
        }
    }

    // =====================================================
    // HITUNG SUBTOTAL
    // =====================================================

    private int subtotal(
    javax.swing.JTextField harga,
    javax.swing.JTextField qty,
    javax.swing.JTextField sub){

        try {

            int h =
            ambilAngka(harga.getText());

            int q = 0;

            if(!qty.getText().equals("")){

                q =
                Integer.parseInt(
                qty.getText());
            }

            int total = h * q;

            sub.setText(
            String.valueOf(total));

            return total;

        } catch (Exception e) {

            sub.setText("0");

            return 0;
        }
    }

    // =====================================================
    // HITUNG TOTAL
    // =====================================================

    private void hitungTotal(){

        int s1 =
        subtotal(tharga1,tqty1,tsub1);

        int s2 =
        subtotal(tharga2,tqty2,tsub2);

        int s3 =
        subtotal(tharga3,tqty3,tsub3);

        int s4 =
        subtotal(tharga4,tqty4,tsub4);

        int total =
        s1+s2+s3+s4;

        int diskon = 0;

        if(tmetode.getSelectedItem()
        .toString().equalsIgnoreCase("Transfer")){

            diskon = 5;
        }

         tdiskon.setText(String.valueOf(diskon));

        int potongan =
        total*diskon/100;

        int bayar =
        total-potongan;

        ttotal.setText(
        String.valueOf(bayar));
    }

    // =====================================================
    // TAMPIL DATA
    // =====================================================

    private void tampilData(){

        Object[] baris = {

            "ID Resep",
            "ID Registrasi",
            "ID Rekam Medis",
            "Nama Pasien",
            "Tanggal",
            "Obat 1",
            "Obat 2",
            "Obat 3",
            "Obat 4",
            "Total",
            "Pembayaran"
        };

        tabmode =
        new DefaultTableModel(null,baris);

        tblplgn.setModel(tabmode);

        try {

            String sql =
            "SELECT * FROM farmasi";

            st = con.createStatement();

            rs = st.executeQuery(sql);

            while(rs.next()){

                String[] data = {

                    rs.getString("id_resep"),
                    rs.getString("id_registrasi"),
                    rs.getString("id_rekam_medis"),
                    rs.getString("nama_pasien"),
                    rs.getString("tanggal_resep"),
                    rs.getString("obat1"),
                    rs.getString("obat2"),
                    rs.getString("obat3"),
                    rs.getString("obat4"),
                    rs.getString("total_bayar"),
                    rs.getString("metode_pembayaran")
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

        tidregistrasi.setText("");
        tnamapasien.setText("");
        tdiskon.setText("");
        ttotal.setText("");

        tharga1.setText("");
        tharga2.setText("");
        tharga3.setText("");
        tharga4.setText("");

        tqty1.setText("");
        tqty2.setText("");
        tqty3.setText("");
        tqty4.setText("");

        tsub1.setText("");
        tsub2.setText("");
        tsub3.setText("");
        tsub4.setText("");

        dtanggal.setDate(null);
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
        tidresep = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        tnamapasien = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        crekam = new javax.swing.JComboBox<>();
        dtanggal = new com.toedter.calendar.JDateChooser();
        tidregistrasi = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tdiskon = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tharga2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tqty2 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        tsub2 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        tharga3 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        tqty3 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        tsub3 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        tharga4 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        tqty4 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        tsub4 = new javax.swing.JTextField();
        tqty1 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        tharga1 = new javax.swing.JTextField();
        tsub1 = new javax.swing.JTextField();
        ttotal = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        bsimpan = new javax.swing.JButton();
        bubah = new javax.swing.JButton();
        bhapus = new javax.swing.JButton();
        bbatal = new javax.swing.JButton();
        bkeluar = new javax.swing.JButton();
        cobat1 = new javax.swing.JComboBox<>();
        cobat2 = new javax.swing.JComboBox<>();
        cobat3 = new javax.swing.JComboBox<>();
        cobat4 = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        tmetode = new javax.swing.JComboBox<>();
        bhitung = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));

        jLabel18.setFont(new java.awt.Font("News706 BT", 0, 30)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("TRANSAKSI FARMASI");

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
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bcari))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 810, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bcari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        jLabel2.setText("ID Resep");

        tidresep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidresepActionPerformed(evt);
            }
        });

        jLabel6.setText("ID Rekam Medis");

        jLabel21.setText("Nama Pasien");

        tnamapasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnamapasienActionPerformed(evt);
            }
        });

        jLabel3.setText("Tanggal Resep");

        jLabel13.setText("Metode Pembayaran");

        crekam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        crekam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crekamActionPerformed(evt);
            }
        });

        tidregistrasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidregistrasiActionPerformed(evt);
            }
        });

        jLabel4.setText("Diskon");

        tdiskon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tdiskonActionPerformed(evt);
            }
        });

        jLabel7.setText("Harga Obat");

        tharga2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tharga2ActionPerformed(evt);
            }
        });

        jLabel8.setText("Qty");

        tqty2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tqty2ActionPerformed(evt);
            }
        });

        jLabel10.setText("Subtotal Harga");

        tsub2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tsub2ActionPerformed(evt);
            }
        });

        jLabel12.setText("Harga Obat");

        tharga3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tharga3ActionPerformed(evt);
            }
        });

        jLabel14.setText("Qty");

        tqty3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tqty3ActionPerformed(evt);
            }
        });

        jLabel15.setText("Subtotal Harga");

        tsub3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tsub3ActionPerformed(evt);
            }
        });

        jLabel17.setText("Harga Obat");

        tharga4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tharga4ActionPerformed(evt);
            }
        });

        jLabel19.setText("Qty");

        tqty4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tqty4ActionPerformed(evt);
            }
        });

        jLabel20.setText("Subtotal Harga");

        tsub4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tsub4ActionPerformed(evt);
            }
        });

        tqty1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tqty1ActionPerformed(evt);
            }
        });

        jLabel25.setText("Nama Obat");

        jLabel26.setText("Subtotal Harga");

        jLabel27.setText("Qty");

        jLabel28.setText("Harga Obat");

        tharga1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tharga1ActionPerformed(evt);
            }
        });

        tsub1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tsub1ActionPerformed(evt);
            }
        });

        ttotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ttotalActionPerformed(evt);
            }
        });

        jLabel29.setText("Total Harga");

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

        cobat1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cobat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cobat1ActionPerformed(evt);
            }
        });

        cobat2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cobat2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cobat2ActionPerformed(evt);
            }
        });

        cobat3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cobat3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cobat3ActionPerformed(evt);
            }
        });

        cobat4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cobat4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cobat4ActionPerformed(evt);
            }
        });

        jLabel30.setText("Nama Obat");

        jLabel31.setText("Nama Obat");

        jLabel33.setText("Nama Obat");

        jLabel16.setText("ID Registrasi");

        tmetode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "Transfer" }));
        tmetode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmetodeActionPerformed(evt);
            }
        });

        bhitung.setBackground(new java.awt.Color(0, 255, 0));
        bhitung.setText("Hitung");
        bhitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bhitungActionPerformed(evt);
            }
        });

        jLabel5.setText("%");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel31)
                                    .addComponent(jLabel30)
                                    .addComponent(jLabel33))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cobat4, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cobat2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cobat3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel29)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(ttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel17)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(tharga4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel19)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(tqty4))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel12)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(tharga3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel14)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(tqty3))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(tharga2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel8)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(tqty2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addGap(133, 133, 133))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel15)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(tsub3, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel20)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(tsub4, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(6, 6, 6))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(116, 116, 116))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tidresep, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tnamapasien)
                                    .addComponent(crekam, 0, 178, Short.MAX_VALUE)
                                    .addComponent(dtanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(97, 97, 97)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel16))
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(tdiskon, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel5))
                                            .addComponent(tidregistrasi, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(43, 43, 43))
                                    .addComponent(tmetode, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(tsub2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cobat1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tharga1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tqty1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel26)
                                .addGap(30, 30, 30)
                                .addComponent(tsub1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(102, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bhitung)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bsimpan)
                        .addGap(18, 18, 18)
                        .addComponent(bubah)))
                .addGap(18, 18, 18)
                .addComponent(bhapus)
                .addGap(18, 18, 18)
                .addComponent(bbatal)
                .addGap(18, 18, 18)
                .addComponent(bkeluar)
                .addGap(54, 54, 54))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tidregistrasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tmetode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(tidresep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel16))
                                    .addGap(21, 21, 21)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(crekam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(tnamapasien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel21)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel13)
                                    .addGap(23, 23, 23)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(tdiskon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5))))
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dtanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tharga1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel28)
                        .addComponent(tqty1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel27)
                        .addComponent(tsub1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel26))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cobat1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tharga4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(tqty4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(tsub4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cobat2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30)
                            .addComponent(tharga2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(tqty2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(tsub2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cobat3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31)
                            .addComponent(tharga3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(tqty3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addComponent(tsub3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cobat4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel33))
                            .addComponent(jLabel20))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ttotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bhitung)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bubah)
                    .addComponent(bhapus)
                    .addComponent(bbatal)
                    .addComponent(bkeluar)
                    .addComponent(bsimpan))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblplgnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblplgnMouseClicked
    int baris =
        tblplgn.getSelectedRow();

        tidresep.setText(
        tabmode.getValueAt(baris,0)
        .toString());

        tidregistrasi.setText(
        tabmode.getValueAt(baris,1)
        .toString());

        crekam.setSelectedItem(
        tabmode.getValueAt(baris,2)
        .toString());

        tnamapasien.setText(
        tabmode.getValueAt(baris,3)
        .toString());

        cobat1.setSelectedItem(
        tabmode.getValueAt(baris,5)
        .toString());

        cobat2.setSelectedItem(
        tabmode.getValueAt(baris,6)
        .toString());

        cobat3.setSelectedItem(
        tabmode.getValueAt(baris,7)
        .toString());

        cobat4.setSelectedItem(
        tabmode.getValueAt(baris,8)
        .toString());

        ttotal.setText(
        tabmode.getValueAt(baris,9)
        .toString());

        tmetode.setSelectedItem(
        tabmode.getValueAt(baris,10)
        .toString());

    }//GEN-LAST:event_tblplgnMouseClicked

    private void tcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tcariActionPerformed

    private void bcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcariActionPerformed
    try {

        String sql =
        "SELECT * FROM farmasi "
        + "WHERE id_resep='"
        + tcari.getText()
        + "' OR id_registrasi='"
        + tcari.getText()
        + "'";

        st = con.createStatement();
        rs = st.executeQuery(sql);

        tabmode.setRowCount(0);

        while(rs.next()){

            String[] data = {

                rs.getString("id_resep"),
                rs.getString("id_registrasi"),
                rs.getString("nama_pasien"),
                rs.getString("tanggal_resep"),
                rs.getString("obat1"),
                rs.getString("obat2"),
                rs.getString("obat3"),
                rs.getString("obat4"),
                rs.getString("total_bayar"),
                rs.getString("metode_pembayaran")
            };

            tabmode.addRow(data);
        }

    } catch (Exception e) {

        JOptionPane.showMessageDialog(null, e);
    }
    }//GEN-LAST:event_bcariActionPerformed

    private void tidresepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidresepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tidresepActionPerformed

    private void tnamapasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnamapasienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tnamapasienActionPerformed

    private void crekamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crekamActionPerformed
    tampilPasien();  // TODO add your handling code here:
    }//GEN-LAST:event_crekamActionPerformed

    private void tidregistrasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidregistrasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tidregistrasiActionPerformed

    private void tdiskonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tdiskonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tdiskonActionPerformed

    private void tharga2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tharga2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tharga2ActionPerformed

    private void tqty2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tqty2ActionPerformed
    hitungTotal();    // TODO add your handling code here:
    }//GEN-LAST:event_tqty2ActionPerformed

    private void tsub2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tsub2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tsub2ActionPerformed

    private void tharga3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tharga3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tharga3ActionPerformed

    private void tqty3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tqty3ActionPerformed
    hitungTotal();    // TODO add your handling code here:
    }//GEN-LAST:event_tqty3ActionPerformed

    private void tsub3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tsub3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tsub3ActionPerformed

    private void tharga4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tharga4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tharga4ActionPerformed

    private void tqty4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tqty4ActionPerformed
     hitungTotal();   // TODO add your handling code here:
    }//GEN-LAST:event_tqty4ActionPerformed

    private void tsub4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tsub4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tsub4ActionPerformed

    private void tqty1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tqty1ActionPerformed
    hitungTotal();    // TODO add your handling code here:
    }//GEN-LAST:event_tqty1ActionPerformed

    private void tharga1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tharga1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tharga1ActionPerformed

    private void tsub1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tsub1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tsub1ActionPerformed

    private void ttotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ttotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ttotalActionPerformed

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpanActionPerformed
     try {

        if(dtanggal.getDate()==null){

            JOptionPane.showMessageDialog(
            null,
            "Tanggal belum dipilih");

            return;
        }

        hitungTotal();

        SimpleDateFormat fm =
        new SimpleDateFormat(
        "yyyy-MM-dd");

        String tanggal =
        fm.format(
        dtanggal.getDate());

        String sql =
        "INSERT INTO farmasi("
        + "id_resep,"
        + "id_registrasi,"
        + "id_rekam_medis,"
        + "nama_pasien,"
        + "tanggal_resep,"
        + "obat1,"
        + "obat2,"
        + "obat3,"
        + "obat4,"
        + "subtotal1,"
        + "subtotal2,"
        + "subtotal3,"
        + "subtotal4,"
        + "diskon,"
        + "total_bayar,"
        + "metode_pembayaran"
        + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement ps =
        con.prepareStatement(sql);

        ps.setString(1,
        tidresep.getText());

        ps.setString(2,
        tidregistrasi.getText());

        ps.setString(3,
        crekam.getSelectedItem().toString());

        ps.setString(4,
        tnamapasien.getText());

        ps.setString(5,
        tanggal);

        ps.setString(6,
        cobat1.getSelectedItem().toString());

        ps.setString(7,
        cobat2.getSelectedItem().toString());

        ps.setString(8,
        cobat3.getSelectedItem().toString());

        ps.setString(9,
        cobat4.getSelectedItem().toString());

        ps.setString(10,
        tsub1.getText());

        ps.setString(11,
        tsub2.getText());

        ps.setString(12,
        tsub3.getText());

        ps.setString(13,
        tsub4.getText());

        ps.setString(14,
        tdiskon.getText());

        ps.setString(15,
        ttotal.getText());

        ps.setString(16,
        tmetode.getSelectedItem().toString());

        ps.executeUpdate();

        JOptionPane.showMessageDialog(
        null,
        "Data berhasil disimpan");

        tampilData();

        reset();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
        null,e);
    }
    }//GEN-LAST:event_bsimpanActionPerformed

    private void bubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bubahActionPerformed

        try {

            if(dtanggal.getDate() == null){

                JOptionPane.showMessageDialog(
                null,
                "Tanggal belum dipilih");

                return;
            }

            SimpleDateFormat fm =
            new SimpleDateFormat("yyyy-MM-dd");

            String tanggal =
            fm.format(dtanggal.getDate());

            String sql =
            "UPDATE farmasi SET "

            + "id_registrasi=?,"
            + "id_rekam_medis=?,"
            + "nama_pasien=?,"
            + "tanggal_resep=?,"
            + "obat1=?,"
            + "obat2=?,"
            + "obat3=?,"
            + "obat4=?,"
            + "total_bayar=?,"
            + "metode_pembayaran=? "

            + "WHERE id_resep=?";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.setString(1,
            tidregistrasi.getText());

            ps.setString(2,
            crekam.getSelectedItem().toString());

            ps.setString(3,
            tnamapasien.getText());

            ps.setString(4,
            tanggal);

            ps.setString(5,
            cobat1.getSelectedItem().toString());

            ps.setString(6,
            cobat2.getSelectedItem().toString());

            ps.setString(7,
            cobat3.getSelectedItem().toString());

            ps.setString(8,
            cobat4.getSelectedItem().toString());

            ps.setString(9,
            ttotal.getText());

            ps.setString(10,
            tmetode.getSelectedItem().toString());

            ps.setString(11,
            tidresep.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
            null,
            "Data berhasil diubah");

            tampilData();

            reset();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
            null,
            e);
        }
    
    }//GEN-LAST:event_bubahActionPerformed

    private void bhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhapusActionPerformed
 int opsi =
        JOptionPane.showConfirmDialog(
        null,
        "Yakin ingin menghapus data?",
        "Hapus",
        JOptionPane.YES_NO_OPTION);

        if(opsi == JOptionPane.YES_OPTION){

            try {

                String sql =
                "DELETE FROM farmasi "
                + "WHERE id_resep=?";

                PreparedStatement ps =
                con.prepareStatement(sql);

                ps.setString(1,
                tidresep.getText());

                ps.executeUpdate();

                JOptionPane.showMessageDialog(
                null,
                "Data berhasil dihapus");

                reset();

                tampilData();

            } catch (Exception e) {

                JOptionPane.showMessageDialog(
                null,
                e);
            }
        }
    }//GEN-LAST:event_bhapusActionPerformed

    private void bbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatalActionPerformed
    reset();
    }//GEN-LAST:event_bbatalActionPerformed

    private void bkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bkeluarActionPerformed
        dispose();
    }//GEN-LAST:event_bkeluarActionPerformed

    private void cobat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cobat1ActionPerformed
    tampilHarga(cobat1,tharga1);
    }//GEN-LAST:event_cobat1ActionPerformed

    private void cobat2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cobat2ActionPerformed
    tampilHarga(cobat2,tharga2);        // TODO add your handling code here:
    }//GEN-LAST:event_cobat2ActionPerformed

    private void cobat3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cobat3ActionPerformed
    tampilHarga(cobat3,tharga3);      // TODO add your handling code here:
    }//GEN-LAST:event_cobat3ActionPerformed

    private void cobat4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cobat4ActionPerformed
    tampilHarga(cobat4,tharga4);  // TODO add your handling code here:
    }//GEN-LAST:event_cobat4ActionPerformed

    private void tmetodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmetodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tmetodeActionPerformed

    private void bhitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhitungActionPerformed
    hitungTotal();        // TODO add your handling code here:
    }//GEN-LAST:event_bhitungActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                new FormFarmasi().setVisible(true);
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
    private javax.swing.JComboBox<String> cobat1;
    private javax.swing.JComboBox<String> cobat2;
    private javax.swing.JComboBox<String> cobat3;
    private javax.swing.JComboBox<String> cobat4;
    private javax.swing.JComboBox<String> crekam;
    private com.toedter.calendar.JDateChooser dtanggal;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblplgn;
    private javax.swing.JTextField tcari;
    private javax.swing.JTextField tdiskon;
    private javax.swing.JTextField tharga1;
    private javax.swing.JTextField tharga2;
    private javax.swing.JTextField tharga3;
    private javax.swing.JTextField tharga4;
    private javax.swing.JTextField tidregistrasi;
    private javax.swing.JTextField tidresep;
    private javax.swing.JComboBox<String> tmetode;
    private javax.swing.JTextField tnamapasien;
    private javax.swing.JTextField tqty1;
    private javax.swing.JTextField tqty2;
    private javax.swing.JTextField tqty3;
    private javax.swing.JTextField tqty4;
    private javax.swing.JTextField tsub1;
    private javax.swing.JTextField tsub2;
    private javax.swing.JTextField tsub3;
    private javax.swing.JTextField tsub4;
    private javax.swing.JTextField ttotal;
    // End of variables declaration//GEN-END:variables
}
