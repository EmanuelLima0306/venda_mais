/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;

import Controller.CaixaController;
import Controller.ClienteController;
import Controller.DocumentoController12;
import Controller.EntradaStockController;
import Controller.EntradaStockItemController;
import Controller.FacturaController;
import Controller.FacturaItemController;
import Controller.ParamentroController;
import Controller.ProdutoController;
import Controller.UsuarioController;
import Ireport.FacturaIreport;
import Model.ArmazemModel;
import Model.CaixaModel;
import Model.CategoriaModel;
import Model.ClienteModel;
import Model.EntradaStockItemModel;
import Model.EntradaStockModel;
import Model.EstadoModel;
import Model.FabricanteModel;
import Model.FacturaItemModel;
import Model.FacturaModel;
import Model.FormaPagamentoModel;
import Model.FornecedorModel;
import Model.Moeda;
import Model.Motivo;
import Model.ParamentroModel;
import Model.ProdutoModel;
import Model.Taxa;
import Model.TipoClienteModel;
import Model.UsuarioModel;
import Util.Calculo;
import Util.DataComponent;
import View.BalcaoView;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import json_xml_iva.RSA;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author emanuel
 */
public class Excel {
    File file;
    Workbook workbookk = null;
    UsuarioModel usuario = new UsuarioModel(2, "");
    EntradaStockItemModel entradaStockItemModel; 
    int id;
    String codBarra = "";
    CaixaModel caixaModel = null;
    String dataFactura;
    int indexDescricao, indexPreco, indexCodBarra, indexData, indexQTD, indexPrecoCompra, lastLinha, primeiraLinha = 1, indexLote;
    boolean allLInha = true, temPrecoCompra, salvarProdutos, salvarExistencias, temLote;
    public Excel(){
        
        
    }
    public Excel(int index[],boolean temPrecoCompra,boolean salvarProdutos,boolean salvarExistencias, boolean temLote, UsuarioModel usuarioModel){
      indexDescricao = index[0];
      indexPreco = index[1]; 
      indexCodBarra = index[2]; 
      indexData = index[3]; 
      indexQTD = index[4]; 
      indexPrecoCompra = index[5]; 
      lastLinha = index[6];
      primeiraLinha = index[7];
      indexLote = index[8];
      this.temPrecoCompra = temPrecoCompra;
      this.salvarProdutos = salvarProdutos;
      this.salvarExistencias = salvarExistencias;
      this.usuario = usuarioModel;
      this.temLote = temLote;
    }
    
    public void updateLote(){
        EntradaStockItemController controller = new EntradaStockItemController();
        
        for(EntradaStockItemModel e : controller.getAll()){
            System.out.println("Lote>>> "+e.getLote()+"\nCodBarra>>>> "+e.getCodBarra());
            
            
            if(e.getLote() == null){
                
                System.out.println("Id::::: "+e.getId());
                e.setLote(e.getCodBarra());
                controller.updateLoteItem(e);
            }else{
                if(e.getLote().isEmpty()){
                    e.setLote(e.getCodBarra());
                    controller.updateLoteItem(e);
                }
            }
                
        }
       
        JOptionPane.showMessageDialog(null, "Terminado com sucesso");
        
    }
    
    public void salvar(){
        
        
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int resultado = fc.showOpenDialog(null);
        
        if(resultado == JFileChooser.CANCEL_OPTION){
            
        }else{
            
                file = fc.getSelectedFile();
                try{
                        workbookk = new HSSFWorkbook(new FileInputStream(file));

                        if(salvarProdutos)
                            salvarProdutos();
                        //InserirFacturas();
                        if(salvarExistencias)
                            salvarExtoques();
                    }catch(Exception e){
                        System.out.println(e);
                    }

            }
        
        
    }
    
    private void gravarEntradaDEfault() {

        gravarEntradaItem(gravarEntrada());
    }
    private void gravarEntradaDEfault(ProdutoModel p, String codbar, int existencia, String dataEntrada, double precoVenda, double precoCompra, String lote) {

        gravarEntradaItem(gravarEntrada(dataEntrada),p,codbar,existencia,precoVenda,precoCompra,lote);
    }
    
