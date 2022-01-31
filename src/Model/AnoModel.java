/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author emanuellima
 */
public class AnoModel {
    
    private int codigo = 0;
    private int ano = 0;

    public AnoModel() {
    }

    public AnoModel(int codigo, int ano) {
        this.codigo = codigo;
        this.ano = ano;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    @Override
    public String toString() {
        return "" + ano;
    }
    
    
    
}
