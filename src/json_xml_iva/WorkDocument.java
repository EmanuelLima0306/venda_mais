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
public class WorkDocument {

    String DocumentNumber = "2NFa 1";
    //String ATCUD = "1";
    DocumentStatus DocumentStatus;
    String Hash = "J9NYlNNLMU0c/JeH5fCLhtjSsq3sdc5xmzd4QILjw3MN8UGHLyOoslGFmhK0fO1nbLengUbnPecBOETcG6r6FPb2jp2HXoyDtmsXFTCPsndN7gTLvr93/l4jBF4J8MDXqnH+ZBaoGsIPNB7GB6lAs4nrC8nmSXEl7Koxu9rIT4E=";
    String HashControl = "1";
    String Period = "5";
    String WorkDate = "2007-01-24";
    String WorkType = "Factura";
    String SourceID = "Operador Demostração";
    String SystemEntryDate = "2007-01-24T15:37:50";
    String TransactionID = "";
    String CustomerID = "123456789";
//     String TransactionID ="null";
    List<LineItensWorkDocument> LineItens = new ArrayList<>();
    DocumentTotals DocumentTotals = new DocumentTotals();

    public WorkDocument() {
        DocumentStatus = new DocumentStatus();
    }

    public WorkDocument(String DocumentNumber) {
        this.DocumentNumber = DocumentNumber;
    }


    public class DocumentStatus {

        String WorkStatus = "N";
        String WorkStatusDate = "2019-05-03T09:21:27";
        String Reason = "";
        String SourceID = "1";
        String SourceBilling = "P";

        public DocumentStatus() {

        }

        public String getWorkStatus() {
            return WorkStatus;
        }

        public void setWorkStatus(String WorkStatus) {
            this.WorkStatus = WorkStatus;
        }
        public String getReason() {
            return Reason;
        }

        public void setReason(String Reason) {
            this.Reason = Reason;
        }

        public String getWorkStatusDate() {
            return WorkStatusDate;
        }

        public void setWorkStatusDate(String WorkStatusDate) {
            this.WorkStatusDate = WorkStatusDate;
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

    public String getDocumentNumber() {
        return DocumentNumber;
    }

    public void setDocumentNumber(String DocumentNumber) {
        this.DocumentNumber = DocumentNumber;
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

    public String getWorkDate() {
        return WorkDate;
    }

    public void setWorkDate(String WorkDate) {
        this.WorkDate = WorkDate;
    }

    public String getWorkType() {
        return WorkType;
    }

    public void setWorkType(String WorkType) {
        this.WorkType = WorkType;
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
   
    public String getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(String TransactionID) {
        this.TransactionID = TransactionID;
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

    public String getSourceID() {
        return SourceID;
    }

    public void setSourceID(String SourceID) {
        this.SourceID = SourceID;
    }

    public List<LineItensWorkDocument> getLineItens() {
        return LineItens;
    }

    public void setLineItens(List<LineItensWorkDocument> Line) {
        this.LineItens = Line;
    }

    public DocumentTotals getDocumentTotals() {
        return DocumentTotals;
    }

    public void setDocumentTotals(DocumentTotals DocumentTotals) {
        this.DocumentTotals = DocumentTotals;
    }

}
