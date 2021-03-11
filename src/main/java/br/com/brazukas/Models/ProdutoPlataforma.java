package br.com.brazukas.Models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProdutoPlataforma {
    private int _ps4;
    private  int _pc;
    private int _xbox;
    private int _ps5;
    private int _outros;
}
