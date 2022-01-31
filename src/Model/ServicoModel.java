/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Mac
 */
public class ServicoModel {

    private int id;
    private String descricao;
    private EstadoModel estado;
    
    public ServicoModel() {
    }

    public ServicoModel(int id, String descricao, EstadoModel estado) {
        this.id = id;
        this.descricao = descricao;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public EstadoModel getEstado() {
        return estado;
    }

    public void setEstado(EstadoModel estado) {
        this.estado = estado;
    }
    
    
    
}
