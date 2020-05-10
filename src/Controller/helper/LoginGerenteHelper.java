/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.helper;

import Model.Funcionario;
import View.LoginGerente;

/**
 *
 * @author André Lucas
 */
public class LoginGerenteHelper {
    
    
 private final LoginGerente view;
    public LoginGerenteHelper(LoginGerente view)
    {
        
    this.view=view;
   
    } 
    
    //obter os campos digitados na senha e colocar no funcionario
    public Funcionario obterModelo()
    {
          //PEGAR O USUARIO DIGITADO NA VIEW     
        String usuario =view.getCampoUsuarioGerente().getText();
   //PEGAR A SENHA DIGITADA NA VIEW     
        //String.valueOf transforma o char retornado de getPassowrd para String
        String senha= String.valueOf(view.getCampoSenhaGerente().getPassword());
        //System.out.println(senha);
        //verificação se há campos digitados
        if("".equals(senha)&&"".equals(usuario))
        {
        camposVazios();
        return null;
        }
        else
        {    
        Funcionario modelo = new Funcionario(senha, usuario, 0);
        
        return modelo;
        }
    }
//________________________________________________________________________________________________________________________________    
    //adicionar um usuario ou senha já existente no sistema
    public void setarModelo(Funcionario modelo)
    {
       String usuario= modelo.getUsuario();
       String senha = modelo.getSenha();
       
       view.getCampoUsuarioGerente().setText(usuario);
       view.getCampoSenhaGerente().setText(senha);
    }
//______________________________________________________________________________________________________________________
   //limpar campos 
    public void limparTela()
    {
     view.getCampoUsuarioGerente().setText("");
     view.getCampoSenhaGerente().setText("");
    }
//__________________________________________________________________________________________________________________________
    public void camposVazios()
    {
    this.view.exibemensagem("Campos vazios, digite seu usuário e senha");
    }
    
//___________________________________________________________________________________________________________________    
    public void acessoNegado()
    {
    this.view.exibemensagem("usuário ou senha incorretos");
    
    }
//_________________________________________________________________________________________________________________________________


    
    
}
