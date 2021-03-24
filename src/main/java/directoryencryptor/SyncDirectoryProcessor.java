package directoryencryptor;

import encryptionalgorithms.EncryptionAlgorithm;
import eventslogger.EncryptionLogger;
import eventslogger.Events;
import fileencryptor.FileEncryptor;
import utils.FileOperations;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SyncDirectoryProcessor extends DirectoryProcessor {
    public static final String SYNC_DECRYPTED = "SyncDecrypted";
    public static final String SYNC_ENCRYPTED = "SyncEncrypted";
    public static final String DIRECTORY_KEY = "DirectoryKey";
    private final List<FileEncryptor> fileEncryptorList = new LinkedList<>();

    public SyncDirectoryProcessor(String pathToDir,
                                  EncryptionAlgorithm encryptionAlgorithm) throws IOException {
        super(new FileOperations(pathToDir, DIRECTORY_KEY + encryptionAlgorithm.getNameOfEncryption(), false),
                encryptionAlgorithm,
                pathToDir);
        generateKey();
        createFiles();
        setObserversForFiles();
    }

    public void createFiles() throws IOException {
        setEncryptedDirectoryOperations(new FileOperations(getPathToDir(),
                getEncryptionAlgorithm().getNameOfEncryption() + SYNC_ENCRYPTED,
                true));
        setDecryptedDirectoryOperations(new FileOperations(getPathToDir(),
                getEncryptionAlgorithm().getNameOfEncryption() + SYNC_DECRYPTED,
                true));

        for (String file :  getFileNames()) {
            FileEncryptor fileEncryptor = new FileEncryptor(getEncryptionAlgorithm(),
                    getEncryptedDirectoryOperations(),
                    getDecryptedDirectoryOperations(),
                    getKeyFile(),
                    file);
            fileEncryptorList.add(fileEncryptor);
        }
    }

    public void setObserversForFiles() {
        for (FileEncryptor fileEncryptor : fileEncryptorList){
            fileEncryptor.addObserver(new EncryptionLogger(fileEncryptor));
        }
    }

    @Override
    public void encryptDirectory() throws IOException {
        setEvent(getDirectoryOperations().getPathToFile(),
                getEncryptedDirectoryOperations().getPathToFile(),
                Events.ENCRYPTION_STARTED,
                getEncryptionAlgorithm());

        for (FileEncryptor fileEncryptor : fileEncryptorList){
            fileEncryptor.encryptFile(true);
        }
        setEvent(getDirectoryOperations().getPathToFile(),
                getEncryptedDirectoryOperations().getPathToFile(),
                Events.ENCRYPTION_ENDED,
                getEncryptionAlgorithm());
    }

    @Override
    public void decryptDirectory() throws IOException {
        setEvent(getEncryptedDirectoryOperations().getPathToFile(),
                getDecryptedDirectoryOperations().getPathToFile(),
                Events.DECRYPTION_STARTED,
                getEncryptionAlgorithm());

        for (FileEncryptor fileEncryptor : fileEncryptorList){
            fileEncryptor.decryptFile();
        }
        setEvent(getEncryptedDirectoryOperations().getPathToFile(),
                getDecryptedDirectoryOperations().getPathToFile(),
                Events.DECRYPTION_ENDED,
                getEncryptionAlgorithm());
    }

}
