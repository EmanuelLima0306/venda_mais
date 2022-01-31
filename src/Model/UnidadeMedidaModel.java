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
public class UnidadeMedidaModel   implements Serializable{
    
    private int id;
    private String designacao;
    private int item;
    private EstadoModel estado;

    public UnidadeMedidaModel(int id, String designacao, int item) {
        this.id = id;
        this.designacao = designacao;
        this.item = item;
     
    }

    public UnidadeMedidaModel(int id, String designacao, int item, EstadoModel estado) {
        this.id = id;
        this.designacao = designacao;
        this.item = item;
        this.estado = estado;
    }

    

    public UnidadeMedidaModel(int id, String designacao) {
        this.id = id;
        this.designacao = designacao;
    }

    public UnidadeMedidaModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public EstadoModel getEstado() {
        return estado;
    }

    public void setEstado(EstadoModel estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return  designacao ;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public boolean isEmpty() {
        
        return designacao.isEmpty();
    }
    
    
    
}
