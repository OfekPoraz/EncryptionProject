package jsonjaxbtesting;

import jsonjaxb.JsonReaderWriter;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JsonValidateTest {
    public static final String LESS_JSON = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\resources\\" +
            "JsonJaxbFiles\\jsonResultLess.json";
    public static final String MORE_JSON = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\resources\\" +
            "JsonJaxbFiles\\jsonResultMore.json";
    public static final String INPUT_JSON_SCHEMA = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\" +
            "resources\\JsonJaxbFiles\\JsonSchemaInput.Json";
    public static final String INPUT_JSON = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\" +
            "resources\\JsonJaxbFiles\\JsonParameters.json";
    public static final String INPUT_JSON_LESS = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\" +
            "resources\\JsonJaxbFiles\\JsonParametersLess.json";
    public static final String INPUT_JSON_MORE = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\" +
            "resources\\JsonJaxbFiles\\JsonParametersMore.json";
    private static JsonReaderWriter jsonReaderWriter;

    @BeforeClass
    public static void beforeClass(){
        try {
            jsonReaderWriter = JsonTest.getJsonReaderWriter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validationResultTest() {
        try {
            assertTrue(jsonReaderWriter.validator(false, false));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void validationResultWithLessLinesInSchema(){
        jsonReaderWriter.setJsonPathForTest(LESS_JSON);
        try {
            assertFalse(jsonReaderWriter.validator(true, false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validationResultWithMoreLinesInSchema(){
        jsonReaderWriter.setJsonPathForTest(MORE_JSON);
        try {
            assertFalse(jsonReaderWriter.validator(true, false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validationInputTest() {
        jsonReaderWriter.setJsonPathForTest(INPUT_JSON);
        jsonReaderWriter.setJsonSchemaPathForTest(INPUT_JSON_SCHEMA);
        try {
            assertTrue(jsonReaderWriter.validator(true, true));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void validationInputWithLessLinesInXml(){
        jsonReaderWriter.setJsonPathForTest(INPUT_JSON_LESS);
        jsonReaderWriter.setJsonSchemaPathForTest(INPUT_JSON_SCHEMA);
        try {
            assertFalse(jsonReaderWriter.validator(true, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validationInputWithMoreLinesInXml(){
        jsonReaderWriter.setJsonPathForTest(INPUT_JSON_MORE);
        jsonReaderWriter.setJsonSchemaPathForTest(INPUT_JSON_SCHEMA);
        try {
            assertFalse(jsonReaderWriter.validator(true, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
