/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.CategoriaModel;
import Model.EntradaStockModel;
import Model.EstadoModel;
import Model.ProdutoModel;
import Model.UsuarioModel;
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
public class EntradaStockController implements IController<EntradaStockModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(EntradaStockModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `entradaproduto`\n"
                    + "("
                    + "`IdFornecedor`,\n"
                    + "`IdUsuario`,\n"
                    + "`ValorTotal`,\n"
                    + "`DataFactura`,\n"
                    + "`IdFormaPagamento`,\n"
                    + "`TemDivida`,\n"
                    + "`Data`,\n"
                    + "`IdEstado`,NumFactura)\n"
                    + "VALUES\n"
                    + "("
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,?);";
            con = conFactory.open();
            command = con.prepareCall(sql);

            command.setInt(1, obj.getFornecedor().getId());
            command.setInt(2, obj.getUsuario().getId());
            command.setDouble(3, obj.getTotal());
            System.out.println(obj.getDataFactura());
            command.setString(4, obj.getDataFactura());
            command.setInt(5, obj.getFormaPagamento().getId());
            command.setBoolean(6, obj.isTemDivida());
            command.setString(7, obj.getData());
            command.setInt(8, obj.getEstado().getId());
            command.setString(9, obj.getNumFactura());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return result;
    }

    @Override
    public boolean update(EntradaStockModel obj) {

        boolean result = false;
        try {
            String sql = "  UPDATE `entradaproduto`\n"
                    + "SET\n"
                    + "\n"
                    + "`IdFornecedor` = ?,\n"
                    + "`ValorTotal` = ?,\n"
                    + "`DataFactura` = ?,\n"
                    + "`IdFormaPagamento` = ?,\n"
                    + "`TemDivida` = ?,\n"
                    + "\n"
                    + "`IdEstado` = ?\n"
                    + "WHERE `Id` = ?;";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, obj.getFornecedor().getId());

            command.setDouble(2, obj.getTotal());
            command.setString(3, obj.getDataFactura());
            command.setInt(4, obj.getFormaPagamento().getId());
            command.setBoolean(5, obj.isTemDivida());

            command.setInt(6, obj.getEstado().getId());
            command.setInt(7, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    public boolean updateFormaPagamento(EntradaStockModel obj) {

        boolean result = false;
        try {
            String sql = "  UPDATE `entradaproduto`\n"
                    + "SET\n"
                    + "`IdFormaPagamento` = ?\n"
                    + "WHERE `Id` = ?;";
            con = conFactory.open();
            command = con.prepareStatement(sql);
            command.setInt(1, obj.getFormaPagamento().getId());

            command.setInt(2, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(EntradaStockModel obj) {

        if (obj.getId() > 0) {
            return update(obj);
        }
        return save(obj);
    }

    @Override
    public EntradaStockModel getById(int id) {
        EntradaStockModel modelo = null;
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM entradaproduto"
                    + " WHERE IdEstado = 1 AND Id = ? ;";

            command = con.prepareCall(sql);
            command.setInt(1, id);
            query = command.executeQuery();
            if (query.next()) {
                modelo = new EntradaStockModel();
                modelo.setId(query.getInt("Id"));
                modelo.setData(query.getString("Data"));
                modelo.setDataFactura(query.getString("DataFactura"));

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return modelo;
    }
    

    @Override
    public List<EntradaStockModel> get() {

        List<EntradaStockModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM entradaproduto"
                    + " WHERE IdEstado = 1 ;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                //  lista.add(new EntradaStockModel(query.getInt("Id"), query.getString("Designacao")));
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public List<EntradaStockModel> getLast() {

        List<EntradaStockModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT MAX(Id) "
                    + " FROM entradaproduto"
                    + " WHERE IdEstado = 1 ;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                //  lista.add(new EntradaStockModel(query.getInt("Id"), query.getString("Designacao")));
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public EntradaStockModel getLastEntrada(UsuarioModel model) {

        EntradaStockModel lista = new EntradaStockModel();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM entradaproduto"
                    + " WHERE IdEstado = 1 AND IdUsuario = ? Order by Id DESC LIMIT 1 ;";

            command = con.prepareCall(sql);
            command.setInt(1, model.getId());
            query = command.executeQuery();
            while (query.next()) {

                lista.setId(query.getInt("Id"));

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockController.class.getName()).log(Level.SEVERE, null, ex);
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
        }finally{
            conFactory.close(con, command,query);
        }

    }

    public List<EntradaStockModel> getEntradaStockDividaFornecedor(int id) {
        List<EntradaStockModel> lista = new ArrayList();
        try {

            con = conFactory.open();
            String sql = " SELECT NumFactura,Id,ValorTotal "
                    + " FROM entradaproduto"
                    + " WHERE IdFormaPagamento = 4 and IdFornecedor = ? Order by Id DESC ;";

            command = con.prepareCall(sql);
            command.setInt(1, id);
            query = command.executeQuery();
            while (query.next()) {

                EntradaStockModel modelo = new EntradaStockModel();
                modelo.setId(query.getInt("Id"));
                modelo.setNumFactura(query.getString("NumFactura"));
                modelo.setTotal(query.getDouble("ValorTotal"));

                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public double getTotalDividaFornecedor(int id) {

        double total = 0;
        try {

            con = conFactory.open();
            String sql = " SELECT SUM(ValorTotal) AS ValorTotal "
                    + " FROM entradaproduto"
                    + " WHERE IdEstado = 1 AND IdFormaPagamento = 4 and IdFornecedor = ? Order by Id DESC LIMIT 1 ;";

            command = con.prepareCall(sql);
            command.setInt(1, id);
            query = command.executeQuery();
            if (query.next()) {

                total = query.getDouble("ValorTotal");
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return total;
    }
}
