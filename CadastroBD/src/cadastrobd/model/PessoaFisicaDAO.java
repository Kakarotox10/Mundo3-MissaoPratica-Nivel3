/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd.model;

import cadastrobd.model.util.ConectorBD;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author gilvan
 */
public class PessoaFisicaDAO {
    

    public PessoaFisicaDAO() {
        
    }
    
    
    public PessoaFisica getPessoa(Integer id) throws SQLException {
        
        String sql = "SELECT pf.id_pessoa, pf.cpf, p.nome, p.endereco, p.cidade, p.estado, p.telefone, p.email "
            + "FROM pessoa_fisica pf "
            + "INNER JOIN Pessoa p ON pf.id_pessoa = p.id_pessoa "
            + "WHERE pf.id_pessoa = ?";
        ConectorBD connector = new ConectorBD();
        try (Connection con = connector.getConnection(); 
            PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PessoaFisica(
                        rs.getInt("id_pessoa"),
                        rs.getString("nome"),
                        rs.getString("endereco"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("cpf")
                    );
                }
            }
        }
        return null;
    }

    public ArrayList<PessoaFisica> getPessoas() throws SQLException {
        ArrayList<PessoaFisica> list = new ArrayList<>();
        String sql = "SELECT pf.id_pessoa, pf.cpf, p.nome, p.endereco, p.cidade, p.estado, p.telefone, p.email "
                + "FROM pessoa_fisica pf "
                + "INNER JOIN Pessoa p ON pf.id_pessoa = p.id_pessoa";
        ConectorBD connector = new ConectorBD();
        try (Connection con = connector.getConnection(); PreparedStatement stmt =
            con.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new PessoaFisica(
                    rs.getInt("id_pessoa"),
                    rs.getString("nome"),
                    rs.getString("endereco"),
                    rs.getString("cidade"),
                    rs.getString("estado"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("cpf")));
            }
        }
        return list;
    }
    
    public int incluir(PessoaFisica pf) throws SQLException {
        
        if (pf.getNome() == null || pf.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("o campo [Nome] não pode ser vazio!");
        }
        String sqlInsertPessoa = "INSERT INTO Pessoa(nome, endereco, cidade, estado, telefone, email) VALUES(?, ?, ?, ?, ?, ?)";
        String sqlInsertPessoaFisica = "INSERT INTO pessoa_fisica(id_pessoa, cpf) VALUES(?, ?)";

        ConectorBD connector = new ConectorBD();                
        try (Connection con = connector.getConnection(); PreparedStatement stmtPessoa = con.prepareStatement(sqlInsertPessoa, Statement.RETURN_GENERATED_KEYS)) {
            
            String[] pfArray = {"", pf.getNome(), pf.getEndereco(), pf.getCidade(), pf.getEstado(), pf.getTelefone(), pf.getEmail()};
            for(int i = 1; i < 7; i++) {
                stmtPessoa.setString(i, pfArray[i]);
            }
            if (stmtPessoa.executeUpdate() != 0) {
                System.out.println("Registro Cadastrado com Sucesso!");
            } else {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            
            
            try (ResultSet generatedKeys = stmtPessoa.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idNovaPessoa = generatedKeys.getInt(1);
                    pf.setId(idNovaPessoa);
                    try (PreparedStatement stmtPessoaFisica = con.prepareStatement(sqlInsertPessoaFisica, Statement.RETURN_GENERATED_KEYS)) {
                        stmtPessoaFisica.setInt(1, idNovaPessoa);
                        stmtPessoaFisica.setString(2, pf.getCpf());
                        stmtPessoaFisica.executeUpdate();
                    }
                    return idNovaPessoa;
                } else {
                    throw new SQLException("Creating user failed. No ID obtained.");
                }
            }
        }
    }

    public void alterar(PessoaFisica pf) throws SQLException {
        
        String sqlUpdatePessoa = "UPDATE Pessoa SET nome = ?, endereco = ?, cidade = ?, estado = ?, telefone = ?, email = ? WHERE id_pessoa = ?;";
        String sqlUpdatePessoaFisica = "UPDATE pessoa_fisica SET cpf = ? WHERE id_pessoa = ?;";
        
        ConectorBD connector = new ConectorBD();
        
        try (
            Connection con = connector.getConnection();
            PreparedStatement stmtPessoa = con.prepareStatement(sqlUpdatePessoa);
            PreparedStatement stmtPessoaFisica = con.prepareStatement(sqlUpdatePessoaFisica)) {
            String[] pfArray = {"", pf.getNome(), pf.getEndereco(), pf.getCidade(), pf.getEstado(), pf.getTelefone(), pf.getEmail()};
            for(int i = 1; i < 7; i++) {
                stmtPessoa.setString(i, pfArray[i]);
            }
            stmtPessoa.setInt(7, pf.getId());
            stmtPessoa.executeUpdate();
            stmtPessoaFisica.setString(1, pf.getCpf());
            stmtPessoaFisica.setInt(2, pf.getId());
            stmtPessoaFisica.executeUpdate();
        }
    }

    public void excluir(PessoaFisica pf) throws SQLException {
        String sqlDeletePessoaFisica = "DELETE FROM pessoa_fisica WHERE id_pessoa = ?;";
        String sqlDeletePessoa = "DELETE FROM Pessoa WHERE id_pessoa = ?;";
        ConectorBD connector = new ConectorBD();
        try (Connection con = connector.getConnection(); PreparedStatement stmtPessoaFisica = con.prepareStatement(sqlDeletePessoaFisica); PreparedStatement stmtPessoa = con.prepareStatement(sqlDeletePessoa)) {
            stmtPessoaFisica.setInt(1, pf.getId());
            stmtPessoaFisica.executeUpdate();
            stmtPessoa.setInt(1, pf.getId());
            stmtPessoa.executeUpdate();
        }
    }
    
}
