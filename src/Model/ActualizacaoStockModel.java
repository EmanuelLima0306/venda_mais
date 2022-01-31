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
public class ActualizacaoStockModel implements Serializable{
    
    private int id;
    private ProdutoModel produto;
    private ArmazemModel origem;
    private int existencia;
    private int novaExistencia;
    private String data;
    private UsuarioModel usuario;
            
}
