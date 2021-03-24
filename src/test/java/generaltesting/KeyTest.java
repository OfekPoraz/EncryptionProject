package generaltesting;

import keys.Key;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KeyTest {

    @Test
    public void gcdTest(){
        Key key = new Key();
        int check = key.gcd(0, 4);
        assertEquals(0, check);
    }

}
