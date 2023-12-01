package building;

import resource.Resource;
import resource.ResourceType;

import java.util.Arrays;

public class ApartmentBuildingFactory implements BuildingFactory {
    @Override
    public Building createBuilding() {
        return new Building(BuildingType.APARTMENT_BUILDING, 120, 0, 6,
                Arrays.asList(new Resource(ResourceType.GOLD, 4), new Resource(ResourceType.WOOD, 50),
                        new Resource(ResourceType.STONE, 50)), Arrays.asList(), Arrays.asList(), 60, 0);
    }
}
