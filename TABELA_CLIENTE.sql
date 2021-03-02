CREATE DATABASE  IF NOT EXISTS `brazukas`;

USE `brazukas`;

DROP TABLE IF EXISTS `CLIENTE`;

CREATE TABLE `CLIENTE` (
  `ID_CLIENTE` int(10) NOT NULL AUTO_INCREMENT,
  `NOME` varchar(50) NOT NULL,
  `CPF` varchar(11) NOT NULL,
  `SEXO` varchar(1) NOT NULL,
  `DATANASCIMENTO` varchar(20) NOT NULL,
  `TELEFONE` varchar(20) NOT NULL,
  `EMAIL` varchar(50) NOT NULL,
  `CEP` varchar(9) NOT NULL,
  `ENDERECO` varchar(100) NOT NULL,
  `BAIRRO` varchar(20) NOT NULL,
  `NUMERO` int(5) NOT NULL,
  `COMPLEMENTO` varchar(30) DEFAULT NULL,
  `CIDADE` varchar(30) NOT NULL,
  `ESTADO` varchar(20) NOT NULL,
  PRIMARY KEY (`ID_CLIENTE`),
  UNIQUE KEY `CPF` (`CPF`),
  UNIQUE KEY `EMAIL` (`EMAIL`)
);

INSERT INTO `CLIENTE` (`ID_CLIENTE`, `NOME`, `CPF`, `SEXO`, `DATANASCIMENTO`, `TELEFONE`, `EMAIL`, `CEP`, `ENDERECO`, `BAIRRO`, `NUMERO`, `COMPLEMENTO`, `CIDADE`, `ESTADO`)
 VALUES (1,'Caio Moreno','47885964825','M','1998-12-18','11947874059','cvinicius.moreno@outlook.com','05775200','AV LEITAO DA CUNHA','Parque Regina',118,'Casa','SAO PAULO','SP'),(2,'Joao Vitor Alves','11111111111','M','2000-11-11','11928998328','joao@teste.com','12920910','12920910','Bela vista',1290,'Quinto andar','Sao paulo','AC'),(3,'Caio Teste','12121221121','M','2020-10-29','11947874059','teste@teste.com','11111111','11111111','csbhdsfbhj',118,'Quinto andar','sao paulo','AC'),(4,'Andre 12112','37370592889','M','1990-09-12','23123321233','teste@ahjdsadsjk.com','23123123','23123123','111',121221,'cvxdsdfsa','Saoo Paulo','AC');

SELECT * FROM CLIENTE;