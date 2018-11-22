package com.rbc.petstore.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.rbc.petstore.PetstoreApplication;
import com.rbc.petstore.dto.PetDTO;
import com.rbc.petstore.model.InventoryStatus;
import com.rbc.petstore.model.Pet;
import com.rbc.petstore.repository.PetRepository;
import com.rbc.petstore.service.PetService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PetstoreApplication.class)
@Transactional
public class PetResourceTest {

    private static final String CNT_PET_NAME = "testValidPet";

    @Autowired
    private PetRepository repository;

    @Autowired
    private PetService petService;

    private MockMvc restMvc;

    /**
     * Initialize mocks
     */
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

        PetResource petResource = new PetResource(petService);
        this.restMvc = MockMvcBuilders.standaloneSetup(petResource).build();
    }

    /**
     * Test Pet creation REST call with valid parameters
     *
     * @throws IOException
     * @throws Exception
     * @see PetResource#create(PetDTO)
     */
    @Test
    public void testCreate_valid() throws Exception {
        PetDTO pet = getValidPet();

        restMvc.perform(
                post("/api/pet")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(getPetJSON(pet)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(pet.getName()));
    }

    /**
     * Test Pet creation REST calls with invalid inputs that must fail at validation
     * level
     *
     * @throws IOException
     * @throws Exception
     * @see PetResource#create(PetDTO)
     */
    @Test
    public void testCreate_invalidName() throws Exception {
        PetDTO pet = getValidPet();
        pet.setName("");

        restMvc.perform(
                post("/api/pet")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(getPetJSON(pet)))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test get pet call with an existing id
     *
     * @throws IOException
     * @throws Exception
     * @see PetResource#getOne(Long)
     */
    @Test
    public void testGet_valid() throws Exception {
        Pet firstPet = repository.findById(1L).get();

        restMvc.perform(
                get("/api/pet/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(firstPet.getId().toString()))
                .andExpect(jsonPath("$.name").value(firstPet.getName()));
    }

    /**
     * Test get pet calls with negative value.
     *
     * @throws IOException
     * @throws Exception
     * @see PetResource#getOne(Long)
     */
    @Test
    public void testGet_negativeValue() throws Exception {
        restMvc.perform(
                get("/api/pet/-1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test get pet calls with invalid id
     *
     * @throws IOException
     * @throws Exception
     * @see PetResource#getOne(Long)
     */
    @Test
    public void testGet_nonNumericValue() throws Exception {
        restMvc.perform(
                get("/api/pet/aa")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test delete call with an existing pet identifier
     *
     * @throws IOException
     * @throws Exception
     * @see PetResource#delete(Long)
     */
    @Test
    public void testDelete_valid() throws Exception {

        restMvc.perform(
                delete("/api/pet/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test delete call with invalid value
     *
     * @throws IOException
     * @throws Exception
     * @see PetResource#delete(Long)
     */
    @Test
    public void testDelete_invalidValue() throws Exception {
        restMvc.perform(
                delete("/api/pet/-1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Helper methods
     */

    /**
     * @return a dummy valid pet instance
     */
    private PetDTO getValidPet() {
        PetDTO pet = new PetDTO(null, CNT_PET_NAME, InventoryStatus.AVAILABLE);
        return pet;
    }

    private String getPetJSON(PetDTO pet) {
        Gson gson = new Gson();
        String json = gson.toJson(pet);
        return json;
    }
}
