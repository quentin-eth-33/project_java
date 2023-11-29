package resource;

import java.util.ArrayList;
import java.util.List;

public class Resource {
    private ResourceType type;
    private int quantity;
    private List<ResourceObserver> observers;

    public Resource(ResourceType type, int quantity) {
        this.type = type;
        this.quantity = quantity;
        this.observers = new ArrayList<>();
    }

    public ResourceType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        notifyObservers();
    }

    public void addObserver(ResourceObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ResourceObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (ResourceObserver observer : observers) {
            observer.onResourceChanged(type, quantity);
        }
    }
}
