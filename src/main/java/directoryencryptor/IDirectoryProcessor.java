package directoryencryptor;

import exceptions.InvalidKeyException;

import java.io.IOException;

public interface IDirectoryProcessor {
    void encryptDirectory() throws InvalidKeyException, IOException;
    void decryptDirectory() throws IOException;
}
