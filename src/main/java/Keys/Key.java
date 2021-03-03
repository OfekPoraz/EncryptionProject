package Keys;

import EventsLogger.EncryptionLog4JLogger;
import org.apache.logging.log4j.Level;

import java.security.SecureRandom;

public class Key {
    private final EncryptionLog4JLogger log4JLogger = new EncryptionLog4JLogger();
    private int key;

    public Key() {
        log4JLogger.writeToLogger("Generating a new key", Level.DEBUG);
        SecureRandom secureRandomNumber = new SecureRandom();
        while (true) {
            int number = secureRandomNumber.nextInt(26);
            if (coPrime(number, 26) && number !=7) {
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
