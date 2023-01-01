package uk.co.example.romanNumbers;

public class RomanResult {
    private final String error;
    private final String value;

    public RomanResult(String  error, String value) {
        this.error = error;
        this.value = value;
    }

    public String getError() {
        return error;
    }

    public String getValue() {
        return value;
    }
}
