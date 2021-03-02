# Ecommerce-Java-API
 API de Ecommerce em Spring Boot


## ENDPOINTS
 http://localhost:8080/Produtos //pesquisa todos os produtos GET
 
 Retorno:
 ```
 [
    {
        "_idProduto": 1,
        "_nomeProduto": "God of War",
        "_descricao": "God of War é uma série de jogos eletrônicos de ação-aventura vagamente baseado nas mitologias grega e nórdica sendo criado originalmente por  David Jaffe da Santa Monica Studio. Iniciada em 2005, a série tornou-se carro-chefe para a marca PlayStation, que consiste em oito jogos em várias plataformas.",
        "_qualidadeProduto": 3.5,
        "_categoria": "Guerra",
        "_statusProduto": "A",
        "_qtdEstoque": 10,
        "_preco": 20.0,
        "_imagem": {
            "caminhoImagem1": "IMAGEM1 God of War",
            "caminhoImagem2": "IMAGEM2 God of War",
            "caminhoImagem3": "IMAGEM3 God of War",
            "caminhoImagem4": "IMAGEM4 God of War"
        },
        "_plataforma": ""
    }
 ]
 ```
 http://localhost:8080/Produtos?Nome=God Of War // Filtra por nome GET
 
 Retorno:
 ```
 [
    {
        "_idProduto": 1,
        "_nomeProduto": "God of War",
        "_descricao": "God of War é uma série de jogos eletrônicos de ação-aventura vagamente baseado nas mitologias grega e nórdica sendo criado originalmente por David Jaffe da Santa Monica Studio. Iniciada em 2005, a série tornou-se carro-chefe para a marca PlayStation, que consiste em oito jogos em várias plataformas.",
        "_qualidadeProduto": 3.5,
        "_categoria": "Guerra",
        "_statusProduto": "A",
        "_qtdEstoque": 10,
        "_preço": 20.0,
        "_imagem": "IMAGEM1 God of War",
        "_plataforma": ""
    },
    {
        "_idProduto": 2,
        "_nomeProduto": "PES",
        "_descricao": "Jogo de futebol japones",
        "_qualidadeProduto": 2.0,
        "_categoria": "Futebol",
        "_statusProduto": "A",
        "_qtdEstoque": 10,
        "_preço": 30.0,
        "_imagem": "IMAGEM1 PES",
        "_plataforma": ""
    },
    {
        "_idProduto": 3,
        "_nomeProduto": "FIFA",
        "_descricao": "Jogo de futebol com narração de Gustavo Villani",
        "_qualidadeProduto": 5.0,
        "_categoria": "Futebol",
        "_statusProduto": "A",
        "_qtdEstoque": 10,
        "_preço": 60.0,
        "_imagem": "IMAGEM1 FIFA",
        "_plataforma": ""
    },
    {
        "_idProduto": 4,
        "_nomeProduto": "Black Ops Cold War",
        "_descricao": "Jogo de tiro",
        "_qualidadeProduto": 5.0,
        "_categoria": "FPS",
        "_statusProduto": "A",
        "_qtdEstoque": 10,
        "_preço": 90.0,
        "_imagem": "IMAGEM1 Black Ops Cold War",
        "_plataforma": ""
    },
    {
        "_idProduto": 5,
        "_nomeProduto": "Modern Warfare",
        "_descricao": "Jogo de tiro",
        "_qualidadeProduto": 5.0,
        "_categoria": "FPS",
        "_statusProduto": "A",
        "_qtdEstoque": 10,
        "_preço": 130.0,
        "_imagem": "IMAGEM1 Modern Warfare",
        "_plataforma": ""
    },
    {
        "_idProduto": 6,
        "_nomeProduto": "Need for Speed",
        "_descricao": "Jogo de corrida",
        "_qualidadeProduto": 4.0,
        "_categoria": "Corrida",
        "_statusProduto": "A",
        "_qtdEstoque": 10,
        "_preço": 80.0,
        "_imagem": "IMAGEM1 Need for Speed",
        "_plataforma": ""
    },
    {
        "_idProduto": 7,
        "_nomeProduto": "LOL",
        "_descricao": "RPG",
        "_qualidadeProduto": 2.0,
        "_categoria": "Futebol",
        "_statusProduto": "A",
        "_qtdEstoque": 10,
        "_preço": 30.0,
        "_imagem": "IMAGEM1 LOL",
        "_plataforma": ""
    },
    {
        "_idProduto": 8,
        "_nomeProduto": "Resident Evil",
        "_descricao": "Ataque Zumbi",
        "_qualidadeProduto": 4.0,
        "_categoria": "história",
        "_statusProduto": "A",
        "_qtdEstoque": 10,
        "_preço": 30.0,
        "_imagem": "IMAGEM1 Resident Evil",
        "_plataforma": ""
    },
    {
        "_idProduto": 9,
        "_nomeProduto": "Grand Theft Auto V",
        "_descricao": "Grand Theft Auto V é um jogo eletrônico de ação-aventura desenvolvido pela Rockstar North e publicado pela Rockstar Games.",
        "_qualidadeProduto": 4.5,
        "_categoria": "Crime",
        "_statusProduto": "A",
        "_qtdEstoque": 20,
        "_preço": 40.0,
        "_imagem": "Grand Theft Auto V",
        "_plataforma": "PS4"
    },
    {
        "_idProduto": 10,
        "_nomeProduto": "Grand Theft Auto V",
        "_descricao": "Grand Theft Auto V é um jogo eletrônico de ação-aventura desenvolvido pela Rockstar North e publicado pela Rockstar Games.",
        "_qualidadeProduto": 4.5,
        "_categoria": "Crime",
        "_statusProduto": "A",
        "_qtdEstoque": 20,
        "_preço": 40.0,
        "_imagem": "Grand Theft Auto V",
        "_plataforma": "PS4"
    }
 ]
 ```
 
 http://localhost:8080/Produtos POST
 ```
 {
        "_idProduto": null,
        "_nomeProduto": "Grand Theft Auto V",
        "_descricao": "Grand Theft Auto V é um jogo eletrônico de ação-aventura desenvolvido pela Rockstar North e publicado pela Rockstar Games.",
        "_qualidadeProduto": 4.5,
        "_categoria": "Crime",
        "_statusProduto": "A",
        "_qtdEstoque": 20,
        "_preco": 40.0,
        "_imagem": {
            "caminhoImagem1": "Grand Theft Auto V",
            "caminhoImagem2": "Grand Theft Auto V",
            "caminhoImagem3": "Grand Theft Auto V",
            "caminhoImagem4": "Grand Theft Auto V"
        },
        "_plataforma": "PS4"
}
```
