package ec.edu.espe.cosmoclick.view;

import ec.edu.espe.cosmoclick.controller.CsvExporter;
import ec.edu.espe.cosmoclick.controller.DateUtil;
import ec.edu.espe.cosmoclick.controller.JsonStorage;
import ec.edu.espe.cosmoclick.controller.SpaController;
import ec.edu.espe.cosmoclick.model.Appointment;
import ec.edu.espe.cosmoclick.model.Customer;
import ec.edu.espe.cosmoclick.model.PatientHistory;
import ec.edu.espe.cosmoclick.model.Product;
import ec.edu.espe.cosmoclick.model.Sale;
import ec.edu.espe.cosmoclick.model.Service;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Martin Onate <Team 6, espe>
 */
public class ConsoleMenu {

    private final SpaController controller;
    private final JsonStorage storage;
    private final CsvExporter exporter;
    private final ConsoleInput input;

    public ConsoleMenu(SpaController controller, JsonStorage storage, CsvExporter exporter) {
        this.controller = controller;
        this.storage = storage;
        this.exporter = exporter;
        this.input = new ConsoleInput();
    }

    public void run() {
        int option = -1;
        while (option != 0) {
            showMainMenu();
            option = input.readInt("Elija una opcion: ");
            executeOption(option);
            if (option != 0) {
                input.waitForEnter();
            }
        }
    }

    private void showMainMenu() {
        System.out.println();
        System.out.println("========== COSMOCLICK - MaGaby Home Spa ==========");
        System.out.println("");
        System.out.println("--- Clientes ---");
        System.out.println(" 1. Registrar cliente");
        System.out.println(" 2. Ver clientes");
        System.out.println(" 3. Actualizar datos de un cliente");
        System.out.println(" 4. Registrar historial del paciente");
        System.out.println(" 5. Ver historial del paciente");
        System.out.println("");
        System.out.println("--- Diagnóstico ---");
        System.out.println(" 6. Registrar diagnóstico del paciente");
        System.out.println(" 7. Actualizar diagnóstico del paciente");
        System.out.println("");
        System.out.println("--- Citas ---");
        System.out.println(" 8. Agendar cita");
        System.out.println(" 9. Ver citas");
        System.out.println("10. Confirmar cita");
        System.out.println("11. Cancelar cita");
        System.out.println("");
        System.out.println("--- Inventario y ventas ---");
        System.out.println("12. Registrar producto");
        System.out.println("13. Ver inventario y alertas de stock bajo");
        System.out.println("14. Reabastecer producto");
        System.out.println("15. Registrar venta y pago");
        System.out.println("16. Cancelar venta");
        System.out.println("");
        System.out.println("--- Reportes (CSV) ---");
        System.out.println("17. Reporte de pagos");
        System.out.println("18. Reporte de inventario");
        System.out.println("19. Reporte de clientes");
        System.out.println(" 0. Guardar y salir");
    }

    private void executeOption(int option) {
        switch (option) {
            case 1 -> registerCustomer();
            case 2 -> showCustomers();
            case 3 -> updateCustomer();
            case 4 -> registerPatientHistory();
            case 5 -> viewPatientHistory();
            case 6 -> addDiagnosticRecord();
            case 7 -> updateDiagnosticRecord();
            case 8 -> scheduleAppointment();
            case 9 -> showAppointments();
            case 10 -> confirmAppointment();
            case 11 -> cancelAppointment();
            case 12 -> registerProduct();
            case 13 -> showInventory();
            case 14 -> restockProduct();
            case 15 -> registerSale();
            case 16 -> cancelSale();
            case 17 -> exportPaymentsReport();
            case 18 -> exportInventoryReport();
            case 19 -> exportCustomersReport();
            default -> System.out.println("Opcion no valida.");
        }
    }

    
    private void registerCustomer() {
        String name = input.readText("Nombre: ");
        int phone = input.readPositiveInt("Telefono (solo numeros): ");
        String email = input.readText("Correo: ");
        Customer customer = controller.registerCustomer(name, phone, email);
        System.out.println("Cliente registrado con ID " + customer.getId() + ".");
    }

