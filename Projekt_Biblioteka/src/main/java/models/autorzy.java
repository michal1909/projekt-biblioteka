package models;

public class autorzy {
    private int ID;
    private String model;
    private String model2;

    public autorzy(int iD, String model,String model2) {
        super();
        ID = iD;
        this.model = model;
        this.model2=model2;
    }

    public autorzy() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel2() {
        return model2;
    }

    public void setModel2(String model2) {
        this.model2 = model2;
    }

    @Override
    public String toString() {
        return "("+ID+")  "+model+" "+model2;
    }
}
