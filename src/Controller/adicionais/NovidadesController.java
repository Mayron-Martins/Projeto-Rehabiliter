/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.Utilitarios;
import View.Paineis.Novidades;

/**
 *
 * @author Mayro
 */
public class NovidadesController {
    private final Novidades view;
    private final Utilitarios utilitarios = new Utilitarios();

    public NovidadesController(Novidades view) {
        this.view = view;
    }
    
    public void notasAtualizacao(){
        String novidades = "\u07CBAdicionada Tela de Reposições de Aulas;\n"
                + "\u07CBAdicionada Tela de Histórico de Reposições de Aulas;\n"
                + "\u07CBAtalhos Adicionados em Todas as Telas;\n"
                + "\u07CBBotões de Informações Adicionados;\n"
                + "\u07CBPainel de Configurações Adicionais em Alunos Reformulado;\n"
                + "\u07CBAdicionadas Funções de Reativação ou Cadastro de Plano;\n"
                + "\u07CBAdicionado Painel de Observações em Alunos;\n"
                + "\u07CBAdicionada Função de Encerramento ou Reativação de Turmas;\n"
                + "\u07CBAdicionada Função de Encerramento ou Reativação de Serviços;\n"
                + "\u07CBAdicionada Função de Desvinculamento ou Recontratação de Funcionários;\n"
                + "\u07CBTabela de Vencimentos de Planos Adicionada na Tela Inicial;\n"
                + "\u07CBPossibilitando Login Utilizando Enter;\n"
                + "\u07CBCorreção de Bugs nas Telas Existentes;";
        view.getCampoNovidades().setText("");
        view.getCampoNovidades().append(novidades);
    }
    
    public void desabilitarAtualização(){
        boolean habilitar = view.getCampoVerificacao().isSelected();
        if(habilitar){
            utilitarios.testeNotes();
        }
        view.dispose();
    }
    
}
