/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.MenuItemUsuarioController;
import Controller.UsuarioController;
import Enum.TipoLog;
import Model.MenuItemModel;
import Model.MenuItemUsuarioModel;
import Model.UsuarioModel;
import Util.LogUtil;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author celso & Emanuel
 */
public class PermissaView extends javax.swing.JFrame {

    /**
     * Creates new form PermissaView
     */
    
    private MenuItemUsuarioModel menuItemUsuarioModel;
    private MenuItemUsuarioController menuItemUsuarioController;
    public PermissaView() {
        initComponents();
        usuario();
    }

    private void usuario() {

        UsuarioController controller = new UsuarioController();
        cboUsuario.setModel(new DefaultComboBoxModel(controller.get().toArray()));
        cboUsuario.setSelectedIndex(-1);
        AutoCompleteDecorator.decorate(cboUsuario);
        menuItemUsuarioController = new  MenuItemUsuarioController();
    }

    public void ficheiro(boolean selecionar)
    {
        ckFicheiroProduto.setSelected(selecionar);
        ckFicheiroFornecedor.setSelected(selecionar);
        ckFicheiroEntradaProduto.setSelected(selecionar);
        ckFicheiroCliente.setSelected(selecionar);
        jCheckBox15.setSelected(selecionar);
    }
    
    public void tabela(boolean selecionar)
    {
       ckTabelaCategoria.setSelected(selecionar);
       ckTabelaArmazem.setSelected(selecionar);
       ckTabelaFormaPagamento.setSelected(selecionar);
       ckTabelaFormaIpressao.setSelected(selecionar);
       ckTabelaIPC.setSelected(selecionar);
       ckTabelaFabricante.setSelected(selecionar);
       ckTabelaCambio.setSelected(selecionar);
       ckTabelaMotivoIsencao.setSelected(selecionar);
       ckTabelaTaxa.setSelected(selecionar);
       ckTabelaSaft.setSelected(selecionar);
       ckTabelaNumeracao.setSelected(selecionar);
       jCheckBox16.setSelected(selecionar);
       ckTabelaAgruparEntradasVendas.setSelected(selecionar);
       ckTabelaAdicionarQTDTelaProduto.setSelected(selecionar);
       ckTabelaPermitirFechoDeCaixaAOOperador.setSelected(selecionar);
    }
    
    public void operacoes(boolean selecionar)
    {
        ckOperacaoTransferenciaProduto.setSelected(selecionar);
        ckOperacaoEncomendaCliente.setSelected(selecionar);
        ckOperacaoActualizacaoStock.setSelected(selecionar);
        ckOperacaoAlterarPrecoVenda.setSelected(selecionar);
        ckOpercacaoDevolucao.setSelected(selecionar);
        ckOperacaoAlterarDataExpiracao.setSelected(selecionar);
        ckOperacaoAlterarCodigoBarra.setSelected(selecionar);
        ckOperacaoPagamentoDividaFornecedor.setSelected(selecionar);
        ckOperacaoPagamentoDividaFornecedor.setSelected(selecionar);
        ckOperacaoEncomendaFornecedor.setSelected(selecionar);
        ckOperacaoNotaDeCredito.setSelected(selecionar);
        ckOperacaoAnulacaoDocumento.setSelected(selecionar);
        ckOperacaoLiquidarDivida.setSelected(selecionar);
        ckOperacaoNotaDeDebito.setSelected(selecionar);
        jCheckBox14.setSelected(selecionar);
    }
    public void relatorios(boolean selecionar)
    {
        ckClientePagas.setSelected(selecionar);
        ckClienteDividas.setSelected(selecionar);
        ckClienteDevolvidas.setSelected(selecionar);
        ckClientePagasPorCliente.setSelected(selecionar);
        ckRelarioCategoria.setSelected(selecionar);
        ckRelarioArmazem.setSelected(selecionar);
        ckRelatorioFabricante.setSelected(selecionar);
        ckRelatorioListaMovimento.setSelected(selecionar);
        
        ckRelatorioFornecedorEntrada.setSelected(selecionar);
        ckRelatorioFornecedorEncomenda.setSelected(selecionar);
        ckRelatorioFornecedorTodos.setSelected(selecionar);
        ckRelatorioProdutoStock.setSelected(selecionar);
        ckRelatorioProdutoStockEmBaixo.setSelected(selecionar);
        ckRelatorioProdutoPorArmazem.setSelected(selecionar);
        ckRelatorioProdutoEtiqueta.setSelected(selecionar);
        ckRelatorioClienteTodos.setSelected(selecionar);
        ckRelatorioFornecedorEntradaPorFornecedor.setSelected(selecionar);
        ckRelatorioProdutoExpirado.setSelected(selecionar);
        jCheckBox44.setSelected(selecionar);
        ckRelatorioCaixa.setSelected(selecionar);
        ckRelatorioEntradaEStock.setSelected(selecionar);
        ckRelatorioProdutoPresteExpirar.setSelected(selecionar);
        ckRelatorioProdutoPresteTerminar.setSelected(selecionar);
    }
    
    public void graficos(boolean selecionar)
    {
        ckGraficoCodigoBarra.setSelected(selecionar);
        ckGraficoStockCategoria.setSelected(selecionar);
        ckGraficoStockFabricante.setSelected(selecionar);
        ckGraficoStockProduto.setSelected(selecionar);
        ckGraficoStockFornecedor.setSelected(selecionar);
        ckGraficostockProdutoDetalhado.setSelected(selecionar);
        ckGraficoVendaProduto.setSelected(selecionar);
        ckGraficoVendaBalancoAnual.setSelected(selecionar);
        ckGraficoVendaCliente.setSelected(selecionar);
        ckGraficoVendaBalancoMensal.setSelected(selecionar);
        ckGrafico.setSelected(selecionar);
    }
    public void sistema(boolean selecionar)
    {
        ckSistemaUsuario.setSelected(selecionar);
        ckSistemaPermissao.setSelected(selecionar);
        ckSistemaEmpresa.setSelected(selecionar);
        ckSistemaListaDeUsuario.setSelected(selecionar);
        ckSistemaBackupDoSistema.setSelected(selecionar);
        ckSistema.setSelected(selecionar);
        ckSistemaLogDeAcesso.setSelected(selecionar);
    }
    public void ajuda(boolean selecionar)
    {
        ckAjudaManualSistema.setSelected(selecionar);
        ckAjudaEmpresa.setSelected(selecionar);
        ckAjuda.setSelected(selecionar);
    }
    
    public void todos(boolean selecionar)
    {
        ficheiro(selecionar);
        tabela(selecionar);
        operacoes(selecionar);
        relatorios(selecionar);
        graficos(selecionar);
        sistema(selecionar);
        ajuda(selecionar);
    }
    
    public UsuarioModel getUsuario()
    {
        UsuarioModel usuarioModel = (UsuarioModel) cboUsuario.getSelectedItem();
        return usuarioModel;
    }
    
