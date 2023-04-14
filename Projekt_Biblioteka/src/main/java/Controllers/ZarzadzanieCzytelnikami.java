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
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.czytelnicy;
import models.ksiazki;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ZarzadzanieCzytelnikami implements Initializable {

    @FXML
    private TextField adres_field;

    @FXML
    private Button aktualizuj_button;

    @FXML
    private ImageView backIcon;

    @FXML
    private CheckBox blokada;

    @FXML
    private TableColumn<czytelnicy, String> c_adres;

    @FXML
    private TableColumn<czytelnicy, Number> c_blokada;

    @FXML
    private TableColumn<czytelnicy, Number> c_id_czytelnika;

    @FXML
    private TableColumn<czytelnicy, String> c_imie;

    @FXML
    private TableColumn<czytelnicy, String> c_kod_pocztowy;

    @FXML
    private TableColumn<czytelnicy, String> c_miasto;

    @FXML
    private TableColumn<czytelnicy, String> c_nazwisko;

    @FXML
    private Button dodaj_button;

    @FXML
    private TextField imie_field;

    @FXML
    private TextField kp1_field;

    @FXML
    private TextField kp2_field;

    @FXML
    private TableView<czytelnicy> czytelnicy_table;

    @FXML
    private TextField miasto_field;

    @FXML
    private TextField nazwisko_field;

    @FXML
    private Button usun_button;

    @FXML
    void BackAction(MouseEvent event) throws IOException {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
        stage.setX(1);
        stage.setY(1);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void refreshTable() throws SQLException {
        ObservableList<czytelnicy> czytelnicy = FXCollections.observableArrayList();
        czytelnicy.clear();
        Connection con = ConnectionDB.getConnection();

        PreparedStatement pst = con.prepareStatement("SELECT * FROM czytelnicy");
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            czytelnicy czytelnicy2 = new czytelnicy();
            czytelnicy2.setId_czytelnika(rs.getInt("id_czytelnika"));
            czytelnicy2.setNazwisko(rs.getString("nazwisko"));
            czytelnicy2.setImie(rs.getString("imie"));
            czytelnicy2.setAdres(rs.getString("adres"));
            czytelnicy2.setmiasto(rs.getString("miasto"));
            czytelnicy2.setKod_pocztowy(rs.getString("kod_pocztowy"));
            czytelnicy2.setBlokada(rs.getInt("blokada"));
            czytelnicy.add(czytelnicy2);
        }

        czytelnicy_table.setItems(czytelnicy);
        c_id_czytelnika.setCellValueFactory(f -> f.getValue().id_czytelnikaProperty());
        c_nazwisko.setCellValueFactory(f -> f.getValue().nazwiskoProperty());
        c_imie.setCellValueFactory(f -> f.getValue().imieProperty());
        c_adres.setCellValueFactory(f -> f.getValue().adresProperty());
        c_miasto.setCellValueFactory(f -> f.getValue().miastoProperty());
        c_kod_pocztowy.setCellValueFactory(f -> f.getValue().kod_pocztowyProperty());
        c_blokada.setCellValueFactory(f -> f.getValue().blokadaProperty());
    }

    public void table() throws SQLException {
        ObservableList<czytelnicy> czytelnicy = FXCollections.observableArrayList();
        try {
            Connection con = ConnectionDB.getConnection();
            PreparedStatement pst = con.prepareStatement("select * from czytelnicy");
            ResultSet rs = pst.executeQuery();
            {
                while (rs.next()) {
                    czytelnicy czytelnicy2 = new czytelnicy();
                    czytelnicy2.setId_czytelnika(rs.getInt("id_czytelnika"));
                    czytelnicy2.setNazwisko(rs.getString("nazwisko"));
                    czytelnicy2.setImie(rs.getString("imie"));
                    czytelnicy2.setAdres(rs.getString("adres"));
                    czytelnicy2.setmiasto(rs.getString("miasto"));
                    czytelnicy2.setKod_pocztowy(rs.getString("kod_pocztowy"));
                    czytelnicy2.setBlokada(rs.getInt("blokada"));
                    czytelnicy.add(czytelnicy2);
                }
            }
            czytelnicy_table.setItems(czytelnicy);
            c_id_czytelnika.setCellValueFactory(f -> f.getValue().id_czytelnikaProperty());
            c_nazwisko.setCellValueFactory(f -> f.getValue().nazwiskoProperty());
            c_imie.setCellValueFactory(f -> f.getValue().imieProperty());
            c_adres.setCellValueFactory(f -> f.getValue().adresProperty());
            c_miasto.setCellValueFactory(f -> f.getValue().miastoProperty());
            c_kod_pocztowy.setCellValueFactory(f -> f.getValue().kod_pocztowyProperty());
            c_blokada.setCellValueFactory(f -> f.getValue().blokadaProperty());
        } catch (SQLException throwables) {
            Logger.getLogger(ZarzadzanieCzytelnikami.class.getName()).log(Level.SEVERE, null, throwables);
        }
    }


    @FXML
    void aktualizujAction(ActionEvent event) {
        if (nazwisko_field.getText().isEmpty() || imie_field.getText().isEmpty() || miasto_field.getText().isEmpty() || kp1_field.getText().isEmpty() || kp2_field.getText().isEmpty() || adres_field.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Czytelnicy");
            alert.setContentText("Nie wprowadzono wszystkich danych, uzupełnij wszystkie pola!");
            alert.showAndWait();

        } else {
            String nazwisko = nazwisko_field.getText();
            String imie = imie_field.getText();
            String adres = adres_field.getText();
            String miasto = miasto_field.getText();
            String kp1 = kp1_field.getText();
            String kp2 = kp2_field.getText();
            String full = kp1_field.getText() + "-" + kp2_field.getText();
            boolean check = blokada.isSelected();

            try {
                Connection con = ConnectionDB.getConnection();
                czytelnicy czytelnicy2 = new czytelnicy();
                ObservableList<czytelnicy> czytelnicy = FXCollections.observableArrayList();

                CallableStatement cstmt = con.prepareCall("{call UPDATEREADERS(?,?,?,?,?,?,?)}");
                czytelnicy czytelnicyy = czytelnicy_table.getSelectionModel().getSelectedItem();
                int id = czytelnicyy.getId_czytelnika();
                cstmt.setInt(1, id);
                cstmt.setString(2, imie);
                cstmt.setString(3,nazwisko);
                cstmt.setString(4, adres);
                cstmt.setString(5, miasto);
                cstmt.setString(6, full);
                cstmt.setBoolean(7, check);
                cstmt.execute();
                refreshTable();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            nazwisko_field.setText("");
            imie_field.setText("");
            adres_field.setText("");
            miasto_field.setText("");
            kp1_field.setText("");
            kp2_field.setText("");
            blokada.setSelected(false);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sukces");
            alert.setHeaderText("Czytelnicy");
            alert.setContentText("Edycja przebiegła pomyślnie");
            alert.showAndWait();


        }


    }

    @FXML
    void dodaj_action(ActionEvent event) {

        if (nazwisko_field.getText().isEmpty() || imie_field.getText().isEmpty() || adres_field.getText().isEmpty() || miasto_field.getText().isEmpty() || kp1_field.getText().isEmpty() || kp2_field.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Czytelnicy");
            alert.setContentText("Nie wprowadzono wszystkich danych, uzupełnij wszystkie pola");
            alert.showAndWait();

        } else {
            String nazwisko = nazwisko_field.getText();
            String imie = imie_field.getText();
            String adres = adres_field.getText();
            String miasto = miasto_field.getText();
            String kp1 = kp1_field.getText();
            String kp2 = kp2_field.getText();
            String full = kp1_field.getText() + "-" + kp2_field.getText();
            boolean check = blokada.isSelected();

            try {
                Connection con = ConnectionDB.getConnection();

                CallableStatement cstmt = con.prepareCall("{call INSERTREADERS(?,?,?,?,?,?)}");

                cstmt.setString(1, imie);
                cstmt.setString(2, nazwisko);
                cstmt.setString(3, miasto);
                cstmt.setString(4, full);
                cstmt.setString(5, adres);
                cstmt.setBoolean(6, check);


                int status = cstmt.executeUpdate();
                if (status == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sukces");
                    alert.setHeaderText("");
                    alert.setContentText("Dodano pomyslnie Czytelnika !");
                    alert.showAndWait();

                    nazwisko_field.setText("");
                    imie_field.setText("");
                    adres_field.setText("");
                    miasto_field.setText("");
                    kp1_field.setText("");
                    kp2_field.setText("");
                    blokada.setSelected(false);
//                    KlientField.requestFocus();


                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Blad");
                    alert.setHeaderText("Czytelnicy");
                    alert.setContentText("nie dodano pomyslnie");
                    alert.showAndWait();

                }
                refreshTable();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        }

    }

    @FXML
    void usunAction(ActionEvent event) {
        czytelnicy czytelnicy2 = new czytelnicy();
        ObservableList<czytelnicy> czytelnicy = FXCollections.observableArrayList();

        if (czytelnicy_table.getSelectionModel().getSelectedItem() != null) {
            czytelnicy2 = czytelnicy_table.getSelectionModel().getSelectedItem();
            try {
                Connection con = ConnectionDB.getConnection();
                CallableStatement cstmt = con.prepareCall("{call DELETEREADERS(?)}");
                czytelnicy czytelnicyy = czytelnicy_table.getSelectionModel().getSelectedItem();
                int id = czytelnicyy.getId_czytelnika();
                cstmt.setInt(1, id);
                cstmt.execute();
                refreshTable();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sukces");
            alert.setHeaderText("Czytelnicy");
            alert.setContentText("Pomyślnie usunięto czytelnika");
            alert.showAndWait();


        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Czytelnicy");
            alert.setContentText("Zaznacz czytelnika z tabeli, którego chcesz usunąć");
            alert.showAndWait();
        }


    }

    public void pokazDetale(czytelnicy czytelnicy2) {
        if (czytelnicy2 != null) {

            nazwisko_field.setText(String.valueOf(czytelnicy2.getNazwisko()));
            imie_field.setText(czytelnicy2.getImie());
            miasto_field.setText(czytelnicy2.getMiast());
            adres_field.setText(czytelnicy2.getAdres());
            kp1_field.setText(String.valueOf(czytelnicy2.getKod_pocztowy().substring(0, 2)));
            kp2_field.setText(String.valueOf(czytelnicy2.getKod_pocztowy().substring(3, 6)));
            int intvalue = czytelnicy2.getBlokada();
            boolean boolvalue;
            if (intvalue == 1) {
                boolvalue = true;

            } else
                boolvalue = false;

            blokada.setSelected(boolvalue);


        } else {

            nazwisko_field.setText("");
            imie_field.setText("");
            miasto_field.setText("");
            adres_field.setText("");
            blokada.setSelected(false);
            kp1_field.setText("");
            kp2_field.setText("");


        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            table();
            pokazDetale(null);
            czytelnicy_table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> pokazDetale(newValue));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
