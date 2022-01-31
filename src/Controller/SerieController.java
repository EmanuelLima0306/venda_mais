/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.AnoModel;
import Model.SerieModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jfree.chart.block.Arrangement;

/**
 *
 * @author emanuellima
 */
public class SerieController implements IController<SerieModel>{

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;
    
    @Override
    public boolean save(SerieModel obj) {
        
        boolean result = false;
        try {
            String sql = "INSERT INTO `serie`\n"
                    + "("
                    + "`Designacao`,`Ano`,`Status`)"
                    + "VALUES"
                    + "(?,?,?)";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getDesignacao());
            command.setInt(2, obj.getAno().getCodigo());
            command.setInt(3, obj.getStatus());
            
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
        }
        return result;
    
    }

    @Override
    public boolean update(SerieModel obj) {
    
        
        boolean result = false;
        try {
            String sql = "   UPDATE serie"
                    + "   SET"
                    + "   Designacao = ?,"
                    + "   Ano = ?,"
                    + "   Status = ? "
                    + " WHERE Codigo = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getDesignacao());
            command.setInt(2, obj.getAno().getCodigo());
            command.setInt(3, obj.getStatus());
            
            command.setInt(4, obj.getCodigo());
            
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    
    
    }
    public boolean desactivarAll() {
    
        
        boolean result = false;
        try {
            String sql = "   UPDATE serie"
                    + "   SET"
                    + "   Status = 2 ";
            con = conFactory.open();
            command = con.prepareCall(sql);
            
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    
    
    }
    public boolean desactivar(SerieModel obj) {
    
        
        boolean result = false;
        try {
            String sql = "   UPDATE serie"
                    + "   SET"
                    + "   Status = 2 "
                    + " WHERE Codigo = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, obj.getCodigo());
            
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    
    
    }
    public boolean activar(SerieModel obj) {
    
        
        boolean result = false;
        try {
            String sql = "   UPDATE serie"
                    + "   SET"
                    + "   Status = 1 "
                    + " WHERE Codigo = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, obj.getCodigo());
            
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    
    
    }

    @Override
    public boolean saveOrUpdate(SerieModel obj) {
    
        if(obj.getCodigo() > 0)
            return update(obj);
        else
            return save(obj);
    
    }

    @Override
    public SerieModel getById(int id) {
    
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM serie"
                    + " WHERE Codigo = ? ORDER BY 2;";

            command = con.prepareCall(sql);
            command.setInt(1, id);
            query = command.executeQuery();
            if (query.next()) {

                SerieModel modelo = new SerieModel();
                AnoController ano = new AnoController();
                
                modelo.setCodigo(query.getInt("Codigo"));
                modelo.setDesignacao(query.getString("Designacao"));
                modelo.setAno(ano.getById(query.getInt("Ano")));
                
                return modelo;
                
                
            }

        } catch (Exception ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return null;
    
    }
    
    public boolean getTemFactura(SerieModel obj) {
    
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM serie s"
                    +" INNER JOIN factura f"
                    +" ON s.Codigo = f.IdSerie "
                    + " WHERE s.Codigo = ? ORDER BY 2;";

            command = con.prepareCall(sql);
            command.setInt(1, obj.getCodigo());
            query = command.executeQuery();
            if (query.next()) {
                
//                SerieModel modelo = new SerieModel();
//                AnoController ano = new AnoController();
//                
//                modelo.setCodigo(query.getInt("Codigo"));
//                modelo.setDesignacao(query.getString("Designacao"));
//                modelo.setAno(ano.getById(query.getInt("Ano")));
                
                return true;
                
                
            }

        } catch (Exception ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return false;
    
    }
    public boolean getExiste(SerieModel obj) {
    
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM serie "
                    + " WHERE Designacao = ? AND Ano = ? ORDER BY 2;";

            command = con.prepareCall(sql);
            command.setString(1, obj.getDesignacao());
            command.setInt(2, obj.getAno().getCodigo());
            query = command.executeQuery();
            if (query.next()) {
                
//                SerieModel modelo = new SerieModel();
//                AnoController ano = new AnoController();
//                
//                modelo.setCodigo(query.getInt("Codigo"));
//                modelo.setDesignacao(query.getString("Designacao"));
//                modelo.setAno(ano.getById(query.getInt("Ano")));
                
                return true;
                
                
            }

        } catch (Exception ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return false;
    
    }

    @Override
    public List<SerieModel> get() {
    
        List<SerieModel> lista = new ArrayList<>();
        
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM serie"
                    + " ORDER BY 2;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                SerieModel modelo = new SerieModel();
                AnoController ano = new AnoController();
                
                modelo.setCodigo(query.getInt("Codigo"));
                modelo.setDesignacao(query.getString("Designacao"));
                modelo.setStatus(query.getInt("Status"));
                modelo.setAno(ano.getById(query.getInt("Ano")));
                
                lista.add(modelo);
                
                
            }

        } catch (Exception ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    
    }
    
}
