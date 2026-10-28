package ec.edu.espe.cosmoclick.model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Martin Onate <Team 6, espe>
 */
public class PaymentReport {
   
    private final int MAX_PAYMENTS = 100;
    private int id;
    private Calendar reportDate;
    private String paymentMethod;
    private float totalAmount;
    private String paymentStatus;
    private Payment[] payments;
    private int paymentCount;

    public PaymentReport() {
        this.payments = new Payment[MAX_PAYMENTS];
        this.paymentCount = 0;
    }

    public PaymentReport(int id, Calendar reportDate, String paymentMethod, float totalAmount, String paymentStatus) {
        this.id = id;
        this.reportDate = reportDate;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.payments = new Payment[MAX_PAYMENTS];
        this.paymentCount = 0;
    }

    // ===== Getters y Setters =====
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getReportDate() {
        return reportDate;
    }

    public void setReportDate(Calendar reportDate) {
        this.reportDate = reportDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Payment[] getPayments() {
        return payments;
    }

    public void setPayments(Payment[] payments) {
        this.payments = payments;
    }

    public int getPaymentCount() {
        return paymentCount;
    }

    public void setPaymentCount(int paymentCount) {
        this.paymentCount = paymentCount;
    }

    public void generateReport(Payment payment) {
        if (paymentCount < MAX_PAYMENTS) {
            payments[paymentCount] = payment;
            paymentCount++;
            totalAmount = calculateTotal();
        } else {
            System.out.println("El reporte esta lleno");
        }
    }

    public float calculateTotal() {
        float total = 0;
        for (int i = 0; i < paymentCount; i++) {
            total = total + payments[i].getAmount();
        }
        return total;
    }

    public ArrayList<Payment> filterByMethod(String method) {
        ArrayList<Payment> filtered = new ArrayList<>();
        for (int i = 0; i < paymentCount; i++) {
            if (payments[i].getPaymentMethod().equalsIgnoreCase(method)) {
                filtered.add(payments[i]);
            }
        }
        return filtered;
    }

    public void showPaymentsStatus(Payment payment) {
        System.out.println("Pago #" + payment.getId() + ": " + payment.checkStatus());
    }

    
}
