-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: minorinsights
-- ------------------------------------------------------
-- Server version	8.0.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `daily_custom_schedule`
--

DROP TABLE IF EXISTS `daily_custom_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `daily_custom_schedule` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `frequency` int DEFAULT NULL,
  `frequency_counter` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  `time` int DEFAULT NULL,
  `timezone` varchar(255) DEFAULT NULL,
  `topic` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `daily_custom_schedule`
--

LOCK TABLES `daily_custom_schedule` WRITE;
/*!40000 ALTER TABLE `daily_custom_schedule` DISABLE KEYS */;
INSERT INTO `daily_custom_schedule` VALUES (1,1,1,1,22,'mst',3,1),(3,1,1,1,12,'mst',3,3),(4,1,1,1,12,'mst',3,4);
/*!40000 ALTER TABLE `daily_custom_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `default_schedule`
--

DROP TABLE IF EXISTS `default_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `default_schedule` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `status` int DEFAULT NULL,
  `timezone` varchar(255) DEFAULT NULL,
  `topic` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `default_schedule`
--

LOCK TABLES `default_schedule` WRITE;
/*!40000 ALTER TABLE `default_schedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `default_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (4);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monthly_custom_schedule`
--

DROP TABLE IF EXISTS `monthly_custom_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `monthly_custom_schedule` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `day` int DEFAULT NULL,
  `frequency` int DEFAULT NULL,
  `frequency_counter` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  `time` int DEFAULT NULL,
  `timezone` varchar(255) DEFAULT NULL,
  `topic` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monthly_custom_schedule`
--

