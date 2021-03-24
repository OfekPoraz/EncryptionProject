package jsonjaxbtesting;

import jsonjaxb.JAXBReaderWriter;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class JAXBValidationTest {

    public static final String LESS_XML = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\resources\\" +
            "JsonJaxbFiles\\XmlResultLess.xml";
    public static final String MORE_XML = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\resources\\" +
            "JsonJaxbFiles\\XmlResultMore.xml";
    public static final String INPUT_XSD_PATH = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\" +
            "resources\\JsonJaxbFiles\\XmlInputXSD.xsd";
    public static final String INPUT_XML_PATH = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\" +
            "resources\\JsonJaxbFiles\\InputParamJaxb.xml";
    public static final String LESS_XML_INPUT = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\" +
            "resources\\JsonJaxbFiles\\InputXmlLess.xml";
    public static final String MORE_XML_INPUT = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\" +
            "resources\\JsonJaxbFiles\\InputXmlMore.xml";
    private static JAXBReaderWriter jaxbReaderWriter;

    @BeforeClass
    public static void beforeClass(){
        try {
            jaxbReaderWriter = JAXBTest.getJaxbReaderWriter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validationResultTest() {
        try {
            assertTrue(jaxbReaderWriter.validator(false, false));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void validationResultWithLessLinesInXml(){
        jaxbReaderWriter.setXmlPathForTest(LESS_XML);
        try {
            assertFalse(jaxbReaderWriter.validator(true, false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validationResultWithMoreLinesInXml(){
        jaxbReaderWriter.setXmlPathForTest(MORE_XML);
        try {
            assertFalse(jaxbReaderWriter.validator(true, false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validationInputTest() {
        jaxbReaderWriter.setXmlPathForTest(INPUT_XML_PATH);
        jaxbReaderWriter.setXsdPathForTest(INPUT_XSD_PATH);
        try {
            assertTrue(jaxbReaderWriter.validator(true, true));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void validationInputWithLessLinesInXml(){
        jaxbReaderWriter.setXmlPathForTest(LESS_XML_INPUT);
        jaxbReaderWriter.setXsdPathForTest(INPUT_XSD_PATH);
        try {
            assertFalse(jaxbReaderWriter.validator(true, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validationInputWithMoreLinesInXml(){
        jaxbReaderWriter.setXmlPathForTest(MORE_XML_INPUT);
        jaxbReaderWriter.setXsdPathForTest(INPUT_XSD_PATH);
        try {
            assertFalse(jaxbReaderWriter.validator(true, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
