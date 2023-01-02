package uk.co.example.romanNumbers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RomanPowerOfTenTest {
    RomanPowerOfTen rpt;
    @BeforeEach
    void setUp() {
        String[] array = {"", "X","XX","XXX","XL","L","LX","LXX","LXXX","CX"};
        rpt = new RomanPowerOfTen(10, new ArrayList<String>(Arrays.asList(array)));
    }

    @Test
    void getUnit() {
        assertEquals(rpt.getUnit(),10L);
    }

    @Test
    void getRoman5() {
        assertEquals(rpt.getRoman(5),"L");
    }
    @Test
    void getRoman10() {
        assertEquals(rpt.getRoman(10),"");
    }
    @Test
    void getRomanminus1() {
        assertEquals(rpt.getRoman(-1),"");
    }
}