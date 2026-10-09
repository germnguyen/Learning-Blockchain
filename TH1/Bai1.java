import java.util.Scanner;

public class Bai1{
    public long mod_inverse(long a, long n){
        long t = 0; long new_t = 1;
        long r = n; long new_r = a;

        while(new_r != 0 ){
            long q = r / new_r;

            long temp_t = t - q * new_t;
            t = new_t;
            new_t = temp_t;

            long temp_r = r - q * new_r;
            r = new_r;
            new_r = temp_r;
        }

        if ( r > 1){
            throw new IllegalArgumentException("Khong ton tai nghich dao modulo vi" + a + " và " + n + " co uoc chung la " + r);
        }

        if(t< 0){
            t = t + n;
        }
        return t;
    }

    public static long mod_pow(long a, long e, long n){
        if(n == 1){
            return 0;
        }
        
        long result = 1;
        long base = a % n;

        while (e > 0) {
            if(e % 2 == 1) result = (result * base ) / n;

            base = (base * base ) %n;
            
            e = e / 2;
        }

        return result;
    }
    public void tim_cap_khong_nghich_dao(){
        for (long a = 2; a <= 10 ; a ++){
            for (long n = a + 1; n <= 20; n++){
                try {
                    mod_inverse(a, n);
                } catch (Exception e) {
                    System.out.println("Da tim thay cap so khong nghich dao a = " + a + ", n = "+ n );
                }
            }
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // System.out.print("Nhap a va n ");
        // long a = sc.nextLong();
        // long n = sc.nextLong();
        Bai1 bai1 = new Bai1();
        // System.out.println("Nghich dao cua " + a +" mod " + n + " la: " + bai1.mod_inverse(a, n)); 
        bai1.tim_cap_khong_nghich_dao();
        sc.close();
    }
}