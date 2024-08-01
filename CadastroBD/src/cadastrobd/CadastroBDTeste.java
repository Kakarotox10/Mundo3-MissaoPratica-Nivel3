/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd;

import cadastrobd.model.util.ConectorBD;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author gilva
 */
public class CadastroBDTeste {
    
      public static void main(String[] args) {
        ConectorBD conector = new ConectorBD();
        try {
            Connection connection = conector.getConnection();
            
            // Verifica se a conexão está aberta
            if (connection != null && !connection.isClosed()) {
                System.out.println("A conexão está aberta.");
            } else {
                System.out.println("A conexão está fechada.");
            }
            
            
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
            conector.close();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}
