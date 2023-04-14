package Controllers;

import connect.ConnectionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import oracle.jdbc.OracleTypes;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class home implements Initializable {

    @FXML
    private Pane czarna_pane;

    @FXML
    private Pane czytelnicy_pane;

    @FXML
    private Pane ksiazka_pane;

    @FXML
    private Pane lms_pane;

    @FXML
    private Pane panelPieChart;
    @FXML
    private BarChart<String, Double> chartRecenzje;

    @FXML
    private PieChart bestsellerChart;

    @FXML
    private Pane zamowienia_pane;

    @FXML
    private Pane wypKsiazki_pane;

    @FXML
    private Pane wypozyczenia_pane;

    @FXML
    private Pane zwroty_pane;

    @FXML
    private Label KsiazkiLabel;
    @FXML
    private Label czarnaLabel;
    @FXML
    private Label czytelnicyLabel;
    @FXML
    private Label wydaneLabel;
    @FXML
    private Pane odbiorKsiazki_pane;

    @FXML
    void ksiazkaEnteredAction(MouseEvent event) {
        ksiazka_pane.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
    }

    @FXML
    void ksiazkaExitedAction(MouseEvent event) {
        ksiazka_pane.setBackground(new Background(new BackgroundFill(Color.rgb(51,51,51),null,null)));
    }
    @FXML
    void czarnaEnteredAction(MouseEvent event) {
        czarna_pane.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
    }

    @FXML
    void czarnaExitedAction(MouseEvent event) {
       czarna_pane.setBackground(new Background(new BackgroundFill(Color.rgb(51,51,51),null,null)));
    }

    @FXML
    void czytEnteredAction(MouseEvent event) {
      czytelnicy_pane.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
    }

    @FXML
    void czytExitedAction(MouseEvent event) {
        czytelnicy_pane.setBackground(new Background(new BackgroundFill(Color.rgb(51,51,51),null,null)));
    }



    @FXML
    void lmsEnteredAction(MouseEvent event) {
       lms_pane.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
    }

    @FXML
    void lmsExitedAction(MouseEvent event) {
      lms_pane.setBackground(new Background(new BackgroundFill(Color.rgb(51,51,51),null,null)));
    }

    @FXML
    void rekordEnteredAction(MouseEvent event) {
        zamowienia_pane.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
    }

    @FXML
    void rekordExitedAction(MouseEvent event) {
       zamowienia_pane.setBackground(new Background(new BackgroundFill(Color.rgb(51,51,51),null,null)));
    }

    @FXML
    void wypEnteredAction(MouseEvent event) {
       wypozyczenia_pane.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
    }

    @FXML
    void wypExitedAction(MouseEvent event) {
       wypozyczenia_pane.setBackground(new Background(new BackgroundFill(Color.rgb(51,51,51),null,null)));
    }

    @FXML
    void wypKsiazkiEnteredAction(MouseEvent event) {
     odbiorKsiazki_pane.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
    }

    @FXML
    void wypKsiazkiExitedAction(MouseEvent event) {
      odbiorKsiazki_pane.setBackground(new Background(new BackgroundFill(Color.rgb(51,51,51),null,null)));
    }

    @FXML
    void zwrotEnteredAction(MouseEvent event) {
      zwroty_pane.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
    }

    @FXML
    void zwrotExitedAction(MouseEvent event) {
       zwroty_pane.setBackground(new Background(new BackgroundFill(Color.rgb(51,51,51),null,null)));
    }
    @FXML
    void ksiazkiWindow(MouseEvent event) throws IOException {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ZarzadzanieKsiazkami.fxml"));
        stage.setX(1);
        stage.setY(1);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    void czytelnicyWindow(MouseEvent event) throws IOException {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ZarzadzanieCzytelnikami.fxml"));
        stage.setX(1);
        stage.setY(1);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
    @FXML
    void systemWindow(MouseEvent event) throws IOException {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/SystemWypozyczen.fxml"));
        stage.setX(1);
        stage.setY(1);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
    @FXML
    void zwrotWindow(MouseEvent event) throws IOException {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ZwrotKsiazki.fxml"));
        stage.setX(1);
        stage.setY(1);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
    @FXML
    void zamowienieWindow(MouseEvent event) throws IOException {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/zamowienia.fxml"));
        stage.setX(1);
        stage.setY(1);
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }
    @FXML
    void odbiorWindow(MouseEvent event) throws IOException {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/odbior.fxml"));
        stage.setX(1);
        stage.setY(1);
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }
    @FXML
    void bazaWindow(MouseEvent event) throws IOException {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/tabele.fxml"));
        stage.setX(1);
        stage.setY(1);
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }
    @FXML
    void wylogujWindow(MouseEvent event) throws IOException {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/logowanie.fxml"));
        stage.setX(1);
        stage.setY(1);
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

public void liczbaKsiazek() throws SQLException {

    Connection con = ConnectionDB.getConnection();
    CallableStatement cstmt = con.prepareCall("{ ?= call COUNTBOOKS()}");
    cstmt.registerOutParameter(1, Types.INTEGER);
    cstmt.execute();
    int result = cstmt.getInt(1);

    KsiazkiLabel.setText(String.valueOf(result));

}
    public void liczbaCzytelnikow() throws SQLException {

        Connection con = ConnectionDB.getConnection();
        CallableStatement cstmt = con.prepareCall("{ ?= call COUNTREADERS()}");
        cstmt.registerOutParameter(1, Types.INTEGER);
        cstmt.execute();
        int result = cstmt.getInt(1);

       czytelnicyLabel.setText(String.valueOf(result));

    }
    public void liczbaWypozyczen() throws SQLException {

        Connection con = ConnectionDB.getConnection();
        CallableStatement cstmt = con.prepareCall("{ ?= call COUNTISSUE()}");
        cstmt.registerOutParameter(1, Types.INTEGER);
        cstmt.execute();
        int result = cstmt.getInt(1);

       wydaneLabel.setText(String.valueOf(result));

    }
    public void czarnaLista() throws SQLException {

        Connection con = ConnectionDB.getConnection();
        CallableStatement cstmt = con.prepareCall("{ ?= call COUNTDEFAULTER()}");
        cstmt.registerOutParameter(1, Types.INTEGER);
        cstmt.execute();
        int result = cstmt.getInt(1);

      czarnaLabel.setText(String.valueOf(result));

    }
    private ObservableList data;
    public void bestseller() throws SQLException {
        Connection con = ConnectionDB.getConnection();
        data = FXCollections.observableArrayList();
      CallableStatement cstmt = con.prepareCall("{call SELECTBEST(?)}");
        cstmt.registerOutParameter(1, OracleTypes.CURSOR);
    cstmt.execute();
        ResultSet rs = (ResultSet) cstmt.getObject(1);
        while(rs.next()){
            data.add(new PieChart.Data(rs.getString("tytul2"),rs.getDouble("sprzedana_ilosc")));
        }

    }
public void wykonajbest() throws SQLException {
    Connection con = ConnectionDB.getConnection();
    CallableStatement cstmt = con.prepareCall("{call BESTSELLERS()}");
    cstmt.execute();

}
    XYChart.Series<String, Double> series = new XYChart.Series<>();
public void chartFunction() {
    try {
        Connection con = ConnectionDB.getConnection();
        CallableStatement cstmt = con.prepareCall("{call SELECTREVIEW(?)}");
        cstmt.registerOutParameter(1, OracleTypes.CURSOR);
        cstmt.execute();
        ResultSet rs = (ResultSet) cstmt.getObject(1);
while(rs.next()){
    series.getData().add(new XYChart.Data<>(rs.getString("tytul"),rs.getDouble("srednia")));

}
    } catch (SQLException e) {
        e.printStackTrace();
    }
}



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            liczbaKsiazek();
            liczbaCzytelnikow();
            liczbaWypozyczen();
           czarnaLista();
           wykonajbest();
           bestseller();
           bestsellerChart.getData().addAll(data);
           chartFunction();
           chartRecenzje.getData().addAll(series);
//           Node n = chartRecenzje.lookup(".data0.chart-bar");
//             n.setStyle("-fx-bar-fill: red");
//                n = chartRecenzje.lookup(".data1.chart-bar");
//                n.setStyle("-fx-bar-fill: blue");
//                n = chartRecenzje.lookup(".data2.chart-bar");
//                n.setStyle("-fx-bar-fill: green");
//                n = chartRecenzje.lookup(".data3.chart-bar");
//                n.setStyle("-fx-bar-fill: orange");
//                n = chartRecenzje.lookup(".data4.chart-bar");
//                n.setStyle("-fx-bar-fill: black");
//                n = chartRecenzje.lookup(".data5.chart-bar");
//                n.setStyle("-fx-bar-fill: #d000ff");
//                n = chartRecenzje.lookup(".data6.chart-bar");
//                n.setStyle("-fx-bar-fill: #00ffbb");
//                n = chartRecenzje.lookup(".data7.chart-bar");
//                n.setStyle("-fx-bar-fill: #7300ff");
//                n = chartRecenzje.lookup(".data8.chart-bar");
//                n.setStyle("-fx-bar-fill: #b7ff00");
//                n = chartRecenzje.lookup(".data9.chart-bar");
//                n.setStyle("-fx-bar-fill: #ff0090");
//
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
