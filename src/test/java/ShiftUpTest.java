import EncryptionAlgorithms.ShiftUpEncryption;
import FileEncryptor.FileEncryptor;
import Utils.FileOperations;


import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.*;

public class ShiftUpTest {

    static final private String originalPath = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\resources\\Hi.txt";
    private final FileOperations fileOperations = new FileOperations(originalPath);
    private FileEncryptor fileEncryptor;

    public ShiftUpTest() throws IOException {
        this.fileEncryptor = new FileEncryptor(new ShiftUpEncryption(), originalPath);
    }

    @Test
    public void Test(){
        try {
            fileEncryptor.encryptFile();
            fileEncryptor.decryptFile();
            String decryptedFile = fileEncryptor.getPathToDecryptedFile();

            boolean equals = fileOperations.compareFiles(originalPath, decryptedFile);
            assertTrue(equals);

        } catch (Exception e){
            fail();
        }

    }
}
