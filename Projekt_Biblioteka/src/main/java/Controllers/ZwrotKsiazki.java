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
import javafx.util.StringConverter;
import models.czytelnicy2;
import models.ksiazki2;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ZwrotKsiazki implements Initializable {


    @FXML
    private ImageView backIcon;

    @FXML
    private ComboBox<czytelnicy2> czytelnikBox;

    @FXML
    private Label datawypozyczeniaLabel;

    @FXML
    private Label idegzemplarzaLabel;

    @FXML
    private Label idwypozyczeniaLabel;

    @FXML
    private ComboBox<ksiazki2> ksiazkaBox;

    @FXML
    private Button zwrotButton;

    @FXML
    private DatePicker zwrotDate;

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
    void czytelnikBoxAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        ksiazkaBox.getItems().clear();
        ksiazkaBox.getItems().addAll(Ksiegi());

    }

    @FXML
    void ksiazkaBoxAction(ActionEvent event) throws SQLException {
if(ksiazkaBox.getValue()!=null||czytelnikBox.getValue()!=null) {
    getInfoIssue();
}else{
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("");
    alert.setHeaderText("");
    alert.setContentText("Nie mozna pobrac informacji o wypozyczeniu!");
    alert.showAndWait();
}
    }

    @FXML
    void zwrotAction(ActionEvent event) {

        if (ksiazkaBox.getValue() == null || czytelnikBox.getValue()==null || zwrotDate.getValue()==null ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Zwrot !");
            alert.setContentText("Nie wprowadzono wszystkich danych, uzupełnij wszystkie pola");
            alert.showAndWait();

        } else {
            ksiazki2 ksiazkiii = ksiazkaBox.getSelectionModel().getSelectedItem();
            int id2 = ksiazkiii.getID();
            czytelnicy2 czytelnicyyy = czytelnikBox.getSelectionModel().getSelectedItem();
            int id = czytelnicyyy.getID();
            String zwrot = zwrotDate.getValue().toString();

            try {
                Connection con = ConnectionDB.getConnection();

                CallableStatement cstmt = con.prepareCall("{call RETURNISSUE(?,?,?)}");

                cstmt.setInt(1, id);
                cstmt.setInt(2, id2);
                cstmt.setString(3, zwrot);

                boolean status = cstmt.execute();
                if (!status ) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sukces");
                    alert.setHeaderText("");
                    alert.setContentText("Zwrócono ksiazke !");
                    alert.showAndWait();



                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Blad");
                    alert.setHeaderText("Zwrot");
                    alert.setContentText("nie dodano pomyslnie");
                    alert.showAndWait();

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
public void getInfoIssue() throws SQLException {
    ksiazki2 ksiazkii = ksiazkaBox.getSelectionModel().getSelectedItem();
    int id = ksiazkii.getID();
    czytelnicy2 czytelnicyy = czytelnikBox.getSelectionModel().getSelectedItem();
    int id2 = czytelnicyy.getID();
    Connection con = ConnectionDB.getConnection();
    PreparedStatement pst = con.prepareStatement("SELECT w.id_wypozyczenia,w.id_egzemplarza,TO_CHAR(w.data_wypozyczenia,'yyyy-mm-dd') as data FROM wypozyczenia w INNER JOIN egzemplarze e ON w.id_egzemplarza=e.id_egzemplarza INNER JOIN ksiazki k ON e.id_ksiazki=k.id_ksiazki  where k.id_ksiazki=? and w.id_czytelnika=?");
    pst.setInt(1, id);
    pst.setInt(2,id2);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {

       idwypozyczeniaLabel.setText(rs.getString("id_wypozyczenia"));
      idegzemplarzaLabel.setText(rs.getString("id_egzemplarza"));
      datawypozyczeniaLabel.setText(rs.getString("data"));


    }

}
    @FXML
    ObservableList<czytelnicy2> oblist2 = FXCollections.observableArrayList();

    public ObservableList<czytelnicy2> Czytelnicy() throws ClassNotFoundException, SQLException {
        Connection con = ConnectionDB.getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT id_czytelnika,imie,nazwisko FROM czytelnicy");

        ResultSet rs = pst.executeQuery();

        oblist2.clear();

        while (rs.next()) {
            oblist2.add(new czytelnicy2(rs.getInt("id_czytelnika"), rs.getString("imie"), rs.getString("nazwisko")));
        }
        return oblist2;

    }

    @FXML
    ObservableList<ksiazki2> oblist1 = FXCollections.observableArrayList();

    public ObservableList<ksiazki2> Ksiegi() throws ClassNotFoundException, SQLException {
        Connection con = ConnectionDB.getConnection();
        czytelnicy2 czytelnicyy = czytelnikBox.getSelectionModel().getSelectedItem();
        int id = czytelnicyy.getID();
        PreparedStatement pst = con.prepareStatement("SELECT k.id_ksiazki,k.tytul FROM ksiazki k INNER JOIN egzemplarze e ON k.id_ksiazki=e.id_ksiazki INNER JOIN wypozyczenia w ON e.id_egzemplarza=w.id_egzemplarza INNER JOIN czytelnicy c ON w.id_czytelnika=c.id_czytelnika where c.id_czytelnika=?");
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();


        oblist1.clear();

        while (rs.next()) {
            oblist1.add(new ksiazki2(rs.getInt("id_ksiazki"), rs.getString("tytul")));
        }
        return oblist1;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            czytelnikBox.getItems().addAll(Czytelnicy());
           zwrotDate.setEditable(false);
           zwrotDate.setConverter(
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
