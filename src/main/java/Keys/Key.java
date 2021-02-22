package Keys;

import java.security.SecureRandom;

public class Key {
    private int key;

    public Key() {
        SecureRandom secureRandomNumber = new SecureRandom();
        while (true) {
            int number = secureRandomNumber.nextInt(26);
            if (coPrime(number, 26)) {
                this.key = number;
                break;
            }
        }
    }
    public int getKey() {
        return key;
    }

    public boolean coPrime(int a, int b){
        return (gcd(a,b) == 1);
    }

    public int gcd(int a, int b){
        if (a == 0 || b == 0){
            return 0;
        }
        if (a == b){
            return a;
        }
        if (a > b){
            return gcd(a-b, b);
        } else {
            return gcd(a, b-a);
        }

    }
}
