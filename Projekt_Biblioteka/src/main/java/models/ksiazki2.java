package models;

public class ksiazki2 {
    private int ID;
    private String tytul2;


    public ksiazki2(int id, String tytul) {
        super();
        ID=id;
        this.tytul2=tytul;
    }
    @Override
    public String toString() {
        return "("+ID+")  "+tytul2;
    }

    public int getID() {
        return ID;
    }

    public String getTytul2() {
        return tytul2;
    }
}
