package RegularTesting;

import EncryptionAlgorithms.DoubleEncryption;
import EncryptionAlgorithms.ShiftMultiplyEncryption;
import EncryptionAlgorithms.ShiftUpEncryption;
import EventsLogger.EncryptionLogger;
import FileEncryptor.FileEncryptor;
import Utils.FileOperations;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DoubleEncryptionTest extends abstractTestClass{

    private static FileEncryptor fileEncryptor;

    @BeforeClass
    public static void before(){
        try {
            fileEncryptor = new FileEncryptor(new DoubleEncryption(new ShiftUpEncryption(), new ShiftMultiplyEncryption()), getOriginalPath());
            fileEncryptor.addObserver(new EncryptionLogger(fileEncryptor));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void Test() {
        try {
            fileEncryptor.encryptFile(false);
            fileEncryptor.decryptFile();
            String decryptedFile = fileEncryptor.getPathToDecryptedFile();

            boolean equals = getFileOperations().compareFilesByString(getOriginalPath(), decryptedFile);
            assertTrue(equals);
        } catch (Exception e){
            fail();
        }
    }

    @Test(expected = FileNotFoundException.class)
    public void FileNotFoundTest() throws FileNotFoundException {
        String OGPath = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\resources\\Hi.txt";
        FileOperations fileOperations = new FileOperations(OGPath);
        String string = fileOperations.readFromFile();

    }

    @Test(expected = IOException.class)
    public void IOExceptionTest() throws IOException {
        String OGPath = "C:\\ertyui";
        FileEncryptor fileEncryptor = new FileEncryptor(new DoubleEncryption(new ShiftUpEncryption(), new ShiftMultiplyEncryption()), OGPath);

    }

    @Test(expected = NullPointerException.class)
    public void NullPointerExceptionTest() throws IOException {
        FileEncryptor fileEncryptor = new FileEncryptor(new DoubleEncryption(new ShiftUpEncryption(), new ShiftMultiplyEncryption()), null);
    }


}
