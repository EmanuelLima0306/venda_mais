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

public class Motivo implements Serializable {

  
    private Integer id;
    private String descricao;

    private String codigo;
    private EstadoModel status;

    public Motivo() {
    }

    public Motivo(Integer id, String descricao, String codigo) {
        this.id = id;
        this.descricao = descricao;
        this.codigo = codigo;
    }

    public Motivo(Integer id, String descricao, String codigo, EstadoModel status) {
        this.id = id;
        this.descricao = descricao;
        this.codigo = codigo;
        this.status = status;
    }

    
    
    public EstadoModel getEstadoModel() {
        return status;
    }

    public void setEstadoModel(EstadoModel status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public boolean isEmpty() {
        return descricao.isEmpty() || codigo.isEmpty();
    }

    @Override
    public String toString() {
        return  "["+codigo+"]"+descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EstadoModel getEstado() {
        return status;
    }

    public void setEstado(EstadoModel status) {
        this.status = status;
    }
    
    
}
