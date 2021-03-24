package generaltesting;

import encryptionalgorithms.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EncryptionNameTest {

    @Test
    public void getNameOfShiftUpTest(){
        ShiftUpEncryption shiftUpEncryption = new ShiftUpEncryption();
        String encryptionName = shiftUpEncryption.getName();
        String expectedName = "ShiftUp";
        assertEquals(encryptionName, expectedName);
    }

    @Test
    public void getNameOfShiftMultiplyTest(){
        ShiftMultiplyEncryption shiftMultiplyEncryption = new ShiftMultiplyEncryption();
        String encryptionName = shiftMultiplyEncryption.getName();
        String expectedName = "ShiftMultiply";
        assertEquals(encryptionName, expectedName);
    }

    @Test
    public void getNameOfDoubleEncryptionTest(){
        DoubleEncryption doubleEncryption = new DoubleEncryption(new ShiftUpEncryption(),
                new ShiftMultiplyEncryption());
        String encryptionName = doubleEncryption.getName();
        String expectedName = "DoubleEncryption";
        assertEquals(encryptionName, expectedName);
    }

    @Test
    public void getNameOfRepeatEncryptionTest(){
        RepeatEncryption repeatEncryption = new RepeatEncryption(new ShiftUpEncryption(), 3);
        String encryptionName = repeatEncryption.getName();
        String expectedName = "RepeatEncryption";
        assertEquals(encryptionName, expectedName);
    }

    @Test
    public void getNameOfXorEncryptionTest(){
        XorEncryption xorEncryption = new XorEncryption();
        String encryptionName = xorEncryption.getName();
        String expectedName = "XorEncryption";
        assertEquals(encryptionName, expectedName);
    }
}
