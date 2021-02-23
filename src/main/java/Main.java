import EncryptionAlgorithms.*;
import Exceptions.InvalidKeyException;
import FileEncryptor.FileEncryptor;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, InvalidKeyException {
        String pathToFile = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\resources\\Hi.txt";
        ArrayList<FileEncryptor> fileEncryptors = new ArrayList<>();

        fileEncryptors.add(new FileEncryptor(new ShiftUpEncryption(), pathToFile));
        fileEncryptors.add(new FileEncryptor(new ShiftMultiplyEncryption(), pathToFile));
        fileEncryptors.add(new FileEncryptor(new RepeatEncryption(new ShiftUpEncryption(), 2), pathToFile));
        fileEncryptors.add(new FileEncryptor(new XorEncryption(), pathToFile));
        fileEncryptors.add(new FileEncryptor(new DoubleEncryption(new ShiftUpEncryption(), new ShiftMultiplyEncryption()), pathToFile));

        for (FileEncryptor fileEncryptor : fileEncryptors) {
            fileEncryptor.encryptFile();
            fileEncryptor.decryptFile();
        }

    }
}
