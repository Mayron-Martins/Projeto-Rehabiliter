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
public class EnderecoAlunos extends Endereco{
    private int codAluno;

    public EnderecoAlunos(int codBanco, int codAluno, String logradouro, String bairro, String numero, String complemento, String referencia, String cidade, String estado, String cep) {
        super(codBanco, logradouro, bairro, numero, complemento, referencia, cidade, estado, cep);
        this.codAluno = codAluno;
    }

    public EnderecoAlunos(String logradouro, String bairro, String numero, String complemento, String referencia, String cidade, String estado, String cep) {
        super(logradouro, bairro, numero, complemento, referencia, cidade, estado, cep);
    }
    

    public int getCodAluno() {
        return codAluno;
    }
    
}
