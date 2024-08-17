package com.vendingmachine;;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class VendingMachine {
    private Map<String, Product> products;
    private double balance;

    public VendingMachine() {
        this.products = new HashMap<>();
        this.balance = 0.0;
    }

    public void addProduct(Product product) {
        products.put(product.getName(), product);
    }

    public void depositMoney(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public void purchaseProduct(String name) throws Exception {
        Product product = getProduct(name);
        if (product == null) {
            throw new Exception("Product not found.");
        }
        if (product.getQuantity() <= 0) {
            throw new Exception("Product out of stock.");
        }
        if (balance < product.getPrice()) {
            throw new Exception("Insufficient balance.");
        }
        product.reduceQuantity(1);
        balance -= product.getPrice();
        logTransaction(name, product.getPrice());
    }

    public void saveSalesData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/SalesData.txt"))) {
            for (Product product : products.values()) {
                String line = String.format("%s,%f,%d%n",
                        product.getName(), product.getPrice(), product.getQuantity());
                writer.write(line);
            }
        } catch (IOException e) {
            System.out.println("Error saving sales data: " + e.getMessage());
        }
    }

    public void loadProductsFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    double price = Double.parseDouble(parts[1].trim());
                    int quantity = Integer.parseInt(parts[2].trim());
                    addProduct(new Product(name, price, quantity));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
    }

    private Product getProduct(String name) {
        return products.get(name);
    }

    private void logTransaction(String productName, double price) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/transactions.log", true))) {
            String logEntry = String.format("Product: %s, Price: %.2f, Date: %s%n",
                    productName, price, java.time.LocalDateTime.now());
            writer.write(logEntry);
        } catch (IOException e) {
            System.out.println("Error logging transaction: " + e.getMessage());
        }
    }

    public Map<String, Product> getProducts() {
        return products;
    }

    public double getBalance() {
        return balance;
    }
}
