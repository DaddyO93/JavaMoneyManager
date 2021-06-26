package com.moneyManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static javafx.scene.control.cell.TextFieldTableCell.forTableColumn;

public class AddAccountPageController implements Initializable {
    @FXML private MenuBar menuBar;

    @FXML private TextField entryAccountName;
    @FXML private TextField entryAccountStartingAmount;
    @FXML private TextField entryAccountInstitutionName;
    @FXML private TextField entryItemAccountNumber;

    @FXML private TableView<Account> accountTableView;

    @FXML private TableColumn<Account, String> accountNameColumn;
    @FXML private TableColumn<Account, String> accountStartingAmountColumn;
    @FXML private TableColumn<Account, String> institutionNameColumn;
    @FXML private TableColumn<Account, Integer> accountNumberColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            updateAccountTableView();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        accountNameColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("name"));
        accountStartingAmountColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("account_amount"));
        institutionNameColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("account_institution"));
        accountNumberColumn.setCellValueFactory(new PropertyValueFactory<Account, Integer>("account_number"));

        try {
            accountTableView.setItems(getAccountItems());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        accountTableView.setEditable(true);
        accountNameColumn.setCellFactory(forTableColumn());
        accountStartingAmountColumn.setCellFactory(forTableColumn());
        institutionNameColumn.setCellFactory(forTableColumn());
        accountNumberColumn.setCellFactory(p -> new TextFieldTableCell<>());

//        accountNumberColumn.setCellFactory(forTableColumn());
    }

    @FXML public void addEntryAccountButtonPushed() throws SQLException {
        String accountName = entryAccountName.getText();
        String startingAmountString = entryAccountStartingAmount.getText();
        String institutionName = entryAccountInstitutionName.getText();
        Integer accountNumber = Integer.valueOf(entryItemAccountNumber.getText());

        //  Convert StartingAmount String to Currency String
        String startingAmount = DataScrubber.convertToCurrency(startingAmountString);

        accountName = DataScrubber.stringCannotBeNull(accountName);
        startingAmount = DataScrubber.convertToCurrency(startingAmountString);
        institutionName = DataScrubber.stringCannotBeNull(institutionName);
        accountNumber = DataScrubber.integerCannotBeNull(accountNumber);

        //  Add to DataBase
        AccountSQLDriver.addToAccounts(accountName, startingAmount, institutionName, accountNumber);

        //  Retrieve list from DataBase and add to TableView
        updateAccountTableView();
    }

    //  Delete item(s) from Database
    @FXML private void deleteSelectedAccountsButtonPushed() throws SQLException {
        for (Account account: accountTableView.getSelectionModel().getSelectedItems()){
            Utilities.removeFromDatabase(account.getId(), "accounts");
        }
        updateAccountTableView();
    }

    //  Clear Tableview, then update from Database
    private void updateAccountTableView() throws SQLException {
        accountTableView.getItems().clear();
//        ObservableList<Account> accounts = FXCollections.observableArrayList();
//        accounts.addAll(AccountSQLDriver.getAccountArrayList(Utilities.returnResultSetFromDatabase
//                ("accounts")));
//        accountTableView.setItems(accounts);
        accountTableView.setItems(getAccountItems());
    }

    private ObservableList<Account> getAccountItems() throws SQLException {
        ObservableList<Account> accounts = FXCollections.observableArrayList();
        accounts.addAll(AccountSQLDriver.getAccountArrayList(Utilities.returnResultSetFromDatabase
                ("accounts")));
        return accounts;
    }

    private void changePage(String pageName) throws IOException {
        Parent parentPage = FXMLLoader.load(getClass().getResource(pageName));
        Scene scenePage = new Scene(parentPage);
        Stage window = (Stage) menuBar.getScene().getWindow(); // Using 'menuBar" bc using menuItem
        window.setTitle("Money Manager");
        Image icon = new Image("icon.jpg");
        window.getIcons().add(icon);
        window.setScene(scenePage);
        window.show();
    }

    public void changeToMainPage() throws IOException{
        changePage("MainPage.fxml");
    }

    public void changeToAddAccountPage(ActionEvent event) throws IOException{
        changePage("AddAccountPage.fxml");
    }

    public void changeToAddItemPage(ActionEvent event) throws IOException{
        changePage("AddItemPage.fxml");
    }

}
