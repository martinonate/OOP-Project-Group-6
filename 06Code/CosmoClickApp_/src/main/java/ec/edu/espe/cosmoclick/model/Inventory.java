package ec.edu.espe.cosmoclick.model;

import java.util.ArrayList;

/**
 *
 * @author Martin Onate <Team 6, espe>
 */
public class Inventory {
    
    private int id;
    private ArrayList<Product> products;
    private int totalStock;
    private int umbralMinimo;
    
    
    public Inventory() {
        this.products = new ArrayList<>();
    }

    public Inventory(int id, int totalStock, int umbralMinimo) {
        this.id = id;
        this.products = new ArrayList<>();
        this.totalStock = totalStock;
        this.umbralMinimo = umbralMinimo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }

    public int getUmbralMinimo() {
        return umbralMinimo;
    }

    public void setUmbralMinimo(int umbralMinimo) {
        this.umbralMinimo = umbralMinimo;
    }


    public void addProduct(Product product) {
        products.add(product);
        updateStock(product);
    }

    public void updateQuantity(Product product, int quantity) {
        product.setQuantity(quantity);
        updateStock(product);
    }

  
    public void updateStock(Product product) {
        totalStock = 0;
        for (int i = 0; i < products.size(); i++) {
            totalStock = totalStock + products.get(i).getQuantity();
        }
    }

    public boolean checkAvailability(int id, int quantityProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                return products.get(i).getQuantity() >= quantityProduct;
            }
        }
        return false;
    }

    public void generateLowStockAlert() {
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.getQuantity() <= umbralMinimo) {
                System.out.println("ALERTA: stock bajo de " + p.getName() + " (" + p.getQuantity() + ")");
            }
        }
    }

    public Product getProductDetails(int id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                return products.get(i);
            }
        }
        return null;
    }

    
}
