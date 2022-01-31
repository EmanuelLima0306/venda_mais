/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.PedidoItemTesteModel;
import Model.EstadoModel;
import Model.PedidoModel;
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
public class PedidoItemController implements IController<PedidoItemTesteModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(PedidoItemTesteModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `pedidoitem`\n"
                    
                    + "(`IdProduto`,\n"
                    + "`Preco`,\n"
                    + "`IdEstado`,\n"
                    + "`IVA`,\n"
                    + "`DESCONTO`,\n"
                    + "`Qtd`,\n"
                    + "`CodBarra`,IdPedido,IdUsuario,Subtotal,Retencao,Total)\n"
                    + "VALUES\n"
                    + "("
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,?,?,?,?,?);";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, obj.getProduto().getId());
            command.setDouble(2, obj.getPreco());
            command.setInt(3, obj.getEstado().getId());
             command.setDouble(4, obj.getIva());
             command.setDouble(5, obj.getDesconto());
             command.setDouble(6, obj.getQtd());
             command.setString(7, obj.getCoBarra());
              command.setInt(8, obj.getPedido().getId());
              command.setInt(9, obj.getUsuario().getId());
              command.setDouble(10, obj.getSubTotal());
              command.setDouble(11, obj.getRetencao());
              command.setDouble(12, obj.getTotal());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(PedidoItemController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean update(PedidoItemTesteModel obj) {

        boolean result = false;
        try {
            String sql = "   UPDATE pedidoitem"
                    + "   SET"
                    + "   Qtd = ?,IVA = ?,IdEstado = ?,Subtotal = ?,Retencao = ?, Total = ?, DESCONTO = ?, Preco = ? "
                    + "   WHERE Id = ? ";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setDouble(1, obj.getQtd());
            command.setDouble(2, obj.getIva());
            command.setInt(3, obj.getEstado().getId());
            command.setDouble(4, obj.getSubTotal());
            command.setDouble(5, obj.getRetencao());
            command.setDouble(6, obj.getTotal());
            command.setDouble(7, obj.getDesconto());
            command.setDouble(8, obj.getPreco());
            command.setInt(9, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(PedidoItemController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(PedidoItemTesteModel obj) {

        if (obj.getId() > 0) {
            return update(obj);
        }
        return save(obj);
    }

    @Override
    public PedidoItemTesteModel getById(int id) {
    
        List<PedidoItemTesteModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM pedidoitem i "
                    + " INNER JOIN pedido pd ON pd.Id = i.IdPedido"
                    + " INNER JOIN produto p ON p.Id = i.IdProduto"
                    + " INNER JOIN usuario u ON u.Id = i.IdUsuario "
                    + " INNER JOIN estado e ON e.Id = i.IdEstado"
                    + " WHERE i.IdEstado IN(1,6) AND pd.IdEstado = 1 AND i.Id = ?";

            command = con.prepareCall(sql);
            command.setInt(1, id);
            query = command.executeQuery();
            if (query.next()) {
                PedidoItemTesteModel model = new PedidoItemTesteModel();
                model.setCoBarra(query.getString("CodBarra"));
                
//                ProdutoModel produto = new ProdutoModel(query.getInt("p.Id"), query.getString("p.Designacao"));
                UsuarioModel usuario = new UsuarioModel(query.getInt("u.Id"), query.getString("u.Nome"));
                EstadoModel estado = new EstadoModel(query.getInt("e.Id"), query.getString("e.Designacao"));
                PedidoModel pedido = new PedidoModel();
                pedido.setNome(query.getString("pd.Nome"));
                pedido.setId(query.getInt("pd.Id"));
                
                model.setQtd(query.getDouble("Qtd"));
                model.setPreco(query.getDouble("i.Preco"));
                model.setIva(query.getDouble("i.IVA"));
                model.setDesconto(query.getDouble("i.DESCONTO"));
                model.setSubTotal(query.getDouble("i.Subtotal"));
                model.setRetencao(query.getDouble("i.Retencao"));
                model.setTotal(query.getDouble("i.Total"));
                model.setProduto(new ProdutoController().getById(query.getInt("p.Id")));
                model.setUsuario(usuario);
                model.setEstado(estado);
                model.setPedido(pedido);
                
                return model;
                
//                lista.add(new PedidoItemModel(query.getInt("Id"), query.getString("Designacao")));
            }

        } catch (Exception ex) {
            Logger.getLogger(PedidoItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return null;
    }
    public PedidoItemTesteModel getByProdutoPedido(ProdutoModel produto, PedidoModel pedid) {
    
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM pedidoitem i "
                    + " INNER JOIN pedido pd ON pd.Id = i.IdPedido"
                    + " INNER JOIN produto p ON p.Id = i.IdProduto"
                    + " INNER JOIN usuario u ON u.Id = i.IdUsuario "
                    + " INNER JOIN estado e ON e.Id = i.IdEstado"
                    + " WHERE i.IdEstado IN(1,6) AND pd.IdEstado = 1 AND p.Id = ? AND pd.Id = ?";

            command = con.prepareCall(sql);
            command.setInt(1, produto.getId());
            command.setInt(2, pedid.getId());
            query = command.executeQuery();
            
            if (query.next()) {
                
                PedidoItemTesteModel model = new PedidoItemTesteModel();
                model.setId(query.getInt("i.Id"));
                model.setCoBarra(query.getString("CodBarra"));
                
//                ProdutoModel produto = new ProdutoModel(query.getInt("p.Id"), query.getString("p.Designacao"));
                UsuarioModel usuario = new UsuarioModel(query.getInt("u.Id"), query.getString("u.Nome"));
                EstadoModel estado = new EstadoModel(query.getInt("e.Id"), query.getString("e.Designacao"));
                PedidoModel pedido = new PedidoModel();
                pedido.setNome(query.getString("pd.Nome"));
                pedido.setId(query.getInt("pd.Id"));
                
                model.setQtd(query.getDouble("Qtd"));
                model.setPreco(query.getDouble("i.Preco"));
                model.setIva(query.getDouble("i.IVA"));
                model.setDesconto(query.getDouble("i.DESCONTO"));
                model.setSubTotal(query.getDouble("i.Subtotal"));
                model.setRetencao(query.getDouble("i.Retencao"));
                model.setTotal(query.getDouble("i.Total"));
                model.setProduto(new ProdutoController().getById(query.getInt("p.Id")));
                model.setUsuario(usuario);
                model.setEstado(estado);
                model.setPedido(pedido);
                
                return model;
                
//                lista.add(new PedidoItemModel(query.getInt("Id"), query.getString("Designacao")));
            }

        } catch (Exception ex) {
            Logger.getLogger(PedidoItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return null;
    }

    @Override
    public List<PedidoItemTesteModel> get() {

        List<PedidoItemTesteModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM categoria"
                    + " WHERE IdEstado = 1 ORDER BY 2;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

//                lista.add(new PedidoItemModel(query.getInt("Id"), query.getString("Designacao")));
            }

        } catch (Exception ex) {
            Logger.getLogger(PedidoItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }
       public boolean desactivar(int idPedidoItem) {

        boolean result = false;
        try {
            String sql = "   UPDATE pedidoitem"
                    + "   SET"
                    + "   IdEstado = 4"
                    + "   WHERE Id = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
//            command.setString(1, obj.getDesignacao());
       
            command.setInt(1, idPedidoItem);
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(PedidoTesteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    public List<PedidoItemTesteModel> get(Integer idMesa) {

        List<PedidoItemTesteModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM pedidoitem i "
                    + " INNER JOIN pedido pd ON pd.Id = i.IdPedido"
                    + " INNER JOIN produto p ON p.Id = i.IdProduto"
                    + " INNER JOIN usuario u ON u.Id = i.IdUsuario "
                    + " INNER JOIN estado e ON e.Id = i.IdEstado"
                    + " WHERE i.IdEstado IN(1,6) AND pd.IdEstado = 1 AND pd.IdMesa ="+idMesa;

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {
                PedidoItemTesteModel model = new PedidoItemTesteModel();
                model.setCoBarra(query.getString("CodBarra"));
                
//                ProdutoModel produto = new ProdutoModel(query.getInt("p.Id"), query.getString("p.Designacao"));
                UsuarioModel usuario = new UsuarioModel(query.getInt("u.Id"), query.getString("u.Nome"));
                EstadoModel estado = new EstadoModel(query.getInt("e.Id"), query.getString("e.Designacao"));
                PedidoModel pedido = new PedidoModel();
                pedido.setNome(query.getString("pd.Nome"));
                pedido.setId(query.getInt("pd.Id"));
                
                model.setQtd(query.getDouble("Qtd"));
                model.setPreco(query.getDouble("i.Preco"));
                model.setIva(query.getDouble("i.IVA"));
                model.setDesconto(query.getDouble("i.DESCONTO"));
                model.setSubTotal(query.getDouble("i.Subtotal"));
                model.setRetencao(query.getDouble("i.Retencao"));
                model.setTotal(query.getDouble("i.Total"));
                model.setProduto(new ProdutoController().getById(query.getInt("p.Id")));
                model.setUsuario(usuario);
                model.setEstado(estado);
                model.setPedido(pedido);
                
                lista.add(model);
                
//                lista.add(new PedidoItemModel(query.getInt("Id"), query.getString("Designacao")));
            }

        } catch (Exception ex) {
            Logger.getLogger(PedidoItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }
    public PedidoItemTesteModel get(Integer idMesa,int idProduto) {

       PedidoItemTesteModel model = new PedidoItemTesteModel();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM pedidoitem i "
                    + " INNER JOIN pedido pd ON pd.Id = i.IdPedido"
                    + " INNER JOIN produto p ON p.Id = i.IdProduto"
                    + " INNER JOIN usuario u ON u.Id = i.IdUsuario "
                    + " INNER JOIN estado e ON e.Id = i.IdEstado"
                    + " WHERE i.IdEstado IN(1,6) AND pd.IdEstado = 1 AND pd.IdMesa ="+idMesa+" AND i.IdProduto ="+idProduto;

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {

              
                model.setCoBarra(query.getString("CodBarra"));
                
                ProdutoModel produto = new ProdutoModel(query.getInt("p.Id"), query.getString("p.Designacao"));
                UsuarioModel usuario = new UsuarioModel(query.getInt("u.Id"), query.getString("u.Nome"));
                EstadoModel estado = new EstadoModel(query.getInt("e.Id"), query.getString("e.Designacao"));
                PedidoModel pedido = new PedidoModel();
                pedido.setNome(query.getString("pd.Nome"));
                
                model.setQtd(query.getDouble("Qtd"));
                model.setPreco(query.getDouble("i.Preco"));
                model.setIva(query.getDouble("i.IVA"));
                model.setDesconto(query.getDouble("i.DESCONTO"));
                model.setSubTotal(query.getDouble("i.Subtotal"));
                model.setRetencao(query.getDouble("i.Retencao"));
                model.setTotal(query.getDouble("i.Total"));
                model.setProduto(produto);
                model.setUsuario(usuario);
                model.setEstado(estado);
                model.setId(query.getInt("i.Id"));
                
               return model;
                
//                lista.add(new PedidoItemModel(query.getInt("Id"), query.getString("Designacao")));
            }

        } catch (Exception ex) {
            Logger.getLogger(PedidoItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return model;
    }

    public PedidoItemTesteModel getAll(String text) {

        PedidoItemTesteModel lista = new PedidoItemTesteModel();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM `categoria` WHERE Designacao = ?;";

            command = con.prepareCall(sql);
            command.setString(1, text);
            query = command.executeQuery();
            while (query.next()) {

//                lista = new PedidoItemModel(query.getInt("Id"),
//                        query.getString("Designacao"),
//                        new EstadoModel(query.getInt("IdEstado"), ""));
            }

        } catch (Exception ex) {
            Logger.getLogger(PedidoItemController.class.getName()).log(Level.SEVERE, null, ex);
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
