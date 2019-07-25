package rammstein4o;

import java.util.Random;

public class Balance {
    public String accountId = "00009321a800dc9c-de09d91657c60000";
    public double balance;

    public Balance() {
        double start = 100;
        double end = 5000;
        double random = new Random().nextDouble();
        this.balance = start + (random * (end - start));
    }
}
