package br.com.brazukas.Models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClienteAlterar {
    private String _nome;
    private String _password;
    private String _sexo;
    private String _telefone;
    private String _cpf;
}
