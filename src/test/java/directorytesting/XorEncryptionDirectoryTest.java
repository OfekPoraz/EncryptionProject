package directorytesting;

import directoryencryptor.SyncDirectoryProcessor;
import encryptionalgorithms.XorEncryption;
import eventslogger.EncryptionLogger;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class XorEncryptionDirectoryTest extends abstractDirectoryTestClass{
    private static SyncDirectoryProcessor directoryProcessor;

    @BeforeClass
    public static void before(){
        try {
            directoryProcessor = new SyncDirectoryProcessor(getOriginalPath(), new XorEncryption());
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
        SyncDirectoryProcessor syncDirectoryProcessor = new SyncDirectoryProcessor(failPath,
                new XorEncryption());
    }

}
