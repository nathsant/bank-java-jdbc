package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    public static void main(String[] args){
        try{
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bank_sesi?user=postgres&password=root");

            System.out.println("Consegui conectar");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
