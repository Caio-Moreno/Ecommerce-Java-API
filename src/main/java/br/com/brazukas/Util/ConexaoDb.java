package br.com.brazukas.Util;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Properties;

public class ConexaoDb {

    private static Connection connection = null;
    private static String _connectionString;
    private static String _username;
    private static String _password;

    public ConexaoDb() throws IOException {
        Properties prop = getProp();

        _connectionString = prop.getProperty("prop.connectionString");
        _username = prop.getProperty("prop.username");
        _password = prop.getProperty("prop.password");
    }

    public static Connection getConnection() {
        if (connection != null)
            return connection;
        else {
            try {
                Properties prop = new Properties();
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(_connectionString, _username, _password);

            } catch (ClassNotFoundException e) {
                System.out.println("estou aqui");

            } catch (SQLException e) {

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



