package ibm.eda.kc.persistence;


import ibm.eda.kc.persistence.order.Order;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.repository.CrudRepository;

@EntityScan("ibm.eda.kc.persistence")
public interface EdaMysqlRepository extends CrudRepository<Order, String> {
    Order findByOrderId(String id);
    //List<SomPayloadEntity> findByOrderContaining(String searchWord);

    //void deleteByKey(String key);
}
