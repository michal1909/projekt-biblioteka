package Controllers;

import connect.ConnectionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.czytelnicy;
import models.ksiazki;
import models.wypozyczenia;
import oracle.jdbc.OracleTypes;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class baza implements Initializable {
    @FXML
    private ImageView backIcon;

    @FXML
    private TableColumn<?, ?> czy_aktywne;

    @FXML
    private TableColumn<?, ?> czy_aktywne2;

    @FXML
    private TableColumn<wypozyczenia, String> czy_oddane;

    @FXML
    private TableColumn<wypozyczenia, String> czy_oddane2;

    @FXML
    private TableColumn<?, ?> data_odbioru;

    @FXML
    private TableColumn<?, ?> data_odbioru2;

    @FXML
    private TableColumn<wypozyczenia, String> data_wypozyczenia;

    @FXML
    private TableColumn<wypozyczenia, String> data_wypozyczenia2;

    @FXML
    private TableColumn<wypozyczenia, String> data_zwrotu;

    @FXML
    private TableColumn<wypozyczenia, String> data_zwrotu2;

    @FXML
    private TableColumn<wypozyczenia, Number> id_czytelnika;

    @FXML
    private TableColumn<wypozyczenia, Number> id_czytelnika2;

    @FXML
    private TableColumn<?, ?> id_czytelnikaZ;

    @FXML
    private TableColumn<?, ?> id_czytelnikaZ2;

    @FXML
    private TableColumn<wypozyczenia, Number> id_egzemplarza;

    @FXML
    private TableColumn<wypozyczenia, Number> id_egzemplarza2;

    @FXML
    private TableColumn<?, ?> id_ksiazki2;

    @FXML
    private TableColumn<wypozyczenia, Number> id_wypozyczenia;

    @FXML
    private TableColumn<?, ?> id_wypozyczenia2;

    @FXML
    private TableColumn<?, ?> id_zamowienia;

    @FXML
    private TableColumn<?, ?> id_zamowienia2;

    @FXML
    private TableColumn<?, ?> ilosc;

    @FXML
    private TableColumn<?, ?> ilosc2;

    @FXML
    private TableColumn<?, ?> suma;

    @FXML
    private TableColumn<?, ?> suma2;

    @FXML
    private TableView<wypozyczenia> wypozyczeniaTable;

    @FXML
    private TableView<?> wypozyczeniaTable2;

    @FXML
    private TableView<?> zamowieniaTable;

    @FXML
    private TableView<?> zamowieniaTable2;

    @FXML
    void BackAction(MouseEvent event) throws IOException {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
        stage.setX(1);
        stage.setY(1);
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }
    public void tableWypozyczenia() throws SQLException {
        ObservableList<wypozyczenia> wypozyczenia = FXCollections.observableArrayList();
        try {
            Connection con = ConnectionDB.getConnection();
            PreparedStatement pst = con.prepareStatement("select * from wypozyczenia");
            ResultSet rs = pst.executeQuery();
            {
                while (rs.next()) {
                   wypozyczenia wypozyczenia2 = new wypozyczenia();
                    wypozyczenia2.setId_wypozyczenia(rs.getInt("id_wypozyczenia"));
                    wypozyczenia2.setId_egzemplarza(rs.getInt("id_egzemplarza"));
                    wypozyczenia2.setId_czytelnika(rs.getInt("id_czytelnika"));
                    wypozyczenia2.setData_wypozyczenia(rs.getString("data_wypozyczenia"));
                    wypozyczenia2.setData_zwrotu(rs.getString("data_zwrotu"));
                    wypozyczenia2.setCzy_oddane(rs.getString("czy_oddane"));

                   wypozyczenia.add(wypozyczenia2);
                }
            }
           wypozyczeniaTable.setItems(wypozyczenia);
           id_wypozyczenia.setCellValueFactory(f -> f.getValue().id_wypozyczeniaProperty());
           id_egzemplarza.setCellValueFactory(f -> f.getValue().id_egzemplarzaProperty());
            id_czytelnika.setCellValueFactory(f -> f.getValue().id_czytelnikaProperty());
            data_wypozyczenia.setCellValueFactory(f -> f.getValue().data_wypozyczeniaProperty());
           data_zwrotu.setCellValueFactory(f -> f.getValue().data_zwrotuProperty());
            czy_oddane.setCellValueFactory(f -> f.getValue().czy_oddaneProperty());

        } catch (SQLException throwables) {
            Logger.getLogger(ZarzadzanieCzytelnikami.class.getName()).log(Level.SEVERE, null, throwables);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            tableWypozyczenia();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
