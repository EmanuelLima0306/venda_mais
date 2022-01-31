/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ClienteModel;
import Model.EstadoModel;
import Model.FacturaModel;
import Model.FormaPagamentoModel;
import Model.LiquidarDividaModel;
import Model.UsuarioModel;
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
 * @author emanu
 */
public class LiquidarDividaController implements IController<LiquidarDividaModel>{

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(LiquidarDividaModel obj) {
       boolean result = false;
        try {
            String sql = "INSERT INTO `liquidardivida`\n"
                    + "(\n"
                    + "`Obs`,\n"
                    + "`ValorEntregue`,\n"
                    + "`IdEstado`,\n"
                    + "`IdFactura`,\n"
                    + "`IdUsuario`,\n"
                    + "`IdCliente`,\n"
                    + "`DataEmissao`,\n"
                    + "`DataPagamento`,\n"
                    + "`RetensaoNaFonte`)\n"
                    + "VALUES\n"
                    + "(?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?);";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getObs());
            command.setDouble(2, obj.getValor());
            command.setInt(3, obj.getEstado().getId());
            command.setInt(4, obj.getFactura().getId());
            command.setInt(5, obj.getUsuario().getId());
            command.setInt(6, obj.getCliente().getId());
         

            command.setString(7, obj.getDataEmissao());
            command.setString(8, obj.getDataPagamento());
            command.setDouble(9, obj.getRetensaoNaFonte());
            

            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(MovimentoController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean update(LiquidarDividaModel obj) {
    
        boolean result = false;
        try {
            String sql = "   UPDATE liquidardivida"
                    + "   SET"
                    + "   IdEstado = ?"
                    + "   WHERE Id = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, obj.getEstado().getId());
//            command.setInt(2, obj.getEstado().getId());
            command.setInt(2, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(MovimentoController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(LiquidarDividaModel obj) {
    
        if (obj.getId() > 0) {
            return update(obj);
        }
        return save(obj);
    }

    @Override
    public LiquidarDividaModel getById(int id) {
    
        LiquidarDividaModel modelo = null;

        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM liquidardivida m"
                    + " INNER JOIN cliente c ON c.Id = m.IdCliente "
                    + " INNER JOIN factura f ON f.Id = m.IdFactura"
                    + " WHERE m.IdEstado = 9 and m.Id = "+id;

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                modelo = new LiquidarDividaModel();
                modelo.setId(query.getInt("m.Id"));
                
                EstadoModel estado = new EstadoModel(query.getInt("m.IdEstado"), "");
                modelo.setEstado(estado);
                 
                
                ClienteModel cliente = new ClienteModel();
                cliente.setContacto(query.getString("c.Contacto"));
                cliente.setEmail(query.getString("c.Email"));
                cliente.setEndereco(query.getString("c.Morada"));
                cliente.setId(query.getInt("c.Id"));
                cliente.setLimiteCredito(query.getDouble("c.LimiteCredito"));
                cliente.setValorCarteira(query.getDouble("c.ValorCarteira"));
//                cliente.setTipoCliente(new TipoClienteModel(query.getInt("t.Id"), query.getString("t.Designacao")));
                cliente.setNome(query.getString("c.Nome"));

                modelo.setCliente(cliente);

                FacturaModel factura = new FacturaModel();
                factura.setNomeCliente(query.getString("NomeCliente"));
                factura.setTroco(query.getDouble("Troco"));
                factura.setTotalDesconto(query.getDouble("TotalDesconto"));
                factura.setTotalApagar(query.getDouble("TotalApagar"));
                factura.setSubTotal(query.getDouble("SubTotal"));
                factura.setTotalIVA(query.getDouble("TotalIVA"));
                factura.setHash(query.getString("Hash"));
                factura.setValorEntregue(query.getDouble("ValorEntregue"));
                factura.setValorMulticaixa(query.getDouble("ValorMulticaixa"));

                FormaPagamentoModel formaPagamento = new FormaPagamentoModel();
                formaPagamento.setId(query.getInt("IdFormaPagamento"));

                factura.setId(query.getInt("Id"));

                modelo.setFactura(factura);

            }

        } catch (Exception ex) {
            Logger.getLogger(MovimentoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return modelo;
    }

    @Override
    public List<LiquidarDividaModel> get() {
    
        List<LiquidarDividaModel> modelos = new ArrayList<>();

        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM liquidardivida m"
                    + " INNER JOIN cliente c ON c.Id = m.IdCliente "
                    + " INNER JOIN factura f ON f.Id = m.IdFactura"
                    + " WHERE m.IdEstado = 9";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                LiquidarDividaModel modelo = new LiquidarDividaModel();
                modelo.setId(query.getInt("m.Id"));
                
                EstadoModel estado = new EstadoModel(query.getInt("m.IdEstado"), "");
                modelo.setEstado(estado);
                 
                
                ClienteModel cliente = new ClienteModel();
                cliente.setContacto(query.getString("c.Contacto"));
                cliente.setEmail(query.getString("c.Email"));
                cliente.setEndereco(query.getString("c.Morada"));
                cliente.setId(query.getInt("c.Id"));
                cliente.setLimiteCredito(query.getDouble("c.LimiteCredito"));
                cliente.setValorCarteira(query.getDouble("c.ValorCarteira"));
//                cliente.setTipoCliente(new TipoClienteModel(query.getInt("t.Id"), query.getString("t.Designacao")));
                cliente.setNome(query.getString("c.Nome"));

                modelo.setCliente(cliente);

                FacturaModel factura = new FacturaModel();
                factura.setNomeCliente(query.getString("NomeCliente"));
                factura.setTroco(query.getDouble("Troco"));
                factura.setTotalDesconto(query.getDouble("TotalDesconto"));
                factura.setTotalApagar(query.getDouble("TotalApagar"));
                factura.setSubTotal(query.getDouble("SubTotal"));
                factura.setTotalIVA(query.getDouble("TotalIVA"));
                factura.setHash(query.getString("Hash"));
                factura.setValorEntregue(query.getDouble("ValorEntregue"));
                factura.setValorMulticaixa(query.getDouble("ValorMulticaixa"));

                FormaPagamentoModel formaPagamento = new FormaPagamentoModel();
                formaPagamento.setId(query.getInt("IdFormaPagamento"));

                factura.setId(query.getInt("Id"));

                modelo.setFactura(factura);
                modelos.add(modelo);

            }

        } catch (Exception ex) {
            Logger.getLogger(MovimentoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return modelos;
    }
    
    public int getLastIdByUsuario(UsuarioModel usuario) {

        int id = 0;
        try {
            String sql = "SELECT Id From liquidardivida WHERE IdUsuario = ? Order By Id DESC LIMIT 1";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, usuario.getId());
            query = command.executeQuery();

            if (query.next()) {
                id = query.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return id;
    }
    
    public int getLast() {

        int id = 0;
        try {
            String sql = "SELECT Id From liquidardivida  Order By Id DESC LIMIT 1";
            con = conFactory.open();
            command = con.prepareCall(sql);
           
            query = command.executeQuery();

            if (query.next()) {
                id = query.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return id;
    }
    
    public boolean updateStatus(LiquidarDividaModel model) {

        String sql = "update liquidardivida set IdEstado = " + model.getEstado().getId() + " where IdFactura = " + model.getFactura().getId();
        con = conFactory.open();
        boolean flag = false;
        try {
            command = con.prepareCall(sql);
            flag = command.executeUpdate() > 0;
        } catch (Exception ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;

    }
    
    public double getTotalPagoByIdFactura(FacturaModel facturaModel) {
    
        double totalEntregue = 0.0;

        try {

            con = conFactory.open();
            System.out.println(facturaModel.getId());
            String sql = " SELECT SUM(ValorEntregue) "
                    + " FROM liquidardivida "
                    + " WHERE IdFactura = "+facturaModel.getId();

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {
                
                totalEntregue = query.getDouble(1);
            }

        } catch (Exception ex) {
            Logger.getLogger(MovimentoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return totalEntregue;
    }
}
