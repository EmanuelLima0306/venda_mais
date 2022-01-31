/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.List;



/**
 *
 * @author celso
 */
public interface IController<T> {
    
    boolean save(T obj);
    boolean update(T obj);
    boolean saveOrUpdate(T obj);
    T getById(int id);
    List<T> get();
}
