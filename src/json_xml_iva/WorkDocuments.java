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
public class WorkDocuments {
    
    List<WorkDocument> WorkDocument;

    public WorkDocuments(List<WorkDocument> WorkDocument) {
        this.WorkDocument = WorkDocument;
    }

    public List<WorkDocument> getWorkDocument() {
        return WorkDocument;
    }

    public void setWorkDocument(List<WorkDocument> WorkDocument) {
        this.WorkDocument = WorkDocument;
    }
    
}
