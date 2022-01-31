/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Taxa;
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
public class TaxaController implements IController<Taxa> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(Taxa obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `taxa`\n"
                    + "("
                    + "`Descricao`,"
                    + "`IdEstado`,Taxa)"
                    + "VALUES"
                    + "(?,?,?)";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getDescricao());
            command.setInt(2, obj.getEstado().getId());
            command.setDouble(3, obj.getTaxa());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(TaxaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return result;
    }

    @Override
    public boolean update(Taxa obj) {

        boolean result = false;
        try {
            String sql = "   UPDATE taxa"
                    + "   SET"
                    + "   Descricao = ?,"
                    + "   IdEstado = ?,Taxa = ?"
                    + "   WHERE Id = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getDescricao());
            command.setInt(2, obj.getEstado().getId());
            command.setDouble(3, obj.getTaxa());
            command.setInt(4, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(TaxaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(Taxa obj) {

        if (obj.getId() > 0) {
            return update(obj);
        }
        return save(obj);
    }

    @Override
    public Taxa getById(int id) {

        Taxa lista = new Taxa();
        try {
            
            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM taxa"
                    + " WHERE IdEstado = 1 AND Id = " + id;

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {

                lista = new Taxa(query.getInt("Id"),
                        query.getString("Descricao"),
                        query.getDouble("Taxa"));
                return lista;
            }

        } catch (Exception ex) {
            Logger.getLogger(TaxaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    @Override
    public List<Taxa> get() {

        List<Taxa> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM taxa"
                    + " WHERE IdEstado = 1 ;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                lista.add(new Taxa(query.getInt("Id"),
                        query.getString("Descricao"),
                        query.getDouble("Taxa")));
            }
            command.close();
            ////con.close();
            query.close();
            ////conFactory.close(con, command, query);
        } catch (Exception ex) {
            Logger.getLogger(TaxaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }
    public List<Taxa> getExecluir(int id) {

        List<Taxa> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM taxa"
                    + " WHERE IdEstado = 1 AND id NOT IN ("+id+")";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                lista.add(new Taxa(query.getInt("Id"),
                        query.getString("Descricao"),
                        query.getDouble("Taxa")));
            }
            command.close();
            ////con.close();
            query.close();
            ////conFactory.close(con, command, query);
        } catch (Exception ex) {
            Logger.getLogger(TaxaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public Taxa getAll(String text) {

        Taxa lista = new Taxa();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM `taxa` WHERE Descricao = ?;";

            command = con.prepareCall(sql);
            command.setString(1, text);
            query = command.executeQuery();
            while (query.next()) {

                lista = new Taxa(query.getInt("Id"),
                        query.getString("Descricao"),
                        query.getDouble("Taxa"),
                        new EstadoModel(query.getInt("IdEstado"), ""));
            }

        } catch (Exception ex) {
            Logger.getLogger(TaxaController.class.getName()).log(Level.SEVERE, null, ex);
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
          
            JOptionPane.showMessageDialog(null, "FALHA AO TENTAR MOSTRAR  gráfico!...");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERRO AO TENTAR MOSTRAR gráfico !...");
        }

    }
}
