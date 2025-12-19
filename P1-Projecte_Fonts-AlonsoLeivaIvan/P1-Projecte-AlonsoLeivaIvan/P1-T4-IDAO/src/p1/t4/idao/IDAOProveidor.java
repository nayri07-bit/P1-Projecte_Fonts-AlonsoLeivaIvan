package p1.t4.idao;

import java.util.List;
import p1.t4.model.Proveidor;

public interface IDAOProveidor {
    List<Proveidor> getAll();
    Proveidor getById(int codi);
    void insert(Proveidor proveidor) throws Exception;
    void update(Proveidor proveidor) throws Exception;
    void delete(int codi) throws Exception;
}
