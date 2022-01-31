/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;



import Util.GenericId;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author celso
 */

public class Moeda implements Serializable {

  
    private Integer id;
    private String designacao;
    private Double valor;
    private CambioModel cambioDiario;

    public Moeda() {
    }

    public Moeda(Integer id, String designacao) {
        this.id = id;
        this.designacao = designacao;
    }
  
    


    @Override
    public String toString() {
        return  designacao;
    }

    public String getDesignacao() {
        return designacao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CambioModel getCambioDiario() {
        return cambioDiario;
    }

    public void setCambioDiario(CambioModel cambioDiario) {
        this.cambioDiario = cambioDiario;
    }

   
    
    
}