    private boolean gravarEntradaItem(EntradaStockModel entrada) {

        boolean flag = false;

        if (entrada.getId() > 0) {

            EntradaStockModel eModelo = entrada;

            ParamentroController paramentroController = new ParamentroController();

            EntradaStockItemModel modelo = new EntradaStockItemModel();
            ProdutoModel produto = this.getLastProduto();

            if (entradaStockItemModel.getId() > 0) { // verificamos se já tem itens de entrada

                modelo = entradaStockItemModel;

                produto = new ProdutoModel();
                if (id > 0) {
                    produto.setId(id);
                }

                modelo.setProduto(produto);
            } else {

                modelo.setProduto(produto);
            }

            ParamentroModel paramentroModel = paramentroController.getById(6);

            modelo.setArmazem(new ArmazemModel(paramentroModel.getValor()));

            if (codBarra.trim().isEmpty()) {
                modelo.setCodBarra("" + modelo.getProduto().getId() + "974");
            } else {
                modelo.setCodBarra(codBarra.trim());
            }

            modelo.setQtd(100000);

            modelo.setPrecoCompra(0);

            modelo.setPrecoVenda(produto.getValorVenda());
            modelo.setEntrada(entrada);
            modelo.setDataExpiracao("");
            modelo.setEstado(new EstadoModel(1, ""));

            EntradaStockItemController controller = new EntradaStockItemController();

            if (controller.saveOrUpdate(modelo)) {
                controller.updatePreco(modelo);

//                modelo = controller.getUso(produto);
//                definirEntraProdutoItemEmUso(modelo);

                flag = true;
            } else {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro ao dar entrada do stock", "ERRO 02-ASCEMIL/20", JOptionPane.ERROR_MESSAGE);
            }

        } else {

            //flag = gravarEntradaItem();
        }

        return flag;
    }
    private boolean gravarEntradaItem(EntradaStockModel entrada,ProdutoModel p, String codBarra,int existencia, double precoVenda, double precoCompra, String lote) {

        boolean flag = false;

        if (entrada.getId() > 0) {

            EntradaStockModel eModelo = entrada;

            ParamentroController paramentroController = new ParamentroController();

            EntradaStockItemModel modelo = new EntradaStockItemModel();
            ProdutoModel produto = p;

            if (entradaStockItemModel.getId() > 0) { // verificamos se já tem itens de entrada

                modelo = entradaStockItemModel;

                produto = new ProdutoModel();
                if (id > 0) {
                    produto.setId(id);
                }

                modelo.setProduto(produto);
            } else {

                modelo.setProduto(produto);
            }

            ParamentroModel paramentroModel = paramentroController.getById(6);

            modelo.setArmazem(new ArmazemModel(paramentroModel.getValor()));

            if (codBarra.trim().isEmpty()) {
                modelo.setCodBarra("" + modelo.getProduto().getId() + "974");
            } else {
                modelo.setCodBarra(codBarra.trim());
            }

            modelo.setQtd(existencia);
            modelo.setLote(lote);
            modelo.setPrecoCompra(precoCompra);

            modelo.setPrecoVenda(precoVenda);
            modelo.setEntrada(entrada);
            modelo.setQtdTotal(existencia);
            modelo.setDataExpiracao("2023-12-31");
            modelo.setEstado(new EstadoModel(1, ""));

            EntradaStockItemController controller = new EntradaStockItemController();

            if (controller.saveOrUpdate(modelo)) {
                controller.updatePreco(modelo);

//                modelo = controller.getUso(produto);
//                definirEntraProdutoItemEmUso(modelo);

                flag = true;
            } else {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro ao dar entrada do stock", "ERRO 02-ASCEMIL/20", JOptionPane.ERROR_MESSAGE);
            }

        } else {

            //flag = gravarEntradaItem();
        }

        return flag;
    }
    
    private ProdutoModel getLastProduto() {

        ProdutoController controller = new ProdutoController();
        return controller.getLast();
    }
    
