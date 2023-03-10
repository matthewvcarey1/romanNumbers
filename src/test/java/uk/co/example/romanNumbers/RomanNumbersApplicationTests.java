package uk.co.example.romanNumbers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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
				.andExpect(jsonPath("$.error").value(containsString("The input value is missing or out of range, decimal must be in range 1 - 9")));
	}

	@Test
	public void nonNumericParamRomanShouldReturnAnErrorMessage() throws Exception {
		this.mockMvc.perform(get("/roman").param("decimal", "blob")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.error").value("The input value could not be parsed as a Long Integer"));
	}
	@Test
	public void paramDecimal1999ShouldReturnRomanValue() throws Exception {
		this.mockMvc.perform(get("/roman").param("decimal", "1999"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.value").value("MCMXCIX"))
				.andExpect(jsonPath("$.source").value("1999"));
	}

	@Test
	public void paramDecimal01999ShouldReturnRomanValue() throws Exception {
		this.mockMvc.perform(get("/roman").param("decimal", "01999"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.value").value("MCMXCIX"))
				.andExpect(jsonPath("$.source").value("1999"));
	}

	@Test
	public void pathDecimal1999ShouldReturnRomanValue() throws Exception {
		this.mockMvc.perform(get("/roman/1999"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.value").value("MCMXCIX"))
				.andExpect(jsonPath("$.source").value("1999"));;
	}

	@Test
	public void pathDecimal01999ShouldReturnRomanValue() throws Exception {
		this.mockMvc.perform(get("/roman/01999"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.value").value("MCMXCIX"))
				.andExpect(jsonPath("$.source").value("1999"));;
	}

	@Test
	public void pathRomanDecimal0ShouldReturnAnErrorMessage() throws Exception {
		this.mockMvc.perform(get("/roman/0"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.error").value(containsString("The input value is missing or out of range, decimal must be in range 1 - 9")))
				.andExpect(jsonPath("$.source").value(""));;
	}

	@Test
	public void pathRomanDecimalMAXLONGShouldReturnAValue() throws Exception {
		this.mockMvc.perform(get("/roman/"+ Long.MAX_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.value").value(containsString("I??????X??????C??????C??????X??????X??????M????M????M????C????C????C????L????X????X????M????M????X????X????X????V????I????D??C??C??C??L??I??V??D??C??C??L??X??X??V??DCCCVII")))
				.andExpect(jsonPath("$.source").value(""+ Long.MAX_VALUE));
	}

	@Test
	public void pathRomanDecimalMAXLONGPlus1ShouldReturnAnError() throws Exception {
		this.mockMvc.perform(get("/roman/9223372036854775808"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.error").value(containsString("The input value could not be parsed as a Long Integer")))
				.andExpect(jsonPath("$.source").value(""));
	}

	@Value("${romanNumbersFile}")
	String romanNumbersFilename;

	@Value("${romanNumbersPath}")
	String romanNumbersPath;
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

	@Test
	public void getHome() throws Exception{
		this.mockMvc.perform(get("/"))
				.andDo(print()).andExpect(status().isOk())
						.andExpect(model().size(0));
	}
}




