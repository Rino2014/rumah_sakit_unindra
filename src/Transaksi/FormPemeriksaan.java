package Transaksi;

import Koneksi.Koneksi;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FormPemeriksaan extends javax.swing.JFrame {

    Connection con;
    Statement st;
    ResultSet rs;
    DefaultTableModel tabmode;
    Boolean isloading;

    public FormPemeriksaan() {
        initComponents();
        con = Koneksi.getKoneksi();
        isloading = false;
        tampilRegistrasi(); // Memuat daftar ID Registrasi ke ComboBox
        tampilData();       // Memuat data pemeriksaan ke JTable
        autoID();           // Membuat ID Rekam Medis otomatis pertama kali

        setLocationRelativeTo(null);
        
        // Mencegah modifikasi manual pada field yang seharusnya terisi otomatis
        tidrekam.setEditable(false);
        tidpasien.setEditable(false);
        tnamadokter.setEditable(false);
        tnamapasien.setEditable(false);
        tpoli.setEditable(false);
        tkeluhan.setEditable(false);
        tbiaya.setEditable(false);
    }

    // ================= AUTO ID REKAM MEDIS =================
    private void autoID() {
        try {
            String sql = "SELECT id_rekam_medis FROM pemeriksaan_dokter ORDER BY id_rekam_medis DESC LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sql);

            if (rs.next()) {
                String idTerakhir = rs.getString("id_rekam_medis"); 
                String nomorSaja = idTerakhir.substring(3); 
                int angkaBaru = Integer.parseInt(nomorSaja) + 1; 
                
                String idOtomatis = "PMR" + String.format("%03d", angkaBaru);
                tidrekam.setText(idOtomatis);
            } else {
                tidrekam.setText("PMR001");
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
            tidrekam.setText("PMR001");
        }
    }

    // ================= AMBIL LIST ID REGISTRASI =================
        private void tampilRegistrasi() {
                    isloading = true;
            try {
                if (con == null) {
                    System.out.println("Koneksi database null");
                    return;
                }

                if (cidregistrasi == null) {
                    System.out.println("Component cidregistrasi null");
                    return;
                }

                String sql = "SELECT id_registrasi FROM pendaftaran_pasien";

                st = con.createStatement();
                rs = st.executeQuery(sql);
                System.out.println("HERE!");

                cidregistrasi.removeAllItems();
                cidregistrasi.addItem("- Pilih Registrasi -");

                while (rs.next()) {
                    String id = rs.getString("id_registrasi");
                    if (id != null) {
                        cidregistrasi.addItem(id);
                    }
                }
                        isloading = false;

            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }

   // ================= TAMPIL DATA KE TABLE =================
    private void tampilData() {
        Object[] header = {
            "ID Rekam",
            "ID Dokter",
            "ID Registrasi",
            "ID Pasien",
            "Nama Pasien",
            "Nama Dokter",
            "Poli",
            "Keluhan",
            "Diagnosa",
            "Tensi",
            "Suhu",
            "BB",
            "Goldar",
            "Tindakan",
            "Biaya Dokter",
            "Resep",
            "Catatan"
        };

        tabmode = new DefaultTableModel(null, header);
        tblplgn.setModel(tabmode);

        try {
            String sql = "SELECT * FROM pemeriksaan_dokter";
            st = con.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                String[] data = {
                    rs.getString("id_rekam_medis"),
                    rs.getString("id_dokter"),
                    rs.getString("id_registrasi"),
                    rs.getString("id_pasien"),
                    rs.getString("nama_pasien"),
                    rs.getString("nama_dokter"),
                    rs.getString("poli"),
                    rs.getString("keluhan_awal"),
                    rs.getString("diagnosa"),
                    rs.getString("tekanan_darah"),
                    rs.getString("suhu_tubuh"),
                    rs.getString("berat_badan"),
                    rs.getString("golongan_darah"),
                    rs.getString("tindakan_medis"),
                    rs.getString("biaya_dokter"),
                    rs.getString("resep_obat"),
                    rs.getString("catatan_dokter")
                };
                tabmode.addRow(data);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

   // ================= RESET FORM =================
    private void reset() {
    cidregistrasi.setSelectedIndex(0);

    tidrekam.setText("");
    tidpasien.setText("");
    tiddokter.setText("");

    tnamadokter.setText("");
    tnamapasien.setText("");

    tpoli.setText("");

    // Keluhan Awal
    tdiagnosa.setText("");

    // Diagnosa
    tkeluhan.setText("");

    ttekanan.setText("");
    tsuhu.setText("");
    tberat.setText("");

    cgoldar.setSelectedIndex(0);
    ctindakan.setSelectedIndex(0);

    tbiaya.setText("");
    tresep.setText("");
    tcatatan.setText("");

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
        tidrekam = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ttekanan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tsuhu = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tberat = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        tbiaya = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        tresep = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        tcatatan = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        bsimpan = new javax.swing.JButton();
        bubah = new javax.swing.JButton();
        bhapus = new javax.swing.JButton();
        bbatal = new javax.swing.JButton();
        bkeluar = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        cgoldar = new javax.swing.JComboBox<>();
        cidregistrasi = new javax.swing.JComboBox<>();
        ctindakan = new javax.swing.JComboBox<>();
        tidpasien = new javax.swing.JTextField();
        tkeluhan = new javax.swing.JTextField();
        tpoli = new javax.swing.JTextField();
        tnamadokter = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        tnamapasien = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        tiddokter = new javax.swing.JTextField();
        tdiagnosa = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));

        jLabel18.setFont(new java.awt.Font("News706 BT", 0, 30)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("PEMERIKSAAN - REKAM MEDIS");

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
                .addContainerGap(35, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 844, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bcari)))
                .addGap(27, 27, 27))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bcari))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        jLabel2.setText("ID Rekam Medis");

        tidrekam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidrekamActionPerformed(evt);
            }
        });

        jLabel6.setText("ID Registrasi");

        jLabel3.setText("Nama Pasien");

        jLabel13.setText("Poli");

        jLabel14.setText("Keluhan Awal");

        jLabel4.setText("Diagnosa");

        jLabel5.setText("Tekanan Darah");

        ttekanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ttekananActionPerformed(evt);
            }
        });

        jLabel7.setText("Suhu Tubuh");

        tsuhu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tsuhuActionPerformed(evt);
            }
        });

        jLabel8.setText("Berat Badan");

        tberat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tberatActionPerformed(evt);
            }
        });

        jLabel10.setText("Golongan Darah");

        jLabel11.setText("Tindakan Medis");

        jLabel12.setText("Biaya Dokter");

        tbiaya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbiayaActionPerformed(evt);
            }
        });

        jLabel15.setText("Resep Obat");

        tresep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tresepActionPerformed(evt);
            }
        });

        jLabel16.setText("Catatan Dokter");

        tcatatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tcatatanActionPerformed(evt);
            }
        });

        jLabel17.setText("Celcius");

        jLabel19.setText("Kg");

        jLabel20.setText("mm/Hg");

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

        jLabel21.setText("ID Pasien");

        cgoldar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "AB", "O" }));

        cidregistrasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cidregistrasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cidregistrasiActionPerformed(evt);
            }
        });

        ctindakan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rawat Jalan", "Rawat Inap" }));

        tidpasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidpasienActionPerformed(evt);
            }
        });

        tkeluhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tkeluhanActionPerformed(evt);
            }
        });

        tpoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tpoliActionPerformed(evt);
            }
        });

        tnamadokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnamadokterActionPerformed(evt);
            }
        });

        jLabel25.setText("Nama Dokter");

        tnamapasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnamapasienActionPerformed(evt);
            }
        });

        jLabel26.setText("ID Dokter");

        tiddokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tiddokterActionPerformed(evt);
            }
        });

        tdiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tdiagnosaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel21)
                    .addComponent(jLabel25)
                    .addComponent(jLabel3)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tidrekam, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(tidpasien, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tnamadokter)
                                .addComponent(tnamapasien)
                                .addComponent(tdiagnosa, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                                .addComponent(tkeluhan, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                                .addComponent(tpoli)
                                .addComponent(cidregistrasi, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tiddokter, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tbiaya, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ctindakan, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel15)
                                    .addGap(47, 47, 47)
                                    .addComponent(tresep, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel16)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tcatatan, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(tsuhu, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(26, 26, 26)
                                        .addComponent(ttekanan, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel10))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cgoldar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tberat, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel20)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bsimpan)
                        .addGap(18, 18, 18)
                        .addComponent(bubah)
                        .addGap(18, 18, 18)
                        .addComponent(bhapus)
                        .addGap(18, 18, 18)
                        .addComponent(bbatal)
                        .addGap(18, 18, 18)
                        .addComponent(bkeluar)))
                .addGap(25, 25, 25))
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(tidrekam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cidregistrasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(tiddokter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(tidpasien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(tnamadokter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(tnamapasien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tpoli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13))))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(tkeluhan, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(ttekanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(tsuhu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(tberat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(cgoldar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(ctindakan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(tbiaya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tresep, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(68, 68, 68))
                            .addComponent(tcatatan, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bubah)
                            .addComponent(bhapus)
                            .addComponent(bbatal)
                            .addComponent(bkeluar)
                            .addComponent(bsimpan))
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tdiagnosa, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblplgnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblplgnMouseClicked
    int baris = tblplgn.getSelectedRow();

    if(baris != -1){

        tidrekam.setText(tabmode.getValueAt(baris, 0).toString());
        tiddokter.setText(tabmode.getValueAt(baris, 1).toString());
        cidregistrasi.setSelectedItem(tabmode.getValueAt(baris, 2).toString());
        tidpasien.setText(tabmode.getValueAt(baris, 3).toString());

        tnamapasien.setText(tabmode.getValueAt(baris, 4).toString());
        tnamadokter.setText(tabmode.getValueAt(baris, 5).toString());

        tpoli.setText(tabmode.getValueAt(baris, 6).toString());

        // Keluhan Awal
        tdiagnosa.setText(tabmode.getValueAt(baris, 7).toString());

        // Diagnosa
        tkeluhan.setText(tabmode.getValueAt(baris, 8).toString());

        ttekanan.setText(tabmode.getValueAt(baris, 9).toString());
        tsuhu.setText(tabmode.getValueAt(baris, 10).toString());
        tberat.setText(tabmode.getValueAt(baris, 11).toString());

        cgoldar.setSelectedItem(tabmode.getValueAt(baris, 12).toString());

        ctindakan.setSelectedItem(tabmode.getValueAt(baris, 13).toString());

        tbiaya.setText(tabmode.getValueAt(baris, 14).toString());

        tresep.setText(tabmode.getValueAt(baris, 15).toString());

        tcatatan.setText(tabmode.getValueAt(baris, 16).toString());
    }
    }//GEN-LAST:event_tblplgnMouseClicked

    private void tcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tcariActionPerformed

    private void bcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcariActionPerformed
tabmode = new DefaultTableModel(null, new Object[]{"ID Rekam", "Dokter", "Registrasi", "Nama Pasien", "Poli", "Keluhan", "Diagnosa", "Tensi", "Suhu", "BB", "Goldar", "Tindakan", "Biaya Dokter", "Resep", "Catatan"});
        tblplgn.setModel(tabmode);
        try {
            String sql = "SELECT * FROM pemeriksaan_dokter WHERE nama_pasien LIKE '%"+tcari.getText()+"%' OR id_rekam_medis LIKE '%"+tcari.getText()+"%'";
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                tabmode.addRow(new String[]{
                    rs.getString("id_rekam_medis"), rs.getString("id_dokter"), rs.getString("id_registrasi"),
                    rs.getString("nama_pasien"), rs.getString("id_pasien"), rs.getString("nama_dokter"), rs.getString("poli"), rs.getString("keluhan_awal"),
                    rs.getString("diagnosa"), rs.getString("tekanan_darah"), rs.getString("suhu_tubuh"),
                    rs.getString("berat_badan"), rs.getString("golongan_darah"), rs.getString("tindakan_medis"),
                    rs.getString("biaya_dokter"), rs.getString("resep_obat"), rs.getString("catatan_dokter")
                });
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_bcariActionPerformed

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpanActionPerformed
    if (cidregistrasi.getSelectedIndex() <= 0 ||
        tkeluhan.getText().trim().equals("")) {

        JOptionPane.showMessageDialog(null,
        "ID Registrasi dan Diagnosa wajib diisi!");

        return;
    }

    try {

        String sql = "INSERT INTO pemeriksaan_dokter "
                + "(id_rekam_medis,id_dokter,id_registrasi,"
                + "id_pasien,nama_dokter,nama_pasien,poli,"
                + "keluhan_awal,diagnosa,tekanan_darah,"
                + "suhu_tubuh,berat_badan,golongan_darah,"
                + "tindakan_medis,biaya_dokter,"
                + "resep_obat,catatan_dokter)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, tidrekam.getText());
        ps.setString(2, tiddokter.getText());
        ps.setString(3, cidregistrasi.getSelectedItem().toString());
        ps.setString(4, tidpasien.getText());
        ps.setString(5, tnamadokter.getText());
        ps.setString(6, tnamapasien.getText());
        ps.setString(7, tpoli.getText());
        ps.setString(8, tdiagnosa.getText());
        ps.setString(9, tkeluhan.getText());
        ps.setString(10, ttekanan.getText());
        ps.setString(11, tsuhu.getText());
        ps.setString(12, tberat.getText());
        ps.setString(13, cgoldar.getSelectedItem().toString());
        ps.setString(14, ctindakan.getSelectedItem().toString());
        ps.setString(15, tbiaya.getText());
        ps.setString(16, tresep.getText());
        ps.setString(17, tcatatan.getText());

        ps.executeUpdate();

        JOptionPane.showMessageDialog(null,
        "Data Pemeriksaan Berhasil Disimpan");

        tampilData();
        reset();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(null,
        "Gagal Menyimpan Data : " + e.getMessage());
    }
    }//GEN-LAST:event_bsimpanActionPerformed

    private void bubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bubahActionPerformed
   try {

        String sql = "UPDATE pemeriksaan_dokter SET "
                + "id_dokter=?,"
                + "id_registrasi=?,"
                + "id_pasien=?,"
                + "nama_dokter=?,"
                + "nama_pasien=?,"
                + "poli=?,"
                + "keluhan_awal=?,"
                + "diagnosa=?,"
                + "tekanan_darah=?,"
                + "suhu_tubuh=?,"
                + "berat_badan=?,"
                + "golongan_darah=?,"
                + "tindakan_medis=?,"
                + "biaya_dokter=?,"
                + "resep_obat=?,"
                + "catatan_dokter=? "
                + "WHERE id_rekam_medis=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, tiddokter.getText());
        ps.setString(2, cidregistrasi.getSelectedItem().toString());
        ps.setString(3, tidpasien.getText());
        ps.setString(4, tnamadokter.getText());
        ps.setString(5, tnamapasien.getText());
        ps.setString(6, tpoli.getText());
        ps.setString(7, tdiagnosa.getText());
        ps.setString(8, tkeluhan.getText());
        ps.setString(9, ttekanan.getText());
        ps.setString(10, tsuhu.getText());
        ps.setString(11, tberat.getText());
        ps.setString(12, cgoldar.getSelectedItem().toString());
        ps.setString(13, ctindakan.getSelectedItem().toString());
        ps.setString(14, tbiaya.getText());
        ps.setString(15, tresep.getText());
        ps.setString(16, tcatatan.getText());
        ps.setString(17, tidrekam.getText());

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
 int opsi = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data?", "Hapus", JOptionPane.YES_NO_OPTION);
        if(opsi == JOptionPane.YES_OPTION){
            try {
                String sql = "DELETE FROM pemeriksaan_dokter WHERE id_rekam_medis='"+tidrekam.getText()+"'";
                st = con.createStatement();
                st.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                reset();
                tampilData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Gagal Hapus: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_bhapusActionPerformed

    private void bbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatalActionPerformed
    reset();
    }//GEN-LAST:event_bbatalActionPerformed
    private void bkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bkeluarActionPerformed
        dispose();
    }//GEN-LAST:event_bkeluarActionPerformed

    private void tidrekamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidrekamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tidrekamActionPerformed

    private void ttekananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ttekananActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ttekananActionPerformed
    private void tsuhuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tsuhuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tsuhuActionPerformed

    private void tberatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tberatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tberatActionPerformed

    private void tresepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tresepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tresepActionPerformed

    private void tcatatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tcatatanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tcatatanActionPerformed

    private void tbiayaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbiayaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbiayaActionPerformed

    private void tidpasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidpasienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tidpasienActionPerformed

    private void tkeluhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tkeluhanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tkeluhanActionPerformed

    private void tpoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tpoliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tpoliActionPerformed

    private void tnamadokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnamadokterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tnamadokterActionPerformed

    private void tnamapasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnamapasienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tnamapasienActionPerformed

    private void cidregistrasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cidregistrasiActionPerformed
        if (isloading) return;
        
        if (cidregistrasi.getSelectedIndex() == 0) {

        tidpasien.setText("");
        tnamadokter.setText("");
        tnamapasien.setText("");
        tpoli.setText("");
        tkeluhan.setText("");
        tbiaya.setText("");

        return;
    }

    try {

        // ================= AMBIL DATA PENDAFTARAN =================
        String sql =
        "SELECT * FROM pendaftaran_pasien "
        + "WHERE id_registrasi=?";

        PreparedStatement ps =
        con.prepareStatement(sql);

        ps.setString(1,
        cidregistrasi.getSelectedItem().toString());

        ResultSet rs = ps.executeQuery();

        // Jika data ditemukan
        if(rs.next()){

            // ================= DATA PASIEN =================
            tiddokter.setText(
            rs.getString("id_dokter"));

             tidpasien.setText(
            rs.getString("id_pasien"));        
            
            tnamapasien.setText(
            rs.getString("nama_pasien"));

            tpoli.setText(
            rs.getString("poli"));

            tkeluhan.setText(
            rs.getString("keluhan_awal"));

            // ================= AMBIL DATA DOKTER =================
            String sql2 =
            "SELECT * FROM dokter "
            + "WHERE id_dokter=?";

            PreparedStatement ps2 =
            con.prepareStatement(sql2);

            ps2.setString(1,
            rs.getString("id_dokter"));

            ResultSet rs2 =
            ps2.executeQuery();

            // Jika dokter ditemukan
            if(rs2.next()){

                // Nama dokter otomatis
                tnamadokter.setText(
                rs2.getString("nama_dokter"));

                // Biaya dokter otomatis
                tbiaya.setText(
                rs2.getString("tarif_konsultasi"));

            } else {

                JOptionPane.showMessageDialog(null,
                "Data dokter tidak ditemukan");
            }
        }

    } catch (Exception e) {

        JOptionPane.showMessageDialog(null,
        "Gagal mengambil data : " + e.getMessage());
    }
    }//GEN-LAST:event_cidregistrasiActionPerformed

    private void tiddokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tiddokterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tiddokterActionPerformed

    private void tdiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tdiagnosaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tdiagnosaActionPerformed

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
            java.util.logging.Logger.getLogger(FormPemeriksaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormPemeriksaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormPemeriksaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPemeriksaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormPemeriksaan().setVisible(true);
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
    private javax.swing.JComboBox<String> cgoldar;
    private javax.swing.JComboBox<String> cidregistrasi;
    private javax.swing.JComboBox<String> ctindakan;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JTextField tberat;
    private javax.swing.JTextField tbiaya;
    private javax.swing.JTable tblplgn;
    private javax.swing.JTextField tcari;
    private javax.swing.JTextField tcatatan;
    private javax.swing.JTextField tdiagnosa;
    private javax.swing.JTextField tiddokter;
    private javax.swing.JTextField tidpasien;
    private javax.swing.JTextField tidrekam;
    private javax.swing.JTextField tkeluhan;
    private javax.swing.JTextField tnamadokter;
    private javax.swing.JTextField tnamapasien;
    private javax.swing.JTextField tpoli;
    private javax.swing.JTextField tresep;
    private javax.swing.JTextField tsuhu;
    private javax.swing.JTextField ttekanan;
    // End of variables declaration//GEN-END:variables
}
