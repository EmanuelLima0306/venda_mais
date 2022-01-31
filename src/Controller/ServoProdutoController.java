/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.AnoModel;
import Model.ArmazemModel;
import Model.ProdutoModel;
import Model.ServicoModel;
import Model.ServicoProdutoModel;
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
public class ServoProdutoController implements IController<ServicoProdutoModel> {

     private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;
    @Override
    public boolean save(ServicoProdutoModel obj) {
        
        boolean result = false;
        try {
            String sql = "INSERT INTO `servicoproduto`\n"
                    + "("
                    + "`IdServico`, `IdProduto`)"
                    + "VALUES"
                    + "(?, ?)";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, obj.getServico().getId());
            command.setInt(2, obj.getProduto().getId());
            
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
        }
        return result;
    }

    @Override
    public boolean update(ServicoProdutoModel obj) {
    
        boolean result = false;
        try {
            String sql = "   UPDATE servicoproduto "
                    + "   SET"
                    + "   IdServico = ? "
                    + "   WHERE IdProduto = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, obj.getServico().getId());
            command.setInt(2, obj.getProduto().getId());
            
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    
    }
    
    public boolean update(ServicoProdutoModel novoObj, ServicoProdutoModel antigoObj) {
    
        boolean result = false;
        try {
            String sql = "   UPDATE servicoproduto "
                    + "   SET"
                    + "   IdServico = ?, IdProduto = ? "
                    + "   WHERE IdServico = ? AND  IdProduto = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, novoObj.getServico().getId());
            command.setInt(2, novoObj.getProduto().getId());
            
            command.setInt(3, antigoObj.getServico().getId());
            command.setInt(4, antigoObj.getProduto().getId());
            
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    
    }

    @Override
    public boolean saveOrUpdate(ServicoProdutoModel obj) {
    
        if(update(obj))
            return true;
        else
            return save(obj);
    
    }

    @Override
    public ServicoProdutoModel getById(int id) {
        
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM servicoproduto"
                    + " WHERE Id = ? ORDER BY 2;";

            command = con.prepareCall(sql);
            command.setInt(1, id);
            
            query = command.executeQuery();
            if (query.next()) {

//                return (new AnoModel(query.getInt("Codigo"), query.getInt("Ano")));
            }

        } catch (Exception ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return null;
    
    }
    public ServicoProdutoModel getByServicoProduto(ServicoModel servico, ProdutoModel produto) {
        
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM servicoproduto"
                    + " WHERE IdServico = ? AND IdProduto ORDER BY 2;";

            command = con.prepareCall(sql);
            command.setInt(1, servico.getId());
            command.setInt(2, produto.getId());
            
            query = command.executeQuery();
            if (query.next()) {

                return (new ServicoProdutoModel(query.getInt("Id"),
                        new ServicoController().getById(query.getInt("IdServico")),
                        new ProdutoController().getById(query.getInt("IdProduto")) ));
            }

        } catch (Exception ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return null;
    
    }

    @Override
    public List<ServicoProdutoModel> get() {
    
        List<ServicoProdutoModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM ano"
                    + " WHERE Codigo = 1 ORDER BY 2;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

//                lista.add(new AnoModel(query.getInt("Codigo"), query.getInt("Ano")));
            }

        } catch (Exception ex) {
            Logger.getLogger(ArmazemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    
    }
    
    
    
    
}
