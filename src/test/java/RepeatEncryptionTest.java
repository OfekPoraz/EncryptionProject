import EncryptionAlgorithms.RepeatEncryption;
import EncryptionAlgorithms.ShiftMultiplyEncryption;
import EventsLogger.EncryptionLogger;
import FileEncryptor.FileEncryptor;
import Utils.FileOperations;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class RepeatEncryptionTest {

    static private String originalPath;
    private static FileOperations fileOperations;
    private static FileEncryptor fileEncryptor;

    @BeforeClass
    public static void before(){
        try {
            originalPath = TestAll.getPathToFile();
            fileOperations = TestAll.getFileOperations();
            fileEncryptor = new FileEncryptor(new RepeatEncryption(new ShiftMultiplyEncryption(), 3), originalPath);
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

            boolean equals = fileOperations.compareFilesByString(originalPath, decryptedFile);
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
        FileEncryptor fileEncryptor = new FileEncryptor(new RepeatEncryption(new ShiftMultiplyEncryption(), 3), OGPath);
    }

    @Test(expected = NullPointerException.class)
    public void NullPointerExceptionTest() throws IOException {
        FileEncryptor fileEncryptor = new FileEncryptor(new RepeatEncryption(new ShiftMultiplyEncryption(), 3), null);
    }


}
