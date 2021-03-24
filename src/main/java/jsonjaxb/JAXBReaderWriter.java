package jsonjaxb;

import eventslogger.EncryptionLogger;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class JAXBReaderWriter extends ReaderWriter{


    private static final String RESOURCES = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\resources" +
            "\\JsonJaxbFiles\\";
    private static final String RESULT_XML = "XmlResult.xml";
    private static final String XSD_VALIDATION = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\" +
            "resources\\JsonJaxbFiles\\XmlResultXSD.xsd";
    private static final String XML_PATH = RESOURCES + File.separator + RESULT_XML;
    public static final String INPUT_PARAM_JAXB_XML = "InputParamJaxb.xml";
    private String xmlPathForTest = XML_PATH;
    private String xsdPathForTest = XSD_VALIDATION;
    private final EncryptionLogger logger = new EncryptionLogger();


    public JAXBReaderWriter(String fileName) {
        super(fileName);
    }

    @Override
    public void reader() throws IOException, ParseException {
        try {
            File file = new File(RESOURCES + File.separator + INPUT_PARAM_JAXB_XML);
            JAXBContext jaxbContext = JAXBContext.newInstance(InputParamJaxb.class);

            Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();
            InputParamJaxb inputParamJaxb =(InputParamJaxb) jaxbUnMarshaller.unmarshal(file);

            setPathToFile(inputParamJaxb.getPathToFile());
            setPathToDir(inputParamJaxb.getPathToDir());
            setNameOfEncryption(inputParamJaxb.getNameOfEncryption());

        } catch (JAXBException e) {
            logger.writeErrorLog(e.getMessage());
        }

    }

    @Override
    public void writer(Parameters parameters) throws IOException, JAXBException {
        JAXBContext contextObj = JAXBContext.newInstance(Parameters.class);

        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshallerObj.marshal(parameters, new FileOutputStream(XML_PATH));
    }

    @Override
    public Boolean validator(Boolean setFile, Boolean setValidation) {
        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); // Compliant
            factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, ""); // Compliant

            String xsdPath = Boolean.TRUE.equals(setValidation) ? xsdPathForTest : XSD_VALIDATION;
            String xmlPath = Boolean.TRUE.equals(setFile) ? xmlPathForTest : XML_PATH;
            StreamSource xsdSource = new StreamSource(xsdPath);
            Schema schema = factory.newSchema(xsdSource);

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException e) {
            logger.writeErrorLog("Exception: "+e.getMessage());
            return false;
        }catch(SAXException e1) {
            logger.writeErrorLog("SAX Exception: "+e1.getMessage());
            return false;
        }

        return true;
    }

    public void setXmlPathForTest(String xmlPathForTest) {
        this.xmlPathForTest = xmlPathForTest;
    }

    public void setXsdPathForTest(String xsdPathForTest) {
        this.xsdPathForTest = xsdPathForTest;
    }
}
