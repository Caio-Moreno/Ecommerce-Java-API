package br.com.brazukas.controller.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private String _user;
    private String _password;
}
