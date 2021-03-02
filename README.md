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
