package p1.t4.idao;

import java.util.List;
import p1.t4.model.Provincia;

public interface IDAOProvincia {
    List<Provincia> getAll();
    Provincia getById(int codi);
}
