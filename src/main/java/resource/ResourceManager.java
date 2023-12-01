package resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceManager {
    private Map<ResourceType, Resource> resources;
    private List<ResourceObserver> resourceObservers;

    public ResourceManager() {
        this.resources = new HashMap<>(); // METTRE LES RESSOURCE DE BASE
        this.resourceObservers = new ArrayList<>();
        initializeResources();
    }

    private void initializeResources() {
        for (ResourceType resourceType : ResourceType.values()) {
        Resource resource = new Resource(resourceType, 1000); // A CHANGER le 1000
            resources.put(resourceType, resource);
            resource.addObserver(new ResourceChangeObserver());
        }
    }


    public void updateFoodConsumption(int numResidents) {
        Resource foodResource = getResource(ResourceType.FOOD);
        int foodConsumption = numResidents; // Chaque habitant consomme une unité de nourriture par unité de temps
        if (foodResource.getQuantity() >= foodConsumption) {
            foodResource.setQuantity(foodResource.getQuantity() - foodConsumption);
        } else {
            // Gérer les conséquences de la famine, par exemple, diminuer la population ou le bonheur des habitants
            // ...
        }
    }


    public Resource getResource(ResourceType resourceType) {
        return resources.get(resourceType);
    }

    public void addResourceObserver(ResourceObserver observer) {
        resourceObservers.add(observer);
    }

    public void removeResourceObserver(ResourceObserver observer) {
        resourceObservers.remove(observer);
    }

    private void notifyResourceObservers(ResourceType resourceType, int newQuantity, int consumedQuantity) {
        for (ResourceObserver observer : resourceObservers) {
            observer.onResourceChanged(resourceType, newQuantity, consumedQuantity);
        }
    }

}
