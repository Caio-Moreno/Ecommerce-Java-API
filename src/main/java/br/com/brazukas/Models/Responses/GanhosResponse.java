package br.com.brazukas.Models.Responses;


import br.com.brazukas.Models.Ganhos;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GanhosResponse {
    private int _codigo;
    private String _message;
    private List<Ganhos> _ganhos;

}
