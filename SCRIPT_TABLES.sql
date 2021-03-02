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

INSERT INTO PRODUTO(ID, NOME, DESCRICAO, QUALIDADE, CATEGORIA, STATUS, PRECO, PLATAFORMA, IMAGEM1, IMAGEM2, IMAGEM3, IMAGEM4) VALUES(DEFAULT, 'God of War', 'God of War é uma série de jogos eletrônicos de ação-aventura vagamente baseado nas mitologias grega e nórdica sendo criado originalmente por David Jaffe da Santa Monica Studio. Iniciada em 2005, a série tornou-se carro-chefe para a marca PlayStation, que consiste em oito jogos em várias plataformas.','3.5', 'Guerra', 'A', '20.00','','','','','');
INSERT INTO PRODUTO(ID, NOME, DESCRICAO, QUALIDADE, CATEGORIA, STATUS, PRECO, PLATAFORMA, IMAGEM1, IMAGEM2, IMAGEM3, IMAGEM4) VALUES(DEFAULT, 'PES', 'Jogo de futebol japones','2.0', 'Futebol', 'A', '30.00','','','','','');
INSERT INTO PRODUTO(ID, NOME, DESCRICAO, QUALIDADE, CATEGORIA, STATUS, PRECO, PLATAFORMA, IMAGEM1, IMAGEM2, IMAGEM3, IMAGEM4) VALUES(DEFAULT, 'FIFA', 'Jogo de futebol com narração de Gustavo Villani','5.0', 'Futebol', 'A', '60.00','','','','','');
INSERT INTO PRODUTO(ID, NOME, DESCRICAO, QUALIDADE, CATEGORIA, STATUS, PRECO, PLATAFORMA, IMAGEM1, IMAGEM2, IMAGEM3, IMAGEM4) VALUES(DEFAULT, 'Black Ops Cold War', 'Jogo de tiro','5.0', 'FPS', 'A', '90.00','','','','','');
INSERT INTO PRODUTO(ID, NOME, DESCRICAO, QUALIDADE, CATEGORIA, STATUS, PRECO, PLATAFORMA, IMAGEM1, IMAGEM2, IMAGEM3, IMAGEM4) VALUES(DEFAULT, 'Modern Warfare', 'Jogo de tiro','5.0', 'FPS', 'A', '130.00','','','','','');
INSERT INTO PRODUTO(ID, NOME, DESCRICAO, QUALIDADE, CATEGORIA, STATUS, PRECO, PLATAFORMA, IMAGEM1, IMAGEM2, IMAGEM3, IMAGEM4) VALUES(DEFAULT, 'Need for Speed', 'Jogo de corrida','4.0', 'Corrida', 'A', '80.00','','','','','');
INSERT INTO PRODUTO(ID, NOME, DESCRICAO, QUALIDADE, CATEGORIA, STATUS, PRECO, PLATAFORMA, IMAGEM1, IMAGEM2, IMAGEM3, IMAGEM4) VALUES(DEFAULT, 'LOL', 'RPG','2.0', 'Futebol', 'A', '30.00','','','','','');
INSERT INTO PRODUTO(ID, NOME, DESCRICAO, QUALIDADE, CATEGORIA, STATUS, PRECO, PLATAFORMA, IMAGEM1, IMAGEM2, IMAGEM3, IMAGEM4) VALUES(DEFAULT, 'Resident Evil', 'Ataque Zumbi','4.0', 'história', 'A', '30.00','','','','','');

INSERT INTO ESTOQUE(ID, ID_PRODUTO_FK, QUANTIDADE) VALUES(DEFAULT, '1', '10');
INSERT INTO ESTOQUE(ID, ID_PRODUTO_FK, QUANTIDADE) VALUES(DEFAULT, '2', '10');
INSERT INTO ESTOQUE(ID, ID_PRODUTO_FK, QUANTIDADE) VALUES(DEFAULT, '3', '10');
INSERT INTO ESTOQUE(ID, ID_PRODUTO_FK, QUANTIDADE) VALUES(DEFAULT, '4', '10');
INSERT INTO ESTOQUE(ID, ID_PRODUTO_FK, QUANTIDADE) VALUES(DEFAULT, '5', '10');
INSERT INTO ESTOQUE(ID, ID_PRODUTO_FK, QUANTIDADE) VALUES(DEFAULT, '6', '10');
INSERT INTO ESTOQUE(ID, ID_PRODUTO_FK, QUANTIDADE) VALUES(DEFAULT, '7', '10');
INSERT INTO ESTOQUE(ID, ID_PRODUTO_FK, QUANTIDADE) VALUES(DEFAULT, '8', '10');