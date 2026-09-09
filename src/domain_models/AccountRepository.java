package domain_models;

import java.util.List;
import java.util.Optional;

/**
 * storage contract for accounts (interface); doesn't know implementation details
*/
public interface AccountRepository {
    void save(Account account);
    Optional<Account> findById(String id);
    List<Account> findAll();
    void deleteById(String id);
    boolean existsById(String id);
}
