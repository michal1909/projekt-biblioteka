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

public class rejestracja implements Initializable {

    @FXML
    private PasswordField haslo_field;

    @FXML
    private TextField nazwa_field;

    @FXML
    private PasswordField powthaslo_field;

    @FXML
    private Button rejestracja_button;

    @FXML
    private Button zaloguj_button;

    @FXML
    void rejestrujAction(ActionEvent event) {
        if(checkDuplicateUser()==false){
            insertSignupDetails();
        }

    }

    @FXML
    void zalogujAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/logowanie.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

    public void insertSignupDetails() {

        String nazwa = nazwa_field.getText();
        String password = haslo_field.getText();
        String pwtpassword = powthaslo_field.getText();
        try {
            if (nazwa.equals("") || password.equals("") || pwtpassword.equals("")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(":( ");
                alert.setHeaderText("Uzupełnij wszystkie dane !");
                alert.showAndWait();
            } else {
                if (password.equals(pwtpassword)) {


                    Connection con = ConnectionDB.getConnection();
                    String sql = "insert into admin(nazwa,haslo) values(?,?)";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, nazwa);
                    pst.setString(2, password);

                    int update = pst.executeUpdate();
                    if (update > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(":) ");
                        alert.setHeaderText("Dodano pracownika pomyślnie  ");
                        alert.showAndWait();
                        nazwa_field.clear();
                        haslo_field.clear();
                      powthaslo_field.clear();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle(":( ");
                        alert.setHeaderText("Bład ");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(":( ");
                    alert.setHeaderText("powtorzenie hasla jest nieprawidlowe, sprobuj ponownie ");
                    alert.showAndWait();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean checkDuplicateUser() {
        String nazwa = nazwa_field.getText();
        boolean isExist = false;

        try {
            Connection con = ConnectionDB.getConnection();
            PreparedStatement pst = con.prepareStatement("select * from admin where nazwa= ?");
            pst.setString(1, nazwa);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(":( ");
                alert.setHeaderText("Uzytkownik juz istnieje ");
                alert.showAndWait();
                isExist = true;

            }else{
                  isExist = false;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExist;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}

