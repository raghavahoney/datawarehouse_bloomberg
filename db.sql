CREATE DATABASE  IF NOT EXISTS `datawarehouse_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `datawarehouse_db`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: datawarehouse_db
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accumulative_deals`
--

DROP TABLE IF EXISTS `accumulative_deals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accumulative_deals` (
  `CurrencyISOCode` varchar(128) NOT NULL DEFAULT '',
  `CountOfDeals` bigint(20) DEFAULT '0',
  `count_of_deals` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`CurrencyISOCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `invalid_deal`
--

DROP TABLE IF EXISTS `invalid_deal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invalid_deal` (
  `id` varchar(128) NOT NULL DEFAULT '',
  `from_currency_iso_code` varchar(128) DEFAULT NULL,
  `to_currency_iso_code` varchar(128) DEFAULT NULL,
  `date` varchar(128) DEFAULT NULL,
  `deal_amount` varchar(128) DEFAULT NULL,
  `file_name` varchar(256) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`,`file_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `valid_deal`
--

DROP TABLE IF EXISTS `valid_deal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `valid_deal` (
  `id` varchar(128) NOT NULL DEFAULT '',
  `from_currency_iso_code` varchar(128) DEFAULT NULL,
  `to_currency_iso_code` varchar(128) DEFAULT NULL,
  `deal_amount` varchar(128) DEFAULT NULL,
  `file_name` varchar(256) NOT NULL DEFAULT '',
  `date` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`,`file_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-20 11:25:48
