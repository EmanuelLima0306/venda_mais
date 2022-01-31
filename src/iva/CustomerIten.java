/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iva;

/**
 *
 * @author Ramossoft
 */
public class CustomerIten {

            Customer Customer;
            public CustomerIten() {
                Customer = new Customer();
            }
            
            public class Customer
            {
                String CustomerID = "24";
                String AccountID = "Desconhecido";
                String CustomerTaxID = "Consumidor final";
                String CompanyName = "Cliente AO 5 SN";
                BillingAddress BillingAddress;
                String SelfBillingIndicator="0";

                 public Customer() 
                 {
                     BillingAddress = new BillingAddress();
                 }

                public String getCustomerID() {
                    return CustomerID;
                }

                public void setCustomerID(String CustomerID) {
                    this.CustomerID = CustomerID;
                }

                 public String getAccountID() {
                     return AccountID;
                 }

                 public void setAccountID(String AccountID) {
                     this.AccountID = AccountID;
                 }

                 public String getCustomerTaxID() {
                     return CustomerTaxID;
                 }

                 public void setCustomerTaxID(String CustomerTaxID) {
                     this.CustomerTaxID = CustomerTaxID;
                 }

                 public String getCompanyName() {
                     return CompanyName;
                 }

                 public void setCompanyName(String CompanyName) {
                     this.CompanyName = CompanyName;
                 }

                 public BillingAddress getBillingAddress() {
                     return BillingAddress;
                 }

                 public void setBillingAddress(BillingAddress BillingAddress) {
                     this.BillingAddress = BillingAddress;
                 }

                 public String getSelfBillingIndicator() {
                     return SelfBillingIndicator;
                 }

                 public void setSelfBillingIndicator(String SelfBillingIndicator) {
                     this.SelfBillingIndicator = SelfBillingIndicator;
                 } 
                 
            public class BillingAddress
            {
                String AddressDetail ="Av. Deolinda Rodrigues, 123";
                String City = "Luanda";
                String PostalCode = "*";
                String Country="AO";

                public BillingAddress()
                {
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

               public String getPostalCode() {
                   return PostalCode;
               }

               public void setPostalCode(String PostalCode) {
                   this.PostalCode = PostalCode;
               }

               public String getCountry() {
                   return Country;
               }

               public void setCountry(String Country) {
                   this.Country = Country;
               }
            }
       }
    
        

    public Customer getCustomer() {
        return Customer;
    }

    public void setCustomer(Customer Customer) {
        this.Customer = Customer;
    }
        
}