    private EntradaStockModel gravarEntrada() {

        if (id <= 0) {

            entradaStockItemModel = new EntradaStockItemModel(); // dizemos que ainda não tem item de entrada

            FornecedorModel f = new FornecedorModel(1, "");

            FormaPagamentoModel fp = new FormaPagamentoModel(1, "Numerario", true, false, new EstadoModel(1, ""));

            EntradaStockModel modelo = new EntradaStockModel();
            modelo.setData(dataFactura);
            modelo.setDataFactura(dataFactura);
            modelo.setEstado(new EstadoModel(1, ""));
            modelo.setFormaPagamento(fp);
            modelo.setFornecedor(f);
            modelo.setNumFactura("0000D");
            modelo.setTemDivida(!fp.isCash() && !fp.isMulticaixa());
            modelo.setTotal(0);
            modelo.setUsuario(usuario);

//            if (!modelo.isEmpty()) {

                EntradaStockController controller = new EntradaStockController();

                if (controller.saveOrUpdate(modelo)) {

                    return controller.getLastEntrada(usuario);
                } else {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro ao dar entrada do stock", "ERRO 02-ASCEMIL/20", JOptionPane.ERROR_MESSAGE);
                }
//
//            } else {
//                JOptionPane.showMessageDialog(null, "Verifica os dados da factura");
//            }
        }

        EntradaStockModel modelo = new EntradaStockModel();
        modelo.setId(1);
        return modelo;

    }
    private EntradaStockModel gravarEntrada(String data) {

        if (id <= 0) {

            entradaStockItemModel = new EntradaStockItemModel(); // dizemos que ainda não tem item de entrada

            FornecedorModel f = new FornecedorModel(1, "");

            FormaPagamentoModel fp = new FormaPagamentoModel(1, "Numerario", true, false, new EstadoModel(1, ""));

            EntradaStockModel modelo = new EntradaStockModel();
            modelo.setData(data);
            modelo.setDataFactura(data);
            modelo.setEstado(new EstadoModel(1, ""));
            modelo.setFormaPagamento(fp);
            modelo.setFornecedor(f);
            modelo.setNumFactura("0000D");
            modelo.setTemDivida(!fp.isCash() && !fp.isMulticaixa());
            modelo.setTotal(0);
            modelo.setUsuario(usuario);

//            if (!modelo.isEmpty()) {

                EntradaStockController controller = new EntradaStockController();

                if (controller.saveOrUpdate(modelo)) {

                    return controller.getLastEntrada(usuario);
                } else {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro ao dar entrada do stock", "ERRO 02-ASCEMIL/20", JOptionPane.ERROR_MESSAGE);
                }
//
//            } else {
//                JOptionPane.showMessageDialog(null, "Verifica os dados da factura");
//            }
        }

        EntradaStockModel modelo = new EntradaStockModel();
        modelo.setId(1);
        return modelo;

    }
    
    
    
