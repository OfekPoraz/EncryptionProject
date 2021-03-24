package regulartesting;

import encryptionalgorithms.ShiftUpEncryption;
import eventslogger.EncryptionLogger;
import fileencryptor.FileEncryptor;
import utils.FileOperations;


import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShiftUpTest extends abstractTestClass {

    private static FileEncryptor fileEncryptor;

    @BeforeClass
    public static void before(){
        try {
            fileEncryptor = new FileEncryptor(new ShiftUpEncryption(), getOriginalPath());
            fileEncryptor.addObserver(new EncryptionLogger(fileEncryptor));
        } catch (IOException e){
            getLogger().writeErrorLog(e.getMessage());
        }
    }

    @Test
    public void Test(){
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
        FileEncryptor fileEncryptor = new FileEncryptor(new ShiftUpEncryption(), OGPath);

    }

    @Test(expected = NullPointerException.class)
    public void NullPointerExceptionTest() throws IOException {
        FileEncryptor fileEncryptor = new FileEncryptor(new ShiftUpEncryption(), null);
    }
}
