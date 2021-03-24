package regulartesting;

import eventslogger.EncryptionLogger;
import utils.FileOperations;

public abstract class abstractTestClass {

    private static final String originalPath = TestAll.getPathToFile();
    private static final FileOperations fileOperations = TestAll.getFileOperations();
    private static final EncryptionLogger logger = new EncryptionLogger();


    public static String getOriginalPath() {
        return originalPath;
    }

    public static FileOperations getFileOperations() {
        return fileOperations;
    }

    public static EncryptionLogger getLogger() {
        return logger;
    }
}
