import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Transaction {
    private String from;
    private String to;
    private double amount;

    public Transaction(String from, String to, double amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public double getAmount() {
        return amount;
    }
}
class UTXO {
    private ArrayList<String> wallets = new ArrayList<>();
    private ArrayList<Double> balances = new ArrayList<>();

    private int findWallet(String name) {
        for (int i = 0; i < wallets.size(); i++) {
            if (wallets.get(i).equals(name)) {
                return i;
            }
        }
        return -1;
    }


    private double getBalance(String name) {
        int index = findWallet(name);

        if (index == -1) {
            return 0;
        }

        return balances.get(index);
    }

    private void updateBalance(String name, double amount) {
        int index = findWallet(name);

        if (index == -1) {
            wallets.add(name);
            balances.add(amount);
        } else {
            balances.set(index, balances.get(index) + amount);
        }
    }

    private void processTransaction(Transaction transaction) {
        String from = transaction.getFrom();
        String to = transaction.getTo();
        double amount = transaction.getAmount();

        if (amount <= 0) {
            System.out.println("So tien khong hop le!");
            return;
        }

        if (!from.equals("COINBASE")) {
            if (getBalance(from) < amount) {
                System.out.println("So du khong du: " + from);
                return;
            }

            updateBalance(from, -amount);
        }

        updateBalance(to, amount);
    }

    public void readTransactions(String fileName) {
        try (BufferedReader reader =
                 new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                try {
                    String[] parts = line.split(",");

                    String from = parts[0].split("=")[1]
                            .trim().replace("\"", "");

                    String to = parts[1].split("=")[1]
                            .trim().replace("\"", "");

                    double amount = Double.parseDouble(
                            parts[2].split("=")[1].trim()
                    );

                    Transaction transaction =
                            new Transaction(from, to, amount);

                    processTransaction(transaction);

                } catch (Exception e) {
                    System.out.println(
                        "Giao dich khong hop le: " + line
                    );
                }
            }

        } catch (IOException e) {
            System.out.println("Khong the doc file: " + fileName);
        }
    }

    public void printBalances() {
        System.out.println("===== SO DU CUOI CUNG =====");

        for (int i = 0; i < wallets.size(); i++) {
            System.out.println(
                wallets.get(i) + ": " + balances.get(i) + " coin"
            );
        }
    }
}


public class Bai2 {
    public static void main(String[] args) {
        UTXO utxo = new UTXO();

        utxo.readTransactions("trans.txt");
        utxo.printBalances();
    }
}
