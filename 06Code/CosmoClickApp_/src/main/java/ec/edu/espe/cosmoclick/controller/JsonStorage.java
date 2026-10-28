package ec.edu.espe.cosmoclick.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ec.edu.espe.cosmoclick.model.Appointment;
import ec.edu.espe.cosmoclick.model.Customer;
import ec.edu.espe.cosmoclick.model.PatientHistory;
import ec.edu.espe.cosmoclick.model.Payment;
import ec.edu.espe.cosmoclick.model.Price;
import ec.edu.espe.cosmoclick.model.Product;
import ec.edu.espe.cosmoclick.model.Sale;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * 
 * @author Martin Onate <Team 6, espe>
 */
public class JsonStorage {

    private final String folder;
    private final Gson gson;

    public JsonStorage(String folder) {
        this.folder = folder;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }
    
    public void save(SpaController controller) throws IOException {
        new File(folder).mkdirs();
        write("customers.json", toCustomerData(controller));
        write("histories.json", toHistoryData(controller));
        write("appointments.json", toAppointmentData(controller));
        write("products.json", toProductData(controller));
        write("sales.json", toSaleData(controller));
    }

    public void load(SpaController controller) throws IOException {
        loadCustomers(controller);
        loadProducts(controller);
        loadAppointments(controller);
        loadSales(controller);
    }

    private ArrayList<StoredData.CustomerData> toCustomerData(SpaController controller) {
        ArrayList<StoredData.CustomerData> list = new ArrayList<>();
        for (int i = 0; i < controller.getCustomers().size(); i++) {
            Customer c = controller.getCustomers().get(i);
            StoredData.CustomerData data = new StoredData.CustomerData();
            data.id = c.getId();
            data.name = c.getName();
            data.phone = c.getPhone();
            data.email = c.getEmail();
            list.add(data);
        }
        return list;
    }
    
    private ArrayList<StoredData.HistoryData> toHistoryData(SpaController controller) {
        ArrayList<StoredData.HistoryData> list = new ArrayList<>();
        for (int i = 0; i < controller.getHistories().size(); i++) {
            PatientHistory h = controller.getHistories().get(i);
            StoredData.HistoryData data = new StoredData.HistoryData();
            data.id = h.getId();
            data.customerId = h.getCustomer().getId();
            data.age = h.getAge();
            data.patientName = h.getPatientName();
            data.skinType = h.getSkinType();
            data.treatmentHistory = h.getTreatmentHistory();
            data.recordDate = h.getRecordDate() == null ? null : DateUtil.format(h.getRecordDate());
            data.allergies = h.getAllergies();
            data.observations = h.getObservations();
            list.add(data);
        }
        return list;
    }
    
    private ArrayList<StoredData.AppointmentData> toAppointmentData(SpaController controller) {
        ArrayList<StoredData.AppointmentData> list = new ArrayList<>();
        for (int i = 0; i < controller.getAppointments().size(); i++) {
            Appointment a = controller.getAppointments().get(i);
            StoredData.AppointmentData data = new StoredData.AppointmentData();
            data.id = a.getId();
            data.date = DateUtil.format(a.getDate());
            data.time = a.getTime();
            data.status = a.getStatus();
            data.customerId = a.getCustomer().getId();
            data.serviceId = a.getService().getId();
            list.add(data);
        }
        return list;
    }

