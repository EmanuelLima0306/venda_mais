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
public class EncomendaModel {
    
    private Integer id;
    private String nome;
    private String contacto;
    private String localEntrega;
    private Double total;
    private String data;
    private EstadoModel estado;
    private UsuarioModel usuario;
    private ClienteModel cliente;

    public EncomendaModel(Integer id, String nome, String contacto, String localEntrega, Double total, String data, EstadoModel estado, UsuarioModel usuario) {
        this.id = id;
        this.nome = nome;
        this.contacto = contacto;
        this.localEntrega = localEntrega;
        this.total = total;
        this.data = data;
        this.estado = estado;
        this.usuario = usuario;
    }

    public EncomendaModel(String nome, String contacto, String localEntrega, Double total, String data, EstadoModel estado, UsuarioModel usuario) {
        this.nome = nome;
        this.contacto = contacto;
        this.localEntrega = localEntrega;
        this.total = total;
        this.data = data;
        this.estado = estado;
        this.usuario = usuario;
    }

    public EncomendaModel() {
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }
    
    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    

    public EstadoModel getEstado() {
        return estado;
    }

    public void setEstado(EstadoModel estado) {
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }

   
    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getLocalEntrega() {
        return localEntrega;
    }

    public void setLocalEntrega(String localEntrega) {
        this.localEntrega = localEntrega;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    
    
    
    
}
