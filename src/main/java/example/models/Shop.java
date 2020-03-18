package example.models;

import com.yahoo.elide.annotation.Include;
import java.time.OffsetDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Include
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private OffsetDateTime creationDate = OffsetDateTime.now();

    @ManyToOne
    private Customer customer;

}
