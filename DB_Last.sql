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
  `setCount` varchar(10) DEFAULT '세트 없음',
  PRIMARY KEY (`listId`),
  UNIQUE KEY `recordId` (`recordId`,`exerciseId`),
  KEY `exerciseId` (`exerciseId`),
  CONSTRAINT `exercise_list_ibfk_1` FOREIGN KEY (`exerciseId`) REFERENCES `exercise_type` (`exerciseId`),
  CONSTRAINT `exercise_list_ibfk_2` FOREIGN KEY (`recordId`) REFERENCES `exercise_record` (`recordId`)
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_list`
--

LOCK TABLES `exercise_list` WRITE;
/*!40000 ALTER TABLE `exercise_list` DISABLE KEYS */;
INSERT INTO `exercise_list` VALUES (73,31,7,'15분'),(74,35,7,'10분'),(75,7,7,'3세트'),(76,13,7,'2세트'),(77,25,7,'3세트'),(78,33,7,'10분'),(79,3,7,'세트 없음'),(106,1,8,'4분'),(109,3,8,'3세트'),(110,5,8,'2세트'),(114,2,1,'2세트'),(115,33,1,'30분'),(116,21,1,'2세트'),(117,22,1,'5세트'),(118,13,1,'2세트'),(119,6,1,'3세트');
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
  `recordDate` date DEFAULT (curdate()),
  PRIMARY KEY (`recordId`),
  KEY `userId` (`userId`),
  CONSTRAINT `exercise_record_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_record`
--

LOCK TABLES `exercise_record` WRITE;
/*!40000 ALTER TABLE `exercise_record` DISABLE KEYS */;
INSERT INTO `exercise_record` VALUES (1,'test1','2025-06-08'),(7,'ion0203','2025-06-09'),(8,'test1','2025-06-09');
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
  `koreanName` varchar(30) NOT NULL,
  PRIMARY KEY (`exerciseId`),
  CONSTRAINT `exercise_type_chk_1` CHECK ((char_length(`exerciseName`) between 2 and 30))
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_type`
--

LOCK TABLES `exercise_type` WRITE;
/*!40000 ALTER TABLE `exercise_type` DISABLE KEYS */;
INSERT INTO `exercise_type` VALUES (1,'Leg','barbellsquat','바벨 스쿼트'),(2,'Leg','lunge','런지'),(3,'Leg','legcurl','레그 컬'),(4,'Leg','legpress','레그 프레스'),(5,'Leg','hacksquat','핵 스쿼트'),(6,'Leg','deadlift','데드리프트'),(7,'Back','deadlift','데드리프트'),(8,'Back','tbarrow','티 바 로우'),(9,'Back','latpulldown','랫 풀다운'),(10,'Back','onearmdumbbellrow','원암 덤벨 로우'),(11,'Back','armpulldown','암 풀다운'),(12,'Back','lowrow','로우 로우'),(13,'Chest','benchpress','벤치 프레스'),(14,'Chest','fly','플라이'),(15,'Chest','dips','딥스'),(16,'Chest','pushup','푸시업'),(17,'Chest','chestpress','체스트 프레스'),(18,'Chest','crossover','크로스오버'),(19,'Arm','dumbbellcurl','덤벨 컬'),(20,'Arm','dips','딥스'),(21,'Arm','pushdown','푸시다운'),(22,'Arm','barbellcurl','바벨 컬'),(23,'Arm','hammercurl','해머 컬'),(24,'Arm','iducurl','이두 컬'),(25,'Shoulder','overheadpress','오버헤드 프레스'),(26,'Shoulder','sidelateralrelease','사이드 레터-릴리즈'),(27,'Shoulder','backfly','백 플라이'),(28,'Shoulder','uprightrow','업라이트 로우'),(29,'Shoulder','facepull','페이스 풀'),(30,'Shoulder','shoulderpress','숄더 프레스'),(31,'Cardio','running','러닝'),(32,'Cardio','jumpingjack','점핑잭'),(33,'Cardio','walking','걷기'),(34,'Cardio','cycle','사이클'),(35,'Cardio','stepmill','스텝밀'),(36,'Cardio','stepper','스테퍼');
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
INSERT INTO `user` VALUES ('fasdf','sssssssss','푸푸푸',58,'male',58,58),('gggggg','jjjjjjj','hhhhh',88,'male',88,88),('ion0203','1234567','이승호',24,'male',171,72),('jaeho123','jaeho123','정재호',24,'male',175,80),('test1','12345','test',13,'male',160,50),('test2','12345','test2',6,'female',110,34);
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

-- Dump completed on 2025-06-09 23:15:11
