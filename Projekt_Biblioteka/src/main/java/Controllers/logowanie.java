package Controllers;

import connect.ConnectionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class logowanie implements Initializable {

    @FXML
    private PasswordField haslo_field;

    @FXML
    private TextField nazwa_field;

    @FXML
    private Button rejestracja_button;

    @FXML
    private Button zaloguj_button;

    public static  String login;

    public  String getLogin() {
        return login;
    }





    @FXML
    void zalogujAction(ActionEvent event) throws IOException {
        if (validateLogin() == true) {
           if( login()==true){
               Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
               Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
               stage.setX(1);
               stage.setY(1);
               Scene scene = new Scene(root);
               stage.setScene(scene);
           }else if(login2()==true){
               Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
               Parent root = FXMLLoader.load(getClass().getResource("/fxml/recenzja.fxml"));
               stage.setX(1);
               stage.setY(1);
               Scene scene = new Scene(root);
               stage.setScene(scene);
           }
        }


    }

    public boolean validateLogin() {
        String nazwa = nazwa_field.getText();
        String haslo = haslo_field.getText();
        if (nazwa.equals("") | haslo.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(":( ");
            alert.setHeaderText("Uzupelnij wszystkie pola");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public boolean login() {
        String nazwa = nazwa_field.getText();
        String haslo = haslo_field.getText();
        try {
            Connection con = ConnectionDB.getConnection();
            PreparedStatement pst = con.prepareStatement("select * from admin where nazwa=? and haslo=?");
            pst.setString(1, nazwa);
            pst.setString(2, haslo);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(":)");
                alert.setHeaderText("Zalogowano pomyślnie do admina");
                alert.showAndWait();
                return true;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Alert alert = new Alert(Alert.AlertType.WARNING);
//        alert.setTitle(":)");
//        alert.setHeaderText("Nieprawidłowe dane logowania !");
//        alert.showAndWait();
        return false;

    }
    public boolean login2() {
        login = nazwa_field.getText();
//        setLogin(nazwa_field.getText());
        String haslo = haslo_field.getText();
        try {
            Connection con = ConnectionDB.getConnection();
            PreparedStatement pst = con.prepareStatement("select * from logowanie where login=? and haslo=?");
            pst.setString(1, login);
            pst.setString(2, haslo);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(":)");
                alert.setHeaderText("Zalogowano pomyślnie ");
                alert.showAndWait();
                return true;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(":)");
        alert.setHeaderText("Nieprawidłowe dane logowania !");
        alert.showAndWait();
        return false;

    }

    @FXML
    void zarejestrujAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/rejestracja.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

}
