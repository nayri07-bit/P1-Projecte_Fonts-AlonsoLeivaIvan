package p1.t4.model;

public class Compres {

    private Component component;
    private Proveidor proveidor;
    private double preu;

    public Compres() { }

    public Compres(Component component, Proveidor proveidor, double preu) {
        this.component = component;
        this.proveidor = proveidor;
        this.preu = preu;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public Proveidor getProveidor() {
        return proveidor;
    }

    public void setProveidor(Proveidor proveidor) {
        this.proveidor = proveidor;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }


    public int getCodiComp() {
        if (component == null) {
            return 0;
        }
        return component.getCodic();
    }

    public int getCodiProv() {
        if (proveidor == null) {
            return 0;
        }
        return proveidor.getCodi();
    }

}
