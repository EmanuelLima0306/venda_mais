/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json_xml_iva;

import java.util.List;
import json_xml_iva.LineItensPayment.LineItem;

/**
 *
 * @author Ramossoft
 */
public class LineItensPayment {

    LineItem Line = new LineItem();

    public class LineItem {

        String LineNumber = "3467";
        SourceDocumentID SourceDocumentID;
        String CreditAmount = "";
        String DebitAmount = "";

        public LineItem() {
            SourceDocumentID = new SourceDocumentID();
        }

        public class SourceDocumentID {

            String OriginatingON = "string";
            String InvoiceDate = "2005-04-01";
            String Description = "t";

            public String getOriginatingON() {
                return OriginatingON;
            }

            public void setOriginatingON(String OriginatingON) {
                this.OriginatingON = OriginatingON;
            }

            public String getInvoiceDate() {
                return InvoiceDate;
            }

            public void setInvoiceDate(String InvoiceDate) {
                this.InvoiceDate = InvoiceDate;
            }

            public String getDescription() {
                return Description;
            }

            public void setDescription(String Description) {
                this.Description = Description;
            }
        }

        public String getLineNumber() {
            return LineNumber;
        }

        public void setLineNumber(String LineNumber) {
            this.LineNumber = LineNumber;
        }

        public String getCreditAmount() {
            return CreditAmount;
        }

        public void setCreditAmount(String CreditAmount) {
            this.CreditAmount = CreditAmount;
        }

        public String getDebitAmount() {
            return DebitAmount;
        }

        public void setDebitAmount(String DebitAmount) {
            this.DebitAmount = DebitAmount;
        }

        public SourceDocumentID getSourceDocumentID() {
            return SourceDocumentID;
        }

        public void setSourceDocumentID(SourceDocumentID SourceDocumentID) {
            this.SourceDocumentID = SourceDocumentID;
        }

    }

    public LineItem getLine() {
        return Line;
    }

    public void setLine(LineItem Line) {
        this.Line = Line;
    }

}
