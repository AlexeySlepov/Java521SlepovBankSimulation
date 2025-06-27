package bank;

import java.util.List;

public abstract class Cashier implements Runnable {
    protected final int id;
    protected final List<Integer> operations;
    protected final String type;

    public Cashier(int id, List<Integer> operations, String type) {
        this.id = id;
        this.operations = operations;
        this.type = type;
    }

    @Override
    public void run() {
        for (int amount : operations) {
            processOperation(amount);

            // Вывод статуса каждые 10 операций
            if (BankSimulation.operationCounter.incrementAndGet() % BankSimulation.REPORT_INTERVAL == 0) {
                synchronized (BankSimulation.balanceLock) {
                    System.out.printf("--- Статус: баланс %d, операций: %d ---%n",
                            BankSimulation.balance, BankSimulation.operationCounter.get());
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.printf("Кассир %d (%s) завершил работу%n", id, type);
    }

    protected abstract void processOperation(int amount);
}