package br.com.brazukas.Util;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static br.com.brazukas.Util.CriarArquivoDeLog.gravaLog;


public class ConexaoDb {

    private static final Logger meuLog = Logger.getLogger( ConexaoDb.class.getName() );

    private static Connection connection = null;
    private static String _connectionString = "jdbc:mysql://10.211.55.16:3306/brazukas?useTimezone=true&serverTimezone=UTC&useSSL=false";
    private static String _username ="cmoreno";
    private static String _password = "Theblunt15.02";

    public ConexaoDb() throws IOException {





        gravaLog("Conexão -->" + _connectionString, "ConexaoDb", Level.INFO);
    }

    public static Connection getConnection() throws IOException {
        if (connection != null)
            return connection;
        else {
            try {
                //DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                //Class.forName("com.mysql.jdbc.Driver");
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(_connectionString, _username, _password);
                gravaLog("Conectou", "ConexaoDb", Level.INFO);

            }// catch (ClassNotFoundException e) {
               //gravaLog("Erro na conexão"+ e.getMessage(), "ConexaoDb", Level.SEVERE);

            //} 
        	catch (SQLException e) {
                gravaLog("Erro na SQLException->"+ e.getMessage() + "SQL STATE -->"+e.getSQLState()+ "CAUSA"+e.getCause(), "ConexaoDb", Level.SEVERE);
            } catch (IOException e) {
                gravaLog("Erro na IOException"+ e.getMessage(), "ConexaoDb", Level.SEVERE);
            }
            catch (Exception e){
                gravaLog("Erro na Exception"+ e.getMessage(), "ConexaoDb", Level.SEVERE);
            }
            return connection;
        }

    }

    //funcao que pega os dados do webConfig
    public static Properties getProp() throws IOException
    {
        Properties props = new Properties();
        FileInputStream file = new FileInputStream("./Properties/WebConfig.properties");
        props.load(file);
        return props;
    }
}



