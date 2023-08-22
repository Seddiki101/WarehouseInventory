/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.prefs.Preferences;


import java.util.ArrayList;

public final class SessionManager {

    private static SessionManager instance;
    private static HashSet<Integer> addedProductIds = new HashSet<>();
    private static ArrayList<Produit> panier = new ArrayList<>();

    private SessionManager() {
        // Private constructor to prevent direct instantiation
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public static void addToCart(Produit product) {
        if (!addedProductIds.contains(product.getIdp())) {
            panier.add(product);
            addedProductIds.add(product.getIdp());
        }
    }

    public void removeFromCart(int productId) {
        for (int i = 0; i < panier.size(); i++) {
            Produit product = panier.get(i);
            if (product.getIdp() == productId) {
                panier.remove(i);
                addedProductIds.remove(productId);
                break;  // Once the product is found and removed, exit the loop
            }
        }
    }
    
    
        public  void changeProductQuantity(int productId, int newQuantity) {
        for (Produit product : panier) {
            if (product.getIdp() == productId) {
                product.setQuantity(newQuantity);
                break;  // Exit the loop once the product is found
            }
            }
    }

    public void cleanUserSession() {
        panier.clear();
        addedProductIds.clear();
    }

    public ArrayList<Produit> getCartItems() {
        return panier;
    }
}

 