    public void salvarProdutos(){
        System.out.println("funciona 1");
        if(workbookk != null){
                Sheet sheet = workbookk.getSheetAt(0);
                System.out.println("funciona 2");
                if(allLInha)
                    lastLinha = sheet.getLastRowNum();
                
                ProdutoModel p;
                ProdutoController pc = new ProdutoController();
                for(int i = primeiraLinha; i < lastLinha; i++){
                   
                    Cell des = sheet.getRow(i).getCell(indexDescricao);
                    Cell pr = sheet.getRow(i).getCell(indexPreco);
                    String qr;
                    if(indexCodBarra > 0)
                        qr = sheet.getRow(i).getCell(indexCodBarra).getStringCellValue();
                    else
                        qr = getCodBarra();
                    
                    Cell data = sheet.getRow(i).getCell(indexData);
                    System.out.println("Descricao 1 >>>>> "+des);
                    if(des != null)
                        if(pc.get(des.getStringCellValue()).size() <= 0){
                            System.out.println("Descricao>>>>> "+des);
                            codBarra = qr;
                            
                            dataFactura = DataComponent.getData(data.getDateCellValue());
                            if(dataFactura.isEmpty() )
                            dataFactura = DataComponent.getDataActual();
                            String preco = pr.getStringCellValue();
                            preco = preco.replaceAll(" ", "");
                            preco = preco.replace(",00", "");
                            System.out.println("data>>>>>"  +dataFactura);
                            System.out.println("precooo >>> "+preco);
                            
                            p = new ProdutoModel();
                            p.setDesignacao(des.getStringCellValue());
                            p.setValorVenda(Double.parseDouble(preco));
                            p.setCategoria(new CategoriaModel(1, ""));
                            p.setFabricante(new FabricanteModel(1, ""));
                            p.setExpira(false);
                            p.setStocavel(true);
                            p.setIpc(false);
                            p.setData(dataFactura);
                            p.setUsuario(usuario);
                            p.setStockMinimo(0);
                            p.setQuantCritica(0);
                            p.setDiaAlerta(0);
                            p.setDiaDevolucao(0);
                            p.setEstado(new EstadoModel(1, ""));
                            p.setOrganizacao("ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)");
                            p.setMotivo(new Motivo(2, "", ""));
                            p.setTaxa(new Taxa(1, "", 0.0));
                            p.setUrlImage("null.png");
                            p.setIsMenuDia(true);
                            if(pc.saveOrUpdate(p)){
                               //gravarEntradaDEfault();
                            }
                        }
                    
                }
            
            }
    }
    
    public double getQTDTotal(String designacao){
         Sheet sheet = workbookk.getSheetAt(0);
         double total = 0;
        int linha;
        if(lastLinha > 0)
            linha = lastLinha;
        else
            linha = sheet.getLastRowNum();

        ProdutoModel p;
        ProdutoController pc = new ProdutoController();

        for(int i = primeiraLinha; i < linha; i++){
            
            if(!designacao.isEmpty()){
                
                Cell existencias = sheet.getRow(i).getCell(indexQTD);
                Cell desc = sheet.getRow(i).getCell(indexDescricao);
                if (desc != null) {
                    if(desc.getStringCellValue().equals(designacao)){
                        total += existencias.getNumericCellValue();
                    }
                }
            }
        }
        return total;        
    }
    public void salvarExtoques(){
        if(workbookk != null){
                Sheet sheet = workbookk.getSheetAt(0);
//                Cell c0 = sheet.getRow(0).getCell(0);
//                String titulo = c0.getStringCellValue();
                int linha;
                if(lastLinha > 0)
                    linha = lastLinha;
                else
                    linha = sheet.getLastRowNum();
                
                ProdutoModel p;
                ProdutoController pc = new ProdutoController();
                
                for(int i = primeiraLinha; i < linha; i++){
                    
                    String cBarra;
                    String lote = "";
                    double precoCompr = 0;
                    if(indexCodBarra > 0){
                        cBarra = sheet.getRow(i).getCell(indexCodBarra).getStringCellValue(); 
                    }else{
                        cBarra = getCodBarra();
                    }
                    
                    if(indexLote > 0 ){
                         lote = sheet.getRow(i).getCell(indexLote).getStringCellValue();
                    }
                    Cell desc = sheet.getRow(i).getCell(indexDescricao);
                    double existencias = getQTDTotal(desc != null ? desc.getStringCellValue(): "");
                    
                    if(indexPrecoCompra > 0){
                     Cell precoCompra = sheet.getRow(i).getCell(indexPrecoCompra);
                     precoCompr = precoCompra.getNumericCellValue();
                    }
                    
                    Cell precoVenda = sheet.getRow(i).getCell(indexPreco);
                    
                    Cell data = sheet.getRow(i).getCell(indexData);
                    
                    if(existencias > 0 ){
                        System.out.println("Preco>>>>>> "+precoVenda.getStringCellValue());
                        
                            p = pc.getAll(desc.getStringCellValue());
                            
                            EntradaStockItemController entradaItemController = new EntradaStockItemController();
                            String dat = "";
                            
                                
                        dat = DataComponent.getData(data.getDateCellValue());
                        if(dat.isEmpty())
                            dat = DataComponent.getDataActual();

                        System.out.println("chegouuuuu 22222");
                        String preco = precoVenda.getStringCellValue().replace(",00", "");
                        preco = preco.replaceAll(" ", "");
                        if(p.getId() <= 0 ){

                            this.codBarra = cBarra;
                            dataFactura = dat;


                            p = new ProdutoModel();
                            p.setDesignacao(desc.getStringCellValue());
                            p.setValorVenda(Double.parseDouble(preco));
                            p.setCategoria(new CategoriaModel(1, ""));
                            p.setFabricante(new FabricanteModel(1, ""));
                            p.setExpira(true);
                            p.setStocavel(true);
                            p.setIpc(false);
                            p.setData(dataFactura);
                            p.setUsuario(usuario);
                            p.setStockMinimo(0);
                            p.setQuantCritica(0);
                            p.setDiaAlerta(0);
                            p.setDiaDevolucao(0);
                            p.setEstado(new EstadoModel(1, ""));
                            p.setOrganizacao("ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)");
                            p.setMotivo(new Motivo(2, "", ""));
                            p.setTaxa(new Taxa(1, "", 0.0));
                            p.setUrlImage("null.png");
                            p.setIsMenuDia(true);
                            if(pc.saveOrUpdate(p)){
                                p = this.getLastProduto();
                                if(entradaItemController.getByProduto(p).size() <= 0){
                                    gravarEntradaDEfault(p,cBarra, (int) existencias,dat,Double.parseDouble(preco), precoCompr, lote);
                                    
                                }

                            }
                        }else{
                            if(entradaItemController.getByProduto(p).size() <= 0){
                                gravarEntradaDEfault(p,cBarra, (int) existencias,dat,Double.parseDouble(preco), precoCompr, lote);
                            }

                        }
                            
                    }
                    
                }
            
            }
    }
    
