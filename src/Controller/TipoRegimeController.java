/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.TipoRegimeModel;
import Model.EstadoModel;
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
public class TipoRegimeController implements IController<TipoRegimeModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(TipoRegimeModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `tiposregime`\n"
                    + "("
                    + "`Designacao`,"
                    + "`IdEstado`)"
                    + "VALUES"
                    + "(?,?)";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getDesignacao());
//            command.setInt(2, obj.getEstado().getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(TipoRegimeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public boolean update(TipoRegimeModel obj) {

        boolean result = false;
        try {
            String sql = "   UPDATE tiposregime"
                    + "   SET"
                    + "   Designacao = ?,"
                    + "   IdEstado = ?"
                    + "   WHERE Id = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getDesignacao());
//            command.setInt(2, obj.getEstado().getId());
            command.setInt(3, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(TipoRegimeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(TipoRegimeModel obj) {

        if (obj.getId() > 0) {
            return update(obj);
        }
        return save(obj);
    }

    @Override
    public TipoRegimeModel getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TipoRegimeModel> get() {

        List<TipoRegimeModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM tiposregime"
                    + "  ORDER BY 2;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                lista.add(new TipoRegimeModel(query.getInt("Id"), query.getString("Designacao"),query.getBoolean("cobraImposto")));
            }

        } catch (Exception ex) {
            Logger.getLogger(TipoRegimeController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public TipoRegimeModel getAll(String text) {

        TipoRegimeModel lista = new TipoRegimeModel();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM `tiposregime` WHERE Designacao = ?;";

            command = con.prepareCall(sql);
            command.setString(1, text);
            query = command.executeQuery();
            while (query.next()) {

                 lista = new TipoRegimeModel(query.getInt("Id"), query.getString("Designacao"),query.getBoolean("cobraImposto"));
            }

        } catch (Exception ex) {
            Logger.getLogger(TipoRegimeController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public void ireportCategoria() {

        
        String relatorio = "Relatorio/ListaCategoria.jasper";
        Connection connection = conFactory.open();
        
        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("LOGOTIPO", "Relatorio/logo.jpg");
        try 
        {
            JasperFillManager.fillReport(obterCaminho,hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            if (jasperPrint.getPages().size() >= 1) 
            {                
                
                
                    JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                    jasperViewer.setVisible(true);
                
            } 
            else 
            {
                JOptionPane.showMessageDialog(null, "Folha vazia !...");
            }
        } 
        catch (JRException jex) 
        {
            jex.printStackTrace();
            //System.out.println("aqui");
            JOptionPane.showMessageDialog(null, "FALHA AO TENTAR MOSTRAR  gr??fico!...");
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERRO AO TENTAR MOSTRAR gr??fico !...");
        }
        
    }
}
