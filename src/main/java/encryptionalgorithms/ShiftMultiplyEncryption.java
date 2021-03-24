package encryptionalgorithms;

import java.util.NoSuchElementException;

public class ShiftMultiplyEncryption extends EncryptionAlgorithm{
    private static final int MODULO = 26;
    public ShiftMultiplyEncryption() {
        super("ShiftMultiply");
    }

    @Override
    public String encryptString(String stringToEncrypt, int encryptionKey) throws NoSuchElementException, ClassCastException{
        StringBuffer encryptedData = new StringBuffer();

        for (int i = 0 ; i < stringToEncrypt.length(); i ++){
            if (Character.isAlphabetic(stringToEncrypt.charAt(i))) {
                char upperLower = Character.isUpperCase(stringToEncrypt.charAt(i)) ? 'A' : 'a';
                int dataToEncrypt = stringToEncrypt.charAt(i) - upperLower;
                dataToEncrypt = (dataToEncrypt * encryptionKey) % MODULO;
                encryptedData.append((char) (dataToEncrypt + upperLower));
            } else {
                encryptedData.append(stringToEncrypt.charAt(i));
            }
        }
        return encryptedData.toString();
    }

    @Override
    public String decryptString(String stringToDecrypt, int decryptionKey) throws NoSuchElementException, ClassCastException {
        int decryptionKeyModuloInverse = getReverseModulo(decryptionKey);
        return encryptString(stringToDecrypt , decryptionKeyModuloInverse);
    }

    @Override
    public String getName() {
        return getNameOfEncryption();
    }

    private int getReverseModulo(int key){
        int y = 0;
        int x = 1;
        int innerModulo = MODULO;

            while (key > 1) {
                // q is quotient
                int q = key / innerModulo;
                int t = innerModulo;
                // m is remainder now, process
                // same as Euclid's algo
                innerModulo = key % innerModulo;
                key = t;
                t = y;

                // Update x and y
                y = x - q * y;
                x = t;
            }

            // Make x positive
            if (x < 0)
                x += MODULO;

            return x;
    }
}