    private String getCodBarra() {

        Random gerarNumero = new Random();
        EntradaStockItemController controller = new EntradaStockItemController();
        String codBarra = "17" + String.valueOf(gerarNumero.nextInt()).replaceFirst("-", "");

        if (!controller.getCodBarraExist(codBarra)) {
            return codBarra;
        }
        return getCodBarra();

    }
    
    
    /********************************* facturacao *********************/
    
    public double getTotalFactura(String codFactura){
                Sheet sheet = workbookk.getSheetAt(0);

                int linha = sheet.getLastRowNum();
                double total = 0;
                for(int i = 1; i < linha; i++){
                    Cell cod = sheet.getRow(i).getCell(2);
                    Cell qtd = sheet.getRow(i).getCell(6);
                    
                    if(cod != null)
                        if (cod.getStringCellValue().equals(codFactura)){
                            if(qtd.getNumericCellValue()>0){
                                Cell valor = sheet.getRow(i).getCell(11);
                                total += valor.getNumericCellValue();
                            }
                        }
                }
        return total;
    }
    public double getTotalDesconto(String codFactura){
                Sheet sheet = workbookk.getSheetAt(0);

                int linha = sheet.getLastRowNum();
                double total = 0;
                for(int i = 1; i < linha; i++){
                    Cell cod = sheet.getRow(i).getCell(2);
                    Cell desconto = sheet.getRow(i).getCell(9);
                    Cell qtd = sheet.getRow(i).getCell(6);
                    if(cod != null)
                    if(cod.getStringCellValue().equals(codFactura) && desconto.getNumericCellValue() > 0 && qtd.getNumericCellValue()>0){
                        
                        Cell valor = sheet.getRow(i).getCell(8);
                        total += (qtd.getNumericCellValue() * valor.getNumericCellValue())*(desconto.getNumericCellValue()/100);
                    }
                }
        return total;
    }
    public double getSubTotalFactura(String codFactura){
                Sheet sheet = workbookk.getSheetAt(0);

                int linha = sheet.getLastRowNum();
                double total = 0;
                
                for(int i = 1; i < linha; i++){
                    Cell cod = sheet.getRow(i).getCell(2);
                    Cell qtd =   sheet.getRow(i).getCell(6);
                    if(cod != null)
                        if(cod.getStringCellValue().equals(codFactura) && qtd.getNumericCellValue()>0){
                            Cell valor = sheet.getRow(i).getCell(8);

                            total += valor.getNumericCellValue() * qtd.getNumericCellValue();
                        }
                }
        return total;
    }
    public ClienteModel getCliente(String codFactura){
                Sheet sheet = workbookk.getSheetAt(0);

                int linha = sheet.getLastRowNum();
                
                for(int i = 1; i < linha; i++){
                    Cell cod = sheet.getRow(i).getCell(2);
                    if(cod.getStringCellValue().equals(codFactura)){
                        Cell nome = sheet.getRow(i).getCell(16);
                        ClienteModel cl;
                        if(nome.getStringCellValue().equalsIgnoreCase("CLIENTES DIVERSOS...")){
                            cl = new ClienteModel(1, "");
                            return cl;
                        }else{
                            ClienteController cc = new ClienteController();
                            cl = cc.getAll(nome.getStringCellValue());
                            if(cl != null){
                                
                                return cl;
                            }else{
                                cl = new ClienteModel();
                                cl.setNome(nome.getStringCellValue());
                                cl.setNif("999999999");
                                cl.setUsuario(usuario);
                                cl.setEstado(new EstadoModel(1, ""));
                                cl.setTipoCliente(new TipoClienteModel(1, ""));
                                if(cc.saveOrUpdate(cl)){
                                    return cc.getLast();
                                }
                                    
                            }
                        }
                        
                    }
                }
        return null;
    }
    
   
    
