/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd;

import java.sql.SQLException;
import java.util.ArrayList;

import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaFisicaDAO;
import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.PessoaJuridicaDAO;

import java.util.logging.Logger;
import java.util.logging.Level;


/**
 *
 * @author gilvan
 */
public class CadastroBDTeste {
    
    private static final Logger LOGGER = Logger.getLogger(CadastroBDTeste.class.getName());
    
    private final PessoaFisicaDAO pfDao;
    private final PessoaJuridicaDAO pjDao;
    
    public CadastroBDTeste() {
        pfDao = new PessoaFisicaDAO();
        pjDao = new PessoaJuridicaDAO();
    }
    
    private void run() {
        PessoaFisica pf = new PessoaFisica(null, "Gilvan Junior", "Rua Violeta, 86", "Manaus",
            "AM", "9999-9999", "gilvanx10@gmail.com", "01773587005");

        if (pf.getNome() == null || pf.getNome().trim().isEmpty()) {
            System.out.println("o campo nome não pode ser vazio!");
            return; 
        }
        
        try {
            System.out.println("---------------------------------");
            System.out.println("Pessoa Fisica incluida com ID: " + pfDao.incluir(pf));
            System.out.println("---------------------------------");
            pf.exibir();
            pf.setNome("Gilvan Junior N. Goncalves");
            pf.setCidade("Rio de Janeiro");
            pf.setEstado("RJ");
            pfDao.alterar(pf);
            System.out.println("---------------------------------");
            System.out.println("Pessoa Fisica alterada.");
            pf.exibir();
            ArrayList<PessoaFisica> listaPf = pfDao.getPessoas();
            System.out.println("---------------------------------");
            System.out.println("Exibir todas as pessoas fisicas:");
            for (PessoaFisica pessoa : listaPf) {
                System.out.println("---------------------------------");
                pessoa.exibir();
            }
            System.out.println("---------------------------------");
            pfDao.excluir(pf);
            System.out.println("---------------------------------");
            System.out.println("Pessoa Fisica excluida.");
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        PessoaJuridica pj = new PessoaJuridica(null, "Copy Story","Rua G, 55 Bairro: Lirio do Vale", "Manaus",
            "AM", "8888-8888", "copystory@yahoo.com", "25041878000104");

        if (pj.getNome() == null || pj.getNome().trim().isEmpty()) {
            System.out.println("o campo nome não pode ser vazio!");
            return; 
        }
        
        try {
            System.out.println("---------------------------------");
            System.out.println("Pessoa Juridica incluida com ID: " + pjDao.incluir(pj));
            System.out.println("---------------------------------");
            pj.exibir();
            pj.setNome("Copy Story 2D");
            pj.setCidade("Belo Horizonte");
            pj.setEstado("MG");
            pjDao.alterar(pj);
            System.out.println("---------------------------------");
            System.out.println("Pessoa Juridica alterada.");
            pj.exibir();
            ArrayList<PessoaJuridica> listaPj = pjDao.getPessoas();
            System.out.println("---------------------------------");
            System.out.println("Exibir todas as pessoas juridicas:");
            for (PessoaJuridica pessoa : listaPj) {
                System.out.println("---------------------------------");
                pessoa.exibir();
            }
            System.out.println("---------------------------------");
            pjDao.excluir(pj);
            System.out.println("---------------------------------");
            System.out.println("Pessoa Juridica excluida.");
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        
    }
    
    public static void main(String[] args) {
        new CadastroBDTeste().run();
    }    
      
}
