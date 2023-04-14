package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class wypozyczenia {
    private final IntegerProperty id_wypozyczenia;
    private final IntegerProperty id_egzemplarza;
    private final IntegerProperty id_czytelnika;
    private final StringProperty data_wypozyczenia;
    private final StringProperty data_zwrotu;
    private final StringProperty czy_oddane;


    public int getId_wypozyczenia() {
        return id_wypozyczenia.get();
    }

    public IntegerProperty id_wypozyczeniaProperty() {
        return id_wypozyczenia;
    }

    public void setId_wypozyczenia(int id_wypozyczenia) {
        this.id_wypozyczenia.set(id_wypozyczenia);
    }

    public int getId_egzemplarza() {
        return id_egzemplarza.get();
    }

    public IntegerProperty id_egzemplarzaProperty() {
        return id_egzemplarza;
    }

    public void setId_egzemplarza(int id_egzemplarza) {
        this.id_egzemplarza.set(id_egzemplarza);
    }

    public int getId_czytelnika() {
        return id_czytelnika.get();
    }

    public IntegerProperty id_czytelnikaProperty() {
        return id_czytelnika;
    }

    public void setId_czytelnika(int id_czytelnika) {
        this.id_czytelnika.set(id_czytelnika);
    }

    public String getData_wypozyczenia() {
        return data_wypozyczenia.get();
    }

    public StringProperty data_wypozyczeniaProperty() {
        return data_wypozyczenia;
    }

    public void setData_wypozyczenia(String data_wypozyczenia) {
        this.data_wypozyczenia.set(data_wypozyczenia);
    }

    public String getData_zwrotu() {
        return data_zwrotu.get();
    }

    public StringProperty data_zwrotuProperty() {
        return data_zwrotu;
    }

    public void setData_zwrotu(String data_zwrotu) {
        this.data_zwrotu.set(data_zwrotu);
    }

    public String getCzy_oddane() {
        return czy_oddane.get();
    }

    public StringProperty czy_oddaneProperty() {
        return czy_oddane;
    }

    public void setCzy_oddane(String czy_oddane) {
        this.czy_oddane.set(czy_oddane);
    }

    public wypozyczenia() {
        id_wypozyczenia = new SimpleIntegerProperty(this, "id_wypozyczenia");
        id_egzemplarza = new SimpleIntegerProperty(this, "id_egzemplarza");
        id_czytelnika = new SimpleIntegerProperty(this, "id_czytelnika");
        data_wypozyczenia = new SimpleStringProperty(this, "data_wypozyczenia");
     data_zwrotu = new SimpleStringProperty(this, "data_zwrotu");
      czy_oddane = new SimpleStringProperty(this, "czy_oddane");




    }
}
