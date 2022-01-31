/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ConnectioFactoryModel;
import Util.ConnectionFactor;
import com.mysql.jdbc.CommunicationsException;
import java.io.IOException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author celso
 */
public class ConnectionFactoryController implements Serializable {

    private String arquivo = "Connection";
    private ConnectioFactoryModel modelo;
    private ArmazenamentoController<ConnectioFactoryModel> ficheiro;
    public static Connection con = null;
    public ConnectionFactoryController() {
        
//        try {
//                ficheiro = new ArmazenamentoController<ConnectioFactoryModel>(arquivo);
//                modelo = ficheiro.getAll().get(0);
//                
//                
//        } catch (IOException ex) {
//            Logger.getLogger(ConnectionFactoryController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public Connection open() {
//            if(ConnectionFactor.conexao == null)
//                System.out.println(">><<>>>>>> sem conexao");
//            else
//                System.out.println(">><<>>>>>> tem conexao");
                return ConnectionFactor.conexao;
                
    }
    public Connection openConection() {

        try {
            
                try {
                    ficheiro = new ArmazenamentoController<ConnectioFactoryModel>(arquivo);
                    modelo = ficheiro.getAll().get(0);
                
                
                } catch (IOException ex) {
                    Logger.getLogger(ConnectionFactoryController.class.getName()).log(Level.SEVERE, null, ex);
                }
                ficheiro = new ArmazenamentoController<ConnectioFactoryModel>(arquivo);
                modelo = ficheiro.getAll().get(0);
                Class.forName(modelo.getDriver());

                List<ConnectioFactoryModel> connectionFact = ficheiro.getAll();
                modelo = connectionFact.get(0);
                return DriverManager.getConnection(modelo.getUrl(), modelo.getUser(), modelo.getPassword());
                
        } //        catch(CommunicationsException ex){
        //            JOptionPane.showMessageDialog(null, "Aplicação não está a consegui se comunicar com servidor de informação","BASE DE DADO",JOptionPane.ERROR_MESSAGE);
        //        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionFactoryController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getCause().getMessage(), "BASE DE DADO", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactoryController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getCause().getMessage(), "BASE DE DADO", JOptionPane.ERROR_MESSAGE);
        } catch (StreamCorruptedException ex) {
            Logger.getLogger(ConnectionFactoryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionFactoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public void getConection() {
        ConnectionFactor.conexao = openConection();
        System.out.println(">><<>>>>>> conexao aberta");
    }

    public boolean close(Connection con) {

        if (con != null) {

            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionFactoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }
        return false;
    }

    public boolean close(Connection con, PreparedStatement command) {

        try {

            if (con != null) {

                ////con.close();
            }
            if (command != null) {

                command.close();
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }
//    public boolean close(ConnectionFactor con, PreparedStatement command) {
//
//        if (command != null) {
//
//            try {
//                command.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(ConnectionFactoryController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            return true;
//        }
//        close(con);
//        return false;
//    }

    public boolean close(Connection con, PreparedStatement command, ResultSet query) {

        try {
            if (query != null) {

                query.close();
            }
            if (con != null) {

                ////con.close();
            }
            if (command != null) {

                command.close();
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }
//    public boolean close(ConnectionFactor con,PreparedStatement command,ResultSet query) {
//        
//        if( query != null){
//            
//            try {
//                query.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(ConnectionFactoryController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            return true;
//        }
//        close(con,command);
//        return false;
//    }
}
