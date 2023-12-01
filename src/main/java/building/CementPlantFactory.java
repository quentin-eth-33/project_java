package building;

import resource.Resource;
import resource.ResourceType;

import java.util.Arrays;

public class CementPlantFactory implements BuildingFactory {
    @Override
    public Building createBuilding() {
        return new Building(BuildingType.CEMENT_PLANT, 0, 20, 4,
                Arrays.asList(new Resource(ResourceType.GOLD, 6), new Resource(ResourceType.WOOD, 50),
                        new Resource(ResourceType.STONE, 50)), Arrays.asList(new Resource(ResourceType.STONE, 4),
                new Resource(ResourceType.COAL, 4)), Arrays.asList(new Resource(ResourceType.CEMENT, 4)), 0, 10);
    }
}
