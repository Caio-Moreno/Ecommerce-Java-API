package br.com.brazukas.controller;

import br.com.brazukas.DAO.ImagemDAO;
import br.com.brazukas.Models.Imagem;
import br.com.brazukas.Models.Produto;
import br.com.brazukas.controller.infra.FileServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/imagem")
public class ImagemController {

    @Autowired
    private FileServer fileSaver;



    @RequestMapping(params = {"id"},method =  RequestMethod.POST)
    public Imagem Post(@RequestBody List<MultipartFile> foto,int id) throws IOException, SQLException {
         Imagem imagem = Imagem.builder().build();
         
        var caminhoImagem = fileSaver.upload(foto,id);
        System.out.println(caminhoImagem.size() + " size");

        System.out.println(imagem.getCaminhoImagem1());

        System.out.println(imagem.toString() + "to aqui bugaddo");

        for (String s : caminhoImagem) {
            if (imagem.getCaminhoImagem1() == null ) {
                System.out.println("foreach");
                imagem.setCaminhoImagem1(s);
            } else if (imagem.getCaminhoImagem2() == null ) {
                System.out.println("foreach");
                imagem.setCaminhoImagem2(s);
            } else if (imagem.getCaminhoImagem3() == null  ) {
                System.out.println("foreach");
                imagem.setCaminhoImagem3(s);
            } else if (imagem.getCaminhoImagem4() == null) {
                System.out.println("foreach");
                imagem.setCaminhoImagem4(s);
            }
        }

        System.out.println(imagem.getCaminhoImagem1());
        System.out.println(imagem.getCaminhoImagem2());
        System.out.println(imagem.getCaminhoImagem3());
        System.out.println(imagem.getCaminhoImagem4());

        ImagemDAO.inserirImagens(imagem,id);

        return imagem;

    }

}
