package br.com.brazukas.controller.infra;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class FileServer {

    @Autowired
    private AmazonS3 amazonS3;
    private static final String BUCKET="imagens-bombapath-games";

    public String upload(@RequestParam MultipartFile foto) {
        try{
            amazonS3.putObject(new PutObjectRequest(BUCKET,
                    foto.getOriginalFilename(),foto.getInputStream(),null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            System.out.println("------->  "+"http://s3.amazonaws.com/"+BUCKET+"/"+foto.getOriginalFilename());
            return "http://s3.amazonaws.com/"+BUCKET+"/"+foto.getOriginalFilename();
        } catch (IllegalStateException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}