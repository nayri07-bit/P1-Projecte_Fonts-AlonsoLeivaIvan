package p1.t4.model;

public class FormacioProducte {

    private int codiItem;
    private int codiProducte;
    private Item item;
    private int quantitat;

    public FormacioProducte() {
    }

    public FormacioProducte(int codiProducte, int codiItem, int quantitat, Item item) {
        this.codiProducte = codiProducte;
        this.codiItem = codiItem;
        this.quantitat = quantitat;
        this.item = item;
    }

    public int getCodiItem() {
        return codiItem;
    }

    public void setCodiItem(int codiItem) {
        this.codiItem = codiItem;
    }

    public int getCodiProducte() {
        return codiProducte;
    }

    public void setCodiProducte(int codiProducte) {
        this.codiProducte = codiProducte;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }

    @Override
    public String toString() {
        return "FormacioProducte{" + "codiItem=" + codiItem + ", codiProducte=" + codiProducte + ", item=" + item + ", quantitat=" + quantitat + '}';
    }
}
