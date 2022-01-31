/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.FacturaModel;
import Model.Motivo;
import Model.Movimento;
import Model.MovimentoItemModel;
import Model.ProdutoModel;
import Model.Taxa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author celso
 */
public class MovimentoItemController implements IController<MovimentoItemModel> {
    
    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;
    
    @Override
    public boolean save(MovimentoItemModel obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean update(MovimentoItemModel obj) {
        
        boolean result = false;
        /* try {
            String sql = " UPDATE  `facturaitem`"
                       + " SET IdEstado = "+obj.getEstado().getId()+""
                       + " WHERE IdFactura = "+obj.getFactura().getId()+""
                       + " AND IdProduto = "+obj.getProduto().getId()+""
                      + "  AND Lote = '"+obj.getLote()+"'\n";
                    

            con = conFactory.open();
            command = con.prepareCall(sql);

            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(MovimentoItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
         */
        return result;
    }
    
    @Override
    public boolean saveOrUpdate(MovimentoItemModel obj) {
        
        boolean result = false;
        try {
            String sql = "INSERT INTO `movimentoitem`\n"
                    + "(`Desconto`,\n"
                    + "`IdMovimento`,\n"
                    + "`IdProduto`,\n"
                    + "`Preco`,\n"
                    + "`Qtd`,\n"
                    + "`SubTotal`,\n"
                    + "`Iva`,Lote,Total)\n"
                    + "VALUES\n"
                    + "(?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,?,?);";
            
            con = conFactory.open();
            command = con.prepareCall(sql);
            
            command.setDouble(1, obj.getFacturaItemModel().getDesconto());
            command.setInt(2, obj.getMovimento().getId());
            command.setInt(3, obj.getFacturaItemModel().getProduto().getId());
            command.setDouble(4, obj.getFacturaItemModel().getPreco());
            command.setDouble(5, obj.getFacturaItemModel().getQtd());
            command.setDouble(6, obj.getFacturaItemModel().getSubTotal());
            command.setDouble(7, obj.getFacturaItemModel().getIva());
            command.setString(8, obj.getFacturaItemModel().getLote());
            command.setDouble(9, obj.getFacturaItemModel().getTotal());
            
            result = command.executeUpdate() > 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(MovimentoItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        
        return result;
    }
    
    @Override
    public MovimentoItemModel getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<MovimentoItemModel> get() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public double getTotalCredit(String data, String data1) {

        String sql = "SELECT sum(SubTotal) total FROM facturaitem fi \n"
                + " WHERE fi.IdFactura IN (SELECT f.Id FROM factura f WHERE f.IdEstado = 1 \n"
                + "                      AND date(Data) between '"+data+"' and '"+data1+"'\n"
                + "                      and (f.TipoFactura ='Venda' or f.TipoFactura ='Perfoma'));";
        double valor = 0;
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {
                valor = query.getDouble("total");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        finally {
//            ////conFactory.close(con, command, query);
//        }
        return valor;
    }
public String totalNota(String data, String data1) {

//        Connection con = conFactory.open();
        String sql = "select count(Id) as total "
                + "from movimento "
                + "where date(Data) between '" + data + "' and '" + data1 + "'";

        String valor = "0";
        try {

            con = conFactory.open();
            command = con.prepareCall(sql);
            query = command.executeQuery();

            if (query.next()) {
                valor = String.valueOf(query.getInt("total"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        finally {
//            ////conFactory.close(con, command, query);
//        }
        return valor;
    }
    public List<MovimentoItemModel> getItemByIdFactura(int idFactura) {
        
        List<MovimentoItemModel> lista = new ArrayList();
        
        String sql = "SELECT * FROM movimentoitem f INNER JOIN produto p  ON p.Id = f.IdProduto WHERE IdFactura = " + idFactura;
        try {
            con = conFactory.open();
            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {
                
                MovimentoItemModel model = new MovimentoItemModel();
                model.setId(query.getInt("f.Id"));
                model.getFacturaItemModel().setDesconto(query.getDouble("f.Desconto"));
                model.getFacturaItemModel().setIva(query.getDouble("f.Iva"));
                model.getFacturaItemModel().setLote(query.getString("f.Lote"));
                model.getFacturaItemModel().setQtd(query.getDouble("f.Qtd"));
                model.getFacturaItemModel().setPreco(query.getDouble("f.Preco"));
                model.getFacturaItemModel().setSubTotal(query.getDouble("f.SubTotal"));
                model.getFacturaItemModel().setTotal(query.getDouble("f.Total"));
                
                ProdutoModel p = new ProdutoModel(query.getInt("p.Id"), query.getString("p.Designacao"));
                model.getFacturaItemModel().setProduto(p);
                
                lista.add(model);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MovimentoItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        
        return lista;
    }

    public List<MovimentoItemModel> getItemByIdMovimento(int id, Connection con) {
        
        List<MovimentoItemModel> lista = new ArrayList();
        
        String sql = "SELECT * FROM movimentoitem f"
                + " INNER JOIN produto p  ON p.Id = f.IdProduto "
                + " INNER JOIN taxa t ON t.Id = p.IdTaxa "
                + " INNER JOIN motivo mo ON mo.Id = p.IdMotivo"
                + " INNER JOIN movimento m ON m.Id = f.IdMovimento"
                + " INNER JOIN factura ft ON ft.Id = m.IdFactura"
                + "WHERE f.IdMovimento = " + id;
        try {
            
            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {
                
                MovimentoItemModel model = new MovimentoItemModel();
                model.setId(query.getInt("f.Id"));
                model.getFacturaItemModel().setDesconto(query.getDouble("f.Desconto"));
                model.getFacturaItemModel().setIva(query.getDouble("f.Iva"));
                model.getFacturaItemModel().setLote(query.getString("f.Lote"));
                model.getFacturaItemModel().setQtd(query.getDouble("f.Qtd"));
                model.getFacturaItemModel().setPreco(query.getDouble("f.Preco"));
                model.getFacturaItemModel().setSubTotal(query.getDouble("f.SubTotal"));
                model.getFacturaItemModel().setTotal(query.getDouble("f.Total"));
                
                FacturaModel facturaModel = new FacturaModel();
                facturaModel.setNextFactura(query.getString("ft.NextFactura"));
                
                Movimento m = new Movimento();
                m.setNext(query.getString("m.NextFactura"));
                m.setId(query.getInt("m.Id"));
                
                m.setFactura(facturaModel);
                
                model.setMovimento(m);
                
                ProdutoModel p = new ProdutoModel(query.getInt("p.Id"), query.getString("p.Designacao"));
                
                Taxa t = new Taxa();
                t.setDescricao(query.getString("t.Descricao"));
                t.setId(query.getInt("t.Id"));
                t.setTaxa(query.getDouble("t.Taxa"));
                
                p.setTaxa(t);
                
                Motivo motivo = new Motivo();
                motivo.setCodigo(query.getString("mo.Codigo"));
                motivo.setId(query.getInt("mo.Id"));
                motivo.setDescricao(query.getString("mo.Descricao"));
                
                p.setMotivo(motivo);
                
                model.getFacturaItemModel().setProduto(p);
                
                
                lista.add(model);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MovimentoItemController.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        return lista;
    }
    public List<MovimentoItemModel> getItemByIdMovimento(int id) {
        
        List<MovimentoItemModel> lista = new ArrayList();
        
        String sql = "SELECT * FROM movimentoitem f"
                + " INNER JOIN produto p  ON p.Id = f.IdProduto "
                + " INNER JOIN taxa t ON t.Id = p.IdTaxa "
                + " INNER JOIN motivo mo ON mo.Id = p.IdMotivo"
                + " INNER JOIN movimento m ON m.Id = f.IdMovimento"
                + " INNER JOIN factura ft ON ft.Id = m.IdFactura"
                + " WHERE f.IdMovimento = " + id;
        try {
            
            Connection con = conFactory.open();
            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {
                
                MovimentoItemModel model = new MovimentoItemModel();
                model.setId(query.getInt("f.Id"));
                model.getFacturaItemModel().setDesconto(query.getDouble("f.Desconto"));
                model.getFacturaItemModel().setIva(query.getDouble("f.Iva"));
                model.getFacturaItemModel().setLote(query.getString("f.Lote"));
                model.getFacturaItemModel().setQtd(query.getDouble("f.Qtd"));
                model.getFacturaItemModel().setPreco(query.getDouble("f.Preco"));
                model.getFacturaItemModel().setSubTotal(query.getDouble("f.SubTotal"));
                model.getFacturaItemModel().setTotal(query.getDouble("f.Total"));
                
                Movimento m = new Movimento();
                m.setNext(query.getString("m.NextFactura"));
                m.setId(query.getInt("m.Id"));
                
                
                FacturaModel facturaModel = new FacturaModel();
                facturaModel.setNextFactura(query.getString("ft.NextFactura"));
                m.setFactura(facturaModel);
                model.setMovimento(m);
                
                ProdutoModel p = new ProdutoModel(query.getInt("p.Id"), query.getString("p.Designacao"));
                
                Taxa t = new Taxa();
                t.setDescricao(query.getString("t.Descricao"));
                t.setId(query.getInt("t.Id"));
                t.setTaxa(query.getDouble("t.Taxa"));
                
                p.setTaxa(t);
                
                Motivo motivo = new Motivo();
                motivo.setCodigo(query.getString("mo.Codigo"));
                motivo.setId(query.getInt("mo.Id"));
                motivo.setDescricao(query.getString("mo.Descricao"));
                
                p.setMotivo(motivo);
                
                model.getFacturaItemModel().setProduto(p);
                
                
                lista.add(model);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MovimentoItemController.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        return lista;
    }

    public Double getTotalMovimento(int id, Connection con) {
        
        List<MovimentoItemModel> lista = new ArrayList();
        
        String sql = "SELECT Sum(f.Total) total,Sum(f.Iva) imposto FROM movimentoitem f INNER JOIN produto p  ON p.Id = f.IdProduto WHERE IdMovimento = " + id;
        try {
            
            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {
                
                return query.getDouble("total")-query.getDouble("imposto");
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MovimentoItemController.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        return 0.0;
    }
    public Double getTotalDescontoMovimento(int id, Connection con) {
        
        List<MovimentoItemModel> lista = new ArrayList();
        
        String sql = "SELECT Sum(f.Desconto) total FROM movimentoitem f INNER JOIN produto p  ON p.Id = f.IdProduto WHERE IdMovimento = " + id;
        try {
            
            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {
                
                return query.getDouble("total");
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MovimentoItemController.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        return 0.0;
    }

    public Double getTotalIvaMovimento(int id, Connection con) {
        
        List<MovimentoItemModel> lista = new ArrayList();
        
        String sql = "SELECT Sum(f.Iva) total FROM movimentoitem f INNER JOIN produto p  ON p.Id = f.IdProduto WHERE IdMovimento = " + id;
        try {
            
            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {
                
                return query.getDouble("total");
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MovimentoItemController.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        return 0.0;
    }

    public Double getSubTotalMovimento(int id, Connection con) {
        
        List<MovimentoItemModel> lista = new ArrayList();
        
        String sql = "SELECT Sum(f.SubTotal) total FROM movimentoitem f INNER JOIN produto p  ON p.Id = f.IdProduto WHERE IdMovimento = " + id;
        try {
            
            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {
                
                return query.getDouble("total");
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MovimentoItemController.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        return 0.0;
    }

    public Double getSubTotalDescontoMovimento(int id, Connection con) {
        
        List<MovimentoItemModel> lista = new ArrayList();
        
        String sql = "SELECT Sum(f.Desconto) total FROM movimentoitem f INNER JOIN produto p  ON p.Id = f.IdProduto WHERE IdMovimento = " + id;
        try {
            
            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {
                
                return query.getDouble("total");
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MovimentoItemController.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        return 0.0;
    }

    public Double getItemByIdFactura(int idFactura, Connection con) {
        
        String sql = "SELECT SUM(f.Desconto) desconto FROM facturaitem f INNER JOIN produto p  ON p.Id = f.IdProduto WHERE IdFactura = " + idFactura;
        try {
            
            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {
                
                return query.getDouble("desconto");
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MovimentoItemController.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        return 0.0;
    }

    public List<MovimentoItemModel> getItemByIdFactura(int idFactura, String cliente) {
        
        List<MovimentoItemModel> lista = new ArrayList();
        
        String sql = "SELECT * FROM facturaitem f"
                + " INNER JOIN produto p  ON p.Id = f.IdProduto"
                + " INNER JOIN factura factura ON factura.Id = f.IdFactura"
                + " WHERE f.IdEstado = 1 AND NomeCliente = '" + cliente + "' AND  IdFactura = " + idFactura;
        try {
            con = conFactory.open();
            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {
                
                MovimentoItemModel model = new MovimentoItemModel();
                model.getFacturaItemModel().setId(query.getInt("f.Id"));
                model.getFacturaItemModel().setDesconto(query.getDouble("f.Desconto"));
                model.getFacturaItemModel().setIva(query.getDouble("f.Iva"));
                model.getFacturaItemModel().setLote(query.getString("f.Lote"));
                model.getFacturaItemModel().setQtd(query.getDouble("f.Qtd"));
                model.getFacturaItemModel().setPreco(query.getDouble("f.Preco"));
                model.getFacturaItemModel().setSubTotal(query.getDouble("f.SubTotal"));
                model.getFacturaItemModel().setTotal(query.getDouble("f.Total"));
                
                ProdutoModel p = new ProdutoModel(query.getInt("p.Id"), query.getString("p.Designacao"));
                model.getFacturaItemModel().setProduto(p);
                
                lista.add(model);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MovimentoItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        
        return lista;
    }
    
}
