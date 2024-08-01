/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd.model.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

/**
 *
 * @author gilvan
 */
public class ConectorBD {
    
    private static final String URL = "jdbc:sqlserver://localhost\\GILVANPCDELL:1433;databaseName=loja;encrypt=true;trustServerCertificate=true";
    private static final String USER = "loja";
    private static final String PASSWORD = "loja";

    public Connection getConnection() throws SQLException {
        
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao obter conexao com o banco de dados: " + e.getMessage());
            }
        }
        return conn;
    }

    
    
    
    private Connection conn = null; 
    private PreparedStatement stmt;
    private ResultSet rs;
     
     
      public PreparedStatement getPrepared(String sql) throws SQLException {
        stmt = getConnection().prepareStatement(sql);
        return stmt;
    }


    
    
     public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        Connection connection = getConnection();
        return connection.prepareStatement(sql);
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            return resultSet;
        } finally {
            close(statement);
        }
    }

    public void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Conexção SQL Server Fechada!");
            }
        } catch (SQLException e) {
           System.out.println(e);
        }
    }

    public void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void close(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void close() throws SQLException {
        if (stmt != null && !stmt.isClosed()) {
            stmt.close();
        }
        if (rs != null && !rs.isClosed()) {
            rs.close();
        }
        if (conn != null && !conn.isClosed()) {
            conn.close();
            System.out.println("Conexão com banco Close!");
            
        }
    }
    
}
