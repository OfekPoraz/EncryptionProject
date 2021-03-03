package DirectoryEncryptor;

import EncryptionAlgorithms.EncryptionAlgorithm;

import EventsLogger.Events;
import Exceptions.InvalidKeyException;
import Keys.Key;
import Utils.FileOperations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ASyncDirectoryEncryptor extends DirectoryProcessor {
    private String pathToDir;
    private FileOperations directoryOperations;

    private FileOperations keyFile;
    private EncryptionAlgorithm encryptionAlgorithm;
    private List<Thread> encryptThreadList = new ArrayList<>();
    private List<Thread> decryptThreadList = new ArrayList<>();

    private FileOperations encryptedDir;
    private FileOperations decryptedDir;

    public ASyncDirectoryEncryptor(String pathToDir, FileOperations keyFile,
                                   EncryptionAlgorithm encryptionAlgorithm) throws IOException, InvalidKeyException {
        this.pathToDir = pathToDir;
        this.directoryOperations = new FileOperations(pathToDir);
        this.keyFile = keyFile;
        this.encryptionAlgorithm = encryptionAlgorithm;

        this.encryptedDir = new FileOperations(pathToDir, encryptionAlgorithm.getNameOfEncryption() + "EncryptedASynchronized", true);
        this.decryptedDir = new FileOperations(pathToDir, encryptionAlgorithm.getNameOfEncryption() + "DecryptedASynchronized", true);
        generateKey();
    }

    public void generateKey() throws IOException, InvalidKeyException {
        Key key = new Key();
        try {
            int k = (int)key.getKey();
        } catch (ClassCastException e){
            setEvent("Error in casting key", Events.Error, encryptionAlgorithm);
            throw new InvalidKeyException("Invalid key, not an int");
        }
        this.keyFile.writeToFile(Integer.toString(key.getKey()));
    }



    public String[] getFileNames() {
        String[] filesToEncrypt = directoryOperations.getFilesFromDirectoryTxt();
        List<String> filesToEncryptList = new ArrayList<>(Arrays.asList(filesToEncrypt));
        filesToEncryptList.removeIf(file -> file.contains("Key"));
        filesToEncrypt = filesToEncryptList.toArray(new String[0]);
        return filesToEncrypt;
    }


    @Override
    public void encryptDirectory() {
        setEvent(pathToDir, encryptedDir.getPathToFile() , Events.EncryptionStarted, encryptionAlgorithm);
        for (String file : getFileNames()) {
            Thread t = new ASyncDirectoryProcessor(pathToDir + File.separator + file, encryptionAlgorithm, keyFile, encryptedDir, decryptedDir, true);
            encryptThreadList.add(t);
            t.start();
        }

        for (Thread t : encryptThreadList){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        setEvent(pathToDir, encryptedDir.getPathToFile() , Events.EncryptionEnded, encryptionAlgorithm);
    }

    @Override
    public void decryptDirectory() throws IOException {
        setEvent(pathToDir, encryptedDir.getPathToFile() , Events.DecryptionStarted, encryptionAlgorithm);
        for (String file : getFileNames()) {
            Thread t = new ASyncDirectoryProcessor(pathToDir + File.separator + file, encryptionAlgorithm, keyFile, encryptedDir, decryptedDir, false);
            decryptThreadList.add(t);
            t.start();
        }

        for (Thread t : decryptThreadList){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        setEvent(pathToDir, encryptedDir.getPathToFile() , Events.DecryptionEnded, encryptionAlgorithm);

    }
}
