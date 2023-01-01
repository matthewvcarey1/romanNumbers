package uk.co.example.romanNumbers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class RomanNumbersApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Test
	void contextLoads() {
	}

	@Test
	public void noParamRomanShouldReturnAnErrorMessage() throws Exception {
		this.mockMvc.perform(get("/roman"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.error").value("Parameter decimal is missing or out of range, decimal must be in range 1 - 9999999999"));
	}

	@Test
	public void nonNumericRomanShouldReturnAnErrorMessage() throws Exception {
		this.mockMvc.perform(get("/roman").param("decimal", "blob")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.error").value("Parameter decimal could not be parsed as an Integer in the range or 1 - 9999999999"));
	}
	@Test
	public void paramDecimal1999ShouldReturnRomanValue() throws Exception {
		this.mockMvc.perform(get("/roman").param("decimal", "1999"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.value").value("MCMXCIX"));
	}

}