package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class czytelnicy {
    private final IntegerProperty id_czytelnika;
    private final StringProperty nazwisko;
    private final StringProperty imie;
    private final StringProperty adres;
    private final StringProperty miasto;
    private final StringProperty kod_pocztowy;
    private final IntegerProperty blokada;

    public int getId_czytelnika() {
        return id_czytelnika.get();
    }
    public IntegerProperty id_czytelnikaProperty() {
        return id_czytelnika;
    }
    public void setId_czytelnika(int id_czytelnika) {
        this.id_czytelnika.set(id_czytelnika);
    }

    public String getNazwisko() {
        return nazwisko.get();
    }
    public StringProperty nazwiskoProperty() {
        return nazwisko;
    }
    public void setNazwisko(String nazwisko) {
        this.nazwisko.set(nazwisko);
    }

    public String getImie() {
        return imie.get();
    }
    public StringProperty imieProperty() {
        return imie;
    }
    public void setImie(String imie) {
        this.imie.set(imie);
    }

    public String getAdres() {
        return adres.get();
    }
    public StringProperty adresProperty() {
        return adres;
    }
    public void setAdres(String adres) {
        this.adres.set(adres);
    }

    public String getMiast() {
        return miasto.get();
    }
    public StringProperty miastoProperty() {
        return miasto;
    }
    public void setmiasto(String miasto) {
        this.miasto.set(miasto);
    }

    public String getKod_pocztowy() { return kod_pocztowy.get(); }
    public StringProperty kod_pocztowyProperty() {
        return kod_pocztowy;
    }
    public void setKod_pocztowy(String kod_pocztowy) {
        this.kod_pocztowy.set(kod_pocztowy);
    }

    public int getBlokada() {
        return blokada.get();
    }
    public IntegerProperty blokadaProperty() {
        return blokada;
    }
    public void setBlokada(int blokada) { this.blokada.set(blokada); }

    public czytelnicy() {
        id_czytelnika = new SimpleIntegerProperty(this, "id_czytelnika");
        nazwisko = new SimpleStringProperty(this, "nazwisko");
        imie = new SimpleStringProperty(this, "imie");
        adres = new SimpleStringProperty(this, "adres");
        miasto = new SimpleStringProperty(this, "miasto");
        kod_pocztowy = new SimpleStringProperty(this, "kod_pocztowy");
        blokada = new SimpleIntegerProperty(this, "blokada");



    }
}
