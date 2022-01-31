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
public class SupplierIten {

    Supplier Supplier;

    public SupplierIten() {
        Supplier = new Supplier();
    }

    public class Supplier {

        String SupplierID = "ADM19";
        String AccountID = "Desconhecido";
        String SupplierTaxID = "1234567890";
        String CompanyName = "FORNECEDOR EXEMPLO";
        String Contact = "Desconhecido";
        BillingAddress BillingAddress;
        //ShipFromAddress ShipFromAddress;
        String Telephone = "Desconhecido";
        String Fax = "Desconhecido";
        String Website = "Desconhecido";
        String SelfBillingIndicator = "0";

        public Supplier() {
             BillingAddress = new BillingAddress();
            //ShipFromAddress = new ShipFromAddress();
        }

        public class BillingAddress {

            String AddressDetail = "Morada do Cliente";
            String City = "Luanda";
            String Country = "AO";

            public BillingAddress() {

            }

            public String getAddressDetail() {
                return AddressDetail;
            }

            public void setAddressDetail(String AddressDetail) {
                this.AddressDetail = AddressDetail;
            }

            public String getCity() {
                return City;
            }

            public void setCity(String City) {
                this.City = City;
            }

            public String getCountry() {
                return Country;
            }

            public void setCountry(String Country) {
                this.Country = Country;
            }
        }

        public class ShipFromAddress {

            String AddressDetail = "Morada do Cliente";
            String City = "Luanda";
            String Country = "AO";

            public ShipFromAddress() {

            }

            public String getAddressDetail() {
                return AddressDetail;
            }

            public void setAddressDetail(String AddressDetail) {
                this.AddressDetail = AddressDetail;
            }

            public String getCity() {
                return City;
            }

            public void setCity(String City) {
                this.City = City;
            }

            public String getCountry() {
                return Country;
            }

            public void setCountry(String SourceID) {
                this.Country = Country;
            }

        }

        public String getSupplierID() {
            return SupplierID;
        }

        public void setSupplierID(String SupplierID) {
            this.SupplierID = SupplierID;
        }

        public String getAccountID() {
            return AccountID;
        }

        public void setAccountID(String AccountID) {
            this.AccountID = AccountID;
        }

        public String getSupplierTaxID() {
            return SupplierTaxID;
        }

        public void setSupplierTaxID(String SupplierTaxID) {
            this.SupplierTaxID = SupplierTaxID;
        }

        public String getCompanyName() {
            return CompanyName;
        }

        public void setCompanyName(String CompanyName) {
            this.CompanyName = CompanyName;
        }

        public String getContact() {
            return Contact;
        }

        public void setContact(String Contact) {
            this.Contact = Contact;
        }

        public String getTelephone() {
            return Telephone;
        }

        public void setTelephone(String Telephone) {
            this.Telephone = Telephone;
        }

        public String getFax() {
            return Fax;
        }

        public void setFax(String Fax) {
            this.Fax = Fax;
        }

        public String getWebsite() {
            return Website;
        }

        public void setWebsite(String Website) {
            this.Website = Website;
        }

        public String getSelfBillingIndicator() {
            return SelfBillingIndicator;
        }

        public void setSelfBillingIndicator(String SelfBillingIndicator) {
            this.SelfBillingIndicator = SelfBillingIndicator;
        }

        public BillingAddress getBillingAddress() {
            return this.BillingAddress;
        }

        public void setBillingAddress(BillingAddress BillingAddress) {
            this.BillingAddress = BillingAddress;
        }
    }

    public Supplier getSupplier() {
        return Supplier;
    }

    public void setSupplier(Supplier Supplier) {
        this.Supplier = Supplier;
    }
}
