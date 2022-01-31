/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.BarbeiroItemModel;
import Model.CaixaModel;
import Model.FacturaItemModel;
import Model.FacturaModel;
import Model.FormaPagamentoModel;
import Model.ParamentroModel;
import Model.ProdutoModel;
import Util.ConnectionFactor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author celso
 */
public class FacturaItemController implements IController<FacturaItemModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(FacturaItemModel obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(FacturaItemModel obj) {

        boolean result = false;
        try {
            String sql = " UPDATE  `facturaitem`"
                    + " SET IdEstado = " + obj.getEstado().getId() + ""
                    + " WHERE IdFactura = " + obj.getFactura().getId() + ""
                    + " AND IdProduto = " + obj.getProduto().getId() + ""
                    + "  AND Lote = '" + obj.getLote() + "'\n";

            con = conFactory.open();            
//            con = Connection.conexao;
            command = con.prepareCall(sql);

            result = command.executeUpdate() > 0;

            if (result) {
                BarbeiroItemController barbeiroItemController = new BarbeiroItemController();
                for (BarbeiroItemModel b : obj.getBarbeiro()) {
                    b.setEstadoModel(obj.getEstado());
                    barbeiroItemController.update(b);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(FacturaItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }

        return result;
    }

    @Override
    public boolean saveOrUpdate(FacturaItemModel obj) {

        boolean result = false;
        try {

            ParamentroController paramentroController = new ParamentroController();
            ParamentroModel paramentroModel = paramentroController.getById(3);
            String sql = "INSERT INTO `facturaitem`\n"
                    + "(`Desconto`,\n"
                    + "`IdFactura`,\n"
                    + "`IdProduto`,\n"
                    + "`Preco`,\n"
                    + "`Qtd`,\n"
                    + "`SubTotal`,\n"
                    + "`Iva`,Lote,Total,Retencao)\n"
                    + "VALUES\n"
                    + "(?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,?,?,?);";

            con = conFactory.open();            
            //con = ConnectionFactor.conexao;
            command = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            command.setDouble(1, obj.getDesconto());
            command.setInt(2, obj.getFactura().getId());
            command.setInt(3, obj.getProduto().getId());
            command.setDouble(4, obj.getPreco());
            command.setDouble(5, obj.getQtd());
            command.setDouble(6, obj.getSubTotal());
            command.setDouble(7, obj.getIva());
            command.setString(8, obj.getLote());
            command.setDouble(9, obj.getTotal());
            command.setDouble(10, obj.getRetencao());

            result = command.executeUpdate() > 0;
            final ResultSet rs = command.getGeneratedKeys();
            if (result) {

                if (rs.next()) {

                    BarbeiroItemController barbeiroItemController = new BarbeiroItemController();
                    obj.setId(rs.getInt(1));
                    for (BarbeiroItemModel b : obj.getBarbeiro()) {

                        b.setFacturaItemModel(obj);
                        barbeiroItemController.save(b);
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(FacturaItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }

        return result;
    }

    @Override
    public FacturaItemModel getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FacturaItemModel> get() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<FacturaItemModel> getItemByIdFactura(int idFactura) {

        List<FacturaItemModel> lista = new ArrayList();

        String sql = "SELECT * FROM facturaitem f INNER JOIN produto p  ON p.Id = f.IdProduto WHERE IdFactura = " + idFactura;
        try {
            con = conFactory.open();            
            //con = Connection.conexao;
            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                FacturaItemModel model = new FacturaItemModel();
                model.setId(query.getInt("f.Id"));
                model.setDesconto(query.getDouble("f.Desconto"));
                model.setIva(query.getDouble("f.Iva"));
                model.setLote(query.getString("f.Lote"));
                model.setQtd(query.getDouble("f.Qtd"));
                model.setPreco(query.getDouble("f.Preco"));
                model.setSubTotal(query.getDouble("f.SubTotal"));
                model.setTotal(query.getDouble("f.Total"));

//                ProdutoModel p = new ProdutoModel(query.getInt("p.Id"), query.getString("p.Designacao"));
                ProdutoController pc = new ProdutoController();
                ProdutoModel p = pc.getById(query.getInt("p.Id"));
                model.setProduto(p);

                BarbeiroItemController bc = new BarbeiroItemController();
                model.setBarbeiro(bc.getByItem(model));
                lista.add(model);

            }

        } catch (SQLException ex) {
            Logger.getLogger(FacturaItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }

        return lista;
    }

    public List<FacturaItemModel> getItemByFactura(String dataInicio, String dataFim) {

        List<FacturaItemModel> lista = new ArrayList();

        String sql = "SELECT * FROM facturaitem f \n"
                + "INNER JOIN produto p  ON p.Id = f.IdProduto\n"
                + "INNER JOIN factura f2 ON f2.Id  = f.IdFactura \n"
                + "WHERE DATE(f2.`Data`) BETWEEN '"+dataInicio+"' AND '"+dataFim+"'";
        try {

            con = conFactory.open();            
            //con = Connection.conexao;
            command = con.prepareCall(sql);
            query = command.executeQuery();

            while (query.next()) {

                FacturaItemModel model = new FacturaItemModel();
                model.setId(query.getInt("f.Id"));
                model.setDesconto(query.getDouble("f.Desconto"));
                model.setIva(query.getDouble("f.Iva"));
                model.setLote(query.getString("f.Lote"));
                model.setQtd(query.getDouble("f.Qtd"));
                model.setPreco(query.getDouble("f.Preco"));
                model.setSubTotal(query.getDouble("f.SubTotal"));
                model.setTotal(query.getDouble("f.Total"));
                
                
                FacturaModel facturaModel = new FacturaModel();
                facturaModel.setId(query.getInt("f2.Id"));
                facturaModel.setNextFactura(query.getString("f2.NextFactura"));
                facturaModel.setNomeCliente(query.getString("f2.NomeCliente"));
                facturaModel.setTroco(query.getDouble("f2.Troco"));
                facturaModel.setTotalDesconto(query.getDouble("f2.TotalDesconto"));
                facturaModel.setTotalApagar(query.getDouble("f2.TotalApagar"));
                facturaModel.setSubTotal(query.getDouble("f2.SubTotal"));
                facturaModel.setTipoFacturas(query.getString("f2.TipoFactura"));
                facturaModel.setTotalIVA(query.getDouble("f2.TotalIVA"));
                facturaModel.setHash(query.getString("f2.Hash"));
                facturaModel.setValorEntregue(query.getDouble("f2.ValorEntregue"));
                facturaModel.setValorMulticaixa(query.getDouble("f2.ValorMulticaixa"));
                facturaModel.setCriada_modulo_formacao(query.getBoolean("f2.criada_modulo_formacao"));

                FormaPagamentoModel formaPagamento = new FormaPagamentoModel();
                formaPagamento.setId(query.getInt("f2.IdFormaPagamento"));

                facturaModel.setFormaPagamento(formaPagamento);
                
                model.setFactura(facturaModel);
                

//                ProdutoModel p = new ProdutoModel(query.getInt("p.Id"), query.getString("p.Designacao"));
                ProdutoController pc = new ProdutoController();
                ProdutoModel p = pc.getById(query.getInt("p.Id"), con);
                model.setProduto(p);

                BarbeiroItemController bc = new BarbeiroItemController();
                model.setBarbeiro(bc.getByItem(model));
                lista.add(model);

            }

        } catch (SQLException ex) {
            Logger.getLogger(FacturaItemController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }
    
    
    public FacturaItemModel getItemByCaixaAndProduto(CaixaModel caixa, ProdutoModel produto) {

        String sql = "select \n" +
                    " \n" +
                    "sum(fi.Total) totalVendidoProduto, \n" +
                    "sum(fi.Qtd) qtdVendidaProduto, \n" +
                    "(select sum(f.ValorMulticaixa) from factura f where f.idCaixa=fa.idCaixa) as totMulticaixa,\n" +
                    "(select sum(f.ValorEntregue) from factura f where f.idCaixa=fa.idCaixa) as totEntregue,\n" +
                    "(select sum(f.Troco) from factura f where f.idCaixa=fa.idCaixa) as totTroco,\n" +
                    "(select sum(f.TotalDesconto) from factura f where f.idCaixa=fa.idCaixa) as totDesconto\n" +
                    "\n" +
                    "from \n" +
                    "factura fa inner join facturaitem fi on fa.Id=fi.IdFactura\n" +
                    "where fa.idCaixa = "+caixa.getId()+" AND fi.IdProduto = " +produto.getId()+" AND fa.TipoFactura <> 'Perfoma' "+
                    ";";
        try {
            con = conFactory.open();
            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {

                FacturaItemModel model = new FacturaItemModel();
                model.setQtd(query.getDouble("qtdVendidaProduto"));
                model.setTotal(query.getDouble("totalVendidoProduto"));

//                ProdutoModel p = new ProdutoModel(query.getInt("p.Id"), query.getString("p.Designacao"));
                
                model.setProduto(produto);

                BarbeiroItemController bc = new BarbeiroItemController();
                model.setBarbeiro(bc.getByItem(model));
                
                return model;

            }

        } catch (SQLException ex) {
            Logger.getLogger(FacturaItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //conFactory.close(con, command, query);
        }

        return null;
    }
    
    public List<FacturaItemModel> getItemByFactura(Integer idFactura,Connection con) {

        List<FacturaItemModel> lista = new ArrayList();

        String sql = "SELECT * FROM facturaitem f \n"
                + "INNER JOIN produto p  ON p.Id = f.IdProduto\n"
                + "WHERE f.IdFactura = "+idFactura;
        try {


            command = con.prepareCall(sql);
            query = command.executeQuery();

            while (query.next()) {

                FacturaItemModel model = new FacturaItemModel();
                model.setId(query.getInt("f.Id"));
                model.setDesconto(query.getDouble("f.Desconto"));
                model.setIva(query.getDouble("f.Iva"));
                model.setLote(query.getString("f.Lote"));
                model.setQtd(query.getDouble("f.Qtd"));
                model.setPreco(query.getDouble("f.Preco"));
                model.setSubTotal(query.getDouble("f.SubTotal"));
                model.setTotal(query.getDouble("f.Total"));
                
                
 
                

//                ProdutoModel p = new ProdutoModel(query.getInt("p.Id"), query.getString("p.Designacao"));
                ProdutoController pc = new ProdutoController();
                ProdutoModel p = pc.getById(query.getInt("p.Id"), con);
                model.setProduto(p);

                BarbeiroItemController bc = new BarbeiroItemController();
                model.setBarbeiro(bc.getByItem(model,con));
                lista.add(model);

            }

        } catch (SQLException ex) {
            Logger.getLogger(FacturaItemController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }

    public List<FacturaItemModel> getItemNaoEliminadoByIdFactura(int idFactura) {

        List<FacturaItemModel> lista = new ArrayList();

        String sql = "SELECT * FROM facturaitem f INNER JOIN produto p  ON p.Id = f.IdProduto WHERE IdFactura = " + idFactura + " and f.IdEstado <> 3";
        try {
            con = conFactory.open();            
            //con = Connection.conexao;
            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                FacturaItemModel model = new FacturaItemModel();
                model.setId(query.getInt("f.Id"));
                model.setDesconto(query.getDouble("f.Desconto"));
                model.setIva(query.getDouble("f.Iva"));
                model.setLote(query.getString("f.Lote"));
                model.setQtd(query.getDouble("f.Qtd"));
                model.setPreco(query.getDouble("f.Preco"));
                model.setSubTotal(query.getDouble("f.SubTotal"));
                model.setTotal(query.getDouble("f.Total"));

                ProdutoController pc = new ProdutoController();
                ProdutoModel p = new ProdutoModel(query.getInt("p.Id"), query.getString("p.Designacao"));
                p = pc.getById(query.getInt("p.Id"));
                model.setProduto(p);

                BarbeiroItemController bc = new BarbeiroItemController();
                model.setBarbeiro(bc.getByItem(model));
                lista.add(model);

            }

        } catch (SQLException ex) {
            Logger.getLogger(FacturaItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }

        return lista;
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
            Logger.getLogger(FacturaItemController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0.0;
    }

    public List<FacturaItemModel> getItemByIdFactura(int idFactura, String cliente) {

        List<FacturaItemModel> lista = new ArrayList();

        String sql = "SELECT * FROM facturaitem f"
                + " INNER JOIN produto p  ON p.Id = f.IdProduto"
                + " INNER JOIN factura factura ON factura.Id = f.IdFactura"
                + " WHERE f.IdEstado = 1 AND NomeCliente = '" + cliente + "' AND  IdFactura = " + idFactura;
        try {
            con = conFactory.open();            
//            con = Connection.conexao;
            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                FacturaItemModel model = new FacturaItemModel();
                model.setId(query.getInt("f.Id"));
                model.setDesconto(query.getDouble("f.Desconto"));
                model.setIva(query.getDouble("f.Iva"));
                model.setLote(query.getString("f.Lote"));
                model.setQtd(query.getDouble("f.Qtd"));
                model.setPreco(query.getDouble("f.Preco"));
                model.setSubTotal(query.getDouble("f.SubTotal"));
                model.setTotal(query.getDouble("f.Total"));

                ProdutoModel p = new ProdutoModel(query.getInt("p.Id"), query.getString("p.Designacao"));
                model.setProduto(p);

                lista.add(model);

            }

        } catch (SQLException ex) {
            Logger.getLogger(FacturaItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }

        return lista;
    }

}
