package service;

import model.Account;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AccountService {
    private final Map<UUID, Account> store = new ConcurrentHashMap<>();

    public Account create(BigDecimal openingBalance) {
        Account acc = new Account(openingBalance);
        store.put(acc.getId(), acc);
        return acc;
    }

    public Account get(UUID id) {
        return store.get(id);
    }
}
