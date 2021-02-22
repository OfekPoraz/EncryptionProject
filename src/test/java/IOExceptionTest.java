import EncryptionAlgorithms.ShiftUpEncryption;
import FileEncryptor.FileEncryptor;
import org.junit.Test;

import java.io.IOException;

public class IOExceptionTest {

    static final private String originalPath = "C:\\ertyui";

    @Test(expected = IOException.class)
    public void Test() throws IOException {
        FileEncryptor fileEncryptor = new FileEncryptor(new ShiftUpEncryption(), originalPath);
    }
}
