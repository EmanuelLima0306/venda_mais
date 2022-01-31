/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Enum.TipoLog;

/**
 *
 * @author celso
 */
public class LogModel {
    private int id;
    private TipoLog designacao;
    private String descricao;
    private UsuarioModel Usuario;
    private String data;

    public LogModel(TipoLog designacao, String descricao, UsuarioModel Usuario, String data) {
        this.designacao = designacao;
        this.descricao = descricao;
        this.Usuario = Usuario;
        this.data = data;
    }

    public LogModel(int id, TipoLog designacao, String descricao, UsuarioModel Usuario, String data) {
        this.id = id;
        this.designacao = designacao;
        this.descricao = descricao;
        this.Usuario = Usuario;
        this.data = data;
    }

    public LogModel() {
    }
    
    

    public TipoLog getDesignacao() {
        return designacao;
    }

    public void setDesignacao(TipoLog designacao) {
        this.designacao = designacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public UsuarioModel getUsuario() {
        return Usuario;
    }

    public void setUsuario(UsuarioModel Usuario) {
        this.Usuario = Usuario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
}
