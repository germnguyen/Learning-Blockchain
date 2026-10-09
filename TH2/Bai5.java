import java.util.Scanner;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.math.BigInteger;


public class AddressGenerator {
    private static final String BASE58 =
            "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";

    private byte[] sha256(byte[] data)
            throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(data);
    }

    private byte[] toBytes(String hex) {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException("Public Key Hex khong hop le");
        }

        byte[] result = new byte[hex.length() / 2];

        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) Integer.parseInt(
                    hex.substring(i * 2, i * 2 + 2), 16
            );
        }

        return result;
    }

    private String base58Encode(byte[] input) {
        BigInteger number = new BigInteger(1, input);
        StringBuilder result = new StringBuilder();

        while (number.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] division =
                    number.divideAndRemainder(BigInteger.valueOf(58));

            result.insert(0, BASE58.charAt(division[1].intValue()));
            number = division[0];
        }

        for (byte b : input) {
            if (b == 0) {
                result.insert(0, '1');
            } else {
                break;
            }
        }

        return result.toString();
    }

    public String generateAddress(String publicKey)
            throws NoSuchAlgorithmException {

        byte[] publicKeyBytes = toBytes(publicKey);

        byte[] hash1 = sha256(publicKeyBytes);

        byte[] hash160 = new byte[20];
        System.arraycopy(hash1, 0, hash160, 0, 20);

        byte[] versionedHash = new byte[21];
        versionedHash[0] = 0x00;
        System.arraycopy(hash160, 0, versionedHash, 1, 20);

        byte[] checksumHash = sha256(sha256(versionedHash));

        byte[] result = new byte[25];
        System.arraycopy(versionedHash, 0, result, 0, 21);
        System.arraycopy(checksumHash, 0, result, 21, 4);

        return base58Encode(result);
    }
}

public class Bai5 {
    public static void main(String[] args)
            throws NoSuchAlgorithmException {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhap Public Key (Hex): ");
        String publicKey = scanner.nextLine().trim();

        AddressGenerator generator = new AddressGenerator();

        try {
            String address = generator.generateAddress(publicKey);
            System.out.println("Bitcoin Address: " + address);
        } catch (IllegalArgumentException e) {
            System.out.println("Public Key khong hop le!");
        }

        scanner.close();
    }
} 

