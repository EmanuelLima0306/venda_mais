/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author emanuellima
 */
public class MenuItemModel {
    
    private int id;
    private int idMenu;
    private String designacao;
    private String descricao;
    private List<MenuItemModel> item = new ArrayList<>();
    private int idEstado;

    public MenuItemModel() {
    }

    public MenuItemModel(int id, String designacao, String descricao, int idEstado) {
        this.id = id;
        this.designacao = designacao;
        this.descricao = descricao;
        this.idEstado = idEstado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<MenuItemModel> getItem() {
        return item;
    }

    public void setItem(List<MenuItemModel> item) {
        this.item = item;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }
    
    public void addSubMenu(MenuItemModel subMenuItemModel){
        item.add(subMenuItemModel);
    }
    
    public boolean itemExiste(MenuItemModel item){
        for(MenuItemModel itemModel: this.item){
            if(item.id == itemModel.getId())
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return designacao;
    }
    
    
}
