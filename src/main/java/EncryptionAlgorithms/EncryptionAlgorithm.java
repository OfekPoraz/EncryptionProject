package EncryptionAlgorithms;

import java.io.IOException;

public abstract class EncryptionAlgorithm implements IEncryptionAlgorithm{
    private String nameOfEncryption;

    public EncryptionAlgorithm(String nameOfEncryption) {
        this.nameOfEncryption = nameOfEncryption;
    }

    public String getNameOfEncryption() {
        return nameOfEncryption;
    }

    @Override
    public int getKeyStrength(int encryptionKey) throws IOException {
        return (int) (Math.log10(encryptionKey)+1);
    }


    public boolean checkIfAlphabet(char charToCheck, boolean capitalOrNot){
        if (capitalOrNot){
            return (charToCheck >= 'A') && (charToCheck <= 'Z');
        } else {
            return (charToCheck >= 'a') && (charToCheck <= 'z');
        }
    }

}
