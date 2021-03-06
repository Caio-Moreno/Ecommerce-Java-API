package br.com.brazukas.controller.infra;

import br.com.brazukas.Models.Produto;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileServer {

    @Autowired
    private AmazonS3 amazonS3;
    private static final String BUCKET="imagens-bombapath-games";

    public List<String> upload(@RequestParam List<MultipartFile> foto) {
        List<String> imagensPath = new ArrayList<>();

        try{
            for (int i = 0; i < foto.size();i++) {
                var imagemName =  "imagem"+i+".jpg";
                var imagem = foto.get(i);
                amazonS3.putObject(new PutObjectRequest(BUCKET,
                        imagemName , imagem.getInputStream(), null)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                 imagensPath.add("http://" + BUCKET + ".s3.amazonaws.com/" + imagemName) ;
            }
            return imagensPath;
        } catch (IllegalStateException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}