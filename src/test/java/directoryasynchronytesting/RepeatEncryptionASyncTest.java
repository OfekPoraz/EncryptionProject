package directoryasynchronytesting;

import directoryencryptor.ASyncDirectoryProcessor;
import encryptionalgorithms.RepeatEncryption;
import encryptionalgorithms.ShiftUpEncryption;
import eventslogger.EncryptionLogger;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RepeatEncryptionASyncTest extends AbstractASyncDirectoryTesting{

    private static ASyncDirectoryProcessor directoryProcessor;

    @BeforeClass
    public static void before(){
        try {
            directoryProcessor = new ASyncDirectoryProcessor(getOriginalPath(), new RepeatEncryption(
                    new ShiftUpEncryption(), 3
            ));
            directoryProcessor.addObserver(new EncryptionLogger(directoryProcessor));
        } catch (IOException e){
            getLogger().writeErrorLog(e.getMessage());
        }
    }

    @Test
    public void Test(){
        try {
            directoryProcessor.encryptDirectory();
            directoryProcessor.decryptDirectory();
            String[] filesAfterActions = directoryProcessor.getFileNames();

            for (String file : filesAfterActions){
                Boolean isTheSame = directoryProcessor.getDirectoryOperations().compareFilesByString(
                        directoryProcessor.getDecryptedDirectoryOperations().getParentPathToFile()
                                + File.separator + file,
                        directoryProcessor.getDecryptedDirectoryOperations().getPathToFile() +
                                File.separator + file);
                if (Boolean.FALSE.equals(isTheSame)){
                    fail();
                }
            }
            assertTrue(true);
        } catch (Exception e){
            fail();
        }
    }

    @Test(expected = IOException.class)
    public void ioExceptionTest() throws IOException {
        String failPath = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\resources\\Hi.txt";
        ASyncDirectoryProcessor aSyncDirectoryProcessor = new ASyncDirectoryProcessor(failPath,
                new RepeatEncryption(new ShiftUpEncryption(), 3));
    }

}
