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
public class Invoice {

    String InvoiceNo = "2NFa 1";
    //String ATCUD = "1";
    DocumentStatus DocumentStatus;
    String Hash = "J9NYlNNLMU0c/JeH5fCLhtjSsq3sdc5xmzd4QILjw3MN8UGHLyOoslGFmhK0fO1nbLengUbnPecBOETcG6r6FPb2jp2HXoyDtmsXFTCPsndN7gTLvr93/l4jBF4J8MDXqnH+ZBaoGsIPNB7GB6lAs4nrC8nmSXEl7Koxu9rIT4E=";
    String HashControl = "1";
    String Period = "5";
    String InvoiceDate = "2007-01-24";
    String InvoiceType = "Factura";
    SpecialRegimes SpecialRegimes;
    String SourceID = "Operador Demostração";
    String SystemEntryDate = "2007-01-24T15:37:50";
    String CustomerID = "123456789";
//     String TransactionID ="null";
    List<LineItens> LineItens = new ArrayList<>();
    DocumentTotals DocumentTotals = new DocumentTotals();
    List<LineItensWithholding.WithholdingTax> WithholdingTax = new ArrayList<>();

    public Invoice() {
        DocumentStatus = new DocumentStatus();
        SpecialRegimes = new SpecialRegimes();
    }

    public Invoice(String InvoiceNo) {
        this.InvoiceNo = InvoiceNo;
    }

    public class SpecialRegimes {

        String SelfBillingIndicator = "0";
        String CashVATSchemeIndicator = "0";
        String ThirdPartiesBillingIndicator = "0";

        public SpecialRegimes() {

        }

        public String getSelfBillingIndicator() {
            return SelfBillingIndicator;
        }

        public void setSelfBillingIndicator(String SelfBillingIndicator) {
            this.SelfBillingIndicator = SelfBillingIndicator;
        }

        public String getCashVATSchemeIndicator() {
            return CashVATSchemeIndicator;
        }

        public void setCashVATSchemeIndicator(String CashVATSchemeIndicator) {
            this.CashVATSchemeIndicator = CashVATSchemeIndicator;
        }

        public String getThirdPartiesBillingIndicator() {
            return ThirdPartiesBillingIndicator;
        }

        public void setThirdPartiesBillingIndicator(String ThirdPartiesBillingIndicator) {
            this.ThirdPartiesBillingIndicator = ThirdPartiesBillingIndicator;
        }
    }

    public class DocumentStatus {

        String InvoiceStatus = "N";
        String InvoiceStatusDate = "2019-05-03T09:21:27";
        String SourceID = "1";
        String SourceBilling = "P";

        public DocumentStatus() {

        }

        public String getInvoiceStatus() {
            return InvoiceStatus;
        }

        public void setInvoiceStatus(String InvoiceStatus) {
            this.InvoiceStatus = InvoiceStatus;
        }

        public String getInvoiceStatusDate() {
            return InvoiceStatusDate;
        }

        public void setInvoiceStatusDate(String InvoiceStatusDate) {
            this.InvoiceStatusDate = InvoiceStatusDate;
        }

        public String getSourceID() {
            return SourceID;
        }

        public void setSourceID(String SourceID) {
            this.SourceID = SourceID;
        }

        public String getSourceBilling() {
            return SourceBilling;
        }

        public void setSourceBilling(String SourceBilling) {
            this.SourceBilling = SourceBilling;
        }

    }

    public class DocumentTotals {

        String TaxPayable = "128.64";
        String NetTotal = "612.59";
        String GrossTotal = "741.23";
        Payment Payment = new Payment();

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

        public Payment getPayment() {
            return Payment;
        }

        public void setPayment(Payment Payment) {
            this.Payment = Payment;
        }
    }

    public class Payment {

        String PaymentMechanism = "CC";
        String PaymentAmount = "0.00";
        String PaymentDate = "";

        public String getPaymentMechanism() {
            return PaymentMechanism;
        }

        public void setPaymentMechanism(String PaymentMechanism) {
            this.PaymentMechanism = PaymentMechanism;
        }

        public String getPaymentAmount() {
            return PaymentAmount;
        }

        public void setPaymentAmount(String PaymentAmount) {
            this.PaymentAmount = PaymentAmount;
        }

        public String getPaymentDate() {
            return PaymentDate;
        }

        public void setPaymentDate(String PaymentDate) {
            this.PaymentDate = PaymentDate;
        }

    }

    public String getInvoiceNo() {
        return InvoiceNo;
    }

    public void setInvoiceNo(String InvoiceNo) {
        this.InvoiceNo = InvoiceNo;
    }

//    public String getATCUD() {
//        return ATCUD;
//    }
//
//    public void setATCUD(String ATCUD) {
//        this.ATCUD = ATCUD;
//    }
    public String getPeriod() {
        return Period;
    }

    public void setPeriod(String Period) {
        this.Period = Period;
    }

    public String getInvoiceDate() {
        return InvoiceDate;
    }

    public void setInvoiceDate(String InvoiceDate) {
        this.InvoiceDate = InvoiceDate;
    }

    public String getInvoiceType() {
        return InvoiceType;
    }

    public void setInvoiceType(String InvoiceType) {
        this.InvoiceType = InvoiceType;
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

    public String getHash() {
        return Hash;
    }

    public void setHash(String Hash) {
        this.Hash = Hash;
    }

    public String getHashControl() {
        return HashControl;
    }

    public void setHashControl(String HashControl) {
        this.HashControl = HashControl;
    }

    public SpecialRegimes getSpecialRegimes() {
        return SpecialRegimes;
    }

    public void setSpecialRegimes(SpecialRegimes SpecialRegimes) {
        this.SpecialRegimes = SpecialRegimes;
    }

    public String getSourceID() {
        return SourceID;
    }

    public void setSourceID(String SourceID) {
        this.SourceID = SourceID;
    }

    public List<LineItens> getLineItens() {
        return LineItens;
    }

    public void setLineItens(List<LineItens> Line) {
        this.LineItens = Line;
    }

    public DocumentTotals getDocumentTotals() {
        return DocumentTotals;
    }

    public void setDocumentTotals(DocumentTotals DocumentTotals) {
        this.DocumentTotals = DocumentTotals;
    }

    public List<LineItensWithholding.WithholdingTax> getWithholdingTax() {
        return WithholdingTax;
    }

    public void setWithholdingTax(List<LineItensWithholding.WithholdingTax> WithholdingTax) {
        this.WithholdingTax = WithholdingTax;
    }

}
