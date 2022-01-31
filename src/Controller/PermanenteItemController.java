/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.CaixaModel;
import Model.CategoriaModel;
import Model.EstadoModel;
import Model.PermanenteItemModel;
import Model.PermanenteModel;
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
public class PermanenteItemController implements IController<PermanenteItemModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(PermanenteItemModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `permanenteitem`\n"
                    + "("
                    + "`idProduto`,"
                    + "`totalSaida`,"
                    + "`totalPerca`,"
                    + "`preco`,"
                    + "`total`,"
                    + "`idPermanente`,"
                    + "`idEstado`,"
                    + "`qtdVendida`,"
                    + "`taxaIva`,"
                    + "`totalVendido`,"
                    + "`stock`,"
                    + "`qtdEntrada`"
                    + ")"
                    + "VALUES"
                    + "(?,?,?,?,?,?,?,?,?,?,?,?)";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, obj.getProduto().getId());
            command.setDouble(2, obj.getTotalSaida());
            command.setDouble(3, obj.getTotalPerca());
            command.setDouble(4, obj.getPreco());
            command.setDouble(5, obj.getTotal());
            command.setInt(6, obj.getPermanente().getId());
            command.setInt(7, obj.getEstado().getId());
            command.setDouble(8, obj.getQtdVendida());
            command.setDouble(9, obj.getTaxaIva());
            command.setDouble(10, obj.getTotalVendido());
            command.setDouble(11, obj.getStock());
            command.setDouble(12, obj.getQtdEntrada());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(PermanenteItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conFactory.close(con, command);
        }
        return result;
    }

    @Override
    public boolean update(PermanenteItemModel obj) {

        boolean result = false;
        try {
            String sql = "   UPDATE permanenteitem"
                    + "   SET"
                    + "   idProduto = ?,"
                    + "   totalSaida = ?,"
                    + "   totalPerca = ?,"
                    + "   preco = ?,"
                    + "   total = ?,"
                    + "   idPermantente = ?,"
                    + "   idEstado = ?,"
                    + "   qtdVendida = ?,"
                    + "   taxaIva = ?,"
                    + "   totalVendido = ?,"
                    + "   stock = ?,"
                    + "   qtdEntrada = ?"
                    + "   WHERE Id = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, obj.getProduto().getId());
            command.setDouble(2, obj.getTotalSaida());
            command.setDouble(3, obj.getTotalPerca());
            command.setDouble(4, obj.getPreco());
            command.setDouble(5, obj.getTotal());
            command.setInt(6, obj.getPermanente().getId());
            command.setInt(7, obj.getEstado().getId());
            command.setDouble(8, obj.getQtdVendida());
            command.setDouble(9, obj.getTaxaIva());
            command.setDouble(10, obj.getTotalVendido());
            command.setDouble(11, obj.getStock());
            command.setDouble(12, obj.getQtdEntrada());
            command.setInt(13, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(PermanenteItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conFactory.close(con, command);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(PermanenteItemModel obj) {

        if (obj.getId() > 0) {
            return update(obj);
        }
        return save(obj);
    }

    @Override
    public PermanenteItemModel getById(int id) {
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM permanenteitem"
                    + " WHERE IdEstado = 1 AND Id = ? ORDER BY id;";

            command = con.prepareCall(sql);
            command.setInt(1, id);
            query = command.executeQuery();
            if (query.next()) {

                return new PermanenteItemModel(query.getInt("Id"), 
                        new ProdutoController().getById(query.getInt("idProduto")),
                        query.getDouble("totalSaida"),
                        query.getDouble("totalPerca"),
                        query.getDouble("preco"),
                        query.getDouble("total"),
                        new PermanenteController().getById(query.getInt("idPermanente")),
                        new EstadoController().getById(query.getInt("idEstado")), query.getDouble("stock"));
            }
            
            //con.close();
            command.close();
            query.close();
        } catch (Exception ex) {
            Logger.getLogger(PermanenteItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //conFactory.close(con, command, query);
        }
        return null;
    }

    @Override
    public List<PermanenteItemModel> get() {

        List<PermanenteItemModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM permanenteitem"
                    + " WHERE IdEstado = 1 ORDER BY id;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                lista.add(new PermanenteItemModel(query.getInt("Id"), 
                        new ProdutoController().getById(query.getInt("idProduto")),
                        query.getDouble("totalSaida"),
                        query.getDouble("totalPerca"),
                        query.getDouble("preco"),
                        query.getDouble("total"),
                        new PermanenteController().getById(query.getInt("idPermanente")),
                        new EstadoController().getById(query.getInt("idEstado")), query.getDouble("stock")));
            }
//            //conFactory.close(con, command, query);
            //con.close();
            command.close();
            query.close();
        } catch (Exception ex) {
            Logger.getLogger(PermanenteItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //conFactory.close(con, command, query);
        }
        return lista;
    }
    public List<PermanenteItemModel> getbyCaixaProduto(CaixaModel caixa, ProdutoModel produto) {

        List<PermanenteItemModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM permanenteitem pi inner join permanente p on p.id=pi.idPermanente"
                    + " WHERE p.IdEstado = 1 AND pi.idEstado = 1 and p.idCaixa = "
                    +caixa.getId()+" AND pi.idProduto = "+produto.getId()+"  ORDER BY pi.idProduto;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                lista.add(new PermanenteItemModel(query.getInt("Id"), 
                        new ProdutoController().getById(query.getInt("idProduto")),
                        query.getDouble("totalSaida"),
                        query.getDouble("totalPerca"),
                        query.getDouble("preco"),
                        query.getDouble("total"),
                        new PermanenteController().getById(query.getInt("idPermanente")),
                        new EstadoController().getById(query.getInt("idEstado")), query.getDouble("stock")));
            }
//            //conFactory.close(con, command, query);
            //con.close();
            command.close();
            query.close();
        } catch (Exception ex) {
            Logger.getLogger(PermanenteItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //conFactory.close(con, command, query);
        }
        return lista;
    }
    public boolean verificar(CaixaModel caixa, ProdutoModel produto) {

        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM permanenteitem pi inner join permanente p on p.id=pi.idPermanente"
                    + " WHERE p.IdEstado = 1 AND pi.idEstado = 1 and p.idCaixa = "
                    +caixa.getId()+" AND pi.idProduto = "+produto.getId()+"  ORDER BY pi.idProduto;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {
                return true;
            }
//            //conFactory.close(con, command, query);
            //con.close();
            command.close();
            query.close();
        } catch (Exception ex) {
            Logger.getLogger(PermanenteItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //conFactory.close(con, command, query);
        }
        return false;
    }
    
    public PermanenteModel getLastEntrada(UsuarioModel model) {

        PermanenteModel lista = new PermanenteModel();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM permanente"
                    + " WHERE idEstado = 1 AND idUsuario = ? Order by id DESC LIMIT 1 ;";

            command = con.prepareCall(sql);
            command.setInt(1, model.getId());
            query = command.executeQuery();
            while (query.next()) {

                lista.setId(query.getInt("id"));

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //conFactory.close(con, command, query);
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
            Logger.getLogger(PermanenteItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //conFactory.close(con, command, query);
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
