package generaltesting;

import encryptionalgorithms.ShiftMultiplyEncryption;
import encryptionalgorithms.ShiftUpEncryption;
import eventslogger.EncryptionLogger;
import fileencryptor.FileEncryptor;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;

import static org.junit.Assert.*;

public class FileEncryptorTest {

    private static final String pathToFile = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\" +
            "resources\\Hi.txt";
    private static FileEncryptor fileEncryptorOne;
    private static FileEncryptor fileEncryptorTwo;
    private static final EncryptionLogger logger = new EncryptionLogger();


    @BeforeClass
    public static void before() {
        try {
            fileEncryptorOne = new FileEncryptor(new ShiftUpEncryption(), pathToFile);
            fileEncryptorTwo = new FileEncryptor(new ShiftMultiplyEncryption(), pathToFile);
        } catch (IOException e) {
            logger.writeErrorLog(e.getMessage());
        }
    }

    @Test
    public void test(){
        int compareResult = fileEncryptorTwo.compareTo(fileEncryptorOne);
        boolean result = (compareResult == 1 || compareResult == 0 || compareResult == -1);
        assertTrue(result);
    }

    @Test
    public void equalsTest() {
        try {
            fileEncryptorOne = new FileEncryptor(new ShiftUpEncryption(), pathToFile);
            fileEncryptorTwo = new FileEncryptor(new ShiftUpEncryption(), pathToFile);
            boolean checkOne = fileEncryptorOne.equals(fileEncryptorTwo);
            boolean checkTwo = fileEncryptorTwo.equals(fileEncryptorOne);
            assertEquals(checkOne, checkTwo);
        } catch (IOException e) {
            logger.writeErrorLog(e.getMessage());
            fail();
        }
    }

}
