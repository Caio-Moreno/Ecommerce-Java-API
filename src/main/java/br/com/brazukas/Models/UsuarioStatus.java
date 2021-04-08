package br.com.brazukas.Models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioStatus {
    private int _ativo;
    private int _inativo;
}
