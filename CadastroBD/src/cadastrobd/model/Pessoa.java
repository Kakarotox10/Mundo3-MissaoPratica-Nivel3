/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd.model;

/**
 *
 * @author gilvan
 */
public class Pessoa {
    
    private Integer idPessoa;
    private String nome;
    private String endereco;
    private String cidade;
    private String estado;
    private String telefone;
    private String email;
    
    
    public Pessoa() {
        
    }

    public Pessoa(Integer id, String nome, String endereco, String cidade,
        String estado, String telefone, String email) {
        this.idPessoa = id;
        this.nome = nome;
        this.endereco = endereco;
        this.cidade = cidade;
        this.estado = estado;
        this.telefone = telefone;
        this.email = email;
    }
    
    
    public void exibir() {
        System.out.println(this);
    }

    public Integer getId() {
        return idPessoa;
    }

    public void setId(Integer id) {
        this.idPessoa = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        String output = "id: ".concat(idPessoa.toString());
        output = output.concat("\nnome: ".concat(nome));
        output = output.concat("\nendereco: ".concat(endereco));
        output = output.concat("\ncidade: ".concat(cidade));
        output = output.concat("\nestado: ".concat(estado));
        output = output.concat("\ntelefone: ".concat(telefone));
        output = output.concat("\nemail: ".concat(email));
        return output;
    }
    
}
