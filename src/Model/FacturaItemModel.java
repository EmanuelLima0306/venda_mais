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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author celso
 */
public class FacturaItemModel {
    
    private int id;
    private EstadoModel estado;
    private FacturaModel factura;
    private ProdutoModel produto;
    private List<BarbeiroItemModel> barbeiro = new ArrayList<>();
    private double preco;
    private double qtd;
    private double subTotal;
    private double iva;
    private double total;
    private double desconto;
    private double retencao;
    private String lote;

    public FacturaItemModel(int id, FacturaModel factura, ProdutoModel produto, double preco, int qtd, double subTotal, double iva) {
        this.id = id;
        this.factura = factura;
        this.produto = produto;
        this.preco = preco;
        this.qtd = qtd;
        this.subTotal = subTotal;
        this.iva = iva;
    }

    public FacturaItemModel(FacturaModel factura, ProdutoModel produto, double preco, int qtd, double subTotal, double iva) {
        this.factura = factura;
        this.produto = produto;
        this.preco = preco;
        this.qtd = qtd;
        this.subTotal = subTotal;
        this.iva = iva;
    }

    public FacturaItemModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FacturaModel getFactura() {
        return factura;
    }

    public void setFactura(FacturaModel factura) {
        this.factura = factura;
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

    public double getRetencao() {
        return retencao;
    }

    public void setRetencao(double retencao) {
        this.retencao = retencao;
    }
    
    

    public List<BarbeiroItemModel> getBarbeiro() {
        return barbeiro;
    }

    public void setBarbeiro(List<BarbeiroItemModel> barbeiro) {
        this.barbeiro = barbeiro;
    }
    
    
    
    
}
