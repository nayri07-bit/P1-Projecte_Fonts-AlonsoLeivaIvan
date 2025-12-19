package p1.t4.model;

public class Provincia {

    private int codiProv;
    private String nom;
    private Municipi m_Municipi;

    public Provincia() {
    }

    public Provincia(int codiProv, String nom, Municipi m_Municipi) {
        this.codiProv = codiProv;
        this.nom = nom;
        this.m_Municipi = m_Municipi;
    }

    public int getCodiProv() {
        return codiProv;
    }

    public void setCodiProv(int codiProv) {
        this.codiProv = codiProv;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Municipi getM_Municipi() {
        return m_Municipi;
    }

    public void setM_Municipi(Municipi m_Municipi) {
        this.m_Municipi = m_Municipi;
    }

    @Override
    public String toString() {
        return "Provincia{" + "codiProv=" + codiProv + ", nom=" + nom + ", m_Municipi=" + m_Municipi + '}';
    }
}
