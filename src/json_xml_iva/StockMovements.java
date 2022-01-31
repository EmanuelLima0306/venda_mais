/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json_xml_iva;

import java.util.List;

/**
 *
 * @author Ramossoft
 */
public class StockMovements {
    
    List<StockMovement> StockMovement;

    public StockMovements(List<StockMovement> StockMovement) {
        this.StockMovement = StockMovement;
    }

    public List<StockMovement> getStockMovement() {
        return StockMovement;
    }

    public void setStockMovement(List<StockMovement> StockMovement) {
        this.StockMovement = StockMovement;
    }
    
}
