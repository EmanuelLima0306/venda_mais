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
public class MovimentoItemModel {
    
    private int id;
    private FacturaItemModel facturaItemModel = new FacturaItemModel();
    private Movimento movimento = new Movimento();

    public FacturaItemModel getFacturaItemModel() {
        return facturaItemModel;
    }

    public MovimentoItemModel() {
    }

    public MovimentoItemModel(FacturaItemModel facturaItemModel) {
        this.facturaItemModel = facturaItemModel;
    }

    public void setFacturaItemModel(FacturaItemModel facturaItemModel) {
        this.facturaItemModel = facturaItemModel;
    }

    public Movimento getMovimento() {
        return movimento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMovimento(Movimento movimento) {
        this.movimento = movimento;
    }
    
    
    
    
}
