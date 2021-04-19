package br.com.brazukas.controller;

import br.com.brazukas.Models.Email;
import br.com.brazukas.Models.Responses.ErrorResponse;
import br.com.brazukas.Models.Responses.SuccessResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/Email")
public class EmailController {
    @Autowired
    private JavaMailSender mailSender;

    @ApiOperation("Envia email de recuperação de senha")
    @RequestMapping(method = RequestMethod.POST, value = "Recovery")
    public ResponseEntity<?> sendMail(@RequestBody Email email) {
        try {
            MimeMessage mail = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper( mail );
            helper.setTo(email.get_email());
            helper.setSubject(email.get_assunto());
            helper.setText(email.get_mensagem() + "<p>"+gerarSenha()+"</p>", true);
            mailSender.send(mail);
            return ResponseEntity.ok(new SuccessResponse(200, "E-mail enviado com sucesso!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, "Erro no envio-->>"+e.getMessage()));
        }
    }



    private static String gerarSenha(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-","").substring(0,8);
    }
}
