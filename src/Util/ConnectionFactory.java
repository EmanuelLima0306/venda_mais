/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author celso
 */
public class ConnectionFactory {
    
    private static EntityManagerFactory factory = null;
    
    public static EntityManager getEntityManager(){
        
        factory = Persistence.createEntityManagerFactory("cursoJPAPU");
        return factory.createEntityManager();
    }
    
    public static void close(){
        
        if(factory != null){
            factory.close();
        }
    }
}
