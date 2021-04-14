package br.com.brazukas.Util;

import java.sql.PreparedStatement;

public class Utils {
    public static void printarMinhaConsulta(PreparedStatement minhaString){
        System.out.println("Minha consulta -->>"+minhaString);
    }

    public static  void printarNaTela(String texto){
        System.out.println("INFO -->>"+texto);
    }

    public static void printarErro(String texto){
        System.out.println("ERROR -->>"+texto);
    }
}
