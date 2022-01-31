/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.IPCModel;
import Model.EstadoModel;
import Model.IPCModel;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.collections.map.HashedMap;

/**
 *
 * @author celso
 */
public class IPCController  implements IController<IPCModel>{

     private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(IPCModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `iva`\n"
                    + "("
                    + "`Valor`)"
                    + "VALUES"
                    + "(?)";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getValor());
           
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(CategoriaABController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public boolean update(IPCModel obj) {

        boolean result = false;
        try {
            String sql = "   UPDATE iva"
                    + "   SET"
                    + "   Valor = ?"
                    + "   WHERE Id = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getValor());
           
            command.setInt(2, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(CategoriaABController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(IPCModel obj) {

        if (obj.getId() > 0) {
            return update(obj);
        }
        return save(obj);
    }

    @Override
    public IPCModel getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<IPCModel> get() {

        List<IPCModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM iva";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                lista.add(new IPCModel(query.getInt("Id"), query.getString("Valor")));
            }

        } catch (Exception ex) {
            Logger.getLogger(CategoriaABController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }
    public IPCModel getIPC() {

        IPCModel modelo= null;
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM iva LIMIT 1";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {

                modelo = new IPCModel(query.getInt("Id"), query.getString("Valor"));
            }

        } catch (Exception ex) {
            Logger.getLogger(CategoriaABController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return modelo;
    }

    public IPCModel getAll(String text) {

        IPCModel lista = new IPCModel();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM `iva` WHERE Valor = ?;";

            command = con.prepareCall(sql);
            command.setString(1, text);
            query = command.executeQuery();
            while (query.next()) {

                lista = new IPCModel(query.getInt("Id"),
                        query.getString("Valor"));
            }

        } catch (Exception ex) {
            Logger.getLogger(CategoriaABController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

   
    
}
