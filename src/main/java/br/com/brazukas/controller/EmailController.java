package br.com.brazukas.controller;

import br.com.brazukas.DAO.ClienteDAO;
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

    @ApiOperation("Envia email com os dados do pedido")
    @RequestMapping(method = RequestMethod.POST, value = "Recovery")
    public ResponseEntity<?> sendMail(@RequestBody Email email) {
        try {
            var senha = gerarSenha();
            boolean alterou = ClienteDAO.alterarSenha(email.get_email(), senha);

            if(alterou) {

                MimeMessage mail = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mail);
                helper.setTo(email.get_email());
                helper.setSubject(email.get_assunto());
                helper.setText(email.get_mensagem() + "<p>" + senha + "</p>", true);
                mailSender.send(mail);
                return ResponseEntity.ok(new SuccessResponse(200, "E-mail enviado com sucesso!"));
            }else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(500, "Erro para atualizar a senha!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, "Erro no envio-->>"+e.getMessage()));
        }
    }

    @ApiOperation("Envia email de recuperação de senha")
    @RequestMapping(method = RequestMethod.POST, value = "/order")
    public ResponseEntity<?> sendMailOrder(String email, String numPedido, String name) {
        try {
                MimeMessage mail = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mail);
                helper.setTo(email);
                helper.setSubject("Sua compra com Brazukas");
                helper.setText(htmlEmail(numPedido,name),true);
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

    private static String htmlEmail(String pedido, String name){
        String email =
                "<!doctype html>\n" +
                "<html>\n" +
                "\n" +
                "<head>\n" +
                "  <meta name=\"viewport\" content=\"width=device-width\" />\n" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "  <title>Simple Email HTML</title>\n" +
                "\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Montserrat:400,700|Poppins:400,700\" rel=\"stylesheet\">\n" +
                "\n" +
                "  <style>\n" +
                "    /* -------------------------------------\n" +
                "          GLOBAL RESETS\n" +
                "      ------------------------------------- */\n" +
                "\n" +
                "    /*All the styling goes here*/\n" +
                "\n" +
                "    img {\n" +
                "      border: none;\n" +
                "      -ms-interpolation-mode: bicubic;\n" +
                "      max-width: 100%;\n" +
                "    }\n" +
                "\n" +
                "    body {\n" +
                "      background-color: #dbdbdb;\n" +
                "      font-family: 'Poppins', 'Helvetica', sans-serif;\n" +
                "      -webkit-font-smoothing: antialiased;\n" +
                "      font-size: 15px;\n" +
                "      line-height: 28px;\n" +
                "      letter-spacing: .5px;\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "      -ms-text-size-adjust: 100%;\n" +
                "      -webkit-text-size-adjust: 100%;\n" +
                "    }\n" +
                "\n" +
                "    table {\n" +
                "      border-collapse: separate;\n" +
                "      mso-table-lspace: 0pt;\n" +
                "      mso-table-rspace: 0pt;\n" +
                "      width: 100%;\n" +
                "    }\n" +
                "\n" +
                "    table td {\n" +
                "      font-family: 'Poppins', 'Helvetica', sans-serif;\n" +
                "      font-size: 15px;\n" +
                "      line-height: 28px;\n" +
                "      letter-spacing: .5px;\n" +
                "      text-align: left;\n" +
                "      color: #6F6F6F;\n" +
                "    }\n" +
                "\n" +
                "    /* -------------------------------------\n" +
                "          BODY & CONTAINER\n" +
                "      ------------------------------------- */\n" +
                "\n" +
                "    .body {\n" +
                "      background-color: #dbdbdb;\n" +
                "      width: 100%;\n" +
                "    }\n" +
                "\n" +
                "    /* Set a max-width, and make it display as block so it will automatically stretch to that width, but will also shrink down on a phone or something */\n" +
                "\n" +
                "    .container {\n" +
                "      display: block;\n" +
                "      Margin: 0 auto !important;\n" +
                "      /* makes it centered */\n" +
                "      max-width: 580px;\n" +
                "      padding: 10px;\n" +
                "      width: 580px;\n" +
                "    }\n" +
                "\n" +
                "    /* This should also be a block element, so that it will fill 100% of the .container */\n" +
                "\n" +
                "    .content {\n" +
                "      box-sizing: border-box;\n" +
                "      display: block;\n" +
                "      Margin: 0 auto;\n" +
                "      max-width: 580px;\n" +
                "      padding: 10px;\n" +
                "    }\n" +
                "\n" +
                "    /* -------------------------------------\n" +
                "          HEADER, FOOTER, MAIN\n" +
                "      ------------------------------------- */\n" +
                "\n" +
                "    .main {\n" +
                "      background: #ffffff;\n" +
                "      border-radius: 3px;\n" +
                "      width: 100%;\n" +
                "    }\n" +
                "\n" +
                "    .wrapper {\n" +
                "      box-sizing: border-box;\n" +
                "      padding: 20px;\n" +
                "    }\n" +
                "\n" +
                "    .content-block {\n" +
                "      padding-bottom: 10px;\n" +
                "      padding-top: 10px;\n" +
                "    }\n" +
                "\n" +
                "    .footer {\n" +
                "      clear: both;\n" +
                "      Margin-top: 10px;\n" +
                "      text-align: center;\n" +
                "      width: 100%;\n" +
                "    }\n" +
                "\n" +
                "    .footer td,\n" +
                "    .footer p,\n" +
                "    .footer span,\n" +
                "    .footer a {\n" +
                "      color: #000000;\n" +
                "      font-size: 14px;\n" +
                "      text-align: center;\n" +
                "    }\n" +
                "\n" +
                "    /* -------------------------------------\n" +
                "          TYPOGRAPHY\n" +
                "      ------------------------------------- */\n" +
                "\n" +
                "    h1 {\n" +
                "      font-family: 'Montserrat', 'Verdana', sans-serif;\n" +
                "      font-size: 30px;\n" +
                "      font-weight: bold;\n" +
                "      line-height: 42px;\n" +
                "      text-align: left;\n" +
                "      color: #000000;\n" +
                "      padding-bottom: 15px !important;\n" +
                "    }\n" +
                "\n" +
                "    h2 {\n" +
                "      font-family: 'Montserrat', 'Verdana', sans-serif;\n" +
                "      font-size: 24px;\n" +
                "      font-weight: bold;\n" +
                "      line-height: 32px;\n" +
                "      text-align: left;\n" +
                "      color: #000000;\n" +
                "      padding-bottom: 15px !important;\n" +
                "    }\n" +
                "\n" +
                "    h3 {\n" +
                "      font-family: 'Montserrat', 'Verdana', sans-serif;\n" +
                "      font-size: 20px;\n" +
                "      font-weight: bold;\n" +
                "      line-height: 28px;\n" +
                "      text-align: left;\n" +
                "      color: #000000;\n" +
                "      padding-bottom: 15px !important;\n" +
                "    }\n" +
                "\n" +
                "    p,\n" +
                "    ul,\n" +
                "    ol {\n" +
                "      font-family: 'Poppins', 'Helvetica', sans-serif;\n" +
                "      font-size: 15px;\n" +
                "      font-weight: normal;\n" +
                "      margin: 0;\n" +
                "      margin-bottom: 15px;\n" +
                "    }\n" +
                "\n" +
                "    p li,\n" +
                "    ul li,\n" +
                "    ol li {\n" +
                "      list-style-position: inside;\n" +
                "      margin-left: 5px;\n" +
                "    }\n" +
                "\n" +
                "    a {\n" +
                "      color: #39b54a;\n" +
                "      text-decoration: underline;\n" +
                "    }\n" +
                "\n" +
                "    /* -------------------------------------\n" +
                "          BUTTONS\n" +
                "      ------------------------------------- */\n" +
                "\n" +
                "    .btn {\n" +
                "      box-sizing: border-box;\n" +
                "      width: 100%;\n" +
                "    }\n" +
                "\n" +
                "    .btn>tbody>tr>td {\n" +
                "      padding-bottom: 15px;\n" +
                "    }\n" +
                "\n" +
                "    .btn table {\n" +
                "      width: auto;\n" +
                "    }\n" +
                "\n" +
                "    .btn table td {\n" +
                "      background-color: #ffffff;\n" +
                "      border-radius: 5px;\n" +
                "      text-align: center;\n" +
                "    }\n" +
                "\n" +
                "    .btn a {\n" +
                "      background-color: #ffffff;\n" +
                "      border: solid 1px #39b54a;\n" +
                "      border-radius: 5px;\n" +
                "      box-sizing: border-box;\n" +
                "      color: #39b54a;\n" +
                "      cursor: pointer;\n" +
                "      display: inline-block;\n" +
                "      font-size: 15px;\n" +
                "      font-weight: bold;\n" +
                "      margin: 0;\n" +
                "      padding: 12px 25px;\n" +
                "      text-decoration: none;\n" +
                "      text-transform: capitalize;\n" +
                "    }\n" +
                "\n" +
                "    .btn-primary table td {\n" +
                "      background-color: #39b54a;\n" +
                "    }\n" +
                "\n" +
                "    .btn-primary a {\n" +
                "      background-color: #39b54a;\n" +
                "      border-color: #39b54a;\n" +
                "      color: #ffffff;\n" +
                "    }\n" +
                "\n" +
                "    /* -------------------------------------\n" +
                "          OTHER STYLES THAT MIGHT BE USEFUL\n" +
                "      ------------------------------------- */\n" +
                "\n" +
                "    .last {\n" +
                "      margin-bottom: 0;\n" +
                "    }\n" +
                "\n" +
                "    .first {\n" +
                "      margin-top: 0;\n" +
                "    }\n" +
                "\n" +
                "    .align-center {\n" +
                "      text-align: center;\n" +
                "    }\n" +
                "\n" +
                "    .align-right {\n" +
                "      text-align: right;\n" +
                "    }\n" +
                "\n" +
                "    .align-left {\n" +
                "      text-align: left;\n" +
                "    }\n" +
                "\n" +
                "    .clear {\n" +
                "      clear: both;\n" +
                "    }\n" +
                "\n" +
                "    .mt0 {\n" +
                "      margin-top: 0;\n" +
                "    }\n" +
                "\n" +
                "    .mb0 {\n" +
                "      margin-bottom: 0;\n" +
                "    }\n" +
                "\n" +
                "    .preheader {\n" +
                "      color: transparent;\n" +
                "      display: none;\n" +
                "      height: 0;\n" +
                "      max-height: 0;\n" +
                "      max-width: 0;\n" +
                "      opacity: 0;\n" +
                "      overflow: hidden;\n" +
                "      mso-hide: all;\n" +
                "      visibility: hidden;\n" +
                "      width: 0;\n" +
                "    }\n" +
                "\n" +
                "    .powered-by a {\n" +
                "      text-decoration: none;\n" +
                "    }\n" +
                "\n" +
                "    hr {\n" +
                "      border: 0;\n" +
                "      border-bottom: 1px solid #dbdbdb;\n" +
                "      Margin: 20px 0;\n" +
                "    }\n" +
                "\n" +
                "    /* -------------------------------------\n" +
                "          RESPONSIVE AND MOBILE FRIENDLY STYLES\n" +
                "      ------------------------------------- */\n" +
                "\n" +
                "    @media only screen and (max-width: 620px) {\n" +
                "      table[class=body] h1 {\n" +
                "        font-size: 28px !important;\n" +
                "        margin-bottom: 10px !important;\n" +
                "      }\n" +
                "      table[class=body] p,\n" +
                "      table[class=body] ul,\n" +
                "      table[class=body] ol,\n" +
                "      table[class=body] td,\n" +
                "      table[class=body] span,\n" +
                "      table[class=body] a {\n" +
                "        font-size: 16px !important;\n" +
                "      }\n" +
                "      table[class=body] .wrapper,\n" +
                "      table[class=body] .article {\n" +
                "        padding: 10px !important;\n" +
                "      }\n" +
                "      table[class=body] .content {\n" +
                "        padding: 0 !important;\n" +
                "      }\n" +
                "      table[class=body] .container {\n" +
                "        padding: 0 !important;\n" +
                "        width: 100% !important;\n" +
                "      }\n" +
                "      table[class=body] .main {\n" +
                "        border-left-width: 0 !important;\n" +
                "        border-radius: 0 !important;\n" +
                "        border-right-width: 0 !important;\n" +
                "      }\n" +
                "      table[class=body] .btn table {\n" +
                "        width: 100% !important;\n" +
                "      }\n" +
                "      table[class=body] .btn a {\n" +
                "        width: 100% !important;\n" +
                "      }\n" +
                "      table[class=body] .img-responsive {\n" +
                "        height: auto !important;\n" +
                "        max-width: 100% !important;\n" +
                "        width: auto !important;\n" +
                "      }\n" +
                "    }\n" +
                "\n" +
                "    /* -------------------------------------\n" +
                "          PRESERVE THESE STYLES IN THE HEAD\n" +
                "      ------------------------------------- */\n" +
                "\n" +
                "    @media all {\n" +
                "      .ExternalClass {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "      .ExternalClass,\n" +
                "      .ExternalClass p,\n" +
                "      .ExternalClass span,\n" +
                "      .ExternalClass font,\n" +
                "      .ExternalClass td,\n" +
                "      .ExternalClass div {\n" +
                "        line-height: 100%;\n" +
                "      }\n" +
                "      .apple-link a {\n" +
                "        color: inherit !important;\n" +
                "        font-family: inherit !important;\n" +
                "        font-size: inherit !important;\n" +
                "        font-weight: inherit !important;\n" +
                "        line-height: inherit !important;\n" +
                "        text-decoration: none !important;\n" +
                "      }\n" +
                "      .btn-primary table td:hover {\n" +
                "        background-color: #2b8838 !important;\n" +
                "      }\n" +
                "      .btn-primary a:hover {\n" +
                "        background-color: #2b8838 !important;\n" +
                "        border-color: #2b8838 !important;\n" +
                "      }\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "\n" +
                "<body class=\"\">\n" +
                "  <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\">\n" +
                "    <tr>\n" +
                "      <td>&nbsp;</td>\n" +
                "      <td class=\"container\">\n" +
                "        <div class=\"footer\">\n" +
                "          <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "            <tr>\n" +
                "              <td class=\"content-block\">\n" +
                "                <a ><img src=\"http://imagens-bombapath-games.s3.amazonaws.com/82imagem0.jpg\" alt=\"Brazukas\" align=\"center\" style=\"display:block;float:none;margin:0 auto;max-width:200px;outline:0;\"></a>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "        </div>\n" +
                "        <div class=\"content\">\n" +

                "\n" +
                "          <!-- START CENTERED WHITE CONTAINER -->\n" +
                "          <span class=\"preheader\">Sua compra foi efetuada com sucesso!.</span>\n" +
                "          <table role=\"presentation\" class=\"main\">\n" +
                "\n" +
                "            <!-- START MAIN CONTENT AREA -->\n" +
                "            <tr>\n" +
                "              <td class=\"wrapper\">\n" +
                "                <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                  <tr>\n" +
                "                    <td>\n" +
                "                      <h2>Parabéns "+name+"</h2>\n" +
                "                      <p>Sua compra foi efetuada com sucesso!.</p>\n" +
                        "                      <h2>Pedido: "+pedido+"</h2>\n" +

                        "<a ><img src=\"http://imagens-bombapath-games.s3.amazonaws.com/81imagem0.jpg\" alt=\"Brazukas\" align=\"center\" style=\"display:block;float:none;margin:0 auto;max-width:200px;outline:0;\"></a>\n" +
                "\n" +
                "          <!-- START FOOTER -->\n" +
                "          <div class=\"footer\">\n" +
                "            <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "              <tr>\n" +
                "                <td class=\"content-block powered-by\">\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "              <tr>\n" +
                "                <td class=\"content-block\">\n" +
                "                  <span class=\"apple-link\">Esse email foi enviado por Brazukas Technology,<br>Avenida paulista 800, São Paulo</span>\n" +
                "                  <br> Copyright © 2021 Brazukas Technology\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "              <tr>\n" +
                "                <td class=\"content-block powered-by\">\n" +
                "                  <a href=\"mailto:brazukastechnology@gmail.com\" target=\"_blank\" style=\"font-weight:bold\"><img title=\"Email\" src=\"https://hoiqh.stripocdn.email/content/assets/img/other-icons/logo-black/mail-logo-black.png\" alt=\"Email\" width=\"32\" height=\"32\" style=\"display:inline-block;border:0;outline:none;text-decoration:none;\"></a>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </div>\n" +
                "          <!-- END FOOTER -->\n" +
                "\n" +
                "          <!-- END CENTERED WHITE CONTAINER -->\n" +
                "        </div>\n" +
                "      </td>\n" +
                "      <td>&nbsp;</td>\n" +
                "    </tr>\n" +
                "  </table>\n" +
                "</body>\n" +
                "\n" +
                "</html>\n" +
                "\n" +
                "\n" +
                "\n" ;

        return email;
    }
}
