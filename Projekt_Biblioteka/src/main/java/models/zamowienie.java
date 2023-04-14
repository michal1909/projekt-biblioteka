package models;

public class zamowienie {
    private int ID;
    private String tytul;
    public zamowienie(int id,String tytul) {
        super();
        ID=id;
this.tytul=tytul;
    }
    @Override
    public String toString() {
        return "("+ID+")"+" "+tytul;
    }

    public int getID() {
        return ID;
    }

    public String getTytul() {
        return tytul;
    }
}
