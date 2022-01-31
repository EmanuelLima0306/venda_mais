/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ArmazemModel;
import Model.CategoriaModel;
import Model.EstadoModel;
import Model.FormaPagamentoModel;
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
public class FormaPagamentoController implements IController<FormaPagamentoModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(FormaPagamentoModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO formapagamento\n"
                    + "("
                    + "`Designacao`,"
                    + "`IdEstado`,Cash,Multicaixa)"
                    + "VALUES"
                    + "(?,?,?,?)";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getDesignacao());
            command.setInt(2, obj.getEstado().getId());
            command.setBoolean(3, obj.isCash());
            command.setBoolean(4, obj.isMulticaixa());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(FormaPagamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean update(FormaPagamentoModel obj) {

        boolean result = false;
        try {
            String sql = "   UPDATE formapagamento"
                    + "   SET"
                    + "   Designacao = ?,"
                    + "   IdEstado = ?,Cash=?,Multicaixa=?"
                    + "   WHERE Id = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getDesignacao());
            command.setInt(2, obj.getEstado().getId());
             command.setBoolean(3, obj.isCash());
            command.setBoolean(4, obj.isMulticaixa());
            command.setInt(5, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(FormaPagamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(FormaPagamentoModel obj) {

        if (obj.getId() > 0) {
            return update(obj);
        }
        return save(obj);
    }

    @Override
    public FormaPagamentoModel getById(int id) {
       
         FormaPagamentoModel modelo = null;
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM formapagamento"
                    + " WHERE IdEstado = 1 AND Id = "+id;

            command = con.prepareCall(sql);
            query = command.executeQuery();
            
            while (query.next()) {

               modelo = new FormaPagamentoModel(query.getInt("Id"),
                        query.getString("Designacao"),
                        query.getBoolean("Cash"),
                        query.getBoolean("Multicaixa"),
                        new EstadoModel(query.getInt("IdEstado"), ""));
            }

        } catch (Exception ex) {
            Logger.getLogger(FormaPagamentoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return modelo;
    }

    @Override
    public List<FormaPagamentoModel> get() {

        List<FormaPagamentoModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM formapagamento"
                    + " WHERE IdEstado = 1 ORDER BY 2;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            
            while (query.next()) {

                lista.add(new FormaPagamentoModel(query.getInt("Id"),
                        query.getString("Designacao"),
                        query.getBoolean("Cash"),
                        query.getBoolean("Multicaixa"),
                        new EstadoModel(query.getInt("IdEstado"), "")));
            }

        } catch (Exception ex) {
            Logger.getLogger(FormaPagamentoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public FormaPagamentoModel getAll(String text) {

        FormaPagamentoModel lista = new FormaPagamentoModel();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM `formapagamento` WHERE Designacao = ?;";

            command = con.prepareCall(sql);
            command.setString(1, text);
            query = command.executeQuery();
            while (query.next()) {

                lista = new FormaPagamentoModel(query.getInt("Id"),
                        query.getString("Designacao"),
                        query.getBoolean("Cash"),
                        query.getBoolean("Multicaixa"),
                        new EstadoModel(query.getInt("IdEstado"), ""));
            }

        } catch (Exception ex) {
            Logger.getLogger(FormaPagamentoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public void ireportFormaPagamento() {

        String relatorio = "Relatorio/ListaFormaPagamento.jasper";
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
