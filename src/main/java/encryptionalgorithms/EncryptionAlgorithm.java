package encryptionalgorithms;

import java.io.IOException;

public abstract class EncryptionAlgorithm implements IEncryptionAlgorithm{
    private String nameOfEncryption;

    protected EncryptionAlgorithm(String nameOfEncryption) {
        this.nameOfEncryption = nameOfEncryption;
    }

    public String getNameOfEncryption() {
        return nameOfEncryption;
    }

    @Override
    public int getKeyStrength(int encryptionKey) throws IOException {
        return (int) (Math.log10(encryptionKey)+1);
    }


}
