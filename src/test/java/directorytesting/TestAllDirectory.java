package directorytesting;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ShiftMultiplyDirectoryTest.class,
        ShiftUpDirectoryTest.class,
        DoubleEncryptionDirectoryTest.class,
        RepeatEncryptionDirectoryTest.class,
        XorEncryptionDirectoryTest.class,
})
public class TestAllDirectory {
    private final static String PATH_TO_FILE = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\" +
            "resources\\Hi.txt";
    private final static String PATH_TO_DIR = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\" +
            "resources";

    public static String getPathToDir() {
        return PATH_TO_DIR;
    }

    public static String getPathToFile() {
        return PATH_TO_FILE;
    }
}
