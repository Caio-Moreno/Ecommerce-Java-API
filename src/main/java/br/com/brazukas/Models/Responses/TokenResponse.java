package br.com.brazukas.Models.Responses;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TokenResponse {
    private  int _codigo;
    private String _mensagem;
    private String _endpoint;
    private String _path;
}
