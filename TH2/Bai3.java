import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

class Transaction {
    private String id;
    private double size;
    private double fee;

    public Transaction(String id, double size, double fee) {
        this.id = id;
        this.size = size;
        this.fee = fee;
    }

    public String getId() {
        return id;
    }

    public double getSize() {
        return size;
    }

    public double getFee() {
        return fee;
    }

    public double getRate() {
        return fee / size;
    }
}

class TransactionManager {
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public void readFile(String fileName) {
        try (BufferedReader reader =
                 new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split(",");

                if (parts.length != 3) {
                    continue;
                }

                String id = parts[0].trim();
                double size = Double.parseDouble(parts[1].trim());
                double fee = Double.parseDouble(parts[2].trim());

                if (size > 0 && fee >= 0) {
                    transactions.add(new Transaction(id, size, fee));
                }
            }
        } catch (IOException e) {
            System.out.println("Khong the doc file!");
        } catch (NumberFormatException e) {
            System.out.println("Du lieu trong file khong hop le!");
        }
    }

    public void selectTransactions() {
        transactions.sort(
            Comparator.comparingDouble(Transaction::getRate).reversed()
        );

        double totalSize = 0;
        double totalFee = 0;

        System.out.println("Danh sach giao dich duoc chon:");

        for (Transaction t : transactions) {
            if (totalSize + t.getSize() <= 1000) {
                System.out.println(
                    t.getId() + " | Size: " + t.getSize()
                    + " kB | Fee: " + t.getFee()
                    + " satoshi | Rate: " + t.getRate()
                );

                totalSize += t.getSize();
                totalFee += t.getFee();
            }
        }

        System.out.println("Tong kich co: " + totalSize + " kB");
        System.out.println("Tong phi: " + totalFee + " satoshi");
    }
}

public class Bai3 {
    public static void main(String[] args) {
        TransactionManager manager = new TransactionManager();

        manager.readFile("trans.txt");
        manager.selectTransactions();
    }
}