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
public class Documento {
    
    private int id;
    private String Designacao;
    private String next;

    public Documento() {
    }

    public Documento(int id, String Designacao, String next) {
        this.id = id;
        this.Designacao = Designacao;
        this.next = next;
    }

    public Documento(String Designacao, String next) {
        this.Designacao = Designacao;
        this.next = next;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   

    public String getDesignacao() {
        return Designacao;
    }

    public void setDesignacao(String Designacao) {
        this.Designacao = Designacao;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return Designacao ;
    }
    
    
}
