package building;

import resource.Resource;
import resource.ResourceType;

import java.util.Arrays;

public class HouseFactory implements BuildingFactory {
    @Override
    public Building createBuilding() {
        return new Building(BuildingType.HOUSE, 8, 0, 4,
                Arrays.asList(new Resource(ResourceType.GOLD, 1), new Resource(ResourceType.WOOD, 2),
                        new Resource(ResourceType.STONE, 2)), Arrays.asList(), Arrays.asList(), 4, 0);
    }
}
