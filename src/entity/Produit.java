/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author k
 */
public class Produit {
    private int idp;
    private String nom ;
    private int quantity;
   private float prix; 
    private int nombreVendu;
    private String descr ;
    private String dateProd ;

    public Produit(int idp, String nom, int quantity, float prix, int nombreVendu, String descr, String dateProd) {
        this.idp = idp;
        this.nom = nom;
        this.quantity = quantity;
        this.prix = prix;
        this.nombreVendu = nombreVendu;
        this.descr = descr;
        this.dateProd = dateProd;
    }



    public int getIdp() {
        return idp;
    }

    public void setIdp(int idp) {
        this.idp = idp;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getNombreVendu() {
        return nombreVendu;
    }

    public void setNombreVendu(int nombreVendu) {
        this.nombreVendu = nombreVendu;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }




    public String getDateProd() {
        return dateProd;
    }

    public void setDateProd(String dateProd) {
        this.dateProd = dateProd;
    }
    


    
    
    
}
