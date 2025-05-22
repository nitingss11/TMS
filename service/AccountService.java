package service;

import model.Account;
import model.AccountType;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AccountService {
    private final Map<UUID, Account> store = new ConcurrentHashMap<>();

    public Account create(BigDecimal openingBalance) {
        return create(openingBalance, AccountType.TypeA);
    }

    public Account create(BigDecimal openingBalance, AccountType type) {
        Account acc = new Account(openingBalance, type);
        store.put(acc.getId(), acc);
        return acc;
    }

    public Account get(UUID id) {
        return store.get(id);
    }
}
