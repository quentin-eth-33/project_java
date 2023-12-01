package building;

import resource.Resource;
import resource.ResourceType;

import java.util.Arrays;

public class LumberMillFactory implements BuildingFactory {
    @Override
    public Building createBuilding() {
        return new Building(BuildingType.LUMBER_MILL, 0, 20, 4,
                Arrays.asList(new Resource(ResourceType.GOLD, 6), new Resource(ResourceType.WOOD, 50),
                        new Resource(ResourceType.STONE, 50)), Arrays.asList(new Resource(ResourceType.WOOD, 4)),
                Arrays.asList(new Resource(ResourceType.LUMBER, 4)), 0, 10);
    }
}

