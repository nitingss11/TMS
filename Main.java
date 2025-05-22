import java.math.BigDecimal;

import model.TransactionType;
import service.AccountService;
import service.TransactionService;

public class Main {
    public static void main(String[] args) {
        
        AccountService accSvc = new AccountService();
        TransactionService txnSvc = new TransactionService();

        var person1 = accSvc.create(new BigDecimal("1000.00"));
        var person2   = accSvc.create(new BigDecimal("500.00"));
        
        // Account transfer
        var t1 = txnSvc.transfer(person1, person2, new BigDecimal("250.00"));
        System.out.println("Account transfer status: " + t1.getStatus());

        // Cash deposit
        var c1 = txnSvc.cash(person1, TransactionType.CASH_CREDIT, new BigDecimal("300.00"));
        System.out.println("Cash deposit status:  " + c1.getStatus());

        // balances
        System.out.println("person1: " + person1.getBalance());
        System.out.println("person2:   "   + person2.getBalance());
    }
}
