/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author emanuel
 */
public class PermanenteModel {
    private int id;
    private CaixaModel caixa;
    private UsuarioModel usuario;
    private CategoriaModel categoria;
    private EstadoModel estado;
    private double totalMulticaixa;
    private double totalNumerario;
    

    public PermanenteModel() {
    }

    public PermanenteModel(int id, CaixaModel caixa, UsuarioModel usuario, CategoriaModel categoria, EstadoModel estado) {
        this.id = id;
        this.caixa = caixa;
        this.usuario = usuario;
        this.categoria = categoria;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CaixaModel getCaixa() {
        return caixa;
    }

    public void setCaixa(CaixaModel caixa) {
        this.caixa = caixa;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public CategoriaModel getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaModel categoria) {
        this.categoria = categoria;
    }

    public EstadoModel getEstado() {
        return estado;
    }

    public void setEstado(EstadoModel estado) {
        this.estado = estado;
    }

    public double getTotalMulticaixa() {
        return totalMulticaixa;
    }

    public void setTotalMulticaixa(double totalMulticaixa) {
        this.totalMulticaixa = totalMulticaixa;
    }

    public double getTotalNumerario() {
        return totalNumerario;
    }

    public void setTotalNumerario(double totalNumerario) {
        this.totalNumerario = totalNumerario;
    }

    
    

    @Override
    public String toString() {
        return caixa.getUsuario().getNome();
    }
    
    
    
    public boolean isEmpty(){
        return caixa == null;
    }
    
}
