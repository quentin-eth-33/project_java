package building;

import resource.Resource;
import resource.ResourceType;

import java.util.Arrays;

public class QuarryFactory implements BuildingFactory {
    @Override
    public Building createBuilding() {
        return new Building(BuildingType.QUARRY, 4, 60, 2,
                Arrays.asList(new Resource(ResourceType.GOLD, 4), new Resource(ResourceType.WOOD, 50)),
                Arrays.asList(), Arrays.asList(new Resource(ResourceType.STONE, 4), new Resource(ResourceType.IRON, 4),
                new Resource(ResourceType.COAL, 4), new Resource(ResourceType.GOLD, 2)), 2, 30);
    }
}
