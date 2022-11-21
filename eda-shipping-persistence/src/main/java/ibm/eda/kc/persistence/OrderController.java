package ibm.eda.kc.persistence;

import ibm.eda.kc.persistence.order.Address;
import ibm.eda.kc.persistence.order.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@EntityScan("ibm.eda.kc.persistence")
@RestController
@RequestMapping("/api")
public class EdaRepoController
{
    private final Logger log = LoggerFactory.getLogger(EdaRepoController.class);

    @Autowired
    OrderRepository edaRepository;

    //retrieve all Orders
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam(required = false) String key)
    {
        log.info("...in getAllPayloads()");
        try
        {
            List<Order> orders = new ArrayList<>();
            edaRepository.findAll().forEach((orders::add));
            log.info("Found "+orders.size() +" orders");

            if (orders.isEmpty())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //retrieve order by id
    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id)
    {
        log.info("...in getOrderById()");
        Optional<Order> order = Optional.ofNullable(edaRepository.findById(id));
        if (order.isPresent())
        {
            log.info("Found this order: \n" +order);
            return new ResponseEntity<>(order.get(), HttpStatus.OK);
        }
        else
        {
            log.error("Record not found for order id: "+id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    //create a new Order
    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody Order order)
    {
        log.info("...in createOrder(). Creating this order:\n" +order.toString());
        try
        {
            Order _order = edaRepository.save(initOrder(order));
            log.info("...created order:\n" + _order);
            return new ResponseEntity<>(_order, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            log.error("Error occurred creating new order\n" + order);
            log.error(e.getLocalizedMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Order initOrder(Order order)
    {
        return new Order(
                order.getVoyageId(),
                buildAddress(order.getPickupAddress()),
                order.getPickupDate(),
                buildAddress(order.getDestinationAddress()),
                order.getExpectedDeliveryDate(),
                order.getCreationDate(),
                order.getUpdateDate(),
                order.getStatus(),
                order.getContainerID());
    }

    private Address buildAddress(Address address)
    {
        return new Address(address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZipcode(),
                address.getCountry()
        );
    }

    //update a Order by id
    @PutMapping("/order/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id, @RequestBody Order order)
    {
        log.info("...in updateOrder(). Updating this order:\n" +order);
        Optional<Order> targetOrder = Optional.ofNullable(edaRepository.findById(id));
        if (targetOrder.isPresent())
        {
            return new ResponseEntity<>(edaRepository.save(initOrder(order)), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    //delete a Payload by key
    @DeleteMapping("/payload/{key}")
    public ResponseEntity<HttpStatus> deletePayload(@PathVariable("key") String key)
    {
        log.info("...in deletePayload() held by this key: "+key);
        try
        {
            edaRepository.deleteById(key);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //delete all 
    @DeleteMapping("/payload")
    public ResponseEntity<HttpStatus> deleteAll()
    {
        log.warn("...in deleteAll() \n CAUTION: ABOUT TO DELETE ALL PAYLOADS IN THE DATABASE");
        try
        {
            edaRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
