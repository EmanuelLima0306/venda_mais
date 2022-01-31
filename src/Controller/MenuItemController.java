/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.AnoModel;
import Model.ArmazemModel;
import Model.MenuItemModel;
import Model.UsuarioModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emanuellima
 */
public class MenuItemController implements IController<MenuItemModel> {

     private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;
    @Override
    public boolean save(MenuItemModel obj) {
        return true;
    }

    @Override
    public boolean update(MenuItemModel obj) {
    
        return true;
    
    }

    @Override
    public boolean saveOrUpdate(MenuItemModel obj) {
    
          return true;
    
    }

    @Override
    public MenuItemModel getById(int id) {
        
        return null;
    
    }
    public List<MenuItemModel> getByUser(UsuarioModel usuarioModel) {
        
             List<MenuItemModel> lista = new ArrayList<>();
             MenuItemModel menuItemModel = new MenuItemModel();
        try {

            con = conFactory.open();
            String sql = "SELECT m.Id, m.Designacao, m.Descricao, u.IdEstado FROM "
                       + " grest.menuitem m inner join grest.menuitemusuario u on "
                       + " m.Id = u.IdMenuItem where u.IdUsuario = ? and m.Id = m.IdMenu;";
                    

            command = con.prepareCall(sql);
            command.setInt(1, usuarioModel.getId());
            
            query = command.executeQuery();
            while (query.next()) {
                
                 menuItemModel = new MenuItemModel();
                 menuItemModel.setId(query.getInt("m.Id"));
                 menuItemModel.setDesignacao(query.getString("m.Designacao"));
                 menuItemModel.setDescricao(query.getString("m.Descricao"));
                 menuItemModel.setIdEstado(query.getInt("u.IdEstado"));
                 getItem(query.getInt("m.Id"),usuarioModel,menuItemModel);
                 
                lista.add(menuItemModel);
            }

        } catch (Exception ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    
    }
    
    public void getItem(int id,UsuarioModel usuarioModel, MenuItemModel menuItemModel1) {
        
//             List<MenuItemModel> lista = new ArrayList<>();
             MenuItemModel menuItemModel = new MenuItemModel();
        try {

            con = conFactory.open();
            String sql = "SELECT m.Id, m.Designacao, m.Descricao, u.IdEstado FROM "
                       + " grest.menuitem m inner join grest.menuitemusuario u on "
                       + " m.Id = u.IdMenuItem where u.IdUsuario = ? and m.IdMenu = ? and m.IdMenu <> m.Id;";

            command = con.prepareCall(sql);
            command.setInt(1, usuarioModel.getId());
            command.setInt(2, id);
            System.out.println(sql);
            query = command.executeQuery();
            System.out.println(query);
            while (query.next()) {
                
                 menuItemModel = new MenuItemModel();
                 menuItemModel.setId(query.getInt("m.Id"));
                 menuItemModel.setDesignacao(query.getString("m.Designacao"));
                 menuItemModel.setDescricao(query.getString("m.Descricao"));
                 menuItemModel.setIdEstado(query.getInt("u.IdEstado"));
                 
                 if(getItem(query.getInt("m.Id"), usuarioModel)){
                     
                    getItem(query.getInt("m.Id"),usuarioModel,menuItemModel);
                 }
                 
                 menuItemModel1.addSubMenu(menuItemModel);
            }
                

        } catch (Exception ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    public void getItem2(MenuItemModel itemModel, List<MenuItemModel> lista) {
            
            List<MenuItemModel> listaItem = new ArrayList<>();
           if(lista.size() <= 0){ //se não existir menu nem item
               
           }
           else{ // se existir
               
               if(itemExiste(itemModel, lista)){ // se esse menu ou item possuir item
                   for (MenuItemModel itemModel1: lista){
                       
                       if (itemModel.getId() == itemModel1.getIdMenu() && itemModel1.getId() != itemModel1.getIdMenu()){
                           
                            getItem2(itemModel1, lista);
                            itemModel.addSubMenu(itemModel1);
                       }
                   }
                   
                   
               }
               
           }
               
    }
    
    public boolean itemExiste(MenuItemModel item, List<MenuItemModel> lista){
        for(MenuItemModel itemModel: lista){
            if(item.getId() == itemModel.getIdMenu() && itemModel.getId() != itemModel.getIdMenu())
                return true;
        }
        return false;
    }
    
    public boolean getItem(int id,UsuarioModel usuarioModel) {
        
//             List<MenuItemModel> lista = new ArrayList<>();
             MenuItemModel menuItemModel = new MenuItemModel();
        try {

            con = conFactory.open();
            String sql = "SELECT m.Id FROM "
                       + " grest.menuitem m inner join grest.menuitemusuario u on "
                       + " m.Id = u.IdMenuItem where u.IdUsuario = ? and m.IdMenu = ? and m.IdMenu <> m.Id;";

            command = con.prepareCall(sql);
            command.setInt(1, usuarioModel.getId());
            command.setInt(2, id);
            
            query = command.executeQuery();
            
            if (query.next()) {
                
                return true;
            }
                

        } catch (Exception ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
//            ////conFactory.close(con, command, query);
        }
        return false;
    }
    

    public List<MenuItemModel> get(UsuarioModel usuarioModel) {
    
         List<MenuItemModel> lista = new ArrayList<>();
         List<MenuItemModel> lista1 = new ArrayList<>();
         List<MenuItemModel> listaMenu = new ArrayList<>();
             MenuItemModel menuItemModel = new MenuItemModel();
        try {

            con = conFactory.open();
            String sql = "SELECT m.Id, m.Designacao, m.Descricao, u.IdEstado, m.IdMenu FROM "
                       + " grest.menuitem m inner join grest.menuitemusuario u on "
                       + " m.Id = u.IdMenuItem where u.IdUsuario = ?;";
                    

            command = con.prepareCall(sql);
            command.setInt(1, usuarioModel.getId());
            
            query = command.executeQuery();
            
            while (query.next()) {// carrega todos os menus e items do usuario
                
                 menuItemModel = new MenuItemModel();
                 menuItemModel.setId(query.getInt("m.Id"));
                 menuItemModel.setDesignacao(query.getString("m.Designacao"));
                 menuItemModel.setDescricao(query.getString("m.Descricao"));
                 menuItemModel.setIdEstado(query.getInt("u.IdEstado"));
                 menuItemModel.setIdMenu(query.getInt("m.IdMenu"));
                 
                lista.add(menuItemModel);
            }

        } catch (Exception ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        
        for (MenuItemModel menuItemModel1 : lista) { // separa os menus principais dos items
            if(menuItemModel1.getId() == menuItemModel1.getIdMenu())
                listaMenu.add(menuItemModel1);
        }
        
        for(MenuItemModel menu: listaMenu){ // preenche os menus com os respeitivos itens e os item com os items também
            
            getItem2(menu, lista);
            lista1.add(menu); 
        }
        
        return lista1; // retorna o menu de forma organizada
    
    }

    @Override
    public List<MenuItemModel> get() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
