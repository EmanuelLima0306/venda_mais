/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.PedidoModel;
import Model.EstadoModel;
import Model.MesaModel;
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
public class PedidoTesteController implements IController<PedidoModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(PedidoModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `pedido`\n"
                    + "("
                    + "`IdMesa`,"
                    + "`IdEstado`,Data,Nome)"
                    + "VALUES"
                    + "(?,?,?,?)";
            con = conFactory.open();
            command = con.prepareCall(sql);

            command.setInt(1, obj.getMesa().getId());
            command.setInt(2, obj.getEstado().getId());
            command.setString(3, obj.getData());
            command.setString(4, obj.getNome());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(PedidoTesteController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean update(PedidoModel obj) {

        boolean result = false;
        try {
            String sql = "   UPDATE pedido"
                    + "   SET"
                    + "   IdMesa = ?,"
                    + "   IdEstado = ?,"
                    + "   Data = ?,"
                    + "   Nome = ?"
                    + "   WHERE Id = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, obj.getMesa().getId());
            command.setInt(2, obj.getEstado().getId());
            command.setString(3, obj.getData());
            command.setString(4, obj.getNome());
            command.setInt(5, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(PedidoTesteController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }
    public boolean liquidar(int idPedido) {

        boolean result = false;
        try {
            String sql = "   UPDATE pedido"
                    + "   SET"
                    + "   IdEstado = 10"
                    + "   WHERE Id = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
//            command.setString(1, obj.getDesignacao());
       
            command.setInt(1, idPedido);
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(PedidoTesteController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(PedidoModel obj) {

        if (obj.getId() > 0) {
            return update(obj);
        }
        return save(obj);
    }

    @Override
    public PedidoModel getById(int id) {
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM pedido"
                    + " WHERE IdEstado = 1 AND Id = ?";

            command = con.prepareCall(sql);
            command.setInt(1, id);
            query = command.executeQuery();
            if (query.next()) {
                PedidoModel pedido = new PedidoModel();
                pedido.setId(query.getInt("Id"));
                pedido.setEstado(new EstadoModel(query.getInt("IdEstado"), ""));
                pedido.setData(query.getString("Data"));
                pedido.setMesa(new MesaModel(query.getInt("IdMesa"), ""));
                pedido.setNome(query.getString("Nome"));
                return pedido;
            }

        } catch (Exception ex) {
            Logger.getLogger(PedidoTesteController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return null;
    
    }

    @Override
    public List<PedidoModel> get() {

        List<PedidoModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM pedido"
                    + " WHERE IdEstado = 1 ORDER BY 2;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

//                lista.add(new PedidoModel(query.getInt("Id"), query.getString("Designacao")));
            }

        } catch (Exception ex) {
            Logger.getLogger(PedidoTesteController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }
    public int  getLastId(int idMesa) {

      int id = 0;
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM pedido"
                    + " WHERE IdEstado = 1 AND IdMesa = "+idMesa;

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {
                id = query.getInt("Id");

                
//                lista.add(new PedidoModel(query.getInt("Id"), query.getString("Designacao")));
            }

        } catch (Exception ex) {
            Logger.getLogger(PedidoTesteController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return id;
    }

    public PedidoModel getAll(String text) {

        PedidoModel lista = new PedidoModel();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM `pedido` WHERE Designacao = ?;";

            command = con.prepareCall(sql);
            command.setString(1, text);
            query = command.executeQuery();
            while (query.next()) {

//                lista = new PedidoModel(query.getInt("Id"),
//                        query.getString("Designacao"),
//                        new EstadoModel(query.getInt("IdEstado"), ""));
            }

        } catch (Exception ex) {
            Logger.getLogger(PedidoTesteController.class.getName()).log(Level.SEVERE, null, ex);
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
