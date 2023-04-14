package Controllers;

import connect.ConnectionDB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.czytelnicy2;
import models.ksiazki2;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ZarzadzanieZamowieniami  implements Initializable {
    @FXML
    private ComboBox<czytelnicy2> czytelnikBox;

    @FXML
    private TextField iloscField;

    @FXML
    private ComboBox<ksiazki2> ksiazkaBox;

    @FXML
    private Label sumaLabel;

    @FXML
    private Button zamowienie_button;

    @FXML
    private ImageView backIcon;

    @FXML
    void ZlozAction(ActionEvent event) {

        if (ksiazkaBox.getValue() == null || czytelnikBox.getValue()==null ||iloscField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Zamówienia");
            alert.setContentText("Nie wprowadzono wszystkich danych, uzupełnij wszystkie pola");
            alert.showAndWait();

        } else {

            ksiazki2 ksiazkiii = ksiazkaBox.getSelectionModel().getSelectedItem();
            int id = ksiazkiii.getID();
            czytelnicy2 czytelnicyyy = czytelnikBox.getSelectionModel().getSelectedItem();
            int id2 = czytelnicyyy.getID();
            String ilosc = iloscField.getText();

            try {

                Connection con = ConnectionDB.getConnection();

                CallableStatement cstmt = con.prepareCall("{call INSERTORDER(?,?,?)}");

                cstmt.setInt(1, id2);
                cstmt.setInt(2, id);
                cstmt.setString(3, ilosc);

                int status = cstmt.executeUpdate();
                if (status==1 ) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sukces");
                    alert.setHeaderText("");
                    alert.setContentText("Dodano pomyslnie zamówienie!");
                    alert.showAndWait();


                    iloscField.setText("0");
                    ksiazkaBox.valueProperty().set(null);
                    czytelnikBox.valueProperty().set(null);
                    sumaLabel.setText("0 zł");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Blad");
                    alert.setHeaderText("Zamowienia");
                    alert.setContentText("nie dodano pomyslnie");
                    alert.showAndWait();

                }

            } catch (SQLException e) {

                e.printStackTrace();

            }

        }

    }

    @FXML
    void BackAction(MouseEvent event) throws IOException {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
        stage.setX(1);
        stage.setY(1);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
    @FXML
    ObservableList<ksiazki2> oblist1 = FXCollections.observableArrayList();

    public ObservableList<ksiazki2> Ksiegi() throws ClassNotFoundException, SQLException {
        Connection con = ConnectionDB.getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT id_ksiazki,tytul FROM ksiazki");

        ResultSet rs = pst.executeQuery();


        oblist1.clear();

        while (rs.next()) {
            oblist1.add(new ksiazki2(rs.getInt("id_ksiazki"), rs.getString("tytul")));
        }
        return oblist1;

    }

    @FXML
    ObservableList<czytelnicy2> oblist2 = FXCollections.observableArrayList();

    public ObservableList<czytelnicy2> Czytelnicy() throws ClassNotFoundException, SQLException {
        Connection con = ConnectionDB.getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT id_czytelnika,imie,nazwisko FROM czytelnicy ");

        ResultSet rs = pst.executeQuery();

        oblist2.clear();

        while (rs.next()) {
            oblist2.add(new czytelnicy2(rs.getInt("id_czytelnika"), rs.getString("imie"), rs.getString("nazwisko")));
        }
        return oblist2;

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ksiazkaBox.getItems().addAll(Ksiegi());
            czytelnikBox.getItems().addAll(Czytelnicy());

            iloscField.textProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {

                    ksiazki2 ksiazkii = ksiazkaBox.getSelectionModel().getSelectedItem();
                    int id = ksiazkii.getID();
                    int  ilosc = Integer.parseInt(iloscField.getText());

                    Connection con = ConnectionDB.getConnection();
                    try {
                        PreparedStatement pst = con.prepareStatement("SELECT cena from ksiazki where id_ksiazki=?");
                        pst.setInt(1, id);
//                    ResultSet rs = pst.executeQuery();
                        ResultSet rs = pst.executeQuery();
                        while (rs.next()) {
                            int suma = Integer.parseInt(rs.getString("cena")) * ilosc;
                            sumaLabel.setText(Integer.toString(suma) + " zł");
                        }




                    }catch (SQLException e) {
                        e.printStackTrace();
                    }catch(NumberFormatException e){
                        System.out.println(e);
                    }
                }
            });


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch(NumberFormatException e){
            System.out.println(e);
        }


    }
}
