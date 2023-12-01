package building;

import resource.Resource;
import resource.ResourceType;

import java.util.Arrays;

public class SteelMillFactory implements BuildingFactory {
    @Override
    public Building createBuilding() {
        return new Building(BuildingType.STEEL_MILL, 0, 80, 6,
                Arrays.asList(new Resource(ResourceType.GOLD, 6), new Resource(ResourceType.WOOD, 100),
                        new Resource(ResourceType.STONE, 50)), Arrays.asList(new Resource(ResourceType.IRON, 4),
                new Resource(ResourceType.COAL, 2)), Arrays.asList(new Resource(ResourceType.STEEL, 4)), 0, 40);
    }
}
