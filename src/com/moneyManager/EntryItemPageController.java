package com.moneyManager;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static javafx.scene.control.cell.TextFieldTableCell.forTableColumn;

public class EntryItemPageController implements Initializable {
    @FXML private MenuBar menuBar;

    @FXML private TextField entryItemName;
    @FXML private TextField entryItemAmount;
    @FXML private DatePicker entryItemDueDate;
    @FXML private ChoiceBox<String> categoryChoiceBox;
    @FXML private TextField entryItemPayee;
    @FXML private ChoiceBox<String> accountNameChoiceBox;
    @FXML private CheckBox entryItemIsRecurring;

    @FXML private TableView<EntryItem> budgetItemTableView;

    @FXML private TableColumn<EntryItem, String> nameColumn;
    //  amountColumn is String instead of Integer because was converted to currency String
    @FXML private TableColumn<EntryItem, String> amountColumn;
    @FXML private TableColumn<EntryItem, String> dueDateColumn;
    @FXML private TableColumn<EntryItem, String> categoryColumn;
    @FXML private TableColumn<EntryItem, String> payeeColumn;
    @FXML private TableColumn<EntryItem, String> accountNameColumn;
    @FXML private TableColumn<EntryItem, Boolean> isRecurringColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryChoiceBox.getItems().addAll(Utilities.categoryList);

        ObservableList<String> accountNames = Utilities.getListOfDatabaseColumnEntries("accounts", "name");
        accountNameChoiceBox.getItems().addAll(accountNames);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        payeeColumn.setCellValueFactory(new PropertyValueFactory<>("payee"));
        accountNameColumn.setCellValueFactory(new PropertyValueFactory<>("accountName"));

        /**
         * To be able to edit checkbox in table, need following code
         * Used lambda expression
         * Checkbox is live in tableview
         */
        isRecurringColumn.setCellValueFactory(param -> {
            EntryItem entry = param.getValue();
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(entry.getIsRecurring());
            booleanProp.addListener((observable, oldValue, newValue) -> entry.setIsRecurring(newValue));
            return booleanProp;
        });

        try {
            budgetItemTableView.setItems(getBudgetItems());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        budgetItemTableView.setEditable(true);
        isRecurringColumn.setCellFactory(p -> new CheckBoxTableCell<>());
        nameColumn.setCellFactory(forTableColumn());
        amountColumn.setCellFactory(forTableColumn());
        dueDateColumn.setCellFactory(forTableColumn());
        categoryColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(Utilities.categoryList));
        payeeColumn.setCellFactory(forTableColumn());
        accountNameColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(accountNames));
    }

    private ObservableList<EntryItem> getBudgetItems() throws SQLException, NullPointerException {
        ObservableList<EntryItem> items = FXCollections.observableArrayList();
        items.addAll(EntryItemSQLDriver.getEntryItemArrayList(Utilities.returnResultSetFromDatabase(
                "entryitems")));
        return items;
    }

    @FXML private void deleteSelectedBudgetItemsButtonPushed() throws SQLException {
        for (EntryItem entryItem: budgetItemTableView.getSelectionModel().getSelectedItems()){
            Utilities.removeFromDatabase(entryItem.getId(), "entryitems");
        }
        updateEntryItemTableView();
    }

    @FXML private void addBudgetItemButtonPushed() throws SQLException {
        String name = entryItemName.getText();
        String amountString = entryItemAmount.getText();
        String category = categoryChoiceBox.getValue();
        LocalDate tempDueDate = entryItemDueDate.getValue();
        String payee = entryItemPayee.getText();
        String accountName = accountNameChoiceBox.getValue();
        Boolean isRecurring = entryItemIsRecurring.isSelected();

        name = DataScrubber.stringCannotBeNull(name);
        //  Convert Amount String to Currency String
        String currencyString = DataScrubber.convertToCurrency(amountString);
        category = DataScrubber.stringCannotBeNull(category);
        /**
         *    Convert dueDate LocalDate to a String so it can be edited in the table
         *    Will need to convert back to LocalDate to use with calendar elsewhere
         */
        String dueDate = DataScrubber.DateUtil.formatLocalDateToString(tempDueDate);
        payee = DataScrubber.stringCannotBeNull(payee);
        accountName = DataScrubber.stringCannotBeEmpty(accountName);
        isRecurring = DataScrubber.mustBeBoolean(isRecurring);

        EntryItemSQLDriver.addToEntryItem(
                name,
                currencyString,
                category,
                null,
                dueDate,
                null,
                payee,
                accountName,
                isRecurring);
        ResultSet resultSet = Utilities.getSingleObject("name", name,"entryitems");
        EntryItem entryItem = EntryItemSQLDriver.convertRowToSingleEntryItem(resultSet);
        budgetItemTableView.getItems().add(entryItem);
    }

    private void updateEntryItemTableView() throws SQLException {
        budgetItemTableView.getItems().clear();
        ObservableList<EntryItem> entryItems = FXCollections.observableArrayList();
        entryItems.addAll(EntryItemSQLDriver.getEntryItemArrayList(Utilities.returnResultSetFromDatabase("entryitems")));
        budgetItemTableView.setItems(entryItems);
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

    public void changeLabelCellEvent(CellEditEvent editedCell) {
        EntryItem itemSelected = budgetItemTableView.getSelectionModel().getSelectedItem();
        itemSelected.setName(editedCell.getNewValue().toString());
    }

    public void changeAmountCellEvent(CellEditEvent editedCell) {
        EntryItem itemSelected = budgetItemTableView.getSelectionModel().getSelectedItem();
        itemSelected.setAmount(editedCell.getNewValue().toString());
    }

    public void changeCategoryCellEvent(CellEditEvent editedCell) {
        EntryItem itemSelected = budgetItemTableView.getSelectionModel().getSelectedItem();
        itemSelected.setCategory(editedCell.getNewValue().toString());
    }

    public void changeDueDateCellEvent(CellEditEvent editCell) {
        EntryItem itemSelected = budgetItemTableView.getSelectionModel().getSelectedItem();
        itemSelected.setDueDate((String) editCell.getNewValue());
    }

    public void changePayeeCellEvent(CellEditEvent editedCell) {
        EntryItem itemSelected = budgetItemTableView.getSelectionModel().getSelectedItem();
        itemSelected.setPayee((Integer) editedCell.getNewValue());
    }

    public void changeAccountNumberCellEvent(CellEditEvent editCell) {
        EntryItem itemSelected = budgetItemTableView.getSelectionModel().getSelectedItem();
        itemSelected.setAccount((Integer) editCell.getNewValue());
    }

    public void changeIsRecurringCellEvent(CellEditEvent editCell) {
        EntryItem itemSelected = budgetItemTableView.getSelectionModel().getSelectedItem();
        itemSelected.setIsRecurring((Boolean) editCell.getNewValue());
    }
}
