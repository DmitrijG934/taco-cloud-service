package nn.dgordeev.taco.controller;

import nn.dgordeev.taco.container.PostgresContainer;
import nn.dgordeev.taco.repository.IngredientRepository;
import nn.dgordeev.taco.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest
@Testcontainers
@DisplayName("Тесты на проверку главной страницы")
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private OrderRepository orderRepository;

    @Container
    private static final PostgresContainer POSTGRES_CONTAINER = new PostgresContainer();

    @Test
    @DisplayName("Главная страница отдаёт корректное представление")
    void when_homePageRequested_thenPageContainsExpectedData() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(content().string(containsString("Welcome to...")));
    }
}