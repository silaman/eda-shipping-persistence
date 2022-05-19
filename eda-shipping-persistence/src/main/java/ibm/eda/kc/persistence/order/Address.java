package ibm.eda.kc.persistence.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Setter
@Getter
@NoArgsConstructor
@Embeddable
public class Address {
//    @Id
//    @GeneratedValue(generator="system-uuid")
//    @GenericGenerator(name="system-uuid", strategy = "uuid")
//    public Long id;
    @Column(name = "street_column", insertable=false, updatable = false)
    public String street;
    @Column(name = "city_column", insertable=false, updatable = false)
    public String city;
    @Column(name = "country_column", insertable=false, updatable = false)
    public String country;
    @Column(name = "state_column", insertable=false, updatable = false)
    public String state;
    @Column(name = "zip_column", insertable=false, updatable = false)
    public String zipcode;

    public Address(String street, String city, String state, String zipcode, String country)
    {
        this.street=street;
        this.city=city;
        this.state=state;
        this.zipcode=zipcode;
        this.country=country;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString()
    {
        return String.format(
                "Address[street='%s', city='%s', state='%s', country='%s', zip='%s']",
                street, city,state,country,zipcode);
    }

}
