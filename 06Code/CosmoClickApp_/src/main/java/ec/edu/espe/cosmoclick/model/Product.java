package ec.edu.espe.cosmoclick.model;

import java.util.Calendar;

/**
 *
 * @author Martin Onate <Team 6, espe>
 */
public class Product {
    
    private int id;
    private String name;
    private String brand;
    private Price price;
    private Calendar expirationDate;
    private int quantity;

    public Product() {
    }

    public Product(int id, String name, String brand, Price price, Calendar expirationDate, int quantity) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Calendar getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Calendar expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void searchProduct() {
        System.out.println("Producto: " + name + " | Marca: " + brand + " | Stock: " + quantity);
    }

    public void updatePrice(Price price) {
        this.price = price;
    }

    public void buyProduct() {
        quantity++;
    }

    public void sellProduct() {
        if (quantity > 0) {
            quantity--;
        } else {
            System.out.println("No hay stock de " + name);
        }
    }

    public boolean checkExpiration(Calendar today) {
        return expirationDate.before(today);
    }

    public void discardProduct() {
        quantity = 0;
    }

}
