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
    
    public Cliente(String nome, char sexo, String telefone, String email, Date datadenascimento, String endereco_rua, String endereco_bairro, String endereco_numero, String endereco_complemento, String endereco_cep, String endereco_cidade, String endereco_estado) {
        super(nome, sexo, telefone, email, datadenascimento, endereco_rua, endereco_bairro, endereco_numero, endereco_complemento, endereco_cep, endereco_cidade, endereco_estado);
    }
    
}
