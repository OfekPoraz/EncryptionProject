package DirectoryEncryptor;

import EncryptionAlgorithms.EncryptionAlgorithm;
import EventsLogger.EncryptionLogger;
import EventsLogger.Events;
import Exceptions.InvalidKeyException;
import FileEncryptor.FileEncryptor;
import Utils.FileOperations;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SyncDirectoryProcessor extends DirectoryProcessor {
    public static final String SYNC_DECRYPTED = "SyncDecrypted";
    public static final String SYNC_ENCRYPTED = "SyncEncrypted";
    public static final String DIRECTORY_KEY = "DirectoryKey";
    private FileOperations encryptedDirectoryOperations;
    private FileOperations decryptedDirectoryOperations;
    private final List<FileEncryptor> fileEncryptorList = new LinkedList<>();

    public SyncDirectoryProcessor(String pathToDir,
                                  EncryptionAlgorithm encryptionAlgorithm) throws IOException, InvalidKeyException {
        super(new FileOperations(pathToDir, DIRECTORY_KEY + encryptionAlgorithm.getNameOfEncryption()),
                encryptionAlgorithm,
                pathToDir);
        generateKey();
        createFiles();
        setObserversForFiles();
    }

    public void createFiles() throws IOException {
        encryptedDirectoryOperations = new FileOperations(getPathToDir(),
                getEncryptionAlgorithm().getNameOfEncryption() + SYNC_ENCRYPTED,
                true);
        decryptedDirectoryOperations = new FileOperations(getPathToDir(),
                getEncryptionAlgorithm().getNameOfEncryption() + SYNC_DECRYPTED,
                true);

        for (String file :  getFileNames()) {
            FileEncryptor fileEncryptor = new FileEncryptor(getEncryptionAlgorithm(),
                    encryptedDirectoryOperations,
                    decryptedDirectoryOperations,
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
    public void encryptDirectory() throws InvalidKeyException, IOException {
        setEvent(getDirectoryOperations().getPathToFile(),
                encryptedDirectoryOperations.getPathToFile(),
                Events.EncryptionStarted,
                getEncryptionAlgorithm());

        for (FileEncryptor fileEncryptor : fileEncryptorList){
            fileEncryptor.encryptFile(true);
        }
        setEvent(getDirectoryOperations().getPathToFile(),
                encryptedDirectoryOperations.getPathToFile(),
                Events.EncryptionEnded,
                getEncryptionAlgorithm());
    }

    @Override
    public void decryptDirectory() throws IOException {
        setEvent(encryptedDirectoryOperations.getPathToFile(),
                decryptedDirectoryOperations.getPathToFile(),
                Events.DecryptionStarted,
                getEncryptionAlgorithm());

        for (FileEncryptor fileEncryptor : fileEncryptorList){
            fileEncryptor.decryptFile();
        }
        setEvent(encryptedDirectoryOperations.getPathToFile(),
                decryptedDirectoryOperations.getPathToFile(),
                Events.DecryptionEnded,
                getEncryptionAlgorithm());
    }

}
