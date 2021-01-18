/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.auxiliar.ConversaoDeDinheiro;
import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.ExportarArquivos;
import Controller.auxiliar.ImpressaoComponentes;
import Controller.auxiliar.LogsSystem;
import Controller.auxiliar.VerificarCodigoNoBanco;
import Dao.AlunosDao;
import Dao.DetOrcamentarioDao;
import Dao.FuncionarioDao;
import Dao.LogAçoesFuncionarioDao;
import Dao.PlanosDao;
import Dao.ProdutosDao;
import Dao.ServicosDao;
import Dao.TurmasDao;
import Dao.VendasDao;
import Model.Aluno;
import Model.Funcionario;
import Model.Produtos;
import Model.Vendas;
import Model.auxiliar.DetOrcamentario;
import Model.auxiliar.ItemVendido;
import Model.auxiliar.LogAçoesFuncionario;
import Model.auxiliar.Planos;
import Model.auxiliar.Servicos;
import Model.auxiliar.Turmas;
import View.Caixa;
import static java.lang.Thread.sleep;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mayro
 */
public class CaixaController {
    private final Caixa view;
    private final DefaultTableModel tabelaDeClientes;
    private final DefaultTableModel tabelaDeProdutos;
    private final DefaultTableModel tabelaDeCarrinho;
    private final DefaultTableModel tabelaDeMensalidade;
    private final AlunosDao alunosDao = new AlunosDao();
    private final TurmasDao turmasDao = new TurmasDao();
    private final ProdutosDao produtosDao = new ProdutosDao();
    private final PlanosDao planosDao = new PlanosDao();
    private final ServicosDao servicos = new ServicosDao();
    private final VendasDao vendasDao = new VendasDao();
    private final DetOrcamentarioDao orcamentarioDao = new DetOrcamentarioDao();
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    private final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    private final VerificarCodigoNoBanco verificador = new VerificarCodigoNoBanco();
    private final ImpressaoComponentes imprimirComprovante = new ImpressaoComponentes();
    private final ExportarArquivos exportarComprovante = new ExportarArquivos();

    public CaixaController(Caixa view) {
        this.view = view;
        this.tabelaDeClientes = (DefaultTableModel) view.getTabelaDeClientes().getModel();
        this.tabelaDeProdutos = (DefaultTableModel) view.getTabelaDeProdutos().getModel();
        this.tabelaDeCarrinho = (DefaultTableModel) view.getTabelaDeCarrinho().getModel();
        this.tabelaDeMensalidade = (DefaultTableModel) view.getTabelaMensalidade().getModel();
    }
    
