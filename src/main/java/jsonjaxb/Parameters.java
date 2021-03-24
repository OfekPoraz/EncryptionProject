package jsonjaxb;

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
    private String ioExceptionTestResult;
    @XmlElement
    private String fileNotFoundTestResult;
    @XmlElement
    private String nullPointerExceptionTestResult;

    public Parameters() {
    }

    public Parameters(String pathToFileOrDir,
                      String nameOfEncryption,
                      String testResult,
                      String ioExceptionTestResult,
                      String fileNotFoundTestResult,
                      String nullPointerExceptionTestResult) {
        super();
        this.pathToFileOrDir = pathToFileOrDir;
        this.nameOfEncryption = nameOfEncryption;
        this.testResult = testResult;
        this.ioExceptionTestResult = ioExceptionTestResult;
        this.fileNotFoundTestResult = fileNotFoundTestResult;
        this.nullPointerExceptionTestResult = nullPointerExceptionTestResult;
    }

}
