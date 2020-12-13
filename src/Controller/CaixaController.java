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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
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
    public void setarTabelaClientes() throws Exception{
        String alunoPesquisa = view.getCampoCliente().getText();
        ArrayList <Aluno> alunos = alunosDao.pesquisarPorNome(alunoPesquisa);
        if(alunoPesquisa.equals("")){listarAlunos();}
        else{this.buscasClientes(alunos);}        
    }
    
    public void listarAlunos() throws Exception{
        ArrayList <Aluno> alunos = this.alunosDao.selecionarTodosAlunos();
        this.buscasClientes(alunos);
    }
    
    private void buscasClientes(ArrayList <Aluno> listar) throws Exception{
        ArrayList<Turmas> turmas = new ArrayList<>();
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
    public void setarTabelaProdutos() throws Exception{
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
    
    public void listarProdutos() throws SQLException, ParseException, Exception{
        ArrayList <Produtos> produtos= this.produtosDao.selecionarTodosProdutos();
        this.buscasProdutos(produtos);
    }
    
    private void buscasProdutos(ArrayList <Produtos> listar) throws Exception{
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
            for(int linhas=0; linhas<totalLinhas; linhas++){
                valorTabela = new BigDecimal(converterDinheiro.converterParaBigDecimal(tabelaDeCarrinho.getValueAt(linhas, 4).toString()).toString());
                valorTotal = valorTotal.add(valorTabela);
            }
            valorTotal = valorTotal.setScale(2, RoundingMode.UP);
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
        BigDecimal novoValor = valorTotal.subtract(valorDesconto);
        novoValor = novoValor.setScale(2, RoundingMode.UP);
            view.getCampoVTotal().setText(novoValor.toString());
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
             view.getjPanelFormaDePagamento().setVisible(false);
         }
         else{
             view.exibeMensagem("Valor Pago Insuficiente!");
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
    public void setarTabelaMensalidade() throws SQLException, ParseException{
        limparTabelaMensalidade();
        int linhaSelecionada = view.getTabelaDeClientes().getSelectedRow();
        int codBanco = Integer.parseInt(tabelaDeClientes.getValueAt(linhaSelecionada, 0).toString());
        String nome = tabelaDeClientes.getValueAt(linhaSelecionada, 1).toString();
        
        Aluno aluno = alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+codBanco).get(0);
        Planos plano = planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE codAluno = "+codBanco).get(0);
        Servicos servico = servicos.pesquisarServicos("SELECT * FROM tblServicos WHERE codServico = "+plano.getCodServico()).get(0);
        
        
        String nomeServico = plano.getCodServico()+"."+servico.getNome()+"-"+servico.getFormaPagamento();
        String situacao = plano.getSituacao();
        String dataVencimento;
        
        Date dataPag = converterData.parseDate(converterData.parseDate(plano.getDataPagamento()));
        Date dataCadastro = converterData.parseDate(converterData.parseDate(aluno.getDataCadastro()));
        LocalDate dataBanco = converterData.conversaoLocalforDate(dataPag);
        LocalDate dataCad = converterData.conversaoLocalforDate(dataCadastro);
        if(dataBanco == null){
            dataVencimento = plano.getDiaVencimento()+"/"+ (dataCad.getMonthValue()+1)+"/"+dataCad.getYear();
        }
        else{
            dataVencimento = plano.getDiaVencimento()+"/"+(dataBanco.getMonthValue()+1)+"/"+dataBanco.getYear();
        }
        
        Object[] dadosTabela = {codBanco, nome, nomeServico, dataVencimento, situacao};
        tabelaDeMensalidade.addRow(dadosTabela);
        
    }
    
    public void setarTotalMensalidade() throws SQLException, ParseException{
        int linhaSelecionada = view.getTabelaMensalidade().getSelectedRow();
        
        if(linhaSelecionada!=-1){
            String situacao = tabelaDeMensalidade.getValueAt(linhaSelecionada, 4).toString();
            if(!situacao.equals("Pago")){
            int codBanco = Integer.parseInt(tabelaDeMensalidade.getValueAt(linhaSelecionada, 0).toString());
            Aluno aluno = alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+codBanco).get(0);
            
            view.getCampoVTotal().setText(aluno.getValorContrato().toString());
            }
            else{
                view.exibeMensagem("Fatura já foi Paga!");
            }
        }
    }
    
    public void finalizarVenda() throws SQLException, Exception{
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
            
            Vendas venda = new Vendas(codVenda, codCliente, codAluno, valorTotal, valorPago, valorTroco, dataVenda, formaDePagamento, this.tipoVenda());
            ArrayList<ItemVendido> itens = new ArrayList<>();
            
            long chaveVenda = venda.getChaveVenda();
            int codProduto;
            float quantidade;
            BigDecimal valor;
            BigDecimal subtotal;
            
            if(view.getPainelTabelaProdutos().isVisible()){
                int quantLinhas = view.getTabelaDeCarrinho().getRowCount();
                int codOrcamentario = verificador.verificarUltimo("tblDetOrcamentario", "codBanco")+1;
                DetOrcamentario orcamentario = new DetOrcamentario(codOrcamentario, "Vendas", formaDePagamento, valorTotal, dataVenda, chaveVenda);
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
                    this.setarLog(funcionarios, acao, descricao);
                }
                }
                
                vendasDao.inserirDados(venda, itens);
                orcamentarioDao.inserirDados(orcamentario);
            }
            
            else{
                int linhaSelecionada = view.getTabelaMensalidade().getSelectedRow();
                int codOrcamentario = verificador.verificarUltimo("tblDetOrcamentario", "codBanco")+1;
                DetOrcamentario orcamentario = new DetOrcamentario(codOrcamentario, "Planos", formaDePagamento, valorTotal, dataVenda, chaveVenda);
                
                Aluno aluno = alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+codAluno).get(0);
                BigDecimal debitos = new BigDecimal(aluno.getDebito().toString());
                
                codProduto = 0;
                quantidade = 1;
                valor = valorTotal;
                subtotal = valor;
                    
                ItemVendido itemVendido = new ItemVendido(chaveVenda, codProduto, quantidade, valor, subtotal);
                itens.add(itemVendido);
                
                
                Planos plano = new Planos(codAluno, 0, 0, 0, dataVenda, null, "Pago");
                if(debitos.compareTo(BigDecimal.ZERO)!=0){
                    if(debitos.subtract(valor).compareTo(BigDecimal.ZERO)>=0){
                        vendasDao.inserirDados(venda, itens);
                        orcamentarioDao.inserirDados(orcamentario);
                        planosDao.atualizarSituacao(plano);
                        alunosDao.atualizarDebitos(codAluno, debitos.subtract(valor));
                        
                    ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
                    if(funcionarios!=null){
                        String acao = "Pagamento de Plano";
                        String descricao = "Pagamento do plano do aluno "+aluno.getNome()+" da turma "+aluno.getTurma()+" no valor de "+valorTotal;
                        this.setarLog(funcionarios, acao, descricao);
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
            BigDecimal valorDesconto = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoVDesconto().getText()).toString());
            view.exibeMensagem("Venda Concluída!");
            
            if(view.getImpressaoComprovante().isSelected()){
               String subtotalVenda = valorTotal.add(valorDesconto).toString();
               String desconto = view.getCampoVDesconto().getText();
               this.imprimirComprovanteVenda(venda, subtotalVenda, desconto);
            }
            else{
                this.novaVenda();
            }
            
            
            
            
            

        }
        else{
            view.exibeMensagem("Adicione uma Forma de Pagamento!");
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
    
    private LogAçoesFuncionario setarLog(ArrayList <Funcionario> funcionarios, String acao, String descricao){
        Funcionario funcionario = funcionarios.get(0);
        Date dataEvento = new Date();
        LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, acao, descricao);
        return logAcao;
    }
    
    private void imprimirComprovanteVenda(Vendas venda,String subtotal, String desconto) throws ParseException{
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
            try {
                Aluno aluno = alunoDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+venda.getCodAluno()).get(0);
                nomeCliente+= " "+aluno.getNome();
                if(aluno.getCpf()!=null){
                    cpf+=" "+aluno.getCpf();
                }
                else{
                    cpf+=" Não Possui";
                }
            } catch (SQLException ex) {
                Logger.getLogger(ImpressaoComponentes.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(ImpressaoComponentes.class.getName()).log(Level.SEVERE, null, ex);
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
        String valorPago = "PAGO \t\t\t\t\t\tR$"+venda.getValorPago()+"\n\r";
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
        imprimirComprovante.printTextFile("C:/Rehabiliter/Info/Comprovantes/Comprovante dia "+dataArquivo+".txt");
        //exportarComprovante.gerarPDF("C:/Rehabiliter/Info/Comprovantes/Comprovante dia "+dataArquivo+".txt", "C:/Rehabiliter/Info/Comprovantes/Comprovante dia "+dataArquivo+".pdf");
        //exportarComprovante.convertDocx2pdf("C:/Rehabiliter/Info/Comprovantes/Comprovante dia "+dataArquivo+".txt", ".txt", "ACTIVEX.ActiveXCtrl.1");
        //imprimirComprovante.imprimirString("C:/Rehabiliter/Info/Comprovantes/Comprovante dia "+dataArquivo+".txt");
        
        novaVenda();
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
    }
}
