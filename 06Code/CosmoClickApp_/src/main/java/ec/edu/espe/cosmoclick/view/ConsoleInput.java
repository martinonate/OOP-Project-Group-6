package ec.edu.espe.cosmoclick.view;

import ec.edu.espe.cosmoclick.controller.DateUtil;
import java.util.Calendar;
import java.util.Scanner;

/**
 * 
 *
 * @author Martin Onate <Team 6, espe>
 */
public class ConsoleInput {

    private final Scanner scanner;

    public ConsoleInput() {
        this.scanner = new Scanner(System.in);
    }

    public String readText(String message) {
        String text = "";
        while (text.isEmpty()) {
            System.out.print(message);
            text = scanner.nextLine().trim();
            if (text.isEmpty()) {
                System.out.println("El campo no puede estar vacio.");
            }
        }
        return text;
    }

    public int readInt(String message) {
        while (true) {
            try {
                return Integer.parseInt(readText(message));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero entero valido.");
            }
        }
    }

    public int readPositiveInt(String message) {
        int number = readInt(message);
        while (number <= 0) {
            System.out.println("El numero debe ser mayor que cero.");
            number = readInt(message);
        }
        return number;
    }

    public double readPositiveDouble(String message) {
        while (true) {
            try {
                double number = Double.parseDouble(readText(message).replace(',', '.'));
                if (number > 0) {
                    return number;
                }
                System.out.println("El valor debe ser mayor que cero.");
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido (ej: 12.50).");
            }
        }
    }

    public Calendar readDate(String message) {
        Calendar date = null;
        while (date == null) {
            date = DateUtil.parse(readText(message + " (aaaa-mm-dd): "));
            if (date == null) {
                System.out.println("Fecha invalida. Use el formato aaaa-mm-dd, por ejemplo 2026-12-31.");
            }
        }
        return date;
    }

    public void waitForEnter() {
        System.out.print("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }
}
