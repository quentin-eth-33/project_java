package resource;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private Map<ResourceType, Resource> resources;

    public ResourceManager() {
        this.resources = new HashMap<>();
        initializeResources();
    }
    private void initializeResources() {
        for (ResourceType resourceType : ResourceType.values()) {
            resources.put(resourceType, new Resource(resourceType, 0));
        }
    }

    public Resource getResource(ResourceType resourceType) {
        return resources.get(resourceType);
    }

    public void addResource(ResourceType resourceType, int quantity) {
        Resource resource = resources.get(resourceType);
        if (resource != null) {
            resource.setQuantity(resource.getQuantity() + quantity);
        }
    }

    public void consumeResource(ResourceType resourceType, int quantity) {
        Resource resource = resources.get(resourceType);
        if (resource != null) {
            int newQuantity = resource.getQuantity() - quantity;
            if (newQuantity >= 0) {
                resource.setQuantity(newQuantity);
            } else {
                System.out.println("Ressource insuffisante : " + resourceType);
            }
        }
    }
}