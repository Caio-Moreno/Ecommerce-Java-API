package br.com.brazukas.Models;

import br.com.brazukas.enums.StatusEnum;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserAtualizar {
    private int _id;
    private StatusEnum _status;
}
