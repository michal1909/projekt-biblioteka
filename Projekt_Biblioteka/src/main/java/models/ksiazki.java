package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ksiazki {
    private final IntegerProperty id_ksiazki;
    private final IntegerProperty id_autora;
    private final StringProperty tytul;
    private final StringProperty wydawnictwo;
    private final StringProperty gatunek;
    private final IntegerProperty rok_wydania;
    private final IntegerProperty ilosc_egzemplarzy;
    private final IntegerProperty cena;



    public int getId_ksiazki() {
        return id_ksiazki.get();
    }

    public IntegerProperty id_ksiazkiProperty() {
        return id_ksiazki;
    }

    public void setId_ksiazki(int id_ksiazki) {
        this.id_ksiazki.set(id_ksiazki);
    }

    public int getId_autora() {
        return id_autora.get();
    }

    public IntegerProperty id_autoraProperty() {
        return id_autora;
    }

    public void setId_autora(int id_autora) {
        this.id_autora.set(id_autora);
    }

    public String getTytul() {
        return tytul.get();
    }

    public StringProperty tytulProperty() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul.set(tytul);
    }

    public String getWydawnictwo() {
        return wydawnictwo.get();
    }

    public StringProperty wydawnictwoProperty() {
        return wydawnictwo;
    }

    public void setWydawnictwo(String wydawnictwo) {
        this.wydawnictwo.set(wydawnictwo);
    }

    public String getGatunek() {
        return gatunek.get();
    }

    public StringProperty gatunekProperty() {
        return gatunek;
    }

    public void setGatunek(String gatunek) {
        this.gatunek.set(gatunek);
    }

    public int getRok_wydania() {
        return rok_wydania.get();
    }

    public IntegerProperty rok_wydaniaProperty() {
        return rok_wydania;
    }

    public void setRok_wydania(int rok_wydania) {
        this.rok_wydania.set(rok_wydania);
    }

    public int getIlosc_egzemplarzy() {
        return ilosc_egzemplarzy.get();
    }

    public IntegerProperty ilosc_egzemplarzyProperty() {
        return ilosc_egzemplarzy;
    }

    public void setIlosc_egzemplarzy(int ilosc_egzemplarzy) {
        this.ilosc_egzemplarzy.set(ilosc_egzemplarzy);
    }

    public int getCena() {
        return cena.get();
    }

    public IntegerProperty cenaProperty() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena.set(cena);
    }
    public ksiazki() {
      id_autora = new SimpleIntegerProperty(this, "id_ksiazki");
       id_ksiazki = new SimpleIntegerProperty(this, "id_autora");
        tytul = new SimpleStringProperty(this, "Imię");
      wydawnictwo = new SimpleStringProperty(this, "Telefon");
     gatunek = new SimpleStringProperty(this, "Miejscowość");
      rok_wydania = new SimpleIntegerProperty(this, "Ulica");
     ilosc_egzemplarzy = new SimpleIntegerProperty(this, "Numer_domu");
      cena = new SimpleIntegerProperty(this, "Kod_pocztowy");



    }

}
