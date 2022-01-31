/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author celso de sousa
 */
public class PermissaoUsuarioModel {
    
    private int id;
    private UsuarioModel usuario;
    private PermissaoModel permissao;
    private String data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public PermissaoModel getPermissao() {
        return permissao;
    }

    public void setPermissao(PermissaoModel permissao) {
        this.permissao = permissao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    
    
    
    
}
