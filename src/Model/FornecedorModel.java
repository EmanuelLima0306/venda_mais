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
public class FornecedorModel implements Serializable{
    
    private int id;
    private String nome;
    private String email;
    private String contacto;
    private String localizacao;
    private EstadoModel estado;
    private TipoFornecedorModel tipoFornecedor;
    private UsuarioModel usuario;
    private String numContribuente;
    private String data;

    public FornecedorModel(int id, String nome, String email, String contacto, String localizacao, EstadoModel estado, TipoFornecedorModel tipoFornecedor) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.contacto = contacto;
        this.localizacao = localizacao;
        this.estado = estado;
        this.tipoFornecedor = tipoFornecedor;
    }

    public FornecedorModel(String nome, String email, String contacto, String localizacao, EstadoModel estado, TipoFornecedorModel tipoFornecedor) {
        this.nome = nome;
        this.email = email;
        this.contacto = contacto;
        this.localizacao = localizacao;
        this.estado = estado;
        this.tipoFornecedor = tipoFornecedor;
    }

    public FornecedorModel(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public FornecedorModel() {
       
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

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public EstadoModel getEstado() {
        return estado;
    }

    public void setEstado(EstadoModel estado) {
        this.estado = estado;
    }

    public TipoFornecedorModel getTipoFornecedor() {
        return tipoFornecedor;
    }

    public void setTipoFornecedor(TipoFornecedorModel tipoFornecedor) {
        this.tipoFornecedor = tipoFornecedor;
    }

    @Override
    public String toString() {
        return  nome;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public boolean isEmpty() {
        
        
        return nome.isEmpty() || contacto.isEmpty();
        
    }

    public String getNumContribuente() {
        return numContribuente;
    }

    public void setNumContribuente(String numContribuente) {
        this.numContribuente = numContribuente;
    }
    
    
    
    
}
