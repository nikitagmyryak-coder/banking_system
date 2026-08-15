package domain_models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;

public class InMemoryAccountRepository implements AccountRepository {
    private final Map<String, Account> accounts = new HashMap<>();

    @Override
    public void save(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    @Override
    public Optional<Account> findById(String id) {
        Account value = accounts.get(id);
        return Optional.ofNullable(value);
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(accounts.values());
    }

    @Override
    public void deleteById(String id) {
        accounts.remove(id);
    }

    @Override
    public boolean existsById(String id) {
        return accounts.containsKey(id);
    }
}
