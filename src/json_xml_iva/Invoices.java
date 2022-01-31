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
public class Invoices {
    
    List<Invoice> Invoice;

    public Invoices(List<Invoice> Invoice) {
        this.Invoice = Invoice;
    }

    public List<Invoice> getInvoice() {
        return Invoice;
    }

    public void setInvoice(List<Invoice> Invoice) {
        this.Invoice = Invoice;
    }
    
}
