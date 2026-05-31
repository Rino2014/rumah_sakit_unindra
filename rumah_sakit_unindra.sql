-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 31, 2026 at 02:18 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rumah_sakit_unindra`
--

-- --------------------------------------------------------

--
-- Table structure for table `antrean_farmasi`
--

CREATE TABLE `antrean_farmasi` (
  `id_antrean` int(11) NOT NULL,
  `id_pasien` varchar(20) DEFAULT NULL,
  `nama_pasien` varchar(100) DEFAULT NULL,
  `resep_obat` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `antrean_rawat_inap`
--

CREATE TABLE `antrean_rawat_inap` (
  `id_antrean` int(11) NOT NULL,
  `id_pasien` varchar(20) DEFAULT NULL,
  `nama_pasien` varchar(100) DEFAULT NULL,
  `dokter` varchar(100) DEFAULT NULL,
  `diagnosa` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `dokter`
--

CREATE TABLE `dokter` (
  `id_dokter` varchar(11) NOT NULL,
  `nama_dokter` varchar(100) DEFAULT NULL,
  `poli` varchar(100) DEFAULT NULL,
  `spesialis` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `no_telepon` varchar(20) DEFAULT NULL,
  `alamat` text DEFAULT NULL,
  `tarif_konsultasi` varchar(40) DEFAULT NULL,
  `hari_praktik` varchar(200) DEFAULT NULL,
  `jam_masuk` time DEFAULT NULL,
  `jam_keluar` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `dokter`
--

INSERT INTO `dokter` (`id_dokter`, `nama_dokter`, `poli`, `spesialis`, `email`, `no_telepon`, `alamat`, `tarif_konsultasi`, `hari_praktik`, `jam_masuk`, `jam_keluar`) VALUES
('DR001', 'Drs. Sabrina', 'Poli Anak', 'Spesialis Anak', 'sabrina@gmail.com', '0852112535444', 'TANJUNG PRIOK', '350000', 'Senin, Rabu, Jumat, ', '10:00:00', '16:00:00'),
('DR002', 'Dr. Sueb', 'Poli Jantung', 'Spesialis Jantung', 'Sueb@gmail.com', '085315642125', 'CIRACAS', '500000', 'Selasa, Kamis, Sabtu, ', '08:00:00', '17:00:00'),
('DR003', 'Dr. Azzam', 'Poli Anak', 'Spesialis Anak', 'azzam@gmail.com', '085211534004', 'CIPAYUNG', '400000', 'Senin, Selasa, Rabu, Kamis, Jumat, ', '08:00:00', '14:00:00'),
('DR004', 'Dr Azril', 'Poli Jantung', 'Spesialis Jantung', 'azril@gmail.com', '085211530045', 'BOGOR', '350000', 'Sabtu, Minggu, ', '10:00:00', '18:00:00'),
('DR005', 'Dr. Rina Putri', 'Poli Umum', 'Dokter Umum', 'rina.putri@gmail.com', '081234567801', 'BEKASI', '200000', 'Senin, Selasa, Rabu', '08:00:00', '14:00:00'),
('DR006', 'Dr. Budi Santoso', 'Poli Saraf', 'Spesialis Saraf', 'budi.santoso@gmail.com', '081234567802', 'DEPOK', '450000', 'Selasa, Kamis, Sabtu', '09:00:00', '16:00:00'),
('DR007', 'Dr. Maya Lestari', 'Poli Kandungan', 'Spesialis Kandungan', 'maya.lestari@gmail.com', '081234567803', 'JAKARTA TIMUR', '550000', 'Senin, Rabu, Jumat', '10:00:00', '17:00:00'),
('DR008', 'Dr. Andi Pratama', 'Poli THT', 'Spesialis THT', 'andi.pratama@gmail.com', '081234567804', 'TANGERANG', '400000', 'Senin, Kamis, Jumat', '08:00:00', '15:00:00'),
('DR009', 'Dr. Siti Rahma', 'Poli Mata', 'Spesialis Mata', 'siti.rahma@gmail.com', '081234567805', 'BEKASI', '500000', 'Rabu, Kamis, Sabtu', '09:00:00', '16:00:00'),
('DR010', 'Dr. Denny Wijaya', 'Poli Kulit', 'Spesialis Kulit dan Kelamin', 'denny.wijaya@gmail.com', '081234567806', 'JAKARTA SELATAN', '450000', 'Selasa, Rabu, Jumat', '10:00:00', '18:00:00'),
('DR011', 'Dr. Ridwan', 'Poli Gigi', 'Spesialis Anak', 'ridwan@gmail.com', '081234567807', 'CIBUBUR', '300000', 'Senin, Selasa, Kamis, Jumat, ', '08:00:00', '15:00:00'),
('DR012', 'Dr. Hendra Gunawan', 'Poli Paru', 'Spesialis Paru', 'hendra.gunawan@gmail.com', '081234567808', 'BOGOR', '475000', 'Senin, Rabu, Sabtu', '09:00:00', '17:00:00'),
('DR013', 'Dr. Nabila Aisyah', 'Poli Anak', 'Spesialis Anak', 'nabila.aisyah@gmail.com', '081234567809', 'BEKASI', '425000', 'Selasa, Kamis, Minggu', '08:00:00', '14:00:00'),
('DR014', 'Dr. Reza Maulana', 'Poli Bedah', 'Spesialis Bedah Umum', 'reza.maulana@gmail.com', '081234567810', 'JAKARTA PUSAT', '650000', 'Senin, Kamis, Sabtu', '11:00:00', '19:00:00'),
('DR015', 'Dr. Zul', 'Poli Anak', 'Spesialis Anak', 'zul@gmail.com', '082125545645', 'CIRACAS', '400000', 'Selasa, Jumat, Minggu, ', '06:00:00', '12:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `farmasi`
--

CREATE TABLE `farmasi` (
  `id_resep` varchar(11) NOT NULL,
  `id_registrasi` varchar(11) DEFAULT NULL,
  `nama_pasien` varchar(50) NOT NULL,
  `diskon` int(11) DEFAULT NULL,
  `total_bayar` varchar(20) DEFAULT NULL,
  `metode_pembayaran` varchar(50) DEFAULT NULL,
  `tanggal_resep` datetime DEFAULT current_timestamp(),
  `obat1` varchar(40) NOT NULL,
  `obat2` varchar(40) NOT NULL,
  `obat3` varchar(40) NOT NULL,
  `obat4` varchar(40) NOT NULL,
  `id_rekam_medis` varchar(40) NOT NULL,
  `subtotal1` varchar(30) DEFAULT NULL,
  `subtotal2` varchar(30) DEFAULT NULL,
  `subtotal3` varchar(30) DEFAULT NULL,
  `subtotal4` varchar(30) DEFAULT NULL,
  `status_kasir` varchar(50) NOT NULL DEFAULT 'Antrean'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `farmasi`
--

INSERT INTO `farmasi` (`id_resep`, `id_registrasi`, `nama_pasien`, `diskon`, `total_bayar`, `metode_pembayaran`, `tanggal_resep`, `obat1`, `obat2`, `obat3`, `obat4`, `id_rekam_medis`, `subtotal1`, `subtotal2`, `subtotal3`, `subtotal4`, `status_kasir`) VALUES
('FRM001', 'PDT001', 'Oni', 5, '418000', 'Transfer', '2026-05-30 00:00:00', 'Madu', 'CD-R 10 pcs ', 'Madu', 'CD-R 10 pcs ', 'PMR002', '120000', '100000', '120000', '100000', 'Selesai'),
('FRM002', 'PDT002', 'Samuel', 0, '33000', 'Cash', '2026-05-30 00:00:00', 'Paracetamol 500 mg', 'Paracetamol 500 mg', 'Antangin JRG', 'Promag', 'PMR001', '8000', '8000', '10000', '7000', 'Sedang Dilayani'),
('FRM003', 'PDT004', 'Siti Nurhaliza', 0, '100000', 'Cash', '2026-05-30 00:00:00', 'CD-R 10 pcs ', 'Amoxicillin 500 mg', 'OBH Combi', 'Promag', 'PMR005', '50000', '18000', '22000', '10000', 'Sedang Dilayani'),
('FRM004', 'PDT005', 'Budi Santoso', 0, '152000', 'Cash', '2026-05-30 00:00:00', 'Madu', 'Paracetamol 500 mg', 'Antangin JRG', 'Amoxicillin 500 mg', 'PMR006', '60000', '24000', '50000', '18000', 'Antrean'),
('FRM005', 'PDT006', 'Rina Marlina', 0, '323000', 'Cash', '2026-05-30 00:00:00', 'CD-R 10 pcs ', 'Amoxicillin 500 mg', 'Vitamin C 1000 mg', 'Antangin JRG', 'PMR007', '100000', '18000', '180000', '25000', 'Antrean'),
('FRM006', 'PDT007', 'Dedi Kurniawan', 5, '250800', 'Transfer', '2026-05-30 00:00:00', 'CD-R 10 pcs ', 'OBH Combi', 'Madu', 'Madu', 'PMR008', '100000', '44000', '120000', '0', 'Antrean');

-- --------------------------------------------------------

--
-- Table structure for table `kamar`
--

CREATE TABLE `kamar` (
  `id_kamar` varchar(11) NOT NULL,
  `nama_ruangan` varchar(100) DEFAULT NULL,
  `kelas_kamar` varchar(50) DEFAULT NULL,
  `tarif_per_hari` double DEFAULT NULL,
  `jumlah_bed` varchar(11) DEFAULT NULL,
  `status_ketersediaan` enum('Tersedia','Penuh') DEFAULT NULL,
  `fasilitas` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kamar`
