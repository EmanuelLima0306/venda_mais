/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author celso
 */
public class CaixaModel {
    
    private Integer id = 0;
    private UsuarioModel usuario;
    private String dataAbertura;
    private String dataFecho;
    private Double valorInicial = 0.0;
    private String estado = "Aberto";

    public CaixaModel(Integer id, UsuarioModel usuario, String dataAbertura, String dataFecho) {
        this.id = id;
        this.usuario = usuario;
        this.dataAbertura = dataAbertura;
        this.dataFecho = dataFecho;
    }

    public CaixaModel(UsuarioModel usuario, String dataAbertura, String dataFecho) {
        this.usuario = usuario;
        this.dataAbertura = dataAbertura;
        this.dataFecho = dataFecho;
    }

    public CaixaModel(UsuarioModel usuario, String dataAbertura) {
        this.usuario = usuario;
        this.dataAbertura = dataAbertura;
    }

    public CaixaModel() {
    }

    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public String getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(String dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public String getDataFecho() {
        return dataFecho;
    }

    public void setDataFecho(String dataFecho) {
        this.dataFecho = dataFecho;
    }

    public Double getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(Double valorInicial) {
        this.valorInicial = valorInicial;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
    
}
