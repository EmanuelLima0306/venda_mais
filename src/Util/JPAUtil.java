/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ao.ascemil_tecnologias.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author celso
 */
public class JPAUtil {
    
    private static EntityManagerFactory factory;
    static {
        factory =   Persistence.createEntityManagerFactory("cursoJPAPU");
    }
    
    public static EntityManager getEntityManager(){
        
        return factory.createEntityManager();
    }
    
    public static void close(EntityManager entity){
        factory.close();
        entity.close();
    }
}
