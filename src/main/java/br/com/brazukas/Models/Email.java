package br.com.brazukas.Models;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Email {
    private String _email;
    private String _assunto;
    private String _mensagem;
}
