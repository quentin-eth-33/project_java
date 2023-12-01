package building;

public enum BuildingType {
    WOODEN_CABIN("Wooden Cabin"),
    HOUSE("House"),
    APARTMENT_BUILDING("Apartment Building"),
    FARM("Farm"),
    QUARRY("Quarry"),
    LUMBER_MILL("Lumber Mill"),
    CEMENT_PLANT("Cement Plant"),
    STEEL_MILL("Steel Mill"),
    TOOL_FACTORY("Tool Factory");

    private final String buildingName;

    BuildingType(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingName() {
        return buildingName;
    }
}
