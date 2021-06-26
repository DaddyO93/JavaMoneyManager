package com.moneyManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    @FXML
    MenuBar menuBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

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

    public void changeToMainPage(ActionEvent event) throws IOException{
//        changePage("MainPage.fxml");
    }

    public void changeToAddAccountPage(ActionEvent event) throws IOException{
        changePage("AddAccountPage.fxml");
    }

    public void changeToAddItemPage(ActionEvent event) throws IOException{
        changePage("AddItemPage.fxml");
    }

}
