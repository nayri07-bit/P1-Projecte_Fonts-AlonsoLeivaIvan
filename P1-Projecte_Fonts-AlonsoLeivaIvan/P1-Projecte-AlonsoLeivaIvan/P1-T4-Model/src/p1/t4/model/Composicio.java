package p1.t4.model;

public class Composicio {

    private int itPare;      
    private int itFill;      
    private String nom;      
    private int quantitat;   

    public Composicio() {
    }

    public Composicio(int itPare, int itFill, String nom, int quantitat) {
        this.itPare = itPare;
        this.itFill = itFill;
        this.nom = nom;
        this.quantitat = quantitat;
    }

    public int getItPare() {
        return itPare;
    }

    public void setItPare(int itPare) {
        this.itPare = itPare;
    }

    public int getItFill() {
        return itFill;
    }

    public void setItFill(int itFill) {
        this.itFill = itFill;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }
}
