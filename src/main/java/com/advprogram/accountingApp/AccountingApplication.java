package main.java.com.advprogram.accountingApp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.com.advprogram.accountingApp.core.LoginManager;

public class AccountingApplication extends Application {
    @Override
    public void start(Stage stage){
        Scene scene = new Scene(new AnchorPane());
        LoginManager loginManager = new LoginManager(scene);
        loginManager.showLoginScreen();
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) { launch(args); }
}