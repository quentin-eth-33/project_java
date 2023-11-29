package resource;

public class ResourceChangeObserver implements ResourceObserver {
    @Override
    public void onResourceChanged(ResourceType resourceType, int newQuantity) {
        System.out.println("La ressource " + resourceType + " a changé. Nouvelle quantité : " + newQuantity);
    }
}
