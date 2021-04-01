CREATE DATABASE BRAZUKAS;

 USE BRAZUKAS;

CREATE TABLE PRODUTO (
	ID int primary key auto_increment not null,
	NOME varchar(150) not null,
	DESCRICAO text not null,
	QUALIDADE float not null,
	CATEGORIA varchar(50) not null,
	STATUS char(1) not null,
	PRECO decimal(10,2) not null,
	IMAGEM1 varchar(255),
	IMAGEM2 varchar(255),
	IMAGEM3 varchar(255),
	IMAGEM4 varchar(255)
);

CREATE TABLE ESTOQUE (
	ID int primary key auto_increment not null,
	ID_PRODUTO_FK int not null,
	QUANTIDADE int not null,
	CONSTRAINT FK_PRODUTO FOREIGN KEY (ID_PRODUTO_FK) REFERENCES PRODUTO(ID)
);

CREATE TABLE CLIENTE (
	ID int primary key auto_increment not null,
	NOME varchar(50) not null,
	CPF varchar(11) not null unique key,
	SEXO char(1) not null,
	DATANASCIMENTO varchar(20) not null,
	TELEFONE varchar(20) not null,
	EMAIL varchar(50) not null,
	CEP varchar(9) not null,
	ENDERECO varchar(100) not null,
	BAIRRO varchar(20) not null,
	NUMERO int not null,
	COMPLEMENTO varchar(30),
	CIDADE varchar(30) not null,
	ESTADO varchar(20) not null
);

CREATE TABLE VENDA (
	ID int primary key not null auto_increment,
	DATA_VENDA varchar(20) NOT NULL,
	COD_CLIENTE int(10) NOT NULL,
	QUANTIDADE int(10) NOT NULL,
	DESCONTO decimal(10,2) DEFAULT NULL,
	VALOR_TOTAL decimal(10,2) NOT NULL
);

CREATE TABLE VENDA_HAS_PRODUTO(
	ID_VENDA_FK int not null,
	ID_PRODUTO_FK int not null,
	VALOR float not null,
	QUANTIDADE int not null
);

CREATE TABLE LOGIN(
	NOME varchar(20) not null,
	SOBRENOME varchar(100) not null,
	LOGIN varchar(20) not null,
	PASSWORD varchar(200) not null
);

ALTER TABLE PRODUTO
CHANGE COLUMN DESCRICAO DESCRICAO TEXT NOT NULL ;

ALTER TABLE PRODUTO
ADD COLUMN PLATAFORMA VARCHAR(45) NULL AFTER PRECO;

DELIMITER $$
CREATE TRIGGER atualiza_estoque AFTER INSERT ON VENDA_HAS_PRODUTO FOR EACH ROW
BEGIN
UPDATE ESTOQUE SET QUANTIDADE = QUANTIDADE - NEW.QUANTIDADE WHERE ID_PRODUTO_FK = NEW.ID_PRODUTO_FK;
END $$

ALTER TABLE `BRAZUKAS`.`ESTOQUE`
DROP FOREIGN KEY `FK_PRODUTO`;
ALTER TABLE `BRAZUKAS`.`ESTOQUE`
ADD CONSTRAINT `FK_PRODUTO`
  FOREIGN KEY (`ID_PRODUTO_FK`)
  REFERENCES `BRAZUKAS`.`PRODUTO` (`ID`)
  ON DELETE CASCADE;

  ALTER TABLE `BRAZUKAS`.`VENDA`
CHANGE COLUMN `DATA_VENDA` `DATA_VENDA` VARCHAR(10) NOT NULL ;


CREATE TABLE `BRAZUKAS`.`CARRINHO` (
  `ID_PRODUTO` INT NOT NULL,
  `NOME` VARCHAR(200) NULL,
  `QUANTIDADE` INT NULL,
  `VALOR` DECIMAL(10,2) NULL);


ALTER TABLE `BRAZUKAS`.`CARRINHO`
ADD COLUMN `ID_CLIENTE` INT NULL FIRST;


