package com.vendingmachine;

import java.util.Scanner;

public class VendingMachineCLI {

    public static void main(String[] args) {
        // Create an instance of VendingMachine
        VendingMachine vendingMachine = new VendingMachine();

        // Load initial products from a file
        vendingMachine.loadProductsFromFile("src/main/resources/products.txt");

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            // Display available products
            System.out.println("\nVending Machine:");
            for (Product product : vendingMachine.getProducts().values()) {
                System.out.println(product.getName() + ": $" + product.getPrice() + " (" + product.getQuantity() + " in stock)");
            }
            System.out.println("Balance: $" + vendingMachine.getBalance());
            System.out.println("Options: (1) Deposit Money (2) Purchase (3) Add Product (4) Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter amount to deposit: ");
                    double amount = scanner.nextDouble();
                    vendingMachine.depositMoney(amount);
                    break;
                case 2:
                    System.out.print("Enter product name: ");
                    String productName = scanner.nextLine();
                    try {
                        vendingMachine.purchaseProduct(productName);
                        System.out.println("Purchased " + productName);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Enter new product name: ");
                    String newProductName = scanner.nextLine();
                    System.out.print("Enter product price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter product quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    vendingMachine.addProduct(new Product(newProductName, price, quantity));
                    System.out.println("Product added: " + newProductName);
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
        vendingMachine.saveSalesData();
        scanner.close();
    }
}
