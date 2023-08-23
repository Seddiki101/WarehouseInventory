/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author k
 */
public class DashController implements Initializable {

        
      AnchorPane listprod,listOrder,listCommande;
    
    @FXML
    private VBox sidePanel;
    @FXML
    private AnchorPane centralPanel;
    @FXML
    private TextField tfusername;
    @FXML
    private Button connectBTN;
    @FXML
    private Label errorLabel;
    @FXML
    private PasswordField tfpwd;

    /**
     * Initializes the controller class.
     */
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        errorLabel.setVisible(false);
        sidePanel.setVisible(false);
        
        try{
            
            listprod = FXMLLoader.load(getClass().getResource("/viewProd/listProd.fxml"));
            listCommande = FXMLLoader.load(getClass().getResource("/viewCommande/listCommande.fxml"));
            listOrder = FXMLLoader.load(getClass().getResource("/viewOrder/listOrder.fxml"));

            
        } catch (IOException ex) {
            System.out.println(ex);
            }
    }

    
        private void setNode(Node node) {
        centralPanel.getChildren().clear();
        centralPanel.getChildren().add((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }
    
    
    @FXML
    public void onConnectClicked(ActionEvent event)
    {
      if( (tfusername.getText().equals("sunny"))&&(tfpwd.getText().equals("room")) )
      {
          sidePanel.setVisible(true);
          setNode(listprod);
      }
      else errorLabel.setVisible(true);
    }
        
    @FXML
    public void switchProd()
    {
        setNode(listprod);
    }
    

      @FXML
    public void switchCommand()
    {
        setNode(listCommande);
    }
    
    
      @FXML
            public void switchOrder()
    {
        setNode(listOrder);
    }
    
    
    
}
