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
public class CriarContaClienteModel implements Serializable{
    
    private int id;
    private double limiteCredito;
    private ClienteModel cliente;

    public CriarContaClienteModel(int id, double limiteCredito, ClienteModel cliente) {
        this.id = id;
        this.limiteCredito = limiteCredito;
        this.cliente = cliente;
    }

    public CriarContaClienteModel(double limiteCredito, ClienteModel cliente) {
        this.limiteCredito = limiteCredito;
        this.cliente = cliente;
    }

    public CriarContaClienteModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }
    
    
    
}
