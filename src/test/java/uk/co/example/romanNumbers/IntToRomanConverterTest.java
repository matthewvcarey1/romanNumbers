package uk.co.example.romanNumbers;

import org.junit.jupiter.api.Test;
import uk.co.example.romanNumbers.IntToRomanConverter;

import static org.junit.jupiter.api.Assertions.*;

class IntToRomanConverterTest {

    @Test
    void test3000() {
        IntToRomanConverter testConvertor = IntToRomanConverter.getInstance();
        String result = testConvertor.convert(3000);
        assertEquals("MMM",result);
    }
    @Test
    void test2900() {
        IntToRomanConverter testConvertor = IntToRomanConverter.getInstance();
        String result = testConvertor.convert(2900);
        assertEquals("MMCM",result);
    }
    @Test
    void test2905() {
        IntToRomanConverter testConvertor = IntToRomanConverter.getInstance();
        String result = testConvertor.convert(2905);
        assertEquals("MMCMV",result);
    }
    @Test
    void test2904() {
        IntToRomanConverter testConvertor = IntToRomanConverter.getInstance();
        String result = testConvertor.convert(2904);
        assertEquals("MMCMIV",result);
    }
    @Test
    void test69000000() {
        IntToRomanConverter testConvertor = IntToRomanConverter.getInstance();
        String result = testConvertor.convert(69000000);
        assertEquals("L̿X̿I̿X̿",result);
    }


    @Test
    void validateHappy() {
        IntToRomanConverter testConvertor = IntToRomanConverter.getInstance();
        assertTrue(testConvertor.validate(1));
    }

    @Test
    void validateZero(){
        IntToRomanConverter testConvertor = IntToRomanConverter.getInstance();
        assertFalse(testConvertor.validate(0));
    }
    @Test
    void validateMinus1(){
        IntToRomanConverter testConvertor = IntToRomanConverter.getInstance();
        assertFalse(testConvertor.validate(-1));
    }

    @Test
    void validateTooBig(){
        IntToRomanConverter testConvertor = IntToRomanConverter.getInstance();
        Long limit = testConvertor.getTopLimit();
        assertFalse(testConvertor.validate(limit+1L));
    }
}