/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import cadastrobd.model.util.ConectorBD;

        
/**
 * @author gilvan
 */
public class PessoaJuridicaDAO {
    
   
    
    public PessoaJuridicaDAO() {
        
    }
    
    public PessoaJuridica getPessoa(Integer id) throws SQLException {
        String sql = "SELECT pj.id_pessoa, pj.cnpj, p.nome, p.endereco, p.cidade, p.estado, p.telefone, p.email "
            + "FROM pessoa_juridica pj "
            + "INNER JOIN Pessoa p ON pj.id_pessoa = p.id_pessoa "
            + "WHERE pj.id_pessoa = ?";
        ConectorBD connector = new ConectorBD();
        try (Connection con = connector.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PessoaJuridica(
                        rs.getInt("id_pessoa"),
                        rs.getString("nome"),
                        rs.getString("endereco"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("cnpj")
                    );
                }
            }
        }
        return null;
    }

    public ArrayList<PessoaJuridica> getPessoas() throws SQLException {
        ArrayList<PessoaJuridica> list = new ArrayList<>();
        String sql = "SELECT pj.id_pessoa, pj.cnpj, p.nome, p.endereco, p.cidade, p.estado, p.telefone, p.email "
            + "FROM pessoa_juridica pj "
            + "INNER JOIN Pessoa p ON pj.id_pessoa = p.id_pessoa";
        ConectorBD connector = new ConectorBD();
        try (Connection con = connector.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new PessoaJuridica(
                    rs.getInt("id_pessoa"),
                    rs.getString("nome"),
                    rs.getString("endereco"),
                    rs.getString("cidade"),
                    rs.getString("estado"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("cnpj")));
            }
        }
        return list;
    }
    
    public int incluir(PessoaJuridica pj) throws SQLException {
        if (pj.getNome() == null || pj.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("'nome' cannot be empty or null.");
        }
        String sqlInsertPessoa = "INSERT INTO Pessoa(nome, endereco, cidade, estado, telefone, email) VALUES(?, ?, ?, ?, ?, ?)";
        String sqlInsertPessoaJuridica = "INSERT INTO pessoa_juridica(id_pessoa, cnpj) VALUES(?, ?)";
        ConectorBD connector = new ConectorBD();
        try (Connection con = connector.getConnection();
            PreparedStatement stmtPessoa = con.prepareStatement(sqlInsertPessoa,Statement.RETURN_GENERATED_KEYS)) {
            String[] pfArray = {"", pj.getNome(), pj.getEndereco(), pj.getCidade(), pj.getEstado(), pj.getTelefone(), pj.getEmail()};
            for(int i = 1; i < 7; i++) {
                stmtPessoa.setString(i, pfArray[i]);
            }
            if (stmtPessoa.executeUpdate() != 0) {
                System.out.println("INSERT INTO PessoaJuridica success.");
            }
            else {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmtPessoa.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idNovaPessoa = generatedKeys.getInt(1);
                    pj.setId(idNovaPessoa);
                    try (PreparedStatement stmtPessoaFisica = con.prepareStatement(sqlInsertPessoaJuridica,Statement.RETURN_GENERATED_KEYS)) {
                        stmtPessoaFisica.setInt(1, idNovaPessoa);
                        stmtPessoaFisica.setString(2, pj.getCnpj());
                        stmtPessoaFisica.executeUpdate();
                    }
                    return idNovaPessoa;
                } else {
                    throw new SQLException("Creating user failed. No ID obtained.");
                }
            }
        }
    }
    
    public void alterar(PessoaJuridica pj) throws SQLException {
        String sqlUpdatePessoa = "UPDATE Pessoa SET nome = ?, endereco = ?, cidade = ?, estado = ?, telefone = ?, email = ? WHERE id_pessoa = ?;";
        String sqlUpdatePessoaJuridica = "UPDATE pessoa_juridica SET cnpj = ? WHERE id_pessoa = ?;";
        ConectorBD connector = new ConectorBD();
        try (Connection con = connector.getConnection();
            PreparedStatement stmtPessoa = con.prepareStatement(sqlUpdatePessoa,Statement.RETURN_GENERATED_KEYS);
            PreparedStatement stmtPessoaJuridica = con.prepareStatement(sqlUpdatePessoaJuridica,Statement.RETURN_GENERATED_KEYS)) {
            String[] pfArray = {"", pj.getNome(), pj.getEndereco(), pj.getCidade(), pj.getEstado(), pj.getTelefone(), pj.getEmail()};
            for(int i = 1; i < 7; i++) {
                stmtPessoa.setString(i, pfArray[i]);
            }
            stmtPessoa.setInt(7, pj.getId());
            stmtPessoa.executeUpdate();
            stmtPessoaJuridica.setString(1, pj.getCnpj());
            stmtPessoaJuridica.setInt(2, pj.getId());
            stmtPessoaJuridica.executeUpdate();
        }
    }
    
    public void excluir(PessoaJuridica pj) throws SQLException {
        String sqlDeletePessoaJuridica = "DELETE FROM pessoa_juridica WHERE id_pessoa = ?;";
        String sqlDeletePessoa = "DELETE FROM Pessoa WHERE id_pessoa = ?;";
        ConectorBD connector = new ConectorBD();
        try (Connection con = connector.getConnection();
             PreparedStatement stmtPessoaJuridica = con.prepareStatement(sqlDeletePessoaJuridica,Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtPessoa = con.prepareStatement(sqlDeletePessoa,Statement.RETURN_GENERATED_KEYS)) {
            stmtPessoaJuridica.setInt(1, pj.getId());
            stmtPessoaJuridica.executeUpdate();
            stmtPessoa.setInt(1, pj.getId());
            stmtPessoa.executeUpdate();
        }
    }   

    
}