package directorytesting;

import eventslogger.EncryptionLogger;

public class abstractDirectoryTestClass {

    private static final String originalPath = TestAllDirectory.getPathToDir();
    private static final EncryptionLogger logger = new EncryptionLogger();


    public static String getOriginalPath() {
        return originalPath;
    }

    public static EncryptionLogger getLogger() {
        return logger;
    }

}
