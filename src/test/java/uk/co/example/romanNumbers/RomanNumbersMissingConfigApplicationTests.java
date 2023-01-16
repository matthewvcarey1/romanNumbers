package uk.co.example.romanNumbers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = { "romanNumbersPath=src/main/resources/static/" })
@AutoConfigureMockMvc
class RomanNumbersMissingConfigApplicationTests {
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

    @Test
    public void noConfigNoParamRomanShouldReturnAnErrorMessage() throws Exception {
        this.mockMvc.perform(get("/roman"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.error")
                        .value("java.io.FileNotFoundException: src/main/resources/static/romanNumbers.json (No such file or directory)"))
                .andExpect(jsonPath("$.source").value(""));
    }

    @Test
    public void noConfigRomanShouldReturnAnErrorMessage() throws Exception {
        this.mockMvc.perform(get("/roman/1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.error")
                        .value("java.io.FileNotFoundException: src/main/resources/static/romanNumbers.json (No such file or directory)"))
                .andExpect(jsonPath("$.source").value(""));
    }
    @Test
    public void getRomanLimits() throws Exception {
        this.mockMvc.perform(get("/romanLimits/"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.error")
                .value("src/main/resources/static/romanNumbers.json (No such file or directory)"));
    }
}
