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
public class TipoRegimeModel   implements Serializable{
    
    private int id;
    private String designacao;
    private boolean cobraImposto;

    public TipoRegimeModel(int id, String designacao, boolean cobraImposto) {
        this.id = id;
        this.designacao = designacao;
        this.cobraImposto = cobraImposto;
    }
  

   

    

    public TipoRegimeModel() {
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

    public boolean isCobraImposto() {
        return cobraImposto;
    }

    public void setCobraImposto(boolean cobraImposto) {
        this.cobraImposto = cobraImposto;
    }

    

    @Override
    public String toString() {
        return  designacao ;
    }

    public boolean isEmpty() {
        
        return designacao.isEmpty();
    }
    
    
    
}
