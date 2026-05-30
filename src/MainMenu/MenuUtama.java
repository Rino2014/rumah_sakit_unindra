package MainMenu;

import Koneksi.Koneksi;
import Master.*;
import Transaksi.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class MenuUtama extends JFrame {

    // Hak Akses Koneksi Database
    Connection con;
    Statement st;
    ResultSet rs;

    // Komponen Layout Antarmuka
    JPanel sidebar, mainContent, topBar;
    CardLayout cardLayout;
    
    // Label Statistik Real-time Utama
    JLabel lblTotalPasien, lblPasienHariIni;
    
    // Label Statistik Real-time Per Poliklinik
    JLabel lblPoliJantung, lblPoliAnak, lblPoliKandungan, lblPoliGigi, lblPoliMata;

    public MenuUtama() {
        // Konfigurasi Dasar Jendela Utama
        setTitle("SIMRS PRO - ERP Dashboard | Rumah Sakit Unindra");
        setSize(1350, 780);
        setLocationRelativeTo(null);
        
        // Mencegah aplikasi langsung keluar secara paksa saat tombol [X] diklik
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                aksiKeluarTerpusat();
            }
        });

        setLayout(new BorderLayout());
        con = Koneksi.getKoneksi();

        // 1. MEMBUAT TOP BAR (Header Atas)
        createTopBar();

        // 2. MEMBUAT SIDEBAR (Navigasi Menu Utama Terpusat)
        createSidebar();

        // 3. MEMBUAT MAIN CONTENT (Konten Tengah Dinamis)
        cardLayout = new CardLayout();
        mainContent = new JPanel(cardLayout);
        mainContent.setBackground(new Color(245, 246, 250));
        
        // Memasukkan Panel Dashboard Utama ke dalam Wadah Konten
        mainContent.add(createDashboardPanel(), "Dashboard");
        
        // Menyatukan Semua Struktur Panel Utama ke Jendela JFrame
        add(topBar, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(mainContent, BorderLayout.CENTER);

        // Memuat Data Statistik dari Database MySQL
        updateStatistik();
    }

    private void createTopBar() {
        topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(25, 34, 49)); // Warna Navy Premium
        topBar.setPreferredSize(new Dimension(getWidth(), 60));
        topBar.setBorder(new EmptyBorder(0, 20, 0, 20));

        JLabel lblLogo = new JLabel("<html><font color='#ffffff'><b>SIMRS</b></font> <font color='#00d2d3'>PRO</font></html>");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel lblAdmin = new JLabel("A | Administrator  ");
        lblAdmin.setForeground(Color.WHITE);
        lblAdmin.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        topBar.add(lblLogo, BorderLayout.WEST);
        topBar.add(lblAdmin, BorderLayout.EAST);
    }

    private void createSidebar() {
        sidebar = new JPanel();
        sidebar.setBackground(new Color(33, 47, 69)); // Warna Slate Blue
        sidebar.setPreferredSize(new Dimension(240, getHeight()));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(15, 0, 0, 0));

        JLabel lblNav = new JLabel("  MAIN NAVIGATION");
        lblNav.setForeground(new Color(110, 125, 149));
        lblNav.setFont(new Font("Segoe UI", Font.BOLD, 11));
        sidebar.add(lblNav);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));

        // ================= STRUKTUR TOMBOL KATEGORI UTAMA =================
        
        // 1. DASHBOARD
        addMenuUtamaButton("Dashboard", new Color(46, 204, 113), e -> cardLayout.show(mainContent, "Dashboard"));

        // 2. MASTER FORM (Akses Data Master)
        addMenuUtamaButton("Master Form", new Color(52, 152, 219), e -> beralihKeMasterForm());

        // 3. FORM TRANSAKSI (Akses Modul Alur Transaksi Medis & Finansial)
        addMenuUtamaButton("Form Transaksi", new Color(155, 89, 182), e -> beralihKeTransaksiForm());

        // 4. LAPORAN (Untuk Diintegrasikan Nanti)
        addMenuUtamaButton("Laporan", new Color(26, 188, 156), e -> {
            JOptionPane.showMessageDialog(this, "Fitur Cetak Laporan / Laporan.java sedang disiapkan.", "Informasi Modul", JOptionPane.INFORMATION_MESSAGE);
        });

        // 5. MAINTENANCE (Pemeliharaan Sistem & Reset Database)
        addMenuUtamaButton("Maintenance", new Color(241, 196, 15), e -> {
            String[] opsiMaintenance = {"Reset Semua Data Transaksi", "Batal"};
            int pilihan = JOptionPane.showOptionDialog(this, 
                    "Pilih tindakan pemeliharaan sistem:", "SISTEM MAINTENANCE",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.CONFIG_MESSAGE, null, opsiMaintenance, opsiMaintenance[1]);
            
            if (pilihan == 0) {
                aksiResetSemuaData();
            }
        });
        
        // Dorong Tombol Logout Agar Menempel di Sisi Paling Bawah Sidebar
        sidebar.add(Box.createVerticalGlue()); 
        
        // 6. LOGOUT / KELUAR TERPUSAT
        addMenuUtamaButton("Logout / Keluar", new Color(231, 76, 60), e -> aksiKeluarTerpusat());
    }

    private void addMenuUtamaButton(String text, Color indicatorColor, java.awt.event.ActionListener action) {
        JPanel btnPanel = new JPanel(new BorderLayout());
        btnPanel.setMaximumSize(new Dimension(240, 50));
        btnPanel.setBackground(new Color(33, 47, 69));

        // Indikator Kotak Warna di Sisi Kiri Tombol Navigasi
        JPanel indicator = new JPanel();
        indicator.setBackground(indicatorColor);
        indicator.setPreferredSize(new Dimension(6, 50));
        btnPanel.add(indicator, BorderLayout.WEST);

        JButton btn = new JButton("  " + text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(new Color(220, 225, 230));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.addActionListener(action);

        // Efek Hover Sorot Dinamis
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnPanel.setBackground(new Color(42, 59, 86));
                btn.setForeground(Color.WHITE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnPanel.setBackground(new Color(33, 47, 69));
                btn.setForeground(new Color(220, 225, 230));
            }
        });

        btnPanel.add(btn, BorderLayout.CENTER);
        sidebar.add(btnPanel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 2)));
    }

    // ================= SELEKSI FORM KATEGORI MASTER =================
    private void beralihKeMasterForm() {
        String[] pilihan = {"Form Dokter", "Form Kamar", "Form Obat", "Form Pasien", "Form Pegawai", "Form Poli"};
        String opsi = (String) JOptionPane.showInputDialog(this, "Pilih Form Master yang ingin dibuka:", 
                "MASTER FORM SELECTION", JOptionPane.QUESTION_MESSAGE, null, pilihan, pilihan[0]);
        
        if (opsi != null) {
            JFrame form = null;
            if (opsi.equals("Form Dokter")) form = new FormDokter();
            else if (opsi.equals("Form Kamar")) form = new FormKamar();
            else if (opsi.equals("Form Obat")) form = new FormObat();
            else if (opsi.equals("Form Pasien")) form = new FormPasien();
            else if (opsi.equals("Form Pegawai")) form = new FormPegawai();
            else if (opsi.equals("Form Poli")) form = new FormPoli();
            
            bukaFormAnakAman(form);
        }
    }

    // ================= SELEKSI FORM KATEGORI TRANSAKSI =================
    private void beralihKeTransaksiForm() {
        String[] pilihan = {
            "Antrean Farmasi", "Antrean Kasir", "Antrean Pembayaran", 
            "Antrean Pemeriksaan", "Cetak Pembayaran", "Form Farmasi", 
            "Form Kasir", "Form Pemeriksaan", "Form Pendaftaran", "Form Rawat Inap"
        };
        String opsi = (String) JOptionPane.showInputDialog(this, "Pilih Form Transaksi yang ingin dibuka:", 
                "TRANSACTION FORM SELECTION", JOptionPane.QUESTION_MESSAGE, null, pilihan, pilihan[0]);
        
        if (opsi != null) {
            JFrame form = null;
            if (opsi.equals("Antrean Farmasi")) form = new AntreanFarmasi();
            else if (opsi.equals("Antrean Kasir")) form = new AntreanKasir();
            else if (opsi.equals("Antrean Pembayaran")) form = new AntreanPembayaran();
            else if (opsi.equals("Antrean Pemeriksaan")) form = new AntreanPemeriksaan();
            else if (opsi.equals("Cetak Pembayaran")) form = new CetakPembayaran();
            else if (opsi.equals("Form Farmasi")) form = new FormFarmasi();
            else if (opsi.equals("Form Kasir")) form = new FormKasir();
            else if (opsi.equals("Form Pemeriksaan")) form = new FormPemeriksaan();
            else if (opsi.equals("Form Pendaftaran")) form = new FormPendaftaran();
            else if (opsi.equals("Form Rawat Inap")) form = new FormRawatInap();
            
            bukaFormAnakAman(form);
        }
    }

    private void bukaFormAnakAman(JFrame formAnak) {
        if (formAnak != null) {
            formAnak.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            formAnak.setVisible(true);
        }
    }

    private void aksiKeluarTerpusat() {
        int konfirm = JOptionPane.showConfirmDialog(this, 
                "Apakah Anda yakin ingin menutup seluruh aplikasi SIMRS PRO?", 
                "Konfirmasi Keluar Terpusat", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (konfirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    // ================= INTERFASIAL UTAMA RESET DATABASE =================
    private void aksiResetSemuaData() {
        int konfirm1 = JOptionPane.showConfirmDialog(this, 
                "PERINGATAN: Tindakan ini akan MENGHAPUS SEMUA data pendaftaran dan transaksi!\nApakah Anda yakin?", 
                "Konfirmasi Reset Data (Tahap 1)", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (konfirm1 == JOptionPane.YES_OPTION) {
            int konfirm2 = JOptionPane.showConfirmDialog(this, 
                    "Apakah Anda BENAR-BENAR yakin? Data yang dihapus tidak dapat dikembalikan.", 
                    "Konfirmasi Keamanan (Tahap 2)", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
            
            if (konfirm2 == JOptionPane.YES_OPTION) {
                try {
                    if (con != null) {
                        Statement stReset = con.createStatement();
                        
                        stReset.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
                        stReset.executeUpdate("TRUNCATE TABLE pemeriksaan_dokter");
                        stReset.executeUpdate("TRUNCATE TABLE form_farmasi");
                        stReset.executeUpdate("TRUNCATE TABLE form_kasir");
                        stReset.executeUpdate("TRUNCATE TABLE pendaftaran");
                        stReset.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
                        
                        JOptionPane.showMessageDialog(this, "Semua data form dan transaksi berhasil di-reset ke 0!", 
                                "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        
                        updateStatistik(); // Segarkan nilai dashboard jadi 0 semua
                    } else {
                        JOptionPane.showMessageDialog(this, "Koneksi database tidak aktif. Gagal mereset.", 
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Gagal mereset data: " + e.getMessage(), 
                            "Error Database", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    // ================= DESAIN GRAFIS DAN MONITOR DASHBOARD =================
    private JPanel createDashboardPanel() {
        JPanel pnlDashboard = new JPanel(new BorderLayout(20, 20));
        pnlDashboard.setBackground(new Color(240, 242, 245));
        pnlDashboard.setBorder(new EmptyBorder(25, 25, 25, 25));

        // --- Header Jendela Dashboard ---
        JPanel pnlTitle = new JPanel(new BorderLayout());
        pnlTitle.setOpaque(false);
        JLabel lblDash = new JLabel("Dashboard");
        lblDash.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblDash.setForeground(new Color(44, 62, 80));
        pnlTitle.add(lblDash, BorderLayout.WEST);
        pnlDashboard.add(pnlTitle, BorderLayout.NORTH);

        // --- Area Tengah Konten Grid ---
        JPanel pnlCenterGrid = new JPanel(new GridBagLayout());
        pnlCenterGrid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 15, 0);

        // [BARIS 1]: STATISTIK CARDS (Dua belah bagian: Ringkasan Utama vs Per-Poliklinik)
        JPanel pnlCardsContainer = new JPanel(new GridBagLayout());
        pnlCardsContainer.setOpaque(false);
        GridBagConstraints gbcCards = new GridBagConstraints();
        gbcCards.fill = GridBagConstraints.BOTH;
        gbcCards.weighty = 1.0;

        // Sub-Kiri: Ringkasan Utama (2 Kolom)
        JPanel pnlCardsUtama = new JPanel(new GridLayout(1, 2, 12, 0));
        pnlCardsUtama.setOpaque(false);
        lblTotalPasien = new JLabel("0", SwingConstants.LEFT);
        lblPasienHariIni = new JLabel("0", SwingConstants.LEFT);
        pnlCardsUtama.add(createInfoCard("TOTAL REGISTRASI", lblTotalPasien, new Color(52, 152, 219)));
        pnlCardsUtama.add(createInfoCard("PASIEN HARI INI", lblPasienHariIni, new Color(46, 204, 113)));

        gbcCards.gridx = 0; gbcCards.weightx = 0.3; gbcCards.insets = new Insets(0, 0, 0, 15);
        pnlCardsContainer.add(pnlCardsUtama, gbcCards);

        // Sub-Kanan: Sebaran Pasien Per Poli Baru (5 Kolom)
        JPanel pnlCardsPoli = new JPanel(new GridLayout(1, 5, 10, 0));
        pnlCardsPoli.setOpaque(false);
        
        lblPoliJantung = new JLabel("0", SwingConstants.LEFT);
        lblPoliAnak = new JLabel("0", SwingConstants.LEFT);
        lblPoliKandungan = new JLabel("0", SwingConstants.LEFT);
        lblPoliGigi = new JLabel("0", SwingConstants.LEFT);
        lblPoliMata = new JLabel("0", SwingConstants.LEFT);

        pnlCardsPoli.add(createInfoCard("POLI JANTUNG", lblPoliJantung, new Color(155, 89, 182)));
        pnlCardsPoli.add(createInfoCard("POLI ANAK", lblPoliAnak, new Color(26, 188, 156)));
        pnlCardsPoli.add(createInfoCard("POLI KANDUNGAN", lblPoliKandungan, new Color(241, 196, 15)));
        pnlCardsPoli.add(createInfoCard("POLI GIGI", lblPoliGigi, new Color(230, 126, 34)));
        pnlCardsPoli.add(createInfoCard("POLI MATA", lblPoliMata, new Color(231, 76, 60)));

        gbcCards.gridx = 1; gbcCards.weightx = 0.7; gbcCards.insets = new Insets(0, 0, 0, 0);
        pnlCardsContainer.add(pnlCardsPoli, gbcCards);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1.0; gbc.weighty = 0.22;
        pnlCenterGrid.add(pnlCardsContainer, gbc);

        // [BARIS 2]: MONITOR ANTREAN AKURAT DARI FORM TRANSAKSI
        JPanel pnlVisuals = new JPanel(new GridLayout(1, 3, 15, 0));
        pnlVisuals.setOpaque(false);

        // 1. Antrean Pemeriksaan Dokter
        pnlVisuals.add(createVisualComponentPanel("MONITOR ANTREAN PEMERIKSAAN", 
                createAntreanTable("SELECT id_registrasi, nama_pasien, poli_klinik FROM pemeriksaan_dokter")));
        
        // 2. Antrean Sediaan Farmasi/Obat
        pnlVisuals.add(createVisualComponentPanel("MONITOR ANTREAN FARMASI", 
                createAntreanTable("SELECT id_registrasi, nama_pasien, status_obat FROM form_farmasi")));
        
        // 3. Antrean Kasir Keuangan
        pnlVisuals.add(createVisualComponentPanel("MONITOR ANTREAN KASIR & PAY", 
                createAntreanTable("SELECT id_registrasi, nama_pasien, total_bayar FROM form_kasir")));

        gbc.gridy = 1; gbc.weighty = 0.78;
        pnlCenterGrid.add(pnlVisuals, gbc);

        pnlDashboard.add(pnlCenterGrid, BorderLayout.CENTER);
        return pnlDashboard;
    }

    private JPanel createInfoCard(String title, JLabel lblValue, Color topBorderColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(4, 0, 0, 0, topBorderColor),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblTitle.setForeground(new Color(127, 140, 141));

        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblValue.setForeground(new Color(44, 62, 80));

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        return card;
    }

    private JPanel createVisualComponentPanel(String title, JComponent component) {
        JPanel body = new JPanel(new BorderLayout(0, 10));
        body.setBackground(Color.WHITE);
        body.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTitle.setForeground(new Color(52, 73, 94));
        body.add(lblTitle, BorderLayout.NORTH);
        body.add(component, BorderLayout.CENTER);

        return body;
    }

    private JScrollPane createAntreanTable(String sqlQuery) {
        String[] columns = {"ID Reg", "Nama Pasien", "Keterangan"};
        DefaultTableModel model = new DefaultTableModel(null, columns);
        JTable table = new JTable(model);
        
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(240, 242, 245));
        table.setSelectionBackground(new Color(52, 152, 219));

        try {
            if (con != null) {
                st = con.createStatement();
                rs = st.executeQuery(sqlQuery);
                while (rs.next()) {
                    String[] rowData = {
                        rs.getString(1), 
                        rs.getString(2), 
                        rs.getString(3)  
                    };
                    model.addRow(rowData);
                }
            } else {
                // Fallback Data Sampel jika Database mati
                model.addRow(new String[]{"REG-01", "Rino", "Poli Gigi"});
                model.addRow(new String[]{"REG-02", "Selfia", "Proses Racik Obat"});
                model.addRow(new String[]{"REG-03", "Malik", "Menunggu Kasir"});
            }
        } catch (Exception e) {
            model.addRow(new String[]{"-", "Belum ada antrean baru", "-"});
        }

        return new JScrollPane(table);
    }

    // ================= HITUNG PASIEN PER-POLI SECARA REAL-TIME =================
    private void updateStatistik() {
        try {
            if (con != null) {
                st = con.createStatement();
                
                // 1. Hitung Total Registrasi Keseluruhan
                rs = st.executeQuery("SELECT COUNT(*) AS total FROM pendaftaran");
                if (rs.next()) lblTotalPasien.setText(rs.getString("total"));
                
                // 2. Hitung Pasien Hari Ini
                rs = st.executeQuery("SELECT COUNT(*) AS total FROM pendaftaran WHERE DATE(tgl_daftar) = CURDATE()");
                if (rs.next()) lblPasienHariIni.setText(rs.getString("total"));
                
                // 3. Menghitung Jumlah Antrean Pasien Aktif Masing-Masing Poli
                hitungPoliAktual("Poli Jantung", lblPoliJantung);
                hitungPoliAktual("Poli Anak", lblPoliAnak);
                hitungPoliAktual("Poli Kandungan dan Kebidanan", lblPoliKandungan);
                hitungPoliAktual("Poli Gigi", lblPoliGigi);
                hitungPoliAktual("Poli Mata", lblPoliMata);
            } else {
                // Set default 0 jika koneksi null (menggunakan data dummy)
                lblTotalPasien.setText("3"); lblPasienHariIni.setText("3");
                lblPoliJantung.setText("1"); lblPoliAnak.setText("0");
                lblPoliKandungan.setText("0"); lblPoliGigi.setText("1"); lblPoliMata.setText("1");
            }
        } catch (Exception e) {
            // Berjalan aman
        }
    }

    private void hitungPoliAktual(String namaPoli, JLabel labelTujuan) {
        try {
            String sql = "SELECT COUNT(*) AS jumlah FROM pendaftaran WHERE poli_klinik LIKE '%" + namaPoli + "%'";
            Statement stPoli = con.createStatement();
            ResultSet rsPoli = stPoli.executeQuery(sql);
            if (rsPoli.next()) {
                labelTujuan.setText(rsPoli.getString("jumlah"));
            }
            rsPoli.close();
            stPoli.close();
        } catch (Exception e) {
            labelTujuan.setText("0");
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> {
            new MenuUtama().setVisible(true);
        });
    }
}