package uk.co.example.romanNumbers;

public class RomanLimits {
    private final long lowerLimit;
    private final long upperLimit;

    private final String error;
    public RomanLimits(long lower, long upper, String error){
        lowerLimit = lower;
        upperLimit = upper;
        this.error = error;
    }

    public long getUpperLimit(){
        return upperLimit;
    }
    public long getLowerLimit(){
        return lowerLimit;
    }
    public String getError(){
        return error;
    }
}
