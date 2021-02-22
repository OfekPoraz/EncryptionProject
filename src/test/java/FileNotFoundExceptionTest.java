import EncryptionAlgorithms.ShiftUpEncryption;
import FileEncryptor.FileEncryptor;
import Utils.FileOperations;
import org.junit.Test;

import java.io.FileNotFoundException;


public class FileNotFoundExceptionTest {

    static final private String originalPath = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\resources\\Hi.txt";

    @Test(expected = FileNotFoundException.class)
    public void Test() throws FileNotFoundException {
            FileOperations fileOperations = new FileOperations(originalPath);
            String string = fileOperations.readFromFile();
    }
}
