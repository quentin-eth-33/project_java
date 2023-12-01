package building;

import resource.Resource;
import resource.ResourceType;

import java.util.Arrays;

public class FarmFactory implements BuildingFactory {
    @Override
    public Building createBuilding() {
        return new Building(BuildingType.FARM, 10, 6, 2,
                Arrays.asList(new Resource(ResourceType.GOLD, 4), new Resource(ResourceType.WOOD, 5),
                        new Resource(ResourceType.STONE, 5)), Arrays.asList(),
                Arrays.asList(new Resource(ResourceType.FOOD, 10)), 5, 3);
    }
}
