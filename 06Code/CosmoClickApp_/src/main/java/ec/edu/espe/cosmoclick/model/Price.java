package ec.edu.espe.cosmoclick.model;

/**
 *
 * @author Martin Onate <Team 6, espe>
 */
public class Price {
    
    private double discount;
    private double finalPrice;

    public Price() {
    }

    public Price(double discount, double finalPrice) {
        this.discount = discount;
        this.finalPrice = finalPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public double calculateFinalPrice() {
        return finalPrice;
    }

    public void applyDiscount(double discount) {
        this.discount = discount;
        this.finalPrice = finalPrice - (finalPrice * discount / 100);
    }

    public void updatePrice(float newPrice) {
        this.finalPrice = newPrice;
        this.discount = 0;
    }

}
