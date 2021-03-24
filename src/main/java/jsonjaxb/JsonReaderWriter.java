package jsonjaxb;

import utils.FileOperations;
import com.google.gson.Gson;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.nio.charset.StandardCharsets;


public class JsonReaderWriter extends ReaderWriter{

    public static final String JSON_RESULT = "jsonResult";
    public static final String PATH_TO_FILE = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\" +
            "resources\\JsonJaxbFiles";
    public static final String SCHEMA_JSON = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\" +
            "resources\\JsonJaxbFiles\\JsonSchema.Json";
    public static final String JSON_RESULT_PATH = PATH_TO_FILE + File.separator + JSON_RESULT + ".json";
    private String jsonPathForTest = JSON_RESULT_PATH;
    private String jsonSchemaPathForTest = SCHEMA_JSON;

    public JsonReaderWriter(String fileName) {
        super(fileName);
    }

    @Override
    public void reader() throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader(getFileName()));

        // typecasting obj to JSONObject
        org.json.simple.JSONObject jo = (org.json.simple.JSONObject) obj;

        setPathToFile((String) jo.get("pathToFile"));
        setPathToDir((String) jo.get("pathToDir"));
        setNameOfEncryption((String) jo.get("nameOfEncryption"));
    }

    @Override
    public void writer(Parameters parameters) throws IOException {
        Gson gson = new Gson();
        FileOperations fileOperations = new FileOperations(PATH_TO_FILE, JSON_RESULT, false);
        String json = gson.toJson(parameters);
        fileOperations.writeToFile(json);
    }

    @Override
    public Boolean validator(Boolean setFile, Boolean setValidation) {
        try {
            String schemaFile = Boolean.TRUE.equals(setValidation) ? jsonSchemaPathForTest : SCHEMA_JSON;
            String inputFile = Boolean.TRUE.equals(setFile) ? jsonPathForTest : JSON_RESULT_PATH;
            InputStream is = new FileInputStream(schemaFile);
            String jsonTxt = org.apache.commons.io.IOUtils.toString(is, StandardCharsets.UTF_8);
            JSONObject json = new JSONObject(jsonTxt);

            Schema schema = SchemaLoader.load(json);

            InputStream inputStream = new FileInputStream(inputFile);
            String jsonTxtNew = org.apache.commons.io.IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            JSONObject jsonNew = new JSONObject(jsonTxtNew);

            schema.validate(jsonNew);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public void setJsonPathForTest(String jsonPathForTest) {
        this.jsonPathForTest = jsonPathForTest;
    }

    public void setJsonSchemaPathForTest(String jsonSchemaPathForTest) {
        this.jsonSchemaPathForTest = jsonSchemaPathForTest;
    }
}
