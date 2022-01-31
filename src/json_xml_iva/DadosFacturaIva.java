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
public class DadosFacturaIva {

    AuditFile AuditFile = new AuditFile();

    public void setAuditFile(AuditFile AuditFile) {
        this.AuditFile = AuditFile;
    }

    public AuditFile getAuditFile() {
        return AuditFile;
    }

    public class AuditFile {

        Header Header = new Header();
        MasterFiles MasterFiles = new MasterFiles();
        SourceDocuments SourceDocuments = new SourceDocuments();

        public AuditFile() {
        }

        public class Header {

            String AuditFileVersion = "1.01_01";
            String CompanyID = "5000437395";
            String TaxRegistrationNumber = "";
            String TaxAccountingBasis = "F";
            String CompanyName = "";
            String BusinessName = "EMPRESA DE DEMONSTRAÇÃO";
            CompanyAddress CompanyAddress = new CompanyAddress();
            String FiscalYear = "2019";
            String StartDate = "2019-03-01";
            String EndDate = "2019-03-31";
            String CurrencyCode = "AOA";
            String DateCreated = "2019-04-08";
            String TaxEntity = "Global";
            String ProductCompanyTaxID = "5000437395";
            String SoftwareValidationNumber = "282/AGT/2020";
            String ProductID = "Venda+/ZETASOFT TECNOLOGIA - PRESTAÇAO DE SERVIÇOS E COMERCIO , LDA";
            String ProductVersion = "2.6";
            String Telephone = "930175149-936604677";
            String Fax = "948100";
            String Email = "zetasoft100@gmail.com";
            String Website = "www.zetasoft.com";

//            String AuditFileVersion = "1.01_01";
//            String CompanyID = "5417221951";
//            String TaxRegistrationNumber = "null";
//            String TaxAccountingBasis = "F";
//            String CompanyName = "Maurosoft";
//            String BusinessName = "EMPRESA DE DEMONSTRAÇÃO";
//            CompanyAddress CompanyAddress = new CompanyAddress();
//            String FiscalYear = "2019";
//            String StartDate = "2019-03-01";
//            String EndDate = "2019-03-31";
//            String CurrencyCode = "AOA";
//            String DateCreated = "2019-04-08";
//            String TaxEntity = "Global";
//            String ProductCompanyTaxID = "AO503140600";
//            String SoftwareValidationNumber = "0/AGT/2020";
//            String ProductID =  "AGT/ZETASOFT TECNOLOGIAS";
//            String ProductVersion = "2.6";
//            String Telephone = "930175149-936604677";
//            String Fax = "948100";
//            String Email= "geral@ao.primaverabss.com";
//            String Website = "www.ao.primaverabss.com";
            public class CompanyAddress {

                String AddressDetail = "";
                String City = "";
//                String PostalCode = "1000-000";
                String Country = "AO";

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

//                public String getPostalCode() {
//                    return PostalCode;
//                }
//
//                public void setPostalCode(String PostalCode) {
//                    this.PostalCode = PostalCode;
//                }
                public String getCountry() {
                    return Country;
                }

                public void setCountry(String Country) {
                    this.Country = Country;
                }

            }

            public String getAuditFileVersion() {
                return AuditFileVersion;
            }

            public void setAuditFileVersion(String AuditFileVersion) {
                this.AuditFileVersion = AuditFileVersion;
            }

            public String getCompanyID() {
                return CompanyID;
            }

            public void setCompanyID(String CompanyID) {
                this.CompanyID = CompanyID;
            }

            public String getTaxRegistrationNumber() {
                return TaxRegistrationNumber;
            }

            public void setTaxRegistrationNumber(String TaxRegistrationNumber) {
                this.TaxRegistrationNumber = TaxRegistrationNumber;
            }

            public String getTaxAccountingBasis() {
                return TaxAccountingBasis;
            }

            public void setTaxAccountingBasis(String TaxAccountingBasis) {
                this.TaxAccountingBasis = TaxAccountingBasis;
            }

            public String getCompanyName() {
                return CompanyName;
            }

            public void setCompanyName(String CompanyName) {
                this.CompanyName = CompanyName;
            }

            public String getBusinessName() {
                return BusinessName;
            }

            public void setBusinessName(String BusinessName) {
                this.BusinessName = BusinessName;
            }

            public CompanyAddress getCompanyAddress() {
                return CompanyAddress;
            }

            public void setCompanyAddress(CompanyAddress CompanyAddress) {
                this.CompanyAddress = CompanyAddress;
            }

            public String getFiscalYear() {
                return FiscalYear;
            }

            public void setFiscalYear(String FiscalYear) {
                this.FiscalYear = FiscalYear;
            }

