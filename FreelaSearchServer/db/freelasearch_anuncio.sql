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
-- Table structure for table `anuncio`
--

DROP TABLE IF EXISTS `anuncio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `anuncio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `anunciante_id` int(11) NOT NULL COMMENT 'Identificador do Anunciante que criou o Anúncio',
  `categoria_id` int(11) DEFAULT NULL COMMENT 'Categoria a qual o anúncio está associado',
  `titulo` varchar(150) NOT NULL COMMENT 'Título do Anúncio',
  `descricao` varchar(1000) DEFAULT NULL COMMENT 'Descrição e informações gerais do Anúncio',
  `data` datetime NOT NULL COMMENT 'Data de criação do anúncio',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'Situação do anúncio.\n0: Ativo (padrão)\n1: Inativo\n2: Finalizado',
  `cidade` varchar(55) NOT NULL COMMENT 'Nome da Cidade à qual o anúncio está atrelado',
  `estado` varchar(2) NOT NULL COMMENT 'UF do Estado à qual o anúncio está atrelado',
  PRIMARY KEY (`id`),
  KEY `fk_anuncio_categoria_idx` (`categoria_id`),
  KEY `fk_anuncio_anunciante_idx` (`anunciante_id`),
  CONSTRAINT `fk_anuncio_anunciante` FOREIGN KEY (`anunciante_id`) REFERENCES `anunciante` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_anuncio_categoria` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anuncio`
--

