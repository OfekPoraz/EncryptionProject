import FileEncryptor.FileEncryptor;
import Utils.FileOperations;

public abstract class abstractTestClass {

    private static final String originalPath = TestAll.getPathToFile();;
    private static final FileOperations fileOperations = TestAll.getFileOperations();

//    public abstractTestClass() {
//        originalPath = TestAll.getPathToFile();
//        fileOperations = TestAll.getFileOperations();
//    }

    public static String getOriginalPath() {
        return originalPath;
    }

    public static FileOperations getFileOperations() {
        return fileOperations;
    }
}
