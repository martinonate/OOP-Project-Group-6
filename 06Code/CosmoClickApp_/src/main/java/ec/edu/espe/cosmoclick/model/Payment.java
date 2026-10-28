package ec.edu.espe.cosmoclick.model;

import java.util.Calendar;

/**
 *
 * @author Martin Onate <Team 6, espe>
 */
public class Payment {
   
    private int id;
    private float amount;
    private String paymentMethod;
    private Calendar paymentDate;
    private boolean paymentStatus;

    public Payment() {
    }

    public Payment(int id, float amount, String paymentMethod, Calendar paymentDate, boolean paymentStatus) {
        this.id = id;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Calendar getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Calendar paymentDate) {
        this.paymentDate = paymentDate;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }


    public void processPayment(Sale sale) {
        this.amount = sale.calculateTotal();
        this.paymentDate = Calendar.getInstance();
        this.paymentStatus = true;
        sale.setPayment(this);
    }

    public String checkStatus() {
        if (paymentStatus) {
            return "Pagado";
        }
        return "Pendiente";
    }

    public void generateRefund(float refundAmount) {
        if (paymentStatus && refundAmount <= amount) {
            amount = amount - refundAmount;
            System.out.println("Reembolso generado: " + refundAmount);
        } else {
            System.out.println("No se puede generar el reembolso");
        }
    }

    public boolean validateAmount(float saleAmount) {
        return saleAmount > 0 && saleAmount == amount;
    }

    
}
