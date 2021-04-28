package br.com.brazukas.Models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EnderecoAlterar {
    private String _cep;
    private String _logradouro;
    private int _numero;
    private String _complemento;
    private String _bairro;
    private String _cidade;
    private String _estado;
    private String _tipo;
    private int _id;
}
