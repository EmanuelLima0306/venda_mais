/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.AnoModel;
import Model.ArmazemModel;
import Model.MenuItemUsuarioModel;
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
public class MenuItemUsuarioController implements IController<MenuItemUsuarioModel> {

     private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;
    @Override
    public boolean save(MenuItemUsuarioModel obj) {
        
        boolean result = false;
        try {
            String sql = "INSERT INTO `menuitemusuario`\n"
                    + "("
                    + "`IdMenuitem`,`IdUsuario`,`IdEstado`)"
                    + "VALUES"
                    + "(?,?,?)";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, obj.getMenuItemModel().getId());
            command.setInt(2, obj.getUsuarioModel().getId());
            command.setInt(3, obj.getIdEstado());
            
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
        }
        return result;
    }
    
    // Adiciona as permissões no usuario...
    public boolean saveAllByUser(UsuarioModel usuarioModel) {
        
        boolean result = false;
        try {
            String sql = "insert into grest.menuitemusuario(IdMenuItem, IdUsuario, IdEstado) "
                        + "SELECT Id, ?, ? FROM grest.menuitem order by id asc;";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, usuarioModel.getId());
            command.setInt(2, usuarioModel.getTipoUsuario().getId() == 1? 1 : 2);
            
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
        }
        return result;
    }

    @Override
    public boolean update(MenuItemUsuarioModel obj) {
    
        boolean result = false;
        try {
            String sql = "   UPDATE menuitemusuario"
                    + "   SET"
                    + "   IdEstado = ?"
                    + "   WHERE IdMenuitem = ? AND  IdUsuario = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, obj.getIdEstado());
            command.setInt(2, obj.getMenuItemModel().getId());
            command.setInt(3, obj.getUsuarioModel().getId());
            
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    
    }

    @Override
    public boolean saveOrUpdate(MenuItemUsuarioModel obj) {
    
        if(obj.getId()> 0)
            return update(obj);
        else
            return save(obj);
    
    }

    @Override
    public MenuItemUsuarioModel getById(int id) {
        return null;
    
    }
    
    public MenuItemUsuarioModel getPermissaoDarPermissaoByUsuario(int id) {
         try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM menuitemusuario"
                    + " WHERE IdMenuitem = 71 AND IdUsuario = ? ORDER BY 2;";

            command = con.prepareCall(sql);
            command.setInt(1, id);
            
            query = command.executeQuery();
            if (query.next()) {
                
                MenuItemUsuarioModel modelo = new MenuItemUsuarioModel();
                modelo.setId(query.getInt("Id"));
                modelo.setIdEstado(query.getInt("IdEstado"));
                
                return modelo;
            }

        } catch (Exception ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return null;
    
    }

    @Override
    public List<MenuItemUsuarioModel> get() {
    
       
        return null;
    
    }
    
}
