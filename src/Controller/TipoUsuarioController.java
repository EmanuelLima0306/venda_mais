/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ParamentroModel;
import Model.TipoUsuarioModel;
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
 * @author celso
 */
public class TipoUsuarioController implements IController<TipoUsuarioModel> {

    @Override
    public boolean save(TipoUsuarioModel obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(TipoUsuarioModel obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean saveOrUpdate(TipoUsuarioModel obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TipoUsuarioModel getById(int id) {

        TipoUsuarioModel modelo = null;
          ConnectionFactoryController connection = new ConnectionFactoryController();
          Connection con = null;
          PreparedStatement command = null;
           ResultSet query = null;
        try {
            
          

            String sql = "SELECT * FROM tipousuario WHERE Id = "+id;

             con = connection.open();//ABRIR CONEXAO COM O SERVIDOR DE BD
             command = con.prepareStatement(sql);

             query = command.executeQuery();
            while (query.next()) {

                modelo = new TipoUsuarioModel(query.getInt(1), query.getString(2));

            }

        } catch (SQLException ex) {
            Logger.getLogger(TipoUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connection.close(con, command,query);
        }
        return modelo;
    }

    @Override
    public List<TipoUsuarioModel> get() {

        List<TipoUsuarioModel> lista = new ArrayList();
        try {
            ConnectionFactoryController connection = new ConnectionFactoryController();
            
            ParamentroController paramentroController = new ParamentroController();
            ParamentroModel paramentroModel = paramentroController.getById(3);
            
            String sql;
            if(paramentroModel.getValor() != 3)
                sql = "SELECT * FROM tipousuario where Id not in (3,4)";
            else
                sql = "SELECT * FROM tipousuario where Id not in (4)";

            Connection con = connection.open();//ABRIR CONEXAO COM O SERVIDOR DE BD
            PreparedStatement command = con.prepareStatement(sql);

            ResultSet query = command.executeQuery();
            while (query.next()) {

                TipoUsuarioModel modelo = new TipoUsuarioModel(query.getInt(1), query.getString(2));
                lista.add(modelo);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TipoUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

}
