create database brazukas;
use brazukas;

create table produto (
idProduto int primary key auto_increment not null,
nomeProduto varchar (200) not null,
nomeExtenso varchar (2000) not null,
qualidadeProduto numeric not null,
categoria varchar (20) not null,
statusProduto enum ('I','A') not null,
qtdEstoque numeric not null,
preço numeric (10,2) not null
);