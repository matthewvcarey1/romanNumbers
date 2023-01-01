package uk.co.example.romanNumbers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Stack;

@RestController
public class RomanController {
    private final String ERROR_MESSAGE = "Parameter decimal is missing or out of range, decimal must be in range 1 - ";
    private final String PARSE_ERROR_MESSAGE = "Parameter decimal could not be parsed as an Integer in the range or 1 - ";
    @GetMapping("/roman")
    public RomanResult roman(@RequestParam(value = "decimal", defaultValue = "0") String decimal) {
        IntToRomanConverter converter = IntToRomanConverter.getInstance();
        final long limit = converter.getTopLimit();
        try {
            final long value = Long.parseLong(decimal);
            if(converter.validate(value)){
                return new RomanResult("", converter.convert(value));
            } else {
                return new RomanResult(ERROR_MESSAGE + (limit-1),"");
            }
        } catch (NumberFormatException e){
            return new RomanResult(PARSE_ERROR_MESSAGE + (limit-1), "");
        }
    }
}
