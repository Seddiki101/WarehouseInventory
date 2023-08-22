/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewProd;

import entity.ConnectionDB;
import entity.Produit;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author k
 */
public class ProdAddController implements Initializable {

    
        private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfDescr;
    @FXML
    private TextField tfQuantite;
    @FXML
    private TextField tfPrix;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    

      
      
      
            public void addProd()
    {       cnx = ConnectionDB.getInstance().getCnx();
            String query="insert into produits  (nom,descr,quantity,prix ,dateprod  ) "
                                + "VALUES (?, ?, ?, ?, ?)";   
            
         //
                     
            try {
                if(tfNom.getText().isEmpty()  )
                {
                  //empty fields
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Sunny Room :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Saisir nom ");
                alert.showAndWait();  
                }
                
                else{

                    PreparedStatement smt = cnx.prepareStatement(query);
                    
            smt.setString(1, tfNom.getText());
            smt.setString(2, tfDescr.getText());
               
            smt.setString(3,tfQuantite.getText() );
    
            smt.setString(4,tfPrix.getText() );
            
            LocalDate currentDate = LocalDate.now();
            //System.out.println("Current Date: " + currentDate);
            smt.setString(5,currentDate.toString() );
            
            
            smt.executeUpdate();
             
                Stage stage78 = (Stage) tfNom.getScene().getWindow();
                stage78.close(); // Close the current stage
            
                System.out.println("ajout avec succee");

                }
                
                
            }catch(SQLException ex){
         System.out.println(ex.getMessage());
            }

         //
            
    }            
          
      
      
      
      
      
      
    
}
            
