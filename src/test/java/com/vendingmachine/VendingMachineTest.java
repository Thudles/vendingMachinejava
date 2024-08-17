package com.vendingmachine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.vendingmachine.VendingMachine;
import com.vendingmachine.Product;

import java.io.File;

public class VendingMachineTest {
    private VendingMachine vendingMachine;

    @BeforeEach
    public void setUp() {
        vendingMachine = new VendingMachine();
    }

    @Test
    public void testAddProduct() {
        Product product = new Product("Soda", 1.50, 10);
        vendingMachine.addProduct(product);
        assertNotNull(vendingMachine.getProduct("Soda"));
        assertEquals(10, vendingMachine.getProduct("Soda").getQuantity());
    }

    @Test
    public void testDepositMoney() {
        vendingMachine.depositMoney(5.00);
        assertEquals(5.00, vendingMachine.getBalance());
    }

    @Test
    public void testPurchaseProductSuccess() throws Exception {
        Product product = new Product("Chips", 2.00, 5);
        vendingMachine.addProduct(product);
        vendingMachine.depositMoney(3.00);
        vendingMachine.purchaseProduct("Chips");
        assertEquals(1.00, vendingMachine.getBalance());
        assertEquals(4, vendingMachine.getProduct("Chips").getQuantity());
    }

    @Test
    public void testPurchaseProductFailureInsufficientFunds() throws Exception {
        Product product = new Product("Candy", 1.00, 10);
        vendingMachine.addProduct(product);
        vendingMachine.depositMoney(0.50);
        Exception exception = assertThrows(Exception.class, () -> {
            vendingMachine.purchaseProduct("Candy");
        });
        assertEquals("Insufficient balance.", exception.getMessage());
    }

    @Test
    public void testLoadProductsFromFile() {
        // Assuming products.txt contains: "Soda,1.50,10"
        vendingMachine.loadProductsFromFile("src/test/resources/products.txt");
        assertNotNull(vendingMachine.getProduct("Soda"));
        assertEquals(10, vendingMachine.getProduct("Soda").getQuantity());
    }

    @Test
    public void testSaveSalesData() {
        Product product = new Product("Juice", 3.00, 7);
        vendingMachine.addProduct(product);
        vendingMachine.saveSalesData();
        
        File file = new File("src/main/resources/products.txt");
        assertTrue(file.exists());
        
        // Additional checks can be implemented to verify the contents of the file
    }

    @Test
    public void testLogTransaction() {
        // This is a simple check to see if log file is created, more detailed checks can be added
        Product product = new Product("Cookies", 2.50, 5);
        vendingMachine.addProduct(product);
        vendingMachine.depositMoney(5.00);
        try {
            vendingMachine.purchaseProduct("Cookies");
            File logFile = new File("src/main/resources/transactions.log");
            assertTrue(logFile.length() > 0);
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }
}
