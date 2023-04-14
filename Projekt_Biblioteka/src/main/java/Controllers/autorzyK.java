package Controllers;

import connect.ConnectionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.autorzy;
import models.ksiazki;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class autorzyK implements Initializable {
    @FXML
    private ImageView backIcon;

    @FXML
    private Button dodaj_button;

    @FXML
    private TextField imie_field;

    @FXML
    private TextField nazwisko_field;


//    @FXML
//    void BackAction(MouseEvent event) throws IOException {
//        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ZarzadzanieKsiazkami.fxml"));
//        stage.setX(1);
//        stage.setY(1);
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//
//
//    }


    public void refreshTable() throws SQLException {
        ObservableList<autorzy> autorzy = FXCollections.observableArrayList();
        autorzy.clear();
        Connection con = ConnectionDB.getConnection();

        PreparedStatement pst = con.prepareStatement("SELECT * FROM autorzy");
//        CallableStatement cstmt = con.prepareCall("{call SELECTBOOKS(?)}");
//        cstmt.registerOutParameter (1, OracleTypes.CURSOR);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            autorzy autorzy2 = new autorzy();
            autorzy2.setModel(rs.getString("imie"));
          autorzy2.setModel2(rs.getString("nazwisko"));

            autorzy.add(autorzy2);


        }



    }





    @FXML
    void dodaj_action(ActionEvent event) {
        if (   imie_field.getText().isEmpty() || nazwisko_field.getText().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Autorzy");
            alert.setContentText("Nie wprowadzono wszystkich danych, uzupełnij wszystkie pola");
            alert.showAndWait();

        } else {
            String imie =imie_field.getText();
            String nazwisko = nazwisko_field.getText();

            try {
                Connection con = ConnectionDB.getConnection();

                CallableStatement cstmt = con.prepareCall("{call INSERTAUTOR(?,?)}");

                cstmt.setString(1,imie);
                cstmt.setString(2, nazwisko);


                int status = cstmt.executeUpdate();
                if (status == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sukces");
                    alert.setHeaderText("");
                    alert.setContentText("Dodano pomyslnie autora !");
                    alert.showAndWait();

                    imie_field.setText("");
                   nazwisko_field.setText("");



                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Blad");
                    alert.setHeaderText("Autorzy");
                    alert.setContentText("nie dodano pomyslnie");
                    alert.showAndWait();

                }
                refreshTable();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        }





    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {




    }
}
