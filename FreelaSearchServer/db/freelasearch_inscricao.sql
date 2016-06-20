-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: freelasearch.cxvasrhmvvwl.sa-east-1.rds.amazonaws.com    Database: freelasearch
-- ------------------------------------------------------
-- Server version	5.6.27-log

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
-- Table structure for table `inscricao`
--

DROP TABLE IF EXISTS `inscricao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inscricao` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador da inscrição. Valor sequência',
  `anuncio_id` int(11) NOT NULL COMMENT 'Identificador do anúncio dono do anúncio com a inscrição',
  `freelancer_id` int(11) NOT NULL COMMENT 'Identificador do freelancer inscrito',
  `data_inscricao` date NOT NULL COMMENT 'Data que ocorreu a inscrição',
  `status_inscricao` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'Situação da inscrição.\n0: Em análise / Pendente\n1: Aceita\n2: Finalizada\n3: Cancelada / Recusada',
  PRIMARY KEY (`id`),
  KEY `fk_inscricao_anuncio_idx` (`anuncio_id`),
  KEY `fk_inscricao_freelancer_idx` (`freelancer_id`),
  CONSTRAINT `fk_inscricao_anuncio` FOREIGN KEY (`anuncio_id`) REFERENCES `anuncio` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_inscricao_freelancer` FOREIGN KEY (`freelancer_id`) REFERENCES `freelancer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inscricao`
--

LOCK TABLES `inscricao` WRITE;
/*!40000 ALTER TABLE `inscricao` DISABLE KEYS */;
INSERT INTO `inscricao` VALUES (1,1,2,'2016-02-19',1),(2,1,5,'2016-02-20',2),(3,17,2,'2016-02-20',0),(4,21,2,'2016-02-20',0),(5,33,1,'2016-06-18',0),(6,33,1,'2016-06-18',0),(7,33,1,'2016-06-18',0),(8,31,1,'2016-06-18',0),(9,21,1,'2016-06-18',0),(10,18,1,'2016-06-18',0),(11,34,13,'2016-06-18',3),(12,34,13,'2016-06-18',3),(13,27,1,'2016-06-19',0),(14,35,2,'2016-06-19',3),(15,3,1,'2016-06-19',0),(16,32,1,'2016-06-20',0);
/*!40000 ALTER TABLE `inscricao` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-20  2:03:23