--

INSERT INTO `kamar` (`id_kamar`, `nama_ruangan`, `kelas_kamar`, `tarif_per_hari`, `jumlah_bed`, `status_ketersediaan`, `fasilitas`) VALUES
('RK001', 'Anggrek', 'VIP', 1500000, '1 Bed', 'Tersedia', 'AC + Sofa + Lemari + TV + Kamar Mandi'),
('RK002', 'Melati', 'VIP', 1500000, '1 Bed', 'Tersedia', 'AC + Sofa + Lemari + TV + Kamar Mandi'),
('RK003', 'Mawar', 'Kelas I', 1000000, '2 Bed', 'Tersedia', 'AC + Lemari + TV + Kamar Mandi'),
('RK004', 'Tulip', 'Kelas I', 1000000, '2 Bed', 'Penuh', 'AC + Lemari + TV + Kamar Mandi'),
('RK005', 'Kenanga', 'Kelas II', 750000, '3 Bed', 'Tersedia', 'Kipas Angin + Lemari + Kamar Mandi'),
('RK006', 'Cempaka', 'Kelas II', 750000, '3 Bed', 'Penuh', 'Kipas Angin + Lemari + Kamar Mandi'),
('RK007', 'Flamboyan', 'Kelas III', 500000, '4 Bed', 'Tersedia', 'Kipas Angin + Kamar Mandi Bersama'),
('RK008', 'Bougenville', 'Kelas III', 500000, '4 Bed', 'Penuh', 'Kipas Angin + Kamar Mandi Bersama'),
('RK009', 'Dahlia', 'VIP', 1750000, '1 Bed', 'Tersedia', 'AC + Sofa + Lemari + Smart TV + Kamar Mandi'),
('RK010', 'Teratai', 'VVIP', 2500000, '1 Bed', 'Tersedia', 'AC + Sofa + Lemari + Smart TV + Kulkas + Kamar Mandi'),
('RK011', 'Lavender', 'VVIP', 2500000, '1 Bed', 'Penuh', 'AC + Sofa + Lemari + Smart TV + Kulkas + Kamar Mandi'),
('RK012', 'Edelweiss', 'Kelas I', 1000000, '2 Bed', 'Tersedia', 'AC + Lemari + TV + Kamar Mandi'),
('RK013', 'Sakura', 'Kelas II', 750000, '3 Bed', 'Tersedia', 'Kipas Angin + Lemari + Kamar Mandi'),
('RK014', 'Magnolia', 'Kelas III', 500000, '4 Bed', 'Tersedia', 'Kipas Angin + Kamar Mandi Bersama'),
('RK015', 'Orchid', 'VIP', 1750000, '1 Bed', 'Penuh', 'AC + Sofa + Lemari + Smart TV + Kamar Mandi'),
('RK016', 'Jasmine', 'Kelas 2', 500000, '1 Bed', 'Penuh', 'AC + Lemari'),
('RK017', 'Anggrek', 'Kelas 1', 750000, '1 Bed', 'Tersedia', 'AC + Lemari + TV + Kamar Mandi');

