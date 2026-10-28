
package ec.edu.espe.cosmoclick.model;

/**
 *
 * @author Martin Onate <Team 6, espe>
 */
public class Service {
    
    private int id;
    private String name;
    private String description;
    private Price price;
    private int duration;

    public Service() {
    }

    public Service(int id, String name, String description, Price price, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void displayDetails() {
        System.out.println("Servicio: " + name);
        System.out.println("Descripcion: " + description);
        System.out.println("Duracion: " + duration + " minutos");
        System.out.println("Precio: " + price.calculateFinalPrice());
    }

    public void updatePrice(Price price) {
        this.price = price;
    }
    
}
