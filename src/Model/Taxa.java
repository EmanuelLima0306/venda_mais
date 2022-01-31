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

public class Taxa   implements Serializable {

   
  
   private Integer id;
    private String descricao;
    private Double taxa;
    private EstadoModel status;

    public EstadoModel getEstado() {
        return status;
    }

    public Taxa(Integer id, String descricao, Double taxa, EstadoModel status) {
        this.id = id;
        this.descricao = descricao;
        this.taxa = taxa;
        this.status = status;
    }

    public Taxa() {
    }

    public Taxa(Integer id, String descricao, Double taxa) {
        this.id = id;
        this.descricao = descricao;
        this.taxa = taxa;
    }

    public void setEstado(EstadoModel status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getTaxa() {
        return taxa;
    }

    public void setTaxa(Double taxa) {
        this.taxa = taxa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EstadoModel getStatus() {
        return status;
    }

    public void setStatus(EstadoModel status) {
        this.status = status;
    }

 
public boolean isEmpty(){
    return descricao.isEmpty();
}
    @Override
    public String toString() {
        return  taxa+" - "+descricao;
    }
    
    
}