ALTER TABLE `BRAZUKAS`.`CARRINHO`
CHANGE COLUMN `ID_CLIENTE` `ID_CLIENTE` INT(11) NOT NULL ;

ALTER TABLE `BRAZUKAS`.`CARRINHO`
CHANGE COLUMN `NOME` `STATUS` VARCHAR(20) NULL DEFAULT NULL ;


CREATE TABLE `BRAZUKAS`.`CLIENTE_ENDERECO` (
  `ID_CLIENTE_FK` INT NOT NULL,
  `ENDERECO` VARCHAR(255) NULL);

CREATE TABLE `BRAZUKAS`.`CLIENTE_TELEFONE` (
  `ID_CLIENTE_FK` INT NOT NULL,
  `TELEFONE` VARCHAR(15) NULL);

ALTER TABLE `BRAZUKAS`.`LOGIN`
ADD COLUMN `PERMISSAO` CHAR(1) NOT NULL AFTER `PASSWORD`;


ALTER TABLE `BRAZUKAS`.`LOGIN`
ADD COLUMN `ID` INT NOT NULL FIRST,
ADD PRIMARY KEY (`ID`);


CREATE TABLE `BRAZUKAS`.`IMAGENS` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `CAMINHO` VARCHAR(255) NULL,
  `ID_PRODUTO_FK` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`));



ALTER TABLE `BRAZUKAS`.`LOGIN`
DROP COLUMN `SOBRENOME`,
DROP COLUMN `NOME`,
ADD COLUMN `AUTHENTICATION_SID` VARCHAR(20) NULL AFTER `PERMISSAO`,
ADD COLUMN `ID_CLIENTE_FK` INT NULL AFTER `AUTHENTICATION_SID`,
ADD INDEX `ID_CLIENTE_FK_idx` (`ID_CLIENTE_FK` ASC);
ALTER TABLE `BRAZUKAS`.`LOGIN`
ADD CONSTRAINT `ID_CLIENTE_FK`
  FOREIGN KEY (`ID_CLIENTE_FK`)
  REFERENCES `BRAZUKAS`.`CLIENTE` (`ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


  ALTER TABLE `BRAZUKAS`.`CLIENTE`
CHANGE COLUMN `CPF` `CPF` VARCHAR(11) NULL ,
CHANGE COLUMN `SEXO` `SEXO` CHAR(1) NULL ,
CHANGE COLUMN `DATANASCIMENTO` `DATANASCIMENTO` VARCHAR(20) NULL ,
CHANGE COLUMN `TELEFONE` `TELEFONE` VARCHAR(20) NULL ,
CHANGE COLUMN `EMAIL` `EMAIL` VARCHAR(50) NULL ,
CHANGE COLUMN `CEP` `CEP` VARCHAR(9) NULL ,
CHANGE COLUMN `ENDERECO` `ENDERECO` VARCHAR(100) NULL ,
CHANGE COLUMN `BAIRRO` `BAIRRO` VARCHAR(20) NULL ,
CHANGE COLUMN `NUMERO` `NUMERO` INT(11) NULL ,
CHANGE COLUMN `CIDADE` `CIDADE` VARCHAR(30) NULL ,
CHANGE COLUMN `ESTADO` `ESTADO` VARCHAR(20) NULL ;

ALTER TABLE `BRAZUKAS`.`LOGIN`
CHANGE COLUMN `ID` `ID` INT(11) NOT NULL AUTO_INCREMENT ;


CREATE TABLE `BRAZUKAS`.`TOKENS` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `TOKEN` VARCHAR(20) NOT NULL,
  `STATUS` BIT NOT NULL,
  PRIMARY KEY (`ID`));


ALTER TABLE `BRAZUKAS`.`TOKENS`
ADD COLUMN `DATE_EXPIRED` DATETIME NULL AFTER `STATUS`;


ALTER TABLE `BRAZUKAS`.`TOKENS`
DROP COLUMN `STATUS`;


