/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.MesaModel;
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
public class MesaController implements IController<MesaModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(MesaModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `mesa`\n"
                    + "("
                    + "`Designacao`,"
                    + "`IdEstado`)"
                    + "VALUES"
                    + "(?,?)";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getDesignacao());
            command.setInt(2, obj.getEstado().getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(MesaController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean update(MesaModel obj) {

        boolean result = false;
        try {
            String sql = "   UPDATE mesa"
                    + "   SET"
                    + "   Designacao = ?,"
                    + "   IdEstado = ?"
                    + "   WHERE Id = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getDesignacao());
            command.setInt(2, obj.getEstado().getId());
            command.setInt(3, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(MesaController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(MesaModel obj) {

        if (obj.getId() > 0) {
            return update(obj);
        }
        return save(obj);
    }

    @Override
    public MesaModel getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MesaModel> get() {

        List<MesaModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM mesa"
                    + " WHERE IdEstado = 1 ORDER BY 1;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                
                lista.add(new MesaModel(query.getInt("Id"), query.getString("Designacao")));
            }

        } catch (Exception ex) {
            Logger.getLogger(MesaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }
    public List<MesaModel> getAll() {

        List<MesaModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM mesa"
                    + " ORDER BY 1;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                EstadoModel estado = new EstadoModel(query.getInt("IdEstado"), "");
                lista.add(new MesaModel(query.getInt("Id"), query.getString("Designacao"),estado));
            }

        } catch (Exception ex) {
            Logger.getLogger(MesaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public MesaModel getAll(String text) {

        MesaModel lista = new MesaModel();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM `mesa` WHERE Designacao = ?;";

            command = con.prepareCall(sql);
            command.setString(1, text);
            query = command.executeQuery();
            while (query.next()) {

                lista = new MesaModel(query.getInt("Id"),
                        query.getString("Designacao"),
                        new EstadoModel(query.getInt("IdEstado"), ""));
            }

        } catch (Exception ex) {
            Logger.getLogger(MesaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public void ireportMesa() {

        
        String relatorio = "Relatorio/ListaMesa.jasper";
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
            JOptionPane.showMessageDialog(null, "FALHA AO TENTAR MOSTRAR  gráfico!...");
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERRO AO TENTAR MOSTRAR gráfico !...");
        }
        
    }
}
