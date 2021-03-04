package JsonJAXB;

public class Parameters {
    private static String pathToFileOrDir;
    private static String nameOfEncryption;
    private static String testResult;
    private static String IOExceptionTestResult;
    private static String FileNotFoundTestResult;
    private static String NullPointerExceptionTestResult;


    public Parameters() {
    }

    public static String getPathToFileOrDir() {
        return pathToFileOrDir;
    }

    public static void setPathToFileOrDir(String pathToFileOrDir) {
        Parameters.pathToFileOrDir = pathToFileOrDir;
    }

    public static String getNameOfEncryption() {
        return nameOfEncryption;
    }

    public static void setNameOfEncryption(String nameOfEncryption) {
        Parameters.nameOfEncryption = nameOfEncryption;
    }

    public static String getTestResult() {
        return testResult;
    }

    public static void setTestResult(String testResult) {
        Parameters.testResult = testResult;
    }

    public static String getIOExceptionTestResult() {
        return IOExceptionTestResult;
    }

    public static void setIOExceptionTestResult(String IOExceptionTestResult) {
        Parameters.IOExceptionTestResult = IOExceptionTestResult;
    }

    public static String getFileNotFoundTestResult() {
        return FileNotFoundTestResult;
    }

    public static void setFileNotFoundTestResult(String fileNotFoundTestResult) {
        FileNotFoundTestResult = fileNotFoundTestResult;
    }

    public static String getNullPointerExceptionTestResult() {
        return NullPointerExceptionTestResult;
    }

    public static void setNullPointerExceptionTestResult(String nullPointerExceptionTestResult) {
        NullPointerExceptionTestResult = nullPointerExceptionTestResult;
    }
}