    private FacturaModel getFactura(Sheet sheet, int linha) {

        FacturaModel facturaModel = new FacturaModel();

        facturaModel.setFormaPagamento(new FormaPagamentoModel(1));

        Moeda moeda = new Moeda(1, "");
        facturaModel.setMoeda(moeda);
        facturaModel.setEstado(new EstadoModel(1, ""));
        facturaModel.setData(dataFactura+DataComponent.gethoras());
        facturaModel.setUsuario(usuario);
        facturaModel.setTroco(0);
        
        Cell codFactura = sheet.getRow(linha).getCell(2);
        
        facturaModel.setValorEntregue(getTotalFactura(codFactura.getStringCellValue()));
        facturaModel.setValorMulticaixa(0);
        facturaModel.setTotalApagar(getTotalFactura(codFactura.getStringCellValue()));
        facturaModel.setSubTotal(getSubTotalFactura(codFactura.getStringCellValue()));
        facturaModel.setTotalIVA(0);
        facturaModel.setTotalDesconto(getTotalDesconto(codFactura.getStringCellValue()));
        facturaModel.setTotalRetencao(0);

        facturaModel.setLocalDescarga("");
        facturaModel.setLocalCarga("");
        facturaModel.setObs("");

        facturaModel.setCaixaModel(caixaModel);

        ParamentroController paramentroController = new ParamentroController();
        ParamentroModel moduloFormacao = paramentroController.getById(7);
        facturaModel.setCriada_modulo_formacao(moduloFormacao.getValor() == 1);


        facturaModel.setTipoFacturas("Venda");
        ClienteModel clienteModel = getCliente(codFactura.getStringCellValue());
        facturaModel.setCliente(clienteModel);

            facturaModel.setNomeCliente(clienteModel.getNome());
        return facturaModel;
    }
    
