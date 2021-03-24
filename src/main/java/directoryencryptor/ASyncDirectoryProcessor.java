package directoryencryptor;

import encryptionalgorithms.EncryptionAlgorithm;
import eventslogger.Events;
import utils.FileOperations;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ASyncDirectoryProcessor extends DirectoryProcessor {

    private final ExecutorService encryptThreadExecutor = Executors.newFixedThreadPool(10);
    private final ExecutorService decryptThreadExecutor = Executors.newFixedThreadPool(10);
    public static final String DIRECTORY_KEY = "ASyncDirectoryKey";


    public ASyncDirectoryProcessor(String pathToDir,
                                   EncryptionAlgorithm encryptionAlgorithm) throws IOException{
        super(new FileOperations(pathToDir, DIRECTORY_KEY + encryptionAlgorithm.getNameOfEncryption(), false),
                encryptionAlgorithm,
                pathToDir);
        setEncryptedDirectoryOperations(new FileOperations(pathToDir,
                encryptionAlgorithm.getNameOfEncryption() + "EncryptedASynchronized",
                true));
        setDecryptedDirectoryOperations(new FileOperations(pathToDir,
                encryptionAlgorithm.getNameOfEncryption() + "DecryptedASynchronized",
                true));
        generateKey();
    }

    @Override
    public void encryptDirectory() {
        setEvent(getPathToDir(), getEncryptedDirectoryOperations().getPathToFile() ,
                Events.ENCRYPTION_STARTED, getEncryptionAlgorithm());
        encryptDecryptScript(true);
        setEvent(getPathToDir(), getDecryptedDirectoryOperations().getPathToFile() ,
                Events.ENCRYPTION_ENDED, getEncryptionAlgorithm());
    }

    @Override
    public void decryptDirectory() {
        setEvent(getPathToDir(), getDecryptedDirectoryOperations().getPathToFile() ,
                Events.DECRYPTION_STARTED, getEncryptionAlgorithm());
        encryptDecryptScript(false);
        setEvent(getPathToDir(), getDecryptedDirectoryOperations().getPathToFile() ,
                Events.DECRYPTION_ENDED, getEncryptionAlgorithm());

    }

    public void encryptDecryptScript(Boolean encryptOrDecrypt){
        ExecutorService executorService;
        if (Boolean.TRUE.equals(encryptOrDecrypt)) {
            executorService = encryptThreadExecutor;
        } else {
            executorService = decryptThreadExecutor;
            while (!encryptThreadExecutor.isTerminated());
        }

        for (String file : getFileNames()) {
            Runnable t = new ASyncDirectoryTask(getPathToDir(),
                    getEncryptionAlgorithm(),
                    getKeyFile(),
                    getEncryptedDirectoryOperations(),
                    getDecryptedDirectoryOperations(),
                    encryptOrDecrypt,
                    file);
            executorService.execute(t);
        }
        executorService.shutdown();

        while (!executorService.isTerminated());
    }
}
