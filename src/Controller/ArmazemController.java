/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ArmazemModel;
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
public class ArmazemController implements IController<ArmazemModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(ArmazemModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `armazem`\n"
                    + "("
                    + "`Designacao`,"
                    + "`IdEstado`,Localizacao,Data)"
                    + "VALUES"
                    + "(?,?,?,?)";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getDesignacao());

            command.setInt(2, obj.getEstado().getId());
            command.setString(3, obj.getLocalizacao());
            command.setString(4, obj.getData());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
        }
        return result;
    }

    @Override
    public boolean update(ArmazemModel obj) {

        boolean result = false;
        try {
            String sql = "   UPDATE armazem"
                    + "   SET"
                    + "   Designacao = ?,"
                    + "   IdEstado = ?,Localizacao = ?"
                    + "   WHERE Id = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getDesignacao());

            command.setInt(2, obj.getEstado().getId());
            command.setString(3, obj.getLocalizacao());
          //  command.setString(4, obj.getData());
            command.setInt(4, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(ArmazemModel obj) {

        if (obj.getId() > 0) {
            return update(obj);
        }
        return save(obj);
    }

    @Override
    public ArmazemModel getById(int id) {
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM armazem"
                    + " WHERE IdEstado = 1 AND Id = ? ORDER BY 2;";

            command = con.prepareCall(sql);
            command.setInt(1, id);
            query = command.executeQuery();
            if (query.next()) {

                return new ArmazemModel(query.getInt("Id"), query.getString("Designacao"),
                                           query.getString("Localizacao"),
                                        new EstadoModel(query.getInt("IdEstado"), ""));
            }

        } catch (Exception ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return null;
    }

    @Override
    public List<ArmazemModel> get() {

        List<ArmazemModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM armazem"
                    + " WHERE IdEstado = 1 ;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                lista.add(new ArmazemModel(query.getInt("Id"), query.getString("Designacao"),
                                           query.getString("Localizacao"),
                                        new EstadoModel(query.getInt("IdEstado"), "")));
            }

        } catch (Exception ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }
    public List<ArmazemModel> get(String designacao) {

        List<ArmazemModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM armazem"
                    + " WHERE IdEstado = 1 AND Designacao NOT IN('"+designacao+"') ORDER BY 2;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                lista.add(new ArmazemModel(query.getInt("Id"), query.getString("Designacao"),
                                           query.getString("Localizacao"),
                                        new EstadoModel(query.getInt("IdEstado"), "")));
            }

        } catch (Exception ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public ArmazemModel getAll(String text) {

        ArmazemModel lista = new ArmazemModel();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM armazem WHERE Designacao = ?;";

            command = con.prepareCall(sql);
            command.setString(1, text);
            query = command.executeQuery();
            while (query.next()) {

                lista = new ArmazemModel(query.getInt("Id"), query.getString("Designacao"),
                                           query.getString("Localizacao"),
                                        new EstadoModel(query.getInt("IdEstado"), ""));
            }

        } catch (Exception ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public void ireportArmazem() {

        String relatorio = "Relatorio/ListaArmazem.jasper";
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
            //System.out.println("aqui");
            JOptionPane.showMessageDialog(null, "FALHA AO TENTAR MOSTRAR  gráfico!...");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERRO AO TENTAR MOSTRAR gráfico !...");
        }

    }
}
