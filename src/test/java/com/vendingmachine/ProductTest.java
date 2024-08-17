package com.vendingmachine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.vendingmachine.Product;


public class ProductTest {
    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product("Chips", 1.50, 10);
    }

    @Test
    public void testGetName() {
        assertEquals("Chips", product.getName());
    }

    @Test
    public void testGetPrice() {
        assertEquals(1.50, product.getPrice());
    }

    @Test
    public void testGetQuantity() {
        assertEquals(10, product.getQuantity());
    }

    @Test
    public void testReduceQuantitySuccess() {
        product.reduceQuantity(3);
        assertEquals(7, product.getQuantity());
    }

    @Test
    public void testReduceQuantityNotEnough() {
        product.reduceQuantity(15);
        assertEquals(10, product.getQuantity()); // Quantity should not change if reducing more than available
    }

    @Test
    public void testReduceQuantityInvalidAmount() {
        product.reduceQuantity(-1);
        assertEquals(10, product.getQuantity()); // Quantity should not change for negative amounts
    }
}
