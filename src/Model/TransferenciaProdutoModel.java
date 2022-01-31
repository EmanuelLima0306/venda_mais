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
public class TransferenciaProdutoModel implements Serializable{
    
    private int id;
    private ProdutoModel produto;
    private ArmazemModel origem;
    private ArmazemModel destino;
    private int existencia;
    private int existenciaTransferir;
    private String data;
    private UsuarioModel usuario;

    public TransferenciaProdutoModel(int id, ProdutoModel produto, ArmazemModel origem, ArmazemModel destino, int existencia, int existenciaTransferir, String data, UsuarioModel usuario) {
        this.id = id;
        this.produto = produto;
        this.origem = origem;
        this.destino = destino;
        this.existencia = existencia;
        this.existenciaTransferir = existenciaTransferir;
        this.data = data;
        this.usuario = usuario;
    }

    public TransferenciaProdutoModel(ProdutoModel produto, ArmazemModel origem, ArmazemModel destino, int existencia, int existenciaTransferir, String data, UsuarioModel usuario) {
        this.produto = produto;
        this.origem = origem;
        this.destino = destino;
        this.existencia = existencia;
        this.existenciaTransferir = existenciaTransferir;
        this.data = data;
        this.usuario = usuario;
    }

    public TransferenciaProdutoModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProdutoModel getProduto() {
        return produto;
    }

    public void setProduto(ProdutoModel produto) {
        this.produto = produto;
    }

    public ArmazemModel getOrigem() {
        return origem;
    }

    public void setOrigem(ArmazemModel origem) {
        this.origem = origem;
    }

    public ArmazemModel getDestino() {
        return destino;
    }

    public void setDestino(ArmazemModel destino) {
        this.destino = destino;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public int getExistenciaTransferir() {
        return existenciaTransferir;
    }

    public void setExistenciaTransferir(int existenciaTransferir) {
        this.existenciaTransferir = existenciaTransferir;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }
    
    
    
            
}
