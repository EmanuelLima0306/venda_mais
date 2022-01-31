/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.AnoModel;
import Model.ArmazemModel;
import Model.EstadoModel;
import Model.ProdutoModel;
import Model.ServicoModel;
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
 * @author emanuellima
 */
public class ServicoController implements IController<ServicoModel> {

     private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;
    @Override
    public boolean save(ServicoModel obj) {
        
        boolean result = false;
        try {
            String sql = "INSERT INTO `servico`\n"
                    + "("
                    + "`Descricao`, `IdEstado`)"
                    + "VALUES"
                    + "(?,?)";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getDescricao());
            command.setInt(1, obj.getEstado().getId());
            
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
        }
        return result;
    }

    @Override
    public boolean update(ServicoModel obj) {
    
        boolean result = false;
        try {
            String sql = "   UPDATE servico"
                    + "   SET"
                    + "   Descricao = ?, IdEstado = ?"
                    + "   WHERE Codigo = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getDescricao());
            command.setInt(2, obj.getEstado().getId());
            command.setInt(3, obj.getId());
            
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    
    }

    @Override
    public boolean saveOrUpdate(ServicoModel obj) {
    
        if(obj.getId() > 0)
            return update(obj);
        else
            return save(obj);
    
    }

    @Override
    public ServicoModel getById(int id) {
        
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM servico"
                    + " WHERE Id = ? ORDER BY 2;";

            command = con.prepareCall(sql);
            command.setInt(1, id);
            
            query = command.executeQuery();
            if (query.next()) {

                return (new ServicoModel(query.getInt("Id"), query.getString("Descricao"),new EstadoController().getById(query.getInt("IdEstado"))));
            }

        } catch (Exception ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return null;
    
    }
    public ServicoModel getByProduto(ProdutoModel produto) {
        
        try {

            con = conFactory.open();
            String sql = " SELECT s.Id, s.Descricao, s.IdEstado "
                    + " FROM servico s INNER JOIN servicoproduto sp ON s.Id = sp.IdServico"
                    + " WHERE sp.IdProduto = ? ORDER BY 2;";

            command = con.prepareCall(sql);
            command.setInt(1, produto.getId());
            
            query = command.executeQuery();
            if (query.next()) {

                return (new ServicoModel(query.getInt("Id"), query.getString("Descricao"),new EstadoController().getById(query.getInt("IdEstado"))));
            }

        } catch (Exception ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return null;
    
    }

    @Override
    public List<ServicoModel> get() {
    
        List<ServicoModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM ano"
                    + " WHERE Codigo = 1 ORDER BY 2;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                lista.add(new ServicoModel(query.getInt("Id"), query.getString("Descricao"),new EstadoController().getById(query.getInt("IdEstado"))));
            }

        } catch (Exception ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    
    }
    public ServicoModel getLast() {
        
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM ano"
                    + " WHERE Codigo = 1 ORDER BY 2;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.last()) {

               return (new ServicoModel(query.getInt("Id"), query.getString("Descricao"),new EstadoController().getById(query.getInt("IdEstado"))));
            }

        } catch (Exception ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return null;
    
    }
    
    
    
}
