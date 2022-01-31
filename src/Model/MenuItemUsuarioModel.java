/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author emanuellima
 */
public class MenuItemUsuarioModel {
    
    private int id;
    private int idEstado;
    private MenuItemModel menuItemModel;
    private UsuarioModel usuarioModel;

    public MenuItemUsuarioModel() {
    }

    public MenuItemUsuarioModel(int id, int idEstado, MenuItemModel menuItemModel, UsuarioModel usuarioModel) {
        this.id = id;
        this.idEstado = idEstado;
        this.menuItemModel = menuItemModel;
        this.usuarioModel = usuarioModel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public MenuItemModel getMenuItemModel() {
        return menuItemModel;
    }

    public void setMenuItemModel(MenuItemModel menuItemModel) {
        this.menuItemModel = menuItemModel;
    }

    public UsuarioModel getUsuarioModel() {
        return usuarioModel;
    }

    public void setUsuarioModel(UsuarioModel usuarioModel) {
        this.usuarioModel = usuarioModel;
    }
    
    
}
