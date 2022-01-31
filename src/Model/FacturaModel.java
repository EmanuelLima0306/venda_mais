/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.List;

/**
 *
 * @author celso
 */
public class FacturaModel {
    
    private int id;
    private ClienteModel cliente;
    private UsuarioModel usuario;
    private FormaPagamentoModel formaPagamento;
    private Moeda moeda;
    private int idAno;
    private EstadoModel estado;
    private String data;
    private String dataTransporte;
    private double valorEntregue;
    private double valorMulticaixa;
    private double troco;
    private double totalIVA;
    private double subTotal;
    private double totalApagar;
    private double totalDesconto;
    private double totalRetencao;
    private String obs;
    private String hash;
    private String facturaReference;
    private String nextFactura;
    private String localDescarga;
    private String localCarga;
    private String nomeCliente,TipoFacturas;
    private Integer idSerie;
    private Double cambio = 0.0;
    private Double valorMoedaEstrangeiro = 0.0;
    private Boolean criada_modulo_formacao;
    private CaixaModel caixaModel;
    
    
    private List<FacturaItemModel> facturaItemModels;

    public FacturaModel(int id) {
        this.id = id;
    }
    
    
    
    public double getTotalDesconto() {
        return totalDesconto;
    }

    public void setTotalDesconto(double totalDesconto) {
        this.totalDesconto = totalDesconto;
    }

    
    
    
    public FacturaModel(int id, ClienteModel cliente, UsuarioModel usuario, FormaPagamentoModel formaPagamento, EstadoModel estado, String data, double valorEntregue, double valorMulticaixa, double troco, double totalApagar) {
        this.id = id;
        this.cliente = cliente;
        this.usuario = usuario;
        this.formaPagamento = formaPagamento;
        this.estado = estado;
        this.data = data;
        this.valorEntregue = valorEntregue;
        this.valorMulticaixa = valorMulticaixa;
        this.troco = troco;
        this.totalApagar = totalApagar;
    }

    public FacturaModel(ClienteModel cliente, UsuarioModel usuario, FormaPagamentoModel formaPagamento, EstadoModel estado, String data, double valorEntregue, double valorMulticaixa, double troco, double totalApagar) {
        this.cliente = cliente;
        this.usuario = usuario;
        this.formaPagamento = formaPagamento;
        this.estado = estado;
        this.data = data;
        this.valorEntregue = valorEntregue;
        this.valorMulticaixa = valorMulticaixa;
        this.troco = troco;
        this.totalApagar = totalApagar;
    }

    public FacturaModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public String getNextFactura() {
        return nextFactura;
    }

    public void setNextFactura(String nextFactura) {
        this.nextFactura = nextFactura;
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }

    public double getTotalIVA() {
        return totalIVA;
    }

    public void setTotalIVA(double totalIVA) {
        this.totalIVA = totalIVA;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public FormaPagamentoModel getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamentoModel formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public EstadoModel getEstado() {
        return estado;
    }

    public void setEstado(EstadoModel estado) {
        this.estado = estado;
    }

    public Integer getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(Integer idSerie) {
        this.idSerie = idSerie;
    }

   
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getValorEntregue() {
        return valorEntregue;
    }

    public void setValorEntregue(double valorEntregue) {
        this.valorEntregue = valorEntregue;
    }

    public double getValorMulticaixa() {
        return valorMulticaixa;
    }

    public void setValorMulticaixa(double valorMulticaixa) {
        this.valorMulticaixa = valorMulticaixa;
    }

    public double getTroco() {
        return troco;
    }

    public void setTroco(double troco) {
        this.troco = troco;
    }

    public double getTotalApagar() {
        return totalApagar;
    }

    public void setTotalApagar(double totalApagar) {
        this.totalApagar = totalApagar;
    }

    public boolean isEmpty() {
        return  false;
    }

    @Override
    public String toString() {
        return  nextFactura ;
    }

    public String getObs() {
        return obs;
    }

    public int getIdAno() {
        return idAno;
    }

    public void setIdAno(int idAno) {
        this.idAno = idAno;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getTipoFacturas() {
        return TipoFacturas;
    }

    public void setTipoFacturas(String TipoFacturas) {
        this.TipoFacturas = TipoFacturas;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getFacturaReference() {
        return facturaReference;
    }

    public void setFacturaReference(String facturaReference) {
        this.facturaReference = facturaReference;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }
    
  
    public boolean isValorValido(){
        
        return (valorEntregue+valorMulticaixa) >= totalApagar;
    }

    public Double getCambio() {
        return cambio;
    }

    public void setCambio(Double cambio) {
        this.cambio = cambio;
    }

    public Double getValorMoedaEstrangeiro() {
        return valorMoedaEstrangeiro;
    }

    public void setValorMoedaEstrangeiro(Double valorMoedaEstrangeiro) {
        this.valorMoedaEstrangeiro = valorMoedaEstrangeiro;
    }

    public Boolean getCriada_modulo_formacao() {
        return criada_modulo_formacao;
    }

    public void setCriada_modulo_formacao(Boolean criada_modulo_formacao) {
        this.criada_modulo_formacao = criada_modulo_formacao;
    }

    public CaixaModel getCaixaModel() {
        return caixaModel;
    }

    public void setCaixaModel(CaixaModel caixaModel) {
        this.caixaModel = caixaModel;
    }

    public String getLocalDescarga() {
        return localDescarga;
    }

    public void setLocalDescarga(String localDescarga) {
        this.localDescarga = localDescarga;
    }

    public String getLocalCarga() {
        return localCarga;
    }

    public void setLocalCarga(String localCarga) {
        this.localCarga = localCarga;
    }

    public String getDataTransporte() {
        return dataTransporte;
    }

    public void setDataTransporte(String dataTransporte) {
        this.dataTransporte = dataTransporte;
    }

    public double getTotalRetencao() {
        return totalRetencao;
    }

    public void setTotalRetencao(double totalRetencao) {
        this.totalRetencao = totalRetencao;
    }

    public List<FacturaItemModel> getFacturaItemModels() {
        return facturaItemModels;
    }

    public void setFacturaItemModels(List<FacturaItemModel> facturaItemModels) {
        this.facturaItemModels = facturaItemModels;
    }
    
    
    
    
    
}
