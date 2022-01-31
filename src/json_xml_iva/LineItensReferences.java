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
public class LineItensReferences {

    References Line = new References();

    public class References {

        String Reference = "";
        String Reason = "";

        public String getReference() {
            return Reference;
        }

        public void setReference(String Reference) {
            this.Reference = Reference;
        }

        public String getReason() {
            return Reason;
        }

        public void setReason(String Reason) {
            this.Reason = Reason;
        }
    }

    public References getLine() {
        return Line;
    }

    public void setLine(References Line) {
        this.Line = Line;
    }

}
