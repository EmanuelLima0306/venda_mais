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
public class BarbeiroItemModel {
    
    private int id;
    private UsuarioModel usuarioModel;
    private FacturaItemModel facturaItemModel;
    private Double taxa;
    private Double quantidade;
    private Double valorRemuneracao;
    private EstadoModel estadoModel;

    public BarbeiroItemModel() {
    }

    public BarbeiroItemModel(UsuarioModel usuarioModel, FacturaItemModel facturaItemModel, Double taxa, Double quantidade, Double valorRemuneracao) {
        this.usuarioModel = usuarioModel;
        this.facturaItemModel = facturaItemModel;
        this.taxa = taxa;
        this.quantidade = quantidade;
        this.valorRemuneracao = valorRemuneracao;
    }

    public UsuarioModel getUsuarioModel() {
        return usuarioModel;
    }

    public void setUsuarioModel(UsuarioModel usuarioModel) {
        this.usuarioModel = usuarioModel;
    }

    public FacturaItemModel getFacturaItemModel() {
        return facturaItemModel;
    }

    public void setFacturaItemModel(FacturaItemModel facturaItemModel) {
        this.facturaItemModel = facturaItemModel;
    }

    public Double getTaxa() {
        return taxa;
    }

    public void setTaxa(Double taxa) {
        this.taxa = taxa;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorRemuneracao() {
        return valorRemuneracao;
    }

    public void setValorRemuneracao(Double valorRemuneracao) {
        this.valorRemuneracao = valorRemuneracao;
    }

    public EstadoModel getEstadoModel() {
        return estadoModel;
    }

    public void setEstadoModel(EstadoModel estadoModel) {
        this.estadoModel = estadoModel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return usuarioModel.getNome();
    }
    
    
    
}
