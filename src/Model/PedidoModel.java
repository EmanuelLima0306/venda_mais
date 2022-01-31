/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author celso
 */
public class PedidoModel {
    
    private Integer id;
    private MesaModel mesa;
    private EstadoModel estado;
    private String data;
    private String nome;

    public PedidoModel(Integer id) {
        this.id = id;
    }

   

    public PedidoModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MesaModel getMesa() {
        return mesa;
    }

    public void setMesa(MesaModel mesa) {
        this.mesa = mesa;
    }

  

    public EstadoModel getEstado() {
        return estado;
    }

    public void setEstado(EstadoModel estado) {
        this.estado = estado;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
    
    
    
}