-- --------------------------------------------------------

--
-- Table structure for table `kasir`
--

CREATE TABLE `kasir` (
  `id_kasir` varchar(11) NOT NULL,
  `id_registrasi` varchar(20) NOT NULL,
  `id_pasien` varchar(11) DEFAULT NULL,
  `id_rawat_inap` varchar(11) DEFAULT NULL,
  `biaya_dokter` int(11) DEFAULT NULL,
  `biaya_obat` int(11) DEFAULT NULL,
  `id_resep` varchar(20) DEFAULT NULL,
  `biaya_kamar` int(11) DEFAULT NULL,
  `total_bayar` int(11) DEFAULT NULL,
  `metode_pembayaran` varchar(50) DEFAULT NULL,
  `id_rekam_medis` varchar(40) DEFAULT NULL,
  `nama_pasien` varchar(40) DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kasir`
--

INSERT INTO `kasir` (`id_kasir`, `id_registrasi`, `id_pasien`, `id_rawat_inap`, `biaya_dokter`, `biaya_obat`, `id_resep`, `biaya_kamar`, `total_bayar`, `metode_pembayaran`, `id_rekam_medis`, `nama_pasien`) VALUES
('KSR001', 'PDT001', 'PS001', '-', 400000, 418000, 'FRM001', 0, 818000, 'Transfer', 'PMR002', 'Oni'),
('KSR002', 'PDT002', 'PS002', '-', 500000, 33000, 'FRM002', 0, 533000, 'Cash', 'PMR001', 'Samuel'),
('KSR003', 'PDT004', 'PS004', '-', 200000, 100000, 'FRM003', 0, 300000, 'Cash', 'PMR005', 'Siti Nurhaliza'),
('KSR004', 'PDT005', 'PS005', '-', 425000, 152000, 'FRM004', 0, 577000, 'Cash', 'PMR006', 'Budi Santoso'),
('KSR005', 'PDT006', 'PS006', '-', 450000, 323000, 'FRM005', 0, 773000, 'Cash', 'PMR007', 'Rina Marlina'),
('KSR006', 'PDT007', 'PS007', '-', 400000, 250800, 'FRM006', 0, 650800, 'Transfer', 'PMR008', 'Dedi Kurniawan');

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `id_user` int(11) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `nama_lengkap` varchar(100) DEFAULT NULL,
  `level` enum('Admin','Dokter','Kasir','Farmasi','Pendaftaran') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`id_user`, `username`, `password`, `nama_lengkap`, `level`) VALUES
(1, 'admin', 'admin123', 'Administrator', 'Admin'),
(2, 'dokter', 'dokter123', 'Dokter Umum', 'Dokter'),
(3, 'kasir', 'kasir123', 'Petugas Kasir', 'Kasir'),
(4, 'farmasi', 'farmasi123', 'Petugas Farmasi', 'Farmasi');

-- --------------------------------------------------------

--
-- Table structure for table `obat`
--

CREATE TABLE `obat` (
  `id_obat` varchar(11) NOT NULL,
  `nama_obat` varchar(100) DEFAULT NULL,
  `jenis_obat` varchar(50) DEFAULT NULL,
  `satuan` varchar(30) DEFAULT NULL,
  `harga_beli` double DEFAULT NULL,
  `harga_jual` double DEFAULT NULL,
  `stok` int(11) DEFAULT NULL,
  `tanggal_expired` date DEFAULT NULL,
  `lokasi_rak` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `obat`
--

INSERT INTO `obat` (`id_obat`, `nama_obat`, `jenis_obat`, `satuan`, `harga_beli`, `harga_jual`, `stok`, `tanggal_expired`, `lokasi_rak`) VALUES
('OB001', 'Madu', 'Obat Herbal', 'botol', 50000, 60000, 10, '2026-08-27', 'Rak 1'),
('OB002', 'CD-R 10 pcs ', 'Obat Vitamin dan Suplemenbox', 'botol', 45000, 50000, 12, '2027-05-27', 'Rak 1'),
('OB003', 'Paracetamol 500 mg', 'Obat Analgesik', 'strip', 5000, 8000, 100, '2028-03-15', 'Rak 2'),
('OB004', 'Amoxicillin 500 mg', 'Obat Antibiotik', 'strip', 12000, 18000, 80, '2027-11-20', 'Rak 2'),
('OB005', 'Vitamin C 1000 mg', 'Obat Vitamin dan Suplemen', 'botol', 35000, 45000, 50, '2028-06-10', 'Rak 1'),
('OB006', 'OBH Combi', 'Obat Batuk', 'botol', 15000, 22000, 40, '2027-09-05', 'Rak 3'),
('OB007', 'Antangin JRG', 'Obat Herbal', 'box', 18000, 25000, 60, '2028-01-18', 'Rak 1'),
('OB008', 'Promag', 'Obat Maag', 'strip', 7000, 10000, 120, '2028-04-22', 'Rak 3'),
('OB009', 'Betadine Solution', 'Obat Antiseptik', 'botol', 18000, 25000, 30, '2029-02-14', 'Rak 4'),
('OB010', 'Bodrex Extra', 'Obat Analgesik', 'strip', 4000, 6500, 90, '2028-07-30', 'Rak 2'),
('OB011', 'Diapet', 'Obat Diare', 'box', 12000, 17000, 45, '2027-12-12', 'Rak 3'),
('OB012', 'Insto Regular', 'Obat Tetes Mata', 'botol', 9000, 14000, 35, '2028-09-01', 'Rak 4'),
('OB013', 'Tolak Angin Cair', 'Obat Herbal', 'box', 20000, 28000, 55, '2028-05-25', 'Rak 1'),
('OB014', 'Sangobion', 'Obat Vitamin dan Suplemen', 'strip', 10000, 15000, 70, '2028-08-08', 'Rak 1'),
('OB015', 'Mylanta', 'Obat Maag', 'botol', 22000, 30000, 25, '2028-10-16', 'Rak 3'),
('OB016', 'Procol', 'Obat Analgesik (Pereda Nyeri)', 'tablet', 10000, 15000, 10, '2027-05-26', 'Rak 1');

-- --------------------------------------------------------

--
-- Table structure for table `pasien`
--

CREATE TABLE `pasien` (
  `id_pasien` varchar(11) NOT NULL,
  `nama_pasien` varchar(100) DEFAULT NULL,
  `tempat_lahir` varchar(50) DEFAULT NULL,
  `tanggal_lahir` date DEFAULT NULL,
  `jenis_kelamin` enum('Laki-Laki','Perempuan') DEFAULT NULL,
  `jenis_asuransi` varchar(50) DEFAULT NULL,
  `no_telepon` varchar(20) DEFAULT NULL,
  `alamat` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pasien`
--

INSERT INTO `pasien` (`id_pasien`, `nama_pasien`, `tempat_lahir`, `tanggal_lahir`, `jenis_kelamin`, `jenis_asuransi`, `no_telepon`, `alamat`) VALUES
('PS001', 'Oni', 'Bogor', '2038-08-06', 'Laki-Laki', 'BPJS', '08125000454', 'BOGOR'),
('PS002', 'Samuel', 'Jakarta', '2013-05-27', 'Laki-Laki', 'CAR', '0852114752', 'CIJANTUNG'),
('PS003', 'Andi Pratama', 'Bekasi', '1998-03-15', 'Laki-Laki', 'BPJS', '081234567801', 'BEKASI'),
('PS004', 'Siti Nurhaliza', 'Bandung', '2001-07-22', 'Perempuan', 'Prudential', '081234567802', 'BANDUNG'),
('PS005', 'Budi Santoso', 'Depok', '1985-11-10', 'Laki-Laki', 'CAR', '081234567803', 'DEPOK'),
('PS006', 'Rina Marlina', 'Bogor', '1992-05-08', 'Perempuan', 'BPJS', '081234567804', 'BOGOR'),
('PS007', 'Dedi Kurniawan', 'Jakarta', '1978-09-30', 'Laki-Laki', 'Allianz', '081234567805', 'JAKARTA'),
('PS008', 'Maya Sari', 'Tangerang', '2004-01-12', 'Perempuan', 'BPJS', '081234567806', 'TANGERANG'),
('PS009', 'Rizky Hidayat', 'Bekasi', '1996-12-19', 'Laki-Laki', 'Manulife', '081234567807', 'BEKASI'),
('PS010', 'Fitri Anggraini', 'Cirebon', '1989-04-25', 'Perempuan', 'CAR', '081234567808', 'CIREBON'),
('PS011', 'Agus Saputra', 'Sukabumi', '1994-06-17', 'Laki-Laki', 'BPJS', '081234567809', 'SUKABUMI'),
('PS012', 'Dewi Lestari', 'Semarang', '1987-08-14', 'Perempuan', 'Prudential', '081234567810', 'SEMARANG'),
('PS013', 'Fajar Nugroho', 'Yogyakarta', '1990-02-28', 'Laki-Laki', 'BPJS', '081234567811', 'YOGYAKARTA'),
('PS014', 'Lina Oktavia', 'Malang', '1999-10-05', 'Perempuan', 'Allianz', '081234567812', 'MALANG'),
('PS015', 'Hendra Wijaya', 'Surabaya', '1983-07-09', 'Laki-Laki', 'CAR', '081234567813', 'SURABAYA'),
('PS016', 'Nadia Putri', 'Jakarta', '2002-11-23', 'Perempuan', 'BPJS', '081234567814', 'JAKARTA'),
('PS017', 'Rudi Hartono', 'Bekasi', '1975-03-03', 'Laki-Laki', 'Manulife', '081234567815', 'BEKASI'),
('PS018', 'Yuni Kartika', 'Bandung', '1997-09-18', 'Perempuan', 'Prudential', '081234567816', 'BANDUNG'),
('PS019', 'Arif Rahman', 'Bogor', '1988-01-30', 'Laki-Laki', 'BPJS', '081234567817', 'BOGOR'),
('PS020', 'Citra Amelia', 'Depok', '1995-12-07', 'Perempuan', 'Allianz', '081234567818', 'DEPOK'),
('PS021', 'Rina', 'Jakarta', '2016-05-20', 'Perempuan', 'BPJS', '085231145256', 'SERANG'),
('PS022', 'Akbar', 'Bogor', '2017-05-19', 'Laki-Laki', 'BPJS', '0852115342201', 'BOGOR');

-- --------------------------------------------------------

--
-- Table structure for table `pegawai`
--

CREATE TABLE `pegawai` (
  `id_pegawai` varchar(11) NOT NULL,
  `nama_pegawai` varchar(100) DEFAULT NULL,
  `jabatan` varchar(50) DEFAULT NULL,
  `unit_kerja` varchar(100) DEFAULT NULL,
  `shift_kerja` varchar(50) DEFAULT NULL,
  `no_telepon` varchar(20) DEFAULT NULL,
  `alamat` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pegawai`
--

INSERT INTO `pegawai` (`id_pegawai`, `nama_pegawai`, `jabatan`, `unit_kerja`, `shift_kerja`, `no_telepon`, `alamat`) VALUES
('PG001', 'Azril', 'Staff', 'POLI ANAK', 'Shift 1 - Pagi', '085211534100', 'BOGOR'),
('PG002', 'Rizky Pratama', 'Staff', 'POLI UMUM', 'Shift 1 - Pagi', '085211534101', 'DEPOK'),
('PG003', 'Dewi Lestari', 'Staff', 'POLI GIGI', 'Shift 1 - Pagi', '085211534102', 'BEKASI'),
('PG004', 'Andi Saputra', 'Staff', 'INSTALASI FARMASI', 'Shift 2 - Siang', '085211534103', 'JAKARTA TIMUR'),
('PG005', 'Nabila Putri', 'Staff', 'LABORATORIUM', 'Shift 2 - Siang', '085211534104', 'BOGOR'),
('PG006', 'Fajar Nugroho', 'Staff', 'POLI ANAK', 'Shift 3 - Malam', '085211534105', 'TANGERANG'),
('PG007', 'Siti Rahma', 'Staff', 'RAWAT INAP', 'Shift 1 - Pagi', '085211534106', 'BEKASI'),
('PG008', 'Muhammad Iqbal', 'Staff', 'IGD', 'Shift 3 - Malam', '085211534107', 'DEPOK'),
('PG009', 'Lina Marlina', 'Staff', 'RADIOLOGI', 'Shift 2 - Siang', '085211534108', 'KARAWANG'),
('PG010', 'Arif Hidayat', 'Staff', 'POLI JANTUNG', 'Shift 1 - Pagi', '085211534109', 'JAKARTA SELATAN'),
('PG011', 'Yuni Kartika', 'Staff', 'REKAM MEDIS', 'Shift 2 - Siang', '085211534110', 'BEKASI'),
('PG012', 'Bagus Santoso', 'Staff', 'KEUANGAN', 'Shift 1 - Pagi', '085211534111', 'BOGOR'),
('PG013', 'Maya Sari', 'Staff', 'ADMINISTRASI', 'Shift 2 - Siang', '085211534112', 'TANGERANG'),
('PG014', 'Rudi Hartono', 'Staff', 'POLI SARAF', 'Shift 3 - Malam', '085211534113', 'DEPOK'),
('PG015', 'Fitriani', 'Staff', 'POLI MATA', 'Shift 1 - Pagi', '085211534114', 'JAKARTA TIMUR'),
('PG016', 'Hendra Wijaya', 'Staff', 'RAWAT JALAN', 'Shift 2 - Siang', '085211534115', 'BEKASI'),
('PG017', 'Akbar', 'Manager', 'Kasir', 'Shift 1 - Pagi', '085211534005', 'CIJANTUNG'),
('PG018', 'Rina', 'Manager', 'Kasir', 'Shift 1 - Pagi', '0852145562542', 'TANJUNG PRIOK');

-- --------------------------------------------------------

--
-- Table structure for table `pemeriksaan_dokter`
--

CREATE TABLE `pemeriksaan_dokter` (
  `id_rekam_medis` varchar(11) NOT NULL,
  `id_registrasi` varchar(11) DEFAULT NULL,
  `id_pasien` varchar(11) DEFAULT NULL,
  `id_dokter` varchar(11) DEFAULT NULL,
  `nama_dokter` varchar(40) NOT NULL,
  `nama_pasien` varchar(40) NOT NULL,
  `poli` varchar(100) DEFAULT NULL,
  `keluhan_awal` text DEFAULT NULL,
  `diagnosa` text DEFAULT NULL,
  `tekanan_darah` varchar(20) DEFAULT NULL,
  `suhu_tubuh` varchar(20) DEFAULT NULL,
  `berat_badan` varchar(20) DEFAULT NULL,
  `golongan_darah` varchar(5) DEFAULT NULL,
  `tindakan_medis` enum('Rawat Jalan','Rawat Inap') DEFAULT NULL,
  `resep_obat` varchar(200) NOT NULL,
  `biaya_dokter` int(40) DEFAULT NULL,
  `catatan_dokter` text DEFAULT NULL,
  `status_farmasi` varchar(50) NOT NULL DEFAULT 'Antrean Resep'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pemeriksaan_dokter`
--

INSERT INTO `pemeriksaan_dokter` (`id_rekam_medis`, `id_registrasi`, `id_pasien`, `id_dokter`, `nama_dokter`, `nama_pasien`, `poli`, `keluhan_awal`, `diagnosa`, `tekanan_darah`, `suhu_tubuh`, `berat_badan`, `golongan_darah`, `tindakan_medis`, `resep_obat`, `biaya_dokter`, `catatan_dokter`, `status_farmasi`) VALUES
('PMR001', 'PDT002', 'PS002', 'DR002', 'Dr. Sueb', 'Samuel', 'Poli Jantung', 'Jantung Kronis', 'Jantung berdebar terus', '120/80', '34', '75', 'A', 'Rawat Jalan', 'Vitamin Madu', 500000, 'Banyak Istirahat ', 'Obat Diterima'),
('PMR002', 'PDT001', 'PS001', 'DR003', 'Dr. Azzam', 'Oni', 'Poli Anak', 'Maag', 'Sakit Perut', '80/110', '35', '65', 'A', 'Rawat Jalan', 'Madu', 400000, 'Banyak Minum Air Putih', 'Obat Diterima'),
('PMR005', 'PDT004', 'PS004', 'DR005', 'Dr. Rina Putri', 'Siti Nurhaliza', 'Poli Umum', 'kelelahan', 'Sakit Pinggang dan Pusing', '90/90', '37', '90', 'O', 'Rawat Inap', 'panadol', 200000, 'jangan begadang', 'Siap Diserahkan'),
('PMR006', 'PDT005', 'PS005', 'DR013', 'Dr. Nabila Aisyah', 'Budi Santoso', 'Poli Anak', 'kangker', 'muntaber', '120/80', '34', '68', 'B', 'Rawat Inap', 'vitamin', 425000, 'jangan makan buah semangka', 'Siap Diserahkan'),
('PMR007', 'PDT006', 'PS006', 'DR006', 'Dr. Budi Santoso', 'Rina Marlina', 'Poli Saraf', 'kangker otak', 'saraf kejepit', '110/85', '35', '70', 'AB', 'Rawat Inap', 'vitamin', 450000, '', 'Antrean Resep'),
('PMR008', 'PDT007', 'PS007', 'DR008', 'Dr. Andi Pratama', 'Dedi Kurniawan', 'Poli THT', 'kangker telinga', 'tidak bisa mendengar sempurna', '120/80', '34', '55', 'O', 'Rawat Inap', ' vitamin', 400000, '', 'Antrean Resep'),
('PMR009', 'PDT008', 'PS008', 'DR003', 'Dr. Azzam', 'Maya Sari', 'Poli Anak', 'Muntaber', 'Sakit Perut', '120/80', '36', '50', 'A', 'Rawat Jalan', 'Mylanta 1 botol', 400000, 'istirahat cukup', 'Antrean Resep'),
('PMR010', 'PDT009', 'PS009', 'DR007', 'Dr. Maya Lestari', 'Rizky Hidayat', 'Poli Kandungan', 'Maag', 'Mual sering', '121/82', '35', '75', 'O', 'Rawat Jalan', 'mylanta', 550000, 'isitrahat cukup', 'Antrean Resep');

-- --------------------------------------------------------

--
-- Table structure for table `pendaftaran_pasien`
--

CREATE TABLE `pendaftaran_pasien` (
  `id_registrasi` varchar(11) NOT NULL,
  `tanggal_kunjungan` date DEFAULT NULL,
  `id_pasien` varchar(11) DEFAULT NULL,
  `nama_pasien` varchar(40) NOT NULL,
  `tempat_lahir` varchar(20) NOT NULL,
  `tanggal_lahir` varchar(50) NOT NULL,
  `jenis_kelamin` varchar(20) NOT NULL,
  `no_telepon` varchar(20) NOT NULL,
  `alamat` text NOT NULL,
  `poli` varchar(100) DEFAULT NULL,
  `id_dokter` varchar(11) DEFAULT NULL,
  `nama_dokter` varchar(40) NOT NULL,
  `jenis_pembayaran` varchar(50) DEFAULT NULL,
  `keluhan_awal` text DEFAULT NULL,
  `status_pemeriksaan` varchar(50) NOT NULL DEFAULT 'Antrean'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pendaftaran_pasien`
--

INSERT INTO `pendaftaran_pasien` (`id_registrasi`, `tanggal_kunjungan`, `id_pasien`, `nama_pasien`, `tempat_lahir`, `tanggal_lahir`, `jenis_kelamin`, `no_telepon`, `alamat`, `poli`, `id_dokter`, `nama_dokter`, `jenis_pembayaran`, `keluhan_awal`, `status_pemeriksaan`) VALUES
('PDT001', '2026-05-28', 'PS001', 'Oni', 'Bogor', '2038-08-06', 'Laki-Laki', '08125000454', 'BOGOR', 'Poli Anak', 'DR003', 'Dr. Azzam', 'Transfer', 'Sakit Perut', 'Selesai Diperiksa'),
('PDT002', '2026-05-28', 'PS002', 'Samuel', 'Jakarta', '2013-05-27', 'Laki-Laki', '0852114752', 'CIJANTUNG', 'Poli Jantung', 'DR002', 'Dr. Sueb', 'Cash', 'Jantung berdebar terus', 'Diperiksa'),
('PDT003', '2026-05-29', 'PS001', 'Asep', 'Poli Jantung', 'DR002', 'Dr. Sueb', 'Transfer', 'Dag Dig Dug', '-- Pilih Poli --', 'Item 1', '', 'Transfer', '', 'Selesai Diperiksa'),
('PDT004', '2026-05-30', 'PS004', 'Siti Nurhaliza', 'Bandung', '2001-07-22', 'Perempuan', '081234567802', 'BANDUNG', 'Poli Umum', 'DR005', 'Dr. Rina Putri', 'Cash', 'Sakit Pinggang dan Pusing', 'Diperiksa'),
('PDT005', '2026-05-30', 'PS005', 'Budi Santoso', 'Depok', '1985-11-10', 'Laki-Laki', '081234567803', 'DEPOK', 'Poli Anak', 'DR013', 'Dr. Nabila Aisyah', 'Asuransi', 'muntaber', 'Diperiksa'),
('PDT006', '2026-05-30', 'PS006', 'Rina Marlina', 'Bogor', '1992-05-08', 'Perempuan', '081234567804', 'BOGOR', 'Poli Saraf', 'DR006', 'Dr. Budi Santoso', 'Cash', 'saraf kejepit', 'Antrean'),
('PDT007', '2026-05-30', 'PS007', 'Dedi Kurniawan', 'Poli THT', 'DR008', 'Dr. Andi Pratama', 'Asuransi', 'tidak bisa mendengar sempurna', 'Poli THT', 'DR008', 'Dr. Andi Pratama', 'Transfer', 'Ada kotoran ', 'Antrean'),
('PDT008', '2026-05-30', 'PS008', 'Maya Sari', 'Tangerang', '2004-01-12', 'Perempuan', '081234567806', 'TANGERANG', 'Poli Anak', 'DR003', 'Dr. Azzam', 'Cash', 'Sakit Perut', 'Antrean'),
('PDT009', '2026-05-30', 'PS009', 'Rizky Hidayat', 'Bekasi', '1996-12-19', 'Laki-Laki', '081234567807', 'BEKASI', 'Poli Kandungan', 'DR007', 'Dr. Maya Lestari', 'Cash', 'Mual sering', 'Antrean');

-- --------------------------------------------------------

--
-- Table structure for table `poli_klinik`
--

CREATE TABLE `poli_klinik` (
  `id_poli` varchar(11) NOT NULL,
  `nama_poli` varchar(100) DEFAULT NULL,
  `kepala_poli` varchar(100) DEFAULT NULL,
  `lokasi_poli` varchar(100) DEFAULT NULL,
  `jadwal_operasional` varchar(100) DEFAULT NULL,
  `dokter_penanggung_jawab` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `poli_klinik`
--

INSERT INTO `poli_klinik` (`id_poli`, `nama_poli`, `kepala_poli`, `lokasi_poli`, `jadwal_operasional`, `dokter_penanggung_jawab`) VALUES
('PL001', 'Poli Jantung', 'Dr Joko', 'Lt. 2', 'Selasa, Kamis, ', 'Dr. Ahmad'),
('PL002', 'Poli Anak', 'Dr. Zul', 'Lt. 3', 'Senin, Kamis, Sabtu, ', 'Drs. Mia'),
('PL003', 'Poli Gigi', 'Dr Rani', 'Lt. 3', 'Sabtu, Minggu, ', 'Dr. Kurnia'),
('PL004', 'Poli Mata', 'Dr. Sinta', 'Lt. 2', 'Senin, Rabu, Jumat', 'Dr. Andi'),
('PL005', 'Poli THT', 'Dr. Budi', 'Lt. 1', 'Selasa, Kamis, Sabtu', 'Dr. Fajar'),
('PL006', 'Poli Saraf', 'Dr. Lestari', 'Lt. 4', 'Senin, Selasa, Kamis', 'Dr. Rizal'),
('PL007', 'Poli Kulit dan Kelamin', 'Dr. Ratna', 'Lt. 3', 'Rabu, Jumat, Sabtu', 'Dr. Hendra'),
('PL008', 'Poli Kandungan', 'Dr. Maya', 'Lt. 2', 'Senin, Kamis, Jumat', 'Dr. Dewi'),
('PL009', 'Poli Bedah', 'Dr. Herman', 'Lt. 4', 'Selasa, Rabu, Jumat', 'Dr. Arif'),
('PL010', 'Poli Paru', 'Dr. Fitri', 'Lt. 3', 'Senin, Rabu, Sabtu', 'Dr. Dimas'),
('PL011', 'Poli Penyakit Dalam', 'Dr. Yuni', 'Lt. 2', 'Senin, Selasa, Jumat', 'Dr. Bambang'),
('PL012', 'Poli Rehabilitasi Medik', 'Dr. Agus', 'Lt. 1', 'Selasa, Kamis, Jumat', 'Dr. Nanda'),
('PL013', 'Poli Psikologi', 'Dr. Rina', 'Lt. 5', 'Rabu, Kamis, Sabtu', 'Dr. Sari'),
('PL014', 'Poli Anak', 'Drs. Harni', 'Lt. 5', 'Selasa, Kamis, Sabtu, ', 'Dr. Haul');

-- --------------------------------------------------------

--
-- Table structure for table `rawat_inap`
--

CREATE TABLE `rawat_inap` (
  `id_rawat_inap` varchar(11) NOT NULL,
  `id_pasien` varchar(11) DEFAULT NULL,
  `id_dokter` varchar(11) DEFAULT NULL,
  `id_kamar` varchar(11) DEFAULT NULL,
  `id_registrasi` varchar(40) NOT NULL,
  `id_rekam_medis` varchar(40) NOT NULL,
  `nama_dokter` varchar(40) NOT NULL,
  `nama_pasien` varchar(40) NOT NULL,
  `tanggal_masuk` datetime DEFAULT NULL,
  `tanggal_keluar` datetime DEFAULT NULL,
  `diagnosa` text DEFAULT NULL,
  `biaya_kamar` double DEFAULT NULL,
  `status_rawat` enum('Dirawat','Pulang') DEFAULT 'Dirawat',
  `total_biaya` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `antrean_farmasi`
--
ALTER TABLE `antrean_farmasi`
  ADD PRIMARY KEY (`id_antrean`);

--
-- Indexes for table `antrean_rawat_inap`
--
ALTER TABLE `antrean_rawat_inap`
  ADD PRIMARY KEY (`id_antrean`);

--
-- Indexes for table `dokter`
--
ALTER TABLE `dokter`
  ADD PRIMARY KEY (`id_dokter`);

--
-- Indexes for table `farmasi`
--
ALTER TABLE `farmasi`
  ADD PRIMARY KEY (`id_resep`),
  ADD KEY `id_pasien` (`id_registrasi`);

--
-- Indexes for table `kamar`
--
ALTER TABLE `kamar`
  ADD PRIMARY KEY (`id_kamar`);

--
-- Indexes for table `kasir`
--
ALTER TABLE `kasir`
  ADD PRIMARY KEY (`id_kasir`),
  ADD KEY `id_pasien` (`id_pasien`),
  ADD KEY `id_dokter` (`id_rawat_inap`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `pasien`
--
ALTER TABLE `pasien`
  ADD PRIMARY KEY (`id_pasien`);

--
-- Indexes for table `pegawai`
--
ALTER TABLE `pegawai`
  ADD PRIMARY KEY (`id_pegawai`);

--
-- Indexes for table `pemeriksaan_dokter`
--
ALTER TABLE `pemeriksaan_dokter`
  ADD PRIMARY KEY (`id_rekam_medis`),
  ADD KEY `id_registrasi` (`id_registrasi`),
  ADD KEY `id_pasien` (`id_pasien`),
  ADD KEY `id_dokter` (`id_dokter`);

--
-- Indexes for table `pendaftaran_pasien`
--
ALTER TABLE `pendaftaran_pasien`
  ADD PRIMARY KEY (`id_registrasi`),
  ADD KEY `id_pasien` (`id_pasien`),
  ADD KEY `id_dokter` (`id_dokter`);

--
-- Indexes for table `poli_klinik`
--
ALTER TABLE `poli_klinik`
  ADD PRIMARY KEY (`id_poli`);

--
-- Indexes for table `rawat_inap`
--
ALTER TABLE `rawat_inap`
  ADD PRIMARY KEY (`id_rawat_inap`),
  ADD KEY `id_pasien` (`id_pasien`),
  ADD KEY `id_dokter` (`id_dokter`),
  ADD KEY `id_kamar` (`id_kamar`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `antrean_farmasi`
--
ALTER TABLE `antrean_farmasi`
  MODIFY `id_antrean` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `antrean_rawat_inap`
--
ALTER TABLE `antrean_rawat_inap`
  MODIFY `id_antrean` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `login`
--
ALTER TABLE `login`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
