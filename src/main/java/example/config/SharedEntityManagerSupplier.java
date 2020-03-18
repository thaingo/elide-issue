package example.config;

import com.yahoo.elide.datastores.jpa.JpaDataStore;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SharedEntityManagerSupplier implements JpaDataStore.EntityManagerSupplier {

    private ThreadLocal<EntityManager> entityManagerThreadLocal;
    private EntityManagerFactory emf;

    public SharedEntityManagerSupplier(EntityManagerFactory factory) {
        this.emf = factory;
        entityManagerThreadLocal = new ThreadLocal<>();
    }

    @Override
    public EntityManager get() {
        EntityManager mgr = entityManagerThreadLocal.get();
        if (mgr == null || !mgr.isOpen()) {
            mgr = emf.createEntityManager();
            entityManagerThreadLocal.set(mgr);
        }
        return mgr;
    }
}
