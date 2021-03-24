package generaltesting;

import jsonjaxb.InputParamJaxb;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InputXmlTest {

    private static final InputParamJaxb inputParamJaxb = new InputParamJaxb(
            "file", "dir", "up");
    private static final String pathFile = inputParamJaxb.getPathToFile();
    private static final String pathDir = inputParamJaxb.getPathToDir();
    private static final String name = inputParamJaxb.getNameOfEncryption();

    @Test
    public void fileTest(){
        assertEquals("file", pathFile);
    }
    @Test
    public void dirTest(){
        assertEquals("dir", pathDir);
    }
    @Test
    public void nameTest(){
        assertEquals("up", name);
    }
}
