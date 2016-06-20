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
-- Table structure for table `contratacao`
--

DROP TABLE IF EXISTS `contratacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contratacao` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador da contratação. Valor sequência',
  `data` datetime NOT NULL COMMENT 'Data à qual o Anunciante aceitou a inscrição do Freelancer',
  `anuncio_id` int(11) NOT NULL COMMENT 'Identificador do Anúncio',
  `freelancer_id` int(11) DEFAULT NULL COMMENT 'Identificador do Freelancer que recebeu sua inscrição como aceita',
  `avaliacao_freelancer_id` int(11) DEFAULT NULL COMMENT 'Identificador da Avaliação feita pelo Freelancer',
  `avaliacao_anunciante_id` int(11) DEFAULT NULL COMMENT 'Identificador da Avaliação feita pelo Anunciante',
  PRIMARY KEY (`id`),
  KEY `fk_contratacao_anuncio_idx` (`anuncio_id`),
  KEY `fk_contratacao_freelancer_idx` (`freelancer_id`),
  KEY `fk_contratacao_avaliacao_freelancer_idx` (`avaliacao_freelancer_id`),
  KEY `fk_contratacao_avaliacao_anunciante_idx` (`avaliacao_anunciante_id`),
  CONSTRAINT `fk_contratacao_anuncio` FOREIGN KEY (`anuncio_id`) REFERENCES `anuncio` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_contratacao_avaliacao_anunciante` FOREIGN KEY (`avaliacao_anunciante_id`) REFERENCES `avaliacao` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_contratacao_avaliacao_freelancer` FOREIGN KEY (`avaliacao_freelancer_id`) REFERENCES `avaliacao` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_contratacao_freelancer` FOREIGN KEY (`freelancer_id`) REFERENCES `freelancer` (`id`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contratacao`
--

LOCK TABLES `contratacao` WRITE;
/*!40000 ALTER TABLE `contratacao` DISABLE KEYS */;
INSERT INTO `contratacao` VALUES (1,'2016-02-19 00:00:00',1,1,1,1),(6,'2016-06-19 02:23:21',34,13,NULL,NULL),(10,'2016-06-19 03:33:25',35,2,NULL,NULL);
/*!40000 ALTER TABLE `contratacao` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-20  2:03:24
