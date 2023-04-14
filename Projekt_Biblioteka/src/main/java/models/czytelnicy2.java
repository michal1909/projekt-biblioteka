package models;

public class czytelnicy2 {
    private int ID;
    private String imie;
    private String nazwisko;
    private int blokada;
     public czytelnicy2(int id, String imie,String nazwisko) {
        super();
        ID=id;
        this.imie=imie;
        this.nazwisko=nazwisko;
    }
    @Override
    public String toString() {
        return "("+ID+") "+imie+" "+nazwisko;
    }

    public int getID() {
        return ID;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public int getBlokada() {
        return blokada;
    }
}
