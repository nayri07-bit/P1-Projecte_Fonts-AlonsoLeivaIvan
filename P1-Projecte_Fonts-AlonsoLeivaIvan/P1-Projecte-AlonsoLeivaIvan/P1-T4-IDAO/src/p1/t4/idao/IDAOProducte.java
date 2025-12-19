package p1.t4.idao;

import java.util.List;
import p1.t4.model.Producte;

public interface IDAOProducte {
    List<Producte> getAll();
    Producte getById(int codi);
    void insert(Producte producte) throws Exception;
    void update(Producte producte) throws Exception;
    void delete(int codi) throws Exception;
}
