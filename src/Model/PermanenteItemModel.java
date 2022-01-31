/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author emanuel
 */
public class PermanenteItemModel {
    private int id;
    private ProdutoModel produto;
    private double totalSaida;
    private double totalPerca;
    private double preco;
    private double total;
    private PermanenteModel permanente;
    private EstadoModel estado;
    private double stock;
    private double qtdVendida;
    private double taxaIva;
    private double totalVendido;
    private double qtdEntrada;
    
    public PermanenteItemModel() {
    }

    public PermanenteItemModel(int id, ProdutoModel produto, double totalSaida, double totalPerca, double preco, double total, PermanenteModel permanente, EstadoModel estado, double stock) {
        this.id = id;
        this.produto = produto;
        this.totalSaida = totalSaida;
        this.totalPerca = totalPerca;
        this.preco = preco;
        this.total = total;
        this.permanente = permanente;
        this.estado = estado;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProdutoModel getProduto() {
        return produto;
    }

    public void setProduto(ProdutoModel produto) {
        this.produto = produto;
    }

    public double getTotalSaida() {
        return totalSaida;
    }

    public void setTotalSaida(double totalSaida) {
        this.totalSaida = totalSaida;
    }

    public double getTotalPerca() {
        return totalPerca;
    }

    public void setTotalPerca(double totalPerca) {
        this.totalPerca = totalPerca;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public PermanenteModel getPermanente() {
        return permanente;
    }

    public void setPermanente(PermanenteModel permanente) {
        this.permanente = permanente;
    }

    public EstadoModel getEstado() {
        return estado;
    }

    public void setEstado(EstadoModel estado) {
        this.estado = estado;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public double getQtdVendida() {
        return qtdVendida;
    }

    public void setQtdVendida(double qtdVendida) {
        this.qtdVendida = qtdVendida;
    }

    public double getTaxaIva() {
        return taxaIva;
    }

    public void setTaxaIva(double taxaIva) {
        this.taxaIva = taxaIva;
    }

    public double getTotalVendido() {
        return totalVendido;
    }

    public void setTotalVendido(double totalVendido) {
        this.totalVendido = totalVendido;
    }

    public double getQtdEntrada() {
        return qtdEntrada;
    }

    public void setQtdEntrada(double qtdEntrada) {
        this.qtdEntrada = qtdEntrada;
    }
    
    
    
    
}
