package p1.t4.model;

import java.util.List;

public class Component{

    private int codic;
    private String codiFabricant;
    private double preuMig;
    private List<Proveidor> proveidors;
    private UnitatMesura unitatMesura;
    private List<Compres> compres;

    public Component() {
    }
    
    public Component(int codic, UnitatMesura unitatMesura, String codiFabricant, double preuMig) {
        this.codic = codic;
        this.unitatMesura = unitatMesura;
        this.codiFabricant = codiFabricant;
        this.preuMig = preuMig;
    }
    
    public Component(int codic, UnitatMesura unitatMesura, String codiFabricant,  List<Proveidor> proveidors, List<Compres> compres) {
        this.codic = codic;
        this.codiFabricant = codiFabricant;
        this.unitatMesura = unitatMesura;
        this.proveidors = proveidors;
        this.compres = compres;
        this.recalcularPreuMig();
    }
    
    public double getCost() {
        return preuMig; 
    }
    
    public int getCodic() {
        return codic;
    }

    public void setCodic(int codic) {
        this.codic = codic;
    }

    public String getCodiFabricant() {
        return codiFabricant;
    }

    public void setCodiFabricant(String codiFabricant) {
        this.codiFabricant = codiFabricant;
    }

    public double getPreuMig() {
        return preuMig;
    }

    public void setPreuMig(double preuMig) {
        this.preuMig = preuMig;
    }

    public UnitatMesura getUnitatMesura() {
        return unitatMesura;
    }

    public void setUnitatMesura(UnitatMesura unitatMesura) {
        this.unitatMesura = unitatMesura;
    }

    public List<Proveidor> getProveidors() {
        return proveidors;
    }

    public void setProveidors(List<Proveidor> proveidors) {
        this.proveidors = proveidors;
    }

    public List<Compres> getCompres() {
        return compres;
    }

    public void setCompres(List<Compres> compres) {
        this.compres = compres;
    }

    public void recalcularPreuMig() {
        if (compres == null || compres.isEmpty()) {
            this.preuMig = 0;
            return;
        }

        double suma = 0;
        for (Compres c : compres) {
            suma += c.getPreu();
        }

        this.preuMig = suma / compres.size();
    }

    @Override
    public String toString() {
        return "Component{" + "codic=" + codic + ", codiFabricant=" + codiFabricant + ", preuMig=" + preuMig + ", proveidors=" + proveidors + ", unitatMesura=" + unitatMesura + ", compres=" + compres + '}';
    }
}
