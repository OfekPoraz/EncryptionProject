package JsonJaxbTesting;

import EncryptionAlgorithms.ShiftUpEncryption;
import EventsLogger.EncryptionLogger;
import FileEncryptor.FileEncryptor;
import JsonJAXB.JsonReaderWriter;
import JsonJAXB.Parameters;
import Utils.FileOperations;
import org.json.simple.parser.ParseException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class JsonTest {

    private static String testResult = "False";
    private static String IOTestResult = "False";
    private static String NullTestResult = "False";
    private static String FileTestResult = "False";
    private static FileEncryptor fileEncryptor;
    static JsonReaderWriter jsonReaderWriter = new JsonReaderWriter("C:\\Users\\ofeko\\IdeaProjects\\" +
            "EncryptionProject\\src\\main\\resources\\JsonJaxbFiles\\JsonParameters.json");
    static String pathToFile;

    static {
        try {
            jsonReaderWriter.reader();
            pathToFile = jsonReaderWriter.getPathToFile();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    private static final FileOperations fileOperations = new FileOperations(pathToFile);

    public static JsonReaderWriter getJsonReaderWriter() {
        return jsonReaderWriter;
    }

    @BeforeClass
    public static void before(){
        try {
            fileEncryptor = new FileEncryptor(new ShiftUpEncryption(), pathToFile);
            fileEncryptor.addObserver(new EncryptionLogger(fileEncryptor));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void Test(){
        try {
            fileEncryptor.encryptFile(false);
            fileEncryptor.decryptFile();
            String decryptedFile = fileEncryptor.getPathToDecryptedFile();

            boolean equals = fileOperations.compareFilesByString(pathToFile, decryptedFile);
            assertTrue(equals);
            testResult = "True";
        } catch (Exception e){
            fail();
        }
    }

    @Test(expected = FileNotFoundException.class)
    public void FileNotFoundTest() throws FileNotFoundException {
        try{
            String OGPath = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\resources\\Hi.txt";
            FileOperations fileOperations = new FileOperations(OGPath);
            String string = fileOperations.readFromFile();
        } catch (Exception e){
            FileTestResult = "True";
            throw new FileNotFoundException();
        }

    }

    @Test(expected = IOException.class)
    public void IOExceptionTest() throws IOException {
        try{
            String OGPath = "C:\\ertyui";
            FileEncryptor fileEncryptor = new FileEncryptor(new ShiftUpEncryption(), OGPath);
        } catch (IOException e){
            IOTestResult = "True";
            throw new IOException();
        }


    }

    @Test(expected = NullPointerException.class)
    public void NullPointerExceptionTest() throws IOException {
        try {
            FileEncryptor fileEncryptor = new FileEncryptor(new ShiftUpEncryption(), null);
        } catch (NullPointerException e){
            NullTestResult = "True";
            throw new NullPointerException();
        }


    }

    @AfterClass
    public static void afterClass() throws IOException {
        Parameters parameters = new Parameters(pathToFile,
                fileEncryptor.getNameOfAlgorithm(),
                testResult,
                IOTestResult,
                FileTestResult,
                NullTestResult);
        jsonReaderWriter.writer(parameters);
    }


}
