/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ClienteModel;
import Model.EstadoModel;
import Model.FornecedorModel;
import Model.TipoClienteModel;
import Model.TipoFornecedorModel;
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
public class ClienteController implements IController<ClienteModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(ClienteModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `cliente`\n"
                    + "("
                    + "`Nome`,\n"
                    + "`Email`,\n"
                    + "`Contacto`,\n"
                    + "`Morada`,\n"
                    + "`IdEstado`,\n"
                    + "`IdTipoCliente`,\n"
                    + "`Data`,\n"
                    + "`IdUsuario`,LimiteCredito,ValorCarteira,Nif)\n"
                    + "VALUES\n"
                    + "("
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,?,?,?);";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getNome());
            command.setString(2, obj.getEmail());
            command.setString(3, obj.getContacto());
            command.setString(4, obj.getEndereco());
            command.setInt(5, obj.getEstado().getId());
            command.setInt(6, obj.getTipoCliente().getId());

            command.setString(7, obj.getData());
            command.setInt(8, obj.getUsuario().getId());
            command.setDouble(9, obj.getLimiteCredito());
            command.setDouble(10, obj.getValorCarteira());
            command.setString(11, obj.getNif());

            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(CategoriaABController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command);
        }
        return result;
    }

    @Override
    public boolean update(ClienteModel obj) {

        boolean result = false;
        try {
            String sql = "UPDATE `cliente`\n"
                    + "SET "
                    + "`Nome` = ?,\n"
                    + "`Email`= ?,\n"
                    + "`Contacto`= ?,\n"
                    + "`Morada`= ?,\n"
                    + "`IdEstado`= ?,\n"
                    + "`IdTipoCliente`= ?,LimiteCredito = ?,ValorCarteira = ?,Nif=? WHERE Id = ?";

            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getNome());
            command.setString(2, obj.getEmail());
            command.setString(3, obj.getContacto());
            command.setString(4, obj.getEndereco());
            command.setInt(5, obj.getEstado().getId());
            command.setInt(6, obj.getTipoCliente().getId());
            command.setDouble(7, obj.getLimiteCredito());
            command.setDouble(8, obj.getValorCarteira());
            command.setString(9, obj.getNif());
            command.setInt(10, obj.getId());

            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(CategoriaABController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command);
        }
        return result;
    }
    public boolean updateCarteira(ClienteModel obj) {

        boolean result = false;
        try {
            String sql = "UPDATE `cliente`\n"
                    + "SET ValorCarteira = ? WHERE Id = ?";

            con = conFactory.open();
            command = con.prepareCall(sql);
           
            command.setDouble(1, obj.getValorCarteira());
          
            command.setInt(2, obj.getId());

            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(CategoriaABController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(ClienteModel obj) {

        if (obj.getId() > 0) {
            return update(obj);
        }
        return save(obj);
    }

    @Override
    public ClienteModel getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ClienteModel> get() {
        
        List<ClienteModel> lista = new ArrayList();
        try {

            String sql = "SELECT * FROM cliente c "
                    + "INNER JOIN tipocliente t ON t.Id = c.IdTipoCliente"
                    + " WHERE c.IdEstado = 1 ORDER BY 1";
            con = conFactory.open();
            command = con.prepareCall(sql);
            query = command.executeQuery();
           // lista.add(new ClienteModel(0,""));
            while (query.next()) {

                ClienteModel modelo = new ClienteModel();
                modelo.setContacto(query.getString("Contacto"));
                modelo.setEmail(query.getString("Email"));
                modelo.setEndereco(query.getString("Morada"));
                modelo.setId(query.getInt("c.Id"));
                modelo.setLimiteCredito(query.getDouble("LimiteCredito"));
                modelo.setValorCarteira(query.getDouble("ValorCarteira"));
                modelo.setTipoCliente(new TipoClienteModel(query.getInt("t.Id"), query.getString("t.Designacao")));
                modelo.setNome(query.getString("Nome"));

                lista.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return lista;
    }
    public List<ClienteModel> getClienteFactura() {
        
      

        List<ClienteModel> lista = new ArrayList();
        String sql = "SELECT distinct NomeCliente FROM factura f ";
        con = conFactory.open();
        try {
            command = con.prepareCall(sql); 

            query = command.executeQuery();
            while (query.next()) {

                ClienteModel modelo = new ClienteModel();
                modelo.setNome(query.getString("NomeCliente"));
                lista.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return lista;
    
    }

    public ClienteModel getAll(String text) {

        ClienteModel lista = new ClienteModel();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM cliente WHERE Nome = ?;";

            command = con.prepareCall(sql);
            command.setString(1, text);
            query = command.executeQuery();
            if (query.next()) {

                lista = new ClienteModel();
                lista.setNome(query.getString("Nome"));
                lista.setContacto(query.getString("Contacto"));
                lista.setEmail(query.getString("Email"));
                lista.setEstado(new EstadoModel(query.getInt("IdEstado"), ""));
                lista.setId(query.getInt("Id"));
                lista.setLimiteCredito(query.getDouble("LimiteCredito"));
                lista.setValorCarteira(query.getDouble("ValorCarteira"));
                //lista.setNumContribuente(query.getString("NumContribuente"));
                lista.setTipoCliente(new TipoClienteModel(query.getInt("IdTipoCliente"), ""));
                return lista;
            }

        } catch (Exception ex) {
            Logger.getLogger(CategoriaABController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return null;
    }
    public ClienteModel getLast() {

        ClienteModel lista = new ClienteModel();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM cliente ;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.last()) {

                lista = new ClienteModel();
                lista.setNome(query.getString("Nome"));
                lista.setContacto(query.getString("Contacto"));
                lista.setEmail(query.getString("Email"));
                lista.setEstado(new EstadoModel(query.getInt("IdEstado"), ""));
                lista.setId(query.getInt("Id"));
                lista.setLimiteCredito(query.getDouble("LimiteCredito"));
                lista.setValorCarteira(query.getDouble("ValorCarteira"));
                //lista.setNumContribuente(query.getString("NumContribuente"));
                lista.setTipoCliente(new TipoClienteModel(query.getInt("IdTipoCliente"), ""));
                return lista;
            }

        } catch (Exception ex) {
            Logger.getLogger(CategoriaABController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return null;
    }

    public List<ClienteModel> get(String text) {

        List<ClienteModel> lista = new ArrayList();
        try {

            String sql = "SELECT * FROM cliente c "
                    + "INNER JOIN tipocliente t ON t.Id = c.IdTipoCliente"
                    + " WHERE c.Nome LIKE '" + text + "%' ORDER BY c.Nome;";
            con = conFactory.open();
            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                ClienteModel modelo = new ClienteModel();
                modelo.setContacto(query.getString("Contacto"));
                modelo.setEmail(query.getString("Email"));
                modelo.setEndereco(query.getString("Morada"));
                modelo.setNif(query.getString("Nif"));
                modelo.setId(query.getInt("c.Id"));
                modelo.setLimiteCredito(query.getDouble("LimiteCredito"));
                modelo.setValorCarteira(query.getDouble("ValorCarteira"));
                modelo.setTipoCliente(new TipoClienteModel(query.getInt("t.Id"), query.getString("t.Designacao")));
                modelo.setNome(query.getString("Nome"));

                lista.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return lista;
    }

}
