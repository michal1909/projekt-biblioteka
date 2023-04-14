package Controllers;

import connect.ConnectionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import models.zamowienie;

public class odbior implements Initializable {

    @FXML
    private ImageView backIcon;

    @FXML
    private ComboBox<czytelnicy2> czytelnikBox;

    @FXML
    private Label idczytelnikaLabel;

    @FXML
    private Label idksiazkiLabel;

    @FXML
    private Label idzamowieniaLabel;

    @FXML
    private Label iloscLabel;

    @FXML
    private Button odbierzButton;

    @FXML
    private Label sumaLabel;

    @FXML
    private ComboBox<zamowienie> zamowienieBox;

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
        zamowienieBox.getItems().clear();
        zamowienieBox.getItems().addAll(Zamowienia());

    }

    @FXML
    void odbierzAction(ActionEvent event) {
        if (zamowienieBox.getValue() == null || czytelnikBox.getValue()==null || zwrotDate.getValue()==null ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Zwrot !");
            alert.setContentText("Nie wprowadzono wszystkich danych, uzupełnij wszystkie pola");
            alert.showAndWait();

        } else {
            zamowienie zamowieniee = zamowienieBox.getSelectionModel().getSelectedItem();
            int id = zamowieniee.getID();
            czytelnicy2 czytelnicyy = czytelnikBox.getSelectionModel().getSelectedItem();
            int id2 = czytelnicyy.getID();
            String odbior = zwrotDate.getValue().toString();

            try {
                Connection con = ConnectionDB.getConnection();
                CallableStatement cstmt = con.prepareCall("{call PICKORDER(?,?,?)}");
                cstmt.setInt(1, id);
                cstmt.setInt(2, id2);
                cstmt.setString(3, odbior);

                boolean status = cstmt.execute();
                if (!status ) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sukces");
                    alert.setHeaderText("");
                    alert.setContentText("Odebrano ksiazke ! Milego czytania :D ");
                    alert.showAndWait();



                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Blad");
                    alert.setHeaderText("Odbior");
                    alert.setContentText("nie dodano pomyslnie");
                    alert.showAndWait();

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


    }

    @FXML
    void zamowienieBoxAction(ActionEvent event) throws SQLException {

        if(zamowienieBox.getValue()!=null||czytelnikBox.getValue()!=null) {
            getInfoOrders();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.setContentText("Nie mozna pobrac informacji o zamowieniu!");
            alert.showAndWait();
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
    ObservableList<zamowienie> oblist1 = FXCollections.observableArrayList();

    public ObservableList<zamowienie> Zamowienia() throws ClassNotFoundException, SQLException {
        Connection con = ConnectionDB.getConnection();
        czytelnicy2 czytelnicyy = czytelnikBox.getSelectionModel().getSelectedItem();
        int id = czytelnicyy.getID();
        PreparedStatement pst = con.prepareStatement("SELECT z.id_zamowienia ,k.tytul  FROM zamowienia z INNER JOIN ksiazki k ON z.id_ksiazki=k.id_ksiazki INNER JOIN czytelnicy c ON z.id_czytelnika=c.id_czytelnika where c.id_czytelnika=?");
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();


        oblist1.clear();

        while (rs.next()) {
            oblist1.add(new zamowienie(rs.getInt("id_zamowienia"), rs.getString("tytul")));
        }
        return oblist1;

    }



    public void getInfoOrders() throws SQLException {
      zamowienie zamowieniee = zamowienieBox.getSelectionModel().getSelectedItem();
        int id = zamowieniee.getID();
        czytelnicy2 czytelnicyy = czytelnikBox.getSelectionModel().getSelectedItem();
        int id2 = czytelnicyy.getID();
        Connection con = ConnectionDB.getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT z.id_zamowienia,z.id_czytelnika,z.id_ksiazki,z.ilosc,z.suma FROM zamowienia z  INNER JOIN czytelnicy c ON z.id_czytelnika=c.id_czytelnika  where z.id_zamowienia=? and c.id_czytelnika=?");
        pst.setInt(1, id);
        pst.setInt(2,id2);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
           idzamowieniaLabel.setText(rs.getString("id_zamowienia"));
           idczytelnikaLabel.setText(rs.getString("id_czytelnika"));
           idksiazkiLabel.setText(rs.getString("id_ksiazki"));
         iloscLabel.setText(rs.getString("ilosc")+" szt");
           sumaLabel.setText(rs.getString("suma")+" zł");


        }

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
