package com.roerdev.arqhexagonalmodular;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.roerdev.arqhexagonalmodular.productprice.model.dto.PriceDto;
import com.roerdev.arqhexagonalmodular.productprice.port.input.PriceUseCase;
import com.roerdev.arqhexagonalmodular.productprice.rest.controller.PriceController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PriceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    

    @Mock
    private PriceUseCase priceUseCase;

    @InjectMocks
    private PriceController priceController;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(priceController).build();
    }

    @Test
    public void testGetAllPrices() throws Exception {

        URL resource1 = getClass().getClassLoader().getResource("Price1.json");
        URL resource2 = getClass().getClassLoader().getResource("Price2.json");

        PriceDto price1 = this.objectMapper.readValue(resource1, PriceDto.class);
        PriceDto price2 = this.objectMapper.readValue(resource2, PriceDto.class);

        var list = List.of(price1, price2);
        when(priceUseCase.getAllPrices()).thenReturn(list);

        ResultActions result = mockMvc.perform(get("/api/v1/prices")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(this.objectMapper.writeValueAsString(list)));
    }

    @Test
    public void testGetPriceDtoById() throws Exception {
        URL resource2 = getClass().getClassLoader().getResource("Price2.json");
        PriceDto price2 = this.objectMapper.readValue(resource2, PriceDto.class);
        when(priceUseCase.getPriceById(any())).thenReturn(Optional.of(price2));

        ResultActions result = mockMvc.perform(get("/api/v1/prices/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testCreatePriceDto() throws Exception {
        URL resource2 = getClass().getClassLoader().getResource("Price2.json");
        PriceDto price2 = this.objectMapper.readValue(resource2, PriceDto.class);
        when(priceUseCase.createPrice(price2)).thenReturn(price2);

        ResultActions result = mockMvc.perform(post("/api/v1/prices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(price2)));

        result.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(this.objectMapper.writeValueAsString(price2)));
    }

    @Test
    public void testUpdatePriceDto() throws Exception {
        URL resource2 = getClass().getClassLoader().getResource("Price2.json");
        PriceDto price2 = this.objectMapper.readValue(resource2, PriceDto.class);
        when(priceUseCase.updatePrice(1L, price2)).thenReturn(price2);

        ResultActions result = mockMvc.perform(put("/api/v1/prices/{id}", 1L)
                .content(this.objectMapper.writeValueAsString(price2))
                .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(this.objectMapper.writeValueAsString(price2)));
    }

    @Test
    public void testUpdatePriceDtoReturnNull() throws Exception {
        URL resource2 = getClass().getClassLoader().getResource("Price2.json");
        PriceDto price2 = this.objectMapper.readValue(resource2, PriceDto.class);
        when(priceUseCase.updatePrice(1L, price2)).thenReturn(null);

        ResultActions result = mockMvc.perform(put("/api/v1/prices/{id}", 1L)
                .content(this.objectMapper.writeValueAsString(price2))
                .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isNotFound());
    }

    @Test
    public void testDeletePriceDto() throws Exception {
        Long priceId = 1L;
        doNothing().when(priceUseCase).deletePrice(priceId);

        ResultActions result = mockMvc.perform(delete("/api/v1/prices/{id}", priceId)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNoContent());
    }

    @Test
    void getPriceByDateAndProductAndChain() throws Exception{
        URL resource2 = getClass().getClassLoader().getResource("Price2.json");
        PriceDto price2 = this.objectMapper.readValue(resource2, PriceDto.class);
        when(priceUseCase.getPriceByDateAndProductAndChain(any(), any(), any())).thenReturn(Optional.of(price2));

        ResultActions result = mockMvc.perform(get("/api/v1/prices/{date}/{product}/{chain}",
                "2023-09-01 12:04:55",
                price2.getProductId(),
                price2.getBrandId().getDescription())
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L));
    }
}