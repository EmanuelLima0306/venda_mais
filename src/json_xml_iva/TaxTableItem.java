/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json_xml_iva;

/**
 *
 * @author Ramossoft
 */
public class TaxTableItem {
     
    TaxTableEntry  TaxTableEntry = new TaxTableEntry();
    public class TaxTableEntry
    {
         String TaxType = "IVA";
         String TaxCountryRegion = "AO";
         String TaxCode = "NOR";
         String Description = "Normal";
         String TaxPercentage = "14";
         //String TaxAmount = "0";
        public TaxTableEntry() {
        }

        public String getTaxType() {
            return TaxType;
        }

        public void setTaxType(String TaxType) {
            this.TaxType = TaxType;
        }

        public String getTaxCountryRegion() {
            return TaxCountryRegion;
        }

        public void setTaxCountryRegion(String TaxCountryRegion) {
            this.TaxCountryRegion = TaxCountryRegion;
        }

        public String getTaxCode() {
            return TaxCode;
        }

        public void setTaxCode(String TaxCode) {
            this.TaxCode = TaxCode;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public String getTaxPercentage() {
            return TaxPercentage;
        }

        public void setTaxPercentage(String TaxPercentage) {
            this.TaxPercentage = TaxPercentage;
        } 

//        public String getTaxAmount() {
//            return TaxAmount;
//        }
//
//        public void setTaxAmount(String TaxAmount) {
//            this.TaxAmount = TaxAmount;
//        }
    }
    
    public TaxTableEntry getTaxTableEntry() {
       return TaxTableEntry;
    }

    public void setTaxTableEntry(TaxTableEntry TaxTableEntry) {
        this.TaxTableEntry = TaxTableEntry;
    }
}
