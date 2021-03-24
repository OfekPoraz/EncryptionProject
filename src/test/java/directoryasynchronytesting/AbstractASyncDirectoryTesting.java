package directoryasynchronytesting;

import directorytesting.TestAllDirectory;
import eventslogger.EncryptionLogger;

public class AbstractASyncDirectoryTesting {
    private static final String originalPath = TestAllDirectory.getPathToDir();
    private static final EncryptionLogger logger = new EncryptionLogger();


    public static String getOriginalPath() {
        return originalPath;
    }

    public static EncryptionLogger getLogger() {
        return logger;
    }
}
