package uk.co.example.romanNumbers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class IntToRomanConverterTest {


    String romanNumbersParameter;


    @BeforeEach
    void setUp() {
        try{
            Properties props = PropertiesLoader.loadProperties("application.properties");
            final String romanNumbersFilename = props.getProperty("romanNumbersFile");
            final String romanNumbersPath = props.getProperty("romanNumbersPath");
            romanNumbersParameter = romanNumbersPath+romanNumbersFilename;
        } catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    void test3000() {
        try {
            IntToRomanConverter testConvertor = IntToRomanConverter.getInstance(romanNumbersParameter);
            String result = testConvertor.convert(3000);
            assertEquals("MMM", result);
        } catch (Exception e){
            fail("Should not caused exception");
        }
    }
    @Test
    void test2900() {
        try {
            IntToRomanConverter testConvertor = IntToRomanConverter.getInstance(romanNumbersParameter);
            String result = testConvertor.convert(2900);
            assertEquals("MMCM",result);
        } catch (Exception e){
            fail("Should not caused exception");
        }
    }
    @Test
    void test2905() {
        try {
            IntToRomanConverter testConvertor = IntToRomanConverter.getInstance(romanNumbersParameter);
            String result = testConvertor.convert(2905);
            assertEquals("MMCMV", result);
        } catch (Exception e){
            fail("Should not caused exception");
        }
    }
    @Test
    void test2904() {
        try{
            IntToRomanConverter testConvertor = IntToRomanConverter.getInstance(romanNumbersParameter);
            String result = testConvertor.convert(2904);
            assertEquals("MMCMIV",result);
        } catch (Exception e){
            fail("Should not caused exception");
        }
    }

    @Test
    void testLimit() {
        try{
            IntToRomanConverter testConvertor = IntToRomanConverter.getInstance(romanNumbersParameter);
            Long limit = testConvertor.getTopLimit();
            String result = testConvertor.convert(limit-1);
            assertEquals("X̿̅C̿̅I̿̅X̿̅C̿M̿X̿C̿I̿X̿C̅M̅̅X̅C̅I̅X̅CMXCIX",result);
        } catch (Exception e){
            fail("Should not caused exception");
        }
    }

    @Test
    void testLimit2() {
        try{
            IntToRomanConverter testConvertor = IntToRomanConverter.getInstance(romanNumbersParameter);
            Long limit = testConvertor.getTopLimit();
            String result = testConvertor.convert(limit);
            assertEquals("",result);
        } catch (Exception e){
            fail("Should not caused exception");
        }
    }

    @Test
    void validateHappy() {
        try{
            IntToRomanConverter testConvertor = IntToRomanConverter.getInstance(romanNumbersParameter);
            assertTrue(testConvertor.validate(1));
        } catch (Exception e){
            fail("Should not caused exception");
        }
    }

    @Test
    void validateZero(){
        try{
            IntToRomanConverter testConvertor = IntToRomanConverter.getInstance(romanNumbersParameter);
            assertFalse(testConvertor.validate(0));
        } catch (Exception e){
            fail("Should not caused exception");
        }
    }
    @Test
    void validateMinus1(){
        try{
            IntToRomanConverter testConvertor = IntToRomanConverter.getInstance(romanNumbersParameter);
            assertFalse(testConvertor.validate(-1));
        } catch (Exception e){
            fail("Should not caused exception");
        }
    }

    @Test
    void validateTooBig(){
        try{
            IntToRomanConverter testConvertor = IntToRomanConverter.getInstance(romanNumbersParameter);
            Long limit = testConvertor.getTopLimit();
            assertFalse(testConvertor.validate(limit+1L));
        } catch (Exception e){
            fail("Should not caused exception");
        }
    }

}