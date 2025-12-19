package p1.t4.idao;

import java.util.List;
import p1.t4.model.Compres;

public interface IDAOCompres {
    List<Compres> getAll();
    Compres getById(int codi);
    void insert(Compres compres) throws Exception;
    void update(Compres compres) throws Exception;
    void delete(int codi) throws Exception;
}
