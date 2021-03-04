import DirectoryEncryptor.ASyncDirectoryProcessor;
import DirectoryEncryptor.SyncDirectoryProcessor;
import EncryptionAlgorithms.*;
import EventsLogger.EncryptionLogger;
import Exceptions.InvalidKeyException;
import Utils.FileOperations;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InvalidKeyException {
        try {
            String pathToFile = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\resources";
            FileOperations keyFile = new FileOperations("C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\resources\\SyncDirectoryKeyShiftUp.txt");
            SyncDirectoryProcessor directoryProcessor = new SyncDirectoryProcessor(pathToFile, new ShiftUpEncryption());

            directoryProcessor.addObserver(new EncryptionLogger(directoryProcessor));

            directoryProcessor.encryptDirectory();
            directoryProcessor.decryptDirectory();

            ASyncDirectoryProcessor directoryProcessor1 = new ASyncDirectoryProcessor(pathToFile, keyFile, new ShiftUpEncryption());

            directoryProcessor1.addObserver(new EncryptionLogger(directoryProcessor1));

            directoryProcessor1.encryptDirectory();
            directoryProcessor1.decryptDirectory();
        } catch (Exception e){
            e.printStackTrace();
        }

//        ArrayList<FileEncryptor> fileEncryptors = new ArrayList<>();
//
//        fileEncryptors.add(new FileEncryptor(new ShiftUpEncryption(), pathToFile));
//        fileEncryptors.add(new FileEncryptor(new ShiftMultiplyEncryption(), pathToFile));
//        fileEncryptors.add(new FileEncryptor(new RepeatEncryption(new ShiftUpEncryption(), 2), pathToFile));
//        fileEncryptors.add(new FileEncryptor(new XorEncryption(), pathToFile));
//        fileEncryptors.add(new FileEncryptor(new DoubleEncryption(new ShiftUpEncryption(), new ShiftMultiplyEncryption()), pathToFile));
//
//        for (FileEncryptor fileEncryptor : fileEncryptors) {
//            fileEncryptor.encryptFile(false);
//            fileEncryptor.decryptFile();
//        }

    }
}
