/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author emanu
 */
public class CambioModel {
    
    private int codigo = 0;
    private Double valor;
    private Moeda moeda;

    public CambioModel() {
    }

    public CambioModel(Double valor, Moeda moeda) {
        this.valor = valor;
        this.moeda = moeda;
    }

    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    @Override
    public String toString() {
        return ""+valor;
    }
    
    
    
    
}
