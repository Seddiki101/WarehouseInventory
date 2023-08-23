/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewProd;


import entity.ConnectionDB;
import entity.Produit;
import entity.SessionManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author k
 */
public class ListProdController implements Initializable {

    @FXML
    private TableView<Produit> tvProd;
    
    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    ObservableList<Produit> prodList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<?, ?> colNom;
    @FXML
    private TableColumn<?, ?> colQte;
    @FXML
    private TableColumn<?, ?> colPrix;
    @FXML
    private TableColumn<?, ?> colVentes;
    @FXML
    private TableColumn<?, ?> colDesc;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private TextField tfsearch;

    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        refreshing();
    }    
    
    
        public ObservableList<Produit> getProdList() {
        cnx = ConnectionDB.getInstance().getCnx();
        prodList.clear();

        try {
            String query2 = "SELECT * FROM  produits  ";
            PreparedStatement smt = cnx.prepareStatement(query2);
            Produit pp;
            ResultSet rs = smt.executeQuery();

            while (rs.next()) {    

                pp = new Produit(rs.getInt("idp"), rs.getString("nom"), rs.getInt("quantity"), rs.getFloat("prix"), rs.getInt("nombreVendu"), rs.getString("descr"), rs.getString("dateprod") );
              //  System.out.println( "he has date"+ pp.getDateProd() );
                prodList.add(pp);
            }


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return prodList;
    }
        
        
        
        
        
        @FXML
    public void refreshing() {
        ObservableList<Produit> list = getProdList();

        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colQte.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colVentes.setCellValueFactory(new PropertyValueFactory<>("nombreVendu"));
        
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateProd"));
        
        colDesc.setCellValueFactory(new PropertyValueFactory<>("descr"));        
        
        
        tvProd.setItems(list);
    }






   
    @FXML
       public void Suppression(ActionEvent event) {    
   Produit rico = (Produit) tvProd.getSelectionModel().getSelectedItem();   
        if( rico !=null ) {
        //
                try {
            String requete="delete from produits where idp=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,rico.getIdp());
            pst.executeUpdate();
            
            System.out.println("produit supprimée");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        //
        refreshing();

    }
        else{
            System.out.println("User not selected ");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SunnyRoom :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un produit dans le tableau ");
                alert.showAndWait();  
        }
    }
   
       
       
       
   
    @FXML
         public void modifProd(Event event )
   {
          
 Produit rico = (Produit) tvProd.getSelectionModel().getSelectedItem();      
       
          if (rico != null) {
            //
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/viewProd/prodEdit.fxml"));
                Parent root77 = loader.load();
                ProdEditController controllerB = loader.getController();
                controllerB.receiveProd(rico);
                // Set the scene for the new FXML file
                
                Scene scene77 = new Scene(root77);
                
                Stage newStage77 = new Stage();
                newStage77.setScene(scene77);
                newStage77.show();

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            //
            refreshing();

        } else {
            System.out.println("Reclamation not selected ");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sunny Room :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un produit dans le tableau ");
            alert.showAndWait();
        }

    }

         
    @FXML
     public void ajoutProd()
     {
                   try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/viewProd/prodAdd.fxml"));
                Parent root77 = loader.load();
                ProdAddController controllerB = loader.getController();
                // Set the scene for the new FXML file
                
                Scene scene77 = new Scene(root77);
                
                Stage newStage77 = new Stage();
                newStage77.setScene(scene77);
                newStage77.show();

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            //
            refreshing();  
     }
     
 
     
     
    @FXML
  public void search() {
        FilteredList<Produit> filteredData = new FilteredList<>(prodList, p -> true);
        tfsearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(employe -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (employe.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (employe.getDescr().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (employe.getDateProd().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        // wrap the filtered list in a SortedList
        SortedList<Produit> sortedData = new SortedList<>(filteredData);
        // bind the SortedList comparator to the TableView comparator
        sortedData.comparatorProperty().bind(tvProd.comparatorProperty());
        // add sorted (and filtered) data to the table
        tvProd.getItems().clear();
        tvProd.setItems(sortedData);
    }     
     
     
/*
public void searching() {
    FilteredList<Produit> filteredList = new FilteredList<>(prodList, p -> true);

    // Add a change listener to the search field to update the filtered list
    tfsearch.textProperty().addListener((observable, oldValue, newValue) -> {
        String lowerCaseSearch = newValue.toLowerCase();

        // If search text is empty, show all users
        if (newValue == null || newValue.isEmpty()) {
            filteredList.setPredicate(p -> true);
        } else {
            filteredList.setPredicate(pp ->
                pp.getNom().toLowerCase().contains(lowerCaseSearch) ||
                pp.getDescr().toLowerCase().contains(lowerCaseSearch) ||
                pp.getDateProd().toLowerCase().contains(lowerCaseSearch)
            );
        }
    });

    // Wrap the filtered list in a SortedList to enable sorting
    SortedList<Produit> sortedList = new SortedList<>(filteredList);

    // Bind the sorted list to the TableView
    tvProd.getItems().clear();
    tvProd.setItems(sortedList);
}
*/
         

    @FXML
  public void acheter()
  {
   
    Produit rico = (Produit) tvProd.getSelectionModel().getSelectedItem();      
       
          if (rico != null) {
              
            rico.setQuantity(1);
              
              SessionManager.addToCart(rico);
              //show panier + print
              //if confirmed order ( decrease qte + create order with time

          }   
      
  }
  







public Text generatePrintContent() {
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

    for (Produit produit : prodList) {
        content.append(produit.getNom()).append("\t").append("\t");
        content.append(produit.getQuantity()).append("\t").append("\t");
        content.append(produit.getPrix()).append("\n");
    }

    Text textNode = new Text(content.toString());
    return textNode;
}






@FXML
public void printProducts(ActionEvent event) {
    Text printContent = generatePrintContent();

    PrinterJob job = PrinterJob.createPrinterJob();
    if (job != null) {
        boolean success = job.printPage(printContent);
        if (success) {
            job.endJob();
        }
    }
}


  
    
}
