package p1.t4.idao;

import java.util.List;
import p1.t4.model.FormacioProducte;

public interface IDAOFormacioProducte {
    List<FormacioProducte> getAll();
    FormacioProducte getById(int codi);
    void insert(FormacioProducte formacioProducte) throws Exception;
    void update(FormacioProducte formacioProducte) throws Exception;
    void delete(int codi) throws Exception;
}
