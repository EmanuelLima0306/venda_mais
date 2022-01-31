/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.PermissaoUsuarioModel;
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
public class PermissaoUsuarioController implements IController<PermissaoUsuarioModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(PermissaoUsuarioModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO permissao_usuario\n"
                    + "("
                    + "`IdPermissao`,"
                    + "`IdUsuario`)"
                    + "VALUES"
                    + "(?,?)";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, obj.getPermissao().getId());
            command.setInt(2, obj.getUsuario().getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(PermissaoUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    public boolean delete(PermissaoUsuarioModel obj) {

        boolean result = false;
        try {
            String sql = "DELETE FROM permissao_usuario\n"
                    
                    + " WHERE `IdPermissao` = ?,"
                    + " AND `IdUsuario`=?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, obj.getPermissao().getId());
            command.setInt(2, obj.getUsuario().getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(PermissaoUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public boolean update(PermissaoUsuarioModel obj) {

        boolean result = false;
//        try {
//            String sql = "   UPDATE categoria"
//                    + "   SET"
//                    + "   Designacao = ?,"
//                    + "   IdEstado = ?"
//                    + "   WHERE Id = ?";
//            con = conFactory.open();
//            command = con.prepareCall(sql);
//            command.setString(1, obj.getDesignacao());
//            command.setInt(2, obj.getEstado().getId());
//            command.setInt(3, obj.getId());
//            result = command.executeUpdate() > 0;
//
//        } catch (SQLException ex) {
//            Logger.getLogger(PermissaoUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(PermissaoUsuarioModel obj) {

        if (obj.getId() > 0) {
            return update(obj);
        }
        return save(obj);
    }

    @Override
    public PermissaoUsuarioModel getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PermissaoUsuarioModel> get() {

        List<PermissaoUsuarioModel> lista = new ArrayList<>();
//        try {
//
//            con = conFactory.open();
//            String sql = " SELECT * "
//                    + " FROM categoria"
//                    + " WHERE IdEstado = 1 ORDER BY 2;";
//
//            command = con.prepareCall(sql);
//            query = command.executeQuery();
//            while (query.next()) {
//
//                lista.add(new PermissaoUsuarioModel(query.getInt("Id"), query.getString("Designacao")));
//            }
//
//        } catch (Exception ex) {
//            Logger.getLogger(PermissaoUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            ////conFactory.close(con, command, query);
//        }
        return lista;
    }

    public PermissaoUsuarioModel getAll(String text) {

        PermissaoUsuarioModel lista = new PermissaoUsuarioModel();
//        try {
//
//            con = conFactory.open();
//            String sql = " SELECT * "
//                    + " FROM `categoria` WHERE Designacao = ?;";
//
//            command = con.prepareCall(sql);
//            command.setString(1, text);
//            query = command.executeQuery();
//            while (query.next()) {
//
//                lista = new PermissaoUsuarioModel(query.getInt("Id"),
//                        query.getString("Designacao"),
//                        new EstadoModel(query.getInt("IdEstado"), ""));
//            }
//
//        } catch (Exception ex) {
//            Logger.getLogger(PermissaoUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            ////conFactory.close(con, command, query);
//        }
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
            JOptionPane.showMessageDialog(null, "FALHA AO TENTAR MOSTRAR  gráfico!...");
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERRO AO TENTAR MOSTRAR gráfico !...");
        }
        
    }
}
