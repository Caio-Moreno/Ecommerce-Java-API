package br.com.brazukas.Models;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DashMaisVendidos {
    private String _produto;
    private int _quantidade;
}
