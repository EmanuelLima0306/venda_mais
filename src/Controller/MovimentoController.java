/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ClienteModel;
import Model.Movimento;
import Model.EstadoModel;
import Model.FacturaModel;
import Model.FormaPagamentoModel;
import Model.TipoClienteModel;
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
public class MovimentoController implements IController<Movimento> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(Movimento obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `movimento`\n"
                    + "(\n"
                    + "`Obs`,\n"
                    + "`Valor`,\n"
                    + "`TipoMovimento`,\n"
                    + "`IdFactura`,\n"
                    + "`IdUsuario`,\n"
                    + "`IdCliente`,\n"
                    + "`Data`,\n"
                    + "`DataOperacao`,criada_modulo_formacao)\n"
                    + "VALUES\n"
                    + "(?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,?);";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getObs());
            command.setDouble(2, obj.getValor());
            command.setString(3, obj.getTipoMovimento());
            command.setInt(4, obj.getFactura().getId());
            command.setInt(5, obj.getUsuario().getId());
            command.setInt(6, obj.getCliente().getId());
         

            command.setString(7, obj.getData());
            command.setString(8, obj.getDataOpercao());
            command.setBoolean(9, obj.getCriada_modulo_formacao());
            

            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(MovimentoController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean update(Movimento obj) {

        boolean result = false;
        try {
            String sql = "   UPDATE movimento"
                    + "   SET"
                    + "`Obs` = ?,"
                    + "`Valor` = ?,"
                    + "`TipoMovimento` = ?,"
                    + "`IdFactura` = ?,"
                    + "`IdUsuario` = ?,"
                    + "`IdCliente` = ?,"
                    + "`Data` = ?,"
                    + "`DataOperacao` = ?"
                    + "   WHERE Id = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getObs());
            command.setDouble(2, obj.getValor());
            command.setString(3, obj.getTipoMovimento());
            command.setInt(4, obj.getFactura().getId());
            command.setInt(5, obj.getUsuario().getId());
            command.setInt(6, obj.getCliente().getId());
            
            command.setString(7, obj.getData());
            command.setString(8, obj.getDataOpercao());
            
            command.setInt(9, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(MovimentoController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(Movimento obj) {

        if (obj.getId() > 0) {
            return update(obj);
        }
        return save(obj);
    }

    public int getLastIdByUsuario(UsuarioModel usuario) {

        int id = 0;
        try {
            String sql = "SELECT Id From movimento WHERE IdUsuario = ? Order By Id DESC LIMIT 1";
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
    
    public int getIdByFactura(int idFactura) {

        int id = 0;
        try {
            String sql = "SELECT Id From movimento WHERE IdFactura = ? Order By Id DESC LIMIT 1";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, idFactura);
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
    public boolean getCriadaModuloFormacaoByFactura(int idFactura) {

        boolean flag = false;
        try {
            String sql = "SELECT criada_modulo_formacao From movimento WHERE IdFactura = ? Order By Id DESC LIMIT 1";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, idFactura);
            query = command.executeQuery();

            if (query.next()) {
                flag = query.getBoolean(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return flag;
    }
    public boolean updateHashReference(String hash, String reference, int codigo) {

        String sql = "update movimento set Hash = '" + hash + "',Reference  = '" + reference + "' where Id = " + codigo;
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
  public int getLast() {

        int id = 0;
        try {
            String sql = "SELECT Id From movimento  Order By Id DESC LIMIT 1";
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
    @Override
    public Movimento getById(int id) {
        
        Movimento modelo = null;

        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM movimento m"
                    + " INNER JOIN cliente c ON c.Id = m.IdCliente "
                    + " INNER JOIN factura f ON f.Id = m.IdFactura"
                    + " WHERE m.Id = "+id;
//            String sql = " SELECT * "
//                    + " FROM movimento m"
//                    + " INNER JOIN cliente c ON c.Id = m.IdCliente "
//                    + " INNER JOIN factura f ON f.Id = m.IdFactura"
//                    + " WHERE m.IdEstado = 1 and m.Id = "+id;

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {

                modelo = new Movimento();
                modelo.setId(query.getInt("m.Id"));
                modelo.setNext(query.getString("m.NextFactura"));
                modelo.setHash(query.getString("m.Hash"));

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
    public Movimento getById(int id,Connection con) {
        
        Movimento modelo = null;

        try {

//        
//            String sql = " SELECT * "
//                    + " FROM movimento m"
//                    + " INNER JOIN cliente c ON c.Id = m.IdCliente "
//                    + " INNER JOIN factura f ON f.Id = m.IdFactura"
//                    + " WHERE m.IdEstado = 1 and m.Id = "+id;
            
            String sql = " SELECT * "
                    + " FROM movimento m"
                    + " INNER JOIN cliente c ON c.Id = m.IdCliente "
                    + " INNER JOIN factura f ON f.Id = m.IdFactura"
                    + " WHERE m.Id = "+id;

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                modelo = new Movimento();
                modelo.setId(query.getInt("m.Id"));
                modelo.setNext(query.getString("m.NextFactura"));
                modelo.setData(query.getString("m.Data"));
                modelo.setHash(query.getString("m.Hash"));
                modelo.setUsuario(new UsuarioModel(query.getInt("m.IdUsuario"), ""));

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

                factura.setFormaPagamento(formaPagamento);
                factura.setId(query.getInt("Id"));

                modelo.setFactura(factura);
                modelo.setObs(query.getString("m.Obs"));

            }

        } catch (Exception ex) {
            Logger.getLogger(MovimentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return modelo;
    }

    public boolean updateNextFactura(String next, int codigo) {

        String sql = "update movimento set NextFactura = '" + next + "' where Id = " + codigo;
        con = conFactory.open();
        boolean flag = false;
        try {
            command = con.prepareCall(sql);
            flag = command.executeUpdate() > 0;
        } catch (Exception ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return flag;

    }
    public boolean updateStatus(Movimento model) {

        String sql = "update movimento set IdEstado = " + model.getEstado().getId() + " where Id = " + model.getId();
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

    @Override
    public List<Movimento> get() {

        List<Movimento> lista = new ArrayList<>();

        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM movimento m"
                    + " INNER JOIN cliente c ON c.Id = m.IdCliente "
                    + " INNER JOIN factura f ON f.Id = m.IdFactura"
                    + " WHERE m.IdEstado = 1;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                Movimento modelo = new Movimento();
                modelo.setId(query.getInt("m.Id"));

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
        return lista;
    }
    public List<Movimento> getByTipo(String tipoDoc,String data) {

        List<Movimento> lista = new ArrayList<>();

        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM movimento m"
                    + " INNER JOIN cliente c ON c.Id = m.IdCliente "
                    + " INNER JOIN factura f ON f.Id = m.IdFactura"
                    + " WHERE m.IdEstado = 1 and TipoMovimento = '"+tipoDoc+"'"
                    + " and date(m.Data) ='"+data+"'";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                Movimento modelo = new Movimento();
                modelo.setId(query.getInt("m.Id"));

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
                factura.setNomeCliente(query.getString("f.NomeCliente"));
                factura.setTroco(query.getDouble("f.Troco"));
                factura.setTotalDesconto(query.getDouble("f.TotalDesconto"));
                factura.setTotalApagar(query.getDouble("f.TotalApagar"));
                factura.setSubTotal(query.getDouble("f.SubTotal"));
                factura.setTotalIVA(query.getDouble("f.TotalIVA"));
                factura.setHash(query.getString("f.Hash"));
                factura.setValorEntregue(query.getDouble("f.ValorEntregue"));
                factura.setValorMulticaixa(query.getDouble("f.ValorMulticaixa"));
                factura.setId(query.getInt("f.Id"));

                modelo.setFactura(factura);
                modelo.setNext(query.getString("NextFactura"));
                modelo.setObs(query.getString("Obs"));
                modelo.setValor(query.getDouble("Valor"));
                
                lista.add(modelo);

            }

        } catch (Exception ex) {
            Logger.getLogger(MovimentoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public Movimento getByIdFactura(int id) {

        Movimento modelo = null;
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM movimento"
                    + " WHERE IdEstado =1 and IdFactura=?";

            command = con.prepareCall(sql);
            command.setInt(1, id);
            query = command.executeQuery();
            if (query.next()) {

                modelo = new Movimento();
                modelo.setId(query.getInt("Id"));
                modelo.setNext(query.getString("NextFactura"));
                modelo.setData(query.getString("Data"));
                modelo.setObs(query.getString("Obs"));
                modelo.setTipoMovimento(query.getString("TipoMovimento"));
                modelo.setValor(query.getDouble("Valor"));
            }

        } catch (Exception ex) {
            Logger.getLogger(MovimentoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return modelo;
    }

    public Movimento getByIdFactura(int id, Connection con) {

        Movimento modelo = null;
        try {

//            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM movimento m"
                    + " WHERE m.IdEstado = 1 AND IdFactura=?";
            System.out.println(sql);
            command = con.prepareCall(sql);
            command.setInt(1, id);
            query = command.executeQuery();
            if (query.next()) {

                modelo = new Movimento();
                modelo.setId(query.getInt("Id"));
                modelo.setNext(query.getString("NextFactura"));
                modelo.setData(query.getString("Data"));
                modelo.setObs(query.getString("Obs"));
                modelo.setTipoMovimento(query.getString("TipoMovimento"));
                modelo.setValor(query.getDouble("Valor"));
            }

        } catch (Exception ex) {
            Logger.getLogger(MovimentoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
//            conFactory.close(con, command,query);
        }
        return modelo;
    }

    public Movimento getCreditoByIdFactura(int id) {

        Movimento modelo = null;
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM movimento"
                    + " WHERE IdFactura=? and TipoMovimento='C'";

            command = con.prepareCall(sql);
            command.setInt(1, id);
            query = command.executeQuery();
            if (query.next()) {

                modelo = new Movimento();
                modelo.setId(query.getInt("Id"));
                modelo.setNext(query.getString("NextFactura"));
                modelo.setData(query.getString("Data"));
                modelo.setObs(query.getString("Obs"));
                modelo.setTipoMovimento(query.getString("TipoMovimento"));
                modelo.setValor(query.getDouble("Valor"));
            }

        } catch (Exception ex) {
            Logger.getLogger(MovimentoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return modelo;
    }

    public Movimento getCreditoByIdFactura(int id, Connection con) {

        Movimento modelo = null;
        try {

            String sql = " SELECT * "
                    + " FROM movimento"
                    + " WHERE IdFactura=? and TipoMovimento='C'";

            command = con.prepareCall(sql);
            command.setInt(1, id);
            query = command.executeQuery();
            if (query.next()) {

                modelo = new Movimento();
                modelo.setId(query.getInt("Id"));
                modelo.setNext(query.getString("NextFactura"));
                modelo.setData(query.getString("Data"));
                modelo.setObs(query.getString("Obs"));
                modelo.setTipoMovimento(query.getString("TipoMovimento"));
                modelo.setValor(query.getDouble("Valor"));
            }

        } catch (Exception ex) {
            Logger.getLogger(MovimentoController.class.getName()).log(Level.SEVERE, null, ex);

        }finally{
            conFactory.close(con, command,query);
        }
        return modelo;
    }

    public Movimento getDebitoByIdFactura(int id) {

        Movimento modelo = null;
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM movimento"
                    + " WHERE IdFactura=? and TipoMovimento='D'";

            command = con.prepareCall(sql);
            command.setInt(1, id);
            query = command.executeQuery();
            if (query.next()) {

                modelo = new Movimento();
                modelo.setId(query.getInt("Id"));
                modelo.setNext(query.getString("NextFactura"));
                modelo.setData(query.getString("Data"));
                modelo.setObs(query.getString("Obs"));
                modelo.setTipoMovimento(query.getString("TipoMovimento"));
                modelo.setValor(query.getDouble("Valor"));
            }

        } catch (Exception ex) {
            Logger.getLogger(MovimentoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return modelo;
    }

    public double totalDebito(String data, String data1) {

        double total = 0;
        try {

            con = conFactory.open();
            String sql = " SELECT SUM(m.Valor) total FROM movimento m \n"
                    + "INNER JOIN factura f ON f.Id = m.IdFactura"
                    + " WHERE m.IdEstado = 1 AND TipoMovimento='D' and date(f.data) between '" + data + "' and '" + data1 + "'";

            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {
                total = query.getDouble("total");
            }

        } catch (Exception ex) {
            Logger.getLogger(MovimentoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return total;
    }

    public double totalCredito(String data, String data1) {

        double total = 0;
        try {

            con = conFactory.open();
            String sql = " SELECT SUM(m.Valor) total FROM movimento m \n"
                    + "INNER JOIN factura f ON f.Id = m.IdFactura"
                    + " WHERE m.IdEstado = 1 AND TipoMovimento='C' and date(f.data) between '" + data + "' and '" + data1 + "'";

            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {
                total = query.getDouble("total");
            }

        } catch (Exception ex) {
            Logger.getLogger(MovimentoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return total;
    }

    public Movimento getDebitoByIdFactura(int id, Connection con) {

        Movimento modelo = null;
        try {

//            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM movimento"
                    + " WHERE IdFactura=? and TipoMovimento='D'";

            command = con.prepareCall(sql);
            command.setInt(1, id);
            query = command.executeQuery();
            if (query.next()) {

                modelo = new Movimento();
                modelo.setId(query.getInt("Id"));
                modelo.setNext(query.getString("NextFactura"));
                modelo.setData(query.getString("Data"));
                modelo.setObs(query.getString("Obs"));
                modelo.setTipoMovimento(query.getString("TipoMovimento"));
                modelo.setValor(query.getDouble("Valor"));
            }

        } catch (Exception ex) {
            Logger.getLogger(MovimentoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            conFactory.close(con, command,query);
        }
        return modelo;
    }

    public Movimento getAll(String text) {

        Movimento lista = new Movimento();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM `categoria` WHERE Designacao = ?;";

            command = con.prepareCall(sql);
            command.setString(1, text);
            query = command.executeQuery();
            while (query.next()) {

//                lista = new Movimento(query.getInt("Id"),
//                        query.getString("Designacao"),
//                        new EstadoModel(query.getInt("IdEstado"), ""));
            }

        } catch (Exception ex) {
            Logger.getLogger(MovimentoController.class.getName()).log(Level.SEVERE, null, ex);
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