ALTER TABLE `BRAZUKAS`.`LOGIN`
CHANGE COLUMN `AUTHENTICATION_SID` `TOKEN` VARCHAR(20) NULL DEFAULT NULL ;


ALTER TABLE `BRAZUKAS`.`IMAGENS`
CHANGE COLUMN `ID_PRODUTO_FK` `ID_PRODUTO_FK` INT NULL DEFAULT NULL ,
ADD INDEX `FK_IMAGEM_idx` (`ID_PRODUTO_FK` ASC);
ALTER TABLE `BRAZUKAS`.`IMAGENS`
ADD CONSTRAINT `FK_IMAGEM`
  FOREIGN KEY (`ID_PRODUTO_FK`)
  REFERENCES `BRAZUKAS`.`PRODUTO` (`ID`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION;


  ALTER TABLE `BRAZUKAS`.`PRODUTO`
DROP COLUMN `IMAGEM4`,
DROP COLUMN `IMAGEM3`,
DROP COLUMN `IMAGEM2`,
DROP COLUMN `IMAGEM1`;


ALTER TABLE `BRAZUKAS`.`CLIENTE_ENDERECO`
ADD INDEX `FK_ENDERECO_idx` (`ID_CLIENTE_FK` ASC);
ALTER TABLE `BRAZUKAS`.`CLIENTE_ENDERECO`
ADD CONSTRAINT `FK_ENDERECO`
  FOREIGN KEY (`ID_CLIENTE_FK`)
  REFERENCES `BRAZUKAS`.`CLIENTE` (`ID`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION;


ALTER TABLE `BRAZUKAS`.`CLIENTE_TELEFONE`
ADD INDEX `FK_TELEFONE_idx` (`ID_CLIENTE_FK` ASC);
ALTER TABLE `BRAZUKAS`.`CLIENTE_TELEFONE`
ADD CONSTRAINT `FK_TELEFONE`
  FOREIGN KEY (`ID_CLIENTE_FK`)
  REFERENCES `BRAZUKAS`.`CLIENTE` (`ID`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION;


DROP EVENT limparTokens;

SET GLOBAL event_scheduler = ON;

CREATE EVENT limparTokens
ON SCHEDULE EVERY 1 MINUTE
ON COMPLETION PRESERVE
DO
     DELETE FROM BRAZUKAS.TOKENS
	WHERE 1=1
	and DATE_EXPIRED <= now() OR DATE_EXPIRED is null;


ALTER TABLE `BRAZUKAS`.`CLIENTE`
ADD UNIQUE INDEX `EMAIL_UNIQUE` (`EMAIL` ASC);


ALTER TABLE `BRAZUKAS`.`CLIENTE_ENDERECO`
CHANGE COLUMN `ENDERECO` `LOGRADOURO` VARCHAR(255) NULL DEFAULT NULL ,
ADD COLUMN `CEP` VARCHAR(14) NULL AFTER `ID_CLIENTE_FK`,
ADD COLUMN `BAIRRO` VARCHAR(255) NULL AFTER `LOGRADOURO`,
ADD COLUMN `NUMERO` INT NULL AFTER `BAIRRO`,
ADD COLUMN `COMPLEMENTO` VARCHAR(45) NULL AFTER `NUMERO`,
ADD COLUMN `CIDADE` VARCHAR(100) NULL AFTER `COMPLEMENTO`,
ADD COLUMN `ESTADO` VARCHAR(100) NULL AFTER `CIDADE`;


ALTER TABLE `BRAZUKAS`.`CLIENTE`
DROP COLUMN `ESTADO`,
DROP COLUMN `CIDADE`,
DROP COLUMN `COMPLEMENTO`,
DROP COLUMN `NUMERO`,
DROP COLUMN `BAIRRO`,
DROP COLUMN `ENDERECO`,
DROP COLUMN `CEP`,
DROP COLUMN `TELEFONE`;

ALTER TABLE `BRAZUKAS`.`CLIENTE`
RENAME TO  `BRAZUKAS`.`USUARIO` ;
