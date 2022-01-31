/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.BarbeiroItemModel;
import Model.EstadoModel;
import Model.FacturaItemModel;
import Model.TipoUsuarioModel;
import Model.UsuarioModel;
import java.awt.Menu;
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
public class BarbeiroItemController implements IController<BarbeiroItemModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(BarbeiroItemModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `barbeiroItem`\n"
                    + "("
                    + "`IdFacturaItem`,\n"
                    + "`IdBarbeiro`,\n"
                    + "`QuantidadeItem`,\n"
                    + "`Taxa`,\n"
                    + "`ValorRemuneracao`, IdEstado)\n"
                    + "VALUES\n"
                    + "("
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?);";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, obj.getFacturaItemModel().getId());
            command.setInt(2, obj.getUsuarioModel().getId());
            command.setDouble(3, obj.getQuantidade());
            command.setDouble(4, obj.getTaxa());
            command.setDouble(5, obj.getValorRemuneracao());
            command.setInt(6, obj.getEstadoModel().getId());

            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(BarbeiroItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        
        
        return result;
    }

    @Override
    public boolean update(BarbeiroItemModel obj) {

        boolean result = false;
        try {
                    
            String sql = "UPDATE `barbeiroItem`\n"
                    + " SET "
                    + "`IdFacturaItem` = ?,\n"
                    + "`IdBarbeiro` = ?,\n"
                    + "`QuantidadeItem` = ?,\n"
                    + "`Taxa` = ?,\n"
                    + "`ValorRemuneracao` = ?,\n"
                    + "`IdEstado` = ? WHERE Id = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, obj.getFacturaItemModel().getId());
            command.setInt(2, obj.getUsuarioModel().getId());
            command.setDouble(3, obj.getQuantidade());
            command.setDouble(4, obj.getTaxa());
            command.setDouble(5, obj.getValorRemuneracao());
            command.setInt(6, obj.getEstadoModel().getId());
            
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(BarbeiroItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return result;
    }

    public boolean updateEstado(BarbeiroItemModel obj) {

        boolean result = false;
        try {
            String sql = "UPDATE `barbeiroItem`\n"
                    + " SET "
                    + "`IdEstado` = ?\n"
                    + " WHERE Id = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);

            command.setInt(1, obj.getEstadoModel().getId());

            command.setInt(2, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(BarbeiroItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(BarbeiroItemModel obj) {

        return obj.getId() > 0 ? update(obj) : save(obj);
    }

    @Override
    public BarbeiroItemModel getById(int id) {
        
                BarbeiroItemModel barbeiroItemModel = null;
        try {
                String sql = "SELECT * FROM barbeiroItem WHERE id = ?";

                con = conFactory.open();//ABRIR CONEXAO COM O SERVIDOR DE BD
                 command = con.prepareStatement(sql);
                command.setInt(1, id);


                 query = command.executeQuery();
                if (query.next()) {

                    barbeiroItemModel = new BarbeiroItemModel();
                    barbeiroItemModel.setEstadoModel(new EstadoModel(query.getInt("IdEstado"), ""));
                    
                    FacturaItemController item = new FacturaItemController();
                    barbeiroItemModel.setFacturaItemModel(item.getById(query.getInt("IdFacturaItem")));
                    
                    UsuarioController barbeiro = new UsuarioController();
                    barbeiroItemModel.setUsuarioModel(barbeiro.getById(query.getInt("IdBarbeiro")));
                     
                    barbeiroItemModel.setId(query.getInt("Id"));
                    barbeiroItemModel.setQuantidade(query.getDouble("QuantidadeItem"));
                    barbeiroItemModel.setTaxa(query.getDouble("Taxa"));
                    barbeiroItemModel.setValorRemuneracao(query.getDouble("ValorRemuneracao"));
                }
            } catch (SQLException ex) {
            Logger.getLogger(BarbeiroItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }    
//        ////conFactory.close(con, command, query);

        return barbeiroItemModel;
    
    }

    @Override
    public List<BarbeiroItemModel> get() {

        List<BarbeiroItemModel> lista = new ArrayList();
        try {

            String sql = "SELECT * FROM barbeiroItem ";

                con = conFactory.open();//ABRIR CONEXAO COM O SERVIDOR DE BD
                 command = con.prepareStatement(sql);


                 query = command.executeQuery();
                while (query.next()) {

                    BarbeiroItemModel barbeiroItemModel = new BarbeiroItemModel();
                    barbeiroItemModel.setEstadoModel(new EstadoModel(query.getInt("IdEstado"), ""));
                    
                    FacturaItemController item = new FacturaItemController();
                    barbeiroItemModel.setFacturaItemModel(item.getById(query.getInt("IdFacturaItem")));
                    
                    UsuarioController barbeiro = new UsuarioController();
                    barbeiroItemModel.setUsuarioModel(barbeiro.getById(query.getInt("IdBarbeiro")));
                     
                    barbeiroItemModel.setId(query.getInt("Id"));
                    barbeiroItemModel.setQuantidade(query.getDouble("QuantidadeItem"));
                    barbeiroItemModel.setTaxa(query.getDouble("Taxa"));
                    barbeiroItemModel.setValorRemuneracao(query.getDouble("ValorRemuneracao"));
                    lista.add(barbeiroItemModel);
                }

        } catch (SQLException ex) {
            Logger.getLogger(BarbeiroItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }
    public List<BarbeiroItemModel> getByItem(FacturaItemModel facturaItemModel) {

        List<BarbeiroItemModel> lista = new ArrayList();
        try {

            String sql = "SELECT * FROM barbeiroitem WHERE IdFacturaItem = ?";

                con = conFactory.open();//ABRIR CONEXAO COM O SERVIDOR DE BD
                 command = con.prepareStatement(sql);
                 command.setInt(1, facturaItemModel.getId());

                 query = command.executeQuery();
                while (query.next()) {

                    BarbeiroItemModel barbeiroItemModel = new BarbeiroItemModel();
                    barbeiroItemModel.setEstadoModel(new EstadoModel(query.getInt("IdEstado"), ""));
                    
                    FacturaItemController item = new FacturaItemController();
                    barbeiroItemModel.setFacturaItemModel(item.getById(query.getInt("IdFacturaItem")));
                    
                    UsuarioController barbeiro = new UsuarioController();
                    barbeiroItemModel.setUsuarioModel(barbeiro.getById(query.getInt("IdBarbeiro")));
                     
                    barbeiroItemModel.setId(query.getInt("Id"));
                    barbeiroItemModel.setQuantidade(query.getDouble("QuantidadeItem"));
                    barbeiroItemModel.setTaxa(query.getDouble("Taxa"));
                    barbeiroItemModel.setValorRemuneracao(query.getDouble("ValorRemuneracao"));
                    lista.add(barbeiroItemModel);
                }

        } catch (SQLException ex) {
            Logger.getLogger(BarbeiroItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }
    public List<BarbeiroItemModel> getByItem(FacturaItemModel facturaItemModel,Connection con) {

        List<BarbeiroItemModel> lista = new ArrayList();
        try {

            String sql = "SELECT * FROM barbeiroitem WHERE IdFacturaItem = ?";

               
                 command = con.prepareStatement(sql);
                 command.setInt(1, facturaItemModel.getId());

                 query = command.executeQuery();
                while (query.next()) {

                    BarbeiroItemModel barbeiroItemModel = new BarbeiroItemModel();
                    barbeiroItemModel.setEstadoModel(new EstadoModel(query.getInt("IdEstado"), ""));
                    
                    FacturaItemController item = new FacturaItemController();
                    barbeiroItemModel.setFacturaItemModel(item.getById(query.getInt("IdFacturaItem")));
                    
                    UsuarioController barbeiro = new UsuarioController();
                    barbeiroItemModel.setUsuarioModel(barbeiro.getById(query.getInt("IdBarbeiro")));
                     
                    barbeiroItemModel.setId(query.getInt("Id"));
                    barbeiroItemModel.setQuantidade(query.getDouble("QuantidadeItem"));
                    barbeiroItemModel.setTaxa(query.getDouble("Taxa"));
                    barbeiroItemModel.setValorRemuneracao(query.getDouble("ValorRemuneracao"));
                    lista.add(barbeiroItemModel);
                }

        } catch (SQLException ex) {
            Logger.getLogger(BarbeiroItemController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return lista;
    }

    

}
