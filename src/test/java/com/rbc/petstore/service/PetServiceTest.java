/**
 *
 */
package com.rbc.petstore.service;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.rbc.petstore.model.InventoryStatus;
import com.rbc.petstore.model.Pet;
import com.rbc.petstore.repository.PetRepository;

/**
 *
 */
//@Ignore
@RunWith(MockitoJUnitRunner.class)
public class PetServiceTest {

    @InjectMocks
    PetService petService;

    @Mock
    PetRepository petRepository;

    Pet bella, bunny, chocky;

    @Before
    public void setup() {
        bella = new Pet(1L, "bella", InventoryStatus.AVAILABLE);
        bunny = new Pet(2L, "bunny", InventoryStatus.PENDING);
        chocky = new Pet(1L, "chocky", InventoryStatus.SOLD);
    }

    /**
     * Test {@link PetService#getAllPets()}
     */
    @Test
    public void test_getAll() {
        when(petRepository.findAll()).thenReturn(Arrays.asList(bella, bunny, chocky));

        Iterable<Pet> result = petService.getAllPets();
        Assert.assertNotNull(result);
        Assert.assertEquals(3, result.spliterator().getExactSizeIfKnown());
    }

    /**
     * Test {@link PetService#getPet(Long)}
     */
    @Test
    public void test_getById() {
        when(petRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(bella));

        Pet result = petService.getPet(1L);

        Assert.assertNotNull(result);
        Assert.assertEquals(bella.getName(), result.getName());
    }

    /**
     * Test {@link PetService#createPet(Pet)}
     */
    @Test
    public void test_createPet() {
        when(petRepository.save(bella)).thenReturn(bella);
        bella.setId(null);
        Pet result = petService.createPet(bella);

        Assert.assertNotNull(result);
        Assert.assertEquals(bella.getName(), result.getName());
    }
}
