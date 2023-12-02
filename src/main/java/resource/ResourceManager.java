package resource;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private static ResourceManager instance;

    private Map<ResourceType, Resource> resources;

    private ResourceManager() {
        this.resources = new HashMap<>();
        initializeResources();
    }

    public static ResourceManager getInstance() {
        if (instance == null) {
            synchronized (ResourceManager.class) {
                if (instance == null) {
                    instance = new ResourceManager();
                }
            }
        }
        return instance;
    }

    private void initializeResources() {
        for (ResourceType resourceType : ResourceType.values()) {
            Resource resource = new Resource(resourceType, 1000);
            resources.put(resourceType, resource);
        }
    }

    public Resource getResource(ResourceType resourceType) {
        return resources.get(resourceType);
    }
}
