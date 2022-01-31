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
public class ArmazemModel implements Serializable{
    
    private int id;
    private String designacao;
    private String localizacao;
    private EstadoModel estado;
    private String data;

    public ArmazemModel(int id, String designacao, String localizacao, EstadoModel estado) {
        this.id = id;
        this.designacao = designacao;
        this.localizacao = localizacao;
        this.estado = estado;
    }

    public ArmazemModel(String designacao, String localizacao, EstadoModel estado) {
        this.designacao = designacao;
        this.localizacao = localizacao;
        this.estado = estado;
    }

    public ArmazemModel(int id) {
        this.id = id;
    }

    public ArmazemModel() {
       
        
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

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public EstadoModel getEstado() {
        return estado;
    }

    public void setEstado(EstadoModel estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return  designacao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isEmpty() {
        
        return designacao.isEmpty() || localizacao.isEmpty();
    }
    
    
    
}
