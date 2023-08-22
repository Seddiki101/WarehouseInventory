/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author k
 */


public class ConnectionDB {

    public String url = "jdbc:sqlite:./util/sr.db"; 
    Connection cnx;

    private static ConnectionDB instance;

    public ConnectionDB() {
        try {
            cnx = DriverManager.getConnection(url);
            System.out.println("Established connection");
        } catch (SQLException ex) {
            System.err.print(ex.getMessage());
        }
    }

    public Connection getCnx() {
        return cnx;
    }

    public static ConnectionDB getInstance() {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }
}
