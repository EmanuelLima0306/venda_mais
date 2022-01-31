/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Facturacao;

/**
 *
 * @author celso
 */
public class ExemploProduto {
    
    private String nome;
    private double preco;
    private String imagem;
    private int qtd;

    public ExemploProduto(String nome, double preco, String imagem,int qtd) {
        this.nome = nome;
        this.preco = preco;
        this.qtd = qtd;
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }
    
    
    
}
