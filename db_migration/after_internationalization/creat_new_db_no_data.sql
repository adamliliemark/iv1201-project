drop schema if exists recruitmentdb;
create schema recruitmentdb;
use recruitmentdb;

CREATE DATABASE  IF NOT EXISTS `recruitmentdb` 
/*!40100 DEFAULT CHARACTER SET utf8mb4 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `recruitmentdb`;
-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: recruitmentdb
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS `authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authorities` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `authority` varchar(255) NOT NULL,
  `user_email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKltl1e0mgxnuxv78eswphc7qvn` (`user_email`,`authority`),
  CONSTRAINT `FKkwejet1qepg6ldux6ns5ti8lg` FOREIGN KEY (`user_email`) REFERENCES `users` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `availability`
--

DROP TABLE IF EXISTS `availability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `availability` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `from_date` date NOT NULL,
  `to_date` date NOT NULL,
  `user_email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK5q40gnjonhjrg0605bqg9domf` (`from_date`,`to_date`,`user_email`),
  KEY `FKme040xvsxikuykxdhliy12k4h` (`user_email`),
  CONSTRAINT `FKme040xvsxikuykxdhliy12k4h` FOREIGN KEY (`user_email`) REFERENCES `users` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `competence`
--

DROP TABLE IF EXISTS `competence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `competence` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `competence_profile`
--

DROP TABLE IF EXISTS `competence_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `competence_profile` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `years_of_experience` double DEFAULT NULL,
  `competence_id` bigint DEFAULT NULL,
  `user_email` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs2bcpk8humnrh416e4o7gyqg1` (`competence_id`),
  KEY `FK5u1u01dq9jcpywuvtyythvjbt` (`user_email`),
  CONSTRAINT `FK5u1u01dq9jcpywuvtyythvjbt` FOREIGN KEY (`user_email`) REFERENCES `users` (`email`),
  CONSTRAINT `FKs2bcpk8humnrh416e4o7gyqg1` FOREIGN KEY (`competence_id`) REFERENCES `competence` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `competence_translation`
--

DROP TABLE IF EXISTS `competence_translation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `competence_translation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `text` varchar(255) DEFAULT NULL,
  `competence_id` bigint DEFAULT NULL,
  `language_language_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3gukvetioeanbv0jvpeej3mx2` (`competence_id`),
  KEY `FKccda0nnakys6a6difylemtawb` (`language_language_code`),
  CONSTRAINT `FK3gukvetioeanbv0jvpeej3mx2` FOREIGN KEY (`competence_id`) REFERENCES `competence` (`id`),
  CONSTRAINT `FKccda0nnakys6a6difylemtawb` FOREIGN KEY (`language_language_code`) REFERENCES `language` (`language_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `language`
--

DROP TABLE IF EXISTS `language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `language` (
  `language_code` varchar(255) NOT NULL,
  `native_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`language_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `unmigrated_availability`
--

DROP TABLE IF EXISTS `unmigrated_availability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unmigrated_availability` (
  `availability_id` bigint NOT NULL,
  `from_date` date NOT NULL,
  `person_id` bigint DEFAULT NULL,
  `to_date` date NOT NULL,
  PRIMARY KEY (`availability_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `unmigrated_competence_profile`
--

DROP TABLE IF EXISTS `unmigrated_competence_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unmigrated_competence_profile` (
  `competence_profile_id` bigint NOT NULL,
  `competence_name` varchar(255) DEFAULT NULL,
  `person_id` bigint DEFAULT NULL,
  `years_of_experience` double DEFAULT NULL,
  PRIMARY KEY (`competence_profile_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `unmigrated_person`
--

DROP TABLE IF EXISTS `unmigrated_person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unmigrated_person` (
  `person_id` bigint NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  `ssn` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `ssn` varchar(255) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-04 20:24:14
