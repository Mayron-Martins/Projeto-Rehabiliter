/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.auxiliar;

/**
 *
 * @author Mayro
 */
public class EnderecoFuncionario extends Endereco{
    private int codFuncionario;
    public EnderecoFuncionario(int codBanco, int codFuncionario, String logradouro, String bairro, String numero, String complemento, String referencia, String cidade, String estado, String cep) {
        super(codBanco, logradouro, bairro, numero, complemento, referencia, cidade, estado, cep);
        this.codFuncionario = codFuncionario;
    }

    public int getCodFuncionario() {
        return codFuncionario;
    }
    
}
