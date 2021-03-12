package br.com.brazukas.Models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private int _id;
    private String _name;
    private String _surname;
    private String _login;
    private String _password;
    private String _permission;
}