            public String getStartDate() {
                return StartDate;
            }

            public void setStartDate(String StartDate) {
                this.StartDate = StartDate;
            }

            public String getEndDate() {
                return EndDate;
            }

            public void setEndDate(String EndDate) {
                this.EndDate = EndDate;
            }

            public String getCurrencyCode() {
                return CurrencyCode;
            }

            public void setCurrencyCode(String CurrencyCode) {
                this.CurrencyCode = CurrencyCode;
            }

            public String getDateCreated() {
                return DateCreated;
            }

            public void setDateCreated(String DateCreated) {
                this.DateCreated = DateCreated;
            }

            public String getTaxEntity() {
                return TaxEntity;
            }

            public void setTaxEntity(String TaxEntity) {
                this.TaxEntity = TaxEntity;
            }

            public String getProductCompanyTaxID() {
                return ProductCompanyTaxID;
            }

            public void setProductCompanyTaxID(String ProductCompanyTaxID) {
                this.ProductCompanyTaxID = ProductCompanyTaxID;
            }

            public String getSoftwareValidationNumber() {
                return SoftwareValidationNumber;
            }

            public void setSoftwareValidationNumber(String SoftwareValidationNumber) {
                this.SoftwareValidationNumber = SoftwareValidationNumber;
            }

            public String getProductID() {
                return ProductID;
            }

            public void setProductID(String ProductID) {
                this.ProductID = ProductID;
            }

            public String getProductVersion() {
                return ProductVersion;
            }

            public void setProductVersion(String ProductVersion) {
                this.ProductVersion = ProductVersion;
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

            public String getEmail() {
                return Email;
            }

            public void setEmail(String Email) {
                this.Email = Email;
            }

            public String getWebsite() {
                return Website;
            }

            public void setWebsite(String Website) {
                this.Website = Website;
            }
        }

        public class MasterFiles {

            List<CustomerIten> customerIten;
            //List<SupplierIten> supplierIten;
            List<ProdutItem> produtItem;
            TaxTable TaxTable;

            public MasterFiles() {
                customerIten = new ArrayList<>();
                //supplierIten = new ArrayList<>();
                produtItem = new ArrayList<>();
                TaxTable = new TaxTable();
            }

            public List<CustomerIten> getCustomerIten() {
                return customerIten;
            }

            public void setCustomerIten(List<CustomerIten> customerIten) {
                this.customerIten = customerIten;
            }
//            public List<SupplierIten> getSupplierIten() {
//                return supplierIten;
//            }
//
//            public void setSupplierIten(List<SupplierIten> supplierIten) {
//                this.supplierIten = supplierIten;
//            }

            public List<ProdutItem> getProdutItem() {
                return produtItem;
            }

            public void setProdutItem(List<ProdutItem> produtItem) {
                this.produtItem = produtItem;
            }

            public TaxTable getTaxTable() {
                return TaxTable;
            }

            public void setTaxTable(TaxTable TaxTable) {
                this.TaxTable = TaxTable;
            }

            public class TaxTable {

                List<TaxTableItem> taxTableItems;

                public TaxTable() {
                    taxTableItems = new ArrayList<>();
                }

                public List<TaxTableItem> getTaxTableItems() {
                    return taxTableItems;
                }

                public void setTaxTableItems(List<TaxTableItem> taxTableItems) {
                    this.taxTableItems = taxTableItems;
                }
            }
        }

        public class SourceDocuments {

            SalesInvoices SalesInvoices = new SalesInvoices();
            MovementOfGoods MovementOfGoods = new MovementOfGoods();
            WorkingDocuments WorkingDocuments = new WorkingDocuments();
            AllPayments Payments = new AllPayments();

            public SourceDocuments() {
            }

            public class SalesInvoices {

                String NumberOfEntries = "0";
                String TotalDebit = "0.00";
                String TotalCredit = "0.00";
                List<Invoices> Invoices;

                public SalesInvoices() {
                    Invoices = new ArrayList<>();
//              Invoice.add(new Invoice());
                }

                public String getNumberOfEntries() {
                    return NumberOfEntries;
                }

                public void setNumberOfEntries(String NumberOfEntries) {
                    this.NumberOfEntries = NumberOfEntries;
                }

                public String getTotalDebit() {
                    return TotalDebit;
                }

                public void setTotalDebit(String TotalDebit) {
                    this.TotalDebit = TotalDebit;
                }

                public String getTotalCredit() {
                    return TotalCredit;
                }

                public void setTotalCredit(String TotalCredit) {
                    this.TotalCredit = TotalCredit;
                }

                public List<Invoices> getInvoices() {
                    return Invoices;
                }

