package br.com.brazukas.controller.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginDto {
    private String _username;
    private String _password;
}
