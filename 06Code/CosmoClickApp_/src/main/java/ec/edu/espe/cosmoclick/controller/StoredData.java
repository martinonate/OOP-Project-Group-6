package ec.edu.espe.cosmoclick.controller;

import java.util.ArrayList;

/**
 * 
 * @author Martin Onate <Team 6, espe>
 */
public class StoredData {

    public static class CustomerData {
        public int id;
        public String name;
        public int phone;
        public String email;
    }
    
    public static class HistoryData {
    public int id;
    public int customerId;
    public int age;
    public String patientName;
    public String skinType;
    public String treatmentHistory;
    public String recordDate;
    public String allergies;
    public String observations;
}
    
    public static class AppointmentData {
        public int id;
        public String date;
        public String time;
        public String status;
        public int customerId;
        public int serviceId;
    }

    public static class ProductData {
        public int id;
        public String name;
        public String brand;
        public double price;
        public double discount;
        public String expirationDate;
        public int quantity;
    }

    public static class SaleItemData {
        public int productId;
        public int quantity;
    }

    public static class PaymentData {
        public int id;
        public float amount;
        public String method;
        public String date;
        public boolean paid;
    }

    public static class SaleData {
        public int id;
        public String date;
        public int customerId;
        public String details;
        public ArrayList<SaleItemData> items = new ArrayList<>();
        public PaymentData payment;
    }

    private StoredData() {
    }
}
