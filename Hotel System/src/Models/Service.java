// Service.java
package Models;

public class Service {
    private int serviceID;
    private String name;
    private String description;
    private double price;

    public Service() {
        this(0, "", "", 0.0);
    }

    public Service(int serviceID, String name, String description, double price) {
        this.serviceID = serviceID;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getServiceDetails() {
        if (description == null || description.isEmpty()) {
            return serviceID + " - " + name + " - " + price;
        }
        return serviceID + " - " + name + " - " + description + " - " + price;
    }
}
