package uk.co.example.romanNumbers;

public class RomanLimits {
    private final String lowerLimit;
    private final String upperLimit;

    private final String error;
    public RomanLimits(long lower, long upper, String error){
        lowerLimit = Long.valueOf(lower).toString();
        upperLimit = Long.valueOf(upper).toString();
        this.error = error;
    }

    public String getUpperLimit(){
        return upperLimit;
    }
    public String getLowerLimit(){
        return lowerLimit;
    }
    public String getError(){
        return error;
    }
}
