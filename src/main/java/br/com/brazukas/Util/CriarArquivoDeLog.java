package br.com.brazukas.Util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CriarArquivoDeLog {

    private static final Logger meuLog = Logger.getLogger( CriarArquivoDeLog.class.getName() );

    public static void gravaLog(String log, String Classe, Level levelError) throws IOException {
        String logMontado = "Log -->"+ log + "  Classe -->"+ Classe;

//        meuLog.log(levelError, logMontado);
    }

}
