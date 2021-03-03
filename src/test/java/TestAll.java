import Utils.FileOperations;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        ShiftMultiplyTest.class,
        ShiftUpTest.class,
        DoubleEncryptionTest.class,
        RepeatEncryptionTest.class,
        XorEncryptionTest.class,

})
public class TestAll {
    final static String pathToFile = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\resources\\Hi.txt";
    private static final FileOperations fileOperations = new FileOperations(pathToFile);

    public static String getPathToFile() {
        return pathToFile;
    }

    public static FileOperations getFileOperations() {
        return fileOperations;
    }
}
