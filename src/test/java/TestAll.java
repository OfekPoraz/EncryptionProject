import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        ShiftUpTest.class,
        ShiftMultiplyTest.class,
        DoubleEncryptionTest.class,
        RepeatEncryptionTest.class,
        XorEncryptionTest.class
})
public class TestAll {

}
