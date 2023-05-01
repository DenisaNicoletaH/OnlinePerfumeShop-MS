package com.onlineperfumeshop.productsservice.datalayer;


import com.onlineperfumeshop.productsservice.datalayer.Inventory.Inventory;
import com.onlineperfumeshop.productsservice.datalayer.Inventory.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class InventoryPersistanceTest {


    private Inventory presavedInventory;

    @Autowired
    InventoryRepository inventoryRepository;



    @BeforeEach
    public void setup(){
        inventoryRepository.deleteAll();
        LocalDate expectedLastInvUpdate= LocalDate.parse("2023-02-14");
        presavedInventory = inventoryRepository.save(new Inventory((expectedLastInvUpdate)));
    }

    //by id
    @Test
    public void findByInventoryIdentifier_InventoryId_ShouldSucceed(){

        //act
        Inventory found=inventoryRepository.findByInventoryIdentifier_InventoryId(presavedInventory.getInventoryIdentifier().getInventoryId());
    //assert
        assertNotNull(found);
    assertThat(presavedInventory,samePropertyValuesAs(found));


    }

    //return null if id invalid

    @Test
    public void findByInventoryIdentifier_InventoryId_ShouldReturnNull(){

        //act
        Inventory found =inventoryRepository.findByInventoryIdentifier_InventoryId(presavedInventory.getInventoryIdentifier().getInventoryId() +1);

        //assert
        assertNull(found);

    }


    @Test
    public void saveNewInventory_shouldSucceed(){

        //arrange
        LocalDate expectedLastInventoryUpdate= LocalDate.parse("2023-02-12");

    Inventory savedInventory=inventoryRepository.save(new Inventory(expectedLastInventoryUpdate));

    //assert
        assertNotNull(savedInventory);

        assertNotNull(savedInventory.getId());

        assertNotNull(savedInventory.getInventoryIdentifier().getInventoryId());

        assertEquals(expectedLastInventoryUpdate,savedInventory.getLastInventoryUpdate());

    }


    @Test
    public void deleteInventoryWithValidIdentifier_ShouldSucceed(){
        inventoryRepository.delete(presavedInventory);

        Inventory found=inventoryRepository.findByInventoryIdentifier_InventoryId((presavedInventory.getInventoryIdentifier().getInventoryId()));
        assertNull(found);
    }

    @Test
    public void updateClient_shouldSucceed(){
        //assert
        LocalDate updatedDate= LocalDate.parse("2023-03-16");

        presavedInventory.setLastInventoryUpdate(updatedDate);

        //act
        Inventory savedInventory=inventoryRepository.save(presavedInventory);

        //assert
        assertNotNull(savedInventory);
        assertThat(savedInventory,samePropertyValuesAs(presavedInventory));
    }


}
