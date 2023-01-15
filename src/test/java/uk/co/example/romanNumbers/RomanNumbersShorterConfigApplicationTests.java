package uk.co.example.romanNumbers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = { "romanNumbersPath=src/test/resources/definitions/" })
@AutoConfigureMockMvc
class RomanNumbersShorterConfigApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        try {
            IntToRomanConverter.removeInstance();
        } catch(Exception e){
            e.printStackTrace();
        }
    }


    @Value("${romanNumbersFile}")
    String romanNumbersFilename;

    @Value("${romanNumbersPath}")
    String romanNumbersPath;

    @Test
    public void paramDecimal1999ShouldReturnRomanValue() throws Exception {
        String romanNumbersParameter = romanNumbersPath+romanNumbersFilename;
        IntToRomanConverter testConvertor = IntToRomanConverter.getInstance(romanNumbersParameter);
        this.mockMvc.perform(get("/roman").param("decimal", "1999"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value("MCMXCIX"));
    }

    @Test
    public void pathRomanDecimalLimitShouldReturnAValue() throws Exception {
        String romanNumbersParameter = romanNumbersPath+romanNumbersFilename;
        IntToRomanConverter testConvertor = IntToRomanConverter.getInstance(romanNumbersParameter);
        long upperLimit = testConvertor.getTopLimit();
        this.mockMvc.perform(get("/roman/"+ upperLimit))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(containsString("X̿̿̅C̿̿̅I̿̿̅X̿̿̅C̿̿M̿̿X̿̿C̿̿I̿̿X̿̿C̿̅M̿̅X̿̅C̿̅I̿̅X̿̅C̿M̿X̿C̿I̿X̿C̅M̅X̅C̅I̅X̅CMXCIX")));
    }

    @Test
    public void pathRomanDecimalLimitPluss1ShouldReturnAnErrorMessage() throws Exception {
        String romanNumbersParameter = romanNumbersPath+romanNumbersFilename;
        IntToRomanConverter testConvertor = IntToRomanConverter.getInstance(romanNumbersParameter);
        long upperLimitPlus1 = testConvertor.getTopLimit()+1;
        this.mockMvc.perform(get("/roman/"+upperLimitPlus1))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value(containsString("Parameter decimal is missing or out of range, decimal must be in range 1 - 9")));
    }
    @Test
    public void getRomanLimits() throws Exception {
        String romanNumbersParameter = romanNumbersPath+romanNumbersFilename;
        IntToRomanConverter testConvertor = IntToRomanConverter.getInstance(romanNumbersParameter);
        long upperLimit = testConvertor.getTopLimit();
        this.mockMvc.perform(get("/romanLimits/"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.lowerLimit").value("1"))
                .andExpect(jsonPath("$.upperLimit").value(Long.valueOf(upperLimit).toString()));
    }
}

