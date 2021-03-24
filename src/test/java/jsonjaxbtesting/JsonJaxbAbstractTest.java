package jsonjaxbtesting;

import encryptionalgorithms.*;
import eventslogger.EncryptionLogger;
import fileencryptor.FileEncryptor;
import jsonjaxb.JAXBReaderWriter;
import jsonjaxb.ReaderWriter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class JsonJaxbAbstractTest {

    private static String testResult = "False";
    private static String IOTestResult = "False";
    private static String NullTestResult = "False";
    private static String FileTestResult = "False";
    private static FileEncryptor fileEncryptor;
    private static String pathToFile;
    private static final EncryptionLogger logger = new EncryptionLogger();


    public static EncryptionAlgorithm getEncryptionAlgorithm(String algorithmName){
        switch (algorithmName){
            case "ShiftMultiply":
                return new ShiftMultiplyEncryption();
            case "DoubleEncryption":
                return new DoubleEncryption(new ShiftMultiplyEncryption(), new ShiftUpEncryption());
            case "XorEncryption":
                return new XorEncryption();
            case "RepeatEncryption":
                return new RepeatEncryption(new ShiftUpEncryption(), 3);
            case "ShiftUp":
            default:
                return new ShiftUpEncryption();
        }
    }

    public static EncryptionLogger getLogger() {
        return logger;
    }

    public static FileEncryptor getFileEncryptor() {
        return fileEncryptor;
    }

    public static void setFileEncryptor(FileEncryptor fileEncryptor) {
        JsonJaxbAbstractTest.fileEncryptor = fileEncryptor;
    }

    public static String getPathToFile() {
        return pathToFile;
    }

    public static void setPathToFile(String pathToFile) {
        JsonJaxbAbstractTest.pathToFile = pathToFile;
    }

    public static String getTestResult() {
        return testResult;
    }

    public static void setTestResult(String testResult) {
        JsonJaxbAbstractTest.testResult = testResult;
    }

    public static String getIOTestResult() {
        return IOTestResult;
    }

    public static void setIOTestResult(String IOTestResult) {
        JsonJaxbAbstractTest.IOTestResult = IOTestResult;
    }

    public static String getNullTestResult() {
        return NullTestResult;
    }

    public static void setNullTestResult(String nullTestResult) {
        NullTestResult = nullTestResult;
    }

    public static String getFileTestResult() {
        return FileTestResult;
    }

    public static void setFileTestResult(String fileTestResult) {
        FileTestResult = fileTestResult;
    }

    @Test
    public void getDirPathTest(){
        String INPUT_PARAM_JAXB_XML = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\" +
                "resources\\JsonJaxbFiles\\InputParamJaxb.xml";
        ReaderWriter readerWriter = new JAXBReaderWriter(INPUT_PARAM_JAXB_XML);
        String check = readerWriter.getPathToDir();
        assertEquals(null, check);
    }
}
