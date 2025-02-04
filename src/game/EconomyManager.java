package game;

import buildings.Building;

public class EconomyManager {
    private final City city;

    public EconomyManager(City city) {
        this.city = city;
    }

    public void collectTaxes() {
        int residentialRevenue = city.getResidentialBuildings().stream()
                .mapToInt(Building::calculateRevenue)
                .sum();
        int commercialRevenue = city.getCommercialBuildings().stream()
                .mapToInt(Building::calculateRevenue)
                .sum();
        int industrialRevenue = city.getIndustrialBuildings().stream()
                .mapToInt(Building::calculateRevenue)
                .sum();

        city.addMoney(residentialRevenue + commercialRevenue + industrialRevenue);
    }

    public void maintenanceCost() {
        int residentialCost = city.getResidentialBuildings().stream()
                .mapToInt(Building::getMaintenanceCost)
                .sum();
        int commercialCost = city.getCommercialBuildings().stream()
                .mapToInt(Building::getMaintenanceCost)
                .sum();
        int industrialCost = city.getIndustrialBuildings().stream()
                .mapToInt(Building::getMaintenanceCost)
                .sum();

        city.addMoney(-(residentialCost + commercialCost + industrialCost));

        if(city.getMoney() < 0) {
            city.addHappiness(-10);
        }
    }
}