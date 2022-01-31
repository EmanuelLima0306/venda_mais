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
public class EntradaStockModel {
    
    private int id;
    private String numFactura;
    private FormaPagamentoModel formaPagamento;
    private FornecedorModel fornecedor;
    private UsuarioModel usuario;
    private EstadoModel estado;
    private String dataFactura;
    private String data;
    private double total;
    private boolean temDivida;

    public EntradaStockModel(int id, String numFactura, FormaPagamentoModel formaPagamento, FornecedorModel fornecedor, UsuarioModel usuario, EstadoModel estado) {
        this.id = id;
        this.numFactura = numFactura;
        this.formaPagamento = formaPagamento;
        this.fornecedor = fornecedor;
        this.usuario = usuario;
        this.estado = estado;
    }

    public EntradaStockModel(String numFactura, FormaPagamentoModel formaPagamento, FornecedorModel fornecedor, UsuarioModel usuario, EstadoModel estado) {
        this.numFactura = numFactura;
        this.formaPagamento = formaPagamento;
        this.fornecedor = fornecedor;
        this.usuario = usuario;
        this.estado = estado;
    }

    public EntradaStockModel() {
        
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }

    public FormaPagamentoModel getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamentoModel formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public FornecedorModel getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(FornecedorModel fornecedor) {
        this.fornecedor = fornecedor;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public EstadoModel getEstado() {
        return estado;
    }

    public void setEstado(EstadoModel estado) {
        this.estado = estado;
    }

    public String getDataFactura() {
        return dataFactura;
    }

    public void setDataFactura(String dataFactura) {
        this.dataFactura = dataFactura;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isTemDivida() {
        return temDivida;
    }

    public void setTemDivida(boolean temDivida) {
        this.temDivida = temDivida;
    }

    public boolean isEmpty() {
        
        return numFactura.isEmpty() || dataFactura.isEmpty() || fornecedor.isEmpty() || formaPagamento.isEmpty() ;
    }

    @Override
    public String toString() {
        return  numFactura ;
    }
    
    
    
    
}
