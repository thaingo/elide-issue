package example.config;

import com.yahoo.elide.core.DataStore;
import com.yahoo.elide.datastores.jpa.transaction.NonJtaTransaction;
import example.stores.CustomStore;
import javax.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public DataStore dataStore(EntityManagerFactory entityManagerFactory) {
        SharedEntityManagerSupplier supplier = new SharedEntityManagerSupplier(entityManagerFactory);
//        DataStore jpaDataStore = new JpaDataStore(supplier, (NonJtaTransaction::new), Shop.class);
        return new CustomStore(supplier, NonJtaTransaction::new);
//        return new MultiplexManager(jpaDataStore, customerStore);
    }

}