    private void showCustomers() {
        ArrayList<Customer> customers = controller.getCustomers();
        if (customers.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        }
        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            System.out.println(c.getId() + " | " + c.getName() + " | " + c.getPhone() + " | " + c.getEmail());
        }
    }

    private void updateCustomer() {
        int id = input.readInt("ID del cliente: ");
        if (controller.findCustomer(id) == null) {
            System.out.println("No existe un cliente con ese ID.");
            return;
        }
        String name = input.readText("Nuevo nombre: ");
        int phone = input.readPositiveInt("Nuevo telefono: ");
        String email = input.readText("Nuevo correo: ");
        controller.updateCustomer(id, name, phone, email);
        System.out.println("Datos actualizados.");
    }
    
    private void registerPatientHistory() {
            int customerId = input.readInt("ID del cliente: ");
            if (controller.findCustomer(customerId) == null) {
                System.out.println("No existe un cliente con ese ID.");
                return;
        }
        int age = input.readPositiveInt("Edad: ");
        String skinType = input.readText("Tipo de piel: ");
        String allergies = input.readText("Alergias: ");
        String treatments = input.readText("Tratamientos previos: ");
        String observations = input.readText("Observaciones: ");
        controller.registerPatientHistory(customerId, age, skinType, allergies, treatments, observations);
        System.out.println("Historial registrado.");
    }

    private void viewPatientHistory() {
        int customerId = input.readInt("ID del cliente: ");
            PatientHistory history = controller.findHistoryByCustomer(customerId);
            if (history == null) {
                System.out.println("Ese cliente no tiene historial.");
            } else {
            history.viewPatientHistory();
        }
    }
    
    private void addDiagnosticRecord(){
        int customerId = input.readInt("ID del cliente: ");
            if (controller.findHistoryByCustomer(customerId) == null) {
                System.out.println("Ese cliente no tiene historial. Registralo primero (opcion 4).");
        return;
    }
        String skinType = input.readText("Tipo de piel: ");
        String healthHistory = input.readText("Antecedentes de salud: ");
        String observations = input.readText("Observaciones: ");
        controller.addDiagnosticRecord(customerId, skinType, healthHistory, observations);
        System.out.println("Registro de diagnostico agregado.");
    }

    private void updateDiagnosticRecord() {
        int customerId = input.readInt("ID del cliente: ");
        int recordId = input.readInt("ID del registro: ");
        String skinType = input.readText("Nuevo tipo de piel: ");
        String healthHistory = input.readText("Nuevos antecedentes de salud: ");
        String observations = input.readText("Nuevas observaciones: ");
        
        if (controller.updateDiagnosticRecord(customerId, recordId, skinType, healthHistory, observations)) {
            
            System.out.println("Registro actualizado.");
            
        } else {
            
            System.out.println("No se encontro el historial o el registro.");
            
        }
    }

    
    private void scheduleAppointment() {
        int customerId = input.readInt("ID del cliente: ");
        if (controller.findCustomer(customerId) == null) {
            System.out.println("No existe un cliente con ese ID.");
            return;
        }
        showServices();
        int serviceId = input.readInt("ID del servicio: ");
        if (controller.findService(serviceId) == null) {
            System.out.println("No existe ese servicio.");
            return;
        }
        Calendar date = input.readDate("Fecha de la cita");
        String time = input.readText("Hora (ej: 10:00): ");
        Appointment appointment = controller.scheduleAppointment(customerId, serviceId, date, time);
        if (appointment == null) {
            System.out.println("Ese horario ya esta ocupado. Elija otra fecha u hora.");
        } else {
            System.out.println("Cita agendada con ID " + appointment.getId() + ".");
            System.out.println("Lugar: " + appointment.seeLocation().getLocation());
        }
    }

    private void showServices() {
        ArrayList<Service> services = controller.getServices();
        for (int i = 0; i < services.size(); i++) {
            Service s = services.get(i);
            System.out.println(s.getId() + " | " + s.getName() + " | $" + s.getPrice().calculateFinalPrice()
                    + " | " + s.getDuration() + " min");
        }
    }

    private void showAppointments() {
        ArrayList<Appointment> appointments = controller.getAppointments();
        if (appointments.isEmpty()) {
            System.out.println("No hay citas registradas.");
        }
        for (int i = 0; i < appointments.size(); i++) {
            Appointment a = appointments.get(i);
            System.out.println(a.getId() + " | " + DateUtil.format(a.getDate()) + " " + a.getTime()
                    + " | " + a.getCustomer().getName() + " | " + a.getService().getName()
                    + " | " + a.getStatus());
        }
    }

    private void confirmAppointment() {
        int id = input.readInt("ID de la cita: ");
        if (controller.confirmAppointment(id)) {
            System.out.println("Cita confirmada.");
        } else {
            System.out.println("No se pudo confirmar (no existe o esta cancelada).");
        }
    }

    private void cancelAppointment() {
        int id = input.readInt("ID de la cita: ");
        if (controller.cancelAppointment(id)) {
            System.out.println("Cita cancelada.");
        } else {
            System.out.println("No existe una cita con ese ID.");
        }
    }

    private void registerProduct() {
        String name = input.readText("Nombre del producto: ");
        String brand = input.readText("Marca: ");
        double price = input.readPositiveDouble("Precio: ");
        Calendar expiration = input.readDate("Fecha de caducidad");
        int quantity = input.readPositiveInt("Cantidad: ");
        Product product = controller.registerProduct(name, brand, price, expiration, quantity);
        System.out.println("Producto registrado con ID " + product.getId() + ".");
    }

    private void showInventory() {
        ArrayList<Product> products = controller.getInventory().getProducts();
        if (products.isEmpty()) {
            System.out.println("El inventario esta vacio.");
        }
        Calendar today = Calendar.getInstance();
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            String expired = p.checkExpiration(today) ? " | CADUCADO" : "";
            System.out.println(p.getId() + " | " + p.getName() + " | " + p.getBrand() + " | $"
                    + p.getPrice().calculateFinalPrice() + " | Stock: " + p.getQuantity() + expired);
        }
        System.out.println("Stock total: " + controller.getInventory().getTotalStock());
        controller.getEmployee().manageInventory(controller.getInventory());
    }

    private void restockProduct() {
        int id = input.readInt("ID del producto: ");
        int quantity = input.readPositiveInt("Cantidad a agregar: ");
        if (controller.restockProduct(id, quantity)) {
            System.out.println("Stock actualizado.");
        } else {
            System.out.println("No existe un producto con ese ID.");
        }
    }

    private void registerSale() {
        int customerId = input.readInt("ID del cliente: ");
        if (controller.findCustomer(customerId) == null) {
            System.out.println("No existe un cliente con ese ID.");
            return;
        }
        int count = input.readPositiveInt("Cuantos productos distintos va a vender: ");
        int[] productIds = new int[count];
        int[] quantities = new int[count];
        for (int i = 0; i < count; i++) {
            productIds[i] = input.readInt("ID del producto " + (i + 1) + ": ");
            quantities[i] = input.readPositiveInt("Cantidad: ");
        }
        String method = input.readText("Metodo de pago (Efectivo/Tarjeta/Transferencia): ");
        Sale sale = controller.registerSale(customerId, productIds, quantities, method);
        if (sale == null) {
            System.out.println("No se pudo registrar la venta: revise los IDs y el stock disponible.");
        } else {
            sale.generateReceipt();
            System.out.println("Pago: " + sale.getPayment().checkStatus());
        }
    }

    private void cancelSale() {
        int id = input.readInt("ID de la venta: ");
        if (controller.cancelSale(id)) {
            System.out.println("Venta cancelada y stock devuelto.");
        } else {
            System.out.println("No se pudo cancelar (no existe o ya estaba cancelada).");
        }
    }

    private void exportPaymentsReport() {
        String method = input.readText("Metodo a reportar (Efectivo/Tarjeta/Transferencia/Todos): ");
        try {
            File file = exporter.exportPayments(controller, method);
            System.out.println("Reporte generado: " + file.getPath());
        } catch (IOException e) {
            System.out.println("No se pudo crear el reporte: " + e.getMessage());
        }
    }

    private void exportInventoryReport() {
        try {
            File file = exporter.exportInventory(controller);
            System.out.println("Reporte generado: " + file.getPath());
        } catch (IOException e) {
            System.out.println("No se pudo crear el reporte: " + e.getMessage());
        }
    }

    private void exportCustomersReport() {
        try {
            File file = exporter.exportCustomers(controller);
            System.out.println("Reporte generado: " + file.getPath());
        } catch (IOException e) {
            System.out.println("No se pudo crear el reporte: " + e.getMessage());
        }
    }

    private void saveData() {
        try {
            storage.save(controller);
            System.out.println("Datos guardados en la carpeta data/. Hasta luego.");
        } catch (IOException e) {
            System.out.println("No se pudieron guardar los datos: " + e.getMessage());
        }
    }
}
