/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author emanu
 */
public class LiquidarDividaModel {
    
    private int id;
    private String obs;
    private Double valor;
    private Double retensaoNaFonte;
    private ClienteModel cliente;
    private FacturaModel factura;
    private String dataEmissao;
    private String dataPagamento;
    private UsuarioModel usuario;
    private EstadoModel estado;

    public LiquidarDividaModel() {
    }

    public LiquidarDividaModel(int id, String obs, Double valor, Double retensaoNaFonte, ClienteModel cliente, FacturaModel factura, String dataEmissao, String dataPagamento, UsuarioModel usuario, EstadoModel estado) {
        this.id = id;
        this.obs = obs;
        this.valor = valor;
        this.retensaoNaFonte = retensaoNaFonte;
        this.cliente = cliente;
        this.factura = factura;
        this.dataEmissao = dataEmissao;
        this.dataPagamento = dataPagamento;
        this.usuario = usuario;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }

    public FacturaModel getFactura() {
        return factura;
    }

    public void setFactura(FacturaModel factura) {
        this.factura = factura;
    }

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
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
    
    public Double getRetensaoNaFonte() {
        return retensaoNaFonte;
    }

    public void setRetensaoNaFonte(Double retensaoNaFonte) {
        this.retensaoNaFonte = retensaoNaFonte;
    }

    @Override
    public String toString() {
        return  factura.getNextFactura();
    }
    
    public boolean isEmpty()
    {
        return false;
//        return obs.isEmpty() || valor <= 0.0 || cliente == null || factura == null || dataEmissao.isEmpty() || dataPagamento.isEmpty() || usuario == null || estado == null;
    }
}
