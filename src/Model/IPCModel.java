/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;

/**
 *
 * @author celso
 */
public class IPCModel  implements Serializable{
    
    private int id;
    private String valor;

    public IPCModel(int id, String valor) {
        this.id = id;
        this.valor = valor;
    }

    public IPCModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return  valor ;
    }

    public boolean isEmpty() {
        return valor.isEmpty();
    }
    
    
            
}
