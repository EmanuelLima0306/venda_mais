/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author celso de sousa
 */
public class ParamentroModel {
    
    private int id;
    private String descricao;
    private int valor;

    public ParamentroModel(int id, String descricao, int valor) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
    }

    public ParamentroModel() {
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

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
    
    
    
}
