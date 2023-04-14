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

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.czytelnicy2;
import models.ksiazki2;

import static Controllers.logowanie.login;


public class recenzje implements Initializable {
    @FXML
    private Button dodajButton;

    @FXML
    private ComboBox<ksiazki2> ksiazkaBox;
    @FXML
    private TextArea areaComment;
    @FXML
    private ImageView s1;

    @FXML
    private ImageView s2;

    @FXML
    private ImageView s3;

    @FXML
    private ImageView s4;

    @FXML
    private ImageView s5;
    @FXML
    private ImageView s1gold;
    @FXML
    private ImageView s2gold;
    @FXML
    private ImageView s3gold;
    @FXML
    private ImageView s4gold;
    @FXML
    private ImageView s5gold;
    @FXML
    private Label nameLabel;
    public int ocena = 0;

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
    void s1Action(MouseEvent event) {
        s1gold.setVisible(true);
        s2gold.setVisible(false);
        s3gold.setVisible(false);
        s4gold.setVisible(false);
        s5gold.setVisible(false);
        ocena = 1;
    }

    @FXML
    void s2Action(MouseEvent event) {
        s1gold.setVisible(true);
        s2gold.setVisible(true);
        s3gold.setVisible(false);
        s4gold.setVisible(false);
        s5gold.setVisible(false);
        ocena = 2;
    }

    @FXML
    void s3Action(MouseEvent event) {
        s1gold.setVisible(true);
        s2gold.setVisible(true);
        s3gold.setVisible(true);
        s4gold.setVisible(false);
        s5gold.setVisible(false);
        ocena = 3;
    }

    @FXML
    void s4Action(MouseEvent event) {
        s1gold.setVisible(true);
        s2gold.setVisible(true);
        s3gold.setVisible(true);
        s4gold.setVisible(true);
        s5gold.setVisible(false);
        ocena = 4;
    }

    @FXML
    void s5Action(MouseEvent event) {
        s1gold.setVisible(true);
        s2gold.setVisible(true);
        s3gold.setVisible(true);
        s4gold.setVisible(true);
        s5gold.setVisible(true);
        ocena = 5;
    }

    @FXML
    void s1goldAction(MouseEvent event) {
        s1gold.setVisible(true);
        s2gold.setVisible(false);
        s3gold.setVisible(false);
        s4gold.setVisible(false);
        s5gold.setVisible(false);
        ocena = 1;

    }

    @FXML
    void s2goldAction(MouseEvent event) {
        s1gold.setVisible(true);
        s2gold.setVisible(true);
        s3gold.setVisible(false);
        s4gold.setVisible(false);
        s5gold.setVisible(false);
        ocena = 2;
    }

    @FXML
    void s3goldAction(MouseEvent event) {
        s1gold.setVisible(true);
        s2gold.setVisible(true);
        s3gold.setVisible(true);
        s4gold.setVisible(false);
        s5gold.setVisible(false);
        ocena = 3;
    }

    @FXML
    void s4goldAction(MouseEvent event) {
        s1gold.setVisible(true);
        s2gold.setVisible(true);
        s3gold.setVisible(true);
        s4gold.setVisible(true);
        s5gold.setVisible(false);
        ocena = 4;
    }

    @FXML
    void s5goldAction(MouseEvent event) {
        s1gold.setVisible(true);
        s2gold.setVisible(true);
        s3gold.setVisible(true);
        s4gold.setVisible(true);
        s5gold.setVisible(true);
        ocena = 5;
    }

    @FXML
    void dodajAction(ActionEvent event) {

        if (ksiazkaBox.getValue() == null || ocena == 0 || areaComment.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Recenzje");
            alert.setContentText("Nie wprowadzono wszystkich danych, uzupełnij wszystkie pola");
            alert.showAndWait();

        } else {

            ksiazki2 ksiazkiii = ksiazkaBox.getSelectionModel().getSelectedItem();
            int id = ksiazkiii.getID();
            String area = areaComment.getText();
            try {

                Connection con = ConnectionDB.getConnection();

                CallableStatement cstmt = con.prepareCall("{call INSERTREVIEW(?,?,?,?)}");
                cstmt.setInt(1, id);
               cstmt.setInt(2, getIDCzytelnika());
                cstmt.setInt(3, ocena);
                cstmt.setString(4, area);

                int status = cstmt.executeUpdate();
                if (status == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sukces");
                    alert.setHeaderText("");
                    alert.setContentText("Dodano pomyslnie Recenzje!");
                    alert.showAndWait();


                    areaComment.setText("");
                    ksiazkaBox.valueProperty().set(null);
                    ocena = 0;
                    s1gold.setVisible(false);
                    s2gold.setVisible(false);
                    s3gold.setVisible(false);
                    s4gold.setVisible(false);
                    s5gold.setVisible(false);

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Blad");
                    alert.setHeaderText("Recenzje");
                    alert.setContentText("nie dodano pomyslnie");
                    alert.showAndWait();

                }

            } catch (SQLException e) {

                e.printStackTrace();

            }

        }


    }

    public static void addTextLimiter(final TextArea tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }
    public int id232;
    public int getIDCzytelnika() throws SQLException {

        Connection con = ConnectionDB.getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT id_czytelnika FROM logowanie WHERE login ='" + login + "'");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            id232 = rs.getInt("id_czytelnika");
        }
        return id232;
    }

    @FXML
    ObservableList<ksiazki2> oblist1 = FXCollections.observableArrayList();

    public ObservableList<ksiazki2> Ksiegi() throws ClassNotFoundException, SQLException {
        Connection con = ConnectionDB.getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT k.id_ksiazki,k.tytul FROM ksiazki k INNER JOIN zamowienia_historia z ON k.id_ksiazki=z.id_ksiazki INNER JOIN czytelnicy c ON z.id_czytelnika=c.id_czytelnika GROUP BY k.id_ksiazki,k.tytul,z.id_czytelnika having z.id_czytelnika=?");
      pst.setInt(1,getIDCzytelnika());
        ResultSet rs = pst.executeQuery();

        oblist1.clear();

        while (rs.next()) {
            oblist1.add(new ksiazki2(rs.getInt("id_ksiazki"), rs.getString("tytul")));
        }
        return oblist1;

    }
    @FXML
    void wylogujAction(ActionEvent event) throws IOException {

        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/logowanie.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTextLimiter(areaComment,255);
        try {
            ksiazkaBox.getItems().addAll(Ksiegi());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        nameLabel.setText(login);
    }
}
