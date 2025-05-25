-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: healthplanner
-- ------------------------------------------------------
-- Server version	8.4.0

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
-- Table structure for table `exercise_list`
--

DROP TABLE IF EXISTS `exercise_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercise_list` (
  `listId` int NOT NULL AUTO_INCREMENT,
  `exerciseId` int NOT NULL,
  `recordId` int NOT NULL,
  PRIMARY KEY (`listId`),
  UNIQUE KEY `exerciseId` (`exerciseId`),
  UNIQUE KEY `recordId` (`recordId`),
  CONSTRAINT `exercise_list_ibfk_1` FOREIGN KEY (`exerciseId`) REFERENCES `exercise_type` (`exerciseId`),
  CONSTRAINT `exercise_list_ibfk_2` FOREIGN KEY (`recordId`) REFERENCES `exercise_record` (`recordId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_list`
--

LOCK TABLES `exercise_list` WRITE;
/*!40000 ALTER TABLE `exercise_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `exercise_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise_record`
--

DROP TABLE IF EXISTS `exercise_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercise_record` (
  `recordId` int NOT NULL AUTO_INCREMENT,
  `userId` varchar(20) NOT NULL,
  `recordDate` date NOT NULL,
  PRIMARY KEY (`recordId`),
  KEY `userId` (`userId`),
  CONSTRAINT `exercise_record_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_record`
--

LOCK TABLES `exercise_record` WRITE;
/*!40000 ALTER TABLE `exercise_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `exercise_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise_type`
--

DROP TABLE IF EXISTS `exercise_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercise_type` (
  `exerciseId` int NOT NULL AUTO_INCREMENT,
  `exercisePart` varchar(30) NOT NULL,
  `exerciseName` varchar(30) NOT NULL,
  PRIMARY KEY (`exerciseId`),
  CONSTRAINT `exercise_type_chk_1` CHECK ((char_length(`exerciseName`) between 2 and 30))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_type`
--

LOCK TABLES `exercise_type` WRITE;
/*!40000 ALTER TABLE `exercise_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `exercise_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userId` varchar(20) NOT NULL,
  `userPassword` varchar(20) NOT NULL,
  `userName` varchar(5) NOT NULL,
  `userAge` int NOT NULL,
  `userGender` varchar(6) NOT NULL,
  `userHeight` int NOT NULL,
  `userWeight` int NOT NULL,
  PRIMARY KEY (`userId`),
  CONSTRAINT `user_chk_1` CHECK ((char_length(`userId`) between 5 and 20)),
  CONSTRAINT `user_chk_2` CHECK ((char_length(`userPassword`) between 5 and 20)),
  CONSTRAINT `user_chk_3` CHECK ((char_length(`userName`) between 2 and 5)),
  CONSTRAINT `user_chk_4` CHECK ((`userAge` between 0 and 150)),
  CONSTRAINT `user_chk_5` CHECK ((`userGender` in (_utf8mb4'male',_utf8mb4'female'))),
  CONSTRAINT `user_chk_6` CHECK ((`userHeight` between 50 and 300)),
  CONSTRAINT `user_chk_7` CHECK ((`userWeight` between 10 and 500))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('fasdf','sssssssss','푸푸푸',58,'male',58,58),('gggggg','jjjjjjj','hhhhh',88,'male',88,88),('ion0203','1234567','이승호',24,'male',171,72),('jaeho123','jaeho123','정재호',24,'male',175,80);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-26  1:40:06
