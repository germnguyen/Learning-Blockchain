import java.util.Scanner;
public class Bai3{
    public void XORCipher(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Chon che do( e - ma hoa, d - giai mai): ");
        char mode = scanner.nextLine().charAt(0);

        System.out.print("Nhap khoa (1 - 255): ");
        int key = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Nhap chuoi text: ");
        String text = scanner.nextLine();

        if (mode != 'e' && mode != 'd') {
            System.out.println("Che do khong hop le, ket thuc chuong trinh");
            scanner.close();
            return;
        }

        if (key < 1 || key > 255) {
            System.out.println("Khoa nam trong khoang 1 - 255!");
            scanner.close();
            return;
        }

        StringBuilder ketQua = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            char newChar = (char) (c ^ key);
            ketQua.append(newChar);
        }

        System.out.println("Ket qua: " + ketQua.toString());

        scanner.close();
    }
    public static void main(String[] args) {
        Bai3 bai3= new Bai3();
        bai3.XORCipher();
    }

}



    


        