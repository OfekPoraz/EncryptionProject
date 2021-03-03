package DirectoryEncryptor;

import EncryptionAlgorithms.EncryptionAlgorithm;
import EventsLogger.EncryptionLog4JLogger;
import EventsLogger.EncryptionLogger;
import Exceptions.InvalidKeyException;
import FileEncryptor.FileEncryptor;
import Utils.FileOperations;

import java.io.File;
import java.io.IOException;

public class ASyncDirectoryProcessor extends Thread implements IDirectoryProcessor {
    private final EncryptionLog4JLogger log4JLogger = new EncryptionLog4JLogger();

    private Boolean TrueIfEncrypt;
    private String pathToFile;
    private String fileName;
    private EncryptionAlgorithm encryptionAlgorithm;
    private FileOperations keyFile;

    private FileOperations fileToEncrypt;
    private FileOperations encryptedFile;
    private FileOperations decryptedFile;
    private FileOperations encryptDir;
    private FileOperations decryptDir;

    private FileEncryptor fileEncryptor;



    public ASyncDirectoryProcessor(String pathToFile, EncryptionAlgorithm encryptionAlgorithm, FileOperations keyFile,
                                   FileOperations encryptDir, FileOperations decryptDir, Boolean TrueIfEncrypt) {

       this.pathToFile = pathToFile;
       this.encryptionAlgorithm = encryptionAlgorithm;
       this.keyFile = keyFile;
       this.TrueIfEncrypt = TrueIfEncrypt;
       this.fileToEncrypt = new FileOperations(pathToFile);
       this.encryptDir = encryptDir;
       this.decryptDir = decryptDir;
       this.fileName = fileToEncrypt.getFileName();
    }


    public void createFiles() throws IOException {

        if (TrueIfEncrypt) {
            this.encryptedFile = new FileOperations(encryptDir.getPathToFile(), getClearName(fileName));
        } else {
            this.encryptedFile = new FileOperations(encryptDir.getPathToFile() + File.separator + fileName);
            this.decryptedFile = new FileOperations(decryptDir.getPathToFile(), getClearName(fileName));
        }
    }

    public String getClearName(String name){
        return name.replaceFirst("[.][^.]+$", "");
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
            createFiles();
            fileEncryptor = new FileEncryptor(encryptionAlgorithm, fileToEncrypt, encryptedFile, decryptedFile, keyFile);
            fileEncryptor.addObserver(new EncryptionLogger(this.fileEncryptor));
            if (TrueIfEncrypt){
                encryptDirectory();
            } else {
                decryptDirectory();
            }
        } catch (IOException | InvalidKeyException e) {
            e.printStackTrace();
        }


    }
}
