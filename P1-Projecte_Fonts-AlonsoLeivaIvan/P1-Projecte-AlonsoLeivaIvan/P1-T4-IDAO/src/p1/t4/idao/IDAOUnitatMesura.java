package p1.t4.idao;

import java.util.List;
import p1.t4.model.UnitatMesura;

public interface IDAOUnitatMesura {
    List<UnitatMesura> getAll();
    UnitatMesura getById(int codi);
}
