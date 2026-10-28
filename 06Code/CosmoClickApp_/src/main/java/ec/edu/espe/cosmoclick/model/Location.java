
package ec.edu.espe.cosmoclick.model;

/**
 *
 * @author Martin Onate <Team 6, espe>
 */
public class Location {
    
    private String address;
    private String reference;
    
    public Location() {
    }

    public Location(String address, String reference) {
        this.address = address;
        this.reference = reference;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getLocation() {
        return address + " (" + reference + ")";
    }

    
}