                public void setInvoices(List<Invoices> Invoices) {
                    this.Invoices = Invoices;
                }

            }

            public SalesInvoices getSalesInvoices() {
                return SalesInvoices;
            }

            public void setSalesInvoices(SalesInvoices SalesInvoices) {
                this.SalesInvoices = SalesInvoices;
            }

            public class MovementOfGoods {

                String NumberOfMovementLines = "0";
                String TotalQuantityIssued = "0";
                List<StockMovements> StockMovements;

                public MovementOfGoods() {
                    StockMovements = new ArrayList<>();
//              Invoice.add(new Invoice());
                }

                public String getNumberOfMovementLines() {
                    return NumberOfMovementLines;
                }

                public void setNumberOfMovementLines(String NumberOfMovementLines) {
                    this.NumberOfMovementLines = NumberOfMovementLines;
                }

                public String getTotalQuantityIssued() {
                    return TotalQuantityIssued;
                }

                public void setTotalQuantityIssued(String TotalQuantityIssued) {
                    this.TotalQuantityIssued = TotalQuantityIssued;
                }

                public List<StockMovements> getStockMovements() {
                    return StockMovements;
                }

                public void setStockMovements(List<StockMovements> StockMovements) {
                    this.StockMovements = StockMovements;
                }

            }

            public MovementOfGoods getMovementOfGoods() {
                return MovementOfGoods;
            }

            public void setMovementOfGoods(MovementOfGoods MovementOfGoods) {
                this.MovementOfGoods = MovementOfGoods;
            }

            public class WorkingDocuments {

                String NumberOfEntries = "0";
                String TotalDebit = "0.00";
                String TotalCredit = "0.00";
                List<WorkDocuments> WorkDocuments;

                public WorkingDocuments() {
                    WorkDocuments = new ArrayList<>();
//              Invoice.add(new Invoice());
                }

                public String getNumberOfEntries() {
                    return NumberOfEntries;
                }

                public void setNumberOfEntries(String NumberOfEntries) {
                    this.NumberOfEntries = NumberOfEntries;
                }

                public String getTotalDebit() {
                    return TotalDebit;
                }

                public void setTotalDebit(String TotalDebit) {
                    this.TotalDebit = TotalDebit;
                }

                public String getTotalCredit() {
                    return TotalCredit;
                }

                public void setTotalCredit(String TotalCredit) {
                    this.TotalCredit = TotalCredit;
                }

                public List<WorkDocuments> getWorkDocuments() {
                    return WorkDocuments;
                }

                public void setWorkDocuments(List<WorkDocuments> WorkDocuments) {
                    this.WorkDocuments = WorkDocuments;
                }
            }

            public WorkingDocuments getWorkingDocuments() {
                return WorkingDocuments;
            }

            public void setWorkingDocuments(WorkingDocuments WorkingDocuments) {
                this.WorkingDocuments = WorkingDocuments;
            }

            public class AllPayments {

                String NumberOfEntries = "0";
                String TotalDebit = "0.00";
                String TotalCredit = "0.00";
//          Invoice Invoice = new Invoice();

                List<Payments> PaymentItem;

                public AllPayments() {
                    PaymentItem = new ArrayList<>();
                }

                public String getNumberOfEntries() {
                    return NumberOfEntries;
                }

                public void setNumberOfEntries(String NumberOfEntries) {
                    this.NumberOfEntries = NumberOfEntries;
                }

                public String getTotalDebit() {
                    return TotalDebit;
                }

                public void setTotalDebit(String TotalDebit) {
                    this.TotalDebit = TotalDebit;
                }

                public String getTotalCredit() {
                    return TotalCredit;
                }

                public void setTotalCredit(String TotalCredit) {
                    this.TotalCredit = TotalCredit;
                }

                public List<Payments> getPayments() {
                    return PaymentItem;
                }

                public void setPayment(List<Payments> Payments) {
                    this.PaymentItem = Payments;
                }

            }

            public AllPayments getAllPayments() {
                return Payments;
            }

            public void setAllPayments(AllPayments Payments) {
                this.Payments = Payments;
            }

        }

        public Header getHeader() {
            return Header;
        }

        public void setHeader(Header Header) {
            this.Header = Header;
        }

        public MasterFiles getMasterFiles() {
            return MasterFiles;
        }

        public void setMasterFiles(MasterFiles MasterFiles) {
            this.MasterFiles = MasterFiles;
        }

        public SourceDocuments getSourceDocuments() {
            return SourceDocuments;
        }

        public void setSourceDocuments(SourceDocuments SourceDocuments) {
            this.SourceDocuments = SourceDocuments;
        }
    }
}
