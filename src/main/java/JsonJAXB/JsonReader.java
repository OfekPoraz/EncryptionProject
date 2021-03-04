package JsonJAXB;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonReader {
    private String fileName;
    private String pathToFile;
    private String pathToDir;
    private String nameOfEncryption;

    public JsonReader(String fileName) {
        this.fileName = fileName;
    }


    public void readJson() throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader(fileName));

        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        pathToFile = (String) jo.get("pathToFile");
        pathToDir = (String) jo.get("pathToDir");
        nameOfEncryption = (String) jo.get("nameOfEncryption");
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public String getPathToDir() {
        return pathToDir;
    }

    public String getNameOfEncryption() {
        return nameOfEncryption;
    }

//    public Parameters readJson() throws FileNotFoundException {
//        GsonBuilder builder = new GsonBuilder();
//        Gson gson = builder.create();
//        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
//
//        Parameters parameters = gson.fromJson(bufferedReader, Parameters.class);
//        return parameters;
//    }

//    Object obj = new JSONParser().parse(new FileReader("JSONExample.json"));
//
//    // typecasting obj to JSONObject
//    JSONObject jo = (JSONObject) obj;
}
