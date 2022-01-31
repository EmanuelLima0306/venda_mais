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
public class ProdutItem {

    Product Product;

    public ProdutItem() 
    {
        this.Product = new Product();
    }

    public Product getProduct() {
        return Product;
    }

    public void setProduct(Product Product) {
        this.Product = Product;
    }


    public class Product {
        
        String ProductType = "P";
        String ProductCode = "279";
        String ProductGroup = "N/A";
        String ProductDescription = "PRODUTO AO 1";
        String ProductNumberCode = "1";

         public Product() {
         }

         public String getProductType() {
             return ProductType;
         }

         public void setProductType(String ProductType) {
             this.ProductType = ProductType;
         }

         public String getProductCode() {
             return ProductCode;
         }

         public void setProductCode(String ProductCode) {
             this.ProductCode = ProductCode;
         }

         public String getProductGroup() {
             return ProductGroup;
         }

         public void setProductGroup(String ProductGroup) {
             this.ProductGroup = ProductGroup;
         }

         public String getProductDescription() {
             return ProductDescription;
         }

         public void setProductDescription(String ProductDescription) {
             this.ProductDescription = ProductDescription;
         }

         public String getProductNumberCode() {
             return ProductNumberCode;
         }

         public void setProductNumberCode(String ProductNumberCode) {
             this.ProductNumberCode = ProductNumberCode;
         }
}


}

