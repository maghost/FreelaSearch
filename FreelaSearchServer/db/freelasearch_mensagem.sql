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
-- Table structure for table `mensagem`
--

DROP TABLE IF EXISTS `mensagem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mensagem` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador da mensagem. Valor sequência',
  `conteudo` varchar(500) NOT NULL COMMENT 'Texto digitado pelo usuário',
  `data_envio` datetime NOT NULL COMMENT 'Data de envio da mensagem',
  `status` tinyint(4) DEFAULT NULL COMMENT 'Situação da mensagem:\nnull - Não visualizada\n1 - Visualizada',
  `usuario_id_enviado_por` int(11) DEFAULT NULL COMMENT 'Identificador do usuário que enviou a mensagem',
  `usuario_id_recebido_por` int(11) NOT NULL COMMENT 'Identificador do usuário que recebeu a mensagem',
  PRIMARY KEY (`id`),
  KEY `fk_mensagem_usuario_idx` (`usuario_id_enviado_por`),
  KEY `fk_mensagem_usuario1_idx` (`usuario_id_recebido_por`),
  CONSTRAINT `fk_mensagem_usuario` FOREIGN KEY (`usuario_id_enviado_por`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_mensagem_usuario1` FOREIGN KEY (`usuario_id_recebido_por`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mensagem`
--

LOCK TABLES `mensagem` WRITE;
/*!40000 ALTER TABLE `mensagem` DISABLE KEYS */;
INSERT INTO `mensagem` VALUES (1,'Oi Matheus Anunciante','2016-02-19 00:00:00',NULL,3,1),(2,'Olá Lucas Freelancer','2016-02-19 01:01:01',NULL,1,3),(5,'Parabéns! Sua inscrição para o Anúncio \"testando\" foi aceita. Acesse \"Minhas Contratações\" para mais detalhes.','2016-06-18 21:37:19',NULL,NULL,6),(6,'Parabéns! Sua inscrição para o Anúncio \"Abcd\" foi aceita. Acesse \"Minhas Contratações\" para mais detalhes.','2016-06-19 03:33:25',NULL,NULL,3),(7,'Teste','2016-06-19 11:22:00',NULL,1,2),(8,'Teste 2','2016-06-19 13:01:21',NULL,2,1),(9,'Teste 3','2016-06-19 13:01:25',NULL,1,5),(10,'Teste 4','2016-06-19 16:59:25',NULL,1,2),(11,'Teste 5','2016-06-19 23:48:42',NULL,3,1),(12,'Teste de uma mensagem nova','2016-06-19 23:49:02',NULL,1,3),(13,'Resposta seguida de mensagem','2016-06-19 23:49:10',NULL,1,3),(14,'Oi, isso é um teste?','2016-06-19 23:49:23',NULL,3,1),(15,'Sim. Tudo bem?','2016-06-19 23:49:49',NULL,1,3),(16,'Pode testar sim... já é quase meia-noite','2016-06-19 23:49:55',NULL,3,1),(17,'Verdade, já deveria estar dormindo...','2016-06-19 23:55:00',NULL,1,3),(18,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse a orci ac orci eleifend eleifend vel vel risus. Morbi tempus purus quis massa imperdiet, eu luctus odio ultrices. Nulla consectetur sem tincidunt convallis maximus. Maecenas ut molestie lacus, quis euismod ante. Fusce sit amet dapibus justo. Duis dolor diam, finibus quis ex vehicula, elementum hendrerit ante. Vivamus sed fermentum leo. In tincidunt eget dolor ac fringilla. Nulla iaculis enim eu lacus lobortis lobortis. Nulla vi','2016-06-20 00:00:01',NULL,3,1);
/*!40000 ALTER TABLE `mensagem` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-20  2:03:27
