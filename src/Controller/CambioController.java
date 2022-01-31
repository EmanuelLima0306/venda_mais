/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.CambioModel;
import Model.Moeda;
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
public class CambioController implements IController<CambioModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(CambioModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `cambio`\n"
                    + "("
                    + "`IdMoeda`,"
                    + "`Valor`)"
                    + "VALUES"
                    + "(?,?)";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, obj.getMoeda().getId());
            command.setDouble(2, obj.getValor());
            
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(CambioController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean update(CambioModel obj) {

        boolean result = false;
        try {
            String sql = "   UPDATE cambio"
                    + "   SET"
                    + "   Valor = ?"
                    + "   WHERE IdMoeda = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setDouble(1, obj.getValor());
           
            command.setInt(2, obj.getMoeda().getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(CambioController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(CambioModel obj) {

        if (obj.getCodigo()> 0) {
            return update(obj);
        }
        return save(obj);
    }

//    @Override
//    public CambioModel getById(int id) {
//        
//    }

//    @Override
//    public List<CambioModel> get() {
//
//        List<Moeda> lista = new ArrayList<>();
//        try {
//
//            con = conFactory.open();
//            String sql = " SELECT * "
//                    + " FROM moeda"
//                    + "  ORDER BY 2;";
//
//            command = con.prepareCall(sql);
//            query = command.executeQuery();
//            while (query.next()) {
//
//                lista.add(new Moeda(query.getInt("Id"), query.getString("Designacao")));
//            }
//
//        } catch (Exception ex) {
//            Logger.getLogger(CambioController.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            ////conFactory.close(con, command, query);
//        }
//        return lista;
//    }
//
//    public CambioModel getAll(String text) {
//
//        CambioModel lista = new CambioModel();
//        try {
//
//            con = conFactory.open();
//            String sql = " SELECT * "
//                    + " FROM `moeda` WHERE Designacao = ?;";
//
//            command = con.prepareCall(sql);
//            command.setString(1, text);
//            query = command.executeQuery();
//            while (query.next()) {
//
//                lista = new Moeda(query.getInt("Id"),
//                        query.getString("Designacao"));
//            }
//
//        } catch (Exception ex) {
//            Logger.getLogger(CambioController.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            ////conFactory.close(con, command, query);
//        }
//        return lista;
//    }
//
//    public void ireportCategoria() {
//
//        
//        String relatorio = "Relatorio/ListaCategoria.jasper";
//        Connection connection = conFactory.open();
//        
//        File file = new File(relatorio).getAbsoluteFile();
//        String obterCaminho = file.getAbsolutePath();
//        HashedMap hashMap = new HashedMap();
//        hashMap.put("LOGOTIPO", "Relatorio/logo.jpg");
//        try 
//        {
//            JasperFillManager.fillReport(obterCaminho,hashMap, connection);
//
//            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);
//
//            if (jasperPrint.getPages().size() >= 1) 
//            {                
//                
//                
//                    JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
//                    jasperViewer.setVisible(true);
//                
//            } 
//            else 
//            {
//                JOptionPane.showMessageDialog(null, "Folha vazia !...");
//            }
//        } 
//        catch (JRException jex) 
//        {
//            jex.printStackTrace();
//            //System.out.println("aqui");
//            JOptionPane.showMessageDialog(null, "FALHA AO TENTAR MOSTRAR  gráfico!...");
//        } 
//        catch (Exception ex) 
//        {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(null, "ERRO AO TENTAR MOSTRAR gráfico !...");
//        }
//        
//    }

    public CambioModel getById(Moeda moeda) {
        
        CambioModel cambio = new CambioModel();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM `cambio` WHERE IdMoeda = ?;";

            command = con.prepareCall(sql);
            command.setInt(1, moeda.getId());
            query = command.executeQuery();
            if (query.next()) {

                cambio = new CambioModel();
                cambio.setCodigo(query.getInt("Codigo"));
                cambio.setValor(query.getDouble("Valor"));
                cambio.setMoeda(moeda);
                return cambio;
            }

        } catch (Exception ex) {
            Logger.getLogger(CambioController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return cambio;
    }

    @Override
    public List<CambioModel> get() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CambioModel getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
