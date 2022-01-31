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
public class Payments {
    
    List<Payment> Payment;

    public Payments(List<Payment> Payment) {
        this.Payment = Payment;
    }

    
    public List<Payment> getPayment() {
        return Payment;
    }

    public void setPayment(List<Payment> Payment) {
        this.Payment = Payment;
    }
    
}
