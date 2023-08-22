/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewCommande;

import entity.ConnectionDB;
import entity.Produit;
import entity.SessionManager;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author k
 */
public class ListCommandeController implements Initializable {

    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    
    
    @FXML
    private TableView<Produit> tvCommand;
    @FXML
    private TableColumn<?, ?> colNom;
    @FXML
    private TableColumn<?, ?> colQTE;
    @FXML
    private TableColumn<?, ?> colPrix;
    @FXML
    private TextField tfqt;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        refresh();
    }    
    
    
    @FXML
    public void refresh()
    {
        
        tvCommand.getItems().clear();
        
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colQTE.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
                    
        ArrayList<Produit> cartItems = SessionManager.getInstance().getCartItems();
        // Convert ArrayList<Produit> to ObservableList<Produit>
     ObservableList<Produit> observableCartItems = FXCollections.observableArrayList(cartItems);
        
        tvCommand.setItems(  observableCartItems    );
    }  
    
    
    
    @FXML
    public void supprim()
    {
            Produit rico = (Produit) tvCommand.getSelectionModel().getSelectedItem();
            if(rico != null )
            {
            int id = rico.getIdp() ;            
           SessionManager.getInstance().removeFromCart(id);                        
           refresh();
            }
    }
    
    @FXML
    public void changeQuantity()
    {
                    Produit rico = (Produit) tvCommand.getSelectionModel().getSelectedItem();
            if(rico != null )
            {
                if(!tfqt.getText().isEmpty()) {
                    
               try {       
            int q = Integer.parseInt( tfqt.getText().toString() ) ;
                
            int id = rico.getIdp() ;            
           SessionManager.getInstance().changeProductQuantity(id,q);     
          // System.out.println("check 23");
           refresh();
            
                    } catch (Exception e) {
        e.printStackTrace(); // Print the exception details to the console
    }
                
                
                }
            
            }
    }
    
    
    
    @FXML
    public void achat()
    {
        if( tvCommand.getItems().size() > 0 ) {
        cnx = ConnectionDB.getInstance().getCnx();
        
        for (Produit product :  SessionManager.getInstance().getCartItems() ) 
        {
            String requete="update produits SET Quantity = Quantity - " +  product.getQuantity() +  " WHERE idp = " + product.getIdp() ;
            
            String requete2="update produits SET nombreVendu = nombreVendu + " +  product.getQuantity() +  " WHERE idp = " + product.getIdp() ;
            
            
            // quantite
            try {
            PreparedStatement pst = cnx.prepareStatement(requete);
           pst.executeUpdate();
            
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }    
            
            //nombre vendu
                    try {
            PreparedStatement pst2 = cnx.prepareStatement(requete2);
           pst2.executeUpdate();
            
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            
        }
        
        SessionManager.getInstance().cleanUserSession();
        tvCommand.getItems().clear();
        }
    }
    
            
    
    
}
