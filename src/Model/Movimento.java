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
public class Movimento {
    
    private int id;
    private String obs;
    private String tipoMovimento;
    private Double valor;
    private ClienteModel cliente;
    private FacturaModel factura;
    private String data;
    private String dataOpercao;
    private String next;
    private String notaReference;
    private String hash;
    private UsuarioModel usuario;
    private EstadoModel estado;
    private Boolean criada_modulo_formacao;

    public Movimento(int id, String obs, String tipoMovimento, Double valor, ClienteModel cliente, FacturaModel factura, String data, String dataOpercao, UsuarioModel usuario) {
        this.id = id;
        this.obs = obs;
        this.tipoMovimento = tipoMovimento;
        this.valor = valor;
        this.cliente = cliente;
        this.factura = factura;
        this.data = data;
        this.dataOpercao = dataOpercao;
        this.usuario = usuario;
    }

    public Movimento(String obs, String tipoMovimento, Double valor, ClienteModel cliente, FacturaModel factura, String data, String dataOpercao, UsuarioModel usuario) {
        this.obs = obs;
        this.tipoMovimento = tipoMovimento;
        this.valor = valor;
        this.cliente = cliente;
        this.factura = factura;
        this.data = data;
        this.dataOpercao = dataOpercao;
        this.usuario = usuario;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Movimento() {
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

    public String getTipoMovimento() {
        return tipoMovimento;
    }

    public EstadoModel getEstado() {
        return estado;
    }

    public void setEstado(EstadoModel estado) {
        this.estado = estado;
    }

    public void setTipoMovimento(String tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
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

    public String getNotaReference() {
        return notaReference;
    }

    public void setNotaReference(String notaReference) {
        this.notaReference = notaReference;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public FacturaModel getFactura() {
        return factura;
    }

    public void setFactura(FacturaModel factura) {
        this.factura = factura;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataOpercao() {
        return dataOpercao;
    }

    public void setDataOpercao(String dataOpercao) {
        this.dataOpercao = dataOpercao;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public Boolean getCriada_modulo_formacao() {
        return criada_modulo_formacao;
    }

    public void setCriada_modulo_formacao(Boolean criada_modulo_formacao) {
        this.criada_modulo_formacao = criada_modulo_formacao;
    }
    
    
    public boolean isEmpty(){
        
        
        return false;
        
    }

    @Override
    public String toString() {
        return  obs;
    }
    
            
}
