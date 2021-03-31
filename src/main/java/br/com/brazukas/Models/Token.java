package br.com.brazukas.Models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Token {
    private int _idToken;
    private String _token;
    private String _dataExpiracao;
}
