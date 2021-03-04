package JsonJAXB;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonWriter {

    public void writeToJson() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Parameters parameters = new Parameters();
        String toJson = gson.toJson(parameters);




    }


}