LOCK TABLES `anuncio` WRITE;
/*!40000 ALTER TABLE `anuncio` DISABLE KEYS */;
INSERT INTO `anuncio` VALUES (1,1,3,'[DESENVOLVIMENTO] JAVA APLICAÇÃO','Preciso de alguém que desenvolva uma aplicação que faça a soma de 1 + 1, e que o resultado seja 3.','2016-02-19 14:40:00',1,'Sorocaba','SP'),(2,4,1,'PRECISA-SE DE ESCRITOR','Não sei escrever direito então preciso de um escritor para me ensinar.','2016-02-19 16:00:00',0,'São Paulo','SP'),(3,3,1,'Auxiliar de limpeza','Irá atuar com limpeza do restaurante e lavagens de louças. ','2016-02-19 16:00:00',0,'São Paulo','SP'),(4,1,NULL,'Instrutor de inglês','Irá dar aulas, corrigir provas, lançar notas, lançar chamadas, fazer ligações para alunos e pais de alunos sempre que solicitado e necessário, fazer planejamento e relatório de aulas.','2016-02-19 16:00:00',1,'São Paulo','SP'),(5,2,2,'Redator para blog','Procuramos profissional de redação freelancer para conteúdos periódicos WEB. ','2016-02-19 17:00:00',0,'Sorocaba','SP'),(9,2,4,'Telefonista','Atuar com o atendimento das ligações, anotar os recados, efetuar contatos telefônicos, entre outras atividades relacionadas ao cargo.','2016-02-19 17:00:00',0,'Teresina','PI'),(10,1,2,'Tirar fotos de 5 produtos','O trabalho que precisará ser realizado e a fotografia de 5 produtos para loja virtual. Serão 3 caixas pequenas e duas embalagens sanfonadas. A imagem que será utilizada depois da entrega deve ter fundo transparente, boa qualidade de imagem, e recursos mais artísticos como posição do produto na foto.','2016-02-19 18:00:00',0,'Fortaleza','CE'),(11,1,1,'Depiladora e Designer de Sobrancelha ','Estamos com uma vaga para depiladora freelancer com experiência em sobrancelha. Comissão de 45% mais conduçao ida e volta. Terça-feira a Sabado das 10h00 às 19h00. Com experiência na área. Salão localizado na Zona Sul. próximo ao metrô Praça da árvore e metrô Santa Cruz.','2016-02-19 19:00:00',1,'Itajaí','SC'),(12,2,1,'Criar um site para o meu negócio','Quero um site com um blog, com um portfólio de procedimentos, com um espaço onde o cliente possa se agendar sozinho, com um layout parecido com o do superagendador, que haja uma aba de cadastro em que o cliente deva por obrigatoriamente uma foto e seu número de cartão, abas para explicação do porquê solicitamos essas coisas, uma aba quem nós somos, e depoimentos.','2016-06-02 03:35:00',0,'Teste','AC'),(13,1,3,'Analista de Tráfego Pago - Adwords','Vaga de analista de tráfego pago, para criar e gerenciar campanhas de adwords, comparadores de preços, redes sociais e análises analytics.','2016-06-02 03:43:24',0,'Teste','AC'),(14,5,4,'Gerente de Contas . Site Mercado Financeiro','Estamos desenvolvendo uma plataforma de listagem de empresas para operar em bolsas de balcão, e selecionamos candidatos para a função de Gerente de Contas.\n\nSerá responsável pela prospecção e gestão de carteira de escritórios de contabilidade, gestores de fundos e empresas, e atuará nas seguintes áreas do conhecimento:\n\n- Balanços e Demonstrativos\n- Private Equity . Compra e Venda de Empresas\n- Bolsa de Valores\n- Mercado Balcão\n- Contato com escritórios jurídicos, contábeis e médias e grandes empresas.\n\nPerfil do Candidato:\n- Afinidade com a área de economia e investimentos;\n- Bom atendimento telefônico e contatos de mailing;\n- Conhecimentos de informática, cadastramento de empresas, bancos de dados;\n- Desejável network na área empresarial;\n- Proatividade e Ganhos por comissões sobre negócios;\n\nSalário Fixo + Renda % sobre os negócios da carteira.\nEstimativa: de R$ 2.000 a R$ 6.000 mensais, com possibilidades maiores em razão de comissões.\n\nValor Médio de Comissões (além do fixo):\n80,0','2016-06-02 03:43:33',0,'Teste','AC'),(15,6,4,'Gerente de Projetos','Somos uma agência online e devido a demanda, precisamos de um gerente de projetos para ajudar a manter o padrão de qualidade em nossos projetos.\n\nFunções a trabalhar:\n- Gestão de prazos (interno e clientes)\n- Atendimento e suporte a clientes \n- Gestão interna de testes\n- Análise de projetos','2016-06-02 03:43:44',1,'Teste','AC'),(16,1,1,'Escritor para site de negócios online','Ola preciso de um escritor para longo prazo\ntenho um site sobre negócios digitais preciso de um editor que tenha conhecimento na área e que já realizou algum tipo de trabalho.se não tiver nenhum trabalho realizado me contate que eu envio um titulo teste.\nEstilo de escrita e informal homem falando para ambos os sexos.\nEu mandarei os títulos dos artigos\n\nOs artigos devem possuir entre 500 a 800 palavras\n\nDivididos em frases e parágrafos curtos para facilitar a leitura.','2016-06-02 03:47:21',0,'Teste','SE'),(17,7,3,'Desenvolvimento Java Web','Preciso de alguém que desenvolva uma aplicação que faça a soma de 1 + 1, e que o resultado seja 3.','2016-06-02 03:48:47',0,'Rio De Janeiro','RJ'),(18,4,4,'Criar conteúdo para APOSTILA para 25 horas de aulas','Preciso de conteúdista para criar Apostila e Manual do Professor para curso de capacitação com 100 horas/aula aproximadamente divididos em 4 áreas sendo:\nAssistente Administrativos (25 horas)\nAuxiliar de Recursos Humanos e Departamento Pessoal (25 horas)\nMarketing e Vendas (25 horas)\nCrédito e Cobrança (25 horas)\n\nPúblico: Jovens com idade entre 14 e 25 anos oriundo do ensino público.','2016-06-02 04:15:23',0,'Cidade De Deus','RR'),(19,1,2,'Técnico de Informática','Área e especialização profissional: Informática, Ti, Telecomunicações. \n\nNível hierárquico: Analista','2016-06-02 04:42:38',0,'Sorocaba','SP'),(20,2,3,'Design e Desenvolvimento Website','O site precisa conter as seguintes páginas:\n- Empresa (sobre nós);\n- Serviços (galeria de fotos com descrição);\n- Contato;\n\nTambém deve ser criados o logo e o design.','2016-06-02 12:02:21',1,'São Paulo','SP'),(21,8,1,'Programador PHP','Desenvolver novas funcionalidades em sistemas proprietários e correção de BUGS nos sistemas.\nNossos Sistemas são desenvolvidos em PHP + MYSQL.\n\nBenefícios: Vale Transporte, Assistência Médica / Medicina em grupo, Seguro de Vida em Grupo, Restaurante na empresa, Convênio com Farmácia\nRegime de contratação: CLT (Efetivo)\nHorário: Das 08:00 às 17:48.\nInformações adicionais: Não é preciso viajar. Empresa oferece café da manhã, sucos / frutas / pães e frios. Empresa possui quadra de futebol e etc.','2016-06-02 12:34:17',0,'São Bernardo do Campo','SP'),(22,1,2,'Precisa-se de desenvolvedor para Android','Deve saber o básico de programação Java e ter uma base sobre POO. Também deve ter experiência na área.','2016-06-03 05:12:35',1,'Sorocaba','SP'),(23,11,6,'Novo Anúncio Lucas','Teste','2016-06-03 16:23:49',1,'Sorocaba','SP'),(24,9,1,'Fernando Anuncio','Funfed?','2016-06-03 19:59:25',1,'Sorocity','AC'),(25,9,1,'Teste Fernando','Teste para confirmar se esta ou nao retornando o id do anuncio.','2016-06-03 20:15:16',0,'Sim City','AC'),(26,9,1,'Anuncio Fernando 1','Nenhuma','2016-06-03 20:33:14',0,'Sorocaba','AC'),(27,11,1,'Teste','Rede','2016-06-04 02:11:24',0,'Teste','AC'),(28,1,1,'Teste','teste','2016-06-11 09:25:34',0,'Teste','AC'),(29,16,2,'Anúncio da Anahí 5','Teste','2016-06-11 13:44:29',0,'Sorocaba','SP'),(30,16,6,'Anúncio 2 da Anahí 5','Teste','2016-06-11 14:01:54',1,'Sorocaba','SP'),(31,16,4,'Anúncio 3 Anahí 5','Teste','2016-06-11 14:35:51',0,'Sítio','AC'),(32,16,6,'Anúncio 4 Anahí 5','Teste','2016-06-11 15:12:30',0,'Abc','AC'),(33,16,3,'Teste Ester','É São Paulo','2016-06-18 10:00:29',0,'Iperó','AC'),(34,1,3,'testando','Ele  esta tentando','2016-06-18 10:42:06',2,'Maringa','PR'),(35,1,1,'Abcd','abc','2016-06-19 02:08:55',2,'Abc','BA');
/*!40000 ALTER TABLE `anuncio` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-20  2:03:25
