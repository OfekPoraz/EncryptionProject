package JsonJAXB;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Parameters {
    @XmlElement
    private String pathToFileOrDir;
    @XmlElement
    private String nameOfEncryption;
    @XmlElement
    private String testResult;
    @XmlElement
    private String IOExceptionTestResult;
    @XmlElement
    private String FileNotFoundTestResult;
    @XmlElement
    private String NullPointerExceptionTestResult;

    public Parameters() {
    }

    public Parameters(String pathToFileOrDir,
                      String nameOfEncryption,
                      String testResult,
                      String IOExceptionTestResult,
                      String fileNotFoundTestResult,
                      String nullPointerExceptionTestResult) {
        super();
        this.pathToFileOrDir = pathToFileOrDir;
        this.nameOfEncryption = nameOfEncryption;
        this.testResult = testResult;
        this.IOExceptionTestResult = IOExceptionTestResult;
        FileNotFoundTestResult = fileNotFoundTestResult;
        NullPointerExceptionTestResult = nullPointerExceptionTestResult;
    }

}
