/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewOrder;

import entity.ConnectionDB;
import entity.Order;
import entity.Produit;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author k
 */
public class ListOrderController implements Initializable {

    
        private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    ObservableList<Order> orderList = FXCollections.observableArrayList();
    
    
    @FXML
    private TableView<Order> tvOrder;
    @FXML
    private TableColumn<?, ?> colNom;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private TableColumn<?, ?> colQte;
    @FXML
    private TableColumn<?, ?> colPrixU;
    @FXML
    private TableColumn<?, ?> colPrixT;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        refresh();
    }    
    
    
    
    public ObservableList<Order> getOrderList() {
    cnx = ConnectionDB.getInstance().getCnx();
    ObservableList<Order> orderList = FXCollections.observableArrayList();

    try {
        String query = "SELECT * FROM orders";
        PreparedStatement smt = cnx.prepareStatement(query);
        ResultSet rs = smt.executeQuery();

        while (rs.next()) {
            int ido = rs.getInt("ido");
            String dateOrder = rs.getString("dateOrder");
            String prodName = rs.getString("prodName");
            int orderQuantity = rs.getInt("orderQuantity");
            float prixUnitaire = rs.getFloat("prixUnitaire");
            float prixTotal = rs.getFloat("prixTotal");

            Order order = new Order(ido, dateOrder, prodName, orderQuantity, prixUnitaire, prixTotal);
            orderList.add(order);
        }

    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return orderList;
}

    
    
    
    
        public void refresh() {
        ObservableList<Order> list = getOrderList();

        colNom.setCellValueFactory(new PropertyValueFactory<>("prodName"));
        
                colDate.setCellValueFactory(new PropertyValueFactory<>("dateOrder"));
                
        colQte.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));

        
        colPrixU.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
        
        colPrixT.setCellValueFactory(new PropertyValueFactory<>("prixTotal"));
       
        
        
        tvOrder.setItems(list);
    }
    
        
        
        
        
        
    
public Text generatePrintContent2() {
    String storeName = "Sunny Room";
    LocalDate currentDate = LocalDate.now();
    String address = "Rue Environnement, Jendouba                       " +currentDate ;
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
    content.append("Date").append("\t").append("\t");
    
        content.append("\n");
        content.append("\n");

    for (Order produit : tvOrder.getItems() ) {
        content.append(produit.getProdName()).append("\t").append("\t");
        content.append(produit.getOrderQuantity()).append("\t").append("\t");
        content.append(produit.getPrixUnitaire()).append("\t").append("\t");
        content.append(produit.getDateOrder()).append("\n");
    }

    Text textNode = new Text(content.toString());
    return textNode;
}






@FXML
public void printOrders(ActionEvent event) {
    Text printContent = generatePrintContent2();

    PrinterJob job = PrinterJob.createPrinterJob();
    if (job != null) {
        boolean success = job.printPage(printContent);
        if (success) {
            job.endJob();
        }
    }
}    
        
        
        
        
        
        
        
    
    
}
