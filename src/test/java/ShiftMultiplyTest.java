import EncryptionAlgorithms.ShiftMultiplyEncryption;
import EventsLogger.EncryptionLogger;
import FileEncryptor.FileEncryptor;
import Utils.FileOperations;


import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShiftMultiplyTest {

    static final private String originalPath = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\resources\\Hi.txt";
    private final FileOperations fileOperations = new FileOperations(originalPath);
    private FileEncryptor fileEncryptor;


    @Before
    public void before(){
        try {
            this.fileEncryptor = new FileEncryptor(new ShiftMultiplyEncryption(), originalPath);
            fileEncryptor.addObserver(new EncryptionLogger(this.fileEncryptor));
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
        FileEncryptor fileEncryptor = new FileEncryptor(new ShiftMultiplyEncryption(), OGPath);
    }

    @Test(expected = NullPointerException.class)
    public void NullPointerExceptionTest() throws IOException {
        FileEncryptor fileEncryptor = new FileEncryptor(new ShiftMultiplyEncryption(), null);

    }

}
