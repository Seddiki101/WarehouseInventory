/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author k
 */
public class Order {
 
    private int ido;
    private String dateOrder ;
    private String prodName;
    private int orderQuantity ;
    private float prixUnitaire ;
    private float prixTotal ;

    public Order(int ido, String dateOrder, String prodName, int orderQuantity, float prixUnitaire, float prixTotal) {
        this.ido = ido;
        this.dateOrder = dateOrder;
        this.prodName = prodName;
        this.orderQuantity = orderQuantity;
        this.prixUnitaire = prixUnitaire;
        this.prixTotal = prixTotal;
    }

    public int getIdo() {
        return ido;
    }

    public void setIdo(int ido) {
        this.ido = ido;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public float getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(float prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public float getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(float prixTotal) {
        this.prixTotal = prixTotal;
    }




    
}
