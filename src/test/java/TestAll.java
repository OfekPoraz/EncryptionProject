import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        ShiftMultiplyTest.class,
        ShiftUpTest.class,
        DoubleEncryptionTest.class,
        RepeatEncryptionTest.class,
        XorEncryptionTest.class,
        FileNotFoundExceptionTest.class,
        IOExceptionTest.class,
        NullPointerExceptionTest.class
})
public class TestAll {

}
