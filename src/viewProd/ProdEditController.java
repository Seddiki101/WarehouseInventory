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
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author k
 */
public class ProdEditController implements Initializable {

    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    int modifiableID;
    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfDescr;
    @FXML
    private TextField tfQuantite;
    @FXML
    private TextField tfPrix;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }





        public void receiveProd(Produit rico)
    {
        tfNom.setText(rico.getNom());
        tfDescr.setText(rico.getDescr());
        tfQuantite.setText(""+rico.getQuantity());   
        tfPrix.setText(""+rico.getPrix());        
        this.modifiableID = rico.getIdp();
        
    }
    
        
        
        
    @FXML
        public void updateProd()
    {       cnx = ConnectionDB.getInstance().getCnx();
            String requete2="update produits set nom=?,descr=?,quantity=?,prix=? where idp=? ";
            try {
            PreparedStatement pst = cnx.prepareStatement(requete2);
            pst.setString(1, tfNom.getText() );
            pst.setString(2, tfDescr.getText() );
            pst.setString(3, tfQuantite.getText() );
            pst.setString(4, tfPrix.getText() );
            pst.setInt(5, modifiableID );
            
           pst.executeUpdate();
            
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            //now redirect the user to another page
                Stage stage78 = (Stage) tfQuantite.getScene().getWindow();
                stage78.close(); // Close the current stage
        
    }    
        
        
        
        
        
        
}
