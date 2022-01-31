/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json_xml_iva;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ramossoft
 */
public class Payment {

    String PaymentRefNo = "t";
    String Period = "1";
    String TransactionDate = "2004-03-28";
    String PaymentType = "RC";
    String Description = "Recibo";
    String SystemID = "2";
    DocumentStatus DocumentStatus;
    PaymentMethod PaymentMethod;
    String SourceID = "string";
    String SystemEntryDate = "1990-09-27T22:39:50.06";
    String CustomerID = "string";
    List<LineItensPayment> LineItens = new ArrayList();
    DocumentTotals DocumentTotals;
    List<LineItensWithholding.WithholdingTax> WithholdingTax = new ArrayList<>();

    public Payment() {
        DocumentStatus = new DocumentStatus();
        PaymentMethod = new PaymentMethod();
        DocumentTotals = new DocumentTotals();
    }

    public class DocumentTotals {

        String TaxPayable = "0.00";
        String NetTotal = "0.00";
        String GrossTotal = "0.00";

        public DocumentTotals() {

        }

        public String getTaxPayable() {
            return TaxPayable;
        }

        public void setTaxPayable(String TaxPayable) {
            this.TaxPayable = TaxPayable;
        }

        public String getNetTotal() {
            return NetTotal;
        }

        public void setNetTotal(String NetTotal) {
            this.NetTotal = NetTotal;
        }

        public String getGrossTotal() {
            return GrossTotal;
        }

        public void setGrossTotal(String GrossTotal) {
            this.GrossTotal = GrossTotal;
        }
    }

    public class DocumentStatus {

        String PaymentStatus = "N";
        String PaymentStatusDate = "2019-05-03";
        String SourceID = "1";
        String SourcePayment = "P";

        public DocumentStatus() {

        }

        public String getInvoiceStatus() {
            return PaymentStatus;
        }

        public void setPaymentStatus(String PaymentStatus) {
            this.PaymentStatus = PaymentStatus;
        }

        public String getPaymentStatusDate() {
            return PaymentStatusDate;
        }

        public void setPaymentStatusDate(String PaymentStatusDate) {
            this.PaymentStatusDate = PaymentStatusDate;
        }

        public String getSourceID() {
            return SourceID;
        }

        public void setSourceID(String SourceID) {
            this.SourceID = SourceID;
        }

        public String getSourcePayment() {
            return SourcePayment;
        }

        public void setSourcePayment(String SourcePayment) {
            this.SourcePayment = SourcePayment;
        }

    }

    public class PaymentMethod {

        String PaymentMechanism = "CC";
        String PaymentAmount = "5883330.2349058";
        String PaymentDate = "1989-03-11";

        public PaymentMethod() {

        }

        public String getPaymentAmount() {
            return PaymentAmount;
        }

        public void setPaymentAmount(String PaymentAmount) {
            this.PaymentAmount = PaymentAmount;
        }

        public String getPaymentMechanism() {
            return PaymentMechanism;
        }

        public void setPaymentMechanism(String PaymentMechanism) {
            this.PaymentMechanism = PaymentMechanism;
        }

        public String getPaymentDate() {
            return PaymentDate;
        }

        public void setPaymentDate(String PaymentDate) {
            this.PaymentDate = PaymentDate;
        }
    }

    public List<LineItensWithholding.WithholdingTax> getWithholdingTax() {
        return WithholdingTax;
    }

    public void setWithholdingTax(List<LineItensWithholding.WithholdingTax> WithholdingTax) {
        this.WithholdingTax = WithholdingTax;
    }

//    public String getATCUD() {
//        return ATCUD;
//    }
//
//    public void setATCUD(String ATCUD) {
//        this.ATCUD = ATCUD;
//    }
    public String getPaymentRefNo() {
        return PaymentRefNo;
    }

    public void setPaymentRefNo(String PaymentRefNo) {
        this.PaymentRefNo = PaymentRefNo;
    }

    public String getPeriod() {
        return Period;
    }

    public void setPeriod(String Period) {
        this.Period = Period;
    }

    public String getTransactionDate() {
        return TransactionDate;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getDescription() {
        return Description;
    }

    public void setSystemID(String SystemID) {
        this.SystemID = SystemID;
    }

    public String getSystemID() {
        return SystemID;
    }

    public void setTransactionDate(String TransactionDate) {
        this.TransactionDate = TransactionDate;
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String PaymentType) {
        this.PaymentType = PaymentType;
    }

    public String getSystemEntryDate() {
        return SystemEntryDate;
    }

    public void setSystemEntryDate(String SystemEntryDate) {
        this.SystemEntryDate = SystemEntryDate;
    }

//    public String getTransactionID() {
//        return TransactionID;
//    }
//
//    public void setTransactionID(String TransactionID) {
//        this.TransactionID = TransactionID;
//    }
    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String CustomerID) {
        this.CustomerID = CustomerID;
    }

    public DocumentStatus getDocumentStatus() {
        return DocumentStatus;
    }

    public void setDocumentStatus(DocumentStatus DocumentStatus) {
        this.DocumentStatus = DocumentStatus;
    }

    public PaymentMethod getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(PaymentMethod PaymentMethod) {
        this.PaymentMethod = PaymentMethod;
    }

    public String getSourceID() {
        return SourceID;
    }

    public void setSourceID(String SourceID) {
        this.SourceID = SourceID;
    }

    public DocumentTotals getDocumentTotals() {
        return DocumentTotals;
    }

    public void setDocumentTotals(DocumentTotals DocumentTotals) {
        this.DocumentTotals = DocumentTotals;
    }

    public List<LineItensPayment> getLineItens() {
        return LineItens;
    }

    public void setLineItens(List<LineItensPayment> LineItens) {
        this.LineItens = LineItens;
    }

}
