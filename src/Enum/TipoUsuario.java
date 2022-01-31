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
public enum TipoUsuario {
    
    ADMINISTRADOR("ADMINISTRADOR"),
    OPERADORCAIXA("OPERADOR CAIXA"),
    GESTORSTOCK("GESTOR STOCK");
    
    private String designacao;
    
    TipoUsuario(String designacao){
        this.designacao = designacao;
    }
    
    
}
