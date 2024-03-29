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
    private static String _connectionString = "jdbc:mysql://10.211.55.16:3306/BRAZUKAS?useTimezone=true&serverTimezone=UTC&useSSL=false";
    private static String _username ="root";
    private static String _password = "Theblunt15.02";

    public ConexaoDb() throws IOException {
        gravaLog("Conexão -->" + _connectionString, "ConexaoDb", Level.INFO);
    }

    public static Connection getConnection() throws IOException {
        if (connection != null)
            return connection;
        else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(_connectionString, _username, _password);
                gravaLog("Conectou", "ConexaoDb", Level.INFO);
                System.out.println("Conectou -->>>>>>>>>>>>");

            }
        	catch (SQLException e) {
                System.out.println("Erro na conexao -->>>>>>>>>>>>"+e.getMessage());
            } catch (IOException e) {
                System.out.println("Erro na conexao -->>>>>>>>>>>>"+e.getMessage());
            }
            catch (Exception e){
                System.out.println("Erro na conexao -->>>>>>>>>>>>"+e.getMessage());
            }
            return connection;
        }

    }
}



