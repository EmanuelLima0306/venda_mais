/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;

/**
 *
 * @author celso
 */
public class LicencaModel implements Serializable{
    
    private String dataFimLicenca;
    private String hdSerial;
    private String cPUSerial;
    private String motherboardSerial;
//    private String macAddress;

    public LicencaModel(String dataFimLicenca, String hdSerial, String cPUSerial, String motherboardSerial, String macAddress) {
        this.dataFimLicenca = dataFimLicenca;
        this.hdSerial = hdSerial;
        this.cPUSerial = cPUSerial;
        this.motherboardSerial = motherboardSerial;
//        this.macAddress = macAddress;
    }

    public String getHdSerial() {
        return hdSerial;
    }

    public void setHdSerial(String hdSerial) {
        this.hdSerial = hdSerial;
    }

    public String getcPUSerial() {
        return cPUSerial;
    }

    public void setcPUSerial(String cPUSerial) {
        this.cPUSerial = cPUSerial;
    }

    public String getMotherboardSerial() {
        return motherboardSerial;
    }

    public void setMotherboardSerial(String motherboardSerial) {
        this.motherboardSerial = motherboardSerial;
    }

//    public String getMacAddress() {
//        return macAddress;
//    }
//
//    public void setMacAddress(String macAddress) {
//        this.macAddress = macAddress;
//    }

    public LicencaModel() {
    }

    

    public String getDataFimLicenca() {
        return dataFimLicenca;
    }

    public void setDataFimLicenca(String dataFimLicenca) {
        this.dataFimLicenca = dataFimLicenca;
    }
    
    
}
