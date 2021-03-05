package br.com.brazukas.controller;

import br.com.brazukas.DAO.ProdutoDAO;
import br.com.brazukas.Models.Imagem;
import br.com.brazukas.Models.Produto;
import br.com.brazukas.controller.Dto.ProdutoDto;
import br.com.brazukas.controller.infra.FileServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import static br.com.brazukas.Util.CriarArquivoDeLog.gravaLog;

@CrossOrigin
@RestController
@RequestMapping("/imagem")
public class ImagemController {

    @Autowired
    private FileServer fileSaver;

    private Imagem imagem = Imagem.builder().build();

    @RequestMapping(method =  RequestMethod.POST)
    public Imagem Post(@RequestBody List<MultipartFile> foto) throws IOException, SQLException {

        var caminhoImagem = fileSaver.upload(foto);
        System.out.println(caminhoImagem.size() + " size");

        System.out.println(imagem.getCaminhoImagem1());

        System.out.println(imagem.toString() + "to aqui bugaddo");

        for (String s : caminhoImagem) {
            if (imagem.getCaminhoImagem1() == null || imagem.getCaminhoImagem1().equals(s)) {
                imagem.setCaminhoImagem1(s);
            } else if (imagem.getCaminhoImagem2() == null || imagem.getCaminhoImagem2().equals(s) ) {
                imagem.setCaminhoImagem2(s);
            } else if (imagem.getCaminhoImagem3() == null || imagem.getCaminhoImagem3().equals(s) ) {
                imagem.setCaminhoImagem3(s);
            } else if (imagem.getCaminhoImagem4() == null|| imagem.getCaminhoImagem4().equals(s) ) {
                imagem.setCaminhoImagem4(s);
            }
        }


        return imagem;

    }

}
