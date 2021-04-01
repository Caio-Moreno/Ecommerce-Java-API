package br.com.brazukas.Models;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private int _id;
    private String _login;
    private String _password;
    private String _permission;
}
