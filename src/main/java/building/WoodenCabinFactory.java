package building;
import resource.Resource;
import resource.ResourceType;

import java.util.Arrays;


public class WoodenCabinFactory implements BuildingFactory {
    @Override
    public Building createBuilding() {
        return new Building(BuildingType.WOODEN_CABIN, 4, 4, 2, Arrays.asList(new Resource(ResourceType.GOLD, 1), new Resource(ResourceType.WOOD, 1)),
                Arrays.asList(), Arrays.asList(new Resource(ResourceType.WOOD, 2), new Resource(ResourceType.FOOD, 2)), 2, 2);
    }
}


