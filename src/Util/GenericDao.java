/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;




import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


/**
 *
 * @author celso
 * @param <T>
 */
public abstract class GenericDao<T> {

    public EntityManager entity;

//    public GenericDao(String nameTable) {
//        this.nameTable = nameTable;
//    }

    public boolean save(T obj) throws Exception {

      
        try {
            this.entity = ConnectionFactory.getEntityManager();
            EntityTransaction transaction = this.entity.getTransaction();
            transaction.begin();

            this.entity .persist(obj);
            transaction.commit();
            this.entity .close();
            ConnectionFactory.close();

            return true;
        } catch (Exception ex) {
            this.entity .getTransaction().rollback();
            throw ex;
        }

    }

    public boolean update(T obj) throws Exception {

        try {
            this.entity = ConnectionFactory.getEntityManager();
            EntityTransaction transaction = this.entity .getTransaction();
            transaction.begin();
            this.entity .merge(obj);
            transaction.commit();
            this.entity .close();
            ConnectionFactory.close();
            return true;
        } catch (Exception ex) {
            throw ex;
        }
    }


//    public T getById(Long id) throws Exception {
//
//        
//        try {
//            this.entity = ConnectionFactory.getEntityManager();
//            T modelo = this.entity.find(T.class, id);
//            this.entity .close();
//            return modelo;
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }

//    public List<T> getAll() throws Exception {
//
//        try {
//            List<T> lista = new ArrayList();
//            this.entity = ConnectionFactory.getEntityManager();
//            if (entity != null) {
//                Query query = this.entity .createQuery("from "+this.nameTable, T.class);
//
//                lista = query.getResultList();
//                this.entity .close();
//                ConnectionFactory.close();
//            }
//            return lista;
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }

    public boolean delete(T obj) {
        try {
            this.entity = ConnectionFactory.getEntityManager();
            EntityTransaction transaction = this.entity .getTransaction();
            transaction.begin();
            this.entity .remove(obj);
            transaction.commit();
            this.entity .close();
            ConnectionFactory.close();
            return true;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public EntityManager getEntity() {
        return entity;
    }

    public void setEntity(EntityManager entity) {
        this.entity = entity;
    }

  

}
