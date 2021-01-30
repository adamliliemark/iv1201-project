-- MySQL dump 10.17  Distrib 10.3.24-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: recruitmentdb_old_temp
-- ------------------------------------------------------
-- Server version	10.3.24-MariaDB-2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `authorities`
--

LOCK TABLES `authorities` WRITE;
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
INSERT INTO `authorities` (`authority`, `user_email`) VALUES ('ROLE_ADMIN','per@strand.kth.se');
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `availability`
--

LOCK TABLES `availability` WRITE;
/*!40000 ALTER TABLE `availability` DISABLE KEYS */;
INSERT INTO `availability` (`user_email`, `from_date`, `to_date`) VALUES ('per@strand.kth.se','2014-02-23','2014-05-25'),('per@strand.kth.se','2014-07-10','2014-08-10');
/*!40000 ALTER TABLE `availability` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `competence_profile`
--

LOCK TABLES `competence_profile` WRITE;
/*!40000 ALTER TABLE `competence_profile` DISABLE KEYS */;
INSERT INTO `competence_profile` (`user_email`, `competence_id`, `years_of_experience`) VALUES ('per@strand.kth.se',1,3.50),('per@strand.kth.se',2,2.00);
/*!40000 ALTER TABLE `competence_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `competence_translation`
--

LOCK TABLES `competence_translation` WRITE;
/*!40000 ALTER TABLE `competence_translation` DISABLE KEYS */;
INSERT INTO `competence_translation` (`competence_id`, `language_language_code`, `text`) VALUES (1,'sv_SE','Korvgrillning'),(2,'sv_SE','Karuselldrift');
/*!40000 ALTER TABLE `competence_translation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `language`
--

LOCK TABLES `language` WRITE;
/*!40000 ALTER TABLE `language` DISABLE KEYS */;
INSERT INTO `language` (`language_code`, `native_name`) VALUES ('en_US','english'),('sv_SE','svenska');
/*!40000 ALTER TABLE `language` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `unmigrated_availability`
--

LOCK TABLES `unmigrated_availability` WRITE;
/*!40000 ALTER TABLE `unmigrated_availability` DISABLE KEYS */;
/*!40000 ALTER TABLE `unmigrated_availability` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `unmigrated_competence_profile`
--

LOCK TABLES `unmigrated_competence_profile` WRITE;
/*!40000 ALTER TABLE `unmigrated_competence_profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `unmigrated_competence_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `unmigrated_person`
--

LOCK TABLES `unmigrated_person` WRITE;
/*!40000 ALTER TABLE `unmigrated_person` DISABLE KEYS */;
INSERT INTO `unmigrated_person` (`person_id`, `name`, `surname`, `ssn`, `email`, `password`, `role_id`, `username`) VALUES (1,'Greta','Borg',NULL,NULL,'wl9nk23a',1,'borg');
/*!40000 ALTER TABLE `unmigrated_person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`first_name`, `last_name`, `ssn`, `email`, `password`) VALUES ('Per','Strand','19671212-1211','per@strand.kth.se',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-30 12:43:28
