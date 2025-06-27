package bank;

import java.util.List;

public class DepositCashier extends Cashier {
    public DepositCashier(int id, List<Integer> operations) {
        super(id, operations, "пополнение");
    }

    @Override
    protected void processOperation(int amount) {
        synchronized (BankSimulation.balanceLock) {
            while (BankSimulation.balance + amount > BankSimulation.MAX_BALANCE) {
                System.out.printf("Кассир %d (%s): касса переполнена (%d), ожидаю... Баланс: %d%n",
                        id, type, amount, BankSimulation.balance);
                try {
                    BankSimulation.balanceLock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            BankSimulation.balance += amount;
            System.out.printf("Кассир %d (%s): внес %d. Баланс: %d%n",
                    id, type, amount, BankSimulation.balance);
            BankSimulation.balanceLock.notifyAll();
        }
    }
}