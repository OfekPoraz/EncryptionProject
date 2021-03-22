package JsonJAXB;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "InputParamJaxb", namespace = "")
public class InputParamJaxb {

    private String nameOfEncryption;
    private String pathToFile;
    private String pathToDir;

    public InputParamJaxb() {
    }

    public InputParamJaxb(String pathToFile, String pathToDir, String nameOfEncryption) {
        super();
        this.pathToFile = pathToFile;
        this.pathToDir = pathToDir;
        this.nameOfEncryption = nameOfEncryption;
    }

    @XmlAttribute
    public String getNameOfEncryption() {
        return nameOfEncryption;
    }

    public void setNameOfEncryption(String nameOfEncryption) {
        this.nameOfEncryption = nameOfEncryption;
    }

    @XmlElement
    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    @XmlElement
    public String getPathToDir() {
        return pathToDir;
    }

    public void setPathToDir(String pathToDir) {
        this.pathToDir = pathToDir;
    }

}
