
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;

public class Bai4 {
    public void hex(String args){
        // Kiem tra tham so dau vao
        if (args.length != 1) {
            return;
        }

        // Doc noi dung file
        byte[] data = Files.readAllBytes(Path.of(args[0]));

        // Tao SHA-256
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(data);

        // Chuyen hash sang dang hex
        StringBuilder hex = new StringBuilder();

        for (byte b : hash) {
            hex.append(String.format("%02x", b));
        }

        // Chi in ra mot dong duy nhat
        System.out.println(hex);
    }
    public static void main(String[] args) throws Exception {
        Bai4 bai4 = new Bai4();
        bai4.hex(args);
    }
}

