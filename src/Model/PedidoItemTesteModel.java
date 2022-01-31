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
public class PedidoItemTesteModel {
    
    private Integer id = 0;
    private ProdutoModel produto;
    private EstadoModel estado;
    private Double preco;
    private Double iva;
    private Double desconto;
    private Double qtd;
    private Double subTotal;
    private Double retencao;
    private Double total;
    private String coBarra;
    private UsuarioModel usuario;
    private PedidoModel pedido;

    public PedidoItemTesteModel(Integer id , ProdutoModel produto, EstadoModel estado, Double preco, Double iva, Double desconto, Double qtd, String coBarra) {
        this.id = id;
        
        this.produto = produto;
        this.estado = estado;
        this.preco = preco;
        this.iva = iva;
        this.desconto = desconto;
        this.qtd = qtd;
        this.coBarra = coBarra;
    }

 

    public Double getQtd() {
        return qtd;
    }

    public void setQtd(Double qtd) {
        this.qtd = qtd;
    }

    public String getCoBarra() {
        return coBarra;
    }

    public void setCoBarra(String coBarra) {
        this.coBarra = coBarra;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public PedidoModel getPedido() {
        return pedido;
    }

    public void setPedido(PedidoModel pedido) {
        this.pedido = pedido;
    }

   

    public PedidoItemTesteModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

  

    public ProdutoModel getProduto() {
        return produto;
    }

    public void setProduto(ProdutoModel produto) {
        this.produto = produto;
    }

    public EstadoModel getEstado() {
        return estado;
    }

    public void setEstado(EstadoModel estado) {
        this.estado = estado;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getRetencao() {
        return retencao;
    }

    public void setRetencao(Double retencao) {
        this.retencao = retencao;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return ""+produto;
    }
    
    
    
    
}
