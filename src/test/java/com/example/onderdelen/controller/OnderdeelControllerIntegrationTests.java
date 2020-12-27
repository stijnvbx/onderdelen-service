package com.example.onderdelen.controller;

import com.example.onderdelen.model.Onderdeel;
import com.example.onderdelen.repository.OnderdeelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OnderdeelControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OnderdeelRepository onderdeelRepository;

    private ObjectMapper mapper = new ObjectMapper();

    private Onderdeel onderdeel1 = new Onderdeel("naam1", "merk1", 1, 1);
    private Onderdeel onderdeel2 = new Onderdeel("naam2", "merk2", 2, 2);
    private Onderdeel onderdeelToBeDeleted = new Onderdeel("naam3", "merk3", 3, 3);

    @BeforeEach
    public void beforeAllTests() {
        onderdeelRepository.deleteAll();
        onderdeelRepository.save(onderdeel1);
        onderdeelRepository.save(onderdeel2);
        onderdeelRepository.save(onderdeelToBeDeleted);
    }

    @AfterEach
    public void afterAllTests() {
        onderdeelRepository.deleteAll();
    }

    @Test
    public void givenOnderdeel_whenFindOnderdeelsByMerk_thenReturnJsonOnderdeel() throws Exception {
        mockMvc.perform(get("/onderdelen/{merk}", "merk1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].merk", is("merk1")))
                .andExpect(jsonPath("$[0].naam", is("naam1")))
                .andExpect(jsonPath("$[0].voorraad", is(1)))
                .andExpect(jsonPath("$[0].prijs", is(1)));
    }

    @Test
    public void givenOnderdeel_whenFindAll_thenReturnJsonOnderdeel() throws Exception {
        mockMvc.perform(get("/onderdelen"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].merk", is("merk1")))
                .andExpect(jsonPath("$[0].naam", is("naam1")))
                .andExpect(jsonPath("$[0].voorraad", is(1)))
                .andExpect(jsonPath("$[0].prijs", is(1)))
                .andExpect(jsonPath("$[1].merk", is("merk2")))
                .andExpect(jsonPath("$[1].naam", is("naam2")))
                .andExpect(jsonPath("$[1].voorraad", is(2)))
                .andExpect(jsonPath("$[1].prijs", is(2)))
                .andExpect(jsonPath("$[2].merk", is("merk3")))
                .andExpect(jsonPath("$[2].naam", is("naam3")))
                .andExpect(jsonPath("$[2].voorraad", is(3)))
                .andExpect(jsonPath("$[2].prijs", is(3)));
    }

    @Test
    public void givenPostOnderdeel_thenReturnJsonOnderdeel() throws Exception {
        Onderdeel onderdeelToAdd = new Onderdeel("naam4", "merk4", 4, 4);

        mockMvc.perform(post("/onderdelen")
                .content(mapper.writeValueAsString(onderdeelToAdd))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.merk", is("merk4")))
                .andExpect(jsonPath("$.naam", is("naam4")))
                .andExpect(jsonPath("$.voorraad", is(4)))
                .andExpect(jsonPath("$.prijs", is(4)));
    }

    @Test
    public void givenOnderdeel_whenPutOnderdeel_thenReturnJsonOnderdeel() throws Exception {
        Onderdeel updatedOnderdeel = new Onderdeel("naam1", "merk1", 1, 2);

        mockMvc.perform(put("/onderdelen")
                .content(mapper.writeValueAsString(updatedOnderdeel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.merk", is("merk1")))
                .andExpect(jsonPath("$.naam", is("naam1")))
                .andExpect(jsonPath("$.voorraad", is(1)))
                .andExpect(jsonPath("$.prijs", is(2)));
    }

    @Test
    public void givenOnderdeel_whenDeleteOnderdeel_thenStatusOk() throws Exception {
        mockMvc.perform(delete("/onderdelen/merk/{merk}/naam/{naam}", "merk3", "naam3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoOnderdeel_whenDeleteOnderdeel_thenStatusNotFound() throws Exception {
        mockMvc.perform(delete("/onderdelen/merk/{merk}/naam/{naam}", "merk3", "TEST")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