    public void selecionarUsuario(String nome){
        UsuarioController uc = new UsuarioController();
        usuario = uc.get(nome).get(0);
    }
    
    
    public void InserirFacturas(){
        
        if(workbookk != null){
                Sheet sheet = workbookk.getSheetAt(0);
                Cell c0 = sheet.getRow(0).getCell(0);
                String titulo = c0.getStringCellValue();
                String lastFatura = "";
                String lastData ="", lastUsuario = "";
                int linha = sheet.getLastRowNum();
                
                for(int i = 1; i < linha; i++){
                    
                    Cell codFactura = sheet.getRow(i).getCell(2);
                    Cell nomeUsuario = sheet.getRow(i).getCell(0);
                    Cell dataFatura = sheet.getRow(i).getCell(1);
                    Cell nomeProduto = sheet.getRow(i).getCell(5);
                    Cell codBar = sheet.getRow(i).getCell(4);
                    Cell qtd = sheet.getRow(i).getCell(6);
                    Cell pr = sheet.getRow(i).getCell(8);
                    Cell desc = sheet.getRow(i).getCell(9);
                    Cell total = sheet.getRow(i).getCell(11);
                    
                    if(qtd != null)
                        if(qtd.getNumericCellValue()>0 && codFactura != null){
                            boolean result = false;
                            FacturaModel facturaModel;

                            if(!DataComponent.getData(dataFatura.getDateCellValue()).equals(lastData) || !nomeUsuario.getStringCellValue().equals(lastUsuario)){
                                lastData = DataComponent.getData(dataFatura.getDateCellValue());
                                lastUsuario = nomeUsuario.getStringCellValue();

                                CaixaController cc = new CaixaController();
                                if(caixaModel != null ){

                                    caixaModel.setUsuario(usuario);
                                    caixaModel.setDataFecho(dataFactura);
                                    caixaModel.setEstado("Fechado");
                                    cc.saveOrUpdate(caixaModel);
                                    caixaModel = null;
                                }
                                selecionarUsuario(nomeUsuario.getStringCellValue());
                                dataFactura = DataComponent.getData(dataFatura.getDateCellValue());
                                if(caixaModel == null){
                                    caixaModel = new CaixaModel();
                                    caixaModel.setUsuario(usuario);
                                    caixaModel.setDataAbertura(dataFactura);
                                    caixaModel.setValorInicial(0.0);
                                    caixaModel.setEstado("Aberto");
                                    cc.saveOrUpdate(caixaModel);
                                    caixaModel = cc.getLastByUsuario(usuario.getId(), "Aberto");
                                }
                            }

                            if(!codFactura.getStringCellValue().equals(lastFatura)){

                                facturaModel = getFactura(sheet, i);
                                lastFatura = codFactura.getStringCellValue();

                            }
                            else{
                                    FacturaController fc = new FacturaController();
                                    facturaModel = fc.getById(fc.getLastId());
                                }


                            if (!facturaModel.isEmpty()) {


    //                            if (facturaModel.getTipoFacturas().equals("Venda") && !facturaModel.isValorValido()) {
    //
    //                                JOptionPane.showMessageDialog(null, "Valor entregue insuficiente");
    //                                return;
    //                            }
                                //            if (facturaModel.getTroco() >= 0) {
                                FacturaController controller = new FacturaController();

                                //                Integer idUltimaFactura = controller.getLastId();
                                Integer idUltimaFactura = lastIdFactura(controller, facturaModel);
                                Integer idSerie = controller.getIdSerie();
                                facturaModel.setIdSerie(idSerie);
                                boolean salvo = true; 
                                if(facturaModel.getId() <= 0 ){
                                    try {
                                            salvo = controller.saveOrUpdate(facturaModel);
                                            
                                            /*descomentar a lastLinha abaixo depois*/
//                                            facturaModel.setId(controller.getLastIdByUsuario(usuario));

                                            facturaModel.setId(controller.getLastIdByUsuario(usuario));

                                            FacturaModel facturaAnterior = controller.getById(idUltimaFactura);
                                            String hashAnterior = null;
                                            if (facturaAnterior != null) {

                                                hashAnterior = facturaAnterior.getHash();

                                            }

                                            String referencia = updateNumeracao(facturaModel.getId());
                                            String hashcode = RSA.executeAlgRSA(facturaModel.getData(), referencia, String.valueOf(facturaModel.getTotalApagar()), hashAnterior);
                                            String facturaRef = RSA.getValorCaracterHash(hashcode);
                                            salvo = controller.updateHashReference(hashcode, facturaRef, facturaModel.getId());

                                    } catch (NoSuchAlgorithmException ex) {
                                        Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (InvalidKeyException ex) {
                                        Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (SignatureException ex) {
                                        Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                }

                                if (salvo) {

                                    try {


                                        if (salvo)
                                        {

                                                FacturaItemModel item = new FacturaItemModel();
                                                ProdutoController pc = new ProdutoController();
                                                item.setProduto(pc.getAll(nomeProduto.getStringCellValue()));

                                                FacturaItemModel facturaItemModel = new FacturaItemModel();
                                                facturaItemModel.setFactura(facturaModel);
                                                facturaItemModel.setProduto(item.getProduto());
                                                facturaItemModel.setLote(codBar.getStringCellValue());
                                                facturaItemModel.setQtd(qtd.getNumericCellValue());
                                                facturaItemModel.setPreco(pr.getNumericCellValue());
                                                facturaItemModel.setIva(0);
                                                facturaItemModel.setDesconto((pr.getNumericCellValue()*qtd.getNumericCellValue())*(desc.getNumericCellValue()/100));
                                                facturaItemModel.setTotal(total.getNumericCellValue());
                                                facturaItemModel.setSubTotal(pr.getNumericCellValue()*qtd.getNumericCellValue());
                                                facturaItemModel.setRetencao(0);

                                                FacturaItemController fController = new FacturaItemController();
                                                if (fController.saveOrUpdate(facturaItemModel)) {


                                                    result = true;
                                                } else {
                                                    result = false;
                                                    JOptionPane.showMessageDialog(null, "Ocorreu um erro regista o item da factura", "ERRO", JOptionPane.ERROR_MESSAGE);
                                                    return;
                                                }

                                        }

                                    } catch (Exception ex) {
                                        Logger.getLogger(BalcaoView.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                } else {
                                    JOptionPane.showMessageDialog(null, "Não foi possivel salvar a factura", "ERRO", JOptionPane.ERROR_MESSAGE);
                                }

                                //            } else {
                                //                JOptionPane.showMessageDialog(this, "Valor pago insuficiente");
                                //            }
                            } else {
                                JOptionPane.showMessageDialog(null, "Selecione a forma de pagamento");
                            }

                        }
                    
                   
                    
                }
                
                CaixaController cc = new CaixaController();
                if(caixaModel != null ){

                    caixaModel.setUsuario(usuario);
                    caixaModel.setDataFecho(dataFactura);
                    caixaModel.setEstado("Fechado");
                    cc.saveOrUpdate(caixaModel);
                    caixaModel = null;
                }
            
            }
       
        
    }
    
    public String getNextFacturaSimples(String tipoDoc, int nFact) {
        DocumentoController12 docController = new DocumentoController12();
        int last = docController.getLastInsertAno(tipoDoc);
        int next = last;
        FacturaController controller = new FacturaController();
        String serie = controller.getSerie().trim();

        System.out.println("serie :::: " + serie);

        return serie + DataComponent.getAnoActual() + "/" + next;
//        return "Z00" + DataComponent.getAnoActual() + "/" + nFact;
//        return "AGT" + DataComponent.getAnoActual() + "/" + next;
    }
    
    private String updateNumeracao(int numFacturaLast) {

        String designacao, prefixo;
        
        designacao = "FACTURA RECIBO";
        prefixo = "FR ";

        String nextSimples = getNextFacturaSimples(designacao, numFacturaLast);
        nextSimples = prefixo + nextSimples;
        System.out.println("nextSimples >>>>" + nextSimples);
//        Documento modelo = docController.getAll(designacao);
//        modelo.setNext(String.valueOf(numFacturaLast));

        FacturaController fController = new FacturaController();
        fController.updateNextFactura(nextSimples, numFacturaLast);

        DocumentoController12 docController = new DocumentoController12();
        docController.updateNextNumDoc(designacao);

        return nextSimples;
    }
    
    int lastIdFactura(FacturaController controller, FacturaModel factura) {

//        if (false) {
//
//            return controller.getLastIdFP();
//        } else if (factura.getTipoFacturas().trim().equals("Venda")) {

            return controller.getLastIdFR();
//        }
//
//        return controller.getLastIdFT();
    }
    
    public static void main(String args[]){
        Excel excel = new Excel();
        
    }
    
}
