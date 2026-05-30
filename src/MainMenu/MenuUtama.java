package MainMenu;

import Koneksi.Koneksi;
import Master.FormDokter;
import Master.FormKamar;
import Master.FormObat;
import Master.FormPasien;
import Master.FormPegawai;
import Master.FormPoli;
import Transaksi.AntreanFarmasi;
import Transaksi.AntreanKasir;
import Transaksi.AntreanPemeriksaan;
import Transaksi.FormFarmasi;
import Transaksi.FormKasir;
import Transaksi.FormPemeriksaan;
import Transaksi.FormPendaftaran;
import Transaksi.FormRawatInap;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import javax.imageio.ImageIO;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class MenuUtama extends JFrame {

    Connection con;
    Statement st;
    ResultSet rs;

    JPanel sidebar, mainContent, topBar, footerBar;
    CardLayout cardLayout;

    JLabel lblTotalPasien, lblTotalDokter, lblTotalPegawai;
    JLabel lblJumlahAntreanPemeriksaan, lblJumlahAntreanFarmasi, lblJumlahAntreanKasir;

    JTable tblAntreanPemeriksaan, tblAntreanFarmasi, tblAntreanKasir;
    DefaultTableModel modelPemeriksaan, modelFarmasi, modelKasir;

    private final Color COLOR_BG = new Color(240, 244, 248);
    private final Color COLOR_WHITE = Color.WHITE;
    private final Color COLOR_TOP = new Color(10, 103, 160);
    private final Color COLOR_SIDEBAR = new Color(26, 38, 52);
    private final Color COLOR_SIDEBAR_HOVER = new Color(40, 58, 77);
    private final Color COLOR_BORDER = new Color(210, 218, 226);
    private final Color COLOR_TEXT = new Color(40, 55, 71);
    private final Color COLOR_TEXT_SOFT = new Color(110, 125, 140);

    private final Color COLOR_BLUE = new Color(32, 156, 238);
    private final Color COLOR_GREEN = new Color(39, 174, 96);
    private final Color COLOR_YELLOW = new Color(243, 156, 18);
    private final Color COLOR_PURPLE = new Color(142, 68, 173);
    private final Color COLOR_RED = new Color(231, 76, 60);

    public MenuUtama() {
        con = Koneksi.getKoneksi();

        setTitle("Aplikasi RS - Rumah Sakit Unindra");
        setSize(1440, 840);
        setMinimumSize(new Dimension(1280, 760));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_BG);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                aksiKeluarTerpusat();
            }
        });

        createTopBar();
        createSidebar();
        createFooter();

        cardLayout = new CardLayout();
        mainContent = new JPanel(cardLayout);
        mainContent.setBackground(COLOR_BG);
        mainContent.add(createDashboardPanel(), "Dashboard");

        add(topBar, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(mainContent, BorderLayout.CENTER);
        add(footerBar, BorderLayout.SOUTH);

        updateDashboard();
    }

    private void createTopBar() {
        topBar = new JPanel(new BorderLayout());
        topBar.setBackground(COLOR_TOP);
        topBar.setPreferredSize(new Dimension(getWidth(), 110));
        topBar.setBorder(new EmptyBorder(14, 18, 14, 18));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);
        leftPanel.setPreferredSize(new Dimension(190, 80));
        leftPanel.add(createLogoPlaceholder());

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel lblApp = new JLabel("APLIKASI RS");
        lblApp.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblApp.setForeground(new Color(225, 245, 255));
        lblApp.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JLabel lblRs = new JLabel("RUMAH SAKIT UNINDRA");
        lblRs.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblRs.setForeground(Color.WHITE);
        lblRs.setFont(new Font("Segoe UI", Font.BOLD, 32));

        JLabel lblCreator = new JLabel("Created by Kelompok 6 Kelas X6G Tahun 2026");
        lblCreator.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblCreator.setForeground(new Color(214, 240, 255));
        lblCreator.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        centerPanel.add(lblApp);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 4)));
        centerPanel.add(lblRs);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 4)));
        centerPanel.add(lblCreator);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 8));
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new Dimension(190, 80));

        JLabel lblAdmin = new JLabel("Administrator");
        lblAdmin.setForeground(Color.WHITE);
        lblAdmin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rightPanel.add(lblAdmin);

        topBar.add(leftPanel, BorderLayout.WEST);
        topBar.add(centerPanel, BorderLayout.CENTER);
        topBar.add(rightPanel, BorderLayout.EAST);
    }

