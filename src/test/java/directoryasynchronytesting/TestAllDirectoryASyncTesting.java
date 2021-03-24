package directoryasynchronytesting;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ShiftMultiplyASyncTest.class,
        ShiftUpASyncTest.class,
        DoubleEncryptionASyncDirectoryTest.class,
        RepeatEncryptionASyncTest.class,
        XorEncryptionASyncTest.class,
})
public class TestAllDirectoryASyncTesting {
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
