package bank;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//TODO: Вы реализуете многопоточную модель банка, в которой несколько кассиров одновременно обслуживают клиентов.
// Кассиры могут вносить деньги в кассу (пополнение) или выдавать деньги из кассы (выдача).

public class BankSimulation {
    public static final int MAX_BALANCE = 1000;
    public static final int MIN_BALANCE = 0;
    public static final int REPORT_INTERVAL = 10;

    public static int balance = 0;
    public static final Object balanceLock = new Object();
    public static final AtomicInteger operationCounter = new AtomicInteger(0);

    public static void main(String[] args) {

        List<Cashier> cashiers = new ArrayList<>();

        // выдача
        cashiers.add(new WithdrawCashier(1, List.of(50, 100, 200, 75, 150)));
        cashiers.add(new WithdrawCashier(2, List.of(120, 80, 60, 90, 110)));
        cashiers.add(new WithdrawCashier(3, List.of(70, 130, 40, 160, 30)));

        // пополнение
        cashiers.add(new DepositCashier(4, List.of(100, 200, 150, 80, 120, 100, 300)));
        cashiers.add(new DepositCashier(5, List.of(90, 60, 180, 70, 140, 200)));


        for (Cashier cashier : cashiers) {
            new Thread(cashier).start();
        }
    }


}
