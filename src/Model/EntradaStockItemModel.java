/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author celso
 */
public class EntradaStockItemModel {
    
    private int id;
    private EntradaStockModel entrada;
    private ProdutoModel produto;
    private String dataExpiracao;
    private double qtd,QtdControler,qtdTotal;
    private double precoCompra;
    private double precoVenda;
    private double precoUnitarioCompra;
    private double lucro;
    private double margemLucro;
    private String codBarra;
    private String lote = "";
    private EstadoModel estado = new EstadoModel(1, "");
   
    private ArmazemModel armazem;

    public EntradaStockItemModel(int id, EntradaStockModel entrada, ProdutoModel produto, double qtd, double precoCompra, double precoVenda) {
        this.id = id;
        this.entrada = entrada;
        this.produto = produto;
        this.qtd = qtd;
        this.precoCompra = precoCompra;
        this.precoVenda = precoVenda;
    }

    public EntradaStockItemModel(EntradaStockModel entrada, ProdutoModel produto, double qtd, double precoCompra, double precoVenda) {
        this.entrada = entrada;
        this.produto = produto;
        this.qtd = qtd;
        this.precoCompra = precoCompra;
        this.precoVenda = precoVenda;
    }

    public EntradaStockItemModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EntradaStockModel getEntrada() {
        return entrada;
    }

    public void setEntrada(EntradaStockModel entrada) {
        this.entrada = entrada;
    }

    public ProdutoModel getProduto() {
        return produto;
    }

    public void setProduto(ProdutoModel produto) {
        this.produto = produto;
    }

    public double getQtd() {
        return qtd;
    }

    public void setQtd(double qtd) {
        this.qtd = qtd;
    }

    public double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(double precoCompra) {
        this.precoCompra = precoCompra;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public String getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(String dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public String getCodBarra() {
        return codBarra;
    }

    public void setCodBarra(String codBarra) {
        this.codBarra = codBarra;
    }


    public ArmazemModel getArmazem() {
        return armazem;
    }

    public void setArmazem(ArmazemModel armazem) {
        this.armazem = armazem;
    }

    public EstadoModel getEstado() {
        return estado;
    }

    public void setEstado(EstadoModel estado) {
        this.estado = estado;
    }

    public double getQtdControler() {
        return QtdControler;
    }

    public void setQtdControler(double QtdControler) {
        this.QtdControler = QtdControler;
    }

    public double getQtdTotal() {
        return qtdTotal;
    }

    public void setQtdTotal(double qtdTotal) {
        this.qtdTotal = qtdTotal;
    }

    public double getPrecoUnitarioCompra() {
        return precoUnitarioCompra;
    }

    public void setPrecoUnitarioCompra(double precoUnitarioCompra) {
        this.precoUnitarioCompra = precoUnitarioCompra;
    }

    public double getLucro() {
        return lucro;
    }

    public void setLucro(double lucro) {
        this.lucro = lucro;
    }

    public double getMargemLucro() {
        return margemLucro;
    }

    public void setMargemLucro(double margemLucro) {
        this.margemLucro = margemLucro;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }
    
    

    @Override
    public String toString() {
        return codBarra ;
    }
    
    
    
}
