package p1.t4.model;

public class UnitatMesura {

    private int um_codi;
    private String nom;
    private Component m_Component;

    public UnitatMesura() {
    }

    public UnitatMesura(int um_codi) {
        this.um_codi = um_codi;
    }
    
    public UnitatMesura(int um_codi, String nom) {
        this.um_codi = um_codi;
        this.nom = nom;
        this.m_Component = null;
    }
    
    public UnitatMesura(int um_codi, String nom, Component m_Component) {
        this.um_codi = um_codi;
        this.nom = nom;
        this.m_Component = m_Component;
    }

    public int getCodiu() {
        return um_codi;
    }

    public void setCodiu(int um_codi) {
        this.um_codi = um_codi;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Component getM_Component() {
        return m_Component;
    }

    public void setM_Component(Component m_Component) {
        this.m_Component = m_Component;
    }

    @Override
    public String toString() {
        return "UnitatMesura{" + "um_codi=" + um_codi + ", nom=" + nom + ", m_Component=" + m_Component + '}';
    }

}
