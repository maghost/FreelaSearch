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
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador do usuário. Valor sequência',
  `nome` varchar(150) NOT NULL COMMENT 'Nome completo do usuário',
  `email` varchar(254) NOT NULL COMMENT 'Email que será utilizado para conectar-se no sistema e para receber notificações',
  `senha` varchar(32) DEFAULT NULL COMMENT 'Senha criptografada em MD5. Caso esteja nula, o usuário criou sua conta via Facebook e não cadastrou uma senha no sistema',
  `foto` varchar(254) DEFAULT NULL COMMENT 'Foto de perfil do usuário. Se logado pelo Facebook, pegará a da rede social',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Matheus Marques','matheus_maghost@hotmail.com','45339359513f09155110f63f7ca91c3e','https://graph.facebook.com/1151752831502421/picture?type=large'),(2,'Renato Marquez','renato@gmail.com','ec02d2d95c27675d87dca50018d89192',NULL),(3,'Lucas Kauz','lucaskauz@gmail.com','dc53fc4f621c80bdc2fa0329a6123708','https://graph.facebook.com/100000726568474/picture?type=large'),(4,'Jonathan Cassimiro','jhow@outlook.com','b43e19bcdf3fe7dadaaeb7e6996d430e',NULL),(5,'Ester Mariano','ester@hotmail.com','fa258e7e7a0d43c4266a01029dc2bdfa','https://graph.facebook.com/100003183947740/picture?type=large'),(6,'Wesler de Andrade de Souza','wesler@dark.com','c62804f9a679c3ae5d717380cfd5be19',NULL),(7,'Heloisa Alicia Melissa','heloisa@gmail.com','29691813de923aa290cde8bd2ba88767',NULL),(8,'João da Silva','joaodasilva@gmail.com','dccd96c256bc7dd39bae41a405f25e43','100010489185209'),(9,'Maria Diniz','mamamaria@diniz.com','263bce650e68ab4e23f28263760b9fa5',''),(10,'Vinicius Campanhã','vica9@gmail.com','7fa81ff5e6a88a34ca2392240268c68f','https://graph.facebook.com/100010489185209/picture?type=large'),(11,'Paulo Cezar Gonçalves','pcgoncalves@hotmail.com','bc54f4d60f1cec0f9a6cb70e13f2127a','https://graph.facebook.com/100000421129723/picture?type=large'),(12,'Fernando Fernandes','fernando@email.com','cebdd715d4ecaafee8f147c2e85e0754','https://graph.facebook.com/100000411452061/picture?type=large'),(13,'Lorem Consultoria','lorem@ipsum.dolor','d2e16e6ef52a45b7468f1da56bba1953','http://stephboreldesign.com/wp-content/uploads/2012/03/lorem-ipsum-logo.jpg'),(14,'Belina Aparecida Marques','bela@email.com','cdd7a616f977556bf3bce39917a37a30',NULL),(15,'Teste','teste@teste.com','698dc19d489c4e4db73e28a713eab07b',NULL),(16,'Matheus Teste','matheus@teste.com','45339359513f09155110f63f7ca91c3e',NULL),(17,'Anahí','anahi@email.com','599b7e82a3c5fb04f06f81a5016c4cad',NULL),(18,'Anahí 2','anahi2@email.com','7aa488a67f1e62f962dd8d2c62d96afb',NULL),(19,'Anahí 3','anahi3@email.com','95d65d7ff4a4f0ab6b1e9636e2233537',NULL),(20,'Anahí 4','anahi4@email.com','3d44bc3ed61ba35f0083029ea2020c30',NULL),(21,'Anahí 5','anahi5@email.com','anahi5',NULL);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
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
