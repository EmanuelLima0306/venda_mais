/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;

/**
 *
 * @author celso
 */
public class ClienteModel implements Serializable{
    
    private int id;
    private String nome;
    private String nif;
    private String email;
    private String contacto;
    private String endereco;
    private TipoClienteModel tipoCliente;
    private UsuarioModel usuario;
    private String data;
    private EstadoModel estado;
    private double limiteCredito;
    private double valorCarteira;

    public ClienteModel(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    
    
    public ClienteModel(int id, String nome, String email, String contacto, String endereco, TipoClienteModel tipoCliente, UsuarioModel usuario, EstadoModel estado) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.contacto = contacto;
        this.endereco = endereco;
        this.tipoCliente = tipoCliente;
        this.usuario = usuario;
        this.estado = estado;
    }

    public ClienteModel(String nome, String email, String contacto, String endereco, TipoClienteModel tipoCliente, UsuarioModel usuario, EstadoModel estado) {
        this.nome = nome;
        this.email = email;
        this.contacto = contacto;
        this.endereco = endereco;
        this.tipoCliente = tipoCliente;
        this.usuario = usuario;
        this.estado = estado;
    }

    public ClienteModel() {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getData() {
        return data;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public TipoClienteModel getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoClienteModel tipoCliente) {
        this.tipoCliente = tipoCliente;
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

    @Override
    public String toString() {
        return nome;
    }

    public boolean isEmpty() {
        
        return  nome.isEmpty() || contacto.isEmpty() || endereco.isEmpty();
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public double getValorCarteira() {
        return valorCarteira;
    }

    public void setValorCarteira(double valorCarteira) {
        this.valorCarteira = valorCarteira;
    }
    
    
}
