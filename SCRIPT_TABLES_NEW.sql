CREATE DATABASE  IF NOT EXISTS `BRAZUKAS` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `BRAZUKAS`;
-- MySQL dump 10.13  Distrib 5.6.13, for osx10.6 (i386)
--
-- Host: 10.211.55.16    Database: BRAZUKAS
-- ------------------------------------------------------
-- Server version	5.7.28

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
-- Table structure for table `CARRINHO`
--

DROP TABLE IF EXISTS `CARRINHO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CARRINHO` (
  `ID_CLIENTE` int(11) NOT NULL,
  `ID_PRODUTO` int(11) NOT NULL,
  `STATUS` varchar(20) DEFAULT NULL,
  `QUANTIDADE` int(11) DEFAULT NULL,
  `VALOR` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CARRINHO`
--

LOCK TABLES `CARRINHO` WRITE;
/*!40000 ALTER TABLE `CARRINHO` DISABLE KEYS */;
INSERT INTO `CARRINHO` (`ID_CLIENTE`, `ID_PRODUTO`, `STATUS`, `QUANTIDADE`, `VALOR`) VALUES (1,4,'PENDING',60,10.00),(1,6,'PENDING',2,10.00);
/*!40000 ALTER TABLE `CARRINHO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENTE_ENDERECO`
--

DROP TABLE IF EXISTS `CLIENTE_ENDERECO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENTE_ENDERECO` (
  `ID_CLIENTE_FK` int(11) NOT NULL,
  `CEP` varchar(14) DEFAULT NULL,
  `LOGRADOURO` varchar(255) DEFAULT NULL,
  `BAIRRO` varchar(255) DEFAULT NULL,
  `NUMERO` int(11) DEFAULT NULL,
  `COMPLEMENTO` varchar(45) DEFAULT NULL,
  `CIDADE` varchar(100) DEFAULT NULL,
  `ESTADO` varchar(100) DEFAULT NULL,
  KEY `FK_ENDERECO_idx` (`ID_CLIENTE_FK`),
  CONSTRAINT `FK_ENDERECO` FOREIGN KEY (`ID_CLIENTE_FK`) REFERENCES `USUARIO` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENTE_ENDERECO`
--

LOCK TABLES `CLIENTE_ENDERECO` WRITE;
/*!40000 ALTER TABLE `CLIENTE_ENDERECO` DISABLE KEYS */;
/*!40000 ALTER TABLE `CLIENTE_ENDERECO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENTE_TELEFONE`
--

DROP TABLE IF EXISTS `CLIENTE_TELEFONE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENTE_TELEFONE` (
  `ID_CLIENTE_FK` int(11) NOT NULL,
  `TELEFONE` varchar(15) DEFAULT NULL,
  KEY `FK_TELEFONE_idx` (`ID_CLIENTE_FK`),
  CONSTRAINT `FK_TELEFONE` FOREIGN KEY (`ID_CLIENTE_FK`) REFERENCES `USUARIO` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENTE_TELEFONE`
--

LOCK TABLES `CLIENTE_TELEFONE` WRITE;
/*!40000 ALTER TABLE `CLIENTE_TELEFONE` DISABLE KEYS */;
INSERT INTO `CLIENTE_TELEFONE` (`ID_CLIENTE_FK`, `TELEFONE`) VALUES (55,'11947874059'),(57,'11947874059'),(58,'11947874059'),(60,'11947874059'),(61,'11947874059'),(62,'11947874059'),(63,'11947874059'),(64,'11947874059'),(65,'+5511951332638'),(66,'11947874059'),(67,'11947874059');
/*!40000 ALTER TABLE `CLIENTE_TELEFONE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ESTOQUE`
--

DROP TABLE IF EXISTS `ESTOQUE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ESTOQUE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_PRODUTO_FK` int(11) NOT NULL,
  `QUANTIDADE` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PRODUTO` (`ID_PRODUTO_FK`),
  CONSTRAINT `FK_PRODUTO` FOREIGN KEY (`ID_PRODUTO_FK`) REFERENCES `PRODUTO` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ESTOQUE`
--

LOCK TABLES `ESTOQUE` WRITE;
/*!40000 ALTER TABLE `ESTOQUE` DISABLE KEYS */;
INSERT INTO `ESTOQUE` (`ID`, `ID_PRODUTO_FK`, `QUANTIDADE`) VALUES (65,64,20);
/*!40000 ALTER TABLE `ESTOQUE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `IMAGENS`
--

DROP TABLE IF EXISTS `IMAGENS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `IMAGENS` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CAMINHO` varchar(255) DEFAULT NULL,
  `ID_PRODUTO_FK` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_IMAGEM_idx` (`ID_PRODUTO_FK`),
  CONSTRAINT `FK_IMAGEM` FOREIGN KEY (`ID_PRODUTO_FK`) REFERENCES `PRODUTO` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `IMAGENS`
--

LOCK TABLES `IMAGENS` WRITE;
/*!40000 ALTER TABLE `IMAGENS` DISABLE KEYS */;
/*!40000 ALTER TABLE `IMAGENS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PRODUTO`
--

DROP TABLE IF EXISTS `PRODUTO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRODUTO` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NOME` varchar(150) NOT NULL,
  `DESCRICAO` text NOT NULL,
  `QUALIDADE` float NOT NULL,
  `CATEGORIA` varchar(50) NOT NULL,
  `STATUS` char(1) NOT NULL,
  `PRECO` decimal(10,2) NOT NULL,
  `PLATAFORMA` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PRODUTO`
--

LOCK TABLES `PRODUTO` WRITE;
/*!40000 ALTER TABLE `PRODUTO` DISABLE KEYS */;
INSERT INTO `PRODUTO` (`ID`, `NOME`, `DESCRICAO`, `QUALIDADE`, `CATEGORIA`, `STATUS`, `PRECO`, `PLATAFORMA`) VALUES (64,'Grand Theft Auto V','Grand Theft Auto V é um jogo eletrônico de ação-aventura desenvolvido pela Rockstar North e publicado pela Rockstar Games.',4.5,'Crime','I',40.00,'PS4');
/*!40000 ALTER TABLE `PRODUTO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TOKENS`
--

DROP TABLE IF EXISTS `TOKENS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TOKENS` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TOKEN` varchar(20) NOT NULL,
  `DATE_EXPIRED` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TOKENS`
--

LOCK TABLES `TOKENS` WRITE;
/*!40000 ALTER TABLE `TOKENS` DISABLE KEYS */;
INSERT INTO `TOKENS` (`ID`, `TOKEN`, `DATE_EXPIRED`) VALUES (68,'6ff4b7b002174a6ebfc7','2021-04-01 21:57:30'),(69,'05b647e7a3314bfd8229','2021-04-01 22:00:13'),(70,'e9174b8de7c640bf8c57','2021-04-01 22:00:52'),(71,'471108c8493449ddb655','2021-04-01 22:01:49'),(72,'e383a91b17f94ed9bc58','2021-04-01 22:02:45');
/*!40000 ALTER TABLE `TOKENS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USUARIO`
--

DROP TABLE IF EXISTS `USUARIO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USUARIO` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NOME` varchar(50) NOT NULL,
  `CPF` varchar(11) DEFAULT NULL,
  `SEXO` char(1) DEFAULT NULL,
  `DATANASCIMENTO` varchar(20) DEFAULT NULL,
  `EMAIL` varchar(50) DEFAULT NULL,
  `PASSWORD` varchar(200) NOT NULL,
  `PERMISSAO` char(1) NOT NULL,
  `TOKEN` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CPF` (`CPF`),
  UNIQUE KEY `EMAIL_UNIQUE` (`EMAIL`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USUARIO`
--

LOCK TABLES `USUARIO` WRITE;
/*!40000 ALTER TABLE `USUARIO` DISABLE KEYS */;
INSERT INTO `USUARIO` (`ID`, `NOME`, `CPF`, `SEXO`, `DATANASCIMENTO`, `EMAIL`, `PASSWORD`, `PERMISSAO`, `TOKEN`) VALUES (55,'Caio Moreno',NULL,NULL,NULL,'cviniciius.moreno@outlook.com','e959088c6049f1104c84c9bde5560a13','A',NULL),(57,'Caio Moreno',NULL,NULL,NULL,'cvinicius.moreno@outlook.com','e959088c6049f1104c84c9bde5560a13','A',NULL),(58,'',NULL,NULL,NULL,'caio.vinicius.moreno@gmail.com','25d55ad283aa400af464c76d713c07ad','C',NULL),(60,'teste',NULL,NULL,NULL,'caio.vinicius.moreno1@gmail.com','22d7fe8c185003c98f97e5d6ced420c7','C',NULL),(61,'Caio Moreno',NULL,NULL,NULL,'caio.vinicius.moreno4@gmail.com','09151a42659cfc08aff86820f973f640','C',NULL),(62,'Teste 2',NULL,NULL,NULL,'caio.vinicius.moreno8@gmail.com','ea7f6fc302c7c93a6accc634ef80a3d2','C','5612023ef11a46a2b71d'),(63,'Caio teste agora',NULL,NULL,NULL,'agora@gmail.com','aa1bf4646de67fd9086cf6c79007026c','C','6ff4b7b002174a6ebfc7'),(64,'teste de agora',NULL,NULL,NULL,'caioteste@gmail.com','aa1bf4646de67fd9086cf6c79007026c','C','05b647e7a3314bfd8229'),(65,'Caio Moreno',NULL,NULL,NULL,'teste@gmail.com.br','09151a42659cfc08aff86820f973f640','C','e9174b8de7c640bf8c57'),(66,'Caio testando',NULL,NULL,NULL,'caiotestando@gmail.com','aa1bf4646de67fd9086cf6c79007026c','C','471108c8493449ddb655'),(67,'Caio Moreno',NULL,NULL,NULL,'caiomoreno@gmail.com','621e8930aff1d2fb7b2224be5d6abcb0','C','e383a91b17f94ed9bc58');
/*!40000 ALTER TABLE `USUARIO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `VENDA`
--

DROP TABLE IF EXISTS `VENDA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VENDA` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DATA_VENDA` varchar(10) NOT NULL,
  `COD_CLIENTE` int(10) NOT NULL,
  `QUANTIDADE` int(10) NOT NULL,
  `DESCONTO` decimal(10,2) DEFAULT NULL,
  `VALOR_TOTAL` decimal(10,2) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VENDA`
--

LOCK TABLES `VENDA` WRITE;
/*!40000 ALTER TABLE `VENDA` DISABLE KEYS */;
INSERT INTO `VENDA` (`ID`, `DATA_VENDA`, `COD_CLIENTE`, `QUANTIDADE`, `DESCONTO`, `VALOR_TOTAL`) VALUES (1,'06/02/2021',1,2,2.10,200.00);
/*!40000 ALTER TABLE `VENDA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `VENDA_HAS_PRODUTO`
--

DROP TABLE IF EXISTS `VENDA_HAS_PRODUTO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VENDA_HAS_PRODUTO` (
  `ID_VENDA_FK` int(11) NOT NULL,
  `ID_PRODUTO_FK` int(11) NOT NULL,
  `VALOR` float NOT NULL,
  `QUANTIDADE` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VENDA_HAS_PRODUTO`
--

LOCK TABLES `VENDA_HAS_PRODUTO` WRITE;
/*!40000 ALTER TABLE `VENDA_HAS_PRODUTO` DISABLE KEYS */;
INSERT INTO `VENDA_HAS_PRODUTO` (`ID_VENDA_FK`, `ID_PRODUTO_FK`, `VALOR`, `QUANTIDADE`) VALUES (1,1,2.2,10);
/*!40000 ALTER TABLE `VENDA_HAS_PRODUTO` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`cmoreno`@`%`*/ /*!50003 TRIGGER atualiza_estoque AFTER INSERT ON VENDA_HAS_PRODUTO FOR EACH ROW
BEGIN
UPDATE ESTOQUE SET QUANTIDADE = QUANTIDADE - NEW.QUANTIDADE WHERE ID_PRODUTO_FK = NEW.ID_PRODUTO_FK;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Dumping events for database 'BRAZUKAS'
--
/*!50106 SET @save_time_zone= @@TIME_ZONE */ ;
/*!50106 DROP EVENT IF EXISTS `limparTokens` */;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8 */ ;;
/*!50003 SET character_set_results = utf8 */ ;;
/*!50003 SET collation_connection  = utf8_general_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = '-03:00' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`cmoreno`@`%`*/ /*!50106 EVENT `limparTokens` ON SCHEDULE EVERY 1 MINUTE STARTS '2021-04-01 17:00:41' ON COMPLETION PRESERVE ENABLE DO DELETE FROM BRAZUKAS.TOKENS
	WHERE 1=1
	and DATE_EXPIRED <= now() OR DATE_EXPIRED is null */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
DELIMITER ;
/*!50106 SET TIME_ZONE= @save_time_zone */ ;

--
-- Dumping routines for database 'BRAZUKAS'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-01 18:31:43
