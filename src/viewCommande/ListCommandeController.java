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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

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
            
            String requete3 = "insert into orders (dateOrder,prodname,orderQuantity,prixUnitaire ,prixTotal  )"+ "VALUES (?, ?, ?, ?, ?)"; ;
            
            // quantite
            try {
            PreparedStatement pst = cnx.prepareStatement(requete);
           pst.executeUpdate();

           
           
           
           
           
           
                       //nombre vendu
                    try {
            PreparedStatement pst2 = cnx.prepareStatement(requete2);
           pst2.executeUpdate();
            
           
           
           
           
           
           
           
                       //Order
                    try {
            PreparedStatement pst3 = cnx.prepareStatement(requete3);
            
            //dateOrder,prodname,orderQuantity,prixUnitaire ,prixTotal 
            
            LocalDate currentDate = LocalDate.now();
            pst3.setString(1,currentDate.toString() );
            
            pst3.setString(2,product.getNom() );
            
             pst3.setInt(3,product.getQuantity() );
             
             pst3.setFloat(4,product.getPrix() );
             
             float c = product.getQuantity() * product.getPrix() ;
             
             pst3.setFloat(5, c );
            
            
            
           pst3.executeUpdate();
           
            
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
           
           
           
           
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
           
           
           
           
           
           
           
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }    
            

            
        }
        
        SessionManager.getInstance().cleanUserSession();
        tvCommand.getItems().clear();
        }
    }
    
            
    
    
    
    
    
public Text generatePrintContent3() {
    String storeName = "Sunny Room";
        LocalDate currentDate = LocalDate.now();
    String address = "Rue Environnement, Jendouba                    " +currentDate ;
    StringBuilder content = new StringBuilder();
    
    content.append("\n");
    content.append("\n");
    
    content.append(storeName).append("\n");
    content.append(address).append("\n\n");
    
    content.append("\n");
    content.append("\n");
    
    content.append("Produit").append("\t").append("\t");
    content.append("Quantite").append("\t").append("\t");
    content.append("Prix(U)").append("\t").append("\t");
    
        content.append("\n");
        content.append("\n");

    for (Produit produit : tvCommand.getItems() ) {
        content.append(produit.getNom()).append("\t").append("\t");
        content.append(produit.getQuantity()).append("\t").append("\t");
        content.append(produit.getPrix()).append("\n");
    }

    Text textNode = new Text(content.toString());
    return textNode;
}






@FXML
public void printCommands(ActionEvent event) {
    Text printContent = generatePrintContent3();

    PrinterJob job = PrinterJob.createPrinterJob();
    if (job != null) {
        boolean success = job.printPage(printContent);
        if (success) {
            job.endJob();
        }
    }
}

    
    
    
    
    
    
    
    
    
    
}
