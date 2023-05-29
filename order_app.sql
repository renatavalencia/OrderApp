-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 26, 2023 at 09:44 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `order_app`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customer_id` varchar(20) NOT NULL,
  `customer_name` varchar(100) NOT NULL,
  `customer_address` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customer_id`, `customer_name`, `customer_address`) VALUES
('CS001', 'Amber Hartanto', 'Jl. Pegangsaan Dua, RT.13/RW.3, Pegangsaan Dua, Kec. Klp. Gading, Jkt Utara, Daerah Khusus Ibukota J'),
('CS002', 'Orlando Davinci', 'Bundaran Akbar No.28, RT.13/RW.6, Kb. Kosong, Kec. Kemayoran, Kota Jakarta Pusat, Daerah Khusus Ibuk'),
('CS003', 'Michelle Natasha', 'Jl. Pantai Indah Kapuk, RT.8/RW.1, Kamal Muara, Kec. Penjaringan, Jkt Utara, Daerah Khusus Ibukota J');

-- --------------------------------------------------------

--
-- Table structure for table `history`
--

CREATE TABLE `history` (
  `order_id` varchar(20) NOT NULL,
  `user_id` varchar(20) NOT NULL,
  `customer_id` varchar(20) NOT NULL,
  `order_status` varchar(100) NOT NULL,
  `order_date` varchar(20) NOT NULL,
  `total_price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `history`
--

INSERT INTO `history` (`order_id`, `user_id`, `customer_id`, `order_status`, `order_date`, `total_price`) VALUES
('1000001', 'US001', 'CS003', 'Sent', '10 Mei 2023', 201000),
('1000002', 'US002', 'CS002', 'Canceled', '17 Mei 2023', 83000),
('1000003', 'US001', 'CS001', 'On Going', '19 Mei 2023', 123000);

-- --------------------------------------------------------

--
-- Table structure for table `inventory`
--

CREATE TABLE `inventory` (
  `id` int(11) NOT NULL,
  `product_id` varchar(100) NOT NULL,
  `product_stock` int(11) NOT NULL,
  `product_ordered` int(11) NOT NULL,
  `product_available` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `inventory`
--

INSERT INTO `inventory` (`id`, `product_id`, `product_stock`, `product_ordered`, `product_available`) VALUES
(1, 'P001', 1576, 6, 1570),
(2, 'P002', 999, 9, 990),
(3, 'P003', 1290, 10, 1280),
(4, 'P004', 555, 4, 551);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `product_id` varchar(100) NOT NULL,
  `product_name` varchar(100) NOT NULL,
  `product_url` varchar(200) NOT NULL,
  `product_category` varchar(100) NOT NULL,
  `product_price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`product_id`, `product_name`, `product_url`, `product_category`, `product_price`) VALUES
('P001', 'Kalas', 'https://d2xjmi1k71iy2m.cloudfront.net/dairyfarm/id/images/980/0998019_PE822917_S4.jpg', 'Bedroom', 24900),
('P002', 'Trummis', 'https://d2xjmi1k71iy2m.cloudfront.net/dairyfarm/id/images/283/1028311_PE835295_S4.jpg', 'Office', 149000),
('P003', 'Pepprig', 'https://d2xjmi1k71iy2m.cloudfront.net/dairyfarm/id/images/228/0822824_PE775697_S5.jpg', 'Home Appliances', 27900),
('P004', 'Kleppstad', 'https://d2xjmi1k71iy2m.cloudfront.net/dairyfarm/id/images/333/0733324_PE748781_S4.jpg', 'Bedroom', 1799000);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` varchar(20) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `full_name`, `username`, `password`) VALUES
('US001', 'Sita Astuti', 'sitatuti', 'sita123'),
('US002', 'Andi Pernata', 'andipernata', 'andi123'),
('US003', 'Jessica Orlando', 'jessicaorlando', 'jessica123');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customer_id`);

--
-- Indexes for table `history`
--
ALTER TABLE `history`
  ADD PRIMARY KEY (`order_id`);

--
-- Indexes for table `inventory`
--
ALTER TABLE `inventory`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`product_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `inventory`
--
ALTER TABLE `inventory`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
