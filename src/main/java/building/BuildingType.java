package building;

import resource.Resource;
import resource.ResourceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum BuildingType {
    WOODEN_CABIN(2, 2, 2, Arrays.asList(new Resource(ResourceType.GOLD, 1), new Resource(ResourceType.WOOD, 1)), Arrays.asList(),
            Arrays.asList(new Resource(ResourceType.WOOD, 2), new Resource(ResourceType.FOOD, 2))),
    HOUSE(4, 0, 4, Arrays.asList(new Resource(ResourceType.GOLD, 1), new Resource(ResourceType.WOOD, 2),
            new Resource(ResourceType.STONE, 2)), Arrays.asList(), Arrays.asList()),
    APARTMENT_BUILDING(60, 0, 6, Arrays.asList(new Resource(ResourceType.GOLD, 4), new Resource(ResourceType.WOOD, 50),
            new Resource(ResourceType.STONE, 50)), Arrays.asList(), Arrays.asList()),
    FARM(5, 3, 2, Arrays.asList(new Resource(ResourceType.GOLD, 4), new Resource(ResourceType.WOOD, 5),
            new Resource(ResourceType.STONE, 5)), Arrays.asList(), Arrays.asList(new Resource(ResourceType.FOOD, 10))),
    QUARRY(2, 30, 2, Arrays.asList(new Resource(ResourceType.GOLD, 4), new Resource(ResourceType.WOOD, 50)),
            Arrays.asList(), Arrays.asList(new Resource(ResourceType.STONE, 4), new Resource(ResourceType.IRON, 4),
            new Resource(ResourceType.COAL, 4), new Resource(ResourceType.GOLD, 2))),
    LUMBER_MILL(0, 10, 4, Arrays.asList(new Resource(ResourceType.GOLD, 6), new Resource(ResourceType.WOOD, 50),
            new Resource(ResourceType.STONE, 50)), Arrays.asList(new Resource(ResourceType.WOOD, 4)),
            Arrays.asList(new Resource(ResourceType.LUMBER, 4))),
    CEMENT_PLANT(0, 10, 4, Arrays.asList(new Resource(ResourceType.GOLD, 6), new Resource(ResourceType.WOOD, 50),
            new Resource(ResourceType.STONE, 50)), Arrays.asList(new Resource(ResourceType.STONE, 4),
            new Resource(ResourceType.COAL, 4)), Arrays.asList(new Resource(ResourceType.CEMENT, 4))),
    STEEL_MILL(0, 40, 6, Arrays.asList(new Resource(ResourceType.GOLD, 6), new Resource(ResourceType.WOOD, 100),
            new Resource(ResourceType.STONE, 50)), Arrays.asList(new Resource(ResourceType.IRON, 4),
            new Resource(ResourceType.COAL, 2)), Arrays.asList(new Resource(ResourceType.STEEL, 4))),
    TOOL_FACTORY(0, 12, 8, Arrays.asList(new Resource(ResourceType.GOLD, 8), new Resource(ResourceType.WOOD, 50),
            new Resource(ResourceType.STONE, 50)), Arrays.asList(new Resource(ResourceType.STEEL, 4),
            new Resource(ResourceType.COAL, 4)), Arrays.asList(new Resource(ResourceType.TOOLS, 4)));

    private int maxResidents;
    private final int maxWorkers;
    private final int constructionTime;
    private final List<Resource> constructionCost;
    private final List<Resource> consumption;
    private final List<Resource> production;


    BuildingType(int maxResidents, int maxWorkers, int constructionTime, List<Resource> constructionCost, List<Resource> consumption,
                 List<Resource> production) {
        this.maxResidents = maxResidents;
        this.maxWorkers = maxWorkers;
        this.constructionTime = constructionTime;
        this.production = new ArrayList<>(production);
        this.constructionCost = new ArrayList<>(constructionCost);
        this.consumption = new ArrayList<>(consumption);
    }

    public int getMaxResidents() {
        return maxResidents;
    }
    public void setMaxResidents(int maxResidents) {
         this.maxResidents = maxResidents;
    }

    public int getMaxWorkers() {
        return maxWorkers;
    }

    public int getConstructionTime() {
        return constructionTime;
    }

    public List<Resource> getConstructionCost() {
        return new ArrayList<>(constructionCost);
    }

    public List<Resource> getProduction() {
        return new ArrayList<>(production);
    }

    public List<Resource> getConsumption() {
        return new ArrayList<>(consumption);
    }
}
