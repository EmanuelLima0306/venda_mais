/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ConnectioFactoryModel;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author celso
 */
public class Controller {

    private String arquivo = "Connection";
    private ConnectioFactoryModel connection;

    public Controller() {

        try {
            ArmazenamentoController<ConnectioFactoryModel> ficheiro = new ArmazenamentoController<>(arquivo);
            
            List<ConnectioFactoryModel> lista = ficheiro.getAll();
            if( lista.size() > 0){
                connection = lista.get(0);
            }
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    public ConnectioFactoryModel getPath(){
        return connection;
    }
}
