-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 26, 2023 at 01:16 PM
-- Server version: 5.6.16
-- PHP Version: 5.5.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `e_toll`
--

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE IF NOT EXISTS `feedback` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `feedback` varchar(200) NOT NULL,
  `report` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `fine`
--

CREATE TABLE IF NOT EXISTS `fine` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `x` varchar(80) NOT NULL,
  `y` varchar(100) NOT NULL,
  `z` varchar(100) NOT NULL,
  `description` varchar(80) NOT NULL,
  `latitude` varchar(100) NOT NULL,
  `longitude` varchar(100) NOT NULL,
  `fine_amount` varchar(100) NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `status` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Dumping data for table `fine`
--

INSERT INTO `fine` (`id`, `uid`, `x`, `y`, `z`, `description`, `latitude`, `longitude`, `fine_amount`, `date`, `time`, `status`) VALUES
(11, 2, '18.878', '2.44', '9.254', 'Over Speed detected', '10.0068198', '76.3044249', '1000', '2023-07-25', '11:32:26', 'paid successfully'),
(12, 2, '24.986', '3.206', '10.977', 'Over Speed detected', '10.0067995', '76.3044256', '1000', '2023-07-26', '11:33:31', 'paid successfully'),
(13, 2, '12.98', '4.968', '13.026', 'Over Speed detected', '10.006721666666666', '76.30431666666667', '1000', '2023-07-26', '12:59:10', 'paid successfully'),
(14, 2, '19.624', '-0.891', '4.352', 'Over Speed detected', '10.006493333333333', '76.30431833333334', '1000', '2023-07-26', '12:59:31', 'pending');

-- --------------------------------------------------------

--
-- Table structure for table `payment_tbl`
--

CREATE TABLE IF NOT EXISTS `payment_tbl` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) NOT NULL,
  `amount` varchar(100) NOT NULL,
  `card_name` varchar(100) NOT NULL,
  `cardnumber` varchar(100) NOT NULL,
  `cardtype` varchar(100) NOT NULL,
  `cardmonth` varchar(100) NOT NULL,
  `cardyear` varchar(100) NOT NULL,
  `cvv` varchar(100) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `payment_tbl`
--

INSERT INTO `payment_tbl` (`id`, `user_id`, `amount`, `card_name`, `cardnumber`, `cardtype`, `cardmonth`, `cardyear`, `cvv`, `date`) VALUES
(2, '2', '1000', 'Shibin Puthiyottil Shajy', '25809631478', 'Credit', 'MM', 'YY', '222', '2016-06-23'),
(3, '2', '123456', 'adfgj', '78995456325', 'Credit', '9', '2025', '566', '2016-06-23'),
(4, '2', '1000', 'faizi', '2580963147987', 'Credit', '7', '2021', '333', '2021-06-23'),
(5, '2', '2000', 'asfgh', '258899665', 'Credit', '6', '2021', '333', '2021-06-23'),
(6, '2', '2000', 'fauz', '258964313616194', 'Credit', '6', '2024', '222', '2021-06-23'),
(7, '2', '1000', 'sanju', '215436769', 'Credit', '8', '2024', '666', '2026-07-23'),
(8, '2', '2000', 'sanju', '258097613', 'Credit', '10', '2024', '555', '2026-07-23');

-- --------------------------------------------------------

--
-- Table structure for table `register`
--

CREATE TABLE IF NOT EXISTS `register` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `vehicle_no` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `phone` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `register`
--

INSERT INTO `register` (`id`, `name`, `email`, `vehicle_no`, `password`, `phone`) VALUES
(2, 'faizi', 'fz@gmail.com', 'KL-72-B-3726', '777', '7560855091');

-- --------------------------------------------------------

--
-- Table structure for table `toll`
--

CREATE TABLE IF NOT EXISTS `toll` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `gate_name` varchar(100) NOT NULL,
  `latitude` varchar(100) NOT NULL,
  `longitude` varchar(100) NOT NULL,
  `amount` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `toll`
--

INSERT INTO `toll` (`id`, `gate_name`, `latitude`, `longitude`, `amount`) VALUES
(1, 'NHAI Ponnarimangalam toll plaza', '10.0104702', '76.2580047', '100'),
(2, 'Varappuzha Toll Junction', '10.078354', '76.275425', '110'),
(3, 'Varapuzha Bridge Toll Plaza', '10.0776261', '76.2760784', '90'),
(4, 'palarivattom toll', '10.0063373', '76.3048456', '50'),
(5, 'Union Christian College, Aluva ', '10.1260271', '76.3339964', '120');

-- --------------------------------------------------------

--
-- Table structure for table `user_toll`
--

CREATE TABLE IF NOT EXISTS `user_toll` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `tid` int(11) NOT NULL,
  `toll_booth` varchar(100) NOT NULL,
  `amount` varchar(100) NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `status` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `user_toll`
--

INSERT INTO `user_toll` (`id`, `uid`, `tid`, `toll_booth`, `amount`, `date`, `time`, `status`) VALUES
(2, 2, 4, 'palarivattom toll', '50', '2023-07-25', '14:23:55', 'pending'),
(3, 0, 4, 'palarivattom toll', '50', '2023-07-26', '12:54:33', 'pending'),
(4, 0, 4, 'palarivattom toll', '50', '2023-07-26', '12:56:18', 'pending'),
(5, 0, 4, 'palarivattom toll', '50', '2023-07-26', '12:58:16', 'pending'),
(6, 0, 4, 'palarivattom toll', '50', '2023-07-26', '12:59:18', 'pending'),
(7, 0, 4, 'palarivattom toll', '50', '2023-07-26', '13:05:14', 'pending'),
(8, 0, 4, 'palarivattom toll', '50', '2023-07-26', '13:08:12', 'pending');

-- --------------------------------------------------------

--
-- Table structure for table `wallet_tbl`
--

CREATE TABLE IF NOT EXISTS `wallet_tbl` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) NOT NULL,
  `amount` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `wallet_tbl`
--

INSERT INTO `wallet_tbl` (`id`, `user_id`, `amount`) VALUES
(2, '1', '2900'),
(3, '2', '2000');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
