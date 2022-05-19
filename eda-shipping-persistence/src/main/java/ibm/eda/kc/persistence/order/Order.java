package ibm.eda.kc.persistence.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order
{
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "orderId", nullable = false)
    public String orderId;
    public String voyageId;
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    //@Column(name = "productId", nullable = false)
    public String productId;
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    //@Column(name = "customerId", nullable = false)
    public String customerId;
    public int quantity;

    @Embedded
    public Address pickupAddress = new Address();

    //@OneToOne(cascade = {CascadeType.ALL})
    //@JoinColumn(name = "destination_address_id")
    @Embedded
    @CollectionTable(name = "user_addresses", joinColumns = @JoinColumn(name = "orderId"))
    public Address destinationAddress = new Address();
    public String pickupDate;
    public String expectedDeliveryDate;
    public String creationDate;
    public String updateDate;
    public String status;
    public String containerID;

    public Order(String voyageId, int quantity, Address pickupAddress, String pickupDate, Address destinationAddress,
                 String expectedDeliveryDate, String creationDate, String updateDate, String status, String containerID)
    {
        super();
        this.containerID = containerID;
        this.expectedDeliveryDate = expectedDeliveryDate;
        System.out.println("destination address: "+ destinationAddress.toString());
        //this.destinationAddress = destinationAddress;
        this.destinationAddress.setStreet(destinationAddress.getStreet());
        this.destinationAddress.setCity(destinationAddress.getCity());
        this.destinationAddress.setCountry(destinationAddress.getCountry());
        this.destinationAddress.setState(destinationAddress.getState());
        this.destinationAddress.setZipcode(destinationAddress.getZipcode());
        System.out.println("pick-up address: "+ pickupAddress.toString());
        this.pickupAddress = pickupAddress;
        this.creationDate = creationDate;
        this.pickupDate = pickupDate;
        this.quantity = quantity;
        this.status = status;
        this.voyageId = voyageId;

        System.out.println(this.toString());
    }


    @Override
    public String toString()
    {
        return String.format(
                "Order[orderId='%s', status='%s', creationDate='%s']",
                orderId, status, creationDate);
    }
}