/**
 * @Author      : Milko Del Castillo
 * @Version     : v. 1.0
 * @Since       : 11/09/2018
 * FileName     : AdminDriver.java
 * Description  : This class handles the admin GUI. you can search, add, delete information into the Database
 */

package admin;

import DataBase.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AdminDriver implements Initializable{

    @FXML
    public TextField idAdmin;
    @FXML
    public TextField nameAdmin;
    @FXML
    public TextField lastNameAdmin;
    @FXML
    public TextField emailAdmin;
    @FXML
    public TextField phoneAdmin;
    @FXML
    public TextArea resultConsole;
    @FXML
    public TextField clientID;
    @FXML
    private TableView clientTable;
    @FXML
    private TableColumn<ClientsData, String> idColumn;
    @FXML
    private TableColumn<ClientsData, String> nameColumn;
    @FXML
    private TableColumn<ClientsData, String> lastNameColumn;
    @FXML
    private TableColumn<ClientsData, String> emailColumn;
    @FXML
    private TableColumn<ClientsData, String> phoneColumn;

    private dbConnection dc;  // initialized a connection instance named dc
    private ObservableList<ClientsData> clientdata; // declare a observable list with holds ClientData objects

    //declare a select statement for search all client
    private String sqlAll = "SELECT * FROM client";

    //Query to add a client to table - insert into client table = id, first name, last name, email, phone number
    private String sqlAdd = "INSERT INTO client (id, fname, lname, email, phone) VALUES (?,?,?,?,?)";

    //Query to search by ID
    private String sqlSearch= "SELECT * FROM client WHERE id =" + clientID;

    public  void initialize (URL url, ResourceBundle rb){
        this.dc = new dbConnection(); //for new connection
    }

    @FXML  //to search all clients
    private void loadClientData(ActionEvent event){
        try{
            Connection conn = dbConnection.getConnection();
            this.clientdata= FXCollections.observableArrayList();

            //store result of query into set variable
            ResultSet set= conn.createStatement().executeQuery(sqlAll);

            while(set.next()){ //until the last row on the database
                this.clientdata.add(new ClientsData(set.getString(1), set.getString(2),
                        set.getString(3),set.getString(4), set.getString(5)));
            }

        }catch(SQLException e){
            System.err.println("Error Client data method" + e);
        }

        //display the data in table GUI
        this.idColumn.setCellValueFactory(new PropertyValueFactory<ClientsData, String>("clientID"));
        this.nameColumn.setCellValueFactory(new PropertyValueFactory<ClientsData, String>("firstName"));
        this.lastNameColumn.setCellValueFactory(new PropertyValueFactory<ClientsData, String>("lastName"));
        this.emailColumn.setCellValueFactory(new PropertyValueFactory<ClientsData, String>("email"));
        this.phoneColumn.setCellValueFactory(new PropertyValueFactory<ClientsData, String>("phoneNumber"));

        this.clientTable.setItems(null);            //set table to null
        this.clientTable.setItems(this.clientdata); // fill table with info
    }//end of loadClientData method

    @FXML //to add client
    private void addClient(ActionEvent event){
        try{
            Connection conn = dbConnection.getConnection();
            PreparedStatement statementToAdd = conn.prepareStatement(sqlAdd);

            statementToAdd.setString(1, this.idAdmin.getText());
            statementToAdd.setString(2, this.nameAdmin.getText());
            statementToAdd.setString(3, this.lastNameAdmin.getText());
            statementToAdd.setString(4, this.emailAdmin.getText());
            statementToAdd.setString(5, this.phoneAdmin.getText());

            statementToAdd.execute();
            conn.close();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }//end addClient method

    @FXML
    //This method clears the form to add client into the table Clients
    private void clearForm(ActionEvent event){
        this.idAdmin.setText("");
        this.nameAdmin.setText("");
        this.lastNameAdmin.setText("");
        this.phoneAdmin.setText("");
        this.emailAdmin.setText("");
    }

    //ERROR ********************************************************************************************************************
    @FXML //to search client
    private void searchClient(ActionEvent event){
        try{
            Connection conn = dbConnection.getConnection();
            this.clientdata= FXCollections.observableArrayList();
            ResultSet setTwo= conn.createStatement().executeQuery(sqlSearch);

            //PreparedStatement statementToSearch = conn.prepareStatement(sqlSearch);

            while(setTwo.next()){ //until the last row on the database
                this.clientdata.add(new ClientsData(setTwo.getString(1), setTwo.getString(2),
                        setTwo.getString(3),setTwo.getString(4), setTwo.getString(5)));
            }

            //display the data in table GUI
            this.idColumn.setCellValueFactory(new PropertyValueFactory<ClientsData, String>("idColumn"));
            this.nameColumn.setCellValueFactory(new PropertyValueFactory<ClientsData, String>("nameColumn"));
            this.lastNameColumn.setCellValueFactory(new PropertyValueFactory<ClientsData, String>("lastNameColumn"));
            this.emailColumn.setCellValueFactory(new PropertyValueFactory<ClientsData, String>("emailColumn"));
            this.phoneColumn.setCellValueFactory(new PropertyValueFactory<ClientsData, String>("phoneColumn"));

           // statementToSearch.execute();
           // conn.close();

        }catch(SQLException e){
            e.printStackTrace();
        }
        this.clientTable.setItems(null); //set table to null
        this.clientTable.setItems(this.clientdata); // fill table with info
    }//end searchClient method

    //ERROR ********************************************************************************************************************

}
