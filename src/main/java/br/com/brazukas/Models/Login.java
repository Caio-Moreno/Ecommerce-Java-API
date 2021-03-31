package br.com.brazukas.Models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Login {
    private int _id;
    private String _login;
    private String _password;
    private String _permission;
    private String _token;
    private int _idCliente;
}
