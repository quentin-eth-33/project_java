package building;

import resource.Resource;
import resource.ResourceType;

import java.util.Arrays;

public class ToolFactory implements BuildingFactory {
    @Override
    public Building createBuilding() {
        return new Building(BuildingType.TOOL_FACTORY, 0, 24, 8,
                Arrays.asList(new Resource(ResourceType.GOLD, 8), new Resource(ResourceType.WOOD, 50),
                        new Resource(ResourceType.STONE, 50)), Arrays.asList(new Resource(ResourceType.STEEL, 4),
                new Resource(ResourceType.COAL, 4)), Arrays.asList(new Resource(ResourceType.TOOLS, 4)), 0, 12);
    }
}
