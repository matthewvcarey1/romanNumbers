package uk.co.example.romanNumbers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin(maxAge = 3600)
@RestController
public class RomanController {
    private final String ERROR_MESSAGE = "The input value is missing or out of range, decimal must be in range 1 - ";
    private final String PARSE_ERROR_MESSAGE = "The input value could not be parsed as a Long Integer";



    @Value("${romanNumbersFile}")
    String romanNumbersFilename;

    @Value("${romanNumbersPath}")
    String romanNumbersPath;
    @CrossOrigin("http://localhost")
    @GetMapping("/roman")
    public RomanResult romanParam(@RequestParam(value = "decimal", defaultValue = "0") String decimal) {
        try {
            String romanNumbersParameter = romanNumbersPath+romanNumbersFilename;
            IntToRomanConverter converter = IntToRomanConverter.getInstance(romanNumbersParameter);
            final long limit = converter.getTopLimit();
            final long value = Long.parseLong(decimal);
            if(converter.validate(value)){
                System.out.println(Long.toString(value));
                return new RomanResult("", converter.convert(value), Long.toString(value));
            } else {
                return new RomanResult(ERROR_MESSAGE + (limit-1),"", "");
            }
        } catch (NumberFormatException e){
            return new RomanResult(PARSE_ERROR_MESSAGE , "", "");
        }  catch (Exception e) {
            return new RomanResult(e.toString(),"", "");
        }
    }
    @CrossOrigin("http://localhost")
    @GetMapping("/roman/{valueString}")
    public RomanResult romanPath(@PathVariable String valueString) {
        try {
            long value=Long.parseLong(valueString);
            String romanNumbersParameter = romanNumbersPath+romanNumbersFilename;
            IntToRomanConverter converter = IntToRomanConverter.getInstance(romanNumbersParameter);
            final long limit = converter.getTopLimit();
            if (converter.validate(value)) {
                return new RomanResult("", converter.convert(value), Long.toString(value));
            } else {
                return new RomanResult(ERROR_MESSAGE + (limit - 1), "", "");
            }
        } catch (NumberFormatException e){
            return new RomanResult(PARSE_ERROR_MESSAGE , "", "");
        } catch (Exception e){
            return new RomanResult(e.toString(),"", "");
        }
    }
    @CrossOrigin("http://localhost")
    @GetMapping("/romanLimits/")
    public RomanLimits romanLimits(){
        try{
            String romanNumbersParameter = romanNumbersPath+romanNumbersFilename;
            IntToRomanConverter converter = IntToRomanConverter.getInstance(romanNumbersParameter);
            final long upperLimit = converter.getTopLimit();
            final long lowerLimit = 1L;
            return new RomanLimits(lowerLimit,upperLimit,"");
        }catch (Exception e){
            return new RomanLimits(1,1, e.getMessage());
        }
    }

    @RequestMapping("/")
    public ModelAndView home()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
