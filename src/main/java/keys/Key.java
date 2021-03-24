package keys;

import eventslogger.EncryptionLogger;
import java.security.SecureRandom;

public class Key {
    private int randomKey;

    public Key() {
        EncryptionLogger logger = new EncryptionLogger();
        logger.writeDebugLog("Generating a new key");
        SecureRandom secureRandomNumber = new SecureRandom();
        while (true) {
            int number = secureRandomNumber.nextInt(26);
            if (coPrime(number, 26) && number !=7) {
                this.randomKey = number;
                break;
            } else {
                logger.writeErrorLog("Invalid key created, trying again");
            }
        }
    }
    public int getRandomKey() {
        return randomKey;
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
