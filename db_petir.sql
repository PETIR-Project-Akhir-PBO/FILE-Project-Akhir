-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 31, 2025 at 04:05 PM
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
-- Database: `petir`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id_pengguna` int(11) NOT NULL,
  `akses` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id_pengguna`, `akses`) VALUES
(1, 'crud'),
(2, 'crud');

-- --------------------------------------------------------

--
-- Table structure for table `jadwal`
--

CREATE TABLE `jadwal` (
  `id_jadwal` int(11) NOT NULL,
  `jam_berangkat` datetime NOT NULL,
  `jam_tiba` datetime NOT NULL,
  `tanggal` date NOT NULL,
  `id_rute` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `jadwal`
--

INSERT INTO `jadwal` (`id_jadwal`, `jam_berangkat`, `jam_tiba`, `tanggal`, `id_rute`) VALUES
(1, '2025-10-20 07:00:00', '2025-10-20 09:30:00', '2025-10-20', 1),
(2, '2025-10-20 08:00:00', '2025-10-20 10:15:00', '2025-10-20', 2),
(3, '2025-10-20 06:30:00', '2025-10-20 09:00:00', '2025-10-20', 3),
(4, '2025-10-20 09:00:00', '2025-10-20 11:30:00', '2025-10-20', 4),
(5, '2025-10-20 10:00:00', '2025-10-20 12:15:00', '2025-10-20', 5),
(6, '2025-10-21 06:45:00', '2025-10-21 09:00:00', '2025-10-21', 6),
(7, '2025-10-21 07:15:00', '2025-10-21 09:45:00', '2025-10-21', 7),
(8, '2025-10-21 08:00:00', '2025-10-21 10:30:00', '2025-10-21', 8),
(9, '2025-10-21 09:15:00', '2025-10-21 11:45:00', '2025-10-21', 9),
(10, '2025-10-21 10:00:00', '2025-10-21 12:30:00', '2025-10-21', 10),
(11, '2025-10-22 06:30:00', '2025-10-22 09:15:00', '2025-10-22', 11),
(12, '2025-10-22 07:00:00', '2025-10-22 09:30:00', '2025-10-22', 12),
(13, '2025-10-22 08:15:00', '2025-10-22 10:45:00', '2025-10-22', 13),
(14, '2025-10-22 09:00:00', '2025-10-22 11:15:00', '2025-10-22', 14),
(15, '2025-10-22 10:30:00', '2025-10-22 12:45:00', '2025-10-22', 15),
(16, '2025-10-23 06:45:00', '2025-10-23 09:15:00', '2025-10-23', 16),
(17, '2025-10-23 07:30:00', '2025-10-23 10:00:00', '2025-10-23', 17),
(18, '2025-10-23 08:00:00', '2025-10-23 10:30:00', '2025-10-23', 18),
(19, '2025-10-23 09:15:00', '2025-10-23 11:30:00', '2025-10-23', 19),
(20, '2025-10-23 10:00:00', '2025-10-23 12:30:00', '2025-10-23', 20),
(21, '2025-10-24 06:00:00', '2025-10-24 08:45:00', '2025-10-24', 21),
(22, '2025-10-24 07:15:00', '2025-10-24 09:45:00', '2025-10-24', 22),
(23, '2025-10-24 08:00:00', '2025-10-24 10:30:00', '2025-10-24', 23),
(24, '2025-10-24 09:00:00', '2025-10-24 11:30:00', '2025-10-24', 24),
(25, '2025-10-24 10:00:00', '2025-10-24 12:30:00', '2025-10-24', 25),
(26, '2025-10-25 06:15:00', '2025-10-25 08:45:00', '2025-10-25', 26),
(27, '2025-10-25 07:00:00', '2025-10-25 09:30:00', '2025-10-25', 27),
(28, '2025-10-25 08:30:00', '2025-10-25 10:45:00', '2025-10-25', 28),
(29, '2025-10-25 09:00:00', '2025-10-25 11:15:00', '2025-10-25', 29),
(30, '2025-10-25 10:00:00', '2025-10-25 12:30:00', '2025-10-25', 30),
(31, '2025-10-26 06:45:00', '2025-10-26 09:00:00', '2025-10-26', 31),
(32, '2025-10-26 07:15:00', '2025-10-26 09:45:00', '2025-10-26', 32),
(33, '2025-10-26 08:00:00', '2025-10-26 10:15:00', '2025-10-26', 33),
(34, '2025-10-26 09:00:00', '2025-10-26 11:30:00', '2025-10-26', 34),
(35, '2025-10-26 10:15:00', '2025-10-26 12:45:00', '2025-10-26', 35),
(36, '2025-10-27 06:00:00', '2025-10-27 08:30:00', '2025-10-27', 36),
(37, '2025-10-27 07:00:00', '2025-10-27 09:30:00', '2025-10-27', 37),
(38, '2025-10-27 08:00:00', '2025-10-27 10:45:00', '2025-10-27', 38),
(39, '2025-10-27 09:15:00', '2025-10-27 11:45:00', '2025-10-27', 39),
(40, '2025-10-27 10:00:00', '2025-10-27 12:15:00', '2025-10-27', 40);

-- --------------------------------------------------------

--
-- Table structure for table `pengguna`
--

CREATE TABLE `pengguna` (
  `id_pengguna` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `no_telp` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pengguna`
--

INSERT INTO `pengguna` (`id_pengguna`, `nama`, `password`, `no_telp`) VALUES
(1, 'Admin 1', 'admin123', '2147483647'),
(2, 'Admin 2', 'admin456', '2147483647'),
(3, 'Lionel Messi', 'messi10', '2147483647'),
(4, 'Serena Williams', 'serena23', '2147483647'),
(5, 'Cristiano Ronaldo', 'ronaldo7', '2147483647');

-- --------------------------------------------------------

--
-- Table structure for table `penumpang`
--

CREATE TABLE `penumpang` (
  `id_pengguna` int(11) NOT NULL,
  `saldo` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `penumpang`
--

INSERT INTO `penumpang` (`id_pengguna`, `saldo`) VALUES
(3, 250000),
(4, 150000),
(5, 1980000);

-- --------------------------------------------------------

--
-- Table structure for table `rute`
--

CREATE TABLE `rute` (
  `id_rute` int(11) NOT NULL,
  `nama_rute` varchar(100) NOT NULL,
  `titik_awal` varchar(50) NOT NULL,
  `titik_akhir` varchar(50) NOT NULL,
  `id_transportasi` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rute`
--

INSERT INTO `rute` (`id_rute`, `nama_rute`, `titik_awal`, `titik_akhir`, `id_transportasi`) VALUES
(1, 'Jakarta - Surabaya', 'Jakarta', 'Surabaya', 1),
(2, 'Bandung - Yogyakarta', 'Bandung', 'Yogyakarta', 2),
(3, 'Jakarta - Semarang', 'Jakarta', 'Semarang', 3),
(4, 'Surabaya - Malang', 'Surabaya', 'Malang', 4),
(5, 'Surabaya - Banyuwangi', 'Surabaya', 'Banyuwangi', 5),
(6, 'Jakarta - Solo', 'Jakarta', 'Solo', 6),
(7, 'Yogyakarta - Malang', 'Yogyakarta', 'Malang', 7),
(8, 'Jakarta - Cirebon', 'Jakarta', 'Cirebon', 8),
(9, 'Surabaya - Madiun', 'Surabaya', 'Madiun', 9),
(10, 'Bandung - Surabaya', 'Bandung', 'Surabaya', 10),
(11, 'Jakarta - Purwokerto', 'Jakarta', 'Purwokerto', 11),
(12, 'Jakarta - Tegal', 'Jakarta', 'Tegal', 12),
(13, 'Bandung - Semarang', 'Bandung', 'Semarang', 13),
(14, 'Solo - Surabaya', 'Solo', 'Surabaya', 14),
(15, 'Surabaya - Denpasar', 'Surabaya', 'Denpasar', 15),
(16, 'Semarang - Surabaya', 'Semarang', 'Surabaya', 16),
(17, 'Yogyakarta - Jakarta', 'Yogyakarta', 'Jakarta', 17),
(18, 'Surabaya - Kediri', 'Surabaya', 'Kediri', 18),
(19, 'Malang - Banyuwangi', 'Malang', 'Banyuwangi', 19),
(20, 'Solo - Yogyakarta', 'Solo', 'Yogyakarta', 20),
(21, 'Jakarta - Bandung', 'Jakarta', 'Bandung', 21),
(22, 'Bandung - Tasikmalaya', 'Bandung', 'Tasikmalaya', 22),
(23, 'Jakarta - Bogor', 'Jakarta', 'Bogor', 23),
(24, 'Jakarta - Bekasi', 'Jakarta', 'Bekasi', 24),
(25, 'Bekasi - Bandung', 'Bekasi', 'Bandung', 25),
(26, 'Yogyakarta - Semarang', 'Yogyakarta', 'Semarang', 26),
(27, 'Surabaya - Probolinggo', 'Surabaya', 'Probolinggo', 27),
(28, 'Malang - Blitar', 'Malang', 'Blitar', 28),
(29, 'Semarang - Tegal', 'Semarang', 'Tegal', 29),
(30, 'Bandung - Garut', 'Bandung', 'Garut', 30),
(31, 'Jakarta - Bandung', 'Jakarta', 'Bandung', 31),
(32, 'Jakarta - Sukabumi', 'Jakarta', 'Sukabumi', 32),
(33, 'Bandung - Cirebon', 'Bandung', 'Cirebon', 33),
(34, 'Jakarta - Karawang', 'Jakarta', 'Karawang', 34),
(35, 'Bandung - Jakarta', 'Bandung', 'Jakarta', 35),
(36, 'Yogyakarta - Solo', 'Yogyakarta', 'Solo', 36),
(37, 'Semarang - Kudus', 'Semarang', 'Kudus', 37),
(38, 'Jakarta - Depok', 'Jakarta', 'Depok', 38),
(39, 'Bogor - Bandung', 'Bogor', 'Bandung', 39),
(40, 'Bandung - Cianjur', 'Bandung', 'Cianjur', 40);

-- --------------------------------------------------------

--
-- Table structure for table `tiket`
--

CREATE TABLE `tiket` (
  `id_tiket` int(11) NOT NULL,
  `harga` int(11) NOT NULL,
  `id_jadwal` int(11) NOT NULL,
  `id_transportasi` int(11) NOT NULL,
  `id_rute` int(11) NOT NULL,
  `id_pengguna` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tiket`
--

INSERT INTO `tiket` (`id_tiket`, `harga`, `id_jadwal`, `id_transportasi`, `id_rute`, `id_pengguna`) VALUES
(1, 75000, 1, 1, 1, 5),
(2, 80000, 2, 2, 2, 4),
(3, 70000, 3, 3, 3, 3),
(4, 85000, 4, 4, 4, 4),
(5, 90000, 5, 5, 5, 5),
(6, 95000, 6, 6, 6, 4),
(7, 88000, 7, 7, 7, 5),
(8, 87000, 8, 8, 8, 5),
(9, 92000, 9, 9, 9, 4),
(10, 91000, 10, 10, 10, 3),
(11, 76000, 11, 11, 11, 4),
(12, 78000, 12, 12, 12, 5),
(13, 80000, 13, 13, 13, 3),
(14, 83000, 14, 14, 14, 5),
(15, 85000, 15, 15, 15, 5),
(16, 82000, 16, 16, 16, 4),
(17, 87000, 17, 17, 17, 5),
(18, 90000, 18, 18, 18, 3),
(19, 88000, 19, 19, 19, 3),
(20, 92000, 20, 20, 20, 4),
(21, 94000, 21, 21, 21, 5),
(22, 91000, 22, 22, 22, 3),
(23, 93000, 23, 23, 23, 4),
(24, 89000, 24, 24, 24, 5),
(25, 95000, 25, 25, 25, 5),
(26, 87000, 26, 26, 26, 5),
(27, 88000, 27, 27, 27, 4),
(28, 86000, 28, 28, 28, 5),
(29, 90000, 29, 29, 29, 4),
(30, 93000, 30, 30, 30, 4),
(31, 85000, 31, 31, 31, 4),
(32, 87000, 32, 32, 32, 4),
(33, 88000, 33, 33, 33, 5),
(34, 92000, 34, 34, 34, 4),
(35, 95000, 35, 35, 35, 4),
(36, 87000, 36, 36, 36, 5),
(37, 91000, 37, 37, 37, 3),
(38, 93000, 38, 38, 38, 3),
(39, 94000, 39, 39, 39, 5),
(40, 96000, 40, 40, 40, 4);

-- --------------------------------------------------------

--
-- Table structure for table `transportasi`
--

CREATE TABLE `transportasi` (
  `id_transportasi` int(11) NOT NULL,
  `nama_transportasi` varchar(100) NOT NULL,
  `jenis` varchar(50) NOT NULL,
  `kapasitas` int(4) NOT NULL,
  `jadwal_keberangkatan` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transportasi`
--

INSERT INTO `transportasi` (`id_transportasi`, `nama_transportasi`, `jenis`, `kapasitas`, `jadwal_keberangkatan`) VALUES
(1, 'Kereta Argo Bromo', 'Kereta Api', 400, '2025-10-20 07:05:00'),
(2, 'Kereta Argo Wilis', 'Kereta Api', 380, '2025-10-20 08:05:00'),
(3, 'Kereta Taksaka', 'Kereta Api', 360, '2025-10-20 06:35:00'),
(4, 'Kereta Gajayana', 'Kereta Api', 350, '2025-10-20 09:05:00'),
(5, 'Kereta Mutiara Selatan', 'Kereta Api', 340, '2025-10-20 10:05:00'),
(6, 'Kereta Majapahit', 'Kereta Api', 370, '2025-10-21 06:50:00'),
(7, 'Kereta Jayabaya', 'Kereta Api', 360, '2025-10-21 07:20:00'),
(8, 'Kereta Serayu', 'Kereta Api', 330, '2025-10-21 08:05:00'),
(9, 'Kereta Malabar', 'Kereta Api', 370, '2025-10-21 09:20:00'),
(10, 'Kereta Turangga', 'Kereta Api', 390, '2025-10-21 10:05:00'),
(11, 'Bus Sinar Jaya', 'Bus', 45, '2025-10-22 06:35:00'),
(12, 'Bus Harapan Jaya', 'Bus', 48, '2025-10-22 07:05:00'),
(13, 'Bus Rosalia Indah', 'Bus', 50, '2025-10-22 08:20:00'),
(14, 'Bus Lorena', 'Bus', 46, '2025-10-22 09:05:00'),
(15, 'Bus Pahala Kencana', 'Bus', 50, '2025-10-22 10:35:00'),
(16, 'Bus Kramat Djati', 'Bus', 49, '2025-10-23 06:50:00'),
(17, 'Bus Nusantara', 'Bus', 47, '2025-10-23 07:35:00'),
(18, 'Bus Gunung Harta', 'Bus', 45, '2025-10-23 08:05:00'),
(19, 'Bus Primajasa', 'Bus', 46, '2025-10-23 09:20:00'),
(20, 'Bus Handoyo', 'Bus', 48, '2025-10-23 10:05:00'),
(21, 'Bus Sudiro Tungga Jaya', 'Bus', 47, '2025-10-24 06:05:00'),
(22, 'Bus Karina', 'Bus', 49, '2025-10-24 07:20:00'),
(23, 'Bus Doa Ibu', 'Bus', 44, '2025-10-24 08:05:00'),
(24, 'Bus Eka Cepat', 'Bus', 50, '2025-10-24 09:05:00'),
(25, 'Bus Mira', 'Bus', 50, '2025-10-24 10:05:00'),
(26, 'Bus Safari Dharma Raya', 'Bus', 48, '2025-10-25 06:20:00'),
(27, 'Bus Murni Jaya', 'Bus', 45, '2025-10-25 07:05:00'),
(28, 'Bus Bejeu', 'Bus', 46, '2025-10-25 08:35:00'),
(29, 'Bus NPM', 'Bus', 48, '2025-10-25 09:05:00'),
(30, 'Bus ALS', 'Bus', 47, '2025-10-25 10:05:00'),
(31, 'Travel Cipaganti', 'Travel', 12, '2025-10-26 06:50:00'),
(32, 'Travel Daytrans', 'Travel', 10, '2025-10-26 07:20:00'),
(33, 'Travel Baraya', 'Travel', 11, '2025-10-26 08:05:00'),
(34, 'Travel Jackal Holidays', 'Travel', 13, '2025-10-26 09:05:00'),
(35, 'Travel Big Bird', 'Travel', 14, '2025-10-26 10:20:00'),
(36, 'Travel Lintas Jawa', 'Travel', 12, '2025-10-27 06:05:00'),
(37, 'Travel Arimbi', 'Travel', 11, '2025-10-27 07:05:00'),
(38, 'Travel Cititrans', 'Travel', 12, '2025-10-27 08:05:00'),
(39, 'Travel Xtrans', 'Travel', 10, '2025-10-27 09:20:00'),
(40, 'Travel Pasteur Trans', 'Travel', 12, '2025-10-27 10:05:00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id_pengguna`);

--
-- Indexes for table `jadwal`
--
ALTER TABLE `jadwal`
  ADD PRIMARY KEY (`id_jadwal`),
  ADD KEY `id_rute` (`id_rute`);

--
-- Indexes for table `pengguna`
--
ALTER TABLE `pengguna`
  ADD PRIMARY KEY (`id_pengguna`);

--
-- Indexes for table `penumpang`
--
ALTER TABLE `penumpang`
  ADD PRIMARY KEY (`id_pengguna`);

--
-- Indexes for table `rute`
--
ALTER TABLE `rute`
  ADD PRIMARY KEY (`id_rute`),
  ADD KEY `id_transportasi` (`id_transportasi`);

--
-- Indexes for table `tiket`
--
ALTER TABLE `tiket`
  ADD PRIMARY KEY (`id_tiket`),
  ADD KEY `id_jadwal1` (`id_jadwal`),
  ADD KEY `fk_tiket_transportasi` (`id_transportasi`),
  ADD KEY `fk_tiket_rute` (`id_rute`),
  ADD KEY `fk_tiket_pengguna` (`id_pengguna`);

--
-- Indexes for table `transportasi`
--
ALTER TABLE `transportasi`
  ADD PRIMARY KEY (`id_transportasi`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `jadwal`
--
ALTER TABLE `jadwal`
  MODIFY `id_jadwal` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `pengguna`
--
ALTER TABLE `pengguna`
  MODIFY `id_pengguna` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `rute`
--
ALTER TABLE `rute`
  MODIFY `id_rute` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT for table `tiket`
--
ALTER TABLE `tiket`
  MODIFY `id_tiket` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `transportasi`
--
ALTER TABLE `transportasi`
  MODIFY `id_transportasi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`id_pengguna`) REFERENCES `pengguna` (`id_pengguna`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `jadwal`
--
ALTER TABLE `jadwal`
  ADD CONSTRAINT `jadwal_ibfk_1` FOREIGN KEY (`id_rute`) REFERENCES `rute` (`id_rute`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `penumpang`
--
ALTER TABLE `penumpang`
  ADD CONSTRAINT `penumpang_ibfk_1` FOREIGN KEY (`id_pengguna`) REFERENCES `pengguna` (`id_pengguna`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `rute`
--
ALTER TABLE `rute`
  ADD CONSTRAINT `rute_ibfk_1` FOREIGN KEY (`id_transportasi`) REFERENCES `transportasi` (`id_transportasi`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tiket`
--
ALTER TABLE `tiket`
  ADD CONSTRAINT `fk_tiket_pengguna` FOREIGN KEY (`id_pengguna`) REFERENCES `pengguna` (`id_pengguna`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_tiket_rute` FOREIGN KEY (`id_rute`) REFERENCES `rute` (`id_rute`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_tiket_transportasi` FOREIGN KEY (`id_transportasi`) REFERENCES `transportasi` (`id_transportasi`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tiket_ibfk_2` FOREIGN KEY (`id_jadwal`) REFERENCES `jadwal` (`id_jadwal`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
