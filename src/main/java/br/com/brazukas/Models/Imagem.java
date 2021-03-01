package br.com.brazukas.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Imagem {


    String caminhoImagem1;
    String caminhoImagem2;
    String caminhoImagem3;
    String caminhoImagem4;

    public Imagem(String caminhoImagem1, String caminhoImagem2, String caminhoImagem3, String caminhoImagem4) {
        this.caminhoImagem1 = caminhoImagem1;
        this.caminhoImagem2 = caminhoImagem2;
        this.caminhoImagem3 = caminhoImagem3;
        this.caminhoImagem4 = caminhoImagem4;
    }
}