    /*----------------- Menus ----------------------------------------*/
    
    
     public void permissao(){
        List<MenuItemModel> lista = new ArrayList<>();
        lista = getUsuario().getMenuItem();
        for(MenuItemModel object : lista){
            
            if(object.getDesignacao().equals("Ficheiro"))
                fichero(object);
            
            if(object.getDesignacao().equals("Operação"))
                operacao(object);
            
            if(object.getDesignacao().equals("Tabela"))
                tabela(object);
            
            if(object.getDesignacao().equals("Relatório"))
                relatorio(object);
            
            if(object.getDesignacao().equals("Gráfico"))
                grafico(object);
            
            if(object.getDesignacao().equals("Sistema"))
                sistema(object);
            
            if(object.getDesignacao().equals("Ajuda"))
                ajuda(object);
            
        }
        
    }
    
    public void fichero(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckFicheiroProduto.getText()))
                ckFicheiroProduto.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckFicheiroFornecedor.getText()))
                ckFicheiroFornecedor.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckFicheiroCliente.getText()))
                ckFicheiroCliente.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckFicheiroEntradaProduto.getText()))
                ckFicheiroEntradaProduto.setSelected(object.getIdEstado() == 1);
        }
    }
    
    
    public void operacao(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckOperacaoTransferenciaProduto.getText()))
                ckOperacaoTransferenciaProduto.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equalsIgnoreCase("Pagamento de Divida"))
                pagamentoDeDividas(object);
            
            if(object.getDesignacao().equals(ckOperacaoAlterarPrecoVenda.getText()))
                ckOperacaoAlterarPrecoVenda.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals("Encomenda"))
                encomenda(object);
            
            if(object.getDesignacao().equals(ckOperacaoActualizacaoStock.getText()))
                ckOperacaoActualizacaoStock.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckOperacaoAlterarDataExpiracao.getText()))
                ckOperacaoAlterarDataExpiracao.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckOperacaoAlterarCodigoBarra.getText()))
                ckOperacaoAlterarCodigoBarra.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckOpercacaoDevolucao.getText()))
                ckOpercacaoDevolucao.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals("Documentos Rectificativo"))
                documentoRetificatico(object);
        }
        
    }
    public void tabela(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckTabelaCategoria.getText()))
                ckTabelaCategoria.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckTabelaCambio.getText()))
                ckTabelaCambio.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckTabelaArmazem.getText()))
                ckTabelaArmazem.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckTabelaFormaPagamento.getText()))
                ckTabelaFormaPagamento.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckTabelaFormaIpressao.getText()))
                ckTabelaFormaIpressao.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckTabelaIPC.getText()))
                ckTabelaIPC.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckTabelaFabricante.getText()))
                ckTabelaFabricante.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckTabelaAgruparEntradasVendas.getText()))
                ckTabelaAgruparEntradasVendas.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckTabelaAdicionarQTDTelaProduto.getText()))
                ckTabelaAdicionarQTDTelaProduto.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckTabelaPermitirFechoDeCaixaAOOperador.getText()))
                ckTabelaPermitirFechoDeCaixaAOOperador.setSelected(object.getIdEstado() == 1);
            
            
            if(object.getDesignacao().equals("IVA"))
                iva(object);
        }
        
    }
    
    public void relatorio(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equalsIgnoreCase("Cliente"))
                cliente(object);
            
            if(object.getDesignacao().equalsIgnoreCase("Fornecedor"))
                fornecedores(object);
            
            if(object.getDesignacao().equals("Produto"))
                produto(object);
            
            if(object.getDesignacao().equals(ckRelarioCategoria.getText()))
                ckRelarioCategoria.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckRelarioArmazem.getText()))
                ckRelarioArmazem.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckRelatorioFabricante.getText()))
                ckRelatorioFabricante.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckRelatorioListaMovimento.getText()))
                ckRelatorioListaMovimento.setSelected(object.getIdEstado() == 1);
            if(object.getDesignacao().equals(ckRelatorioCaixa.getText()))
                ckRelatorioCaixa.setSelected(object.getIdEstado() == 1);
            if(object.getDesignacao().equals(ckRelatorioEntradaEStock.getText()))
                ckRelatorioEntradaEStock.setSelected(object.getIdEstado() == 1);
            
        }
        
    }
    
    public void grafico(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals("Stock"))
                stock(object);
            
            if(object.getDesignacao().equals("Venda"))
                venda(object);
        }
        
    }
    
    public void sistema(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckSistemaUsuario.getText()))
                ckSistemaUsuario.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckSistemaPermissao.getText()))
                ckSistemaPermissao.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckSistemaEmpresa.getText()))
                ckSistemaEmpresa.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckSistemaListaDeUsuario.getText()))
                ckSistemaListaDeUsuario.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckSistemaBackupDoSistema.getText()))
                ckSistemaBackupDoSistema.setSelected(object.getIdEstado() == 1);
            if(object.getDesignacao().equals(ckSistemaLogDeAcesso.getText()))
                ckSistemaLogDeAcesso.setSelected(object.getIdEstado() == 1);
            
        }
        
    }
    public void ajuda(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckAjudaManualSistema.getText()))
                ckAjudaManualSistema.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckAjudaEmpresa.getText()))
                ckAjudaEmpresa.setSelected(object.getIdEstado() == 1);
            
        }
        
    }
    
    
    /*----------------- MenuItem ----------------------------------------*/
    
    public void pagamentoDeDividas(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckOperacaoPagamentoDividaFornecedor.getText()))
                ckOperacaoPagamentoDividaFornecedor.setSelected(object.getIdEstado() == 1);
        } 
    }
    
    public void encomenda(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckOperacaoEncomendaFornecedor.getText()))
                ckOperacaoEncomendaFornecedor.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckOperacaoEncomendaCliente.getText()))
                ckOperacaoEncomendaCliente.setSelected(object.getIdEstado() == 1);
        } 
    }
    
    public void documentoRetificatico(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckOperacaoNotaDeCredito.getText()))
                ckOperacaoNotaDeCredito.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckOperacaoNotaDeDebito.getText()))
                ckOperacaoNotaDeDebito.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckOperacaoAnulacaoDocumento.getText()))
                ckOperacaoAnulacaoDocumento.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckOperacaoLiquidarDivida.getText()))
                ckOperacaoLiquidarDivida.setSelected(object.getIdEstado() == 1);
        } 
    }
    
    public void iva(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckTabelaMotivoIsencao.getText()))
                ckTabelaMotivoIsencao.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckTabelaTaxa.getText()))
                ckTabelaTaxa.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckTabelaSaft.getText()))
                ckTabelaSaft.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckTabelaNumeracao.getText()))
                ckTabelaNumeracao.setSelected(object.getIdEstado() == 1);
        } 
    }
    
    public void cliente(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals("Factura"))
                factura(object);
            
            if(object.getDesignacao().equals(ckRelatorioClienteTodos.getText()))
                ckRelatorioClienteTodos.setSelected(object.getIdEstado() == 1);
        } 
    }
    
    public void factura(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckClientePagas.getText()))
                ckClientePagas.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckClienteDividas.getText()))
                ckClienteDividas.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckClienteDevolvidas.getText()))
                ckClienteDevolvidas.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckClientePagasPorCliente.getText()))
                ckClientePagasPorCliente.setSelected(object.getIdEstado() == 1);
        } 
    }
    
    public void fornecedores(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckRelatorioFornecedorEncomenda.getText()))
                ckRelatorioFornecedorEncomenda.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckRelatorioFornecedorTodos.getText()))
                ckRelatorioFornecedorTodos.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckRelatorioFornecedorEntrada.getText()))
                ckRelatorioFornecedorEntrada.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckRelatorioFornecedorEntradaPorFornecedor.getText()))
                ckRelatorioFornecedorEntradaPorFornecedor.setSelected(object.getIdEstado() == 1);
            
        } 
    }
    
    public void produto(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckRelatorioProdutoStock.getText()))
                ckRelatorioProdutoStock.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckRelatorioProdutoPorArmazem.getText()))
                ckRelatorioProdutoPorArmazem.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckRelatorioProdutoStockEmBaixo.getText()))
                ckRelatorioProdutoStockEmBaixo.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckRelatorioProdutoEtiqueta.getText()))
                ckRelatorioProdutoEtiqueta.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckRelatorioProdutoExpirado.getText()))
                ckRelatorioProdutoExpirado.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckRelatorioProdutoPresteExpirar.getText()))
                ckRelatorioProdutoPresteExpirar.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckRelatorioProdutoPresteTerminar.getText()))
                ckRelatorioProdutoPresteTerminar.setSelected(object.getIdEstado() == 1);
        } 
    }
    
    public void stock(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckGraficoCodigoBarra.getText()))
                ckGraficoCodigoBarra.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckGraficoStockProduto.getText()))
                ckGraficoStockProduto.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckGraficoStockCategoria.getText()))
                ckGraficoStockCategoria.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckGraficoStockFornecedor.getText()))
                ckGraficoStockFornecedor.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckGraficoStockFabricante.getText()))
                ckGraficoStockFabricante.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckGraficostockProdutoDetalhado.getText()))
                ckGraficostockProdutoDetalhado.setSelected(object.getIdEstado() == 1);
        } 
    }
    
    public void venda(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckGraficoVendaProduto.getText()))
                ckGraficoVendaProduto.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckGraficoVendaCliente.getText()))
                ckGraficoVendaCliente.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckGraficoVendaBalancoAnual.getText()))
                ckGraficoVendaBalancoAnual.setSelected(object.getIdEstado() == 1);
            
            if(object.getDesignacao().equals(ckGraficoVendaBalancoMensal.getText()))
                ckGraficoVendaBalancoMensal.setSelected(object.getIdEstado() == 1);
        }
    
    }
    
    
    
    
    /*----------------- Codigo para salvar as permicoes dadas ----------------------------------------*/
    
    public void salvarMenuItemUsuario(MenuItemModel object, int idEstado){
        menuItemUsuarioModel = new MenuItemUsuarioModel();
        menuItemUsuarioModel.setMenuItemModel(object);
        menuItemUsuarioModel.setUsuarioModel(getUsuario());
        menuItemUsuarioModel.setIdEstado(idEstado);
        
        menuItemUsuarioController = new MenuItemUsuarioController();
        menuItemUsuarioController.update(menuItemUsuarioModel);
    }
    
     public void permissaoSalvar(){
         
        List<MenuItemModel> lista = new ArrayList<>();
        lista = getUsuario().getMenuItem();
        for(MenuItemModel object : lista){
            
            if(object.getDesignacao().equals("Ficheiro"))
                ficheroSalvar(object);
            
            if(object.getDesignacao().equals("Operação"))
                operacaoSalvar(object);
            
            if(object.getDesignacao().equals("Tabela"))
                tabelaSalvar(object);
            
            if(object.getDesignacao().equals("Relatório"))
                relatorioSalvar(object);
            
            if(object.getDesignacao().equals("Gráfico"))
                graficoSalvar(object);
            
            if(object.getDesignacao().equals("Sistema"))
                sistemaSalvar(object);
            
            if(object.getDesignacao().equals("Ajuda"))
                ajudaSalvar(object);
            
        }
        
        if(lista.size() > 0){
            LogUtil.log.salvarLog(TipoLog.INFO, " Deu Permissão ao Usuário ( "+getUsuario().getNome()+" )");
        }
        
    }
    
    public void ficheroSalvar(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckFicheiroProduto.getText()))
                salvarMenuItemUsuario(object, ckFicheiroProduto.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckFicheiroFornecedor.getText()))
                salvarMenuItemUsuario(object, ckFicheiroFornecedor.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckFicheiroCliente.getText()))
                salvarMenuItemUsuario(object, ckFicheiroCliente.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckFicheiroEntradaProduto.getText()))
                salvarMenuItemUsuario(object, ckFicheiroEntradaProduto.isSelected()? 1: 2);
        }
    }
    
    
    public void operacaoSalvar(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckOperacaoTransferenciaProduto.getText()))
                salvarMenuItemUsuario(object, ckOperacaoTransferenciaProduto.isSelected()? 1: 2);
            
            if(object.getDesignacao().equalsIgnoreCase("Pagamento de Divida"))
                pagamentoDeDividasSalvar(object);
            
            if(object.getDesignacao().equals(ckOperacaoAlterarPrecoVenda.getText()))
                salvarMenuItemUsuario(object, ckOperacaoAlterarPrecoVenda.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals("Encomenda"))
                encomendaSalvar(object);
            
            if(object.getDesignacao().equals(ckOperacaoActualizacaoStock.getText()))
                salvarMenuItemUsuario(object, ckOperacaoActualizacaoStock.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckOperacaoAlterarDataExpiracao.getText()))
                salvarMenuItemUsuario(object, ckOperacaoAlterarDataExpiracao.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckOperacaoAlterarCodigoBarra.getText()))
                salvarMenuItemUsuario(object, ckOperacaoAlterarCodigoBarra.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckOpercacaoDevolucao.getText()))
                salvarMenuItemUsuario(object, ckOpercacaoDevolucao.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals("Documentos Rectificativo"))
                documentoRetificaticoSalvar(object);
        }
        
    }
    public void tabelaSalvar(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckTabelaCategoria.getText()))
                salvarMenuItemUsuario(object, ckTabelaCategoria.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckTabelaCambio.getText()))
                salvarMenuItemUsuario(object, ckTabelaCambio.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckTabelaArmazem.getText()))
                salvarMenuItemUsuario(object, ckTabelaArmazem.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckTabelaFormaPagamento.getText()))
                salvarMenuItemUsuario(object, ckTabelaFormaPagamento.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckTabelaFormaIpressao.getText()))
                salvarMenuItemUsuario(object, ckTabelaFormaIpressao.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckTabelaIPC.getText()))
                salvarMenuItemUsuario(object, ckTabelaIPC.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckTabelaFabricante.getText()))
                salvarMenuItemUsuario(object, ckTabelaFabricante.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckTabelaAgruparEntradasVendas.getText()))
                salvarMenuItemUsuario(object, ckTabelaAgruparEntradasVendas.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckTabelaAdicionarQTDTelaProduto.getText()))
                salvarMenuItemUsuario(object, ckTabelaAdicionarQTDTelaProduto.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckTabelaPermitirFechoDeCaixaAOOperador.getText()))
                salvarMenuItemUsuario(object, ckTabelaPermitirFechoDeCaixaAOOperador.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals("IVA"))
                ivaSalvar(object);
        }
        
    }
    
    public void relatorioSalvar(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equalsIgnoreCase("Cliente"))
                clienteSalvar(object);
            
            if(object.getDesignacao().equalsIgnoreCase("Fornecedor"))
                fornecedoresSalvar(object);
            
            if(object.getDesignacao().equals("Produto"))
                produtoSalvar(object);
            
            if(object.getDesignacao().equals(ckRelarioCategoria.getText()))
                salvarMenuItemUsuario(object, ckRelarioCategoria.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckRelarioArmazem.getText()))
                salvarMenuItemUsuario(object, ckRelarioArmazem.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckRelatorioFabricante.getText()))
                salvarMenuItemUsuario(object, ckRelatorioFabricante.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckRelatorioListaMovimento.getText()))
                salvarMenuItemUsuario(object, ckRelatorioListaMovimento.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckRelatorioCaixa.getText()))
                salvarMenuItemUsuario(object, ckRelatorioCaixa.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckRelatorioEntradaEStock.getText()))
                salvarMenuItemUsuario(object, ckRelatorioEntradaEStock.isSelected()? 1: 2);
            
        }
        
    }
    
    public void graficoSalvar(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals("Stock"))
                stockSalvar(object);
            
            if(object.getDesignacao().equals("Venda"))
                vendaSalvar(object);
        }
        
    }
    
    public void sistemaSalvar(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckSistemaUsuario.getText()))
                salvarMenuItemUsuario(object, ckSistemaUsuario.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckSistemaPermissao.getText()))
                salvarMenuItemUsuario(object, ckSistemaPermissao.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckSistemaEmpresa.getText()))
                salvarMenuItemUsuario(object, ckSistemaEmpresa.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckSistemaListaDeUsuario.getText()))
                salvarMenuItemUsuario(object, ckSistemaListaDeUsuario.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckSistemaBackupDoSistema.getText()))
                salvarMenuItemUsuario(object, ckSistemaBackupDoSistema.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckSistemaLogDeAcesso.getText()))
                salvarMenuItemUsuario(object, ckSistemaLogDeAcesso.isSelected()? 1: 2);
            
        }
        
    }
    public void ajudaSalvar(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckAjudaManualSistema.getText()))
                salvarMenuItemUsuario(object, ckAjudaManualSistema.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckAjudaEmpresa.getText()))
                salvarMenuItemUsuario(object, ckAjudaEmpresa.isSelected()? 1: 2);
            
        }
        
    }
    
    /*----------------- MenuItem Para Salvar----------------------------------------*/
    
    public void pagamentoDeDividasSalvar(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckOperacaoPagamentoDividaFornecedor.getText()))
                salvarMenuItemUsuario(object, ckOperacaoPagamentoDividaFornecedor.isSelected()? 1: 2);
        } 
    }
    
    public void encomendaSalvar(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckOperacaoEncomendaFornecedor.getText()))
                salvarMenuItemUsuario(object, ckOperacaoEncomendaFornecedor.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckOperacaoEncomendaCliente.getText()))
                salvarMenuItemUsuario(object, ckOperacaoEncomendaCliente.isSelected()? 1: 2);
        } 
    }
    
    public void documentoRetificaticoSalvar(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckOperacaoNotaDeCredito.getText()))
                salvarMenuItemUsuario(object, ckOperacaoNotaDeCredito.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckOperacaoNotaDeDebito.getText()))
                salvarMenuItemUsuario(object, ckOperacaoNotaDeDebito.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckOperacaoAnulacaoDocumento.getText()))
                salvarMenuItemUsuario(object, ckOperacaoAnulacaoDocumento.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckOperacaoLiquidarDivida.getText()))
                salvarMenuItemUsuario(object, ckOperacaoLiquidarDivida.isSelected()? 1: 2);
        } 
    }
    
    
    public void ivaSalvar(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckTabelaMotivoIsencao.getText()))
                salvarMenuItemUsuario(object, ckTabelaMotivoIsencao.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckTabelaTaxa.getText()))
                salvarMenuItemUsuario(object, ckTabelaTaxa.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckTabelaSaft.getText()))
                salvarMenuItemUsuario(object, ckTabelaSaft.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckTabelaNumeracao.getText()))
                salvarMenuItemUsuario(object, ckTabelaNumeracao.isSelected()? 1: 2);
        } 
    }
    
    public void clienteSalvar(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals("Factura"))
                facturaSalvar(object);
            
            if(object.getDesignacao().equals(ckRelatorioClienteTodos.getText()))
                salvarMenuItemUsuario(object, ckRelatorioClienteTodos.isSelected()? 1: 2);
        } 
    }
    
    public void facturaSalvar(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckClientePagas.getText()))
                salvarMenuItemUsuario(object, ckClientePagas.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckClienteDividas.getText()))
                salvarMenuItemUsuario(object, ckClienteDividas.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckClienteDevolvidas.getText()))
                salvarMenuItemUsuario(object, ckClienteDevolvidas.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckClientePagasPorCliente.getText()))
                salvarMenuItemUsuario(object, ckClientePagasPorCliente.isSelected()? 1: 2);
        } 
    }
    
    public void fornecedoresSalvar(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckRelatorioFornecedorEncomenda.getText()))
                salvarMenuItemUsuario(object, ckRelatorioFornecedorEncomenda.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckRelatorioFornecedorTodos.getText()))
                salvarMenuItemUsuario(object, ckRelatorioFornecedorTodos.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckRelatorioFornecedorEntrada.getText()))
                salvarMenuItemUsuario(object, ckRelatorioFornecedorEntrada.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckRelatorioFornecedorEntradaPorFornecedor.getText()))
                salvarMenuItemUsuario(object, ckRelatorioFornecedorEntradaPorFornecedor.isSelected()? 1: 2);
            
            
        } 
    }
    
    public void produtoSalvar(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckRelatorioProdutoStock.getText()))
                salvarMenuItemUsuario(object, ckRelatorioProdutoStock.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckRelatorioProdutoPorArmazem.getText()))
                salvarMenuItemUsuario(object, ckRelatorioProdutoPorArmazem.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckRelatorioProdutoStockEmBaixo.getText()))
                salvarMenuItemUsuario(object, ckRelatorioProdutoStockEmBaixo.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckRelatorioProdutoEtiqueta.getText()))
                salvarMenuItemUsuario(object, ckRelatorioProdutoEtiqueta.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckRelatorioProdutoExpirado.getText()))
                salvarMenuItemUsuario(object, ckRelatorioProdutoExpirado.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckRelatorioProdutoPresteExpirar.getText()))
                salvarMenuItemUsuario(object, ckRelatorioProdutoPresteExpirar.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckRelatorioProdutoPresteTerminar.getText()))
                salvarMenuItemUsuario(object, ckRelatorioProdutoPresteTerminar.isSelected()? 1: 2);
        } 
    }
    
    public void stockSalvar(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckGraficoCodigoBarra.getText()))
                salvarMenuItemUsuario(object, ckGraficoCodigoBarra.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckGraficoStockProduto.getText()))
                salvarMenuItemUsuario(object, ckGraficoStockProduto.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckGraficoStockCategoria.getText()))
                salvarMenuItemUsuario(object, ckGraficoStockCategoria.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckGraficoStockFornecedor.getText()))
                salvarMenuItemUsuario(object, ckGraficoStockFornecedor.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckGraficoStockFabricante.getText()))
                salvarMenuItemUsuario(object, ckGraficoStockFabricante.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckGraficostockProdutoDetalhado.getText()))
                salvarMenuItemUsuario(object, ckGraficostockProdutoDetalhado.isSelected()? 1: 2);
        } 
    }
    
    public void vendaSalvar(MenuItemModel menuItemModel){
        
        for (MenuItemModel object : menuItemModel.getItem()) {
            
            if(object.getDesignacao().equals(ckGraficoVendaProduto.getText()))
                salvarMenuItemUsuario(object, ckGraficoVendaProduto.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckGraficoVendaCliente.getText()))
                salvarMenuItemUsuario(object, ckGraficoVendaCliente.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckGraficoVendaBalancoAnual.getText()))
                salvarMenuItemUsuario(object, ckGraficoVendaBalancoAnual.isSelected()? 1: 2);
            
            if(object.getDesignacao().equals(ckGraficoVendaBalancoMensal.getText()))
                salvarMenuItemUsuario(object, ckGraficoVendaBalancoMensal.isSelected()? 1: 2);
        }
    
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        ckOperacaoTransferenciaProduto = new javax.swing.JCheckBox();
        ckOperacaoEncomendaCliente = new javax.swing.JCheckBox();
        ckOperacaoActualizacaoStock = new javax.swing.JCheckBox();
        ckOperacaoAlterarPrecoVenda = new javax.swing.JCheckBox();
        ckOpercacaoDevolucao = new javax.swing.JCheckBox();
        ckOperacaoAlterarDataExpiracao = new javax.swing.JCheckBox();
        ckOperacaoAlterarCodigoBarra = new javax.swing.JCheckBox();
        jCheckBox14 = new javax.swing.JCheckBox();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        ckOperacaoNotaDeCredito = new javax.swing.JCheckBox();
        ckOperacaoAnulacaoDocumento = new javax.swing.JCheckBox();
        ckOperacaoNotaDeDebito = new javax.swing.JCheckBox();
        ckOperacaoLiquidarDivida = new javax.swing.JCheckBox();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        ckOperacaoPagamentoDividaFornecedor = new javax.swing.JCheckBox();
        ckOperacaoEncomendaFornecedor = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jCheckBox16 = new javax.swing.JCheckBox();
        ckTabelaCategoria = new javax.swing.JCheckBox();
        ckTabelaArmazem = new javax.swing.JCheckBox();
        ckTabelaFormaPagamento = new javax.swing.JCheckBox();
        ckTabelaFormaIpressao = new javax.swing.JCheckBox();
        ckTabelaFabricante = new javax.swing.JCheckBox();
        ckTabelaIPC = new javax.swing.JCheckBox();
        ckTabelaCambio = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        ckTabelaMotivoIsencao = new javax.swing.JCheckBox();
        ckTabelaTaxa = new javax.swing.JCheckBox();
        ckTabelaSaft = new javax.swing.JCheckBox();
        ckTabelaNumeracao = new javax.swing.JCheckBox();
        ckTabelaAgruparEntradasVendas = new javax.swing.JCheckBox();
        ckTabelaAdicionarQTDTelaProduto = new javax.swing.JCheckBox();
        ckTabelaPermitirFechoDeCaixaAOOperador = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        ckClientePagas = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        ckClienteDividas = new javax.swing.JCheckBox();
        ckClienteDevolvidas = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        ckClientePagasPorCliente = new javax.swing.JCheckBox();
        jSeparator2 = new javax.swing.JSeparator();
        ckRelatorioFornecedorEntrada = new javax.swing.JCheckBox();
        ckRelatorioFornecedorEncomenda = new javax.swing.JCheckBox();
        ckRelatorioFornecedorTodos = new javax.swing.JCheckBox();
        ckRelatorioProdutoStock = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        ckRelatorioProdutoStockEmBaixo = new javax.swing.JCheckBox();
        ckRelatorioProdutoPorArmazem = new javax.swing.JCheckBox();
        ckRelatorioProdutoEtiqueta = new javax.swing.JCheckBox();
        ckRelarioCategoria = new javax.swing.JCheckBox();
        ckRelarioArmazem = new javax.swing.JCheckBox();
        ckRelatorioFabricante = new javax.swing.JCheckBox();
        ckRelatorioListaMovimento = new javax.swing.JCheckBox();
        jCheckBox44 = new javax.swing.JCheckBox();
        ckRelatorioClienteTodos = new javax.swing.JCheckBox();
        ckRelatorioFornecedorEntradaPorFornecedor = new javax.swing.JCheckBox();
        ckRelatorioProdutoExpirado = new javax.swing.JCheckBox();
        ckRelatorioCaixa = new javax.swing.JCheckBox();
        ckRelatorioEntradaEStock = new javax.swing.JCheckBox();
        ckRelatorioProdutoPresteExpirar = new javax.swing.JCheckBox();
        ckRelatorioProdutoPresteTerminar = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        ckGraficoStockProduto = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        ckGraficoStockCategoria = new javax.swing.JCheckBox();
        ckGraficoCodigoBarra = new javax.swing.JCheckBox();
        ckGraficoStockFornecedor = new javax.swing.JCheckBox();
        ckGraficoStockFabricante = new javax.swing.JCheckBox();
        ckGraficostockProdutoDetalhado = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        ckGraficoVendaProduto = new javax.swing.JCheckBox();
        ckGraficoVendaCliente = new javax.swing.JCheckBox();
        ckGraficoVendaBalancoAnual = new javax.swing.JCheckBox();
        ckGraficoVendaBalancoMensal = new javax.swing.JCheckBox();
        ckGrafico = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        ckSistemaUsuario = new javax.swing.JCheckBox();
        ckSistemaPermissao = new javax.swing.JCheckBox();
        ckSistemaEmpresa = new javax.swing.JCheckBox();
        ckSistemaListaDeUsuario = new javax.swing.JCheckBox();
        ckSistemaBackupDoSistema = new javax.swing.JCheckBox();
        ckSistema = new javax.swing.JCheckBox();
        ckSistemaLogDeAcesso = new javax.swing.JCheckBox();
        jPanel8 = new javax.swing.JPanel();
        ckAjuda = new javax.swing.JCheckBox();
        ckAjudaManualSistema = new javax.swing.JCheckBox();
        ckAjudaEmpresa = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        ckFicheiroProduto = new javax.swing.JCheckBox();
        ckFicheiroFornecedor = new javax.swing.JCheckBox();
        ckFicheiroCliente = new javax.swing.JCheckBox();
        ckFicheiroEntradaProduto = new javax.swing.JCheckBox();
        jCheckBox15 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jCheckBox23 = new javax.swing.JCheckBox();
        cboUsuario = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Permissão");
        setResizable(false);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/BANNE.png"))); // NOI18N

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/ddd.png"))); // NOI18N

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Usuario");

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        ckOperacaoTransferenciaProduto.setText("Transferência de Produto");

        ckOperacaoEncomendaCliente.setText("Cliente");

        ckOperacaoActualizacaoStock.setText("Actualização de Stock");

        ckOperacaoAlterarPrecoVenda.setText("Alterar preço de venda");

        ckOpercacaoDevolucao.setText("Devolução");

        ckOperacaoAlterarDataExpiracao.setText("Alterar data de Expiração");

        ckOperacaoAlterarCodigoBarra.setText("Alterar Código de Barra");

        jCheckBox14.setText("Selecionar todos os itens");
        jCheckBox14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox14ActionPerformed(evt);
            }
        });

        jLabel5.setText("Documentos Retificativos");

        ckOperacaoNotaDeCredito.setText("Nota de Crédito");

        ckOperacaoAnulacaoDocumento.setText("Anulação Documento");

        ckOperacaoNotaDeDebito.setText("Nota debito");

        ckOperacaoLiquidarDivida.setText("Liquidar Divida");

        jLabel11.setText("Pagamento de Dividas");

        jLabel12.setText("Encomenda");

        ckOperacaoPagamentoDividaFornecedor.setText("Fornecedor");

        ckOperacaoEncomendaFornecedor.setText("Fornecedor");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ckOperacaoTransferenciaProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ckOperacaoActualizacaoStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ckOpercacaoDevolucao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(93, 93, 93)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ckOperacaoAlterarDataExpiracao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ckOperacaoAlterarPrecoVenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ckOperacaoAlterarCodigoBarra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ckOperacaoAnulacaoDocumento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ckOperacaoNotaDeCredito, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(49, 49, 49)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ckOperacaoLiquidarDivida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ckOperacaoNotaDeDebito, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(20, 20, 20))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(ckOperacaoEncomendaFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCheckBox14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(99, 99, 99)
                                .addComponent(ckOperacaoEncomendaCliente))
                            .addComponent(ckOperacaoPagamentoDividaFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(ckOperacaoTransferenciaProduto)
                    .addComponent(ckOperacaoAlterarDataExpiracao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ckOperacaoActualizacaoStock)
                            .addComponent(ckOperacaoAlterarPrecoVenda))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ckOperacaoAlterarCodigoBarra)
                            .addComponent(ckOpercacaoDevolucao))
                        .addGap(41, 41, 41)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ckOperacaoPagamentoDividaFornecedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ckOperacaoNotaDeCredito)
                            .addComponent(ckOperacaoNotaDeDebito))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ckOperacaoAnulacaoDocumento)
                            .addComponent(ckOperacaoLiquidarDivida))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(ckOperacaoEncomendaCliente))
                    .addComponent(ckOperacaoEncomendaFornecedor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCheckBox14)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Operações", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jCheckBox16.setText("Selecionar todos os itens");
        jCheckBox16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox16ActionPerformed(evt);
            }
        });

        ckTabelaCategoria.setText("Categoria");

        ckTabelaArmazem.setText("Armazém");

        ckTabelaFormaPagamento.setText("Forma de Pagamento");

        ckTabelaFormaIpressao.setText("Forma de Impressão");

        ckTabelaFabricante.setText("Fabricante");

        ckTabelaIPC.setText("IPC");

        ckTabelaCambio.setText("Cambio");

        jLabel6.setText("IVA");

        ckTabelaMotivoIsencao.setText("Motivo de Isenção");

        ckTabelaTaxa.setText("Taxa");

        ckTabelaSaft.setText("Saft");

        ckTabelaNumeracao.setText("Numeração Documento");

        ckTabelaAgruparEntradasVendas.setText("Agupar Entradas nas Vendas");

        ckTabelaAdicionarQTDTelaProduto.setText("Adicionar QTD Pela Tela de Produto");

        ckTabelaPermitirFechoDeCaixaAOOperador.setText("Permitir Fecho de Caixa ao Operador");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ckTabelaFormaIpressao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ckTabelaFormaPagamento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ckTabelaCategoria, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ckTabelaArmazem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(57, 57, 57)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ckTabelaFabricante, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                            .addComponent(ckTabelaCambio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ckTabelaIPC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ckTabelaMotivoIsencao)
                                    .addComponent(ckTabelaTaxa))
                                .addGap(98, 98, 98)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ckTabelaNumeracao)
                                    .addComponent(ckTabelaSaft))))
                        .addGap(122, 122, 122))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ckTabelaPermitirFechoDeCaixaAOOperador, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ckTabelaAdicionarQTDTelaProduto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ckTabelaAgruparEntradasVendas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ckTabelaCategoria)
                    .addComponent(ckTabelaIPC)
                    .addComponent(ckTabelaMotivoIsencao)
                    .addComponent(ckTabelaSaft))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ckTabelaArmazem)
                    .addComponent(ckTabelaFabricante)
                    .addComponent(ckTabelaTaxa)
                    .addComponent(ckTabelaNumeracao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ckTabelaFormaPagamento)
                    .addComponent(ckTabelaCambio))
                .addGap(18, 18, 18)
                .addComponent(ckTabelaFormaIpressao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ckTabelaAgruparEntradasVendas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ckTabelaAdicionarQTDTelaProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ckTabelaPermitirFechoDeCaixaAOOperador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jCheckBox16)
                .addGap(29, 29, 29))
        );

        jTabbedPane1.addTab("Tabela", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        ckClientePagas.setText("Pagas");

        jLabel2.setText("Cliente");

        ckClienteDividas.setText("Dividas");

        ckClienteDevolvidas.setText("Devolvidas");

        jLabel3.setText("Fornecedor");

        ckClientePagasPorCliente.setText("Pagas Por Cliente");

        ckRelatorioFornecedorEntrada.setText("Entrada");

        ckRelatorioFornecedorEncomenda.setText("Encomenda");

        ckRelatorioFornecedorTodos.setText("Todos");

        ckRelatorioProdutoStock.setText("Stock");

        jLabel4.setText("Produto");

        ckRelatorioProdutoStockEmBaixo.setText("Stock em Baixa");

        ckRelatorioProdutoPorArmazem.setText("Por Armazem");

        ckRelatorioProdutoEtiqueta.setText("Etiqueta");

        ckRelarioCategoria.setText("Categoria");

        ckRelarioArmazem.setText("Armazém");

        ckRelatorioFabricante.setText("Fabricante");

        ckRelatorioListaMovimento.setText("Lista de Movimento");

        jCheckBox44.setText("Selecione todos");
        jCheckBox44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox44ActionPerformed(evt);
            }
        });

        ckRelatorioClienteTodos.setText("Todos");

        ckRelatorioFornecedorEntradaPorFornecedor.setText("Entrada Por Fonecedor");

        ckRelatorioProdutoExpirado.setText("Expirado");

        ckRelatorioCaixa.setText("Caixa");

        ckRelatorioEntradaEStock.setText("Entrada e Stock");

        ckRelatorioProdutoPresteExpirar.setText("Preste a Expirar");

        ckRelatorioProdutoPresteTerminar.setText("Preste a Terminar");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jCheckBox44, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ckRelatorioFornecedorEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ckRelatorioFornecedorTodos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ckRelatorioFornecedorEntradaPorFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ckRelatorioFornecedorEncomenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(ckRelatorioProdutoStockEmBaixo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(ckRelatorioProdutoStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(ckRelatorioProdutoPorArmazem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(ckRelatorioProdutoEtiqueta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(ckRelatorioProdutoExpirado)
                                            .addComponent(ckRelatorioProdutoPresteExpirar)))
                                    .addComponent(ckRelatorioProdutoPresteTerminar))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ckRelatorioClienteTodos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ckClientePagasPorCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ckClientePagas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ckClienteDividas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ckClienteDevolvidas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(240, 240, 240)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ckRelatorioListaMovimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ckRelarioCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ckRelarioArmazem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ckRelatorioFabricante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ckRelatorioCaixa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ckRelatorioEntradaEStock, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                .addGap(275, 275, 275))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(253, 253, 253)
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ckClientePagas)
                            .addComponent(ckRelarioCategoria)
                            .addComponent(ckRelatorioEntradaEStock))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ckClienteDividas)
                            .addComponent(ckRelarioArmazem))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ckClienteDevolvidas)
                            .addComponent(ckRelatorioFabricante))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ckClientePagasPorCliente)
                            .addComponent(ckRelatorioListaMovimento))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ckRelatorioClienteTodos)
                        .addGap(14, 14, 14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(ckRelatorioCaixa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ckRelatorioFornecedorEncomenda)
                    .addComponent(ckRelatorioProdutoStock)
                    .addComponent(ckRelatorioProdutoPorArmazem)
                    .addComponent(ckRelatorioProdutoExpirado)
                    .addComponent(ckRelatorioFornecedorEntrada))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ckRelatorioFornecedorTodos)
                    .addComponent(ckRelatorioProdutoStockEmBaixo)
                    .addComponent(ckRelatorioProdutoEtiqueta)
                    .addComponent(ckRelatorioFornecedorEntradaPorFornecedor)
                    .addComponent(ckRelatorioProdutoPresteExpirar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ckRelatorioProdutoPresteTerminar)
                .addGap(9, 9, 9)
                .addComponent(jCheckBox44)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Relatório", jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        ckGraficoStockProduto.setText("Produto");

        jLabel9.setText("Stock");

        ckGraficoStockCategoria.setText("Categoria");

        ckGraficoCodigoBarra.setText("Código de Barra");

        ckGraficoStockFornecedor.setText("Fornecedor");

        ckGraficoStockFabricante.setText("Fabricante");

        ckGraficostockProdutoDetalhado.setText("Produto detalhado");

        jLabel10.setText("Venda");

        ckGraficoVendaProduto.setText("Produto");

        ckGraficoVendaCliente.setText("Cliente");

        ckGraficoVendaBalancoAnual.setText("Balanço Anual");

        ckGraficoVendaBalancoMensal.setText("Balanço Mensal");

        ckGrafico.setText("Selecionar todos os itens");
        ckGrafico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckGraficoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addGap(74, 74, 74))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ckGraficoCodigoBarra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ckGraficoStockCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(45, 45, 45)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ckGraficoStockFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                                    .addComponent(ckGraficoStockProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(25, 25, 25)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ckGraficoVendaBalancoAnual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ckGraficoVendaProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ckGraficoVendaBalancoMensal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ckGraficoVendaCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(ckGraficoStockFabricante, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(ckGraficostockProdutoDetalhado))
                    .addComponent(ckGrafico))
                .addGap(365, 365, 365))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ckGraficoStockProduto)
                            .addComponent(ckGraficoCodigoBarra))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ckGraficoStockCategoria))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ckGraficoVendaCliente)
                            .addComponent(ckGraficoVendaProduto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ckGraficoVendaBalancoAnual)
                            .addComponent(ckGraficoVendaBalancoMensal)
                            .addComponent(ckGraficoStockFornecedor))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ckGraficoStockFabricante)
                    .addComponent(ckGraficostockProdutoDetalhado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ckGrafico)
                .addGap(20, 20, 20))
        );

        jTabbedPane1.addTab("Gráfico", jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        ckSistemaUsuario.setText("Usuário");

        ckSistemaPermissao.setText("Permissão");

        ckSistemaEmpresa.setText("Empresa");

        ckSistemaListaDeUsuario.setText("Lista de Usuario");

        ckSistemaBackupDoSistema.setText("Backup do Sistema");

        ckSistema.setText("Selecionar todos os itens");
        ckSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckSistemaActionPerformed(evt);
            }
        });

        ckSistemaLogDeAcesso.setText("Log de Acesso");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ckSistema)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(ckSistemaLogDeAcesso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ckSistemaListaDeUsuario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ckSistemaEmpresa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ckSistemaPermissao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ckSistemaUsuario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ckSistemaBackupDoSistema, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(796, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(ckSistemaUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ckSistemaPermissao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ckSistemaEmpresa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ckSistemaListaDeUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ckSistemaBackupDoSistema)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ckSistemaLogDeAcesso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                .addComponent(ckSistema)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sistema", jPanel6);

        jPanel8.setBackground(java.awt.Color.white);

        ckAjuda.setText("Selecionar todos os itens");
        ckAjuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckAjudaActionPerformed(evt);
            }
        });

        ckAjudaManualSistema.setText("Manual do SIstema");

        ckAjudaEmpresa.setText("Empresa");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ckAjuda)
                    .addComponent(ckAjudaManualSistema)
                    .addComponent(ckAjudaEmpresa))
                .addContainerGap(796, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(ckAjudaManualSistema)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ckAjudaEmpresa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ckAjuda)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Ajuda", jPanel8);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        ckFicheiroProduto.setText("Produto");

        ckFicheiroFornecedor.setText("Fornecedor");

        ckFicheiroCliente.setText("Cliente");

        ckFicheiroEntradaProduto.setText("Entrada de Produto");

        jCheckBox15.setText("Selecionar todos os itens");
        jCheckBox15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox15ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ckFicheiroProduto)
                    .addComponent(ckFicheiroEntradaProduto)
                    .addComponent(ckFicheiroFornecedor)
                    .addComponent(ckFicheiroCliente)
                    .addComponent(jCheckBox15))
                .addContainerGap(796, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(ckFicheiroProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ckFicheiroFornecedor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ckFicheiroCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ckFicheiroEntradaProduto)
                .addGap(55, 55, 55)
                .addComponent(jCheckBox15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Ficheiro", jPanel1);

        jButton1.setText("Gravar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Lista de Permissão");

        jCheckBox23.setText("Selecionar todos os itens");
        jCheckBox23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox23ActionPerformed(evt);
            }
        });

        cboUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox23))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(733, 733, 733))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cboUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox23, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 1024, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1024, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        permissaoSalvar();
        JOptionPane.showMessageDialog(null, "Registado com Sucesso");
        permissao();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCheckBox15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox15ActionPerformed
        // TODO add your handling code here:
        ficheiro(jCheckBox15.isSelected());
    }//GEN-LAST:event_jCheckBox15ActionPerformed

    private void jCheckBox16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox16ActionPerformed
        // TODO add your handling code here:
       tabela(jCheckBox16.isSelected());
    }//GEN-LAST:event_jCheckBox16ActionPerformed

    private void jCheckBox14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox14ActionPerformed
        // TODO add your handling code here:
        operacoes(jCheckBox14.isSelected());
    }//GEN-LAST:event_jCheckBox14ActionPerformed

    private void jCheckBox23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox23ActionPerformed
        // TODO add your handling code here:
        
        todos(jCheckBox23.isSelected());
    }//GEN-LAST:event_jCheckBox23ActionPerformed

    private void jCheckBox44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox44ActionPerformed
        // TODO add your handling code here:
        relatorios(jCheckBox44.isSelected());
    }//GEN-LAST:event_jCheckBox44ActionPerformed

    private void ckGraficoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckGraficoActionPerformed
        // TODO add your handling code here:
        graficos(ckGrafico.isSelected());
    }//GEN-LAST:event_ckGraficoActionPerformed

    private void ckSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckSistemaActionPerformed
        // TODO add your handling code here:
        sistema(ckSistema.isSelected());
    }//GEN-LAST:event_ckSistemaActionPerformed

    private void ckAjudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckAjudaActionPerformed
        // TODO add your handling code here:
        ajuda(ckAjuda.isSelected());
    }//GEN-LAST:event_ckAjudaActionPerformed

    private void cboUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboUsuarioActionPerformed
        // TODO add your handling code here:
        if(getUsuario() != null)
            permissao();
    }//GEN-LAST:event_cboUsuarioActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PermissaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PermissaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PermissaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PermissaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PermissaView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboUsuario;
    private javax.swing.JCheckBox ckAjuda;
    private javax.swing.JCheckBox ckAjudaEmpresa;
    private javax.swing.JCheckBox ckAjudaManualSistema;
    private javax.swing.JCheckBox ckClienteDevolvidas;
    private javax.swing.JCheckBox ckClienteDividas;
    private javax.swing.JCheckBox ckClientePagas;
    private javax.swing.JCheckBox ckClientePagasPorCliente;
    private javax.swing.JCheckBox ckFicheiroCliente;
    private javax.swing.JCheckBox ckFicheiroEntradaProduto;
    private javax.swing.JCheckBox ckFicheiroFornecedor;
    private javax.swing.JCheckBox ckFicheiroProduto;
    private javax.swing.JCheckBox ckGrafico;
    private javax.swing.JCheckBox ckGraficoCodigoBarra;
    private javax.swing.JCheckBox ckGraficoStockCategoria;
    private javax.swing.JCheckBox ckGraficoStockFabricante;
    private javax.swing.JCheckBox ckGraficoStockFornecedor;
    private javax.swing.JCheckBox ckGraficoStockProduto;
    private javax.swing.JCheckBox ckGraficoVendaBalancoAnual;
    private javax.swing.JCheckBox ckGraficoVendaBalancoMensal;
    private javax.swing.JCheckBox ckGraficoVendaCliente;
    private javax.swing.JCheckBox ckGraficoVendaProduto;
    private javax.swing.JCheckBox ckGraficostockProdutoDetalhado;
    private javax.swing.JCheckBox ckOperacaoActualizacaoStock;
    private javax.swing.JCheckBox ckOperacaoAlterarCodigoBarra;
    private javax.swing.JCheckBox ckOperacaoAlterarDataExpiracao;
    private javax.swing.JCheckBox ckOperacaoAlterarPrecoVenda;
    private javax.swing.JCheckBox ckOperacaoAnulacaoDocumento;
    private javax.swing.JCheckBox ckOperacaoEncomendaCliente;
    private javax.swing.JCheckBox ckOperacaoEncomendaFornecedor;
    private javax.swing.JCheckBox ckOperacaoLiquidarDivida;
    private javax.swing.JCheckBox ckOperacaoNotaDeCredito;
    private javax.swing.JCheckBox ckOperacaoNotaDeDebito;
    private javax.swing.JCheckBox ckOperacaoPagamentoDividaFornecedor;
    private javax.swing.JCheckBox ckOperacaoTransferenciaProduto;
    private javax.swing.JCheckBox ckOpercacaoDevolucao;
    private javax.swing.JCheckBox ckRelarioArmazem;
    private javax.swing.JCheckBox ckRelarioCategoria;
    private javax.swing.JCheckBox ckRelatorioCaixa;
    private javax.swing.JCheckBox ckRelatorioClienteTodos;
    private javax.swing.JCheckBox ckRelatorioEntradaEStock;
    private javax.swing.JCheckBox ckRelatorioFabricante;
    private javax.swing.JCheckBox ckRelatorioFornecedorEncomenda;
    private javax.swing.JCheckBox ckRelatorioFornecedorEntrada;
    private javax.swing.JCheckBox ckRelatorioFornecedorEntradaPorFornecedor;
    private javax.swing.JCheckBox ckRelatorioFornecedorTodos;
    private javax.swing.JCheckBox ckRelatorioListaMovimento;
    private javax.swing.JCheckBox ckRelatorioProdutoEtiqueta;
    private javax.swing.JCheckBox ckRelatorioProdutoExpirado;
    private javax.swing.JCheckBox ckRelatorioProdutoPorArmazem;
    private javax.swing.JCheckBox ckRelatorioProdutoPresteExpirar;
    private javax.swing.JCheckBox ckRelatorioProdutoPresteTerminar;
    private javax.swing.JCheckBox ckRelatorioProdutoStock;
    private javax.swing.JCheckBox ckRelatorioProdutoStockEmBaixo;
    private javax.swing.JCheckBox ckSistema;
    private javax.swing.JCheckBox ckSistemaBackupDoSistema;
    private javax.swing.JCheckBox ckSistemaEmpresa;
    private javax.swing.JCheckBox ckSistemaListaDeUsuario;
    private javax.swing.JCheckBox ckSistemaLogDeAcesso;
    private javax.swing.JCheckBox ckSistemaPermissao;
    private javax.swing.JCheckBox ckSistemaUsuario;
    private javax.swing.JCheckBox ckTabelaAdicionarQTDTelaProduto;
    private javax.swing.JCheckBox ckTabelaAgruparEntradasVendas;
    private javax.swing.JCheckBox ckTabelaArmazem;
    private javax.swing.JCheckBox ckTabelaCambio;
    private javax.swing.JCheckBox ckTabelaCategoria;
    private javax.swing.JCheckBox ckTabelaFabricante;
    private javax.swing.JCheckBox ckTabelaFormaIpressao;
    private javax.swing.JCheckBox ckTabelaFormaPagamento;
    private javax.swing.JCheckBox ckTabelaIPC;
    private javax.swing.JCheckBox ckTabelaMotivoIsencao;
    private javax.swing.JCheckBox ckTabelaNumeracao;
    private javax.swing.JCheckBox ckTabelaPermitirFechoDeCaixaAOOperador;
    private javax.swing.JCheckBox ckTabelaSaft;
    private javax.swing.JCheckBox ckTabelaTaxa;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox14;
    private javax.swing.JCheckBox jCheckBox15;
    private javax.swing.JCheckBox jCheckBox16;
    private javax.swing.JCheckBox jCheckBox23;
    private javax.swing.JCheckBox jCheckBox44;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
