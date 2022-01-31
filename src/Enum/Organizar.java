/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enum;

/**
 *
 * @author celso
 */
public enum Organizar {
    
    CHEGADA("CHEGADA"),
    DATAEXPIRACAO("DATA EXPIRACAO");
    
    
    private String designacao;
    
    Organizar(String designacao){
        this.designacao = designacao;
    }
    
    
}
