/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.


(`Id`,
`IdFactura`,
`IdProduto`,
`Preco`,
`Qtd`,
`SubTotal`,
`Iva`)

 */
package Model;

/**
 *
 * @author celso
 */
public class EncomendaItemModel {
    
    private int id;
    private EstadoModel estado;
    private EncomendaModel encomenda;
    private ProdutoModel produto;
    private double preco;
    private double qtd;
    private double subTotal;
    private double iva;
    private double total;
    private double desconto;
    private String lote;

    public EncomendaItemModel(int id, EncomendaModel encomenda, ProdutoModel produto, double preco, int qtd, double subTotal, double iva) {
        this.id = id;
        this.encomenda = encomenda;
        this.produto = produto;
        this.preco = preco;
        this.qtd = qtd;
        this.subTotal = subTotal;
        this.iva = iva;
    }

    public EncomendaItemModel(EncomendaModel encomenda, ProdutoModel produto, double preco, int qtd, double subTotal, double iva) {
        this.encomenda = encomenda;
        this.produto = produto;
        this.preco = preco;
        this.qtd = qtd;
        this.subTotal = subTotal;
        this.iva = iva;
    }

    public EncomendaItemModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EncomendaModel getFactura() {
        return encomenda;
    }

    public void setFactura(EncomendaModel encomenda) {
        this.encomenda = encomenda;
    }

    public ProdutoModel getProduto() {
        return produto;
    }

    public void setProduto(ProdutoModel produto) {
        this.produto = produto;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public EstadoModel getEstado() {
        return estado;
    }

    public void setEstado(EstadoModel estado) {
        this.estado = estado;
    }

    public double getQtd() {
        return qtd;
    }

    public void setQtd(double qtd) {
        this.qtd = qtd;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    @Override
    public String toString() {
        return  produto.getDesignacao();
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
    
    
}
