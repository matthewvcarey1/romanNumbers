package uk.co.example.romanNumbers;

import java.util.ArrayList;

public class RomanPowerOfTen {
    private long unit;
    private ArrayList<String> romans;

    public RomanPowerOfTen(long unit, ArrayList<String> romanNames){
        this.unit = unit;
        this.romans = romanNames;
    }
    public long getUnit(){
        return unit;
    }
    public String getRoman(int stringIndex) {
        return stringIndex >= 0 && (stringIndex < romans.size()) ? romans.get(stringIndex) : "";
    }
}
