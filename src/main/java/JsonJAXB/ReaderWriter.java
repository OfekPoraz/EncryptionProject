package JsonJAXB;

import org.json.simple.parser.ParseException;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public abstract class ReaderWriter {

    private String fileName;
    private String pathToFile;
    private String pathToDir;
    private String nameOfEncryption;


    public ReaderWriter(String fileName) {
        this.fileName = fileName;
    }


    public String getFileName() {
        return fileName;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public void setPathToDir(String pathToDir) {
        this.pathToDir = pathToDir;
    }

    public void setNameOfEncryption(String nameOfEncryption) {
        this.nameOfEncryption = nameOfEncryption;
    }

    public abstract void reader() throws IOException, ParseException;
    public abstract void writer(Parameters parameters) throws IOException, ParseException, JAXBException;
    public abstract Boolean validator(Boolean setFile, Boolean setValidation);



}