        //Limpar tabela
    public void limparTabelaClientes(){
        int quantLinhas = this.view.getTabelaDeClientes().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDeClientes.removeRow(0);
        }
    }
    
    public void limparTabelaProdutos(){
        int quantLinhas = this.view.getTabelaDeProdutos().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDeProdutos.removeRow(0);
        }
    }
       
    public void limparTabelaCarrinho(){
        int quantLinhas = this.view.getTabelaDeCarrinho().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDeCarrinho.removeRow(0);
        }
    }
    
    public void limparTabelaMensalidade(){
        int quantLinhas = this.view.getTabelaMensalidade().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDeMensalidade.removeRow(0);
        }
    }
    
    //Funções para Tratamento de Clientes
    public void setarTabelaClientes(){
        String alunoPesquisa = view.getCampoCliente().getText();
        ArrayList <Aluno> alunos = alunosDao.pesquisarPorNome(alunoPesquisa);
        if(alunoPesquisa.equals("")){listarAlunos();}
        else{this.buscasClientes(alunos);}        
    }
    
    public void listarAlunos(){
        ArrayList <Aluno> alunos = this.alunosDao.selecionarTodosAlunos();
        this.buscasClientes(alunos);
    }
    
    private void buscasClientes(ArrayList <Aluno> listar){
        ArrayList<Turmas> turmas;
        ArrayList <Aluno> alunos = listar;


        if(alunos==null){view.exibeMensagem("Cliente Não Encontrado!"); limparTabelaClientes();}
        else{
            limparTabelaClientes();
            for(int linhas = 0; linhas<alunos.size(); linhas++){
            turmas = this.turmasDao.pesquisarTurmas("SELECT * FROM tblTurmas WHERE codTurma = "+
                    alunos.get(linhas).getTurma());

            //Inserindo dados na tabela de alunos
            Object[] dadosDaTabelaAlunos = {alunos.get(linhas).getCodBanco(), 
            alunos.get(linhas).getNome(),turmas.get(0).getCodBanco()+"."+turmas.get(0).getNomeTurma()};
            this.tabelaDeClientes.addRow(dadosDaTabelaAlunos);
            }
        } 
    }
    
    //Fim das Funções para Clientes
    
    //Funções para Tratamento de Produtos
    //Buscar Produtos no campo de busca
    public void setarTabelaProdutos(){
        if(view.getCampoProdutoNome().isVisible()){
            String produtoPesquisa = view.getCampoProdutoNome().getText();
            if(produtoPesquisa.equals("")){listarProdutos();}
            else{
                ArrayList <Produtos> produtos = produtosDao.pesquisarPorNome(produtoPesquisa);
                this.buscasProdutos(produtos);}
        }

        else{
            String produtoPesquisa = view.getCampoProdutoCodigo().getText();
            if(produtoPesquisa.equals("")){listarProdutos();}
            else{ArrayList <Produtos> produtos = produtosDao.pesquisarPorID(produtoPesquisa);
            this.buscasProdutos(produtos);}
        }     
    }    
    
    public void listarProdutos(){
        ArrayList <Produtos> produtos= this.produtosDao.selecionarTodosProdutos();
        this.buscasProdutos(produtos);
    }
    
    private void buscasProdutos(ArrayList <Produtos> listar){
        ArrayList<Produtos> produtos = listar; 

        if(produtos==null){view.exibeMensagem("Produto Não Encontrado!"); limparTabelaProdutos();}
        else{
            limparTabelaProdutos();
            for(int linhas = 0; linhas<produtos.size(); linhas++){
            Object[] dadosDaTabelaFuncionarios = {produtos.get(linhas).getCodBanco(), 
            produtos.get(linhas).getNomeProduto(), produtos.get(linhas).getValorDeVenda(),
            produtos.get(linhas).getQuantidade()};
            this.tabelaDeProdutos.addRow(dadosDaTabelaFuncionarios);
            }
        }
    }
    
    //Fim das Funções para Clientes
    
    //Setar Tabela de Carrinho
    public void setarCarrinho(){
    BigDecimal quantidade = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoQuantidade().getText()).toString());
    int linhaSelecionada = view.getTabelaDeProdutos().getSelectedRow();
        if(linhaSelecionada != -1 && quantidade.compareTo(BigDecimal.ZERO)>=1){
            BigDecimal quantBanco = new BigDecimal(converterDinheiro.converterParaBigDecimal(tabelaDeProdutos.getValueAt(linhaSelecionada, 3).toString()).toString());
            if(quantidade.compareTo(quantBanco)!=1){
                int codBanco = Integer.parseInt(tabelaDeProdutos.getValueAt(linhaSelecionada, 0).toString());
                String produto = tabelaDeProdutos.getValueAt(linhaSelecionada, 1).toString();
                BigDecimal valor = new BigDecimal(converterDinheiro.converterParaBigDecimal(tabelaDeProdutos.getValueAt(linhaSelecionada, 2).toString()).toString());
                BigDecimal subtotal = quantidade.multiply(valor);
                subtotal = subtotal.setScale(2, RoundingMode.UP);

                Object[] dadosDoCarrinho = {codBanco, produto, valor, quantidade, subtotal};
                tabelaDeCarrinho.addRow(dadosDoCarrinho);
                this.adicionarTotal();
            }
            else{
                view.exibeMensagem("Quantidade Solicitada Maior Que Em Estoque!");
            }
            
        }
    }
    //Remover produto Carrinho
    public void removerProduto(){
        int linhaSelecionada = view.getTabelaDeCarrinho().getSelectedRow();
        if(linhaSelecionada!=-1){
          tabelaDeCarrinho.removeRow(linhaSelecionada);
          this.adicionarTotal();
        }
    }
    
    //Adicionar Total
    private void adicionarTotal(){
        int totalLinhas = view.getTabelaDeCarrinho().getRowCount();
        if(totalLinhas>0){
            BigDecimal valorTotal = new BigDecimal("0");
            BigDecimal valorTabela;
            BigDecimal valorDesconto = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoVDesconto().getText()).toString());
            for(int linhas=0; linhas<totalLinhas; linhas++){
                valorTabela = new BigDecimal(converterDinheiro.converterParaBigDecimal(tabelaDeCarrinho.getValueAt(linhas, 4).toString()).toString());
                valorTotal = valorTotal.add(valorTabela);
            }
            valorTotal = valorTotal.setScale(2, RoundingMode.UP);
            valorTotal = valorTotal.subtract(valorDesconto);
            view.getCampoVTotal().setText(valorTotal.toString());
        }
        else{
            view.getCampoVTotal().setText("");
        }
    }
    
    //Adicionar Desconto
    public void adicionarDesconto(){
        BigDecimal valorDesconto = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoVDesconto().getText()).toString());
        BigDecimal valorTotal = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoVTotal().getText()).toString());
        
        if(valorDesconto.compareTo(valorTotal)!=1){
        this.adicionarTotal();
        }
        else{
            view.exibeMensagem("Desconto Maior do Que o Valor Total!");
        }
       
    }
    
    //Adicionar valor Pago
    public void adicionarValorPago(){
         BigDecimal valorTotal = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoVTotal().getText()).toString());
         BigDecimal valorPago;
         if(view.getCampoDinheiro().isEnabled()){
             valorPago = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoDinheiro().getText()).toString());
         }
         else{
             valorPago = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoParcelamento().getText()).toString());
         }
         
         if(valorPago.compareTo(valorTotal)>=0){
             view.getCampoVPago().setText(valorPago.toString());
             this.adicionarTroco();
             view.getQuantParcelas().setText("");
             view.getjPanelFormaDePagamento().setVisible(false);
         }
         else{
             if(view.getCampoParcelas().isEnabled()){
                 view.getCampoVPago().setText(valorPago.toString());
                 view.getCampoVTroco().setText("");
                 view.getQuantParcelas().setText("");
                 view.getQuantParcelas().setText(view.getCampoParcelas().getText()+" X");
                 view.getjPanelFormaDePagamento().setVisible(false);
             }
             else{
                 view.exibeMensagem("Valor Pago Insuficiente!");
             }
         }
    }
    
    //Adicionar Troco
    private void adicionarTroco(){
        BigDecimal valorPago = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoVPago().getText()).toString());
        BigDecimal valorTotal = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoVTotal().getText()).toString());
        
        BigDecimal novoValor = valorPago.subtract(valorTotal);
        novoValor = novoValor.setScale(2, RoundingMode.UP);
            view.getCampoVTroco().setText(novoValor.toString());
    }
    
    //Setar Tabela de Mensalidade
    public void setarTabelaMensalidade(){
        limparTabelaMensalidade();
        int linhaSelecionada = view.getTabelaDeClientes().getSelectedRow();
        int codBanco = Integer.parseInt(tabelaDeClientes.getValueAt(linhaSelecionada, 0).toString());
        
        Aluno aluno = alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+codBanco).get(0);
        Planos plano = planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE codAluno = "+codBanco+" AND situacao != 'Encerrado'").get(0);
        Servicos servico = servicos.pesquisarServicos("SELECT * FROM tblServicos WHERE codServico = "+plano.getCodServico()).get(0);
        
        this.mensalidade(aluno, servico, plano);
    }
    
    public void setarTotalMensalidade(){
        int linhaSelecionada = view.getTabelaMensalidade().getSelectedRow();
        BigDecimal valorMensal = converterDinheiro.converterParaBigDecimal(tabelaDeMensalidade.getValueAt(linhaSelecionada, 3).toString());
        
        if(linhaSelecionada!=-1){
            String situacao = tabelaDeMensalidade.getValueAt(linhaSelecionada, 5).toString();
            if(!situacao.equals("Pago")||!situacao.equals("Encerrado")){
            view.getCampoVTotal().setText(valorMensal.toString());
            }
            else{
                if(situacao.equals("Pago")){
                  view.exibeMensagem("Fatura já foi Paga!");  
                }
                else{
                  view.exibeMensagem("Plano Encerrado!");    
                }
            }
        }
    }
    
    public void finalizarVenda(){
        try{
            BigDecimal valorPago = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoVPago().getText()).toString());
            if(valorPago.compareTo(BigDecimal.ZERO)!=0 && this.retornarCliente()!=-1){
                int codVenda = verificador.verificarUltimo("tblVendas", "codVenda")+1;
                int codCliente = 0;
                BigDecimal valorTotal = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoVTotal().getText()).toString());
                BigDecimal valorTroco = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoVTroco().getText()).toString());

                LocalDate dataAtual = LocalDate.now();
                Date dataVenda = converterData.conversaoLocalforDate(dataAtual);

                String formaDePagamento = this.retornarFormaPagamento();
                int codAluno = this.retornarCliente();

                int parcelas =1;
                if(view.getCampoParcelas().isEnabled()){
                    parcelas = Integer.parseInt(view.getCampoParcelas().getText());
                }


                Vendas venda;
                ArrayList<ItemVendido> itens = new ArrayList<>();

                long chaveVenda;
                int codProduto;
                int chavePlano=0;
                float quantidade;
                BigDecimal valor;
                BigDecimal subtotal;

                //Venda Comum
                if(view.getPainelTabelaProdutos().isVisible()){
                    venda = new Vendas(codVenda, codCliente, codAluno, chavePlano, valorTotal, valorPago, valorTroco, dataVenda, formaDePagamento, this.tipoVenda(), parcelas);
                    chaveVenda = venda.getChaveVenda();
                    int quantLinhas = view.getTabelaDeCarrinho().getRowCount();
                    int codOrcamentario = verificador.verificarUltimo("tblDetOrcamentario", "codBanco")+1;
                    for(int linhas=0; linhas<quantLinhas;linhas++){
                        codProduto = Integer.parseInt(tabelaDeCarrinho.getValueAt(linhas, 0).toString());
                        quantidade = converterDinheiro.converterParaBigDecimal(tabelaDeCarrinho.getValueAt(linhas, 3).toString()).floatValue();
                        valor = converterDinheiro.converterParaBigDecimal(tabelaDeCarrinho.getValueAt(linhas, 2).toString());
                        subtotal = converterDinheiro.converterParaBigDecimal(tabelaDeCarrinho.getValueAt(linhas, 4).toString());

                        Produtos produto = produtosDao.pesquisarPorID(String.valueOf(codProduto)).get(0);


                        ItemVendido itemVendido = new ItemVendido(chaveVenda, codProduto, quantidade, valor, subtotal);
                        itens.add(itemVendido);
                        produtosDao.atualizarEstoque(codProduto, produto.getQuantidade()-quantidade);

                    ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
                    if(funcionarios!=null){
                        String acao = "Venda Efetuada";
                        String descricao = "Venda no Valor de R$"+valorTotal+" com "+formaDePagamento+" ao cliente "+codAluno;
                        this.setarLogAcoesFuncionario(funcionarios, acao, descricao);
                    }
                    }

                    vendasDao.inserirDados(venda, itens);
                    LocalDate dataParcelamentos = converterData.conversaoLocalforDate(dataVenda);
                    for(int linhas=0; linhas<parcelas;linhas++){
                        dataParcelamentos = dataParcelamentos.plusMonths(1);
                        dataVenda = converterData.conversaoLocalforDate(dataParcelamentos);
                        DetOrcamentario orcamentario = new DetOrcamentario(codOrcamentario++, "Vendas", formaDePagamento, valorPago, dataVenda, chaveVenda);
                        orcamentarioDao.inserirDados(orcamentario);
                    }

                    this.posVenda(venda, valorTotal);
                }

                //Pagamento Plano
                else{
                    int totalLinhas = view.getTabelaMensalidade().getRowCount();
                    if(totalLinhas>0){
                        int codOrcamentario = verificador.verificarUltimo("tblDetOrcamentario", "codBanco")+1;
                        chavePlano = Integer.parseInt(tabelaDeMensalidade.getValueAt(0, 0).toString());

                        //Pegando Informações do Banco
                        Planos planoAntigo = planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE chavePlano = "+chavePlano).get(0);
                        Servicos servico = servicos.pesquisarServicos("SELECT * FROM tblServicos WHERE codServico = "+planoAntigo.getCodServico()).get(0); 
                        Aluno aluno = alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+codAluno).get(0);
                        BigDecimal debitos = new BigDecimal(aluno.getDebito().toString());

                        //Construindo a venda
                        venda = new Vendas(codVenda, codCliente, codAluno, chavePlano, valorTotal, valorPago, valorTroco, dataVenda, formaDePagamento, this.tipoVenda(), parcelas);
                        chaveVenda = venda.getChaveVenda();


                        Date dataParaUso = converterData.parseDate(converterData.parseDate(aluno.getDataCadastro()));
                        if(planoAntigo.getDataRenovacao() != null){
                           dataParaUso = converterData.parseDate(converterData.parseDate(planoAntigo.getDataRenovacao()));
                        }
                        LocalDate dataFim = converterData.conversaoLocalforDate(dataParaUso).plusDays(servico.getPeriodDays());
                        Date dataFimPlano = converterData.conversaoLocalforDate(dataFim);

                        codProduto = 0;
                        quantidade = 1;
                        valor = valorTotal;
                        subtotal = valor;

                        ItemVendido itemVendido = new ItemVendido(chaveVenda, codProduto, quantidade, valor, subtotal);
                        itens.add(itemVendido);

                        Planos plano;
                        Date dataVencimento = this.dataVencimento(aluno, servico, planoAntigo);
                        if(dataFim.isEqual(dataAtual)||dataFim.isBefore(dataAtual)){
                            if(aluno.getRenovacaoAutomatica()==1){
                                plano = new Planos(chavePlano, codAluno, 0, 0, 0, dataVencimento, dataVenda, null, dataFimPlano, "Pago"); 
                            }
                            else{
                                plano = new Planos(chavePlano, codAluno, 0, 0, 0, dataVencimento, dataVenda, null, null, "Encerrado"); 
                            }

                        }
                        else{
                            plano = new Planos(chavePlano, codAluno, 0, 0, 0,dataVencimento, dataVenda, null, planoAntigo.getDataRenovacao(), "Pago"); 
                        }

                        if(debitos.compareTo(BigDecimal.ZERO)!=0){
                            if(debitos.subtract(valor).compareTo(BigDecimal.ZERO)>=0){
                                vendasDao.inserirDados(venda, itens);
                                LocalDate dataParcelamentos = converterData.conversaoLocalforDate(dataVenda);
                                for(int linhas=0; linhas<parcelas;linhas++){
                                    dataParcelamentos = dataParcelamentos.plusMonths(1);
                                    dataVenda = converterData.conversaoLocalforDate(dataParcelamentos);
                                    DetOrcamentario orcamentario = new DetOrcamentario(codOrcamentario, "Planos", formaDePagamento, valorPago, dataVenda, chaveVenda);
                                    codOrcamentario++;
                                    orcamentarioDao.inserirDados(orcamentario);
                                }
                                planosDao.atualizarSituacao(plano);
                                alunosDao.atualizarDebitos(codAluno, debitos.subtract(valor));

                            ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
                            if(funcionarios!=null){
                                String acao = "Pagamento de Plano";
                                String descricao = "Pagamento do plano do aluno "+aluno.getNome()+" da turma "+aluno.getTurma()+" no valor de "+valorTotal;
                                this.setarLogAcoesFuncionario(funcionarios, acao, descricao);
                            }
                            }
                            else{
                                view.exibeMensagem("Erro, verifique se o Valor Total corresponde ao Contrato!");
                            }
                        }
                        else{
                            view.exibeMensagem("Aluno Sem Débitos!");
                        }
                    }

                }
            }
            else{
                view.exibeMensagem("Adicione uma Forma de Pagamento!");
            }
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    //Método ao Final da Venda
    private void posVenda(Vendas venda, BigDecimal valorTotal){
        //Impressão ou Não do Comprovante e nova venda
        BigDecimal valorDesconto = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoVDesconto().getText()).toString());
        view.exibeMensagem("Venda Concluída!");

        int confirmacao = JOptionPane.showConfirmDialog(null, "Deseja realizar a impressão do comprovante?", 
                "Nota", JOptionPane.YES_NO_OPTION);
        if(confirmacao == JOptionPane.YES_OPTION){
           String subtotalVenda = valorTotal.add(valorDesconto).toString();
           String desconto = view.getCampoVDesconto().getText();
           this.imprimirComprovanteVenda(venda, subtotalVenda, desconto);
        }
        else{
            this.novaVenda();
        }     
    }
    
    private String retornarFormaPagamento(){
        if(view.getCampoDinheiro().isEnabled()){
            return "Dinheiro";
        }
        else{
            if(view.getAlternarCredito().isSelected()){
                return "Crédito";
            }
            else{
                return "Débito";
            }
        }
    }
    
    private int retornarCliente(){
        if(view.getAlternarClienteCadastrado().isSelected()){
            int linhaSelecionada = view.getTabelaDeClientes().getSelectedRow();
            if(linhaSelecionada!=-1){
                String codCliente = tabelaDeClientes.getValueAt(linhaSelecionada, 0).toString();
                return Integer.parseInt(codCliente);
            }
            else{
                return -1;
            }
        }
        else{
            return 0;
        }    
    }
    
    private String tipoVenda(){
        if(view.getPainelTabelaProdutos().isVisible()){
            return "N";
        }
        else{
            return "S";
        }
    }
    
    private LogAçoesFuncionario setarLogAcoesFuncionario(ArrayList <Funcionario> funcionarios, String acao, String descricao){
        Funcionario funcionario = funcionarios.get(0);
        Date dataEvento = new Date();
        LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, acao, descricao);
        return logAcao;
    }
    
    private void imprimirComprovanteVenda(Vendas venda,String subtotal, String desconto){
        try{
            //Dados da empresa
            String nomeEmpresa = "REHABILITER";
            String endereco = "Rua Botafogo, 150. Centro de Santa Inês-MA";
            String cnpj = "CNPJ: 30.854.735/0001-43";
            String contato = "Tel.: 3653-6694";
            String email= "rehabiliterfintess@gmail.com";
            String dadosEmpresa = "\t\t   "+nomeEmpresa+"\n\r     "
                    +endereco+"\n\t     "
                    +cnpj+"\n\t\t  "
                    +contato+"\n\t    "
                    +email+"\n\r"
                    + "---------------------------------------------------------\n\r";
            //String Divisória
            String divisoria = "\t\t  NOTA NÃO FISCAL\n\r"
                    + "---------------------------------------------------------\n\r";

            //Dados do Cliente
            String nomeCliente = "Cliente:";
            String cpf = "CPF:";
            if(venda.getCodAluno()==0){
                nomeCliente+=" Sem Cadastro";
                cpf+=" Não se Aplica";
            }
            else{
                AlunosDao alunoDao = new AlunosDao();
                Aluno aluno = alunoDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+venda.getCodAluno()).get(0);
                nomeCliente+= " "+aluno.getNome();
                if(aluno.getCpf()!=null){
                    cpf+=" "+aluno.getCpf();
                }
                else{
                    cpf+=" Não Possui";
                }
            }

            String dadosCliente = nomeCliente+"\n\r"+cpf+"\n\r---------------------------------------------------------\n\r";

            String cabecalho="";
            String dadosTabela="";

            if(venda.getPlano().equals("N")){
               String codHProduto = "COD";
               String descricaoHProduto = "DESCRIÇÃO";
               String quantidadeH = "QUANT";
               String valorH = "VALOR";
               cabecalho = codHProduto+"\t"+descricaoHProduto+"\t\t  "+quantidadeH+"\t  "+valorH+"\n\r";


               int linhasTabela = tabelaDeCarrinho.getRowCount();
               for(int linhas=0; linhas<linhasTabela; linhas++){

                   String produto = tabelaDeCarrinho.getValueAt(linhas, 1).toString();
                   if(produto.length()>20){
                       produto = produto.substring(0, 19);
                   }
                   else{
                       while(produto.length()<20){
                           produto+=" ";
                       }
                   }
                   dadosTabela+=tabelaDeCarrinho.getValueAt(linhas, 0)+"\t"
                           +produto+"\t      "
                           +tabelaDeCarrinho.getValueAt(linhas, 3)+"    R$ "
                           +tabelaDeCarrinho.getValueAt(linhas, 4)+"\n\r";
               }
            }
            else{
               String codHAluno = "COD";
               String descricaoHPlano = "PLANO";
               String vencimentoH = "VENCIMENTO";
               String valorH = "VALOR";
               cabecalho = codHAluno+"\t"+descricaoHPlano+"\t\t"+vencimentoH+"\t  "+valorH+"\n";

               int linhasTabela = tabelaDeMensalidade.getRowCount();
               for(int linhas=0; linhas<linhasTabela; linhas++){
                   String servico = tabelaDeMensalidade.getValueAt(linhas, 2).toString();
                   if(servico.length()>20){
                       servico = servico.substring(0, 19);
                   }
                   else{
                       while(servico.length()<20){
                           servico+=" ";
                       }
                   }

                   dadosTabela+=tabelaDeMensalidade.getValueAt(linhas, 0).toString()+"\t"
                           +servico+"\t   "
                           +tabelaDeMensalidade.getValueAt(linhas, 3).toString()+"  R$ "
                           +venda.getValorVenda()+"\n\r";
               }
            }
            //Dados Gerais da Venda
            String valorSubtotal = "SUBTOTAL \t\t\t\t\tR$"+subtotal+"\n\r";
            String descontot = "DESCONTO \t\t\t\t\tR$"+desconto+"\n\r";
            String valorTotal = "VALOR TOTAL\t\t\t\t\tR$"+venda.getValorVenda()+"\n\r";
            String valorPago = "PAGO \t\t\t\t\t     "+venda.getParcelas()+" X R$"+venda.getValorPago()+"\n\r";
            String valorTroco = "TROCO \t\t\t\t\t\tR$"+venda.getValorTroco()+"\n\r";

            Date dataComprovante = new Date();
            String data = "\t\t"+converterData.parseDateAndTime(dataComprovante);
            String despedida = "\n\n\t\t    VOLTE SEMPRE!";
            String espaco = "---------------------------------------------------------\n\r";

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
            String dataArquivo = sdf.format(dataComprovante);

            String comprovante = dadosEmpresa+divisoria+dadosCliente+cabecalho+espaco+dadosTabela+espaco+valorSubtotal+descontot
            +valorTotal+valorPago+valorTroco+espaco+data+despedida;
            exportarComprovante.geraArquivoTxt(comprovante, "C:/Rehabiliter/Info/Comprovantes/Comprovante dia "+dataArquivo+".txt");
            sleep(15);
            exportarComprovante.ConvertTxtToPDF("C:/Rehabiliter/Info/Comprovantes/documento.pdf", "C:/Rehabiliter/Info/Comprovantes/Comprovante dia "+dataArquivo+".txt");
            imprimirComprovante.impressao("C:/Rehabiliter/Info/Comprovantes/documento.pdf");


            novaVenda();
        } catch (InterruptedException ex) {
            gerarLog(ex);
        }
        
    }
    
    private void novaVenda(){
        limparTabelaClientes();
        limparTabelaProdutos();
        limparTabelaCarrinho();
        limparTabelaMensalidade();
        view.getCampoQuantidade().setText("");
        view.getCampoVTotal().setText("");
        view.getCampoVPago().setText("");
        view.getCampoVTroco().setText("");
        view.getCampoVDesconto().setText("");
        view.getCampoDinheiro().setText("");
        view.getCampoParcelamento().setText("");
        view.getQuantParcelas().setText("");
    }
    
    public void setarParcelas(){
        if(!view.getCampoParcelas().getText().equals("")&&!view.getCampoVTotal().getText().equals("")){
            BigDecimal parcelas = new BigDecimal(view.getCampoParcelas().getText());
            BigDecimal valorTotal = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoVTotal().getText()).toString());
            
            BigDecimal valorParcelamento = valorTotal.divide(parcelas, 2, RoundingMode.UP);
            valorParcelamento = valorParcelamento.setScale(2, RoundingMode.UP);
            view.getCampoParcelamento().setText(valorParcelamento.toString());
        }
    }
    
    private void mensalidade(Aluno aluno, Servicos servico, Planos plano){
        BigDecimal periodDays = new BigDecimal(servico.getPeriodDays());
        String nomeServico = plano.getCodServico()+"."+servico.getNome()+"-"+servico.getPeriodo();
        String situacao = plano.getSituacao();
        Date dataVencimento = plano.getDataVencimento();
        if(dataVencimento != null){
            int renovacaoAutomatica = aluno.getRenovacaoAutomatica();
            BigDecimal valorMensal = aluno.getValorMensal();
            
            if(servico.getPeriodDays()<30){
                if(renovacaoAutomatica == 1){
                    BigDecimal period = (new BigDecimal(30)).divide(periodDays,4, RoundingMode.UP);
                    valorMensal = valorMensal.divide(period, 4, RoundingMode.UP);
                    valorMensal = valorMensal.setScale(2, RoundingMode.UP); 
                }
            }
            Object[] dadosTabela = {plano.getChavePlano(), aluno.getNome(), nomeServico, valorMensal, dataVencimento, situacao};
            tabelaDeMensalidade.addRow(dadosTabela);
        }else{
            view.exibeMensagem("Adicione uma data de validade ao plano na tela de Alunos.");
        }
    }
    
    private Date dataVencimento(Aluno aluno, Servicos servico, Planos plano){
        BigDecimal periodDays = new BigDecimal(servico.getPeriodDays());
        Date vencimento = converterData.parseDate(converterData.parseDate(plano.getDataVencimento()));
        LocalDate dataVencimento = converterData.conversaoLocalforDate(vencimento);
        int diaVencimento = plano.getDiaVencimento();
        
        Date dataUsual = converterData.parseDate(converterData.parseDate(aluno.getDataCadastro()));
        if(plano.getDataRenovacao()!=null){
            dataUsual = converterData.parseDate(converterData.parseDate(plano.getDataRenovacao()));
        }
        
        LocalDate dataVencimentoFinal = converterData.conversaoLocalforDate(dataUsual).plusDays(servico.getPeriodDays());
        
        BigDecimal periodoMensal = periodDays.divide(new BigDecimal(30), 4, RoundingMode.UP);
        periodoMensal = periodoMensal.stripTrailingZeros();
        BigDecimal periodoAnual = periodDays.divide(new BigDecimal(365), 4, RoundingMode.UP);
        periodoAnual = periodoAnual.stripTrailingZeros();
        
        boolean mensal = periodoMensal.toPlainString().matches("[1-9]*");
        boolean anual = periodoAnual.toPlainString().matches("[1-9]*");
        
        if(aluno.getRenovacaoAutomatica()==1){
            if(mensal||anual){
                if(dataVencimento.plusMonths(1).getMonth().compareTo(Month.FEBRUARY)==0&&diaVencimento>28){
                    diaVencimento = 28;
                }
                dataVencimento = LocalDate.of(dataVencimento.plusMonths(1).getYear(), dataVencimento.plusMonths(1).getMonthValue(), diaVencimento);
                return converterData.conversaoLocalforDate(dataVencimento);
            }
            else{
                dataVencimento = dataVencimento.plusDays(servico.getPeriodDays());
                return converterData.conversaoLocalforDate(dataVencimento);
            }
        }
        else{
            if(mensal||anual){
                if(dataVencimento.plusMonths(1).getMonth().compareTo(Month.FEBRUARY)==0&&diaVencimento>28){
                    diaVencimento = 28;
                }
                LocalDate dataAtual = LocalDate.now();
            
                if(dataVencimentoFinal.isEqual(dataAtual)||dataVencimentoFinal.isBefore(dataAtual)){
                   return plano.getDataVencimento();
                }
                else{
                   dataVencimento = LocalDate.of(dataVencimento.plusMonths(1).getYear(), dataVencimento.plusMonths(1).getMonthValue(), diaVencimento);
                   if(!dataVencimentoFinal.isBefore(dataVencimento)){
                       return converterData.conversaoLocalforDate(dataVencimento); 
                   }
                   return plano.getDataVencimento();
                }
            }
            else{
                return plano.getDataVencimento();
            }
        }
    }
    
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }
}
