package example.stores;

import com.yahoo.elide.core.DataStoreTransaction;
import com.yahoo.elide.core.RequestScope;
import com.yahoo.elide.core.datastore.wrapped.TransactionWrapper;
import com.yahoo.elide.core.filter.expression.FilterExpression;
import com.yahoo.elide.core.pagination.Pagination;
import com.yahoo.elide.core.sort.Sorting;
import com.yahoo.elide.datastores.jpa.JpaDataStore;
import com.yahoo.elide.datastores.jpa.transaction.JpaTransaction;
import example.models.Customer;
import java.io.Serializable;
import java.util.Optional;

public class CustomerStore extends JpaDataStore {

    public CustomerStore(
        EntityManagerSupplier entityManagerSupplier,
        JpaTransactionSupplier transactionSupplier) {
        super(entityManagerSupplier, transactionSupplier, Customer.class);
    }

    @Override
    public DataStoreTransaction beginTransaction() {
        JpaTransaction jpaTxn = (JpaTransaction) super.beginTransaction();
        jpaTxn.begin();
        return new AccountStoreTransaction(jpaTxn);
    }

    private class AccountStoreTransaction extends TransactionWrapper {

        AccountStoreTransaction(DataStoreTransaction wrappedTxn) {
            super(wrappedTxn);
        }

        @Override
        public Object loadObject(
            Class<?> entityClass, Serializable id, Optional<FilterExpression> filterExpression, RequestScope scope) {
            Object returnObj = super.loadObject(entityClass, id, filterExpression, scope);
            if (null == returnObj) {
                throw new RuntimeException("not found");
            }

            return returnObj;
        }

        @Override
        public Iterable<Object> loadObjects(
            Class<?> entityClass,
            Optional<FilterExpression> filterExpression,
            Optional<Sorting> sorting,
            Optional<Pagination> pagination,
            RequestScope requestScope) {
            return super.loadObjects(entityClass, filterExpression, sorting, pagination, requestScope);
        }
    }
}
