/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model. EncomendaItemModel;
import Model.ProdutoModel;
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
public class EncomendaItemController implements IController< EncomendaItemModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save( EncomendaItemModel obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update( EncomendaItemModel obj) {
        
         

        boolean result = false;
        try {
            String sql = " UPDATE  ` encomendaitem`"
                       + " SET IdEstado = "+obj.getEstado().getId()+""
                       + " WHERE IdEncomenda = "+obj.getFactura().getId()+""
                       + " AND IdProduto = "+obj.getProduto().getId()+""
                      + "  AND Lote = '"+obj.getLote()+"'\n";
                    

            con = conFactory.open();
            command = con.prepareCall(sql);

            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EncomendaItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }

        return result;
    }

    @Override
    public boolean saveOrUpdate( EncomendaItemModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO grest.encomendaitem\n"
                    + "(`Desconto`,\n"
                    + "`IdEncomenda`,\n"
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

            command.setDouble(1, obj.getDesconto());
            command.setInt(2, obj.getFactura().getId());
            command.setInt(3, obj.getProduto().getId());
            command.setDouble(4, obj.getPreco());
            command.setDouble(5, obj.getQtd());
            command.setDouble(6, obj.getSubTotal());
            command.setDouble(7, obj.getIva());
            command.setString(8, obj.getLote());
            command.setDouble(9, obj.getTotal());

            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EncomendaItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }

        return result;
    }

    @Override
    public  EncomendaItemModel getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List< EncomendaItemModel> get() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List< EncomendaItemModel> getItemByIdEncomenda(int idFactura) {

        List< EncomendaItemModel> lista = new ArrayList();

        String sql = "SELECT * FROM  encomendaitem f INNER JOIN produto p  ON p.Id = f.IdProduto WHERE IdEncomenda = " + idFactura;
        try {
            con = conFactory.open();
            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                 EncomendaItemModel model = new  EncomendaItemModel();
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
            Logger.getLogger(EncomendaItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }

        return lista;
    }
    public List< EncomendaItemModel> getItemByIdEncomenda(int idFactura,String cliente) {

        List< EncomendaItemModel> lista = new ArrayList();

        String sql = "SELECT * FROM  encomendaitem f"
                + " INNER JOIN produto p  ON p.Id = f.IdProduto"
                + " INNER JOIN factura factura ON factura.Id = f.IdEncomenda"
                + " WHERE f.IdEstado = 1 AND NomeCliente = '"+cliente+"' AND  IdEncomenda = " + idFactura;
        try {
            con = conFactory.open();
            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                 EncomendaItemModel model = new  EncomendaItemModel();
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
            Logger.getLogger(EncomendaItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }

        return lista;
    }

}