    private ArrayList<StoredData.ProductData> toProductData(SpaController controller) {
        ArrayList<StoredData.ProductData> list = new ArrayList<>();
        ArrayList<Product> products = controller.getInventory().getProducts();
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            StoredData.ProductData data = new StoredData.ProductData();
            data.id = p.getId();
            data.name = p.getName();
            data.brand = p.getBrand();
            data.price = p.getPrice().getFinalPrice();
            data.discount = p.getPrice().getDiscount();
            data.expirationDate = DateUtil.format(p.getExpirationDate());
            data.quantity = p.getQuantity();
            list.add(data);
        }
        return list;
    }

    private ArrayList<StoredData.SaleData> toSaleData(SpaController controller) {
        ArrayList<StoredData.SaleData> list = new ArrayList<>();
        for (int i = 0; i < controller.getSales().size(); i++) {
            Sale s = controller.getSales().get(i);
            StoredData.SaleData data = new StoredData.SaleData();
            data.id = s.getId();
            data.date = DateUtil.format(s.getDateTime());
            data.customerId = s.getCustomer().getId();
            data.details = s.getSaleDetails();
            data.items = toItemData(s);
            data.payment = toPaymentData(s.getPayment());
            list.add(data);
        }
        return list;
    }

    // Agrupa los productos repetidos de una venta en (productId, quantity)
    private ArrayList<StoredData.SaleItemData> toItemData(Sale sale) {
        ArrayList<StoredData.SaleItemData> items = new ArrayList<>();
        for (int i = 0; i < sale.getProducts().size(); i++) {
            int productId = sale.getProducts().get(i).getId();
            StoredData.SaleItemData found = null;
            for (int j = 0; j < items.size(); j++) {
                if (items.get(j).productId == productId) {
                    found = items.get(j);
                }
            }
            if (found == null) {
                found = new StoredData.SaleItemData();
                found.productId = productId;
                items.add(found);
            }
            found.quantity++;
        }
        return items;
    }

    private StoredData.PaymentData toPaymentData(Payment payment) {
        if (payment == null) {
            return null;
        }
        StoredData.PaymentData data = new StoredData.PaymentData();
        data.id = payment.getId();
        data.amount = payment.getAmount();
        data.method = payment.getPaymentMethod();
        data.date = DateUtil.format(payment.getPaymentDate());
        data.paid = payment.isPaymentStatus();
        return data;
    }

    // ===== Conversion datos planos -> objeto =====
    private void loadCustomers(SpaController controller) throws IOException {
        ArrayList<StoredData.CustomerData> list = read("customers.json",
                new TypeToken<ArrayList<StoredData.CustomerData>>() { }.getType());
        controller.getCustomers().clear();
        for (int i = 0; i < list.size(); i++) {
            StoredData.CustomerData d = list.get(i);
            controller.getCustomers().add(new Customer(d.id, d.name, d.phone, d.email));
        }
    }
    
     private void loadHistories(SpaController controller) throws IOException {
        ArrayList<StoredData.HistoryData> list = read("histories.json",
                new TypeToken<ArrayList<StoredData.HistoryData>>() { }.getType());
        controller.getHistories().clear();
        for (int i = 0; i < list.size(); i++) {
            StoredData.HistoryData d = list.get(i);
            Customer customer = controller.findCustomer(d.customerId);
            if (customer != null) {
                PatientHistory history = new PatientHistory(d.id, d.age, d.patientName, d.skinType,
                        d.treatmentHistory, d.recordDate == null ? null : DateUtil.parse(d.recordDate),
                        d.allergies, d.observations, customer);
                controller.getHistories().add(history);
            }
        }
    }

    private void loadProducts(SpaController controller) throws IOException {
        ArrayList<StoredData.ProductData> list = read("products.json",
                new TypeToken<ArrayList<StoredData.ProductData>>() { }.getType());
        controller.getInventory().getProducts().clear();
        for (int i = 0; i < list.size(); i++) {
            StoredData.ProductData d = list.get(i);
            Product product = new Product(d.id, d.name, d.brand, new Price(d.discount, d.price),
                    DateUtil.parse(d.expirationDate), d.quantity);
            controller.getInventory().addProduct(product);
        }
    }

    private void loadAppointments(SpaController controller) throws IOException {
        ArrayList<StoredData.AppointmentData> list = read("appointments.json",
                new TypeToken<ArrayList<StoredData.AppointmentData>>() { }.getType());
        controller.getAppointments().clear();
        for (int i = 0; i < list.size(); i++) {
            StoredData.AppointmentData d = list.get(i);
            Customer customer = controller.findCustomer(d.customerId);
            if (customer != null) {
                Appointment appointment = new Appointment(d.id, DateUtil.parse(d.date), d.time, d.status,
                        customer, null, controller.getLocation(), controller.findService(d.serviceId));
                customer.getAppointments().add(appointment);
                controller.getAppointments().add(appointment);
            }
        }
    }

    private void loadSales(SpaController controller) throws IOException {
        ArrayList<StoredData.SaleData> list = read("sales.json",
                new TypeToken<ArrayList<StoredData.SaleData>>() { }.getType());
        controller.getSales().clear();
        for (int i = 0; i < list.size(); i++) {
            StoredData.SaleData d = list.get(i);
            Sale sale = new Sale(d.id, DateUtil.parse(d.date), controller.findCustomer(d.customerId),
                    controller.getEmployee(), null, d.details);
            for (int j = 0; j < d.items.size(); j++) {
                Product product = controller.getInventory().getProductDetails(d.items.get(j).productId);
                if (product != null) {
                    sale.addItem(product, d.items.get(j).quantity);
                }
            }
            if (d.payment != null) {
                sale.setPayment(new Payment(d.payment.id, d.payment.amount, d.payment.method,
                        DateUtil.parse(d.payment.date), d.payment.paid));
            }
            controller.getSales().add(sale);
        }
    }

    private void write(String fileName, Object data) throws IOException {
        File file = new File(folder, fileName);
        Files.write(file.toPath(), gson.toJson(data).getBytes(StandardCharsets.UTF_8));
    }

    private <T> ArrayList<T> read(String fileName, Type type) throws IOException {
        File file = new File(folder, fileName);
        ArrayList<T> list = new ArrayList<>();
        if (file.exists()) {
            String text = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            ArrayList<T> loaded = gson.fromJson(text, type);
            if (loaded != null) {
                list = loaded;
            }
        }
        return list;
    }
}
