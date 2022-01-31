/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.AnoModel;
import Model.ArmazemModel;
import Model.LogModel;
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
public class LogController implements IController<LogModel> {

     private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;
    @Override
    public boolean save(LogModel obj) {
        
        boolean result = false;
        try {
            String sql = "INSERT INTO `log`\n"
                    + "("
                    + "`Designacao`,"
                    + "`Descricao`,"
                    + "`IdUsuario`,"
                    + "`Data`)"
                    + "VALUES"
                    + "(?,?,?,?)";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getDesignacao().getDesignacao());
            command.setString(2, obj.getDescricao().toUpperCase());
            command.setInt(3, obj.getUsuario()!= null ? obj.getUsuario().getId():0);
            command.setString(4, obj.getData());
            
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
        }
        return result;
    }

    @Override
    public boolean update(LogModel obj) {
    
        boolean result = false;
        try {
            String sql = "   UPDATE ano"
                    + "   SET"
                    + "   ano = ?"
                    + "   WHERE Codigo = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
//            command.setInt(1, obj.getAno());
//            command.setInt(2, obj.getCodigo());
            
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    
    }

    @Override
    public boolean saveOrUpdate(LogModel obj) {
    
        if(obj.getId()> 0)
            return update(obj);
        else
            return save(obj);
    
    }

    @Override
    public LogModel getById(int id) {
        
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM ano"
                    + " WHERE Codigo = ? ORDER BY 2;";

            command = con.prepareCall(sql);
            command.setInt(1, id);
            
            query = command.executeQuery();
            if (query.next()) {

//                return (new AnoModel(query.getInt("Codigo"), query.getInt("Ano")));
            }

        } catch (Exception ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return null;
    
    }
    public AnoModel getByAno(int ano) {
        
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM ano"
                    + " WHERE Ano = ? ORDER BY 2;";

            command = con.prepareCall(sql);
            command.setInt(1, ano);
            
            query = command.executeQuery();
            if (query.next()) {

                return (new AnoModel(query.getInt("Codigo"), query.getInt("Ano")));
            }

        } catch (Exception ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return null;
    
    }

    @Override
    public List<LogModel> get() {
    
        List<LogModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM ano"
                    + " WHERE Codigo = 1 ORDER BY 2;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

//                lista.add(new LogModel(query.getInt("Codigo"), query.getInt("Ano")));
            }

        } catch (Exception ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    
    }
    public AnoModel getLast() {
        
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM ano"
                    + " WHERE Codigo = 1 ORDER BY 2;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.last()) {

                return (new AnoModel(query.getInt("Codigo"), query.getInt("Ano")));
            }

        } catch (Exception ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return null;
    
    }
    
    
    
}
