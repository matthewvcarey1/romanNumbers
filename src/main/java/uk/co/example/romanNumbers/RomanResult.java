package uk.co.example.romanNumbers;

public class RomanResult {
    private final String error;
    private final String value;

    private final String source;

    public RomanResult(String  error, String value, String source) {
        this.error = error;
        this.value = value;
        this.source = source;
    }

    public String getError() {
        return error;
    }

    public String getValue() {
        return value;
    }

    public String getSource(){
        return source;
    }
}
