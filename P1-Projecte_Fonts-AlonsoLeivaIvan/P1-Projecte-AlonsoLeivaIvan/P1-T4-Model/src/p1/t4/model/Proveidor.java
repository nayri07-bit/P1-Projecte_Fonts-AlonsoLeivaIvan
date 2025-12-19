package p1.t4.model;

public class Proveidor {

    private int codi;
    private String cif;
    private String raoSocial;
    private String liniaAdrecaFacturacio;
    private String personaContacte;
    private String telfContacte;
    private Compres m_Compres;
    private int municipi;
    private int provincia;

    public Proveidor() {
    }

    public Proveidor(int codi, String cif, String raoSocial, String liniaAdrecaFacturacio,
                     String personaContacte, String telfContacte, Compres m_Compres) {
        this.codi = codi;
        this.cif = cif;
        this.raoSocial = raoSocial;
        this.liniaAdrecaFacturacio = liniaAdrecaFacturacio;
        this.personaContacte = personaContacte;
        this.telfContacte = telfContacte;
        this.m_Compres = m_Compres;
    }

    public int getMunicipi() {
        return municipi;
    }

    public void setMunicipi(int municipi) {
        this.municipi = municipi;
    }

    public int getProvincia() {
        return provincia;
    }

    public void setProvincia(int provincia) {
        this.provincia = provincia;
    }

    public int getCodi() {
        return codi;
    }

    public void setCodi(int codi) {
        this.codi = codi;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getRaoSocial() {
        return raoSocial;
    }

    public void setRaoSocial(String raoSocial) {
        this.raoSocial = raoSocial;
    }

    public String getLiniaAdrecaFacturacio() {
        return liniaAdrecaFacturacio;
    }

    public void setLiniaAdrecaFacturacio(String liniaAdrecaFacturacio) {
        this.liniaAdrecaFacturacio = liniaAdrecaFacturacio;
    }

    public String getPersonaContacte() {
        return personaContacte;
    }

    public void setPersonaContacte(String personaContacte) {
        this.personaContacte = personaContacte;
    }

    public String getTelfContacte() {
        return telfContacte;
    }

    public void setTelfContacte(String telfContacte) {
        this.telfContacte = telfContacte;
    }

    public Compres getM_Compres() {
        return m_Compres;
    }

    public void setM_Compres(Compres m_Compres) {
        this.m_Compres = m_Compres;
    }

    public String obtenirAdrecaCompleta() {
        return liniaAdrecaFacturacio;
    }

    @Override
    public String toString() {
        return "Proveidor{" + "codi=" + codi + ", cif=" + cif + ", raoSocial=" + raoSocial + ", liniaAdrecaFacturacio=" + liniaAdrecaFacturacio + ", personaContacte=" + personaContacte + ", telfContacte=" + telfContacte + ", m_Compres=" + m_Compres + '}';
    }
}
