# Ecommerce-Java-API
 API de Ecommerce em Spring Boot


## ENDPOINTS
 http://localhost:8080/Produtos //pesquisa todos os produtos GET

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
