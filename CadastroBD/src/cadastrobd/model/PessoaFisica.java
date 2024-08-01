/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd.model;

/**
 *
 * @author gilva
 */
public class PessoaFisica extends Pessoa  {
    
    private String cpf;

    public PessoaFisica() {
        
    }

    public PessoaFisica(Integer id, String nome, String endereco, String cidade,
        String estado, String telefone, String email, String cpf) {
        super(id, nome, endereco, cidade, estado, telefone, email);
        this.cpf = cpf;
    }
    
    @Override
    public void exibir() {
        System.out.println(this);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    @Override
    public String toString() {
        String output = super.toString();
        output = output.concat("\nCPF: ".concat(cpf));
        return output;
    }
       
}
