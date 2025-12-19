package p1.t4.model;

public class Item {

    private int codii;
    private String nom;
    private String descr;
    private boolean esProducte;
    private byte[] foto;
    private int stock;
    private FormacioProducte m_FormacioProducte;
    private double cost;
    
    public Item() {
    }

    public Item(int codii, String nom, String descr, boolean esProducte, byte[] foto, int stock, FormacioProducte m_FormacioProducte, double cost) {
        this.codii = codii;
        this.nom = nom;
        this.descr = descr;
        this.esProducte = esProducte;
        this.foto = foto;
        this.stock = stock;
        this.m_FormacioProducte = m_FormacioProducte;
        this.cost = cost;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    
    public double getCost() {
        return cost; 
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
    public int getCodii() {
        return codii;
    }

    public void setCodii(int codii) {
        this.codii = codii;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public boolean isEsProducte() {
        return esProducte;
    }

    public void setEsProducte(boolean esProducte) {
        this.esProducte = esProducte;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public FormacioProducte getM_FormacioProducte() {
        return m_FormacioProducte;
    }

    public void setM_FormacioProducte(FormacioProducte m_FormacioProducte) {
        this.m_FormacioProducte = m_FormacioProducte;
    }

    public void decrementarStock(int quantitat) {
        if (quantitat <= stock) {
            stock -= quantitat;
        } else {
            stock = 0;
        }
    }

    public void incrementarStock(int quantitat) {
        stock += quantitat;
    }

    @Override
    public String toString() {
        return "Item{" + "codii=" + codii + ", nom=" + nom + ", descr=" + descr + ", esProducte=" + esProducte + ", foto=" + foto + ", stock=" + stock + ", m_FormacioProducte=" + m_FormacioProducte + '}';
    }
    
}
