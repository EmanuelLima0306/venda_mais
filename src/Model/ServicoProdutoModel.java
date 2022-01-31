/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Mac
 */
public class ServicoProdutoModel {
    
    private int id;
    private ServicoModel servico;
    private ProdutoModel produto;

    public ServicoProdutoModel() {
    }

    public ServicoProdutoModel(int id, ServicoModel servico, ProdutoModel produto) {
        this.id = id;
        this.servico = servico;
        this.produto = produto;
    }

    public ServicoModel getServico() {
        return servico;
    }

    public void setServico(ServicoModel servico) {
        this.servico = servico;
    }

    public ProdutoModel getProduto() {
        return produto;
    }

    public void setProduto(ProdutoModel produto) {
        this.produto = produto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
}
