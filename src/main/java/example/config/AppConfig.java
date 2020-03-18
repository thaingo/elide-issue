package example.config;

import com.yahoo.elide.core.DataStore;
import com.yahoo.elide.datastores.jpa.JpaDataStore;
import com.yahoo.elide.datastores.jpa.transaction.NonJtaTransaction;
import com.yahoo.elide.datastores.multiplex.MultiplexManager;
import example.models.Shop;
import example.stores.CustomerStore;
import javax.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public DataStore dataStore(EntityManagerFactory entityManagerFactory) {
        SharedEntityManagerSupplier supplier = new SharedEntityManagerSupplier(entityManagerFactory);
        DataStore jpaDataStore = new JpaDataStore(supplier, (NonJtaTransaction::new), Shop.class);
        CustomerStore customerStore = new CustomerStore(supplier, NonJtaTransaction::new);
        return new MultiplexManager(jpaDataStore, customerStore);
    }

}
