import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class ProofOfWork {
    private String data;
    private int difficulty;
    private int nonce;
    private String hash;
    private long trials;

    public ProofOfWork(String data, int difficulty) {
        this.data = data;
        this.difficulty = difficulty;
    }

    private String calculateHash(String input)
            throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] bytes = md.digest(input.getBytes());

        StringBuilder result = new StringBuilder();

        for (byte b : bytes) {
            result.append(String.format("%02x", b & 0xff));
        }

        return result.toString();
    }

    public void mine() throws NoSuchAlgorithmException {
        String prefix = "0".repeat(difficulty);

        nonce = 0;
        trials = 0;

        while (true) {
            hash = calculateHash(data + nonce);
            trials++;

            if (hash.startsWith(prefix)) {
                break;
            }

            nonce++;
        }
    }

    public void printResult() {
        System.out.println("nonce = " + nonce);
        System.out.println("hash = " + hash);
        System.out.println("trials = " + trials);
    }
}

public class Bai4 {
    public static void main(String[] args)
            throws NoSuchAlgorithmException {
        Scanner scanner = new Scanner(System.in);

        String data = "Hello Nguyen Minh Duc";

        System.out.print("Nhap N: ");
        int n = scanner.nextInt();

        if (n < 0 || n > 64) {
            System.out.println("N phai nam trong khoang 0 den 64");
            scanner.close();
            return;
        }

        ProofOfWork pow = new ProofOfWork(data, n);
        pow.mine();
        pow.printResult();

        scanner.close();
    }
}
