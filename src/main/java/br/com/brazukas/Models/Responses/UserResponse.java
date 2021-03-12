package br.com.brazukas.Models.Responses;

import br.com.brazukas.Models.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponse {
    private int _codigo;
    private String _message;
    private List<User> _users;

}
