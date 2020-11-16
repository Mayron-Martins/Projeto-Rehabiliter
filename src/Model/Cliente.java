/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author Mayro
 */
public class Cliente extends Pessoa{
    
    public Cliente(int codBanco, String nome, String cpf, String rg, String telefone, String celular, String email, Date datadenascimento) {
        super(codBanco, nome, cpf, rg, telefone, celular, email, datadenascimento);
    }
    
    
    
}
