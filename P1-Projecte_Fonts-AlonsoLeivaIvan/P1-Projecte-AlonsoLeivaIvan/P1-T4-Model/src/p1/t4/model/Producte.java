package p1.t4.model;

import java.util.ArrayList;
import java.util.List;

public class Producte {

    private int codip;
    private List<FormacioProducte> composicio;
    private FormacioProducte m_FormacioProducte;
    private Item m_Item;

    public Producte() {
        composicio = new ArrayList<>();
    }

    public Producte(int codip, Item m_Item) {
        this.codip = codip;
        this.m_Item = m_Item;
        this.composicio = new ArrayList<>();
    }

    public int getCodip() {
        return codip;
    }

    public void setCodip(int codip) {
        this.codip = codip;
    }

    public List<FormacioProducte> getComposicio() {
        return composicio;
    }

    public void setComposicio(List<FormacioProducte> composicio) {
        this.composicio = composicio;
    }

    public FormacioProducte getM_FormacioProducte() {
        return m_FormacioProducte;
    }

    public void setM_FormacioProducte(FormacioProducte m_FormacioProducte) {
        this.m_FormacioProducte = m_FormacioProducte;
    }

    public Item getM_Item() {
        return m_Item;
    }

    public void setM_Item(Item m_Item) {
        this.m_Item = m_Item;
    }

    public void afegirComponent(FormacioProducte component) {
        composicio.add(component);
    }

    public void eliminarComponent(FormacioProducte component) {
        composicio.remove(component);
    }

    public double calcularCostTotal() {
        double total = 0;
        for (FormacioProducte fp : composicio) {
            total += fp.getItem().getCost() * fp.getQuantitat();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Producte{" + "codip=" + codip + ", composicio=" + composicio + ", m_FormacioProducte=" + m_FormacioProducte + ", m_Item=" + m_Item + '}';
    }
    
}
