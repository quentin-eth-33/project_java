package resource;

public interface ResourceObserver {
    void onResourceChanged(ResourceType resourceType, int newQuantity);
}
