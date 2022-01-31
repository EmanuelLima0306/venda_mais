/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Motivo;
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
public class MotivoController implements IController<Motivo> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(Motivo obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `motivo`\n"
                    + "("
                    + "`Descricao`,"
                    + "`IdEstado`,Codigo)"
                    + "VALUES"
                    + "(?,?,?)";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getDescricao());
            command.setInt(2, obj.getEstado().getId());
            command.setString(3, obj.getCodigo());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(MotivoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return result;
    }

    @Override
    public boolean update(Motivo obj) {

        boolean result = false;
        try {
            String sql = "   UPDATE motivo"
                    + "   SET"
                    + "   Descricao = ?,"
                    + "   IdEstado = ?,Codigo = ?"
                    + "   WHERE Id = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getDescricao());
            command.setInt(2, obj.getEstado().getId());
            command.setString(3, obj.getCodigo());
            command.setInt(4, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(MotivoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(Motivo obj) {

        if (obj.getId() > 0) {
            return update(obj);
        }
        return save(obj);
    }

    @Override
    public Motivo getById(int id) {

        Motivo lista = new Motivo();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM motivo"
                    + " WHERE IdEstado = 1 AND Id=" + id;

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                lista = new Motivo(query.getInt("Id"),
                        query.getString("Descricao"),
                        query.getString("Codigo"));
            }

        } catch (Exception ex) {
            Logger.getLogger(MotivoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }
    public Motivo getById(int id,Connection con) {

        Motivo lista = new Motivo();
        try {

          
            String sql = " SELECT * "
                    + " FROM motivo"
                    + " WHERE IdEstado = 1 AND Id=" + id;

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                lista = new Motivo(query.getInt("Id"),
                        query.getString("Descricao"),
                        query.getString("Codigo"));
            }

        } catch (Exception ex) {
            Logger.getLogger(MotivoController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return lista;
    }

    @Override
    public List<Motivo> get() {

        List<Motivo> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM motivo"
                    + " WHERE IdEstado = 1 ORDER BY 3;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                lista.add(new Motivo(query.getInt("Id"),
                        query.getString("Descricao"),
                        query.getString("Codigo")));
            }
            command.close();
            ////con.close();
            query.close();
            ////conFactory.close(con, command, query);
        } catch (Exception ex) {
            Logger.getLogger(MotivoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public Motivo getAll(String text) {

        Motivo lista = new Motivo();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM `motivo` WHERE Descricao = ?;";

            command = con.prepareCall(sql);
            command.setString(1, text);
            query = command.executeQuery();
            while (query.next()) {

                lista = new Motivo(query.getInt("Id"),
                        query.getString("Descricao"),
                        query.getString("Codigo"),
                        new EstadoModel(query.getInt("IdEstado"), ""));
            }

        } catch (Exception ex) {
            Logger.getLogger(MotivoController.class.getName()).log(Level.SEVERE, null, ex);
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
            //System.out.println("aqui");
            JOptionPane.showMessageDialog(null, "FALHA AO TENTAR MOSTRAR  gráfico!...");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERRO AO TENTAR MOSTRAR gráfico !...");
        }

    }
}
