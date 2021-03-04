package DirectoryEncryptor;

import EncryptionAlgorithms.EncryptionAlgorithm;

import EventsLogger.Events;
import Exceptions.InvalidKeyException;
import Utils.FileOperations;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ASyncDirectoryProcessor extends DirectoryProcessor {

    private final ExecutorService encryptThreadExecutor = Executors.newFixedThreadPool(10);
    private final ExecutorService decryptThreadExecutor = Executors.newFixedThreadPool(10);
    private FileOperations encryptedDir;
    private FileOperations decryptedDir;

    public ASyncDirectoryProcessor(String pathToDir,
                                   FileOperations keyFile,
                                   EncryptionAlgorithm encryptionAlgorithm) throws IOException, InvalidKeyException {
        super(keyFile, encryptionAlgorithm, pathToDir);
        this.encryptedDir = new FileOperations(pathToDir,
                encryptionAlgorithm.getNameOfEncryption() + "EncryptedASynchronized",
                true);
        this.decryptedDir = new FileOperations(pathToDir,
                encryptionAlgorithm.getNameOfEncryption() + "DecryptedASynchronized",
                true);
        generateKey();
    }

    @Override
    public void encryptDirectory() {
        setEvent(getPathToDir(), encryptedDir.getPathToFile() , Events.EncryptionStarted, getEncryptionAlgorithm());
        encryptDecryptScript(true);
        setEvent(getPathToDir(), encryptedDir.getPathToFile() , Events.EncryptionEnded, getEncryptionAlgorithm());
    }

    @Override
    public void decryptDirectory() {
        setEvent(getPathToDir(), encryptedDir.getPathToFile() , Events.DecryptionStarted, getEncryptionAlgorithm());
        encryptDecryptScript(false);
        setEvent(getPathToDir(), encryptedDir.getPathToFile() , Events.DecryptionEnded, getEncryptionAlgorithm());

    }

    public void encryptDecryptScript(Boolean encryptOrDecrypt){
        ExecutorService executorService;

        if (encryptOrDecrypt) {
            executorService = encryptThreadExecutor;
        } else {
            executorService = decryptThreadExecutor;
            while (!encryptThreadExecutor.isTerminated());
        }

        for (String file : getFileNames()) {
            Runnable t = new ASyncDirectoryTask(getPathToDir(),
                    getEncryptionAlgorithm(),
                    getKeyFile(),
                    encryptedDir,
                    decryptedDir,
                    encryptOrDecrypt,
                    file);
            executorService.execute(t);
        }
        executorService.shutdown();

        while (!executorService.isTerminated()) {}
    }
}
