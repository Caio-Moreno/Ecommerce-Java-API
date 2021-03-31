package br.com.brazukas.Models.Responses;

import br.com.brazukas.Models.Login;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LogoutResponse {
    private int _codigo;
    private String _message;
}
