package DirectoryEncryptor;

import EncryptionAlgorithms.EncryptionAlgorithm;
import EventsLogger.EncryptionLogger;
import Exceptions.InvalidKeyException;
import FileEncryptor.FileEncryptor;
import Utils.FileOperations;

import java.io.IOException;

public class ASyncDirectoryTask extends DirectoryProcessor implements Runnable {
    private Boolean TrueIfEncrypt;
    private FileOperations encryptDir;
    private FileOperations decryptDir;
    private FileEncryptor fileEncryptor;
    private String fileName;
    private EncryptionLogger logger = new EncryptionLogger();

    public ASyncDirectoryTask(String pathToDir, EncryptionAlgorithm encryptionAlgorithm, FileOperations keyFile,
                              FileOperations encryptDir, FileOperations decryptDir, Boolean TrueIfEncrypt, String fileName) {
        super(keyFile, encryptionAlgorithm, pathToDir);
        this.TrueIfEncrypt = TrueIfEncrypt;
        this.encryptDir = encryptDir;
        this.decryptDir = decryptDir;
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
            fileEncryptor = new FileEncryptor(getEncryptionAlgorithm(), encryptDir, decryptDir, getKeyFile(), fileName);
            fileEncryptor.addObserver(new EncryptionLogger(this.fileEncryptor));
            if (TrueIfEncrypt){
                encryptDirectory();
            } else {
                decryptDirectory();
            }
        } catch (IOException | InvalidKeyException e) {
            logger.writeErrorLog(e.getMessage());
        }


    }
}
