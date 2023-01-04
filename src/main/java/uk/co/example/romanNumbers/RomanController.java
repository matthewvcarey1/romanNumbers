package uk.co.example.romanNumbers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Stack;

@RestController
public class RomanController {
    private final String ERROR_MESSAGE = "Parameter decimal is missing or out of range, decimal must be in range 1 - ";
    private final String PARSE_ERROR_MESSAGE = "Parameter decimal could not be parsed as an Integer";


    @Value("${romanNumbersFile}")
    String romanNumbersFilename;

    @Value("${romanNumbersPath}")
    String romanNumbersPath;
    @GetMapping("/roman")
    public RomanResult romanParam(@RequestParam(value = "decimal", defaultValue = "0") String decimal) {
        try {
            String romanNumbersParameter = romanNumbersPath+romanNumbersFilename;
            IntToRomanConverter converter = IntToRomanConverter.getInstance(romanNumbersParameter);
            final long limit = converter.getTopLimit();
            final long value = Long.parseLong(decimal);
            if(converter.validate(value)){
                return new RomanResult("", converter.convert(value));
            } else {
                return new RomanResult(ERROR_MESSAGE + (limit-1),"");
            }
        } catch (NumberFormatException e){
            return new RomanResult(PARSE_ERROR_MESSAGE , "");
        }  catch (Exception e) {
            return new RomanResult(e.toString(),"");
        }
    }
    @GetMapping("/roman/{value}")
    public RomanResult romanPath(@PathVariable Long value) {
        try {
            String romanNumbersParameter = romanNumbersPath+romanNumbersFilename;
            IntToRomanConverter converter = IntToRomanConverter.getInstance(romanNumbersParameter);
            final long limit = converter.getTopLimit();
            if (converter.validate(value)) {
                return new RomanResult("", converter.convert(value));
            } else {
                return new RomanResult(ERROR_MESSAGE + (limit - 1), "");
            }
        } catch (Exception e){
            return new RomanResult(e.toString(),"");
        }
    }
    @GetMapping("/romanLimits/")
    public RomanLimits romanLimits(){
        try{
            String romanNumbersParameter = romanNumbersPath+romanNumbersFilename;
            IntToRomanConverter converter = IntToRomanConverter.getInstance(romanNumbersParameter);
            final long upperLimit = converter.getTopLimit() -1;
            final long lowerLimit = 1L;
            return new RomanLimits(lowerLimit,upperLimit,"");
        }catch (Exception e){
            return new RomanLimits(1,1, e.getMessage());
        }
    }
}
