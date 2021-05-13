package br.com.brazukas.Util;

import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Utils {
    public static void printarMinhaConsulta(PreparedStatement minhaString){
        System.out.println("Minha consulta -->>"+minhaString);
    }

    public static  void printarNaTela(String texto){
        System.out.println("INFO -->>"+texto);
    }

    public static void printarErro(String texto){
        System.err.println("ERROR -->>"+texto);
    }

    public static String gerarNumPedido(){
        SimpleDateFormat d = new SimpleDateFormat("yyyyMMdd-HH:mm:ss");
        String data = ""+d.format(new Date());

        Random gerador = new Random();

        for (int i = 0; i < 4; i++) {
            data += gerador.nextInt(9);
        }




        data = data.replaceAll("[-:.]", "");
        printarNaTela("Num pedido-->>>"+data);
        return data;


    }


}
