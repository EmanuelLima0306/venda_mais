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
public class FabricanteModel   implements Serializable{
    
    private int id;
    private String designacao;
    private EstadoModel estado;

    public FabricanteModel(int id, String designacao, EstadoModel estado) {
        this.id = id;
        this.designacao = designacao;
        this.estado = estado;
    }

    public FabricanteModel(int id, String designacao) {
        this.id = id;
        this.designacao = designacao;
    }

    public FabricanteModel() {
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

    public boolean isEmpty() {
       
        return designacao.isEmpty();
    }
    
    
    
}
