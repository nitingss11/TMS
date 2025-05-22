import java.math.BigDecimal;

import config.TransactionConfig;
import model.TransactionType;
import service.AccountService;
import service.TransactionService;

public class Main {
    public static void main(String[] args) {
        
        TransactionConfig cfg   = new TransactionConfig();
        AccountService accSvc = new AccountService();
        TransactionService txnSvc = new TransactionService(cfg);

        var person1 = accSvc.create(new BigDecimal("1000.00"));
        var person2   = accSvc.create(new BigDecimal("500.00"));
        
        System.out.println("**************************************");
        // Account transfer (OK)
        var t1 = txnSvc.transfer(person1, person2, new BigDecimal("250.00"));
        System.out.println("Account transfer status: " + t1.getStatus());

        // Cash deposit (OK)
        var c1 = txnSvc.cash(person1, TransactionType.CASH_CREDIT, new BigDecimal("300.00"));
        System.out.println("Cash deposit status:  " + c1.getStatus());

        // balances
        System.out.println("person1: " + person1.getBalance());
        System.out.println("person2:   "   + person2.getBalance());
        System.out.println("**************************************");


        System.out.println("**************************************");
        // PerTxn limit exceeded (Fail)
        var t2 = txnSvc.transfer(person1, person2, new BigDecimal("20000.00"));
        System.out.println("Over-limit transfer status: " + t2.getStatus() + " | reason: " + t2.getFailureReason());

        // Insufficient balance on cash withdrawal (Fail)
        var c2 = txnSvc.cash(person2, TransactionType.CASH_DEBIT, new BigDecimal("1000.00"));
        System.out.println("Cash withdrawal status: " + c2.getStatus()
                           + " | reason: " + c2.getFailureReason());

        // Insufficient balance on transfer (Fail)
        var t3 = txnSvc.transfer(person2, person1, new BigDecimal("2000.00"));
        System.out.println("Insufficient-funds transfer status: " + t3.getStatus()
                           + " | reason: " + t3.getFailureReason());

        // balances should remain same
        System.out.println("person1: " + person1.getBalance());
        System.out.println("person2: " + person2.getBalance());
        System.out.println("**************************************");

    }
}