//    private JPanel createLogoPlaceholder() {
//        JPanel panel = new JPanel(new BorderLayout());
//
//        JLabel lbl = new JLabel("");
//        try {
//        String path = "src/image/LogoUnindra.jpg";
//        BufferedImage originalImage = ImageIO.read(new File(path));
//            // Define desired dimensions based on JLabel size
//            int desiredWidth = 450;
//            int desiredHeight = 450;
//
//            // Get the scaled image maintaining aspect ratio
//            Image resizedImage = originalImage.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
//
//            // Create ImageIcon and set it to JLabel
//            lbl.setIcon(new ImageIcon(resizedImage));
//            lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//            lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/WhatsApp_Image_2026-05-30_at_14.02.05-removebg-preview.png"))); // NOI18N
//            lbl.setMaximumSize(new java.awt.Dimension(507, 492));
//            panel.add(lbl);
//
//
//        } catch (Exception e) {
//
//
//        }
//
//        return panel;
//    }

    private JLabel createLogoPlaceholder() {
//    JPanel panel = new JPanel(new BorderLayout());
    JLabel lbl = new JLabel("", SwingConstants.CENTER);

    try {
//        URL imageUrl = getClass().getResource("/image/LogoUnindra.jpg");
            BufferedImage originalImage = ImageIO.read(
                    getClass().getResource("/image/LogoUnindra.png")
            );

            int maxWidth = 85;
            int maxHeight = 85;

            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();

            double widthRatio = (double) maxWidth / originalWidth;
            double heightRatio = (double) maxHeight / originalHeight;
            double ratio = Math.min(widthRatio, heightRatio);

            int newWidth = (int) (originalWidth * ratio);
            int newHeight = (int) (originalHeight * ratio);

            Image resizedImage = originalImage.getScaledInstance(
                    newWidth, newHeight, Image.SCALE_SMOOTH);

            lbl.setIcon(new ImageIcon(resizedImage));
//            panel.add(lbl, BorderLayout.CENTER);

        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println(e.toString());
        }

        return lbl;
    }

    private void createSidebar() {
        sidebar = new JPanel();
        sidebar.setBackground(COLOR_SIDEBAR);
        sidebar.setPreferredSize(new Dimension(255, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(18, 0, 18, 0));

        JLabel lblNav = new JLabel("  MAIN NAVIGATION");
        lblNav.setForeground(new Color(180, 195, 210));
        lblNav.setFont(new Font("Segoe UI", Font.BOLD, 11));

        sidebar.add(lblNav);
        sidebar.add(Box.createRigidArea(new Dimension(0, 18)));

        addMenuUtamaButton("Dashboard", COLOR_GREEN, e -> {
            cardLayout.show(mainContent, "Dashboard");
            updateDashboard();
        });

        addMenuUtamaButton("Master Form", COLOR_BLUE, e -> beralihKeMasterForm());

        addMenuUtamaButton("Form Transaksi", COLOR_PURPLE, e -> beralihKeTransaksiForm());

        addMenuUtamaButton("Laporan", new Color(22, 160, 133), e -> tampilkanMenuLaporan());

        addMenuUtamaButton("Maintenance", COLOR_YELLOW, e -> tampilkanMenuMaintenance());

        sidebar.add(Box.createVerticalGlue());

        addMenuUtamaButton("Logout / Keluar", COLOR_RED, e -> aksiKeluarTerpusat());
    }

    private void addMenuUtamaButton(String text, Color accentColor, java.awt.event.ActionListener action) {
        final JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setMaximumSize(new Dimension(255, 54));
        wrapper.setBackground(COLOR_SIDEBAR);

        JPanel accent = new JPanel();
        accent.setBackground(accentColor);
        accent.setPreferredSize(new Dimension(8, 54));

        final JButton btn = new JButton("  " + text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(action);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                wrapper.setBackground(COLOR_SIDEBAR_HOVER);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                wrapper.setBackground(COLOR_SIDEBAR);
            }
        });

        wrapper.add(accent, BorderLayout.WEST);
        wrapper.add(btn, BorderLayout.CENTER);

        sidebar.add(wrapper);
        sidebar.add(Box.createRigidArea(new Dimension(0, 4)));
    }

    private void createFooter() {
        footerBar = new JPanel(new BorderLayout());
        footerBar.setBackground(new Color(222, 230, 238));
        footerBar.setPreferredSize(new Dimension(getWidth(), 34));
        footerBar.setBorder(new EmptyBorder(6, 14, 6, 14));

        JLabel lblFooter = new JLabel("Rumah Sakit Unindra created by Kelompok 6 Kelas X6G tahun 2026");
        lblFooter.setForeground(new Color(70, 82, 94));
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        footerBar.add(lblFooter, BorderLayout.WEST);
    }

    private JPanel createDashboardPanel() {
        JPanel dashboard = new JPanel(new BorderLayout(0, 18));
        dashboard.setBackground(COLOR_BG);
        dashboard.setBorder(new EmptyBorder(22, 22, 22, 22));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel lblTitle = new JLabel("Dashboard Utama");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(COLOR_TEXT);

        JLabel lblDesc = new JLabel("Monitoring data master dan antrean layanan Rumah Sakit Unindra");
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDesc.setForeground(COLOR_TEXT_SOFT);

        JPanel titleWrap = new JPanel();
        titleWrap.setOpaque(false);
        titleWrap.setLayout(new BoxLayout(titleWrap, BoxLayout.Y_AXIS));
        titleWrap.add(lblTitle);
        titleWrap.add(Box.createRigidArea(new Dimension(0, 4)));
        titleWrap.add(lblDesc);

        header.add(titleWrap, BorderLayout.WEST);
        dashboard.add(header, BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        JPanel statistikRow = new JPanel(new GridLayout(1, 3, 16, 0));
        statistikRow.setOpaque(false);
        statistikRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 132));

        lblTotalPasien = createBigNumberLabel();
        lblTotalDokter = createBigNumberLabel();
        lblTotalPegawai = createBigNumberLabel();

        statistikRow.add(createInfoCard("TOTAL PASIEN", "Jumlah pasien terdaftar pada database pasien", lblTotalPasien, COLOR_BLUE));
        statistikRow.add(createInfoCard("TOTAL DOKTER", "Jumlah dokter terdaftar pada database dokter", lblTotalDokter, COLOR_GREEN));
        statistikRow.add(createInfoCard("TOTAL PEGAWAI", "Jumlah pegawai terdaftar pada database pegawai", lblTotalPegawai, COLOR_YELLOW));

        body.add(statistikRow);
        body.add(Box.createRigidArea(new Dimension(0, 18)));

        modelPemeriksaan = createQueueTableModel();
        modelFarmasi = createQueueTableModel();
        modelKasir = createQueueTableModel();

        tblAntreanPemeriksaan = new JTable(modelPemeriksaan);
        tblAntreanFarmasi = new JTable(modelFarmasi);
        tblAntreanKasir = new JTable(modelKasir);

        styleTable(tblAntreanPemeriksaan);
        styleTable(tblAntreanFarmasi);
        styleTable(tblAntreanKasir);

        lblJumlahAntreanPemeriksaan = createQueueCountLabel();
        lblJumlahAntreanFarmasi = createQueueCountLabel();
        lblJumlahAntreanKasir = createQueueCountLabel();

        pasangComboStatus(tblAntreanPemeriksaan, modelPemeriksaan,
                new String[]{"Antrean", "Diperiksa", "Selesai Diperiksa"},
                "Selesai Diperiksa");

        pasangComboStatus(tblAntreanFarmasi, modelFarmasi,
                new String[]{"Antrean Resep", "Proses Peracikan", "Siap Diserahkan", "Obat Diterima"},
                "Obat Diterima");

        pasangComboStatus(tblAntreanKasir, modelKasir,
                new String[]{"Antrean", "Sedang Dipanggil", "Sedang Dilayani", "No Response", "Selesai"},
                "Selesai");

        JPanel antreanRow = new JPanel(new GridLayout(1, 3, 16, 0));
        antreanRow.setOpaque(false);

        antreanRow.add(createQueuePanel(
                "ANTREAN PEMERIKSAAN",
                "Data dari tabel pendaftaran_pasien",
                lblJumlahAntreanPemeriksaan,
                new JScrollPane(tblAntreanPemeriksaan),
                COLOR_BLUE
        ));

        antreanRow.add(createQueuePanel(
                "ANTREAN FARMASI",
                "Data dari tabel pemeriksaan_dokter",
                lblJumlahAntreanFarmasi,
                new JScrollPane(tblAntreanFarmasi),
                COLOR_PURPLE
        ));

        antreanRow.add(createQueuePanel(
                "ANTREAN KASIR / PEMBAYARAN",
                "Data dari tabel farmasi",
                lblJumlahAntreanKasir,
                new JScrollPane(tblAntreanKasir),
                COLOR_RED
        ));

        body.add(antreanRow);
        dashboard.add(body, BorderLayout.CENTER);

        return dashboard;
    }

    private JLabel createBigNumberLabel() {
        JLabel lbl = new JLabel("0");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lbl.setForeground(COLOR_TEXT);
        return lbl;
    }

    private JLabel createQueueCountLabel() {
        JLabel lbl = new JLabel("0", SwingConstants.CENTER);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 26));
        return lbl;
    }

    private JPanel createInfoCard(String title, String subtitle, JLabel valueLabel, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(COLOR_WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER, 1),
                new EmptyBorder(0, 0, 0, 0)
        ));

        JPanel topStrip = new JPanel(new BorderLayout());
        topStrip.setBackground(accentColor);
        topStrip.setPreferredSize(new Dimension(0, 10));
        card.add(topStrip, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(0, 10));
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(16, 18, 16, 18));

        JPanel topText = new JPanel();
        topText.setOpaque(false);
        topText.setLayout(new BoxLayout(topText, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTitle.setForeground(new Color(90, 102, 114));

        JLabel lblSub = new JLabel("<html><body style='width:270px'>" + subtitle + "</body></html>");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(COLOR_TEXT_SOFT);

        topText.add(lblTitle);
        topText.add(Box.createRigidArea(new Dimension(0, 5)));
        topText.add(lblSub);

        content.add(topText, BorderLayout.NORTH);
        content.add(valueLabel, BorderLayout.CENTER);

        card.add(content, BorderLayout.CENTER);
        return card;
    }

    private JPanel createQueuePanel(String title, String subtitle, JLabel countLabel, JComponent component, Color headerColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_WHITE);
        panel.setBorder(BorderFactory.createLineBorder(COLOR_BORDER, 1));

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(headerColor);
        top.setBorder(new EmptyBorder(12, 14, 12, 14));

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel lblSub = new JLabel(subtitle);
        lblSub.setForeground(new Color(240, 245, 250));
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        left.add(lblTitle);
        left.add(Box.createRigidArea(new Dimension(0, 3)));
        left.add(lblSub);

        JPanel right = new JPanel();
        right.setOpaque(false);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

        JLabel lblJml = new JLabel("Jumlah");
        lblJml.setForeground(Color.WHITE);
        lblJml.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblJml.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        countLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        right.add(lblJml);
        right.add(countLabel);

        top.add(left, BorderLayout.WEST);
        top.add(right, BorderLayout.EAST);

        panel.add(top, BorderLayout.NORTH);

        if (component instanceof JScrollPane) {
            ((JScrollPane) component).getViewport().setBackground(Color.WHITE);
            ((JScrollPane) component).setBorder(new EmptyBorder(0, 0, 0, 0));
        }

        JPanel centerWrap = new JPanel(new BorderLayout());
        centerWrap.setBackground(Color.WHITE);
        centerWrap.add(component, BorderLayout.CENTER);

        panel.add(centerWrap, BorderLayout.CENTER);
        return panel;
    }

    private DefaultTableModel createQueueTableModel() {
        return new DefaultTableModel(new Object[]{"ID Registrasi", "ID Pasien", "Nama Pasien", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                Object first = getValueAt(row, 0);
                return column == 3 && first != null && !"-".equals(first.toString());
            }
        };
    }

    private void styleTable(JTable table) {
        table.setRowHeight(31);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setForeground(COLOR_TEXT);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(new Color(214, 234, 248));
        table.setSelectionForeground(COLOR_TEXT);
        table.setGridColor(new Color(224, 230, 236));
        table.setShowVerticalLines(true);
        table.setShowHorizontalLines(true);
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(new Color(235, 241, 246));
        header.setForeground(COLOR_TEXT);
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(new EmptyBorder(0, 8, 0, 8));

                if (!isSelected) {
                    setBackground(Color.WHITE);
                    setForeground(COLOR_TEXT);
                }

                if (column == 3) {
                    String status = value == null ? "" : value.toString().toLowerCase();

                    if (!isSelected) {
                        if (status.contains("antrean")) {
                            setForeground(new Color(211, 84, 0));
                        } else if (status.contains("diperiksa")
                                || status.contains("proses")
                                || status.contains("dilayani")
                                || status.contains("dipanggil")) {
                            setForeground(new Color(41, 128, 185));
                        } else if (status.contains("siap")) {
                            setForeground(new Color(39, 174, 96));
                        } else if (status.contains("no response")) {
                            setForeground(new Color(192, 57, 43));
                        } else {
                            setForeground(COLOR_TEXT);
                        }
                    }
                }

                return c;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
    }

    private void pasangComboStatus(final JTable table, final DefaultTableModel model, String[] pilihan, final String statusAkhir) {
        JComboBox combo = new JComboBox(pilihan);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(combo));

        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 3) {
                    final int row = e.getFirstRow();

                    if (row >= 0 && row < model.getRowCount()) {
                        Object value = model.getValueAt(row, 3);

                        if (value != null && statusAkhir.equalsIgnoreCase(value.toString())) {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (row >= 0 && row < model.getRowCount()) {
                                        model.removeRow(row);

                                        if (model.getRowCount() == 0) {
                                            model.addRow(new Object[]{"-", "-", "Tidak ada antrean", "-"});
                                        }

                                        updateQueueCountLabels();
                                    }
                                }
                            });
                        } else {
                            updateQueueCountLabels();
                        }
                    }
                }
            }
        });
    }

    private void updateDashboard() {
        lblTotalPasien.setText(getCount("SELECT COUNT(*) FROM pasien"));
        lblTotalDokter.setText(getCount("SELECT COUNT(*) FROM dokter"));
        lblTotalPegawai.setText(getCount("SELECT COUNT(*) FROM pegawai"));

        loadQueueData(
                modelPemeriksaan,
                "SELECT id_registrasi, id_pasien, nama_pasien FROM pendaftaran_pasien ORDER BY id_registrasi DESC",
                "Antrean",
                "Belum ada antrean pemeriksaan"
        );

        loadQueueData(
                modelFarmasi,
                "SELECT id_registrasi, id_pasien, nama_pasien FROM pemeriksaan_dokter ORDER BY id_rekam_medis DESC",
                "Antrean Resep",
                "Belum ada antrean farmasi"
        );

        loadQueueData(
                modelKasir,
                "SELECT f.id_registrasi, p.id_pasien, f.nama_pasien " +
                "FROM farmasi f " +
                "LEFT JOIN pendaftaran_pasien p ON f.id_registrasi = p.id_registrasi " +
                "ORDER BY f.id_resep DESC",
                "Antrean",
                "Belum ada antrean kasir"
        );

        updateQueueCountLabels();
    }

    private String getCount(String sql) {
    
        String hasil = "0";

        try (
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)
        ) {

            if (rs.next()) {
                hasil = rs.getString(1);
            }

        } catch (Exception e) {
            hasil = "0";
        }

        return hasil;
    }

    private void loadQueueData(DefaultTableModel model, String sql, String statusAwal, String emptyMessage) {
        model.setRowCount(0);

        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    statusAwal
                });
            }

            rs.close();
            st.close();

            if (model.getRowCount() == 0) {
                model.addRow(new Object[]{"-", "-", emptyMessage, "-"});
            }

        } catch (Exception e) {
            model.setRowCount(0);
            model.addRow(new Object[]{"-", "-", "Gagal memuat data", "-"});
        }
    }

    private void updateQueueCountLabels() {
        lblJumlahAntreanPemeriksaan.setText(String.valueOf(getJumlahRealRow(modelPemeriksaan)));
        lblJumlahAntreanFarmasi.setText(String.valueOf(getJumlahRealRow(modelFarmasi)));
        lblJumlahAntreanKasir.setText(String.valueOf(getJumlahRealRow(modelKasir)));
    }

    private int getJumlahRealRow(DefaultTableModel model) {
        int jumlah = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            Object val = model.getValueAt(i, 0);
            if (val != null && !"-".equals(val.toString())) {
                jumlah++;
            }
        }
        return jumlah;
    }

    private void beralihKeMasterForm() {
        String[] pilihan = {
            "Form Dokter", "Form Kamar", "Form Obat",
            "Form Pasien", "Form Pegawai", "Form Poli"
        };

        String opsi = (String) JOptionPane.showInputDialog(
                this,
                "Pilih Form Master yang ingin dibuka:",
                "MASTER FORM",
                JOptionPane.QUESTION_MESSAGE,
                null,
                pilihan,
                pilihan[0]
        );

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

    private void beralihKeTransaksiForm() {
        String[] pilihan = {
            "Antrean Pemeriksaan",
            "Antrean Farmasi",
            "Antrean Kasir",
            "Form Pendaftaran",
            "Form Pemeriksaan",
            "Form Farmasi",
            "Form Kasir",
            "Form Rawat Inap"
        };

        String opsi = (String) JOptionPane.showInputDialog(
                this,
                "Pilih Form Transaksi yang ingin dibuka:",
                "FORM TRANSAKSI",
                JOptionPane.QUESTION_MESSAGE,
                null,
                pilihan,
                pilihan[0]
        );

        if (opsi != null) {
            JFrame form = null;

            if (opsi.equals("Antrean Pemeriksaan")) form = new AntreanPemeriksaan();
            else if (opsi.equals("Antrean Farmasi")) form = new AntreanFarmasi();
            else if (opsi.equals("Antrean Kasir")) form = new AntreanKasir();
            else if (opsi.equals("Form Pendaftaran")) form = new FormPendaftaran();
            else if (opsi.equals("Form Pemeriksaan")) form = new FormPemeriksaan();
            else if (opsi.equals("Form Farmasi")) form = new FormFarmasi();
            else if (opsi.equals("Form Kasir")) form = new FormKasir();
            else if (opsi.equals("Form Rawat Inap")) form = new FormRawatInap();

            bukaFormAnakAman(form);
        }
    }

    private void tampilkanMenuLaporan() {

        String[] pilihan = {
            "Laporan Pasien",
            "Laporan Pemeriksaan",
            "Laporan Farmasi",
            "Laporan Kasir"
        };

        String opsi = (String) JOptionPane.showInputDialog(
                this,
                "Pilih laporan",
                "Menu Laporan",
                JOptionPane.QUESTION_MESSAGE,
                null,
                pilihan,
                pilihan[0]);

        if (opsi == null) return;

        if (opsi.equals("Laporan Pasien")) {

            bukaLaporanJasper(
                    "Laporan Pasien",
                    "LaporanPasien.jrxml");

        } else if (opsi.equals("Laporan Pemeriksaan")) {

            bukaLaporanJasper(
                    "Laporan Pemeriksaan",
                    "laporanPemeriksaan.jrxml");

        } else if (opsi.equals("Laporan Farmasi")) {

            bukaLaporanJasper(
                    "Laporan Farmasi",
                    "laporanFarmasi.jrxml");

        } else if (opsi.equals("Laporan Kasir")) {

            bukaLaporanJasper(
                    "Laporan Kasir",
                    "LaporanKasir.jrxml");
        }
    }

    private void bukaLaporanJasper(String judul, String fileLaporan) {

        try {

            if (con == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Koneksi database gagal!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String lokasi = "src/Laporan/" + fileLaporan;

            File file = new File(lokasi);

            if (!file.exists()) {

                JOptionPane.showMessageDialog(
                        this,
                        "File tidak ditemukan:\n" + lokasi,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);

                return;
            }

            JasperReport report =
                    JasperCompileManager.compileReport(lokasi);

            HashMap<String, Object> parameter = new HashMap<String, Object>();

            JasperPrint print =
                    JasperFillManager.fillReport(
                            report,
                            parameter,
                            con);

            JasperViewer viewer =
                    new JasperViewer(print, false);

            viewer.setTitle(judul);
            viewer.setVisible(true);

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    e.toString(),
                    "Error Jasper Report",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tampilkanMenuMaintenance() {
        String[] opsi = {"Reset Semua Data Transaksi", "Batal"};

        int pilih = JOptionPane.showOptionDialog(
                this,
                "Pilih tindakan maintenance sistem:",
                "SYSTEM MAINTENANCE",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                opsi,
                opsi[1]
        );

        if (pilih == 0) {
            aksiResetSemuaData();
        }
    }

    private void aksiResetSemuaData() {
        int konfirm1 = JOptionPane.showConfirmDialog(
                this,
                "PERINGATAN:\nSemua data transaksi akan dihapus.\nLanjutkan?",
                "Konfirmasi Reset Tahap 1",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (konfirm1 == JOptionPane.YES_OPTION) {
            int konfirm2 = JOptionPane.showConfirmDialog(
                    this,
                    "Apakah Anda benar-benar yakin?\nData yang dihapus tidak dapat dikembalikan.",
                    "Konfirmasi Reset Tahap 2",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.ERROR_MESSAGE
            );

            if (konfirm2 == JOptionPane.YES_OPTION) {
                try {
                    st = con.createStatement();
                    st.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
                    jalankanSQLAman("TRUNCATE TABLE kasir");
                    jalankanSQLAman("TRUNCATE TABLE farmasi");
                    jalankanSQLAman("TRUNCATE TABLE pemeriksaan_dokter");
                    jalankanSQLAman("TRUNCATE TABLE pendaftaran_pasien");
                    st.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
                    st.close();

                    JOptionPane.showMessageDialog(this, "Data transaksi berhasil di-reset.");
                    updateDashboard();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Gagal reset data: " + e.getMessage());
                }
            }
        }
    }

    private void jalankanSQLAman(String sql) {
        try {
            Statement stReset = con.createStatement();
            stReset.executeUpdate(sql);
            stReset.close();
        } catch (Exception e) {
        }
    }

    private void bukaFormAnakAman(JFrame formAnak) {
        if (formAnak == null) {
             JOptionPane.showMessageDialog(
                 this,
                 "Form belum tersedia.",
                 "Informasi",
                 JOptionPane.INFORMATION_MESSAGE
             );
             return;
         }

         formAnak.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         formAnak.setLocationRelativeTo(this);
         formAnak.setVisible(true);
    }

    private void aksiKeluarTerpusat() {
        int konfirm = JOptionPane.showConfirmDialog(
                this,
                "Apakah Anda yakin ingin menutup aplikasi?",
                "Konfirmasi Keluar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (konfirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
      
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuUtama().setVisible(true);
            }
        });
    }
}