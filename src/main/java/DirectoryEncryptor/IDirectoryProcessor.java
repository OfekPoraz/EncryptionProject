package DirectoryEncryptor;

import Exceptions.InvalidKeyException;

import java.io.IOException;

public interface IDirectoryProcessor {
    void encryptDirectory() throws InvalidKeyException, IOException;
    void decryptDirectory() throws IOException;
}
