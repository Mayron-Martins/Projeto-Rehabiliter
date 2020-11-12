/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.auxiliar;

import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/**
 *
 * @author Mayro
 */
public final class FormatacaodeCamposRestritos extends JTextField{
    private int maximoCaracteres=-1;// definição de -1 
// como  valor normal de um textfield sem limite de caracters

    //Formatação para Somente Números sem Restrição de Espaço
    public FormatacaodeCamposRestritos() {
        super();
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
    public void keyTyped(java.awt.event.KeyEvent evt) {
        jTextFieldKeyTypedNumber(evt);}});
    }
 
    // //Formatação para Somente Números sem Restrição de Espaço
public FormatacaodeCamposRestritos(int maximo) {    
    super();
    setMaximoCaracteres(maximo);// define o tamanho máximo
//que deve ser aceito no jtextfield que foi recebido no  construtor
   
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
    public void keyTyped(java.awt.event.KeyEvent evt) {
        jTextFieldKeyTypedNumber(evt);}});
    }

public FormatacaodeCamposRestritos(int maximo, int nulo) {    
    super();
    setMaximoCaracteres(maximo);// define o tamanho máximo
//que deve ser aceito no jtextfield que foi recebido no  construtor
   
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
    public void keyTyped(java.awt.event.KeyEvent evt) {
        jTextFieldKeyTyped(evt);}});
    }


private void jTextFieldKeyTypedNumber(KeyEvent evt) {
       
String caracteres="0987654321";// lista de caracters que não devem ser aceitos
if(!caracteres.contains(evt.getKeyChar()+"")){// se o caracter que gerou o evento não estiver na lista
evt.consume();//aciona esse propriedade para eliminar a ação do evento
}
if((getText().length()>=getMaximoCaracteres())&&(getMaximoCaracteres()!=-1)){
//if para saber se precisa verificar também o tamanho da string do campo
// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
evt.consume();
setText(getText().substring(0,getMaximoCaracteres()));
// esta linha acima é para remover os caracters inválidos caso o usuário tenha copiado o //conteúdo de algum lugar, ou seja, com tamanho maior que o definido
}//fim do if do tamanho da string do campo
 
        }

private void jTextFieldKeyTyped(KeyEvent evt) {
       
String caracteres="@.#%&*()[]^´`~";// lista de caracters que não devem ser aceitos
if(caracteres.contains(evt.getKeyChar()+"")){// se o caracter que gerou o evento não estiver na lista
evt.consume();//aciona esse propriedade para eliminar a ação do evento
}
if((getText().length()>=getMaximoCaracteres())&&(getMaximoCaracteres()!=-1)){
//if para saber se precisa verificar também o tamanho da string do campo
// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
evt.consume();
setText(getText().substring(0,getMaximoCaracteres()));
// esta linha acima é para remover os caracters inválidos caso o usuário tenha copiado o //conteúdo de algum lugar, ou seja, com tamanho maior que o definido
}//fim do if do tamanho da string do campo
}
    public int getMaximoCaracteres() {
        return maximoCaracteres;
    }
    public void setMaximoCaracteres(int maximoCaracteres) {
        this.maximoCaracteres = maximoCaracteres;
    }

/*    
private void jTextFieldKeyTypedNumberAndSinal(KeyEvent evt) {
       
String caracteres="0987654321.";// lista de caracters que não devem ser aceitos
if(!caracteres.contains(evt.getKeyChar()+"")){// se o caracter que gerou o evento não estiver na lista
evt.consume();//aciona esse propriedade para eliminar a ação do evento
}
if((getText().length()>=getMaximoCaracteres())&&(getMaximoCaracteres()!=-1)){
//if para saber se precisa verificar também o tamanho da string do campo
// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
evt.consume();
setText(getText().substring(0,getMaximoCaracteres()));
// esta linha acima é para remover os caracters inválidos caso o usuário tenha copiado o //conteúdo de algum lugar, ou seja, com tamanho maior que o definido
}//fim do if do tamanho da string do campo
 
        }*/

}
