/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.CategoriaModel;
import Model.EstadoModel;
import Model.FornecedorModel;
import Model.TipoFornecedorModel;
import com.mysql.jdbc.MysqlDataTruncation;
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
public class FornecedorController implements IController<FornecedorModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(FornecedorModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `fornecedor`\n"
                    + "(`Nome`,\n"
                    + "`Email`,\n"
                    + "`Localizacao`,\n"
                    + "`Idtipofornecedor`,\n"
                    + "`Data`,\n"
                    + "`IdUsuario`,\n"
                    + "`IdEstado`,\n"
                    + "`NumContribuente`,Contacto)\n"
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
            command.setString(1, obj.getNome());
            command.setString(2, obj.getEmail());
            command.setString(3, obj.getLocalizacao());

            command.setInt(4, obj.getTipoFornecedor().getId());

            command.setString(5, obj.getData());
            command.setInt(6, obj.getUsuario().getId());
            command.setInt(7, obj.getEstado().getId());
            command.setString(8, obj.getNumContribuente());
            command.setString(9, obj.getContacto());

            result = command.executeUpdate() > 0;

        } catch(MysqlDataTruncation ex){
             Logger.getLogger(CategoriaABController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) {
            Logger.getLogger(CategoriaABController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean update(FornecedorModel obj) {

        boolean result = false;
        try {
            String sql = "UPDATE `fornecedor`\n"
                    + "SET\n"

                    + "`Nome` = ?,\n"
                    + "`Email` = ?,\n"
                    + "`Localizacao` = ?,\n"
                    + "`Idtipofornecedor` = ?,\n"
                    + "`NumContribuente` = ?,Contacto = ?,IdEstado = ?\n"
                    + "WHERE `Id` = ?;";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getNome());
            command.setString(2, obj.getEmail());
            command.setString(3, obj.getLocalizacao());

            command.setInt(4, obj.getTipoFornecedor().getId());

            command.setString(5, obj.getNumContribuente());
            command.setString(6, obj.getContacto());
            command.setInt(7, obj.getEstado().getId());
            command.setInt(8, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(CategoriaABController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(FornecedorModel obj) {

        if (obj.getId() > 0) {
            return update(obj);
        }
        return save(obj);
    }

    @Override
    public FornecedorModel getById(int id) {
       
          FornecedorModel modelo = new FornecedorModel();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM fornecedor  f INNER JOIN tipofornecedor t ON f.Idtipofornecedor = t.Id"
                    + " WHERE f.IdEstado = 1 AND f.Id = "+id+";";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

              
                modelo.setNome(query.getString("f.Nome"));
                modelo.setContacto(query.getString("f.Contacto"));
                modelo.setEmail(query.getString("f.Email"));
                modelo.setEstado(new EstadoModel(query.getInt("f.IdEstado"), ""));
                modelo.setId(query.getInt("f.Id"));
                modelo.setLocalizacao(query.getString("Localizacao"));
                modelo.setNumContribuente(query.getString("f.NumContribuente"));
                modelo.setTipoFornecedor(new TipoFornecedorModel(query.getInt("t.Id"), "Designacao"));
                
            }

        } catch (Exception ex) {
            Logger.getLogger(CategoriaABController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return modelo;
    }

    @Override
    public List<FornecedorModel> get() {

        List<FornecedorModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM fornecedor  f INNER JOIN tipofornecedor t ON f.Idtipofornecedor = t.Id"
                    + " WHERE f.IdEstado = 1 ORDER BY 2;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                FornecedorModel modelo = new FornecedorModel();
                modelo.setNome(query.getString("f.Nome"));
                modelo.setContacto(query.getString("f.Contacto"));
                modelo.setEmail(query.getString("f.Email"));
                modelo.setEstado(new EstadoModel(query.getInt("f.IdEstado"), ""));
                modelo.setId(query.getInt("f.Id"));
                modelo.setLocalizacao(query.getString("Localizacao"));
                modelo.setNumContribuente(query.getString("f.NumContribuente"));
                modelo.setTipoFornecedor(new TipoFornecedorModel(query.getInt("t.Id"), "Designacao"));
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(CategoriaABController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public FornecedorModel getAll(String text) {

        FornecedorModel lista = new FornecedorModel();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM fornecedor WHERE NumContribuente = ?;";

            command = con.prepareCall(sql);
            command.setString(1, text);
            query = command.executeQuery();
            while (query.next()) {

                lista = new FornecedorModel();
                lista.setNome(query.getString("Nome"));
                lista.setContacto(query.getString("Contacto"));
                lista.setEmail(query.getString("Email"));
                lista.setEstado(new EstadoModel(query.getInt("IdEstado"), ""));
                lista.setId(query.getInt("Id"));
                lista.setNumContribuente(query.getString("NumContribuente"));
                lista.setTipoFornecedor(new TipoFornecedorModel(query.getInt("Idtipofornecedor"), ""));
            }

        } catch (Exception ex) {
            Logger.getLogger(CategoriaABController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public List<FornecedorModel> get(String text) {
       
         List<FornecedorModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM fornecedor  f INNER JOIN tipofornecedor t ON f.Idtipofornecedor = t.Id"
                    + " WHERE f.IdEstado = 1 and f.Nome like'%"+text+"%' ORDER BY f.Nome;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                FornecedorModel modelo = new FornecedorModel();
                modelo.setNome(query.getString("f.Nome"));
                modelo.setContacto(query.getString("f.Contacto"));
                modelo.setEmail(query.getString("f.Email"));
                modelo.setEstado(new EstadoModel(query.getInt("f.IdEstado"), ""));
                modelo.setId(query.getInt("f.Id"));
                modelo.setLocalizacao(query.getString("Localizacao"));
                modelo.setNumContribuente(query.getString("f.NumContribuente"));
                modelo.setTipoFornecedor(new TipoFornecedorModel(query.getInt("t.Id"), "Designacao"));
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(CategoriaABController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

}
