package bank;

import java.util.List;

public class WithdrawCashier extends Cashier {
    public WithdrawCashier(int id, List<Integer> operations) {
        super(id, operations, "выдача");
    }

    @Override
    protected void processOperation(int amount) {
        synchronized (BankSimulation.balanceLock) {
            while (amount > BankSimulation.balance) {
                System.out.printf("Кассир %d (%s): недостаточно средств (%d), ожидаю... Баланс: %d%n",
                        id, type, amount, BankSimulation.balance);
                try {
                    BankSimulation.balanceLock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            BankSimulation.balance -= amount;
            System.out.printf("Кассир %d (%s): выдал %d. Баланс: %d%n",
                    id, type, amount, BankSimulation.balance);
            BankSimulation.balanceLock.notifyAll();
        }
    }
}