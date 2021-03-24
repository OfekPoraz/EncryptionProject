package directoryencryptor;

import encryptionalgorithms.EncryptionAlgorithm;
import eventslogger.EncryptionLogger;
import exceptions.InvalidKeyException;
import fileencryptor.FileEncryptor;
import utils.FileOperations;

import java.io.IOException;

public class ASyncDirectoryTask extends DirectoryProcessor implements Runnable {
    private Boolean trueIfEncrypt;
    private FileEncryptor fileEncryptor;
    private String fileName;
    private EncryptionLogger logger = new EncryptionLogger();

    public ASyncDirectoryTask(String pathToDir, EncryptionAlgorithm encryptionAlgorithm, FileOperations keyFile,
                              FileOperations encryptDir, FileOperations decryptDir, Boolean trueIfEncrypt, String fileName) {
        super(keyFile, encryptionAlgorithm, pathToDir);
        this.trueIfEncrypt = trueIfEncrypt;
        setEncryptedDirectoryOperations(encryptDir);
        setDecryptedDirectoryOperations(decryptDir);
        this.fileName = fileName;
    }

    @Override
    public void encryptDirectory() throws InvalidKeyException, IOException {
        fileEncryptor.encryptFile(true);
    }

    @Override
    public void decryptDirectory() throws IOException {
        fileEncryptor.decryptFile();
    }

    @Override
    public void run() {
        try {
            fileEncryptor = new FileEncryptor(getEncryptionAlgorithm(), getEncryptedDirectoryOperations(),
                    getDecryptedDirectoryOperations(), getKeyFile(), fileName);
            fileEncryptor.addObserver(new EncryptionLogger(this.fileEncryptor));
            if (Boolean.TRUE.equals(trueIfEncrypt)){
                encryptDirectory();
            } else {
                decryptDirectory();
            }
        } catch (IOException | InvalidKeyException e) {
            logger.writeErrorLog(e.getMessage());
        }


    }
}
