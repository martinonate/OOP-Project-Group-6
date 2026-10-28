package ec.edu.espe.cosmoclick.controller;

import ec.edu.espe.cosmoclick.model.Customer;
import ec.edu.espe.cosmoclick.model.Payment;
import ec.edu.espe.cosmoclick.model.PaymentReport;
import ec.edu.espe.cosmoclick.model.Product;
import ec.edu.espe.cosmoclick.model.Sale;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * 
 * @author Martin Onate <Team 6, espe>
 */
public class CsvExporter {
    
    private final String folder;

    
    public CsvExporter(String folder) {
        this.folder = folder;
    }

    public File exportCustomers(SpaController controller) throws IOException {
        String[] headers = {"id", "name", "phone", "email"};
        ArrayList<String[]> rows = new ArrayList<>();
        for (int i = 0; i < controller.getCustomers().size(); i++) {
            Customer c = controller.getCustomers().get(i);
            String[] row = {String.valueOf(c.getId()), c.getName(), String.valueOf(c.getPhone()), c.getEmail()};
            rows.add(row);
        }
        return writeReport("customers_report.csv", headers, rows);
    }

    public File exportPayments(SpaController controller, String method) throws IOException {
        PaymentReport report = controller.buildPaymentReport(method);
        String[] headers = {"payment_id", "sale_id", "customer", "method", "amount", "status", "date"};
        ArrayList<String[]> rows = new ArrayList<>();
        for (int i = 0; i < report.getPaymentCount(); i++) {
            Payment payment = report.getPayments()[i];
            Sale sale = controller.findSaleByPayment(payment);
            String[] row = {
                String.valueOf(payment.getId()),
                String.valueOf(sale.getId()),
                sale.getCustomer().getName(),
                payment.getPaymentMethod(),
                String.valueOf(payment.getAmount()),
                payment.checkStatus(),
                DateUtil.format(payment.getPaymentDate())
            };
            rows.add(row);
        }
        String[] totalRow = {"TOTAL", "", "", "", String.valueOf(report.calculateTotal()), "", ""};
        rows.add(totalRow);
        return writeReport("payments_report.csv", headers, rows);
    }

    public File exportInventory(SpaController controller) throws IOException {
        Calendar today = Calendar.getInstance();
        String[] headers = {"id", "name", "brand", "price", "quantity", "expiration_date", "expired"};
        ArrayList<String[]> rows = new ArrayList<>();
        ArrayList<Product> products = controller.getInventory().getProducts();
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            String[] row = {
                String.valueOf(p.getId()),
                p.getName(),
                p.getBrand(),
                String.valueOf(p.getPrice().calculateFinalPrice()),
                String.valueOf(p.getQuantity()),
                DateUtil.format(p.getExpirationDate()),
                String.valueOf(p.checkExpiration(today))
            };
            rows.add(row);
        }
        return writeReport("inventory_report.csv", headers, rows);
    }

    private File writeReport(String fileName, String[] headers, ArrayList<String[]> rows) throws IOException {
        StringBuilder text = new StringBuilder();
        text.append(joinLine(headers));
        for (int i = 0; i < rows.size(); i++) {
            text.append(joinLine(rows.get(i)));
        }
        new File(folder).mkdirs();
        File file = new File(folder, fileName);
        Files.write(file.toPath(), text.toString().getBytes(StandardCharsets.UTF_8));
        return file;
    }

    private String joinLine(String[] cells) {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < cells.length; i++) {
            if (i > 0) {
                line.append(',');
            }
            line.append(escape(cells[i]));
        }
        line.append('\n');
        return line.toString();
    }

    private String escape(String value) {
        if (value == null) {
            return "";
        }
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