LOCK TABLES `monthly_custom_schedule` WRITE;
/*!40000 ALTER TABLE `monthly_custom_schedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `monthly_custom_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `new_week_or_month`
--

DROP TABLE IF EXISTS `new_week_or_month`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `new_week_or_month` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `current` int DEFAULT NULL,
  `last` int DEFAULT NULL,
  `name` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9gdm630uuuth6edmk1p5xj127` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `new_week_or_month`
--

LOCK TABLES `new_week_or_month` WRITE;
/*!40000 ALTER TABLE `new_week_or_month` DISABLE KEYS */;
INSERT INTO `new_week_or_month` VALUES (1,11,10,0),(2,3,2,1);
/*!40000 ALTER TABLE `new_week_or_month` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ready_schedule`
--

DROP TABLE IF EXISTS `ready_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ready_schedule` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_processed` datetime DEFAULT NULL,
  `date_sent` datetime DEFAULT NULL,
  `schedule_type` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  `time` int DEFAULT NULL,
  `timezone` varchar(255) DEFAULT NULL,
  `topic` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ready_schedule`
--

LOCK TABLES `ready_schedule` WRITE;
/*!40000 ALTER TABLE `ready_schedule` DISABLE KEYS */;
INSERT INTO `ready_schedule` VALUES (1,'2023-03-16 22:39:00',NULL,1,0,22,NULL,3,1),(2,'2023-03-16 22:39:00',NULL,1,0,22,NULL,3,3),(3,'2023-03-16 22:39:00',NULL,1,0,22,NULL,3,2),(4,'2023-03-16 22:54:00',NULL,1,0,22,NULL,3,3),(5,'2023-03-16 22:54:00',NULL,1,0,22,NULL,3,1),(6,'2023-03-16 22:54:00',NULL,1,0,22,NULL,3,2),(7,'2023-03-16 23:00:00',NULL,1,0,23,NULL,3,1),(8,'2023-03-16 23:00:00',NULL,1,0,23,NULL,3,3),(9,'2023-03-16 23:00:00',NULL,1,0,23,NULL,3,2),(10,'2023-03-16 23:45:00',NULL,1,0,23,NULL,3,2),(11,'2023-03-16 23:45:00',NULL,1,0,23,NULL,3,3),(12,'2023-03-16 23:45:00',NULL,1,0,23,NULL,3,1),(13,'2023-03-17 22:43:00',NULL,1,0,22,NULL,3,3),(14,'2023-03-17 22:43:00',NULL,1,0,22,NULL,3,2),(15,'2023-03-17 22:43:00',NULL,1,0,22,NULL,3,1),(16,'2023-03-18 08:00:00',NULL,1,0,8,NULL,3,3),(17,'2023-03-18 08:02:00',NULL,1,0,8,NULL,3,3),(18,'2023-03-18 08:04:00',NULL,1,0,8,NULL,3,3),(19,'2023-03-18 08:06:00',NULL,1,0,8,NULL,3,3),(20,'2023-03-18 08:08:00',NULL,1,0,8,NULL,3,3),(21,'2023-03-18 08:10:00',NULL,1,0,8,NULL,3,3),(22,'2023-03-18 08:12:00',NULL,1,0,8,NULL,3,3),(23,'2023-03-18 08:14:00',NULL,1,0,8,NULL,3,3),(24,'2023-03-18 08:16:00',NULL,1,0,8,NULL,3,3),(25,'2023-03-18 08:18:00',NULL,1,0,8,NULL,3,3),(26,'2023-03-18 08:20:00',NULL,1,0,8,NULL,3,3),(27,'2023-03-18 08:22:00',NULL,1,0,8,NULL,3,3),(28,'2023-03-18 08:25:00',NULL,1,0,8,NULL,3,3),(29,'2023-03-18 08:42:50',NULL,1,0,8,NULL,3,3),(30,'2023-03-18 08:52:00',NULL,1,0,8,NULL,3,3),(31,'2023-03-18 09:12:00',NULL,1,0,9,NULL,3,3),(32,'2023-03-18 09:16:00',NULL,1,0,9,NULL,3,3),(33,'2023-03-18 09:18:00',NULL,1,0,9,NULL,3,3),(34,'2023-03-18 09:36:00',NULL,1,0,9,NULL,3,3),(35,'2023-03-18 12:00:00',NULL,1,0,12,NULL,3,4),(36,'2023-03-18 12:00:00',NULL,1,0,12,NULL,3,3),(37,'2023-03-18 12:18:00',NULL,1,0,12,NULL,3,3),(38,'2023-03-18 12:18:00',NULL,1,0,12,NULL,3,4),(39,'2023-03-18 12:36:00',NULL,1,0,12,NULL,3,4),(40,'2023-03-18 12:36:00',NULL,1,0,12,NULL,3,3),(41,'2023-03-18 12:54:00',NULL,1,0,12,NULL,3,4),(42,'2023-03-18 12:54:00',NULL,1,0,12,NULL,3,3),(43,'2023-03-18 22:00:00',NULL,1,0,22,NULL,3,1),(44,'2023-03-18 22:00:00',NULL,1,0,22,NULL,3,2),(45,'2023-03-18 22:18:00',NULL,1,0,22,NULL,3,1),(46,'2023-03-18 22:18:00',NULL,1,0,22,NULL,3,2),(47,'2023-03-18 22:36:00',NULL,1,0,22,NULL,3,2),(48,'2023-03-18 22:36:00',NULL,1,0,22,NULL,3,1),(49,'2023-03-18 22:54:00',NULL,1,0,22,NULL,3,1),(50,'2023-03-18 22:54:00',NULL,1,0,22,NULL,3,2),(51,'2023-03-19 12:00:00',NULL,1,0,12,NULL,3,3),(52,'2023-03-19 12:00:00',NULL,1,0,12,NULL,3,4),(53,'2023-03-19 12:18:00',NULL,1,0,12,NULL,3,3),(54,'2023-03-19 12:18:00',NULL,1,0,12,NULL,3,4),(55,'2023-03-19 12:36:00',NULL,1,0,12,NULL,3,3),(56,'2023-03-19 12:36:00',NULL,1,0,12,NULL,3,4),(57,'2023-03-19 12:54:00',NULL,1,0,12,NULL,3,4),(58,'2023-03-19 12:54:00',NULL,1,0,12,NULL,3,3);
/*!40000 ALTER TABLE `ready_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sent_mail`
--

DROP TABLE IF EXISTS `sent_mail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sent_mail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_sent` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `offset` varchar(255) DEFAULT NULL,
  `ready_schedule_id` bigint DEFAULT NULL,
  `status` int DEFAULT NULL,
  `topic` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sent_mail`
--

LOCK TABLES `sent_mail` WRITE;
/*!40000 ALTER TABLE `sent_mail` DISABLE KEYS */;
INSERT INTO `sent_mail` VALUES (1,'2023-03-16 22:54:05','chibuisi.amiaka@gmail.com','1',NULL,1,'WORD'),(2,'2023-03-16 22:54:06','ckamiaka2019@gmail.com','1',NULL,1,'WORD'),(3,'2023-03-16 23:00:05','chibuisi.amiaka@gmail.com','2',NULL,1,'WORD'),(4,'2023-03-16 23:00:07','ckamiaka2019@gmail.com','2',NULL,1,'WORD'),(5,'2023-03-16 23:00:07','amiaka.chibuisi@gmail.com','2',NULL,0,'WORD'),(6,'2023-03-16 23:45:06','amiaka.chibuisi@gmail.com','1',NULL,1,'WORD'),(7,'2023-03-16 23:45:07','ckamiaka2019@gmail.com','1',NULL,1,'WORD'),(8,'2023-03-17 22:43:06','amiaka.chibuisi@gmail.com','1',NULL,1,'WORD'),(9,'2023-03-17 22:43:07','ckamiaka2019@gmail.com','1',NULL,1,'WORD'),(10,'2023-03-17 22:43:07','chibuisi.amiaka@gmail.com','1',NULL,1,'WORD'),(11,'2023-03-18 08:02:05','amiaka.chibuisi@gmail.com','1',NULL,1,'WORD'),(12,'2023-03-18 08:25:06','amiaka.chibuisi@gmail.com','1',NULL,1,'WORD'),(13,'2023-03-18 08:42:54','amiaka.chibuisi@gmail.com','1',NULL,1,'WORD'),(14,'2023-03-18 08:52:07','amiaka.chibuisi@gmail.com','1',NULL,1,'WORD'),(15,'2023-03-18 09:12:05','amiaka.chibuisi@gmail.com','1',NULL,1,'WORD'),(16,'2023-03-18 09:18:05','amiaka.chibuisi@gmail.com','1',NULL,1,'WORD'),(17,'2023-03-18 12:00:05','briana.amiaka@gmail.com','1',NULL,1,'WORD'),(18,'2023-03-18 12:00:06','amiaka.chibuisi@gmail.com','1',NULL,1,'WORD'),(19,'2023-03-18 22:00:06','ckamiaka2019@gmail.com','1',NULL,1,'WORD'),(20,'2023-03-18 22:00:07','chibuisi.amiaka@gmail.com','1',NULL,1,'WORD');
/*!40000 ALTER TABLE `sent_mail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscription`
--

DROP TABLE IF EXISTS `subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscription` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_last_renewed` datetime DEFAULT NULL,
  `date_subscribed` datetime DEFAULT NULL,
  `date_unsubscribed` datetime DEFAULT NULL,
  `date_updated` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `supported_topic` int DEFAULT NULL,
  `topic` varchar(255) DEFAULT NULL,
  `unsubscribe_reason` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscription`
--

LOCK TABLES `subscription` WRITE;
/*!40000 ALTER TABLE `subscription` DISABLE KEYS */;
INSERT INTO `subscription` VALUES (1,NULL,'2023-03-16 07:37:06',NULL,'2023-03-16 07:37:06','ckamiaka2019@gmail.com','ACTIVE',3,'WORD',NULL,1),(2,NULL,'2023-03-16 22:36:18',NULL,'2023-03-16 22:36:18','chibuisi.amiaka@gmail.com','ACTIVE',3,'WORD',NULL,2),(3,NULL,'2023-03-16 22:36:28',NULL,'2023-03-16 22:36:28','amiaka.chibuisi@gmail.com','ACTIVE',3,'WORD',NULL,3),(4,NULL,'2023-03-18 09:20:38',NULL,'2023-03-18 09:20:38','briana.amiaka@gmail.com','ACTIVE',3,'WORD',NULL,4);
/*!40000 ALTER TABLE `subscription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscription_renew_dates`
--

DROP TABLE IF EXISTS `subscription_renew_dates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscription_renew_dates` (
  `subscription_id` bigint NOT NULL,
  `renew_dates` datetime DEFAULT NULL,
  KEY `FKtkul5e3d4ly0glgfl8g9oysf2` (`subscription_id`),
  CONSTRAINT `FKtkul5e3d4ly0glgfl8g9oysf2` FOREIGN KEY (`subscription_id`) REFERENCES `subscription` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscription_renew_dates`
--

LOCK TABLES `subscription_renew_dates` WRITE;
/*!40000 ALTER TABLE `subscription_renew_dates` DISABLE KEYS */;
/*!40000 ALTER TABLE `subscription_renew_dates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topic`
--

DROP TABLE IF EXISTS `topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topic` (
  `id` int NOT NULL,
  `date_added` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_mbunn9erv8nmf5lk1r2nu0nex` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic`
--

LOCK TABLES `topic` WRITE;
/*!40000 ALTER TABLE `topic` DISABLE KEYS */;
INSERT INTO `topic` VALUES (2,'2023-03-16 07:36:04','This topic will discuss various Technologies','TECHNOLOGY'),(4,'2023-03-16 07:36:04','This topic will discuss various dictionary words','WORD'),(5,'2023-03-16 07:36:04','This topic will discuss various companies','COMPANY');
/*!40000 ALTER TABLE `topic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topic_item`
--

DROP TABLE IF EXISTS `topic_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topic_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_added` datetime DEFAULT NULL,
  `date_tag` varchar(255) DEFAULT NULL,
  `tag` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `topic_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1hy8wg2nnhoeuqdiennegewm6` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic_item`
--

LOCK TABLES `topic_item` WRITE;
/*!40000 ALTER TABLE `topic_item` DISABLE KEYS */;
INSERT INTO `topic_item` VALUES (1,'2023-03-16 07:36:38',NULL,NULL,'Peace','WORD');
/*!40000 ALTER TABLE `topic_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topic_item_key`
--

DROP TABLE IF EXISTS `topic_item_key`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topic_item_key` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `key_name` varchar(255) DEFAULT NULL,
  `topic_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic_item_key`
--

LOCK TABLES `topic_item_key` WRITE;
/*!40000 ALTER TABLE `topic_item_key` DISABLE KEYS */;
INSERT INTO `topic_item_key` VALUES (1,'','meaning',4),(2,'','title',4),(3,'','origin',4),(4,'','synonyms',4),(5,'','antonyms',4),(6,'','links',4);
/*!40000 ALTER TABLE `topic_item_key` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topic_item_properties`
--

DROP TABLE IF EXISTS `topic_item_properties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topic_item_properties` (
  `id` bigint NOT NULL,
  `property_key` varchar(255) DEFAULT NULL,
  `property_value` varchar(255) DEFAULT NULL,
  `topic_item_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1t63dmar2cfpg1v7kurqf19hy` (`topic_item_id`),
  CONSTRAINT `FK1t63dmar2cfpg1v7kurqf19hy` FOREIGN KEY (`topic_item_id`) REFERENCES `topic_item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic_item_properties`
--

LOCK TABLES `topic_item_properties` WRITE;
/*!40000 ALTER TABLE `topic_item_properties` DISABLE KEYS */;
INSERT INTO `topic_item_properties` VALUES (1,'meaning','the presence of calm and quiet when the body, soul and mind is at rest',1),(2,'origin','English',1),(3,'opposite','war',1);
/*!40000 ALTER TABLE `topic_item_properties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_joined` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `ip_address` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `timezone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'2023-03-16 07:35:43','ckamiaka2019@gmail.com','Kelvin','0:0:0:0:0:0:0:1','Amiaka','mst'),(2,'2023-03-16 22:35:29','chibuisi.amiaka@gmail.com','Chibuisi','0:0:0:0:0:0:0:1','Amiaka','mst'),(3,'2023-03-16 22:35:42','amiaka.chibuisi@gmail.com','Chibuisi','0:0:0:0:0:0:0:1','Amiaka','mst'),(4,'2023-03-18 09:20:08','briana.amiaka@gmail.com','Briana','0:0:0:0:0:0:0:1','Amiaka','mst');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_topic_item_offset`
--

DROP TABLE IF EXISTS `user_topic_item_offset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_topic_item_offset` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `topic` int DEFAULT NULL,
  `topic_item_offset` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6u2cyqsjqo1bppyv5orvkmra0` (`user_id`),
  CONSTRAINT `FK6u2cyqsjqo1bppyv5orvkmra0` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_topic_item_offset`
--

LOCK TABLES `user_topic_item_offset` WRITE;
/*!40000 ALTER TABLE `user_topic_item_offset` DISABLE KEYS */;
INSERT INTO `user_topic_item_offset` VALUES (1,3,2,1),(2,3,2,2),(3,3,2,3),(4,3,2,4);
/*!40000 ALTER TABLE `user_topic_item_offset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `weekly_custom_schedule`
--

DROP TABLE IF EXISTS `weekly_custom_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `weekly_custom_schedule` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `frequency` int DEFAULT NULL,
  `frequency_counter` int DEFAULT NULL,
  `schedule_day` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  `time` int DEFAULT NULL,
  `timezone` varchar(255) DEFAULT NULL,
  `topic` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `weekly_custom_schedule`
--

LOCK TABLES `weekly_custom_schedule` WRITE;
/*!40000 ALTER TABLE `weekly_custom_schedule` DISABLE KEYS */;
INSERT INTO `weekly_custom_schedule` VALUES (1,2,1,1,1,6,'mst',3,2);
/*!40000 ALTER TABLE `weekly_custom_schedule` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-21 14:28:06
