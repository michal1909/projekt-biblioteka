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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.autorzy;
import models.ksiazki;
import oracle.jdbc.OracleTypes;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ZarzadzanieKsiazkami implements Initializable {

    @FXML
    private Button aktualizuj_button;

    @FXML
    private ComboBox<autorzy> autorComboBox;

    @FXML
    private ImageView backIcon;

    @FXML
    private TextField cena_field;

    @FXML
    private Button dodaj_button;

    @FXML
    private TextField gatunek_field;

    @FXML
    private TextField ilosc_field;

    @FXML
    private ImageView plusIcon;

    @FXML
    private TextField rok_wydania_field;

    @FXML
    private TextField tytul_field;

    @FXML
    private Button usun_button;

    @FXML
    private TextField wydawnictwo_field;

    @FXML
    private TableColumn<ksiazki, String> c_gatunek;

    @FXML
    private TableColumn<ksiazki, Number> c_id_autora;

    @FXML
    private TableColumn<ksiazki, Number> c_id_ksiazki;

    @FXML
    private TableColumn<ksiazki, Number> c_ilosc_egzemplarzy;
    @FXML
    private TableColumn<ksiazki, Number> c_cena;

    @FXML
    private TableColumn<ksiazki, Number> c_rok_wydania;

    @FXML
    private TableColumn<ksiazki, String> c_tytul;

    @FXML
    private TableColumn<ksiazki, String> c_wydawnictwo;
    @FXML
    private TableView<ksiazki> ksiazki_table;
    @FXML
    private ImageView refreshIcon;
    @FXML
    void refreshAction(MouseEvent event) throws SQLException, ClassNotFoundException {
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
            autorComboBox.getItems().clear();
            autorComboBox.getItems().addAll(IDSAMFUNC());

        }

    }
    public void refreshTable2() throws SQLException {
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
    //combobox autorzy
    @FXML
    ObservableList<autorzy> oblist1 = FXCollections.observableArrayList();

    public ObservableList<autorzy> IDSAMFUNC() throws ClassNotFoundException, SQLException {
        Connection con = ConnectionDB.getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT id_autora,imie,nazwisko FROM autorzy");

        ResultSet rs = pst.executeQuery();


        oblist1.clear();

        while (rs.next()) {
            oblist1.add(new autorzy(rs.getInt("id_autora"), rs.getString("imie"), rs.getString("nazwisko")));
        }
        return oblist1;

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
    void autorzyWindow(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/autorzy.fxml"));
        Stage stage2 = new Stage();
        Scene scene = new Scene(root);
        stage2.setTitle("Dodawanie autorów");
        stage2.setScene(scene);
        stage2.setResizable(false);
        stage2.initModality(Modality.WINDOW_MODAL);
        stage2.initOwner(plusIcon.getScene().getWindow());
        stage2.show();
    }

    @FXML
    void aktualizujAction(ActionEvent event) {
        if (autorComboBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Ksiazki");
            alert.setContentText("Wybierz autora z listy !!");
            alert.showAndWait();
        } else if (tytul_field.getText().isEmpty() || wydawnictwo_field.getText().isEmpty() || gatunek_field.getText().isEmpty() || rok_wydania_field.getText().isEmpty() || ilosc_field.getText().isEmpty() || cena_field.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Ksiazki");
            alert.setContentText("Nie wprowadzono wszystkich danych, uzupełnij wszystkie pola!");
            alert.showAndWait();

        } else {
            String autorbox = String.valueOf(autorComboBox.valueProperty().getValue().getID());
            String tytul = tytul_field.getText();
            String wydawnictwo = wydawnictwo_field.getText();
            String gatunek = gatunek_field.getText();
            String rok_wydania = rok_wydania_field.getText();
            String ilosc = ilosc_field.getText();
            String cena = cena_field.getText();


            try {
                Connection con = ConnectionDB.getConnection();
                ksiazki ksiazki2 = new ksiazki();
                ObservableList<ksiazki> ksiazki = FXCollections.observableArrayList();

                CallableStatement cstmt = con.prepareCall("{call UPDATEBOOK(?,?,?,?,?,?,?,?)}");
                ksiazki ksiazkii = ksiazki_table.getSelectionModel().getSelectedItem();
                int id = ksiazkii.getId_ksiazki();
                cstmt.setString(1, autorbox);
                cstmt.setString(2, gatunek);
                cstmt.setString(3, rok_wydania);
                cstmt.setString(4, cena);
                cstmt.setString(5, wydawnictwo);
                cstmt.setString(6, ilosc);
                cstmt.setString(7, tytul);
                cstmt.setInt(8, id);
                cstmt.execute();
                refreshTable();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            tytul_field.setText("");
            wydawnictwo_field.setText("");
            gatunek_field.setText("");
            rok_wydania_field.setText("");
            ilosc_field.setText("");
            cena_field.setText("");
            autorComboBox.valueProperty().set(null);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sukces");
            alert.setHeaderText("Ksiazki");
            alert.setContentText("Edycja przebiegła pomyślnie");
            alert.showAndWait();

//        }


        }


    }

    public void refreshTable() throws SQLException {
        ObservableList<ksiazki> ksiazki = FXCollections.observableArrayList();
        ksiazki.clear();
        Connection con = ConnectionDB.getConnection();

        CallableStatement cstmt = con.prepareCall("{ call SELECTBOOKS(?)}");
        cstmt.registerOutParameter(1, OracleTypes.CURSOR);
        cstmt.execute();
        ResultSet rs = (ResultSet) cstmt.getObject(1);

        while (rs.next()) {
            ksiazki ksiazki2 = new ksiazki();
            ksiazki2.setId_ksiazki(rs.getInt("id_ksiazki"));
            ksiazki2.setId_autora(rs.getInt("id_autora"));
            ksiazki2.setTytul(rs.getString("tytul"));
            ksiazki2.setWydawnictwo(rs.getString("wydawnictwo"));
            ksiazki2.setGatunek(rs.getString("Gatunek"));
            ksiazki2.setRok_wydania(rs.getInt("rok_wydania"));
            ksiazki2.setIlosc_egzemplarzy(rs.getInt("ilosc_egzemplarzy"));
            ksiazki2.setCena(rs.getInt("cena"));

            ksiazki.add(ksiazki2);


        }
        ksiazki_table.setItems(ksiazki);
        c_id_ksiazki.setCellValueFactory(f -> f.getValue().id_ksiazkiProperty());
        c_id_autora.setCellValueFactory(f -> f.getValue().id_autoraProperty());
        c_tytul.setCellValueFactory(f -> f.getValue().tytulProperty());
        c_wydawnictwo.setCellValueFactory(f -> f.getValue().wydawnictwoProperty());
        c_gatunek.setCellValueFactory(f -> f.getValue().gatunekProperty());
        c_rok_wydania.setCellValueFactory(f -> f.getValue().rok_wydaniaProperty());
        c_ilosc_egzemplarzy.setCellValueFactory(f -> f.getValue().ilosc_egzemplarzyProperty());
        c_cena.setCellValueFactory(f -> f.getValue().cenaProperty());


    }

    @FXML
    void dodaj_action(ActionEvent event) {


        if (autorComboBox.getValue() == null || tytul_field.getText().isEmpty() || wydawnictwo_field.getText().isEmpty() || gatunek_field.getText().isEmpty() || rok_wydania_field.getText().isEmpty() || ilosc_field.getText().isEmpty() || cena_field.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Ksiazki");
            alert.setContentText("Nie wprowadzono wszystkich danych, uzupełnij wszystkie pola");
            alert.showAndWait();

        } else {
            String autorbox = String.valueOf(autorComboBox.valueProperty().getValue().getID());
            String tytul = tytul_field.getText();
            String wydawnictwo = wydawnictwo_field.getText();
            String gatunek = gatunek_field.getText();
            String rok_wydania = rok_wydania_field.getText();
            String ilosc = ilosc_field.getText();
            String cena = cena_field.getText();

            try {
                Connection con = ConnectionDB.getConnection();

//                PreparedStatement   pst = con.prepareStatement("insert into ksiazki(id_autora,tytul,wydawnictwo,gatunek,rok_wydania,ilosc_egzemplarzy,cena)values(?,?,?,?,?,?,?) ");
                CallableStatement cstmt = con.prepareCall("{call INSERTBOOK2(?,?,?,?,?,?,?)}");

                cstmt.setString(1, autorbox);
                cstmt.setString(2, tytul);
                cstmt.setString(3, wydawnictwo);
                cstmt.setString(4, gatunek);
                cstmt.setString(5, rok_wydania);
                cstmt.setString(6, ilosc);
                cstmt.setString(7, cena);


                int status = cstmt.executeUpdate();
                if (status == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sukces");
                    alert.setHeaderText("");
                    alert.setContentText("Dodano pomyslnie ksiazke !");
                    alert.showAndWait();

                    tytul_field.setText("");
                    wydawnictwo_field.setText("");
                    gatunek_field.setText("");
                    rok_wydania_field.setText("");
                    ilosc_field.setText("");
                    cena_field.setText("");
                    autorComboBox.valueProperty().set(null);
//                    KlientField.requestFocus();


                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Blad");
                    alert.setHeaderText("Ksiazki");
                    alert.setContentText("nie dodano pomyslnie");
                    alert.showAndWait();

                }
                refreshTable();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        }

    }

    //umieszczenie zawartosci z bazy do tabeli
    public void table() throws SQLException {
        ObservableList<ksiazki> ksiazki = FXCollections.observableArrayList();
        try {
            Connection con = ConnectionDB.getConnection();
            CallableStatement cstmt = con.prepareCall("{ call SELECTBOOKS(?)}");
            cstmt.registerOutParameter(1, OracleTypes.CURSOR);
            cstmt.execute();
            ResultSet rs = (ResultSet) cstmt.getObject(1);
            {
                while (rs.next()) {
                    ksiazki ksiazki2 = new ksiazki();
                    ksiazki2.setId_ksiazki(rs.getInt("id_ksiazki"));
                    ksiazki2.setId_autora(rs.getInt("id_autora"));
                    ksiazki2.setTytul(rs.getString("tytul"));
                    ksiazki2.setWydawnictwo(rs.getString("wydawnictwo"));
                    ksiazki2.setGatunek(rs.getString("Gatunek"));
                    ksiazki2.setRok_wydania(rs.getInt("rok_wydania"));
                    ksiazki2.setIlosc_egzemplarzy(rs.getInt("ilosc_egzemplarzy"));
                    ksiazki2.setCena(rs.getInt("cena"));

                    ksiazki.add(ksiazki2);

                }
            }
            ksiazki_table.setItems(ksiazki);
            c_id_ksiazki.setCellValueFactory(f -> f.getValue().id_ksiazkiProperty());
            c_id_autora.setCellValueFactory(f -> f.getValue().id_autoraProperty());
//           c_id_autora.setCellValueFactory(new PropertyValueFactory<>("id_sam"));
//           c_id_autora.setCellFactory(TextFieldTableCell.<autorzy, Integer>forTableColumn(new CustomIntegerStringConverter()));
            c_tytul.setCellValueFactory(f -> f.getValue().tytulProperty());
            c_wydawnictwo.setCellValueFactory(f -> f.getValue().wydawnictwoProperty());
            c_gatunek.setCellValueFactory(f -> f.getValue().gatunekProperty());
            c_rok_wydania.setCellValueFactory(f -> f.getValue().rok_wydaniaProperty());
            c_ilosc_egzemplarzy.setCellValueFactory(f -> f.getValue().ilosc_egzemplarzyProperty());
            c_cena.setCellValueFactory(f -> f.getValue().cenaProperty());

        } catch (SQLException throwables) {
            Logger.getLogger(ZarzadzanieKsiazkami.class.getName()).log(Level.SEVERE, null, throwables);
        }

    }

    public void pokazDetale(ksiazki ksiazki2) {
        if (ksiazki2 != null) {

            tytul_field.setText(String.valueOf(ksiazki2.getTytul()));
            wydawnictwo_field.setText(ksiazki2.getWydawnictwo());
            gatunek_field.setText(ksiazki2.getGatunek());
            rok_wydania_field.setText(String.valueOf(ksiazki2.getRok_wydania()));
//            autorComboBox.valueProperty().set(autorzy2);
            ilosc_field.setText(String.valueOf(ksiazki2.getIlosc_egzemplarzy()));
            cena_field.setText(String.valueOf(ksiazki2.getCena()));
//            ID_Produktu.requestFocus();


        } else {
//            ID_Produktu.setText("");
            tytul_field.setText("");
            wydawnictwo_field.setText("");
            gatunek_field.setText("");
            autorComboBox.valueProperty().set(null);
            rok_wydania_field.setText("");
            ilosc_field.setText("");
            cena_field.setText("");
//            ID_Produktu.requestFocus();


        }


    }






    //    public void fillAutorzy() throws SQLException {
//        ObservableList<String> autorzy = FXCollections.observableArrayList();
//
//        Connection con = ConnectionDB.getConnection();
//
//        PreparedStatement   pst = con.prepareStatement("select id_autora from autorzy");
//        ResultSet rs = pst.executeQuery();
//        while (rs.next()) {
//            autorzy.add(rs.getString("Id_autora"));
//
//        }
//        autorComboBox.setItems(null);
//       autorComboBox.setItems(autorzy);
//
//
//    }
    @FXML
    void usunAction(ActionEvent event) {
        ksiazki ksiazki2 = new ksiazki();
        ObservableList<ksiazki> ksiazki = FXCollections.observableArrayList();

        if (ksiazki_table.getSelectionModel().getSelectedItem() != null) {
            ksiazki2 = ksiazki_table.getSelectionModel().getSelectedItem();
            try {
                Connection con = ConnectionDB.getConnection();
                CallableStatement cstmt = con.prepareCall("{call DELETEBOOK2(?)}");
                ksiazki ksiazkii = ksiazki_table.getSelectionModel().getSelectedItem();
                int id = ksiazkii.getId_ksiazki();
                //                pst = con.prepareStatement("DELETE FROM `towary` WHERE `towary`.`Id_towaru` = " + towary2.getId_towaru());
                cstmt.setInt(1, id);
                cstmt.execute();
                refreshTable();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sukces");
            alert.setHeaderText("Ksiazki");
            alert.setContentText("Pomyślnie usunięto ksiazke");
            alert.showAndWait();


        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Ksiazki");
            alert.setContentText("Zaznacz ksiazke z tabeli, którą chcesz usunąć");
            alert.showAndWait();
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            table();
            autorComboBox.getItems().addAll(IDSAMFUNC());
            pokazDetale(null);
            ksiazki_table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> pokazDetale(newValue));
//            fillAutorzy();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
