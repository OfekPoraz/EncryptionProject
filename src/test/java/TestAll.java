import JsonJAXB.JsonReader;
import Utils.FileOperations;
import org.json.simple.parser.ParseException;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.io.IOException;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        ShiftMultiplyTest.class,
        ShiftUpTest.class,
        DoubleEncryptionTest.class,
        RepeatEncryptionTest.class,
        XorEncryptionTest.class,

})
public class TestAll {

    static JsonReader jsonReader = new JsonReader("C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\resources\\JsonParameters.json");
    static String pathToFile;

    static {
        try {
            jsonReader.readJson();
            pathToFile = jsonReader.getPathToFile();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    //    final static String pathToFile = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\resources\\Hi.txt";
    private static final FileOperations fileOperations = new FileOperations(pathToFile);

    public static String getPathToFile() {
        return pathToFile;
    }

    public static FileOperations getFileOperations() {
        return fileOperations;
    }
}
