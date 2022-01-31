/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.CategoriaModel;
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
public class CategoriaABController implements IController<CategoriaModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(CategoriaModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `categoria`\n"
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
            Logger.getLogger(CategoriaABController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conFactory.close(con, command);
        }
        return result;
    }

    @Override
    public boolean update(CategoriaModel obj) {

        boolean result = false;
        try {
            String sql = "   UPDATE categoria"
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
            Logger.getLogger(CategoriaABController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conFactory.close(con, command);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(CategoriaModel obj) {

        if (obj.getId() > 0) {
            return update(obj);
        }
        return save(obj);
    }

    @Override
    public CategoriaModel getById(int id) {
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM categoria"
                    + " WHERE IdEstado = 1 AND Id = ? ORDER BY 2;";

            command = con.prepareCall(sql);
            command.setInt(1, id);
            query = command.executeQuery();
            if (query.next()) {

                return new CategoriaModel(query.getInt("Id"), query.getString("Designacao"));
            }
            
            ////con.close();
            command.close();
            query.close();
        } catch (Exception ex) {
            Logger.getLogger(CategoriaABController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return null;
    }

    @Override
    public List<CategoriaModel> get() {

        List<CategoriaModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM categoria"
                    + " WHERE IdEstado = 1 ORDER BY 2;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                lista.add(new CategoriaModel(query.getInt("Id"), query.getString("Designacao")));
            }
//            ////conFactory.close(con, command, query);
            ////con.close();
            command.close();
            query.close();
        } catch (Exception ex) {
            Logger.getLogger(CategoriaABController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public CategoriaModel getAll(String text) {

        CategoriaModel lista = new CategoriaModel();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM `categoria` WHERE Designacao = ?;";

            command = con.prepareCall(sql);
            command.setString(1, text);
            query = command.executeQuery();
            while (query.next()) {

                lista = new CategoriaModel(query.getInt("Id"),
                        query.getString("Designacao"),
                        new EstadoModel(query.getInt("IdEstado"), ""));
            }

        } catch (Exception ex) {
            Logger.getLogger(CategoriaABController.class.getName()).log(Level.SEVERE, null, ex);
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
        
        
        
//        hashMap.put("LOGOTIPO", "Relatorio/logo.jpg");
//        hashMap.put("LOGOTIPO",  new File("Relatorio/logo.jpg").getAbsoluteFile());
        try {
            JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            if (jasperPrint.getPages().size() >= 1) {

                JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                jasperViewer.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "Folha vazia !...");
            }
        } catch (JRException jex) {
            jex.printStackTrace();
            //System.out.println("aqui");
            JOptionPane.showMessageDialog(null, "FALHA AO TENTAR MOSTRAR  gráfico!...");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERRO AO TENTAR MOSTRAR gráfico !...");
        }

    }
}
