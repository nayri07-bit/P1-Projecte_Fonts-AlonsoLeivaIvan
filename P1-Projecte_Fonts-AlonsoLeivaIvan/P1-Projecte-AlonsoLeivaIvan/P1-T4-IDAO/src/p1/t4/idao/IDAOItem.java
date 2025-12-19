package p1.t4.idao;

import java.util.List;
import p1.t4.model.Item;

public interface IDAOItem {
    List<Item> getAll();
    Item getById(int codi);
    void insert(Item item) throws Exception;
    void update(Item item) throws Exception;
    void delete(int codi) throws Exception;
}
