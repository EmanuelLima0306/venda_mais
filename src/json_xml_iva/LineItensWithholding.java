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
public class LineItensWithholding {

    WithholdingTax Line = new WithholdingTax();


    public class WithholdingTax {

        String WithholdingTaxType = "IRT";
        String WithholdingTaxDescription = "Aplicação da Retenção";
        String WithholdingTaxAmount = "0.00";

        public String WithholdingTaxType() {
            return WithholdingTaxType;
        }

        public void setWithholdingTaxType(String WithholdingTaxType) {
            this.WithholdingTaxType = WithholdingTaxType;
        }

        public String getWithholdingTaxDescription() {
            return WithholdingTaxDescription;
        }

        public void setWithholdingTaxDescription(String WithholdingTaxDescription) {
            this.WithholdingTaxDescription = WithholdingTaxDescription;
        }

        public String getWithholdingTaxAmount() {
            return WithholdingTaxAmount;
        }

        public void setWithholdingTaxAmount(String WithholdingTaxAmount) {
            this.WithholdingTaxAmount = WithholdingTaxAmount;
        }

    }


    public WithholdingTax getLine() {
        return Line;
    }

    public void setLine(WithholdingTax Line) {
        this.Line = Line;
    }

}
