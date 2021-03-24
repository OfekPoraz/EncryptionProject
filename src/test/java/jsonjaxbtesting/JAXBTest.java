package jsonjaxbtesting;

import eventslogger.EncryptionLogger;
import fileencryptor.FileEncryptor;
import jsonjaxb.JAXBReaderWriter;
import jsonjaxb.Parameters;
import utils.FileOperations;
import org.json.simple.parser.ParseException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class JAXBTest extends JsonJaxbAbstractTest {

    public static final String INPUT_PARAM_JAXB_XML = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\" +
            "resources\\JsonJaxbFiles\\InputParamJaxb.xml";
    static private final JAXBReaderWriter jaxbReaderWriter = new JAXBReaderWriter(INPUT_PARAM_JAXB_XML);

    static {
        try {
            jaxbReaderWriter.reader();
            setPathToFile(jaxbReaderWriter.getPathToFile());
        } catch (ParseException | IOException e) {
            getLogger().writeErrorLog(e.getMessage());
        }
    }

    private static final FileOperations fileOperations = new FileOperations(getPathToFile());

    public static JAXBReaderWriter getJaxbReaderWriter() {
        return jaxbReaderWriter;
    }



    @BeforeClass
    public static void before(){
        try {
            setFileEncryptor(new FileEncryptor(getEncryptionAlgorithm(jaxbReaderWriter.getNameOfEncryption()),
                    getPathToFile()));
            getFileEncryptor().addObserver(new EncryptionLogger(getFileEncryptor()));
        } catch (IOException e){
            getLogger().writeErrorLog(e.getMessage());
        }
    }

    @Test
    public void Test(){
        try {
            getFileEncryptor().encryptFile(false);
            getFileEncryptor().decryptFile();
            String decryptedFile = getFileEncryptor().getPathToDecryptedFile();

            boolean equals = fileOperations.compareFilesByString(getPathToFile(), decryptedFile);
            assertTrue(equals);
            setTestResult("True");
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
            setFileTestResult("True");
            throw new FileNotFoundException();
        }

    }

    @Test(expected = IOException.class)
    public void IOExceptionTest() throws IOException {
        try{
            String OGPath = "C:\\ertyui";
            FileEncryptor fileEncryptor = new FileEncryptor(
                    getEncryptionAlgorithm(jaxbReaderWriter.getNameOfEncryption()),
                    OGPath);
        } catch (IOException e){
            setIOTestResult("True");
            throw new IOException();
        }



    }

    @Test(expected = NullPointerException.class)
    public void NullPointerExceptionTest() throws IOException {
        try {
            FileEncryptor fileEncryptor = new FileEncryptor(
                    getEncryptionAlgorithm(jaxbReaderWriter.getNameOfEncryption()),
                    null);
        } catch (NullPointerException e){
            setNullTestResult("True");
            throw new NullPointerException();
        }
    }


    @AfterClass
    public static void afterClass() throws IOException, JAXBException {
        Parameters parameters = new Parameters(getPathToFile(),
                getFileEncryptor().getNameOfAlgorithm(),
                getTestResult(),
                getIOTestResult(),
                getFileTestResult(),
                getNullTestResult());
        jaxbReaderWriter.writer(parameters);
    }
}
