package p1.t4.idao;

import java.util.List;
import p1.t4.model.Component;

public interface IDAOComponent {
    List<Component> getAll();
    List<Component> getAllOrdenat(String ordre);
    Component getById(int codic);
    List<Component> getByFabricant(String codiFabricant);
    void insert(Component component, int precioInicial) throws Exception;
    void update(Component component);
    void delete(int codic);
    double getPreuMig(int codic);
    void asignarPrecioProveedor(int cmCodi, int pvCodi, int precio);
    List<String> getDistinctFabricants();
}
