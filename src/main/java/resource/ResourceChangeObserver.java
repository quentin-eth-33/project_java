package resource;

public class ResourceChangeObserver implements ResourceObserver {
    @Override
    public void onResourceChanged(ResourceType resourceType, int newQuantity, int consumedQuantity) {
        System.out.println("La ressource " + resourceType + " a changé. Nouvelle quantité : " + newQuantity +
                " (Consommé : " + consumedQuantity + ")");
    }
}