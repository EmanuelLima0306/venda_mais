/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.EstadoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author celso
 */
public class EstadoController implements IController<EstadoModel>{

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;
    
    
    @Override
    public boolean save(EstadoModel obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(EstadoModel obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean saveOrUpdate(EstadoModel obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EstadoModel getById(int id) {

        EstadoModel modelo = null;
        try {

            con = conFactory.open();
            String sql = " SELECT * FROM estado c where "
                    + "  c.Id = " + id + " ";

            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {

                modelo = new EstadoModel();
                modelo.setId(query.getInt("c.Id"));
                modelo.setDesignacao(query.getString("c.Designacao"));

                return modelo;
            }

        } catch (Exception ex) {
            Logger.getLogger(CaixaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //conFactory.close(con, command, query);
        }
        return modelo;
    }

    @Override
    public List<EstadoModel> get() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
