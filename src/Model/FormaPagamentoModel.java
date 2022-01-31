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
public class FormaPagamentoModel implements Serializable{
    
    private int id;
    private String designacao;
    private boolean cash;
    private boolean multicaixa;
    private EstadoModel estado;

    public FormaPagamentoModel(int id, String designacao, boolean cash, boolean multicaixa, EstadoModel estado) {
        this.id = id;
        this.designacao = designacao;
        this.cash = cash;
        this.multicaixa = multicaixa;
        this.estado = estado;
    }

    public FormaPagamentoModel(String designacao, boolean cash, boolean multicaixa, EstadoModel estado) {
        this.designacao = designacao;
        this.cash = cash;
        this.multicaixa = multicaixa;
        this.estado = estado;
    }

    public EstadoModel getEstado() {
        return estado;
    }

    public void setEstado(EstadoModel estado) {
        this.estado = estado;
    }

    public FormaPagamentoModel(int id) {
        this.id = id;
    }

    

    public FormaPagamentoModel() {
    }

    public boolean isCash() {
        return cash;
    }

    public void setCash(boolean cash) {
        this.cash = cash;
    }

    public boolean isMulticaixa() {
        return multicaixa;
    }

    public void setMulticaixa(boolean multicaixa) {
        this.multicaixa = multicaixa;
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

    @Override
    public String toString() {
        return  designacao;
    }

    public boolean isEmpty() {
      
        return designacao.isEmpty();
    }
    
    
    
}
