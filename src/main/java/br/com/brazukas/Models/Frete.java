package br.com.brazukas.Models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Frete {
    private int _id;
    private String _UF;
    private double _valorFast;
    private double _valorNormal;
}
