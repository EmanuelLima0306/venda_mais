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
public class SerieModel {
    
    private int codigo = 0;
    private String designacao;
    private AnoModel ano;
    private int status = 0;

    public SerieModel() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public AnoModel getAno() {
        return ano;
    }

    public void setAno(AnoModel ano) {
        this.ano = ano;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return designacao + "" + ano.getAno();
    }
    
    
    
}
