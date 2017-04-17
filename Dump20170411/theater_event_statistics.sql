-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: theater
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `event_statistics`
--

DROP TABLE IF EXISTS `event_statistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_statistics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `accessed_by_name` bigint(20) NOT NULL,
  `prices_queried` bigint(20) NOT NULL,
  `booked_tickets` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_statistics`
--

LOCK TABLES `event_statistics` WRITE;
/*!40000 ALTER TABLE `event_statistics` DISABLE KEYS */;
INSERT INTO `event_statistics` VALUES (1,1,0,0),(3,0,0,0),(5,0,0,0),(7,0,0,0),(8,0,0,0),(9,0,1,0),(10,0,1,0),(11,0,1,0),(12,0,1,0),(13,0,1,0),(14,0,1,0),(15,0,1,0),(16,4,0,0),(17,4,0,0),(18,4,0,0),(19,4,0,0),(20,0,1,0),(21,0,1,0),(22,0,1,0),(23,0,0,1),(24,0,0,1),(25,0,1,0),(26,0,0,1),(27,0,0,1),(28,4,1,0),(29,0,0,1),(30,0,0,1),(31,4,1,0),(32,0,0,1),(33,0,0,1);
/*!40000 ALTER TABLE `event_statistics` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-17 19:33:22
