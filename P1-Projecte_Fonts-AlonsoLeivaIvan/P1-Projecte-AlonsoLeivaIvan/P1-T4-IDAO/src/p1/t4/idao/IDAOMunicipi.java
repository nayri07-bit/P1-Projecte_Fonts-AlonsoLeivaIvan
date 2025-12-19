package p1.t4.idao;

import java.util.List;
import p1.t4.model.Municipi;

public interface IDAOMunicipi {
    List<Municipi> getAll();
    Municipi getById(int codi);
}
