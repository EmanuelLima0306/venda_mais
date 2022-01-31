/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.MenuItemController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author celso
 */
public class UsuarioModel implements Serializable{
    
    private int id;
    private int idUsuario;
    //Dados Pessoais
    private String nome;
    private String email;
    private String contacto;
    private String statusAcesso = "NOVO-USUARIO";
    private Double taxa = 0.0;
            
    //Dados de Usuario  
    private String usuario;
    private String senha;
    private TipoUsuarioModel tipoUsuario;
    private EstadoModel estado;
    private String data;

    public UsuarioModel(int id, String nome, String email, String contacto, String usuario, String senha, TipoUsuarioModel tipoUsuario, EstadoModel estado) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.contacto = contacto;
        this.usuario = usuario;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
        this.estado = estado;
    }

    public UsuarioModel(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }

    public UsuarioModel(String nome, String email, String contacto, String usuario, String senha, TipoUsuarioModel tipoUsuario, EstadoModel estado) {
        this.nome = nome;
        this.email = email;
        this.contacto = contacto;
        this.usuario = usuario;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
        this.estado = estado;
    }

    public UsuarioModel(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public UsuarioModel() {
        
        
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuarioModel getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuarioModel tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public EstadoModel getEstado() {
        return estado;
    }

    public void setEstado(EstadoModel estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return  nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    
    public boolean isUsuarioEmpty(){
        return usuario.isEmpty() || senha.isEmpty();
    }

    public String getStatusAcesso() {
        return statusAcesso;
    }

    public void setStatusAcesso(String statusAcesso) {
        this.statusAcesso = statusAcesso;
    }

    
    public boolean isNewAcesso(){
        
        return statusAcesso.equals("NOVO-USUARIO");
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Double getTaxa() {
        return taxa;
    }

    public void setTaxa(Double taxa) {
        this.taxa = taxa;
    }
    
    
    
    public List<MenuItemModel> getMenuItem(){
        
        MenuItemController menuItemController = new MenuItemController();
        return menuItemController.get(this);
    }
    
}
