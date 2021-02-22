import EncryptionAlgorithms.ShiftUpEncryption;
import FileEncryptor.FileEncryptor;
import org.junit.Test;
import java.io.IOException;

import static org.junit.Assert.fail;

public class NullPointerExceptionTest {

    @Test(expected = NullPointerException.class)
    public void Test(){
        try {
            FileEncryptor fileEncryptor = new FileEncryptor(new ShiftUpEncryption(), null);
        } catch (IOException e){
            fail();
        }
    }

}
