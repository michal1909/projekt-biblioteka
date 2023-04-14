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
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import models.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class SystemWypozyczen implements Initializable {

    @FXML
    private ImageView backIcon;

    @FXML
    private ComboBox<czytelnicy2> czytelnikBox;

    @FXML
    private Button dodaj_button;

    @FXML
    private ComboBox<ksiazki2> ksiazkaBox;

    @FXML
    private DatePicker wypozyczenieDate;

    @FXML
    private DatePicker zwrotDate;
    @FXML
    private Label gatunekLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Label iloscLabel;
    @FXML
    private Label autorLabel;
    @FXML
    private Label rokLabel;

    @FXML
    private Label tytulLabel;

    @FXML
    private Label wydawnictwoLabel;
    @FXML
    private Label CADRESLabel;

    @FXML
    private Label CBLOKLabel;

    @FXML
    private Label CIDLabel;

    @FXML
    private Label CIMIELabel;

    @FXML
    private Label CKODLabel;

    @FXML
    private Label CMIASTOLabel;

    @FXML
    private Label CNAZWISKOLabel;

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
    void dodaj_action(ActionEvent event)  {


        if (ksiazkaBox.getValue() == null || czytelnikBox.getValue()==null || wypozyczenieDate.getValue()==null ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Wypozyczenie !");
            alert.setContentText("Nie wprowadzono wszystkich danych, uzupełnij wszystkie pola");
            alert.showAndWait();

        } else {

            ksiazki2 ksiazkiii = ksiazkaBox.getSelectionModel().getSelectedItem();
            int id = ksiazkiii.getID();
            czytelnicy2 czytelnicyyy = czytelnikBox.getSelectionModel().getSelectedItem();
            int id2 = czytelnicyyy.getID();
            int czy_oddane=0;
            String wypozyczenie = wypozyczenieDate.getValue().toString();

            try {

                Connection con = ConnectionDB.getConnection();


                CallableStatement cstmt = con.prepareCall("{call INSERTISSUE(?,?,?)}");

                cstmt.setInt(1, id);
                cstmt.setInt(2, id2);
                cstmt.setString(3, wypozyczenie);

              int status = cstmt.executeUpdate();
//                if(cstmt.getString(1)!=null){
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("nene");
//                    alert.setHeaderText("");
//                    alert.setContentText("nie ma wiecej egzemplarzy!");
//                    alert.showAndWait();
//                }
           if (status==1 ) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sukces");
                    alert.setHeaderText("");
                    alert.setContentText("Dodano pomyslnie wypozyczenie!");
                    alert.showAndWait();


//                   czytelnikBox.valueProperty().set(null);
//                    ksiazkaBox.valueProperty().set(null);
//                  wypozyczenieDate.valueProperty().set(null);


                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Blad");
                    alert.setHeaderText("Wypozyczenia");
                    alert.setContentText("nie dodano pomyslnie");
                    alert.showAndWait();

                }

            } catch (SQLException e) {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Blad");
//                alert.setHeaderText("Wypozyczenia");
//                alert.setContentText(throwables.getMessage());
//                alert.showAndWait();
                e.printStackTrace();

            }

        }
iloscLabel.setText("");
        try {
            getInfoKsiazka2();
            getInfoCzytelnik();
        } catch (SQLException e) {
            e.printStackTrace();
        }

//disable_stmt.executeUpdate();


    }

    public void getInfoKsiazka() throws SQLException {
        ksiazki2 ksiazkii = ksiazkaBox.getSelectionModel().getSelectedItem();
        int id = ksiazkii.getID();
        Connection con = ConnectionDB.getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT k.id_ksiazki, a.imie||' '||a.nazwisko as fullname, k.tytul, k.wydawnictwo, k.gatunek, k.rok_wydania FROM ksiazki k INNER JOIN autorzy a ON k.id_autora=a.id_autora  where id_ksiazki=?");
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {

            idLabel.setText(rs.getString("id_ksiazki"));
            autorLabel.setText(rs.getString("fullname"));
            tytulLabel.setText(rs.getString("tytul"));
            wydawnictwoLabel.setText(rs.getString("wydawnictwo"));
            gatunekLabel.setText(rs.getString("gatunek"));
            rokLabel.setText(rs.getString("rok_wydania"));
//            iloscLabel.setText(rs.getString("ilosc_egzemplarzy"));

        }

    }

    public void getInfoKsiazka2() throws SQLException {
        ksiazki2 ksiazkii = ksiazkaBox.getSelectionModel().getSelectedItem();
        int id = ksiazkii.getID();
        Connection con = ConnectionDB.getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT COUNT(e.id_egzemplarza) as liczba   FROM egzemplarze e  WHERE e.id_ksiazki = ? AND NOT EXISTS ( SELECT eg.id_egzemplarza  FROM egzemplarze eg INNER JOIN wypozyczenia w on eg.id_egzemplarza=w.id_egzemplarza  WHERE eg.id_egzemplarza = e.id_egzemplarza)");
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {

            iloscLabel.setText(rs.getString("liczba"));

        }

    }


    public void getInfoCzytelnik() throws SQLException {
        czytelnicy2 czytelnicyy = czytelnikBox.getSelectionModel().getSelectedItem();
        int id = czytelnicyy.getID();
      int blok = czytelnicyy.getBlokada();


        Connection con = ConnectionDB.getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT * from czytelnicy  where id_czytelnika=?");
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {

            CIDLabel.setText(rs.getString("id_czytelnika"));
          CIMIELabel.setText(rs.getString("imie"));
            CNAZWISKOLabel.setText(rs.getString("nazwisko"));
            CADRESLabel.setText(rs.getString("adres"));
          CMIASTOLabel.setText(rs.getString("miasto"));
            CKODLabel.setText(rs.getString("kod_pocztowy"));
          CBLOKLabel.setText(rs.getString("blokada"));

        }
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
    void test123(ActionEvent event) throws SQLException {
        getInfoKsiazka();
        getInfoKsiazka2();
    }
    @FXML
    void czytelnikAction(ActionEvent event) throws SQLException {
getInfoCzytelnik();
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
wypozyczenieDate.setEditable(false);
          wypozyczenieDate.setConverter(
                    new StringConverter<>() {
                        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yy/MM/dd");

                        @Override
                        public String toString(LocalDate date) {
                            return (date != null) ? dateFormatter.format(date) : "";
                        }

                        @Override
                        public LocalDate fromString(String string) {
                            return (string != null && !string.isEmpty())
                                    ? LocalDate.parse(string, dateFormatter)
                                    : null;
                        }
                    });


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
