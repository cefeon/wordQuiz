package net.cefeon.wordquiz.controller;

import net.cefeon.wordquiz.repository.TranslationPlEngRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = TranslationController.class)
class TranslationControllerTest {

    @MockBean
    private TranslationPlEngRepository translationPlEngRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Should list polish translations of english word when making request to endpoint /en/pl/{word}")
    void shouldListPolishFromEnglish() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/en/pl/onion"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].word", Matchers.is("cebula")));
    }

}