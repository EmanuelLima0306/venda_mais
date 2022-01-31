/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.CaixaModel;
import Model.ClienteModel;
import Model.EstadoModel;
import Model.FacturaItemModel;
import Model.FacturaModel;
import Model.FormaPagamentoModel;
import Model.Moeda;
import Model.Motivo;
import Model.Movimento;
import Model.Taxa;
import Model.UsuarioModel;
import Util.Calculo;
import Util.ConnectionFactor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import iva.CustomerIten;
import iva.Invoice;
import iva.Invoice.DocumentStatus;
import iva.Invoices;
import iva.LineItens;
import iva.ProdutItem;
import iva.TaxTableItem;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *
 * @author celso
 */
public class FacturaController implements IController<FacturaModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(FacturaModel obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(FacturaModel obj) {

        boolean result = false;
        try {
            String sql = "UPDATE `factura`\n"
                    + "SET `IdFormaPagamento` = ? , Troco = ? ,`ValorEntregue` = ?,\n"
                    + "`ValorMulticaixa` = ? WHERE Id = ?\n";
            con = conFactory.open();
            command = con.prepareCall(sql);

            command.setInt(1, obj.getFormaPagamento().getId());
            command.setDouble(2, obj.getTroco());
            command.setDouble(3, obj.getValorEntregue());
            command.setDouble(4, obj.getValorMulticaixa());

            command.setInt(5, obj.getId());

            result = command.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return result;
    }

    public boolean updateEstado(FacturaModel obj) {

        boolean result = false;
        try {
            String sql = "UPDATE `factura`\n"
                    + "SET `IdEstado` = ?  WHERE Id = ?\n";
            con = conFactory.open();
            
            con.setAutoCommit(false);
            command = con.prepareCall(sql);

            command.setInt(1, obj.getEstado().getId());

            command.setInt(2, obj.getId());

            result = command.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return result;
    }

    public boolean updateNextFactura(String next, int codigo) {

        String sql = "update factura set NextFactura = '" + next + "' where Id = " + codigo;
        con = conFactory.open();
        boolean flag = false;
        try {
            command = con.prepareCall(sql);
            flag = command.executeUpdate() > 0;
        } catch (Exception ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conFactory.close(con, command);
        }
        return flag;

    }

    public boolean updateHashReference(String hash, String reference, int codigo) {

        String sql = "update factura set Hash = '" + hash + "',FacturaRefence = '" + reference + "' where Id = " + codigo;
        con = conFactory.open();
        boolean flag = false;
        try {
            command = con.prepareCall(sql);
            flag = command.executeUpdate() > 0;
        } catch (Exception ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return flag;

    }

    public boolean updateValor(FacturaModel obj) {

        boolean result = false;
        try {
            String sql = " UPDATE `factura`\n"
                    + " SET `TotalApagar` = ? , Troco = ? ,`TotalDesconto` = ?"
                    + " WHERE Id = ?\n";
            con = conFactory.open();
            command = con.prepareCall(sql);

//            command.setInt(1, obj.getFormaPagamento().getId());
            command.setDouble(1, obj.getTotalApagar());
            command.setDouble(2, obj.getTroco());
            command.setDouble(3, obj.getTotalDesconto());
            command.setInt(4, obj.getId());

            result = command.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return result;
    }

    public boolean eliminarFactura(FacturaModel obj) {

        boolean result = false;
        try {
            String sql = "UPDATE `factura`\n"
                    + "SET `IdEstado` = ? , Obs = ?  WHERE Id = ?\n";
            con = conFactory.open();
            command = con.prepareCall(sql);

            command.setInt(1, 3);
            command.setString(2, obj.getObs());

            command.setInt(3, obj.getId());

            result = command.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(FacturaModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `factura`\n"
                    + "(\n"
                    + "`IdCliente`,\n"
                    + "`IdUsuario`,\n"
                    + "`IdFormaPagamento`,\n"
                    + "`IdEstado`,\n"
                    + "`Data`,\n"
                    + "`ValorEntregue`,\n"
                    + "`ValorMulticaixa`,\n"
                    + "`Troco`,\n"
                    + "`TotalApagar`,TotalDesconto,NomeCliente,"
                    + "TipoFactura,TotalIVA,SubTotal,"
                    + "IdMoeda,IdSerie,cambio,"
                    + "valor_moeda_estrangeira,"
                    + "criada_modulo_formacao,idCaixa,localDescarga,localCarga,dataTransporte,Obs,TotalRetencao)\n"
                    + "VALUES\n"
                    + "("
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            con = conFactory.open();            
//            con = Connection.conexao;
            command = con.prepareCall(sql);
            //command = conFactory.getConection().prepareCall(sql);

            command.setInt(1, obj.getCliente().getId());
            command.setInt(2, obj.getUsuario().getId());
            command.setInt(3, obj.getFormaPagamento().getId());
            command.setInt(4, obj.getEstado().getId());
            command.setString(5, obj.getData());
            command.setDouble(6, obj.getValorEntregue());
            command.setDouble(7, obj.getValorMulticaixa());
            command.setDouble(8, obj.getTroco());
            command.setDouble(9, obj.getTotalApagar());
            command.setDouble(10, obj.getTotalDesconto());
            command.setString(11, obj.getNomeCliente());
            command.setString(12, obj.getTipoFacturas());
            command.setDouble(13, obj.getTotalIVA());
            command.setDouble(14, obj.getSubTotal());
            command.setInt(15, obj.getMoeda().getId());
            command.setInt(16, obj.getIdSerie());
            command.setDouble(17, obj.getCambio());
            command.setDouble(18, obj.getValorMoedaEstrangeiro());
            command.setBoolean(19, obj.getCriada_modulo_formacao());
            command.setInt(20, obj.getCaixaModel().getId());
            command.setString(21, obj.getLocalDescarga());
            command.setString(22, obj.getLocalCarga());
            command.setString(23, obj.getDataTransporte());
            command.setString(24, obj.getObs());
            command.setDouble(25, obj.getTotalRetencao());

            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return result;
    }

    @Override
    public FacturaModel getById(int id) {

        FacturaModel modelo = new FacturaModel();
        String sql = "SELECT * FROM factura f  WHERE Id = " + id;
        con = conFactory.open();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

                modelo.setNomeCliente(query.getString("NomeCliente"));
                modelo.setTroco(query.getDouble("Troco"));
                modelo.setTotalDesconto(query.getDouble("TotalDesconto"));
                modelo.setTotalApagar(query.getDouble("TotalApagar"));
                modelo.setSubTotal(query.getDouble("SubTotal"));
                modelo.setTotalIVA(query.getDouble("TotalIVA"));
                modelo.setHash(query.getString("Hash"));
                modelo.setValorEntregue(query.getDouble("ValorEntregue"));
                modelo.setValorMulticaixa(query.getDouble("ValorMulticaixa"));
                modelo.setTipoFacturas(query.getString("TipoFactura"));
                modelo.setData(query.getString("Data"));
                FormaPagamentoModel formaPagamento = new FormaPagamentoModel();
                formaPagamento.setId(query.getInt("IdFormaPagamento"));
                
                modelo.setFormaPagamento(formaPagamento);
                modelo.setId(query.getInt("Id"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return modelo;
    }

    public FacturaModel getById(int id, Connection con) {

        FacturaModel modelo = new FacturaModel();
        String sql = "SELECT * FROM factura f  WHERE Id = " + id;
//        con = conFactory.open();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

                modelo.setNomeCliente(query.getString("NomeCliente"));
                modelo.setTroco(query.getDouble("Troco"));
                modelo.setTotalDesconto(query.getDouble("TotalDesconto"));
                modelo.setTotalApagar(query.getDouble("TotalApagar"));
                modelo.setSubTotal(query.getDouble("SubTotal"));
                modelo.setTotalIVA(query.getDouble("TotalIVA"));
                modelo.setHash(query.getString("Hash"));
                modelo.setValorEntregue(query.getDouble("ValorEntregue"));
                modelo.setValorMulticaixa(query.getDouble("ValorMulticaixa"));

                FormaPagamentoModel formaPagamento = new FormaPagamentoModel();
                formaPagamento.setId(query.getInt("IdFormaPagamento"));

                modelo.setFormaPagamento(formaPagamento);
                modelo.setId(query.getInt("Id"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
//            ////conFactory.close(con, command, query);
        }
        return modelo;
    }

    @Override
    public List<FacturaModel> get() {
        List<FacturaModel> lista = new ArrayList();
        String sql = "SELECT * FROM factura ";
        
        con = conFactory.open();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            
            FacturaItemController facturaItemController = new FacturaItemController();
            
            while (query.next()) {

                FacturaModel modelo = new FacturaModel();
                modelo.setId(query.getInt("Id"));
                modelo.setCliente(new ClienteModel(query.getInt("IdCliente"), ""));
                modelo.setUsuario(new UsuarioModel(query.getInt("IdUsuario"), ""));
                FormaPagamentoModel formaPagamento = new FormaPagamentoModel();
                formaPagamento.setId(query.getInt("IdFormaPagamento"));
                modelo.setEstado(new EstadoModel(query.getInt("IdEstado"), ""));
                modelo.setData(query.getString("Data"));
                modelo.setValorEntregue(query.getDouble("ValorEntregue"));
                modelo.setValorMulticaixa(query.getDouble("ValorMulticaixa"));
                modelo.setTroco(query.getDouble("Troco"));
                modelo.setTotalApagar(query.getDouble("TotalApagar"));
                modelo.setTotalDesconto(query.getDouble("TotalDesconto"));
                modelo.setNomeCliente(query.getString("NomeCliente"));
                modelo.setTipoFacturas(query.getString("TipoFactura"));
                modelo.setIdSerie(query.getInt("IdSerie"));
                modelo.setMoeda(new Moeda(query.getInt("IdMoeda"), ""));
                modelo.setFacturaReference(query.getString("FacturaRefence"));
                modelo.setHash(query.getString("Hash"));
                modelo.setSubTotal(query.getDouble("SubTotal"));
                modelo.setTotalIVA(query.getDouble("TotalIVA"));
                modelo.setNextFactura(query.getString("NextFactura"));
                modelo.setCambio(query.getDouble("cambio"));
                modelo.setValorMoedaEstrangeiro(query.getDouble("valor_moeda_estrangeira"));
                modelo.setCriada_modulo_formacao(query.getBoolean("criada_modulo_formacao"));
                modelo.setCaixaModel(new CaixaModel(query.getInt("idCaixa"), null, "", ""));
                modelo.setTotalRetencao(query.getDouble("TotalRetencao"));
                modelo.setFormaPagamento(formaPagamento);
                modelo.setFacturaItemModels(facturaItemController.getItemByFactura(query.getInt("Id"), con));
                
                lista.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public List<FacturaModel> getFacturaDividaCliente(int codCliente) {

        List<FacturaModel> lista = new ArrayList();
        String sql = "SELECT * FROM factura f where f.IdCliente = " + codCliente + "";
        con = conFactory.open();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

                FacturaModel modelo = new FacturaModel();
                modelo.setId(query.getInt("Id"));
                modelo.setNextFactura(query.getString("NextFactura"));
                modelo.setTotalApagar(query.getDouble("TotalApagar"));

                lista.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public List<FacturaModel> getFacturaNaoEliminada(int codCliente, String nextFactura) {

        List<FacturaModel> lista = new ArrayList();
        String sql = "SELECT * FROM factura f where f.IdCliente = " + codCliente +" and TipoFactura NOT IN('Perfoma')  and f.NextFactura LIKE '" +nextFactura+ "%' and f.IdEstado <> 3 and f.Id NOT IN( SELECT m.IdFactura FROM movimento m) ORDER BY f.NextFactura";
//        String sql = "SELECT * FROM factura f where f.IdCliente = " + codCliente + " and f.IdEstado Not In(3,8) and TipoFactura Not In('Venda Credito')";
        con = conFactory.open();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

                FacturaModel modelo = new FacturaModel();
                modelo.setId(query.getInt("Id"));
                modelo.setNextFactura(query.getString("NextFactura"));
                modelo.setTotalApagar(query.getDouble("TotalApagar"));
                modelo.setCliente(new ClienteModel(query.getInt("f.IdCliente"), ""));

                lista.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }
    public List<FacturaModel> getFacturaDebitadaNaoLiquidada(int codCliente, String nextFactura) {

        List<FacturaModel> lista = new ArrayList();
        String sql = "SELECT distinct f.Id, f.NextFactura, f.TotalApagar FROM factura f  where f.IdCliente = " + codCliente +" and f.NextFactura LIKE '" +nextFactura+ "%' and  f.TipoFactura = 'Venda Credito' ORDER BY f.NextFactura";
//        String sql = "SELECT * FROM factura f where f.IdCliente = " + codCliente + " and f.IdEstado Not In(3,8) and TipoFactura Not In('Venda Credito')";
        con = conFactory.open();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

                FacturaModel modelo = new FacturaModel();
                modelo.setId(query.getInt("f.Id"));
                modelo.setNextFactura(query.getString("f.NextFactura"));
                modelo.setTotalApagar(query.getDouble("f.TotalApagar"));
                
                LiquidarDividaController liquidarDividaController = new LiquidarDividaController();
                if(liquidarDividaController.getTotalPagoByIdFactura(modelo) != modelo.getTotalApagar())
                    lista.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public List<FacturaModel> getFacturaNaoEliminadaDebito(int codCliente) {

        List<FacturaModel> lista = new ArrayList();
        String sql = "SELECT * FROM factura f where f.IdCliente = " + codCliente + " and f.IdEstado Not In(3,8) and TipoFactura='Venda Credito'";
        con = conFactory.open();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

                FacturaModel modelo = new FacturaModel();
                modelo.setId(query.getInt("Id"));
                modelo.setNextFactura(query.getString("NextFactura"));
                modelo.setTotalApagar(query.getDouble("TotalApagar"));

                lista.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public boolean isFacturaEmitidaByCliente(int codCliente) {

        List<FacturaModel> lista = new ArrayList();
        String sql = "SELECT Count(Id) as FacturaCode FROM factura f where f.IdCliente = " + codCliente + "";
        con = conFactory.open();
        boolean flag = false;
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {

                flag = query.getInt("FacturaCode") > 0;

            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return flag;
    }

    public boolean isFacturaEmitidaByProduto(int idProduto) {

        List<FacturaModel> lista = new ArrayList();
        String sql = "SELECT Count(f.Id) as FacturaCode FROM factura f INNER JOIN facturaitem i ON f.Id = i.IdFactura where i.IdProduto = " + idProduto + "";
        con = conFactory.open();
        boolean flag = false;
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {

                flag = query.getInt("FacturaCode") > 0;

            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return flag;
    }

    public List<FacturaModel> getFacturaDividaCliente1(int codCliente) {

        List<FacturaModel> lista = new ArrayList();
        String sql = "SELECT * FROM factura f where f.IdCliente = " + codCliente + " AND TipoFactura='Venda Credito'";
        con = conFactory.open();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

                System.out.println("aaaa>>>>" + query.getString("NextFactura"));
                FacturaModel modelo = new FacturaModel();
                modelo.setId(query.getInt("Id"));
                modelo.setNextFactura(query.getString("NextFactura"));
                modelo.setTotalApagar(query.getDouble("TotalApagar"));

                lista.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public String getDataLastFactura() {

        String sql = "SELECT data FROM factura f ORDER BY Id desc limit 1";
        String data = null;
      
        try {
              con = conFactory.open();
            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {

                data = query.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return data;
    }

    public List<FacturaModel> getCliente() {

        List<FacturaModel> lista = new ArrayList();
        String sql = "SELECT NomeCliente FROM factura f ";
        con = conFactory.open();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

                FacturaModel modelo = new FacturaModel();
                modelo.setNomeCliente(query.getString("NomeCliente"));
                lista.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public List<FacturaModel> getFacturaData(String dataInicio, String dataFim) {

        List<FacturaModel> lista = new ArrayList();
        String sql = "SELECT * FROM factura f where date(Data) BETWEEN '" + dataInicio + "' and '" + dataFim + "' AND IdEstado not in (3)";
        
        con = conFactory.open();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            
            FacturaItemController facturaItemController = new FacturaItemController();
            
            while (query.next()) {

                FacturaModel modelo = new FacturaModel();
                modelo.setId(query.getInt("Id"));
                modelo.setNextFactura(query.getString("NextFactura"));
                modelo.setNomeCliente(query.getString("NomeCliente"));
                modelo.setTroco(query.getDouble("Troco"));
                modelo.setTotalDesconto(query.getDouble("TotalDesconto"));
                modelo.setTotalApagar(query.getDouble("TotalApagar"));
                modelo.setSubTotal(query.getDouble("SubTotal"));
                modelo.setTipoFacturas(query.getString("TipoFactura"));
                modelo.setTotalIVA(query.getDouble("TotalIVA"));
                modelo.setHash(query.getString("Hash"));
                modelo.setValorEntregue(query.getDouble("ValorEntregue"));
                modelo.setValorMulticaixa(query.getDouble("ValorMulticaixa"));
                modelo.setCriada_modulo_formacao(query.getBoolean("criada_modulo_formacao"));

                FormaPagamentoModel formaPagamento = new FormaPagamentoModel();
                formaPagamento.setId(query.getInt("IdFormaPagamento"));

                modelo.setFormaPagamento(formaPagamento);
                
                modelo.setFacturaItemModels(facturaItemController.getItemByFactura(query.getInt("Id"), con));
                
                lista.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }
    public List<FacturaModel> getFacturaDataClienteTipo(String dataInicio, String dataFim, ClienteModel cliente, boolean recibo, boolean performa, boolean retificada, boolean anulada) {

        List<FacturaModel> lista = new ArrayList();
        String sql = "SELECT DISTINCT * FROM factura f where date(Data) BETWEEN '" + dataInicio + "' and '" + dataFim + "' ";
        String sql2 = "";
        
        if(recibo)
            sql2 = " and f.TipoFactura = 'Venda' and f.Id NOT IN (SELECT m.IdFactura From movimento m)";
        if(performa)
            sql2 = " and f.TipoFactura = 'Perfoma' and f.Id NOT IN (SELECT m.IdFactura From movimento m)";
        if(retificada)
            sql2 = " and f.Id IN (SELECT m.IdFactura From facturaitem m where m.IdEstado = 3) ";
        if(anulada)
            sql2 = " and f.IdEstado = 3 ";
        
        if((cliente != null))
            sql = sql+sql2+" and f.IdCliente = "+cliente.getId();
        else
            sql = sql+sql2;
        
        System.out.println("sql >>>> "+sql);
        
        con = conFactory.open();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

                FacturaModel modelo = new FacturaModel();
                modelo.setId(query.getInt("Id"));
                modelo.setNextFactura(query.getString("NextFactura"));
                modelo.setNomeCliente(query.getString("NomeCliente"));
                modelo.setTroco(query.getDouble("Troco"));
                modelo.setTotalDesconto(query.getDouble("TotalDesconto"));
                modelo.setTotalApagar(query.getDouble("TotalApagar"));
                modelo.setSubTotal(query.getDouble("SubTotal"));
                modelo.setTotalIVA(query.getDouble("TotalIVA"));
                modelo.setHash(query.getString("Hash"));
                modelo.setValorEntregue(query.getDouble("ValorEntregue"));
                modelo.setValorMulticaixa(query.getDouble("ValorMulticaixa"));

                FormaPagamentoModel formaPagamento = new FormaPagamentoModel();
                formaPagamento.setId(query.getInt("IdFormaPagamento"));

                modelo.setFormaPagamento(formaPagamento);
                
                lista.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public boolean verificarTaxTableEntryExiste(List<TaxTableItem> list, String type) {
        for (TaxTableItem value : list) {
            if (value.getTaxTableEntry().getTaxType() == type) {
                return true;
            }
        }

        return false;
    }

    public double getTotalDebito(String data, String data1) {

        String sql = "select sum(f.SubTotal) as total "
                + " from factura f INNER JOIN facturaitem ft ON ft.IdFactura = f.Id "
                + " where date(Data) between '" + data + "' and '" + data1 + "' and f.TipoFactura = 'Venda Credito'";

        con = conFactory.open();
        double valor = 0;
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {
                valor = query.getDouble("total");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ////conFactory.close(con, command, query);
        }
        return valor;
    }

    public String getSerie() {
        String codigo = "";
        String sql = "SELECT Designacao FROM serie where Status = 1";
        con = conFactory.open();

        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {
                codigo = query.getString("Designacao");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return codigo;
        } finally {
            ////conFactory.close(con, command, query);
        }
        return codigo;
    }
    public Integer getIdSerie() {
        Integer codigo = 0;
        String sql = "SELECT Codigo FROM serie where Status = 1";
        con = conFactory.open();

        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {
                codigo = query.getInt("Codigo");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return codigo;
        } finally {
            ////conFactory.close(con, command, query);
        }
        return codigo;
    }

    public int getNsequencial() {

        String sql = "SELECT Ano FROM serie where Status = 2";
        con = conFactory.open();
        int ano = 0;
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {
                ano = query.getInt("Ano");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ////conFactory.close(con, command, query);
        }
        return ano;
    }

    public String getValorCasaDecimal(String str, String expressao) {
        String output = "";
        if (str.charAt(0) == ',') {
            output += "0";
        }

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '.') {
                output += str.charAt(i);
            }
        }

        System.out.println("---------\n str : " + str);

        System.out.println("output : " + output);
        return output;
    }

    public List<LineItens> getItemFatura(int codigoFatura, String dataFactura) {

        String sql = "SELECT f.*, p.*,t.* FROM facturaitem f"
                + " inner join produto p on p.Id = f.IdProduto "
                + " inner join taxa t ON t.Id = p.IdTaxa"
                + "inner join factura ft on ft.Id = f.IdFactura"
                + "  where f.IdFactura=" + codigoFatura;

        List<LineItens> lista = new ArrayList<>();

        con = conFactory.open();
        DecimalFormat df = new DecimalFormat("#,###.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        try {

            command = con.prepareCall(sql);

            query = command.executeQuery();

            Integer cont = 0;
            while (query.next()) {
                cont++;
                LineItens.LineItem item = new LineItens().getLine();
                item.setLineNumber(cont.toString());
                item.setProductCode(query.getString("p.Id"));

                item.setProductDescription(query.getString("p.Designacao"));
                item.setQuantity(query.getString("f.Qtd"));
                item.setUnitOfMeasure("UND");
                item.setUnitPrice(getValorCasaDecimal(Calculo.converterCash(query.getDouble("f.Preco")), ".").replace(',', '.'));
                item.setTaxPointDate(dataFactura);
                String descricao = query.getString("p.Descricao");
                if (!Objects.equals(descricao, null) && !Objects.equals(descricao, "")) {
                    item.setDescription(descricao);
                }
                String tipoFactura = query.getString("ft.TipoFactura");

                if (!tipoFactura.trim().equals("Venda Credito")) {
                    item.setDebitAmount("0.00");
                    item.setCreditAmount(getValorCasaDecimal(Calculo.converterCash(query.getDouble("f.Preco") * query.getDouble("f.Qtd")), ".").replace(',', '.'));
                } else {
                    item.setDebitAmount(getValorCasaDecimal(Calculo.converterCash(query.getDouble("f.Preco") * query.getDouble("f.Qtd")), ".").replace(',', '.'));
                    item.setCreditAmount("0.00");
                }
                TaxaController taxaController = new TaxaController();

                Taxa modelo1 = new Taxa();
                modelo1.setId(query.getInt("p.IdTaxa"));
                modelo1.setTaxa(query.getDouble("t.Taxa"));
                if (modelo1.getTaxa() != 0) {

                    if (modelo1.getTaxa() == 14) {
                        item.getTax().setTaxType("IVA");
                        item.getTax().setTaxCode("NOR");

                    }

                    item.getTax().setTaxPercentage("" + modelo1.getTaxa());

                } else {

                    // int codigoMotivo = modelo1.getMotivo().getCodigo();
                    MotivoController motivoController = new MotivoController();
                    Motivo modelo = motivoController.getById(query.getInt("p.IdMotivo"));

                    item.getTax().setTaxType("NS");
                    item.getTax().setTaxCode("ISE");
                    item.setTaxExemptionReason(modelo.getDescricao());
                    item.setTaxExemptionCode(modelo.getCodigo());
//                    item.getTax().setTaxCode(modelo.getCodigoMotivo());
                    item.getTax().setTaxPercentage("0");
                }

                item.setSettlementAmount("0");
                LineItens linha = new LineItens();
                linha.setLine(item);
                lista.add(linha);

            }
        } catch (SQLException ex) {

            ex.printStackTrace();
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

//    public List<Invoices> getFacturaGerais(String data, String data1) {
//
//        String sql = "select * from factura f where date(Data) between '" + data + "' and '" + data1 + "'";
//        // CRIAR UMA LISTA QUE ARMAZENA AS FATURAS
//        List<Invoices> invoiceses = new ArrayList<>();
//        con = conFactory.open();
//        try {
//            command = con.prepareCall(sql);
//
//            query = command.executeQuery();
//
//            String dataFactura2 = "";
//            while (query.next()) {
//
//                String dataFactura = query.getString("Data");
//                String idCliente = query.getString("IdCliente");
//                String tipoFactura = query.getString("TipoFactura");
//                int id = query.getInt("Id");
////                String dataFactura = query.getString("Data").substring(0, 10);
//                Integer codigoFatura = query.getInt("Id");
//
//                Invoice element = new Invoice();
//                element.setInvoiceNo(codigoFatura.toString() + " " + getSerie() + "/" + getNsequencial());
//                element.setInvoiceDate(dataFactura);
//
////                element.setHash("" + query.getString("f.Hash"));
//               
//                String datav = dataFactura.substring(0, 19);
//                String[] vet = datav.split(" ");
//                element.setSystemEntryDate(vet[0] + "T" + vet[1]);
//
//                element.setCustomerID(idCliente);
//
//                Date hora = new Date();
//                SimpleDateFormat hora_formato = new SimpleDateFormat("dd/MM/yyyy");
//
//                String vet2[] = hora_formato.format(hora).split("/");
//
//                int mes = Integer.parseInt(vet2[1]);
//                element.setPeriod(mes < 9 ? vet2[1].substring(1) : vet2[1]);
//
//                if (!tipoFactura.trim().equalsIgnoreCase("Venda")) {
//                    element.setInvoiceType("FT");
//                    element.setInvoiceType("A");
//                } else {
//                    element.setInvoiceType("FR");
//                    element.setInvoiceType("N");
//                }
//
//                element.setLineItens(getItemFatura(codigoFatura, dataFactura));
//                //  CRIAR UMA LISTA DE FATURA COM TODOS OS SEUS ITENS
//
//                FacturaModel factura = getById(id);
//                DecimalFormat df = new DecimalFormat("#,###.00");
//
////                String str = Calculo.converterCash(factura.getTotalIVA()).replaceAll(".", "");
//                String str = getValorCasaDecimal(Calculo.converterCash(factura.getTotalIVA()), ".");
//
//                element.getDocumentTotals().setTaxPayable(str.replace(',', '.'));
//
//                str = getValorCasaDecimal(Calculo.converterCash(factura.getSubTotal()), ".");
//                element.getDocumentTotals().setNetTotal(str.replace(',', '.'));
//
//                str = getValorCasaDecimal(Calculo.converterCash(factura.getTotalApagar()), ".");
//                element.getDocumentTotals().setGrossTotal(str.replace(',', '.'));
//
//                List<Invoice> lista = new ArrayList<>();
//                lista.add(element);
//                invoiceses.add(new Invoices(lista));
//                System.out.println("faturas");
//                System.out.println(element);
//                System.out.println("--------------------------------------------------");
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            System.out.println("ClassExpetion:" + ex.getClass() + ",\n MessageException : " + ex.getMessage());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return invoiceses;
//    }
    public List<Invoices> getFacturaGerais(String data, String data1) {

        String sql = "select * from factura where date(data) between '" + data + "' and '" + data1 + "'";
        // CRIAR UMA LISTA QUE ARMAZENA AS FATURAS
        List<Invoices> invoiceses = new ArrayList<>();

        try {

            Connection con = conFactory.open();
            PreparedStatement cmd = con.prepareCall(sql);
            ResultSet rs = cmd.executeQuery();

            String dataFactura2 = "";
            while (rs.next()) {

                String dataAux = rs.getString("data");
                String tipoFactura = rs.getString("TipoFactura");
//                double netTotal = rs.getDouble("SubTotal");
//                String dataFactura = rs.getString("data").substring(0, 10);
                String dataFactura = dataAux.substring(0, 10);
                Integer codigoFatura = rs.getInt("id");

                Invoice element = new Invoice();
                element.setInvoiceNo(codigoFatura.toString() + " " + getSerie(con) + "/" + getNsequencial(con));
                element.setInvoiceDate(dataFactura);
//                System.out.println("hash: " + rs.getString("hash"));
                element.setHash("" + rs.getString("hash"));

                String datav = dataAux.substring(0, 19);
                String[] vet = datav.split(" ");
                element.setSystemEntryDate(vet[0] + "T" + vet[1]);

                element.setCustomerID(rs.getString("IdCliente"));

                Date hora = new Date();
                SimpleDateFormat hora_formato = new SimpleDateFormat("dd/MM/yyyy");

                String vet2[] = hora_formato.format(hora).split("/");

                int mes = Integer.parseInt(vet2[1]);
                element.setPeriod(mes < 9 ? vet2[1].substring(1) : vet2[1]);

                String nextPagamento = rs.getString("nextFactura");
                nextPagamento = nextPagamento.trim().toUpperCase().substring(0, 2);
                element.setInvoiceType(nextPagamento);

                DocumentStatus documentStatus = element.getDocumentStatus();
                //------------------ MOVIMENTO ----------------------------------
                MovimentoController movimentoController = new MovimentoController();
                Movimento movimento = movimentoController.getByIdFactura(codigoFatura, con);

                //-------------------- EM TESTE -----------------------------------------------------------        
                //----------------- FIM MOVIMENTO --------------------------------
                if (tipoFactura.trim().equalsIgnoreCase("Perfoma")) {
////                    element.setInvoiceType("FT");

                    documentStatus.setInvoiceStatus("A");

                } else {
////                    element.setInvoiceType("FR");
                    documentStatus.setInvoiceStatus("N");
                }

                if (movimento != null) {

                    if (movimento.getTipoMovimento().equals("C")) {
//                        element.setInvoiceType("NC");
                        documentStatus.setInvoiceStatus("A");//por equanto
                    } else if (movimento.getTipoMovimento().equals("D")) {
//                        element.setInvoiceType("ND");
                        documentStatus.setInvoiceStatus("A");//por equanto
                    }
                }

                element.setDocumentStatus(documentStatus);
                //----------------------------- FIM TESTE ---------------------------------------------------------        

                List<LineItens> itens = getItemFatura(codigoFatura, dataFactura, tipoFactura, con, movimento);
                element.setLineItens(itens);
                //  CRIAR UMA LISTA DE FATURA COM TODOS OS SEUS ITENS

                FacturaModel factura = this.getById(codigoFatura, con);

//                String str = Calculo.converterCash(factura.getTotalIVA()).replaceAll(".", "");
                String str = getValorCasaDecimal(Calculo.converterCash(factura.getTotalIVA()), ".");
                Double totalIva = factura.getTotalIVA();
                element.getDocumentTotals().setTaxPayable(str.replace(',', '.'));

                str = getValorCasaDecimal(Calculo.converterCash(factura.getSubTotal()), ".");
                element.getDocumentTotals().setNetTotal(str.replace(',', '.'));

                Double total = factura.getSubTotal() + totalIva;
                str = getValorCasaDecimal(Calculo.converterCash(total), ".");

//                str = getValorCasaDecimal(Calculo.converterCash(factura.getTotalApagar()), ".");
                element.getDocumentTotals().setGrossTotal(str.replace(',', '.'));
                Double totalDesconto = factura.getTotalDesconto();

                FacturaItemController fiController = new FacturaItemController();
                totalDesconto += fiController.getItemByIdFactura(codigoFatura, con);

                str = getValorCasaDecimal(Calculo.converterCash(totalDesconto), ".");
                element.getDocumentTotals().setSettlement(str.replace(',', '.'));

                List<Invoice> lista = new ArrayList<>();
                lista.add(element);
                invoiceses.add(new Invoices(lista));

            }
            conFactory.close(con, cmd, rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("ClassExpetion:" + ex.getClass() + ",\n MessageException : " + ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return invoiceses;
    }

    public List<Invoices> getNotasGerais(String data, String data1, List<Invoices> invoiceses) {

        String sql = "select * from factura where date(data) between '" + data + "' and '" + data1 + "'";
        // CRIAR UMA LISTA QUE ARMAZENA AS FATURAS

        try {

            Connection con = conFactory.open();
            PreparedStatement cmd = con.prepareCall(sql);
            ResultSet rs = cmd.executeQuery();

            String dataFactura2 = "";
            while (rs.next()) {

                String dataAux = rs.getString("data");
                String tipoFactura = rs.getString("TipoFactura");
//                String dataFactura = rs.getString("data").substring(0, 10);
                String dataFactura = dataAux.substring(0, 10);
                Integer codigoFatura = rs.getInt("id");

                Invoice element = new Invoice();
                element.setInvoiceNo(codigoFatura.toString() + " " + getSerie(con) + "/" + getNsequencial(con));
                element.setInvoiceDate(dataFactura);
//                System.out.println("hash: " + rs.getString("hash"));
                element.setHash("" + rs.getString("hash"));

                String datav = dataAux.substring(0, 19);
                String[] vet = datav.split(" ");
                element.setSystemEntryDate(vet[0] + "T" + vet[1]);

                element.setCustomerID(rs.getString("IdCliente"));

                Date hora = new Date();
                SimpleDateFormat hora_formato = new SimpleDateFormat("dd/MM/yyyy");

                String vet2[] = hora_formato.format(hora).split("/");

                int mes = Integer.parseInt(vet2[1]);
                element.setPeriod(mes < 9 ? vet2[1].substring(1) : vet2[1]);

                String nextPagamento = rs.getString("nextFactura");
                nextPagamento = nextPagamento.trim().toUpperCase().substring(0, 2);
                element.setInvoiceType(nextPagamento);

                DocumentStatus documentStatus = element.getDocumentStatus();
                //------------------ MOVIMENTO ----------------------------------
                MovimentoController movimentoController = new MovimentoController();
                Movimento movimento = movimentoController.getByIdFactura(codigoFatura, con);

                //-------------------- EM TESTE -----------------------------------------------------------        
                //----------------- FIM MOVIMENTO --------------------------------
                if (tipoFactura.trim().equalsIgnoreCase("Perfoma")) {
////                    element.setInvoiceType("FT");

                    documentStatus.setInvoiceStatus("A");

                } else {
////                    element.setInvoiceType("FR");
                    documentStatus.setInvoiceStatus("N");
                }

                if (movimento != null) {

                    if (movimento.getTipoMovimento().equals("C")) {
                        element.setInvoiceType("NC");
                    } else if (movimento.getTipoMovimento().equals("D")) {
                        element.setInvoiceType("ND");
                    }
                }

                element.setDocumentStatus(documentStatus);
                //----------------------------- FIM TESTE ---------------------------------------------------------        

                List<LineItens> itens = getItemFatura(codigoFatura, dataFactura, tipoFactura, con, movimento);
                element.setLineItens(itens);
                //  CRIAR UMA LISTA DE FATURA COM TODOS OS SEUS ITENS

                FacturaModel factura = this.getById(codigoFatura);

//                String str = Calculo.converterCash(factura.getTotalIVA()).replaceAll(".", "");
                String str = getValorCasaDecimal(Calculo.converterCash(factura.getTotalIVA()), ".");

                element.getDocumentTotals().setTaxPayable(str.replace(',', '.'));

                str = getValorCasaDecimal(Calculo.converterCash(factura.getTotalApagar()), ".");
                element.getDocumentTotals().setNetTotal(str.replace(',', '.'));

                str = getValorCasaDecimal(Calculo.converterCash(factura.getTotalApagar()), ".");
                element.getDocumentTotals().setGrossTotal(str.replace(',', '.'));

                Double totalDesconto = factura.getTotalDesconto();

                FacturaItemController fiController = new FacturaItemController();
                totalDesconto += fiController.getItemByIdFactura(codigoFatura, con);

                str = getValorCasaDecimal(Calculo.converterCash(totalDesconto), ".");
                element.getDocumentTotals().setSettlement(str.replace(',', '.'));

                List<Invoice> lista = new ArrayList<>();
                lista.add(element);
                invoiceses.add(new Invoices(lista));

            }
            conFactory.close(con, cmd, rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("ClassExpetion:" + ex.getClass() + ",\n MessageException : " + ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return invoiceses;
    }

    public List<LineItens> getItemFatura(Integer codigoFatura, String dataFactura, String tipoFactura, Connection con, Movimento movimento) {

        String sql = "SELECT f.*, p.*"
                + "FROM facturaitem f "
                + "join  produto p on p.id = f.IdProduto"
                + " where f.IdFactura=" + codigoFatura;

        List<LineItens> lista = new ArrayList<>();

        try {

            Integer cont = 0;

            if (con.isClosed()) {
                con = conFactory.open();;
            }
            PreparedStatement cmd = con.prepareCall(sql);
            ResultSet rs = cmd.executeQuery();

            while (rs.next()) {
                cont++;
                LineItens.LineItem item = new LineItens().getLine();
                item.setLineNumber(cont.toString());
                item.setProductCode(rs.getString("p.id"));

//                double subTotal = rs.getDouble("f.preco") * rs.getDouble("f.qtd");
                double descValor = rs.getDouble("f.Desconto");
//                descValor = (descValor*100)/subTotal;

                item.setProductDescription(rs.getString("p.designacao"));
                item.setQuantity(rs.getString("f.qtd"));
                item.setUnitOfMeasure("UND");
                item.setUnitPrice(getValorCasaDecimal(Calculo.converterCash(rs.getDouble("f.preco")), ".").replace(',', '.'));
                item.setTaxPointDate(dataFactura);
                String descricao = null;
                if (!Objects.equals(descricao, null) && !Objects.equals(descricao, "")) {
                    item.setDescription(descricao);
                }
                movimento = null; //por enquanto
                if (movimento == null) {
//                   if (!tipoFactura.trim().equals("Venda Credito")) {
                    item.setDebitAmount("0.00");
                    item.setCreditAmount(getValorCasaDecimal(Calculo.converterCash(rs.getDouble("f.preco") * rs.getDouble("f.Qtd")), ".").replace(',', '.'));
                } else if (movimento.getTipoMovimento().equals("D")) {

                    item.setDebitAmount("0.00");
                    item.setCreditAmount(getValorCasaDecimal(Calculo.converterCash(movimento.getValor()), ".").replace(',', '.'));

                } else if (movimento.getTipoMovimento().equals("C")) {

                    item.setDebitAmount(getValorCasaDecimal(Calculo.converterCash(movimento.getValor()), ".").replace(',', '.'));
                    item.setCreditAmount("0.00");
                }
//                } else {
//                    item.setDebitAmount(getValorCasaDecimal(Calculo.converterCash(rs.getDouble("f.preco")), ".").replace(',', '.'));
//                    item.setCreditAmount("0.00");
//                }
                Integer idTaxa = rs.getInt("p.IdTaxa");
                Integer idMotivo = rs.getInt("p.IdMotivo");

                TaxaController taxaDao = new TaxaController();

                Taxa modelo1 = taxaDao.getById(idTaxa);

                if (modelo1.getTaxa() != 0) {

                    if (modelo1.getTaxa() == 14) {
                        item.getTax().setTaxType("IVA");
                        item.getTax().setTaxCode("NOR");

                    }

                    item.getTax().setTaxPercentage("" + modelo1.getTaxa());

                } else {

                    MotivoController motivoDao = new MotivoController();
                    Motivo motivo = motivoDao.getById(idMotivo);

                    item.getTax().setTaxType("NS");
                    item.getTax().setTaxCode("ISE");
                    item.setTaxExemptionReason(motivo.getDescricao());
                    item.setTaxExemptionCode(motivo.getCodigo());
//                    item.getTax().setTaxCode(modelo.getCodigoMotivo());
                    item.getTax().setTaxPercentage("0");
                }

                item.setSettlementAmount(Calculo.converterSemCasa(descValor));

                LineItens linha = new LineItens();
                linha.setLine(item);
                lista.add(linha);

            }
            cmd.close();
            rs.close();
        } catch (Exception ex) {

            ex.printStackTrace();
        } finally {
//            ////conFactory.close(con, command, query);
        }

        return lista;
    }

    public int getNsequencial(Connection con) {

        String sql = "SELECT ano FROM serie where status = 2";

        try {
//               ConnectionFactor con = ConexaoMysql.open();
            PreparedStatement cmd = con.prepareCall(sql);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                return rs.getInt("ano");
            }
//            ConexaoMysql.close(con, cmd, rs);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public String getSerie(Connection con) {
        String codigo = "";
        String sql = "SELECT designacao FROM serie where status = 2";

        try {

            PreparedStatement cmd = con.prepareCall(sql);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                codigo = rs.getString("designacao");
            }
//            ConexaoMysql.close(con, cmd, rs);
        } catch (Exception ex) {
            ex.printStackTrace();
            return codigo;
        }
        return codigo;
    }

    public List<CustomerIten> listaClientes(String data1, String data2) {
//        sql = "select count(Codigo) as total from factura where date(DataFactura) between '" + data + "' and '" + data1 + "' and not StatusFactura = 3";
        String sql = "select DISTINCT c.Id, c.Nif, c.Nome, c.Morada from factura f join cliente c  on c.Id = f.IdCliente where date(f.Data) between '" + data1 + "' and '" + data2 + "'";

        con = conFactory.open();

        List<CustomerIten> item = new ArrayList<>();

        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {
                CustomerIten modelo = new CustomerIten();
                CustomerIten.Customer ci = new CustomerIten().getCustomer();
                ci.setCustomerID(String.valueOf(query.getInt("c.Id")));
                String custormerTaxID = query.getString("c.Nif");

                if (custormerTaxID != null) {
                    custormerTaxID.trim();
                    if (custormerTaxID.equals("")) {
                        ci.setCustomerTaxID(custormerTaxID);
                    } else {
                        ci.setCustomerTaxID("Consumidor final");
                    }
                } else {
                    ci.setCustomerTaxID("Consumidor final");
                }

                ci.setCompanyName(query.getString("c.Nome"));
                ci.getBillingAddress().setAddressDetail(query.getString("c.Morada"));
                modelo.setCustomer(ci);
//                ci.getBillingAddress()
                item.add(modelo);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ////conFactory.close(con, command, query);
        }
        return item;
    }

    public List<ProdutItem> listaProdutosVendidos(String data1, String data2) {
//        sql = "select count(Codigo) as total from factura where date(DataFactura) between '" + data + "' and '" + data1 + "' and not StatusFactura = 3";
        String sql = "select DISTINCT  p.Id, p.Designacao from facturaitem f "
                + "inner join produto p on p.Id = f.IdProduto"
                + " inner join factura fa on fa.Id = f.IdFactura  "
                + " where date(fa.Data) between '" + data1 + "' and '" + data2 + "'";

        con = conFactory.open();

        List<ProdutItem> pitem = new ArrayList<>();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

                ProdutItem.Product produto = new ProdutItem().getProduct();
                produto.setProductCode(String.valueOf(query.getInt("p.Id")));
                produto.setProductDescription(query.getString("p.Designacao"));
                ProdutItem modelo = new ProdutItem();
                modelo.setProduct(produto);
                pitem.add(modelo);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ////conFactory.close(con, command, query);
        }

        return pitem;
    }

    public double getTotalCredit(String data, String data1) {

        String sql = "select sum(ft.SubTotal) as total "
                + " from factura f INNER JOIN facturaitem ft ON ft.IdFactura = f.Id "
                + " where f.IdEstado = 1 AND date(Data) between '" + data + "' and '" + data1 + "' and f.TipoFactura ='Venda'";

        con = conFactory.open();
        double valor = 0;
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {
                valor = query.getDouble("total");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ////conFactory.close(con, command, query);
        }
        return valor;
    }

    public double getTotalFacturaCredito(String data, String data1) {

        String sql = "select sum(f.SubTotal) as total "
                + " from factura f INNER JOIN facturaitem ft ON ft.IdFactura = f.Id"
                + " where date(Data) between '" + data + "' and '" + data1 + "'"
                + " and f.TipoFactura = 'Venda Credito'"
                + " and f.Id NOT IN(SELECT IdFactura FROM movimento )";

        con = conFactory.open();
        double valor = 0;
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {
                valor = query.getDouble("total");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ////conFactory.close(con, command, query);
        }
        return valor;
    }

    public double getTotalFacturaDebita(String data, String data1) {

        String sql = "select sum(f.SubTotal) as total "
                + " from factura f INNER JOIN facturaitem ft ON ft.IdFactura = f.Id"
                + " where date(Data) between '" + data + "' and '" + data1 + "'"
                + " and f.TipoFactura = 'Venda'"
                + " and f.Id IN(SELECT IdFactura FROM movimento where TipoMovimento='C')";

        con = conFactory.open();
        double valor = 0;
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {
                valor = query.getDouble("total");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ////conFactory.close(con, command, query);
        }
        return valor;
    }

    public List<TaxTableItem> listaIVAIsencao(String data1, String data2) {

        String sql = " select DISTINCT t.Id, t.Descricao, t.Taxa "
                + " from facturaitem f"
                + " inner join produto p on p.Id = f.IdProduto "
                + " inner join taxa t on t.Id = p.IdTaxa "
                + " inner join factura fa on fa.Id = f.IdFactura  "
                + " where date(fa.Data) between '" + data1 + "' and '" + data2 + "'";

        con = conFactory.open();

        List<TaxTableItem> pitem = new ArrayList<>();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

                TaxTableItem.TaxTableEntry tax = new TaxTableItem().getTaxTableEntry();
                tax.setTaxPercentage(String.valueOf(query.getInt("t.taxa")));

                if (Integer.parseInt(tax.getTaxPercentage()) == 14) {
                    if (!verificarTaxTableEntryExiste(pitem, "IVA")) {
                        tax.setTaxType("IVA");
                        tax.setTaxCode("NOR");
                        tax.setDescription("Taxa Normal");
                        TaxTableItem titem = new TaxTableItem();
                        titem.setTaxTableEntry(tax);
                        pitem.add(titem);
                    }
                } /*else if(Integer.parseInt(tax.getTaxPercentage())== 7)
                {
                   tax.setTaxType("IVA");
                   tax.setTaxCode("NOR");
                   tax.setDescription("Normal");
                   tax.setTaxCountryRegion("AO");
                }*/ else {
                    if (!verificarTaxTableEntryExiste(pitem, "IS")) {
                        tax.setTaxType("IS");
                        tax.setTaxCode("ISE");
                        tax.setDescription("Isenta");
                        tax.setTaxAmount("0.00");
                        tax.setTaxPercentage("0.00");
                        TaxTableItem titem = new TaxTableItem();
                        titem.setTaxTableEntry(tax);

                        pitem.add(titem);
                    }
                }

            }

            System.out.println(" lista de IVAInsenção : " + pitem.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ////conFactory.close(con, command, query);
        }

        return pitem;
    }

    public String getNentradasFacturas(String data, String data1) {

        con = conFactory.open();
        String sql = "select count(Id) as total "
                + "from factura "
                + "where date(Data) between '" + data + "' and '" + data1 + "'";

        String valor = "0";
        try {

            command = con.prepareCall(sql);
            query = command.executeQuery();

            if (query.next()) {
                valor = String.valueOf(query.getInt("total"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ////conFactory.close(con, command, query);
        }
        return valor;
    }

    public List<FacturaModel> getFacturas() {

        List<FacturaModel> lista = new ArrayList();
        String sql = "SELECT * FROM factura f where  IdEstado NOT IN(3)";
        con = conFactory.open();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

                FacturaModel modelo = new FacturaModel();
                modelo.setId(query.getInt("Id"));
                modelo.setTotalApagar(query.getDouble("TotalApagar"));

                lista.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public List<FacturaModel> getFacturas(String dataInicio, String dataFim) {

        List<FacturaModel> lista = new ArrayList();
        String sql = "SELECT * FROM factura f where  IdEstado NOT IN(3)"
                + " and Data between '" + dataInicio + "' and '" + dataFim + "'";
        con = conFactory.open();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

                FacturaModel modelo = new FacturaModel();
                modelo.setId(query.getInt("Id"));
                modelo.setTotalApagar(query.getDouble("TotalApagar"));

                lista.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public double getTotalDividaCliente(int codCliente) {

        double valorTotal = 0;
        String sql = "SELECT SUM(TotalApagar) AS TotalApagar FROM factura f "
                + "where  f.IdCliente = " + codCliente + " and TipoFactura ='Venda'"
                + " and IdFormaPagamento = 4 "
                + " and TotalApagar > 0";
        con = conFactory.open();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {

                valorTotal = query.getDouble("TotalApagar");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return valorTotal;
    }

    public double getTotalFactura(int codFactura) {

        double valorTotal = 0;
        String sql = "SELECT TotalApagar AS TotalApagar FROM factura f "
                + "where  f.Id = " + codFactura;
        con = conFactory.open();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {

                valorTotal = query.getDouble("TotalApagar");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return valorTotal;
    }

    public int getLastIdByUsuario(UsuarioModel usuario) {

        int id = 0;
        try {
            String sql = "SELECT Id From factura WHERE IdUsuario = ? Order By Id DESC LIMIT 1";
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

    public int getLastId() {

        int id = 0;
        try {
            String sql = "SELECT Id From factura Order By Id DESC LIMIT 1";
            con = conFactory.open();
            command = con.prepareCall(sql);
//            command.setInt(1, usuario.getId());
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
    
    public int getLastIdFR() {

        int id = 0;
        try {
            String sql = "SELECT Id From factura WHERE TipoFactura IN ('Venda') Order By Id DESC LIMIT 1";
            con = conFactory.open();
            command = con.prepareCall(sql);
//            command.setInt(1, usuario.getId());
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
    public int getLastIdFR(FacturaModel facturaModel) {

        int id = 0;
        try {
            String sql = "SELECT Id From factura WHERE TipoFactura IN ('Venda') AND Id < "+facturaModel.getId()+" Order By Id DESC LIMIT 1";
            con = conFactory.open();
            command = con.prepareCall(sql);
//            command.setInt(1, usuario.getId());
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
    public int getLastIdFT() {

        int id = 0;
        try {
            String sql = "SELECT Id From factura WHERE TipoFactura IN ('Venda Credito') Order By Id DESC LIMIT 1";
            con = conFactory.open();
            command = con.prepareCall(sql);
//            command.setInt(1, usuario.getId());
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
    public int getLastIdFP() {

        int id = 0;
        try {
            String sql = "SELECT Id From factura WHERE TipoFactura IN ('Perfoma') Order By Id DESC LIMIT 1";
            con = conFactory.open();
            command = con.prepareCall(sql);
//            command.setInt(1, usuario.getId());
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
    
    
    
     

}
